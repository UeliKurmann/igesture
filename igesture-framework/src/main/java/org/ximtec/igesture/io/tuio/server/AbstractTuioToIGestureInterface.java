package org.ximtec.igesture.io.tuio.server;

/** Extend this class to easily create gestures from tuio objects */
public abstract class AbstractTuioToIGestureInterface implements TuioToIGestureInterface{

	private static final long serialVersionUID = 1L;
	
	/** Is the gesture trigger active */
	private boolean gestureTrigger = false;
	/** Is the application used to only send the gesture data of objects */
	private boolean sendGesturesOnly = false;
	
	/**
	 * @return the gestureTrigger
	 */
	public boolean isGestureTriggered() {
		return gestureTrigger;
	}
	/**
	 * @param gestureTrigger the gestureTrigger to set
	 */
	public void setGestureTrigger(boolean gestureTrigger) {
		this.gestureTrigger = gestureTrigger;
		if(this.gestureTrigger && sendGesturesOnly) //when activating the trigger
			sendFakeAdd();
		else if(!this.gestureTrigger && sendGesturesOnly) //when deactivating the trigger
			sendFakeRemove();
	}
	/**
	 * @return the sendGesturesOnly
	 */
	public boolean isSendGesturesOnly() {
		return sendGesturesOnly;
	}
	/**
	 * @param sendGesturesOnly the sendGesturesOnly to set
	 */
	public void setSendGesturesOnly(boolean sendGesturesOnly) {
		this.sendGesturesOnly = sendGesturesOnly;
	}
	

}
