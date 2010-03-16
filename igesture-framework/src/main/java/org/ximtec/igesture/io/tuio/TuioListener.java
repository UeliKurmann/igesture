package org.ximtec.igesture.io.tuio;

import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject;

/**
 * The TuioListener interface provides a simple callback infrastructure which is used by the {@link TuioProcessor} class 
 * to dispatch TUIO events to all registered instances of classes that implement the TuioListener interface defined here.<P> 
 * Any class that implements the TuioListener interface is required to implement all of the callback methods defined here.
 *
 * @author Martin Kaltenbrunner, Bjorn Puype
 * @version 1.4
 */
public interface TuioListener{
	
	/**
	 * This callback method is invoked by the TuioClient when a new TuioObject is added to the session.   
	 *
	 * @param  tobj  the TuioObject reference associated to the addTuioObject event
	 */
	public void addTuioObject(AbstractTuioObject tobj);

	/**
	 * This callback method is invoked by the TuioClient when an existing TuioObject is updated during the session.   
	 *
	 * @param  tobj  the TuioObject reference associated to the updateTuioObject event
	 */
	public void updateTuioObject(AbstractTuioObject tobj);

	/**
	 * This callback method is invoked by the TuioClient when an existing TuioObject is removed from the session.   
	 *
	 * @param  tobj  the TuioObject reference associated to the removeTuioObject event
	 */
	public void removeTuioObject(AbstractTuioObject tobj);
	
	/**
	 * This callback method is invoked by the TuioClient when a new TuioCursor is added to the session.   
	 *
	 * @param  tcur  the TuioCursor reference associated to the addTuioCursor event
	 */
	public void addTuioCursor(AbstractTuioCursor tcur);

	/**
	 * This callback method is invoked by the TuioClient when an existing TuioCursor is updated during the session.   
	 *
	 * @param  tcur  the TuioCursor reference associated to the updateTuioCursor event
	 */
	public void updateTuioCursor(AbstractTuioCursor tcur);

	/**
	 * This callback method is invoked by the TuioClient when an existing TuioCursor is removed from the session.   
	 *
	 * @param  tcur  the TuioCursor reference associated to the removeTuioCursor event
	 */
	public void removeTuioCursor(AbstractTuioCursor tcur);
	
	/**
	 * This callback method is invoked by the TuioClient to mark the end of a received TUIO message bundle.   
	 *
	 * @param  ftime  the TuioTime associated to the current TUIO message bundle
	 */
	public void refresh(TuioTime ftime);
}
