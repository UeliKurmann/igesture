package org.ximtec.igesture.tool.view.devicemanager;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.graphix.GuiBundle;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceDiscoveryService;
import org.ximtec.igesture.io.DeviceManagerListener;
import org.ximtec.igesture.io.DeviceUserAssociation;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.io.IUser;
import org.ximtec.igesture.io.User;
import org.ximtec.igesture.storage.IStorageManager;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.util.XMLParser;

/**
 * This class is an implementation of the controller of the Device Manager. It implements the {@link org.ximtec.igesture.io.IDeviceManager} interface.
 * It also extends {@link org.ximtec.igesture.tool.core.DefaultController} to allow easy instantion by the main controller of the workbench.
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class DeviceManagerController extends DefaultController implements IDeviceManager{

	private static final Logger LOGGER = Logger.getLogger(DeviceManagerController.class.getName());
	
	/*
	 * device mapping: one user per device, what about tuio?
	 * */
	
	private Set<IUser> users = new HashSet<IUser>();
	private Set<AbstractGestureDevice<?,?>> devices = new HashSet<AbstractGestureDevice<?,?>>();
	private Map<AbstractGestureDevice<?,?>, IUser> userMapping = new HashMap<AbstractGestureDevice<?,?>,IUser>();
	
	private final Map<String, DeviceDiscoveryService> discoveryMapping = new HashMap<String,DeviceDiscoveryService>();
	private boolean enableAddDevicesAction = true;
	
	/** The system user is the default user and cannot be removed from the device manager */
	private IUser defaultUser;
	private AbstractGestureDevice<?, ?> defaultDevice;
	private DeviceManagerView view;

	private List<DeviceManagerListener> listeners = new ArrayList<DeviceManagerListener>();
	
	/**
	 * Constructor
	 * @param parentController
	 * @param key
	 * @param guiBundle
	 */
	public DeviceManagerController(Controller parentController, String key, GuiBundle guiBundle) {
		super(parentController);
				
		//create the mapping between the connection types and the discovery services
		XMLParser parser = new XMLParser(){

			@Override
			public void execute(ArrayList<NodeList> nodeLists) {
				
				try {
					String connection = ((Node)nodeLists.get(0).item(0)).getNodeValue();
					String className = ((Node)nodeLists.get(1).item(0)).getNodeValue();
					Class<?> c = Class.forName(className);
					
					Constructor<?> constructor = c.getConstructor();
					discoveryMapping.put(connection, (DeviceDiscoveryService)constructor.newInstance());
					
					LOGGER.log(Level.INFO,"Discovery Service Added: "+connection+" Discovery Service");
					
				} catch (ClassNotFoundException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class not found. The corresponding discovery service was not added.",e);
				} catch (SecurityException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class could not be instantiated. The corresponding discovery service was not added.",e);
				} catch (NoSuchMethodException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class could not be instantiated. The corresponding discovery service was not added.",e);
				} catch (IllegalArgumentException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class could not be instantiated. The corresponding discovery service was not added.",e);
				} catch (InstantiationException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class could not be instantiated. The corresponding discovery service was not added.",e);
				} catch (IllegalAccessException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class could not be instantiated. The corresponding discovery service was not added.",e);
				} catch (InvocationTargetException e) {
					LOGGER.log(Level.WARNING, "Discovery Service Class could not be instantiated. The corresponding discovery service was not added.",e);
				} catch (NullPointerException e) {
					LOGGER.log(Level.WARNING, "An empty node was encountered. The corresponding discovery service was not added.",e);
				}
			}
			
		};
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("name");
		nodes.add("discoveryService");
		try {
			parser.parse(System.getProperty("user.dir")+System.getProperty("file.separator")+GestureConstants.XML_DISCOVERY, "connection", nodes);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not find "+GestureConstants.XML_DISCOVERY +". Adding devices will be disabled",e);
			enableAddDevicesAction = false;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"Could not parse "+GestureConstants.XML_DISCOVERY +". Adding devices will be disabled",e);
			enableAddDevicesAction = false;
		}
		
		//instantiate the view
		view = new DeviceManagerView(this, key, guiBundle);
		
		//instantiate the default user/system user
		String userName = System.getProperty("user.name");
		defaultUser = new User(userName,userName.substring(0, 1));
		defaultDevice = (AbstractGestureDevice<?, ?>) parentController.getLocator().getService(SwingMouseReaderService.IDENTIFIER);
		
		//add the default user and the mouse.
		addUser(defaultUser);
		addDevice(defaultDevice,defaultUser);
	}
	
	/**
	 * This method returns true if the add devices action should be enabled
	 * @return
	 */
	public boolean getEnableAddDevicesAction()
	{
		/*
		 * enableAddDevicesAction is false when connections.xml could not be found or parsed
		 * even if the file could be parsed, it is possible that the mentionned discovery classes are incorrect
		 * and no services were added to the mapping
		 * in both cases, adding devices is not possible
		 */
		return (enableAddDevicesAction || !discoveryMapping.isEmpty());
	}

	@Override
	public TabbedView getView() {
		return null;
	}

	@Override
	public void addDevice(AbstractGestureDevice<?,?> device, IUser user) {
		if(!devices.contains(device))
		{
			devices.add(device);
			DeviceUserAssociation ass = new DeviceUserAssociation(device,user);
			if(!userMapping.containsKey(device))
			{
				userMapping.put(device, user);
			}
			view.addDevice(ass);
			notifyDeviceManagerListener(DeviceManagerListener.ADD,device);
			LOGGER.log(Level.INFO,"Device Added: "+ ass);
		}
	}

	@Override
	public void addUser(IUser user) {
		if(!users.contains(user))
		{
			users.add(user);
			view.addUser(user);
			
			LOGGER.log(Level.INFO,"User Added: "+ user);
		}
	}

	@Override
	public IUser getDefaultUser() {
		return defaultUser;
	}

	@Override
	public Set<AbstractGestureDevice<?,?>> getDevices() {
		return devices;
	}

	@Override
	public Set<IUser> getUsers() {
		return users;
	}

	@Override
	public void removeDevice(AbstractGestureDevice<?,?> device) {
		
		
		//TODO als actie luistert naar selecties, kan je hem disablen zodat nooit defaultmouse wordt verwijderd
		if(!device.isDefaultDevice())//no removal of system mouse / default devices
		{
			view.removeDevice();
			devices.remove(device);
			userMapping.remove(device);
			notifyDeviceManagerListener(DeviceManagerListener.REMOVE,device);
			LOGGER.log(Level.INFO, "Device Removed: "+ device);
		}
				
	}

	@Override
	public void removeUser(IUser user) {
		
		
		//TODO als actie luistert naar selecties, kan je hem disablen zodat nooit defaultuser wordt verwijderd
		if(!defaultUser.equals(user))//system user may not be removed
		{
			view.removeUser();
			users.remove(user);
			//reassociate the devices that had this user to the default user
			if(userMapping.containsValue(user))
			{
				//remove user, update bindings behavior (new user of device is default user)
				Set<AbstractGestureDevice<?,?>> keys = userMapping.keySet();
				for(AbstractGestureDevice<?,?> d : keys)
				{
					if(userMapping.get(d).equals(user))
					{
						userMapping.put(d, getDefaultUser());
						
						Collection<DeviceUserAssociation> list = view.getDevices();
						for(Iterator<DeviceUserAssociation> iter = list.iterator(); iter.hasNext(); )
						{
							DeviceUserAssociation ass = iter.next();
							if(ass.getUserItem().equals(user))
							{
								view.updateDevice(getDefaultUser(), DeviceManagerView.COL_DEVICE_USER, ass);
								break;
							}
						}						
					}
				}
				
				LOGGER.log(Level.INFO,"User Removed: "+ user);
			}
		}
	}

	@Override
	public void associateUser(AbstractGestureDevice<?,?> device, IUser user) {
		
			view.updateDevice(user,DeviceManagerView.COL_DEVICE_USER, view.getSelectedDevice());
			userMapping.put(device, user);
			
			LOGGER.log(Level.INFO,"Associated User '"+user+"' with Device '"+device+"'");
	}

	@Override
	public void showView(Point p) {
		view.setLocation(p);
		view.setVisible(true);		
	}

	@Override
	public void saveConfiguration(File file)
	{
		//DB4O storage engine is used
		StorageEngine engine = StorageManager.createStorageEngine(file);
		IStorageManager storageManager = new StorageManager(engine);
		// save the users
		for(IUser user : users)
			storageManager.store((User)user);
		//save the devices and association
		for(DeviceUserAssociation ass : view.getDevices())
			storageManager.store(ass);		
		
		storageManager.commit();
		storageManager.dispose();
		
		LOGGER.log(Level.INFO, "Saved Device-User Configuration: "+file.toString());
	}

	@Override
	public void loadConfiguration(File file)
	{
		//DB4O storage engine is used
		StorageEngine engine = StorageManager.createStorageEngine(file);
		IStorageManager storageManager = new StorageManager(engine);
		//get the users from file
		List<User> users = storageManager.load(User.class);
		//get the associations from file
		List<DeviceUserAssociation> associations = storageManager.load(DeviceUserAssociation.class);
		storageManager.dispose();
		
		//remove users and devices except the default ones
		for(AbstractGestureDevice<?,?> device : devices)
		{
			if(!device.isDefaultDevice())
				removeDevice(device);
		}
		for(User user : users)
		{
			if(!user.equals(defaultUser))
				removeUser(user);
		}
		
		// load users
		for(User user : users)
		{
			if(!user.equals(defaultUser))
				addUser(user);
		}
		// load devices and association
		for(DeviceUserAssociation ass : associations)
		{
			if(!ass.getDeviceItem().isDefaultDevice())
			{
				ass.getDeviceItem().setIsConnected(false);
				ass.getDeviceItem().connect();
				addDevice(ass.getDeviceItem(),ass.getUserItem());
			} 
			else 
			{
				//TODO if SwingMouseReaderService is not referenced anywhere in hardcoded way this is not necessary anymore
				// for system mouse, keep current SwingMouseReaderService
				// if it was previously associated with the system user, use current system user object
				IUser u = null;
				if(!ass.getUserItem().equals(defaultUser))
					u = defaultUser;
				else // else use the associated user
					u = ass.getUserItem();
				associateUser(defaultDevice, u);
			}
		}
		
		LOGGER.log(Level.INFO, "Loaded Device-User Configuration: "+file.toString());
	}
	
	/**
	 * Clear the users, devices and device-user mapping.
	 */
	private void cleanup()
	{
		users.clear();
		devices.clear();
		userMapping.clear();
//		view.clear();
	}
	
	@Override
	public Map<String, DeviceDiscoveryService> getDiscoveryMapping()
	{
		return discoveryMapping;
	}
	
	public void addDeviceManagerListener(DeviceManagerListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeDeviceManagerListener(DeviceManagerListener listener)
	{
		listeners.remove(listener);
	}
	
	public void removeAllDeviceManagerListener()
	{
		listeners.clear();
	}
	
	public void notifyDeviceManagerListener(int operation, AbstractGestureDevice<?,?> device)
	{
		for(DeviceManagerListener listener : listeners)
		{
			listener.updateDeviceManagerListener(operation, device);
		}
	}

	@Override
	public IUser getAssociatedUser(GestureDevice<?, ?> device) {
		return userMapping.get((AbstractGestureDevice<?,?>)device);
	}
	
	public boolean areInitialsValid(String initials)
	{
		boolean valid = true;
		for(IUser user: users)
		{
			if(user.getInitials().equals(initials))
			{
				valid = false;
				break;
			}
		}
		return valid;
	}
}
