/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.tuio.TuioConstants;
import org.ximtec.igesture.io.tuio.TuioReader;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.view.Property;
import org.ximtec.igesture.tool.view.devicemanager.XMLParser;
import org.ximtec.igesture.tool.view.devicemanager.discoveryservice.BlueToothDeviceConverter.BTDeviceClass;
import org.xml.sax.SAXException;

/**
 * A BlueTooth device discovery service. It implements the {@link org.ximtec.igesture.tool.view.devicemanager.discoveryservice.DeviceDiscoveryService} interface.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class TuioDeviceDiscoveryService implements DeviceDiscoveryService {

	private static final Logger LOGGER = Logger.getLogger(TuioDeviceDiscoveryService.class.getName());
	/**
	 * First port of range to scan
	 */
	private int startPort;
	/**
	 * Last port of range to scan (inclusive)
	 */
	private int endPort;
	/**
	 * created devices
	 */
	private Set<AbstractGestureDevice<?,?>> devices;
	/**
	 * Mapping between port and name
	 */
	private Map<Integer, String> map;
	
	/**
	 * Constructor
	 */
	public TuioDeviceDiscoveryService()
	{
		//get port range to scan from the properties file
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(GestureConstants.PROPERTIES));
			startPort = Integer.parseInt(properties.getProperty(Property.TUIO_START_PORT));
			endPort = Integer.parseInt(properties.getProperty(Property.TUIO_START_PORT));
		} catch (Exception e) {
			LOGGER.log(Level.WARNING,"Could not load the tuio port range property from file. Falling back on the default port: 3333.",e);
			startPort = TuioConstants.DEFAULT_PORT;
			endPort = startPort;
		}
		
		map = new HashMap<Integer, String>();
		
		devices = new HashSet<AbstractGestureDevice<?,?>>();
		
		//get the registered tuio services
		XMLParser parser = new XMLParser(){

			@Override
			public void execute(ArrayList<NodeList> nodeLists) 
			{
				try {
					// read the port number
					int port = Integer.parseInt(((Node)nodeLists.get(0).item(0)).getNodeValue());
					// read the service name
					String deviceName = ((Node)nodeLists.get(1).item(0)).getNodeValue();
					// put it in the map
					map.put(port,deviceName);
				} catch (NumberFormatException e) {
					LOGGER.log(Level.WARNING,"Invalid Port Number. The corresponding Tuio device type was not added.",e);
				} catch (NullPointerException e) {
					LOGGER.log(Level.WARNING, "An empty node was encountered. The corresponding Tuio device type was not added.",e);
				}
			}
			
		};
		ArrayList<String> textNodes = new ArrayList<String>();
		textNodes.add("port");
		textNodes.add("name");
		try {
			parser.parse(System.getProperty("user.dir")+System.getProperty("file.separator")+GestureConstants.XML_TUIO, "device", textNodes);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not find "+GestureConstants.XML_TUIO,e);//TODO
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"Could not parse "+GestureConstants.XML_TUIO,e);//TODO
		}
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.view.devicemanager.discoveryservice.DeviceDiscoveryService#discover()
	 */
	@Override
	public Set<AbstractGestureDevice<?, ?>> discover() {
		
		LOGGER.log(Level.INFO,"Device discovery started!");
		
		//for each port in the defined range
		for(int port = startPort; port <= endPort; port++)
		{
			if(map.containsKey(port))
			{
				
				AbstractGestureDevice<?,?> device = new TuioReader(port);
				String name = map.get(port);
				if(name != null && !name.isEmpty())
				{
					device.setName(name);
				}
				else
					name = "Tuio Service";
				LOGGER.log(Level.INFO,"Device discovered: "+name+" on port "+port);
				devices.add(device);
			}
		}
		
		LOGGER.log(Level.INFO,"Device discovery completed!");
		
		if(devices.isEmpty())
			LOGGER.log(Level.INFO,"No devices discovered.");
		
		return devices;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.view.devicemanager.discoveryservice.DeviceDiscoveryService#dispose()
	 */
	@Override
	public void dispose() {
		devices.clear();
	}

}
