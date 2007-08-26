package org.ximtec.igesture.util;

import java.lang.reflect.Field;

import org.ximtec.igesture.core.DataObject;

public class ReflectTools {
	
	public static <T extends DataObject> Object getFieldValue(T dataObject, String fieldName){
		Object object = null;
		
		try {	
			Field field = dataObject.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			object = field.get(dataObject);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}

}
