package org.ximtec.igesture.util;

//import java.util.logging.Logger;

import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.Gesture;

public class GestureSample3D extends DefaultDataObject implements Cloneable,
Gesture<RecordedGesture3D> {

	//private static final Logger LOGGER = Logger.getLogger(GestureSample.class.getName());

	public static final String PROPERTY_NAME = "name";

	public static final String PROPERTY_GESTURE = "gesture";

	private RecordedGesture3D gesture;

	private String name;
	
   /**
	* Constructs a new GestureSample3D.
	* 
	* @param name the name of the gesture sample.
	* @param note the note the sample note.
	*/
	public GestureSample3D(String name, RecordedGesture3D gesture) {
		super();
		setName(name);
		setGesture(gesture);
	}	
	
	
   /**
	* Sets the sample name.
	* 
	* @param name the name to be set.
	*/
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
	}
	
	/** Returns the sample name
	 * 
	 */
	public String getName() {
		return name;
	}	
	
	
   /**
    * Sets the sample gesture.
	* 
	* @param gesture the gesture to be set.
	*/
	public void setGesture(RecordedGesture3D gesture) {
		RecordedGesture3D oldValue = this.gesture;
		this.gesture = gesture;
		propertyChangeSupport.firePropertyChange(PROPERTY_GESTURE, oldValue, gesture);
	}
	
	
   /** Returns the gesture sample.
	* 
	*/
	public RecordedGesture3D getGesture() {
		return this.gesture;
	}

}
