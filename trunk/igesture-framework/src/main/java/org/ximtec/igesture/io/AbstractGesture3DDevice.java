/**
 * 
 */
package org.ximtec.igesture.io;

import java.util.HashSet;
import java.util.Set;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public abstract class AbstractGesture3DDevice implements Gesture3DDevice<RecordedGesture3D, Point3D> {

	   private Set<GestureEventListener> gestureListeners;
	   
	   private String connectionType;
	   private String deviceType;
	   private String id;
	   private String name;
	   private boolean isDefault = false;
	   private boolean connected = false;
	   
	   public AbstractGesture3DDevice(){
	      gestureListeners = new HashSet<GestureEventListener>();
	   }
	   
	   /*
	    * (non-Javadoc)
	    * @see org.ximtec.igesture.io.GestureDevice#removeGestureHandle(org.ximtec.igesture.io.GestureEventListener)
	    */
	   @Override
	   public void removeGestureHandler(GestureEventListener listener) {
	      gestureListeners.remove(listener);
	   }


	   /*
	    * (non-Javadoc)
	    * @see org.ximtec.igesture.io.GestureDevice#addGestureHandler(org.ximtec.igesture.io.GestureEventListener)
	    */
	   @Override
	   public void addGestureHandler(GestureEventListener listener) {
	      gestureListeners.add(listener);
	   }
	   
	   /**
	    * Fires a Gesture Event
	    * @param gesture
	    */
	   protected void fireGestureEvent(Gesture<?> gesture){
	      for(GestureEventListener listener:gestureListeners){
	         listener.handleGesture(gesture);
	      }
	   }
	   
	   /**
	    * Removes all Gesture listeners
	    */
	   protected void removeAllListener(){
	      gestureListeners.clear();
	   }
	   
	   	@Override
		public String getDeviceID() 
		{
			return id;
		}
		
	   	@Override
	   	public void setDeviceID(String id)
	   	{
	   		this.id = id;
	   	}
	   	
	   	@Override
		public boolean hasUniqueDeviceID()
	   	{
	   		return true;
	   	}
		
	   	@Override
		public String getName()
	   	{
	   		return name;
	   	}
	   	
	   	@Override
		public void setName(String name) 
	   	{
	   		this.name = name;
		}
		
	   	@Override
		public abstract void disconnect();
	   	@Override
		public abstract void connect();
	   	
	   	@Override
		public boolean isConnectable() {
			return true;
		}
	   	
	   	@Override
		public boolean isDisconnectable() {
			return true;
		}
	   	
	   	@Override
		public boolean isConnected() {
			return connected;
		}
	   	@Override
		public void setIsConnected(boolean isConnected) 
	   	{
	   		connected = isConnected;
		}
		
	   	@Override
	   	public String getDeviceType() {
			return deviceType;
		}
	   	@Override
		public void setDeviceType(String deviceType) {
	   		this.deviceType = deviceType;
		}
		
	   	@Override
	   	public String getConnectionType() {
			return connectionType;
		}
	   	@Override
		public void setConnectionType(String connectionType) {
	   		this.connectionType = connectionType;
		}
		
	   	@Override
		public boolean isDefaultDevice() {
			return isDefault;
		}
	   	@Override
		public void setDefaultDevice(boolean isDefault) {
	   		this.isDefault = isDefault;
		}	
		
		public String toString() {
			return getName() + " ("+getDeviceID()+")";
		}

}
