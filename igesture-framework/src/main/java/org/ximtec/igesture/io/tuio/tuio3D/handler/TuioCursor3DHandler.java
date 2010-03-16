package org.ximtec.igesture.io.tuio.tuio3D.handler;

import java.util.Enumeration;
import java.util.Vector;

import org.ximtec.igesture.io.tuio.TuioListener;
import org.ximtec.igesture.io.tuio.TuioTime;
import org.ximtec.igesture.io.tuio.handler.AbstractTuioCursorHandler;
import org.ximtec.igesture.io.tuio.tuio3D.TuioCursor3D;


import com.illposed.osc.OSCMessage;

/**
 * Handles /tuio/3Dcur messages.
 * @author bjorn
 *
 */
public class TuioCursor3DHandler extends AbstractTuioCursorHandler<TuioCursor3D> {
	
	/** Constructor */
	public TuioCursor3DHandler()
	{
		
	}

	@Override
	public void acceptMessage(OSCMessage message, TuioTime currentTime) {
		Object[] args = message.getArguments();
		String command = (String)args[0];

		if (command.equals("set")) {

			long s_id  = ((Integer)args[1]).longValue();
			float xpos = ((Float)args[2]).floatValue();
			float ypos = ((Float)args[3]).floatValue();
			float zpos = ((Float)args[4]).floatValue();
			float xspeed = ((Float)args[5]).floatValue();
			float yspeed = ((Float)args[6]).floatValue();
			float zspeed = ((Float)args[7]).floatValue();
			float maccel = ((Float)args[8]).floatValue();
			
			if (cursorList.get(s_id) == null) {
								
				TuioCursor3D addCursor = new TuioCursor3D(s_id, -1 ,xpos,ypos,zpos);
				frameCursors.addElement(addCursor);
				
			} else {
			
				TuioCursor3D tcur = cursorList.get(s_id);
				if (tcur==null) return;
				if ((tcur.getX()!=xpos) || (tcur.getY()!=ypos) || (tcur.getZ()!=zpos) || (tcur.getXSpeed()!=xspeed) || (tcur.getYSpeed()!=yspeed) || (tcur.getZSpeed()!=zspeed) || (tcur.getMotionAccel()!=maccel)) {

					TuioCursor3D updateCursor = new TuioCursor3D(s_id,tcur.getCursorID(),xpos,ypos,zpos);
					updateCursor.update(xpos,ypos,zpos,xspeed,yspeed,zspeed,maccel);
					frameCursors.addElement(updateCursor);
				}
			}
			
			//System.out.println("set cur " + s_id+" "+xpos+" "+ypos+" "+xspeed+" "+yspeed+" "+maccel);
			
		} else if (command.equals("alive")) {

			newCursorList.clear();
			for (int i=1;i<args.length;i++) {
				// get the message content
				long s_id = ((Integer)args[i]).longValue();
				newCursorList.addElement(s_id);
				// reduce the cursor list to the lost cursors
				if (aliveCursorList.contains(s_id)) 
					aliveCursorList.removeElement(s_id);
			}
			
			// remove the remaining cursors
			for (int i=0;i<aliveCursorList.size();i++) {
				TuioCursor3D removeCursor = cursorList.get(aliveCursorList.elementAt(i));
				if (removeCursor==null) continue;
				removeCursor.remove(currentTime);
				frameCursors.addElement(removeCursor);
			}
							
		} else if (command.equals("fseq")) {
			long fseq = ((Integer)args[1]).longValue();
			boolean lateFrame = false;
			
			if (fseq>0) {
				if (fseq>currentFrame) currentTime = TuioTime.getSessionTime();
				if ((fseq>=currentFrame) || ((currentFrame-fseq)>100)) currentFrame = fseq;
				else lateFrame = true;
			} else if (TuioTime.getSessionTime().subtract(currentTime).getTotalMilliseconds()>100) {
				currentTime = TuioTime.getSessionTime();
			}
			if (!lateFrame) {

				Enumeration<TuioCursor3D> frameEnum = frameCursors.elements();
				while(frameEnum.hasMoreElements()) {
					TuioCursor3D tcur = frameEnum.nextElement();
					
					switch (tcur.getTuioState()) {
						case TuioCursor3D.TUIO_REMOVED:
						
							TuioCursor3D removeCursor = tcur;
							removeCursor.remove(currentTime);
							
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.removeTuioCursor(removeCursor);
							}

							cursorList.remove(removeCursor.getSessionID());

							if (removeCursor.getCursorID()==maxCursorID) {
								maxCursorID = -1;
								if (cursorList.size()>0) {
									Enumeration<TuioCursor3D> clist = cursorList.elements();
									while (clist.hasMoreElements()) {
										int c_id = clist.nextElement().getCursorID();
										if (c_id>maxCursorID) maxCursorID=c_id;
									}
									
									Enumeration<TuioCursor3D> flist = freeCursorList.elements();
									while (flist.hasMoreElements()) {
										int c_id = flist.nextElement().getCursorID();
										if (c_id>=maxCursorID) freeCursorList.removeElement(c_id);
									}
								} else freeCursorList.clear();
							} else if (removeCursor.getCursorID()<maxCursorID) {
								freeCursorList.addElement(removeCursor);
							}
							
							break;

						case TuioCursor3D.TUIO_ADDED:

							int c_id = cursorList.size();
							if ((cursorList.size()<=maxCursorID) && (freeCursorList.size()>0)) {
								TuioCursor3D closestCursor = freeCursorList.firstElement();
								Enumeration<TuioCursor3D> testList = freeCursorList.elements();
								while (testList.hasMoreElements()) {
									TuioCursor3D testCursor = testList.nextElement();
									if (testCursor.getDistance(tcur)<closestCursor.getDistance(tcur)) closestCursor = testCursor;
								}
								c_id = closestCursor.getCursorID();
								freeCursorList.removeElement(closestCursor);
							} else maxCursorID = c_id;		
							
							TuioCursor3D addCursor = new TuioCursor3D(currentTime,tcur.getSessionID(),c_id,tcur.getX(),tcur.getY(),tcur.getZ());
							cursorList.put(addCursor.getSessionID(),addCursor);
							
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.addTuioCursor(addCursor);
							}
							break;
							
						default:
							
							TuioCursor3D updateCursor = cursorList.get(tcur.getSessionID());
							if ( (tcur.getX()!=updateCursor.getX() && tcur.getXSpeed()==0) || (tcur.getY()!=updateCursor.getY() && tcur.getYSpeed()==0) || (tcur.getZ()!=updateCursor.getZ() && tcur.getZSpeed()==0))
								updateCursor.update(currentTime,tcur.getX(),tcur.getY(),tcur.getZ());
							else 
								updateCursor.update(currentTime,tcur.getX(),tcur.getY(),tcur.getZ(),tcur.getXSpeed(),tcur.getYSpeed(),tcur.getZSpeed(),tcur.getMotionAccel());
								
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.updateTuioCursor(updateCursor);
							}
					}
				}
				
				for (int i=0;i<listenerList.size();i++) {
					TuioListener listener = (TuioListener)listenerList.elementAt(i);
					if (listener!=null) listener.refresh(new TuioTime(currentTime));
				}
				
				Vector<Long> buffer = aliveCursorList;
				aliveCursorList = newCursorList;
				// recycling the vector
				newCursorList = buffer;				
			}
			
			frameCursors.clear();
		} 
		
	}
}
