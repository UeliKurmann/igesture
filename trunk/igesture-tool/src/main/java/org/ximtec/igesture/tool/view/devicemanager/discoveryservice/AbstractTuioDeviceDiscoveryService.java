/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceDiscoveryService;
import org.ximtec.igesture.io.tuio.TuioConstants;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.view.Property;
import org.ximtec.igesture.util.XMLParser;

/**
 * @author Björn Puypeype, bpuype@gmail.com
 * 
 */
public abstract class AbstractTuioDeviceDiscoveryService implements
		DeviceDiscoveryService {

	private static final Logger LOGGER = Logger
			.getLogger(AbstractTuioDeviceDiscoveryService.class.getName());

	/**
	 * First port of range to be scanned.
	 */
	private int startPort;

	/**
	 * Last port of range to be scanned (inclusive)
	 */
	private int endPort;

	/**
	 * List of created devices.
	 */
	private Set<AbstractGestureDevice<?, ?>> devices;

	/**
	 * Mapping between port and name.
	 */
	private Map<Integer, String> map;

	private String typeToDiscover;
	private Class<?> clazzToInstantiate;

	/**
	 * Constructor
	 */
	public AbstractTuioDeviceDiscoveryService(String type, Class<?> clazz) {
		typeToDiscover = type;
		clazzToInstantiate = clazz;

		// get port range to scan from the properties file
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(
					GestureConstants.PROPERTIES));
			startPort = Integer.parseInt(properties
					.getProperty(Property.TUIO_START_PORT));
			endPort = Integer.parseInt(properties
					.getProperty(Property.TUIO_END_PORT));
		} catch (Exception e) {
			LOGGER
					.log(Level.WARNING,
							"Could not load the TUIO port range property. Using the default port '3333'.");
			startPort = TuioConstants.DEFAULT_PORT;
			endPort = startPort;
		}

		map = new HashMap<Integer, String>();
		devices = new HashSet<AbstractGestureDevice<?, ?>>();

		// get the registered tuio services
		XMLParser parser = new XMLParser() {

			@Override
			public void execute(ArrayList<NodeList> nodeLists) {
				try {
					// read the port number
					int port = Integer.parseInt(((Node) nodeLists.get(0)
							.item(0)).getNodeValue());
					// read the service name
					String deviceName = ((Node) nodeLists.get(1).item(0))
							.getNodeValue();
					// read in the types
					String type = ((Node) nodeLists.get(2).item(0))
							.getNodeValue();
					String[] types = type.split(Constant.COMMA);
					for (String s : types) {
						if (typeToDiscover.equals(s)) {
							// put it in the map
							map.put(port, deviceName);
							break;
						}

					}
				} catch (NumberFormatException e) {
					LOGGER
							.log(Level.WARNING,
									"Invalid Port Number. The corresponding Tuio device type was not added.");
				} catch (NullPointerException e) {
					LOGGER
							.log(
									Level.WARNING,
									"An empty node was encountered. The corresponding Tuio device type was not added.");
				}
			}

		};
		ArrayList<String> textNodes = new ArrayList<String>();
		textNodes.add("port");
		textNodes.add("name");
		textNodes.add("type");
		try {
			parser.parse(System.getProperty("user.dir")
					+ System.getProperty("file.separator")
					+ GestureConstants.XML_TUIO, "device", textNodes);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Could not find "
					+ GestureConstants.XML_TUIO, e);// TODO
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Could not parse "
					+ GestureConstants.XML_TUIO, e);// TODO
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.ximtec.igesture.tool.view.devicemanager.discoveryservice.
	 * DeviceDiscoveryService#discover()
	 */
	@Override
	public Set<AbstractGestureDevice<?, ?>> discover() {
		LOGGER.log(Level.INFO, "Device discovery started!");

		// for each port in the defined range
		for (int port = startPort; port <= endPort; port++) {
			if (map.containsKey(port)) {

				try {
					Constructor ctor = clazzToInstantiate
							.getConstructor(Integer.class);
					AbstractGestureDevice<?, ?> device = (AbstractGestureDevice<?, ?>) ctor
							.newInstance(new Integer(port));
					String name = map.get(port);
					if (name != null && !name.isEmpty()) {
						device.setName(name);
					} else
						name = "Tuio Service";
					LOGGER.log(Level.INFO, "Device discovered: " + name
							+ " on port " + port);
					devices.add(device);
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
		}

		LOGGER.log(Level.INFO, "Device discovery completed!");

		if (devices.isEmpty()) {
			LOGGER.log(Level.INFO, "No devices discovered.");
		}

		return devices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.ximtec.igesture.tool.view.devicemanager.discoveryservice.
	 * DeviceDiscoveryService#dispose()
	 */
	@Override
	public void dispose() {
		devices.clear();
	}
}
