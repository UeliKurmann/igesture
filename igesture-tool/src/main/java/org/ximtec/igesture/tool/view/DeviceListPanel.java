/**
 * 
 */
package org.ximtec.igesture.tool.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerListener;
import org.ximtec.igesture.tool.view.devicemanager.IDeviceManager;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class DeviceListPanel extends JPanel implements /*DeviceManagerListener,*/ ListSelectionListener{

	private IDeviceManager deviceManagerController;
	
	private List<DeviceListPanelListener> listeners;
	
	private JList list = null;
	
	public DeviceListPanel(/*IDeviceManager deviceManager*/)
	{
//		this.deviceManagerController = deviceManager;
//		deviceManagerController.addDeviceManagerListener(this);
		
		listeners = new ArrayList<DeviceListPanelListener>();
		
		//layout
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		//label
		add(new JLabel("Choose a capture device:"),BorderLayout.NORTH);
		
		//device list
		list = new JList();
		DefaultListModel model = new DefaultListModel();
//		for(AbstractGestureDevice<?,?> device : deviceManagerController.getDevices())
//			model.addElement(device);
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		
		JScrollPane scrollpane = new JScrollPane(list);
		add(scrollpane,BorderLayout.CENTER);
	}

//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.tool.view.devicemanager.DeviceManagerListener#update()
//	 */
//	@Override
//	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?,?> device) {
//		
//		int index = list.getSelectedIndex();
//		DefaultListModel model = (DefaultListModel)list.getModel();
//		if(operation == DeviceManagerListener.ADD)
//		{
//			model.addElement(device);
//		}
//		else if(operation == DeviceManagerListener.REMOVE)
//		{
//			model.removeElement(device);
//		}
//		if(index > model.getSize())
//			index = 0;
//		list.setSelectedIndex(index);
//
//	}
	
	public void addDevice(AbstractGestureDevice<?,?> device)
	{
		int index = list.getSelectedIndex();
		getCastedModel().addElement(device);
		if(index == -1 || index == 0)//index > getCastedModel().getSize() || 
			index = 0;
		else
			index -= 1;
		list.setSelectedIndex(index);
	}
	
	public void removeDevice(AbstractGestureDevice<?,?> device)
	{
		int index = list.getSelectedIndex();
		getCastedModel().removeElement(device);
		if(index == -1 || index == 0)
			index = 0;
		else
			index -= 1;
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
			DefaultListModel model = (DefaultListModel) list.getModel();
			AbstractGestureDevice<?,?> device = (AbstractGestureDevice<?,?>)model.getElementAt(list.getSelectedIndex());
			notifyDevicePanelListener(device);
		}
		
	}
}

