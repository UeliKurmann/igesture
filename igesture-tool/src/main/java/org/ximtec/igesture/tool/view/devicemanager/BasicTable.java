package org.ximtec.igesture.tool.view.devicemanager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * This class represents a table containing objects of type T.
 *  
 * @author Björn Puype, bpuype@gmail.com
 *
 * @param <T>	kind of object the table contains
 */
public class BasicTable<T> extends JTable {

	//instead of keeping the original object in the map, when getting the object it could also be created with a constructor
	//this way the table needs less space, but it imposes extra constraints and difficulties on that constructor (order of passing)
	
	private ArrayList<T> mapping;
	
	/**
	 * Constructor
	 * @param headers	The column names, they should match with the field names of T.
	 */
	public BasicTable(String[] headers)
	{
		super();
		mapping = new ArrayList<T>();
		//create a model and add the headers to it
		DefaultTableModel tableModel= new BasicTableModel();
		for(String s : headers)
			tableModel.addColumn(s);
		//set this model as the table's model
		setModel(tableModel);
	}
	
	/**
	 * Add an item to the table
	 * @param item	The item to add.
	 */
	public void addItem(T item)
	{
		DefaultTableModel model = (DefaultTableModel)getModel();
		// get all the data corresponding with the headers
		Object[] data = new Object[model.getColumnCount()];
		for(int i = 0; i < model.getColumnCount(); i++)
		{
			String name = model.getColumnName(i);
			Object obj = null;
			try {
				Method method = item.getClass().getMethod("get"+name);
				obj = method.invoke(item);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			data[i] = obj;
		}
		//put the item in the mapping and add it to the model
		mapping.add(getRowCount(),item);
		model.insertRow(getRowCount(), data);
		
	}
	
	/**
	 * Get the currently selected item.
	 * @return	The selected item. Returns null if nothing is selected.
	 */
	public T getSelectedItem()
	{
		int i = getSelectedRow();
		if(i == -1)
			return null;
		else
			return mapping.get(i);
	}
	
	/**
	 * Remove the currently selected item.
	 * @return The item that is about to be removed. Returns null if nothing was selected.
	 */
	public T removeItem()
	{
		T item = getSelectedItem();
		if(item != null)
		{
			DefaultTableModel model = (DefaultTableModel)getModel();
			
			ArrayList<Integer> list = new ArrayList<Integer>();
			int i = getSelectedRow();
			//remove from mapping
			mapping.remove(i);
			//adjust the mapping so indexes will correspond with new row numbers
			mapping.trimToSize();
			//remove from model
			model.removeRow(i);
		}
		return item;
	}
	
	/**
	 * Update item in table.
	 * @param value	The new value.
	 * @param col	The column index in the table.
	 * @param item	The item to update, null if you do not want to update an item itself, only the table.
	 */
	public void updateItem(Object value, int col, T item)
	{
		int row = 0;
		if(item == null)
		{
			// update selected item in table model only
			row = getSelectedRow();
		}
		else // update item itself
			row = indexOf(item);
		
		if(row != -1)
		{
			// update table
			DefaultTableModel model = (DefaultTableModel) getModel();
			model.setValueAt(value, row, col);
			
			// update item that was passed
			if(item != null)
			{
				String name = getModel().getColumnName(col);
				
				try {
		//			Class[] args = new Class[]{String.class};//constraint: parametertype should be a String
		//			Method method = item.getClass().getMethod("set"+name,args);
		//			Object[] argv = new Object[]{value};
		//			method.invoke(item,argv);
					Field field = item.getClass().getDeclaredField(name.toLowerCase());
					field.setAccessible(true);
					field.set(item, value);
					
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
		//		} catch (InvocationTargetException e) {
		//			e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Remove all items from the table.
	 */
	public void removeAllItems()
	{
		//Clear the model.
		DefaultTableModel model = (DefaultTableModel)getModel();
		int rows = model.getRowCount();
		for(int row = 0; row < rows; row++)
		{
			model.removeRow(0);
		}
		//Clear the mapping
		mapping.clear();
	}
	
	/**
	 * Add multiple items to the table.
	 * @param items	The items to add.
	 */
	public void addItems(Set<T> items)
	{
		for(T item : items)
		{
			addItem(item);
		}
	}
	
	/**
	 * Get all items in the table.
	 * @return	A Collection of the items in the table.
	 */
	public Collection<T> getItems()
	{
		return mapping;
	}
	
	/**
	 * Get the index of the item.
	 * @param o	The item.
	 * @return	The index of the item. Returns -1 if not in the table.
	 */
	private int indexOf(Object o)
	{
		return mapping.indexOf(o);
	}
	
	/**
	 * Add a selection listener to the table.
	 * @param l	The listener.
	 */
	public void addListSelectionListener(ListSelectionListener l)
	{
		getSelectionModel().addListSelectionListener(l);
	}
	
	/**
	 * This class represents the model of the table.
	 * @author Björn Puype, bpuype@gmail.com
	 *
	 */
	class BasicTableModel extends DefaultTableModel
	{
		//make the table uneditable
		@Override
		public boolean isCellEditable(int row, int col)
        { 
			return false; 
		}

		//use checkboxes to render booleans
		@Override
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

	}
}
