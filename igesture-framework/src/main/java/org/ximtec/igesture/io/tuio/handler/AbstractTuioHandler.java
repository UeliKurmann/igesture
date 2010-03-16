package org.ximtec.igesture.io.tuio.handler;

import java.util.Vector;

import org.ximtec.igesture.io.tuio.TuioListener;
import org.ximtec.igesture.io.tuio.TuioTime;

import com.illposed.osc.OSCMessage;

public abstract class AbstractTuioHandler {
	
	protected long currentFrame = 0;
	/** List of listeners */
	protected Vector<TuioListener> listenerList = new Vector<TuioListener>();
	
	/**
	 * Handle a TUIO message
	 * 
	 * @param message	The message to process.
	 * @param currentTime
	 */
	abstract public void acceptMessage(OSCMessage message, TuioTime currentTime);

	/**
	 * Adds the provided TuioListener to the list of registered TUIO event listeners
	 *
	 * @param  listener  the TuioListener to add
	 */
	public void addTuioListener(TuioListener listener)
	{
		listenerList.addElement(listener);
	}
	
	/**
	 * Removes the provided TuioListener from the list of registered TUIO event listeners
	 *
	 * @param  listener  the TuioListener to remove
	 */
	public void removeTuioListener(TuioListener listener)
	{
		listenerList.removeElement(listener);	
	}
	
	/**
	 * Removes all TuioListener from the list of registered TUIO event listeners
	 */
	public void removeAllTuioListeners()
	{
		listenerList.clear();
	}

}
