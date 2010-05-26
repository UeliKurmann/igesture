/**
 * 
 */
package org.ximtec.igesture.util;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.TypeMapper;

/**
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class Constant {
	
	private static final Map<Integer, String> toDeviceTypeTextMapping = new HashMap<Integer, String>();
	private static final Map<String, Integer> toDeviceTypeValueMapping = new HashMap<String, Integer>();
	
	private static final Map<Integer, String> toConnectionTypeTextMapping = new HashMap<Integer, String>();
	private static final Map<String, Integer> toConnectionTypeValueMapping = new HashMap<String, Integer>();
	
	public static final String TYPE_2D_TEXT = "2D";
	public static final String TYPE_3D_TEXT = "3D";
	public static final String TYPE_VOICE_TEXT = "voice";
	
	public static final int TYPE_2D = 0;
	public static final int TYPE_3D = 1;
	public static final int TYPE_VOICE = 2;
	
	public static final int CONNECTION_BLUETOOTH = 0;
	public static final int CONNECTION_TUIO = 1;
	public static final int CONNECTION_USB = 2;
	
	public static final String CONNECTION_BLUETOOTH_TEXT = "Bluetooth";
	public static final String CONNECTION_TUIO_TEXT = "TUIO";
	public static final String CONNECTION_USB_TEXT = "USB";
	
	
	static{
		toDeviceTypeTextMapping.put(TYPE_2D, TYPE_2D_TEXT);
		toDeviceTypeTextMapping.put(TYPE_3D, TYPE_3D_TEXT);
		toDeviceTypeTextMapping.put(TYPE_VOICE, TYPE_VOICE_TEXT);
		
		toDeviceTypeValueMapping.put(TYPE_2D_TEXT, TYPE_2D);
		toDeviceTypeValueMapping.put(TYPE_3D_TEXT, TYPE_3D);
		toDeviceTypeValueMapping.put(TYPE_VOICE_TEXT, TYPE_VOICE);
		
		toConnectionTypeTextMapping.put(CONNECTION_BLUETOOTH, CONNECTION_BLUETOOTH_TEXT);
		toConnectionTypeTextMapping.put(CONNECTION_USB, CONNECTION_USB_TEXT);
		toConnectionTypeTextMapping.put(CONNECTION_TUIO, CONNECTION_TUIO_TEXT);
		
		toConnectionTypeValueMapping.put(CONNECTION_BLUETOOTH_TEXT, CONNECTION_BLUETOOTH);
		toConnectionTypeValueMapping.put(CONNECTION_USB_TEXT, CONNECTION_USB);
		toConnectionTypeValueMapping.put(CONNECTION_TUIO_TEXT, CONNECTION_TUIO);
	}
	
	public static String getDeviceTypeName(int type)
	{
		return toDeviceTypeTextMapping.get(type);
	}
	
	public static int getDeviceTypeValue(String type)
	{
		return toDeviceTypeValueMapping.get(type);
	}
	
	public static String getConnectionTypeName(int type)
	{
		return toConnectionTypeTextMapping.get(type);
	}
	
	public static int getConnectionTypeValue(String type)
	{
		return toConnectionTypeValueMapping.get(type);
	}

	public static final String M = "m";
	public static final String CM = "cm"; 
	public static final String MM = "mm";
	public static final String KM = "km";
	
	public static final int MUL_M = 1000;
	public static final int MUL_CM = 10;
	public static final int MUL_KM = 1000000;
}
