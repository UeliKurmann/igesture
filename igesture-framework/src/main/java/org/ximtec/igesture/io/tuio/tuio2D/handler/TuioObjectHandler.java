package org.ximtec.igesture.io.tuio.tuio2D.handler;

import java.util.Enumeration;
import java.util.Vector;

import org.ximtec.igesture.io.tuio.TuioListener;
import org.ximtec.igesture.io.tuio.TuioTime;
import org.ximtec.igesture.io.tuio.handler.AbstractTuioObjectHandler;
import org.ximtec.igesture.io.tuio.tuio2D.TuioObject;


import com.illposed.osc.OSCMessage;

/**
 * Handles /tuio/2Dobj messages.
 * @author Bjorn Puype
 *
 */
public class TuioObjectHandler extends AbstractTuioObjectHandler<TuioObject> {
	
	/** Constructor */
	public TuioObjectHandler()
	{
		
	}
	
	public void acceptMessage(OSCMessage message, TuioTime currentTime) {
		Object[] args = message.getArguments();
		String command = (String)args[0];
		
		if (command.equals("set")) {
			
			long s_id  = ((Integer)args[1]).longValue();
			int c_id  = ((Integer)args[2]).intValue();
			float xpos = ((Float)args[3]).floatValue();
			float ypos = ((Float)args[4]).floatValue();
			float angle = ((Float)args[5]).floatValue();
			float xspeed = ((Float)args[6]).floatValue();
			float yspeed = ((Float)args[7]).floatValue();
			float rspeed = ((Float)args[8]).floatValue();
			float maccel = ((Float)args[9]).floatValue();
			float raccel = ((Float)args[10]).floatValue();
			
			if (objectList.get(s_id) == null) {
			
				TuioObject addObject = new TuioObject(s_id,c_id,xpos,ypos,angle);
				frameObjects.addElement(addObject);
				
			} else {
			
				TuioObject tobj = objectList.get(s_id);
				if (tobj==null) return;
				if ((tobj.getX()!=xpos) || (tobj.getY()!=ypos) || (tobj.getAngle()!=angle) || (tobj.getXSpeed()!=xspeed) || (tobj.getYSpeed()!=yspeed) || (tobj.getRotationSpeed()!=rspeed) || (tobj.getMotionAccel()!=maccel) || (tobj.getRotationAccel()!=raccel)) {
					
					TuioObject updateObject = new TuioObject(s_id,c_id,xpos,ypos,angle);
					updateObject.update(xpos,ypos,angle,xspeed,yspeed,rspeed,maccel,raccel);
					frameObjects.addElement(updateObject);
				}
			
			}
			
		} else if (command.equals("alive")) {

			newObjectList.clear();
			for (int i=1;i<args.length;i++) {
				// get the message content
				long s_id = ((Integer)args[i]).longValue();
				newObjectList.addElement(s_id);
				// reduce the object list to the lost objects
				if (aliveObjectList.contains(s_id))
					 aliveObjectList.removeElement(s_id);
			}
			
			// remove the remaining objects
			for (int i=0;i<aliveObjectList.size();i++) {
				TuioObject removeObject = objectList.get(aliveObjectList.elementAt(i));
				if (removeObject==null) continue;
				removeObject.remove(currentTime);
				frameObjects.addElement(removeObject);
			}
				
		} else if (command.equals("fseq")) {
			
			long fseq = ((Integer)args[1]).longValue();
			boolean lateFrame = false;
			
			if (fseq>0) {
				if (fseq>currentFrame) currentTime = TuioTime.getSessionTime();
				if ((fseq>=currentFrame) || ((currentFrame-fseq)>100)) currentFrame=fseq;
				else lateFrame = true;
			} else if (TuioTime.getSessionTime().subtract(currentTime).getTotalMilliseconds()>100) {
				currentTime = TuioTime.getSessionTime();
			}
			
			if (!lateFrame) {
				Enumeration<TuioObject> frameEnum = frameObjects.elements();
				while(frameEnum.hasMoreElements()) {
					TuioObject tobj = frameEnum.nextElement();
					
					switch (tobj.getTuioState()) {
						case TuioObject.TUIO_REMOVED:
							TuioObject removeObject = tobj;
							removeObject.remove(currentTime);
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.removeTuioObject(removeObject);
							}								
							objectList.remove(removeObject.getSessionID());
							break;

						case TuioObject.TUIO_ADDED:
							TuioObject addObject = new TuioObject(currentTime,tobj.getSessionID(),tobj.getSymbolID(),tobj.getX(),tobj.getY(),tobj.getAngle());
							objectList.put(addObject.getSessionID(),addObject);
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.addTuioObject(addObject);
							}
							break;
															
						default:
							TuioObject updateObject = objectList.get(tobj.getSessionID());
							if ( (tobj.getX()!=updateObject.getX() && tobj.getXSpeed()==0) || (tobj.getY()!=updateObject.getY() && tobj.getYSpeed()==0) )
								updateObject.update(currentTime,tobj.getX(),tobj.getY(),tobj.getAngle());
							else
								updateObject.update(currentTime,tobj.getX(),tobj.getY(),tobj.getAngle(),tobj.getXSpeed(),tobj.getYSpeed(),tobj.getRotationSpeed(),tobj.getMotionAccel(),tobj.getRotationAccel());

							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.updateTuioObject(updateObject);
							}
					}
				}
				
				for (int i=0;i<listenerList.size();i++) {
					TuioListener listener = (TuioListener)listenerList.elementAt(i);
					if (listener!=null) listener.refresh(new TuioTime(currentTime));
				}
				
				Vector<Long> buffer = aliveObjectList;
				aliveObjectList = newObjectList;
				// recycling the vector
				newObjectList = buffer;					
			}
			frameObjects.clear();
		}
	}
}
