package org.ximtec.igesture.storage;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.util.ReflectTools;

public abstract class DefaultStorageEngine implements StorageEngine {

  /*
   * (non-Javadoc)
   * @see org.ximtec.igesture.storage.StorageEngine#load(java.lang.Class, java.lang.String, java.lang.Object)
   */
  @Override
	public <T extends DataObject> List<T> load(Class<T> clazz, String fieldName, Object value) {
		List<T> result = new ArrayList<T>();
		for (T dataObject : load(clazz)) {
			if (value == null) {
				if (ReflectTools.getFieldValue(dataObject, fieldName) == null) {
					result.add(dataObject);
				}
			} else {
				if (value.equals(ReflectTools.getFieldValue(dataObject,
						fieldName))) {
					result.add(dataObject);
				}
			}
		}
		return result;
	}

}