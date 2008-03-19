package org.ximtec.igesture.tool.explorer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.apache.commons.beanutils.BeanUtils;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeView;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class NodeInfoImpl implements NodeInfo {

	private String propertyName;
	private String childList;
	private Class<? extends ExplorerTreeView> view;

	private Class<?> type;

	public NodeInfoImpl(Class<?> type, String propertyName, String childList, Class<? extends ExplorerTreeView> view) {
		this.type = type;
		this.propertyName = propertyName;
		this.childList = childList;
		this.view = view;
	}

	@Override
	public List<Object> getChildren(Object node) {
		List<Object> result = new ArrayList<Object>();
		if (childList != null) {
			for (String name : childList.split(";")) {

				try {
					Field field = type.getDeclaredField(name);
					field.setAccessible(true);
					Object o = field.get(node);
					if (o instanceof Collection) {
						result.addAll((Collection<?>) o);
					} else if (o instanceof Map) {
						result.addAll(((Map<?,?>) o).values());
					} else {
						result.add(o);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Object node) {
		if (propertyName != null) {
			try {
				return BeanUtils.getProperty(node, propertyName);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return type.getName();
	}

	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getType() {
		return type;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ExplorerTreeView getView(Object node) {
		
		try {
			Constructor<? extends ExplorerTreeView> ctor = view.getConstructor(type);
			return ctor.newInstance(node);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
