/*
	TUIO Java backend - part of the reacTIVision project
	http://reactivision.sourceforge.net/

	Copyright (c) 2005-2009 Martin Kaltenbrunner <mkalten@iua.upf.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package org.ximtec.igesture.io.tuio;

import com.illposed.osc.*;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.io.tuio.handler.AbstractTuioHandler;
import org.xml.sax.SAXException;


/**
 * @author Martin Kaltenbrunner
 * @author Björn Puypepuype@gmail.com
 * @version 1.4
 */ 
public class TuioConnection implements OSCListener {
	
	private static final Logger LOGGER = Logger.getLogger(TuioConnection.class.getName());
	
	/** port number to listen to */
	private int port = TuioConstants.DEFAULT_PORT;
	/** OSC port */
	private OSCPortIn oscPort;
	/** connection status */
	private boolean connected = false;
	/** time */
	private TuioTime currentTime;
	
	/** Mapping of the TUIO profiles on the TUIO handlers */
	private Map<String, AbstractTuioHandler> profileToHandler = new HashMap<String, AbstractTuioHandler>();
	
	/**
	 * Default constructor
	 */
	public TuioConnection()
	{
		this(TuioConstants.DEFAULT_PORT);
	}
	
	/**
	 * This constructor creates a client that listens to the provided port
	 *
	 * @param  port  the listening port number
	 */
	public TuioConnection(int port) {
		this.port = port;
	}
		
	/**
	 * The TuioClient starts listening to TUIO messages on the configured UDP port
	 * All received TUIO messages are decoded and the resulting TUIO events are broadcasted to all registered TuioListener(3D)s
	 */
	public void connect() {

		//setup timing information
		TuioTime.initSession();
		currentTime = new TuioTime();
		currentTime.reset();
		
		try {		
			//create OSC port to listen to
			oscPort = new OSCPortIn(port);
			
			//create handlers
			initProfiles();
						
			//add listener for each profile
			Set<String> keys = profileToHandler.keySet();
			for(String key : keys)
			{
				oscPort.addListener(key, this);
			}
			
			//start listening for messages
			oscPort.startListening();
			connected = true;
			LOGGER.log(Level.INFO,"Connected to port "+port);
		} catch (SocketException e) {
			LOGGER.log(Level.SEVERE,"Failed to connect to port "+port,e);
			connected = false;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"Could not parse "+TuioConstants.XML_TUIO_PROFILES,e);
			disconnect();
		}
	}
	
	/**
	 * Initialize the TUIO profiles.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private void initProfiles() throws ParserConfigurationException, SAXException, IOException
	{
		//location of the profiles file
		File file = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+TuioConstants.XML_TUIO_PROFILES);
			//TODO it should get the file from the framework not the tool
		//create a XML parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		//parse the XML file and create a DOM tree
		Document doc = db.parse(file);
		
		doc.getDocumentElement().normalize();
		//get all the association elements
		NodeList nodeList = doc.getElementsByTagName("association");

		//for all associations, create the mapping TUIO profile on TUIO handler
		for (int i = 0; i < nodeList.getLength(); i++) 
		{
		    Node node = nodeList.item(i);
		    
		    if (node.getNodeType() == Node.ELEMENT_NODE) 
		    {
		      NodeList profiles = ((Element)node).getElementsByTagName("profile");
		      NodeList p = ((Element) profiles.item(0)).getChildNodes();
//		      System.out.println("Profile : "  + ((Node) p.item(0)).getNodeValue());
		      
		      NodeList handlers = ((Element)node).getElementsByTagName("handler");
		      NodeList h = ((Element) handlers.item(0)).getChildNodes();
//		      System.out.println("Handler : " + ((Node) h.item(0)).getNodeValue());
		      
		      try {
		    	  //use reflection to create an instance through the class name
			      Class c = Class.forName(((Node) h.item(0)).getNodeValue());
			      profileToHandler.put(((Node) p.item(0)).getNodeValue(), (AbstractTuioHandler) c.newInstance());
		      } 
		      catch (Exception e) {
				LOGGER.log(Level.SEVERE,"Could not find class: "+((Node) h.item(0)).getNodeValue(),e);
		      }
		    }	    
		}
	}
	
	/**
	 * The TuioClient stops listening to TUIO messages on the configured UDP port
	 */
	public void disconnect() {
		LOGGER.log(Level.INFO,"Disconnecting from port "+port);
		oscPort.stopListening();
		try { Thread.sleep(100); }
		catch (Exception e) {};
		oscPort.close();
		connected = false;
	}

	/**
	 * Returns true if this TuioClient is currently connected.
	 * @return	true if this TuioClient is currently connected
	 */
	public boolean isConnected() { return connected; }

	/**
	 * Adds the provided TuioListener to the list of registered TUIO event listeners
	 *
	 * @param  	listener  the TuioListener to add
	 * @param	modifiers	the desired TUIO profiles	
	 */
	public void addTuioListener(TuioListener listener, Set<String> modifiers)
	{
		for(String s : modifiers)
		{
			if(profileToHandler.containsKey(s))
			{
				profileToHandler.get(s).addTuioListener(listener);
			}
			else
			{
				LOGGER.log(Level.WARNING,this.getClass().getName()+": Incorrect modifiers");
			}
		}
	}
	
	/**
	 * Removes the provided TuioListener from the list of registered TUIO event listeners
	 *
	 * @param  listener  the TuioListener to remove
	 * @param	modifiers	the desired TUIO profiles
	 */
	public void removeTuioListener(TuioListener listener, Set<String> modifiers)
	{
		for(String s : modifiers)
		{
			if(profileToHandler.containsKey(s))
			{
				profileToHandler.get(s).removeTuioListener(listener);
			}
			else
			{
				LOGGER.log(Level.WARNING,this.getClass().getName()+": Incorrect modifiers");
			}
		}
	}	
	
	/**
	 * Removes all TuioListeners from the list of registered TUIO event listeners
	 * @param	modifiers	the desired TUIO profiles
	 */
	public void removeAllTuioListener(Set<String> modifiers)
	{
		for(String s : modifiers)
		{
			if(profileToHandler.containsKey(s))
			{
				profileToHandler.get(s).removeAllTuioListeners();
			}
			else
			{
				LOGGER.log(Level.WARNING,this.getClass().getName()+": Incorrect modifiers");
			}
		}
	}
	
	/**
	 * The OSC callback method where all TUIO messages are received and decoded
	 * and where the TUIO event callbacks are dispatched
	 *
	 * @param  date	the time stamp of the OSC bundle
	 * @param  message	the received OSC message
	 */
	public void acceptMessage(Date date, OSCMessage message) {
	
		String address = message.getAddress();

		//delegate the message to the corresponding handler
		if(profileToHandler.containsKey(address))
			profileToHandler.get(address).acceptMessage(message, currentTime);
	}
}

