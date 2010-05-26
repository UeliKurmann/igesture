package org.ximtec.igesture.io.tuio.server;

/** 
 * @author Bjorn Puype, bpuype@gmail.com
 * 
 * Extend this class to easily create gestures from tuio objects 
 * 
 */
public abstract class AbstractTuioToIGestureInterface implements TuioToIGestureInterface{

	private static final long serialVersionUID = 1L;
	
	/** Is the gesture trigger active */
	private boolean gestureTrigger = false;
	/** Is the application used in compatibility mode (only send the gesture data of objects) */
	private boolean inCompatibilityMode = false;
	
	/**
	 * Returns true if the gesture trigger is activated.
	 * @return the state of the gesture trigger
	 */
	public boolean isGestureTriggered() {
		return gestureTrigger;
	}
	/**
	 * Activate or deactivate the gesture trigger.
	 * @param gestureTrigger the gestureTrigger to set
	 */
	public void setGestureTrigger(boolean gestureTrigger) {
		this.gestureTrigger = gestureTrigger;
		if(inCompatibilityMode)
		{
			if(this.gestureTrigger) //when activating the trigger
				sendVirtualAdd();
			else //when deactivating the trigger
				sendVirtualRemove();
		}
	}
	/**
	 * Returns true if the compatibility mode is activated.
	 * @return the state of the compatibility mode
	 */
	public boolean isCompatibilityModeActivated() {
		return inCompatibilityMode;
	}
	/**
	 * Activate or deactivate the compatibility mode.
	 * @param compatibilyModeActivated The value to set.
	 */
	public void setCompatibilityModeActivated(boolean compatibilyModeActivated) {
		this.inCompatibilityMode = compatibilyModeActivated;
	}
	

}
