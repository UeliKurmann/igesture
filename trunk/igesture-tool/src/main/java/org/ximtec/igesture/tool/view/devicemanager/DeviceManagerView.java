package org.ximtec.igesture.tool.view.devicemanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicLabel;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.view.devicemanager.action.AddDeviceAction;
import org.ximtec.igesture.tool.view.devicemanager.action.AddUserAction;
import org.ximtec.igesture.tool.view.devicemanager.action.AssociateUserAction;
import org.ximtec.igesture.tool.view.devicemanager.action.DisconnectDeviceAction;
import org.ximtec.igesture.tool.view.devicemanager.action.LoadDeviceConfigurationAction;
import org.ximtec.igesture.tool.view.devicemanager.action.ReconnectDeviceAction;
import org.ximtec.igesture.tool.view.devicemanager.action.RemoveDeviceAction;
import org.ximtec.igesture.tool.view.devicemanager.action.RemoveUserAction;
import org.ximtec.igesture.tool.view.devicemanager.action.SaveDeviceConfigurationAction;

/**
 * This class is the view for the Device Manager. It implements the {@link org.ximtec.igesture.tool.view.devicemanage.IDeviceManagerView} interface and extends {@link import org.sigtec.graphix.widget.BasicDialog;}.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class DeviceManagerView extends BasicDialog implements IDeviceManagerView
{

	private static final long serialVersionUID = 1L;
	
	//user table
	public static final int COL_USER_NAME = 0;
	public static final int COL_USER_INITIALS = 1;
	//device table
	public static final int COL_DEVICE_NAME = 0;
	public static final int COL_DEVICE_ID = 1;
	public static final int COL_DEVICE_TYPE = 2;
	public static final int COL_DEVICE_USER = 3;
	public static final int COL_DEVICE_CONNECTION = 4;
	public static final int COL_DEVICE_CONNECTED = 5;
	
	private static final Logger LOGGER = Logger.getLogger(DeviceManagerView.class.getName());
	
	private DeviceManagerController deviceManagerController;
	private static final int TABLE_HEIGHT = 200;
	private static final int BUTTON_WIDTH = 200;
	
	private BasicTable<User> userTable;
	private BasicTable<DeviceUserAssociation> deviceTable;

	/**
	 * Constructs a new Device Manager.
	 * 
	 * @param controller
	 * 			  parent controller
	 * @param key
	 *            the key to be used for the lookup of information in the GUI
	 *            bundle.
	 * @param guiBundle
	 *            the GUI bundle to be used to create the about dialog.
	 */
	public DeviceManagerView(DeviceManagerController controller, String key, GuiBundle guiBundle) {
		super(key,guiBundle);
		deviceManagerController = controller;
		initUI(key, guiBundle);
	}
	
	/**
	 * Initialize UI
	 * @param key
	 * @param guiBundle
	 */
	private void initUI(String key, GuiBundle guiBundle) {
		setLayout(new GridBagLayout());

		/********************************* User Panel *******************************/
		/////////////////////// user actions
		
		AddUserAction addUserAction = new AddUserAction(deviceManagerController);
		RemoveUserAction removeUserAction = new RemoveUserAction(deviceManagerController,this);
		
		/////////////////////// user table
		String[] headers = new String[]{GestureConstants.USER_NAME,GestureConstants.USER_INITIALS};
		userTable = new BasicTable<User>(headers);
		userTable.addListSelectionListener(removeUserAction);
		JScrollPane userPane = createScrollableTable(userTable, guiBundle.getWidth(key), TABLE_HEIGHT);
		userPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),"Users:"));
		
		/////////////////////// add components
		add(userPane,0,1,5,0);
		add(createButton(addUserAction,BUTTON_WIDTH),0,2,1,0.5);
		add(createButton(removeUserAction,BUTTON_WIDTH),1,2,1,0.5);
		
		
		/********************************* Device Panel *******************************/
		/////////////////////// device actions
		AddDeviceAction addDeviceAction = new AddDeviceAction(deviceManagerController);
		addDeviceAction.setEnabled(deviceManagerController.getEnableAddDevicesAction());
		AssociateUserAction associateUserAction = new AssociateUserAction(deviceManagerController,this);
		associateUserAction.setEnabled(false);
		RemoveDeviceAction removeDeviceAction = new RemoveDeviceAction(deviceManagerController,this);
		removeDeviceAction.setEnabled(false);		
		ReconnectDeviceAction reconnectDeviceAction = new ReconnectDeviceAction(deviceManagerController,this);
		reconnectDeviceAction.setEnabled(false);
		DisconnectDeviceAction disconnectDeviceAction = new DisconnectDeviceAction(deviceManagerController,this);
		disconnectDeviceAction.setEnabled(false);
		
		reconnectDeviceAction.addAction(disconnectDeviceAction);
		disconnectDeviceAction.addAction(reconnectDeviceAction);
		
		/////////////////////// device table
		headers = new String[]{GestureConstants.DEVICE_NAME, GestureConstants.DEVICE_ID, GestureConstants.DEVICE_DIMENSION,
									GestureConstants.DEVICE_USER, GestureConstants.DEVICE_CONNECTION, GestureConstants.DEVICE_CONNECTED};
		
		deviceTable = new BasicTable<DeviceUserAssociation>(headers);
		deviceTable.addListSelectionListener(associateUserAction);
		deviceTable.addListSelectionListener(removeDeviceAction);
		deviceTable.addListSelectionListener(reconnectDeviceAction);
		deviceTable.addListSelectionListener(disconnectDeviceAction);
		
		JScrollPane devicePane = createScrollableTable(deviceTable, guiBundle.getWidth(key), TABLE_HEIGHT);
		devicePane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),"Devices:"));
		
		/////////////////////// add components
		add(devicePane,0,4,5,0);
		add(createButton(addDeviceAction,BUTTON_WIDTH),0,5,1,0.5);
		add(createButton(associateUserAction,BUTTON_WIDTH),1,5,1,0.5);
		add(createButton(removeDeviceAction,BUTTON_WIDTH),2,5,1,0.5);
		add(createButton(reconnectDeviceAction,BUTTON_WIDTH),3,5,1,0.5);
		add(createButton(disconnectDeviceAction,BUTTON_WIDTH),4,5,1,0.5);
		
		/********************************* Bottom Panel *******************************/
		/////////////////////// actions
		SaveDeviceConfigurationAction saveDeviceConfigurationAction = new SaveDeviceConfigurationAction(deviceManagerController);
		LoadDeviceConfigurationAction loadDeviceConfigurationAction = new LoadDeviceConfigurationAction(deviceManagerController);
		
		add(createButton(loadDeviceConfigurationAction,BUTTON_WIDTH),0,6,1,0.5);
		add(createButton(saveDeviceConfigurationAction,BUTTON_WIDTH),1,6,1,0.5);
		
		//close button
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}			
		});
		add(closeButton,4,6,1,0.5);
		
		pack();
	}
	
	/**
	 * Add component to the GridBagLayout.
	 * @param component	The component to add.
	 * @param gridx		The x position.
	 * @param gridy		The y position.
	 * @param gridWidth	The width of the component in number of columns.
	 */
	private void add(JComponent component, int gridx, int gridy, int gridWidth, double weightx)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridWidth;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = weightx;
		add(component,c);		
	}

	/**
	 * Initialises the dialogue.
	 * 
	 * @param key
	 *            the key to be used for the lookup of dialogue information in
	 *            the GUI bundle.
	 * @param guiBundle
	 *            the GUI bundle to be used to create the dialogue.
	 */
	protected void init(String key, GuiBundle guiBundle)
	{
		LOGGER.log(Level.FINER, "Init Device Manager");
		super.init(key, guiBundle);	
	}
	
	/**
	 * Create a scrollable table.
	 * @param table			The table to add.
	 * @param tableWidth	The desired width.
	 * @param tableHeight	The desired height.
	 * @return	A scrollable table.
	 */
	private JScrollPane createScrollableTable(JTable table, int tableWidth, int tableHeight)
	{
		JScrollPane pane = new JScrollPane(table);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		pane.setPreferredSize(new Dimension(tableWidth,tableHeight));
		return pane;
	}
	
	/**
	 * Create a BasicButton.
	 * @param action	The action that is coupled to the button.
	 * @param width		The width of the button
	 * @return
	 */
	private BasicButton createButton(BasicAction action, int width)
	{
		BasicButton btn = new BasicButton(action);
		Formatter.formatButton(btn);
//		btn.setHorizontalAlignment(SwingConstants.CENTER);
		return btn;		
	}
	
	private void closeDialog() {
		setVisible(false);
//		dispose();
	}
	
	@Override
	public void addUser(User user)
	{
		userTable.addItem(user);
	}
	
	@Override
	public void addDevice(DeviceUserAssociation association)
	{
		deviceTable.addItem(association);
	}
	
	@Override
	public void removeDevice()
	{
		deviceTable.removeItem();
	}
	
	@Override
	public void removeUser()
	{
		userTable.removeItem();
	}
	
	@Override
	public Collection<DeviceUserAssociation> getDevices()
	{
		return deviceTable.getItems();
	}
	
	/**
	 * Update a device-user association.
	 * @param value		The new value.
	 * @param column	The field that should be updated.
	 * @param object	The object that should be updated, when null only the table model is updated.
	 */
	@Override
	public void updateDevice(Object value, int column, DeviceUserAssociation object)
	{
		deviceTable.updateItem(value, column, object);
	}
	
	@Override
	public DeviceUserAssociation getSelectedDevice()
	{
		return deviceTable.getSelectedItem();
	}
	
	@Override
	public User getSelectedUser()
	{
		return userTable.getSelectedItem();
	}
	
	@Override
	public void clear()
	{
		userTable.removeAllItems();
		deviceTable.removeAllItems();
	}
}
