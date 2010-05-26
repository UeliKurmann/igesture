package org.ximtec.igesture.io.tuio.server;

/**
 * 
 * @author Björn Puypepuype@gmail.com
 *
 */
public interface TuioToIGestureInterface {
	
	/** 
	 * When only gesture data of the objects is sent to the tuio client, the client must be informed of all 
	 * objects that are present at the beginning of the gesture. This way the client will know the begin position 
	 * of the gesture.
	 */
	public abstract void sendVirtualAdd();
	/**
	 * When only gesture data of the objects is sent to the tuio client, the client must be informed of the removal
	 * of all present objects. This way the client will know the end postion of the gesture.
	 */
	public abstract void sendVirtualRemove();

}
