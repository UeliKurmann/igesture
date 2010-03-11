package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.view.devicemanager.XMLParser;
import org.ximtec.igesture.tool.view.devicemanager.temp.BlueToothReader;

/**
 * This class converts a found BlueTooth device to a {@link org.ximtec.igesture.io.AbstractGestureDevice}
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class BlueToothDeviceConverter {
	
	/**
	 * Mapping between the device numbers and the appropriate {@link org.ximtec.igesture.io.AbstractGestureDevice} to instantiate.
	 */
	private final Map<BTDeviceClass,Class<?>> map;
	private XMLParser parser;
	
	public BlueToothDeviceConverter()
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
					// read the class name
					String className = ((Node)nodeLists.get(2).item(0)).getNodeValue();
					// load the class
					Class<?> c = Class.forName(className);
					// put it in the map
					map.put(new BTDeviceClass(minor,major), c);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			
		};
		// the text nodes to read
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("minor");
		nodes.add("major");
		nodes.add("class");
		//parse the XML file
		try {
			parser.parse(System.getProperty("user.dir")+System.getProperty("file.separator")+GestureConstants.XML_BLUETOOTH, "device", nodes);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not parse bluetoothdevices.xml");//TODO
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
		
		for(BTDeviceClass btdc : map.keySet())//registered bluetooth devices types
		{
			//if device class numbers match
			if(btdc.getMajor() == clazz.getMajorDeviceClass() && btdc.getMinor() == clazz.getMinorDeviceClass())
			{
				// get the class
				Class<?> cl = map.get(btdc);
				// instantiate it
				try {
					Constructor<?> constructor = cl.getConstructor(new Class<?>[]{String.class, String.class});
					obj = constructor.newInstance(new Object[]{device.getBluetoothAddress(),device.getFriendlyName(false)});
					
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
				Constructor<?> constructor = cl.getConstructor(new Class<?>[]{String.class, String.class});
				obj = constructor.newInstance(new Object[]{device.getBluetoothAddress(),device.getFriendlyName(false)});
				
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
	 * This class is an encapsulation of the device class numbers of a particular type of device.
	 * @author Björn Puype, bpuype@gmail.com
	 *
	 */
	class BTDeviceClass
	{
		private int major;
		private int minor;
		
		public BTDeviceClass(int minor, int major)
		{
			this.major = major;
			this.minor = minor;
		}
		
		public int getMinor(){ return minor; }
		public int getMajor(){ return major; }
	}
}
