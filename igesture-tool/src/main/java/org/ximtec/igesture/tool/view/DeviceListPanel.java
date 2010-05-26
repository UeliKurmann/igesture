/**
 * 
 */
package org.ximtec.igesture.tool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.AbstractGestureDevice;

/**
 * @author Björn Puypepuype@gmail.com
 *
 */
public class DeviceListPanel extends JPanel implements ListSelectionListener{

	private List<DeviceListPanelListener> listeners;
	
	private JList list = null;
	
	public DeviceListPanel()
	{
		
		listeners = new ArrayList<DeviceListPanelListener>();
		
		//layout
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		//label
		add(new JLabel("Choose a capture device:"),BorderLayout.NORTH);
		
		//device list
		list = new JList();
		DefaultListModel model = new DefaultListModel();
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		
		list.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		JScrollPane scrollpane = new JScrollPane(list);
		add(scrollpane,BorderLayout.CENTER);
	}
	
	public void addDevice(AbstractGestureDevice<?,?> device)
	{
		//get current selected item
		int index = list.getSelectedIndex();
		Object d = null;
		if(index > 1)
			d = getCastedModel().getElementAt(index);
		//add new device
		getCastedModel().addElement(device);
		//new index
		if(index > -1)
		{
			index = getCastedModel().indexOf(d);
			list.setSelectedIndex(index);
		}
	}
	
	public void removeDevice(AbstractGestureDevice<?,?> device)
	{
		//get current selected item
		int index = list.getSelectedIndex();
		Object d = null;
		if(index > -1)
			d = getCastedModel().getElementAt(index);
		//remove device
		getCastedModel().removeElement(device);
		//update selected index
		if(d == device)//if current selected device is being removed
		{
			index = -1;//clear selection
		}
		else //adjust index
		{
			index = getCastedModel().indexOf(d);
		}
		list.setSelectedIndex(index);
	}
	
	private DefaultListModel getCastedModel()
	{
		return (DefaultListModel) list.getModel();
	}
	
	public void addDevicePanelListener(DeviceListPanelListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeDevicePanelListener(DeviceListPanelListener listener)
	{
		listeners.remove(listener);
	}
	
	public void removeAllDevicePanelListener()
	{
		listeners.clear();
	}
	
	private void notifyDevicePanelListener(AbstractGestureDevice<?,?> device)
	{
		for(DeviceListPanelListener listener : listeners)
		{
			listener.updateDeviceListPanelListener(device);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting() && list.getSelectedIndex() > -1)
		{
			list.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			DefaultListModel model = (DefaultListModel) list.getModel();
			AbstractGestureDevice<?,?> device = (AbstractGestureDevice<?,?>)model.getElementAt(list.getSelectedIndex());
			notifyDevicePanelListener(device);
		}		
	}

}

