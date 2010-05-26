package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.view.devicemanager.BlueToothReader;
import org.ximtec.igesture.util.XMLParser;

/**
 * This class converts a found Bluetooth device to a {@link org.ximtec.igesture.io.AbstractGestureDevice}
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class BluetoothDeviceConverter {
	
	private static final Logger LOGGER = Logger.getLogger(BluetoothDeviceDiscoveryService.class.getName());
	
	/**
	 * Mapping between the device numbers and the appropriate {@link org.ximtec.igesture.io.AbstractGestureDevice} to instantiate.
	 */
	private final Map<BTDeviceClass,Class<?>> map;
	private XMLParser parser;
	
	public BluetoothDeviceConverter()
	{
		//load the mapping between bluetooth devices and their classes
		
		map = new HashMap<BTDeviceClass, Class<?>>();
		//create a parser
		parser = new XMLParser(){

			@Override
			public void execute(ArrayList<NodeList> nodeLists) {
				
				try {
					// read the device numbers
					int minor = Integer.parseInt(((Node)nodeLists.get(0).item(0)).getNodeValue());
					int major = Integer.parseInt(((Node)nodeLists.get(1).item(0)).getNodeValue());
					// read the friendly name
					String deviceName = ((Node)nodeLists.get(2).item(0)).getNodeValue();
					// read the class name
					String className = ((Node)nodeLists.get(3).item(0)).getNodeValue();
					// load the class
					Class<?> c = Class.forName(className);
					// put it in the map
					map.put(new BTDeviceClass(minor,major,deviceName), c);
					
					LOGGER.log(Level.INFO,"Added new registered BlueTooth device type: "+deviceName);
					
				} catch (ClassNotFoundException e) {
					LOGGER.log(Level.WARNING, "Device Class not found. The corresponding BlueTooth device type was not added.",e);
				} catch (NumberFormatException e) {
					LOGGER.log(Level.WARNING, "One or more of the Device Class numbers is not a number. The corresponding BlueTooth device type was not added.",e);
				} catch (NullPointerException e) {
					LOGGER.log(Level.WARNING, "An empty node was encountered. The corresponding BlueTooth device type was not added.",e);
				}
			}
			
		};
		// the text nodes to read
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("minor");
		nodes.add("major");
		nodes.add("name");
		nodes.add("class");
		//parse the XML file
		try {
			parser.parse(System.getProperty("user.dir")+System.getProperty("file.separator")+GestureConstants.XML_BLUETOOTH, "device", nodes);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not find "+GestureConstants.XML_BLUETOOTH+". Falling back to default BlueTooth devices.",e);//TODO
		} catch (Exception e){
			LOGGER.log(Level.SEVERE,"Could not parse "+GestureConstants.XML_BLUETOOTH+". Falling back to default BlueTooth devices.",e);//TODO
		}
	}

	/**
	 * Create a {@link org.ximtec.igesture.io.AbstractGestureDevice} from a found BlueTooth device.
	 * @param device	The found device.
	 * @param clazz		The device class (+ numbers)
	 * @return	The created device.
	 */
	public AbstractGestureDevice<?,?> createDevice(RemoteDevice device, DeviceClass clazz)
	{
		boolean registeredDeviceTypeFound = false;
		Object obj = null;
		
		int major = clazz.getMajorDeviceClass();
		int minor = clazz.getMinorDeviceClass();
		String friendlyName = null;
		try {
			friendlyName = device.getFriendlyName(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		for(BTDeviceClass btdc : map.keySet())//registered bluetooth devices types
		{
			//if device class numbers match
			//or
			//if uncategorized device, if friendly name matches
			if((btdc.getMajor() == major && btdc.getMinor() == minor)
					||
				(btdc.getMajor() == 0 && btdc.getMinor() == 0 && btdc.getFriendlyName().equals(friendlyName))
			)
			{
				// get the class
				Class<?> cl = map.get(btdc);
				// instantiate it
				try {
					Constructor<?> constructor = cl.getConstructor(new Class<?>[]{String.class, String.class, RemoteDevice.class});
					obj = constructor.newInstance(new Object[]{device.getBluetoothAddress(),device.getFriendlyName(false),device});
					
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				registeredDeviceTypeFound = true;
				break;
			}
		}
		if(!registeredDeviceTypeFound)//random bluetooth device
		{
			Class<?> cl = BlueToothReader.class;
			try {
				Constructor<?> constructor = cl.getConstructor(new Class<?>[]{String.class, String.class, RemoteDevice.class});
				obj = constructor.newInstance(new Object[]{device.getBluetoothAddress(),device.getFriendlyName(false),device});
				
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return (AbstractGestureDevice<?, ?>) obj;
	}
	
	/**
	 * This class is an encapsulation of the device class numbers of a particular type of device and a friendly name.
	 * @author Bj�n�ype, bpuype@gmail.com
	 *
	 */
	class BTDeviceClass
	{
		private int major; //e.g. computer, imaging, audio/video, peripheral, phone
		private int minor; //e.g. computer: laptop, desktop, ...
		private String deviceName;
		
		public BTDeviceClass(int minor, int major, String deviceName)
		{
			this.major = major;
			this.minor = minor;
			this.deviceName = deviceName;
		}
		
		public int getMinor(){ return minor; }
		public int getMajor(){ return major; }
		public String getFriendlyName() { return deviceName; }
	}
}
