package org.ximtec.igesture.io.tuio.tuio3D.handler;

import java.util.Enumeration;
import java.util.Vector;

import org.ximtec.igesture.io.tuio.TuioListener;
import org.ximtec.igesture.io.tuio.TuioTime;
import org.ximtec.igesture.io.tuio.handler.AbstractTuioObjectHandler;
import org.ximtec.igesture.io.tuio.tuio3D.TuioObject3D;


import com.illposed.osc.OSCMessage;

/**
 * Handles /tuio/3Dobj messages.
 * @author bjorn
 *
 */
public class TuioObject3DHandler extends AbstractTuioObjectHandler<TuioObject3D> {
	
	/** Constructor */
	public TuioObject3DHandler()
	{
		
	}

	@Override
	public void acceptMessage(OSCMessage message, TuioTime currentTime) {
		Object[] args = message.getArguments();
		String command = (String)args[0];
		
		if (command.equals("set")) {
			
			long s_id  = ((Integer)args[1]).longValue();
			int c_id  = ((Integer)args[2]).intValue();
			float xpos = ((Float)args[3]).floatValue();
			float ypos = ((Float)args[4]).floatValue();
			float zpos = ((Float)args[5]).floatValue();
			float angleXY = ((Float)args[6]).floatValue();
			float angleXZ = ((Float)args[7]).floatValue();
			float angleYZ = ((Float)args[8]).floatValue();
			float xspeed = ((Float)args[9]).floatValue();
			float yspeed = ((Float)args[10]).floatValue();
			float zspeed = ((Float)args[11]).floatValue();
			float rspeedXY = ((Float)args[12]).floatValue();
			float rspeedXZ = ((Float)args[13]).floatValue();
			float rspeedYZ = ((Float)args[14]).floatValue();
			float maccel = ((Float)args[15]).floatValue();
			float raccel = ((Float)args[16]).floatValue();
			
			if (objectList.get(s_id) == null) {
			
				TuioObject3D addObject = new TuioObject3D(s_id,c_id,xpos,ypos,zpos,angleXY,angleXZ,angleYZ);
				frameObjects.addElement(addObject);
				
			} else {
			
				TuioObject3D tobj = objectList.get(s_id);
				if (tobj==null) return;
				if ((tobj.getX()!=xpos) || (tobj.getY()!=ypos) || (tobj.getZ()!=zpos) || (tobj.getAngleXY()!=angleXY) || (tobj.getAngleXZ()!=angleXZ) || (tobj.getAngleYZ()!=angleYZ) || (tobj.getXSpeed()!=xspeed) || (tobj.getYSpeed()!=yspeed) || (tobj.getZSpeed()!=zspeed) || (tobj.getRotationSpeedXY()!=rspeedXY) || (tobj.getRotationSpeedXZ()!=rspeedXZ) || (tobj.getRotationSpeedYZ()!=rspeedYZ) || (tobj.getMotionAccel()!=maccel) || (tobj.getRotationAccel()!=raccel)) {
					
					TuioObject3D updateObject = new TuioObject3D(s_id,c_id,xpos,ypos,zpos,angleXY,angleXZ,angleYZ);
					updateObject.update(xpos,ypos,zpos,angleXY,angleXZ,angleYZ,xspeed,yspeed,zspeed,rspeedXY,rspeedXZ,rspeedYZ,maccel,raccel);
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
				TuioObject3D removeObject = objectList.get(aliveObjectList.elementAt(i));
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
				Enumeration<TuioObject3D> frameEnum = frameObjects.elements();
				while(frameEnum.hasMoreElements()) {
					TuioObject3D tobj = frameEnum.nextElement();
					
					switch (tobj.getTuioState()) {
						case TuioObject3D.TUIO_REMOVED:
							TuioObject3D removeObject = tobj;
							removeObject.remove(currentTime);
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.removeTuioObject(removeObject);
							}								
							objectList.remove(removeObject.getSessionID());
							break;

						case TuioObject3D.TUIO_ADDED:
							TuioObject3D addObject = new TuioObject3D(currentTime,tobj.getSessionID(),tobj.getSymbolID(),tobj.getX(),tobj.getY(),tobj.getZ(),tobj.getAngle(TuioObject3D.XY),tobj.getAngle(TuioObject3D.XZ),tobj.getAngle(TuioObject3D.YZ));
							objectList.put(addObject.getSessionID(),addObject);
							for (int i=0;i<listenerList.size();i++) {
								TuioListener listener = (TuioListener)listenerList.elementAt(i);
								if (listener!=null) listener.addTuioObject(addObject);
							}
							break;
															
						default:
							TuioObject3D updateObject = objectList.get(tobj.getSessionID());
							if ( (tobj.getX()!=updateObject.getX() && tobj.getXSpeed()==0) || (tobj.getY()!=updateObject.getY() && tobj.getYSpeed()==0) || (tobj.getZ()!=updateObject.getZ() && tobj.getZSpeed()==0) )
								updateObject.update(currentTime,tobj.getX(),tobj.getY(),tobj.getZ(),tobj.getAngle(TuioObject3D.XY),tobj.getAngle(TuioObject3D.XZ),tobj.getAngle(TuioObject3D.YZ));
							else
								updateObject.update(currentTime,tobj.getX(),tobj.getY(),tobj.getZ(),tobj.getAngle(TuioObject3D.XY),tobj.getAngle(TuioObject3D.XZ),tobj.getAngle(TuioObject3D.YZ),tobj.getXSpeed(),tobj.getYSpeed(),tobj.getZSpeed(),tobj.getRotationSpeedXY(),tobj.getRotationSpeedXZ(),tobj.getRotationSpeedYZ(),tobj.getMotionAccel(),tobj.getRotationAccel());

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
