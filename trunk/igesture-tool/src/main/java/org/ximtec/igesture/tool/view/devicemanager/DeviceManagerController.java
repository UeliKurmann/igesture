package org.ximtec.igesture.tool.view.devicemanager;

import java.awt.Point;
import java.io.File;
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

import org.sigtec.graphix.GuiBundle;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.storage.IStorageManager;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.view.devicemanager.discoveryservice.DeviceDiscoveryService;

/**
 * This class is an implementation of the controller of the Device Manager. It implements the {@link org.ximtec.igesture.tool.view.devicemanager.IDeviceManager} interface.
 * It also extends {@link org.ximtec.igesture.tool.core.DefaultController} to allow easy instantion by the main controller of the workbench.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class DeviceManagerController extends DefaultController implements IDeviceManager{

	/*
	 * device mapping: one user per device, what about tuio?
	 * */
	
	private Set<User> users = new HashSet<User>();
	private Set<AbstractGestureDevice<?,?>> devices = new HashSet<AbstractGestureDevice<?,?>>();
	private Map<AbstractGestureDevice<?,?>, User> userMapping = new HashMap<AbstractGestureDevice<?,?>,User>();
	
	private final Map<String, DeviceDiscoveryService> discoveryMapping = new HashMap<String,DeviceDiscoveryService>();
	
	private User defaultUser;
//	private AbstractGestureDevice<?,?> defaultMouse;
	private DeviceManagerView view;

	/**
	 * Constructor
	 * @param parentController
	 * @param key
	 * @param guiBundle
	 */
	public DeviceManagerController(Controller parentController, String key, GuiBundle guiBundle) {
		super(parentController);
		
		//instantiate the view
		view = new DeviceManagerView(this, key, guiBundle);
		
		//instantiate the default user/system user
		String userName = System.getProperty("user.name");
		defaultUser = new User(userName,userName.substring(0, 1));
		defaultUser.setDefaultUser(true);
		
		//add the default user and the mouse.
		addUser(defaultUser);
		addDevice((AbstractGestureDevice<?, ?>) parentController.getLocator().getService(SwingMouseReaderService.IDENTIFIER),defaultUser);
//		addUser(new User("test","T"));
//		User userI = new User("ik","I");
//		addUser(userI);
//		addUser(new User("Beat","BS"));
		
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
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
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
				}
			}
			
		};
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("name");
		nodes.add("discoveryService");
		try {
			parser.parse(System.getProperty("user.dir")+System.getProperty("file.separator")+GestureConstants.XML_DISCOVERY, "connection", nodes);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not parse connections.xml"); //TODO
		}		
	}

	@Override
	public TabbedView getView() {
		return null;
	}

	@Override
	public void addDevice(AbstractGestureDevice<?,?> device, User user) {
		if(!devices.contains(device))
		{
			devices.add(device);
			DeviceUserAssociation ass = new DeviceUserAssociation(device,user);
			if(!userMapping.containsKey(device))
			{
				userMapping.put(device, user);
			}
			view.addDevice(ass);
		}
	}

	@Override
	public void addUser(User user) {
		if(!users.contains(user))
		{
			users.add(user);
			view.addUser(user);
		}
	}

	@Override
	public User getDefaultUser() {
		return defaultUser;
	}

	@Override
	public Set<AbstractGestureDevice<?,?>> getDevices() {
		return devices;
	}

	@Override
	public Set<User> getUsers() {
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
		}
				
	}

	@Override
	public void removeUser(User user) {
		
		
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
			}
		}
	}

	@Override
	public void associateUser(AbstractGestureDevice<?,?> device, User user) {
		
			view.updateDevice(user,DeviceManagerView.COL_DEVICE_USER,null);
			userMapping.put(device, user);
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
		for(User user : users)
			storageManager.store(user);
		//save the devices and association
		for(DeviceUserAssociation ass : view.getDevices())
			storageManager.store(ass);		
		
		storageManager.commit();
		storageManager.dispose();
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
		
		cleanup();
		// load users
		for(User user : users)
		{
			addUser(user);
		}
		// load devices and association
		for(DeviceUserAssociation ass : associations)
		{
			if(!devices.contains(ass.getDeviceItem()))
			{
				addDevice(ass.getDeviceItem(),ass.getUserItem());
			}
		}
		
	}
	
	/**
	 * Clear the users, devices and device-user mapping.
	 */
	private void cleanup()
	{
		users.clear();
		devices.clear();
		userMapping.clear();
	}
	
	@Override
	public Map<String, DeviceDiscoveryService> getDiscoveryMapping()
	{
		return discoveryMapping;
	}
}
