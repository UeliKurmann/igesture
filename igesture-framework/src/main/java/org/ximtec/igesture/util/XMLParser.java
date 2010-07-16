package org.ximtec.igesture.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class helps with parsing of configuration files.
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public abstract class XMLParser {
	
	/**
	 * Parse an XML file.
	 * @param fileName	The path of the file to parse.
	 * @param nodeName	The name of the nodes under the root node (should all be same type).
	 * @param textNodes	The names of the sub nodes of a node of type nodeName. These nodes should all be text nodes.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parse(String fileName, String nodeName, List<String> textNodes) throws ParserConfigurationException, SAXException, IOException
	{
		//location of the XML file
		File file = new File(fileName);
		//create a XML parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		//parse the XML file and create a DOM tree
		Document doc = db.parse(file);
		
		doc.getDocumentElement().normalize();
		//get all the nodeName elements
		NodeList nodeList = doc.getElementsByTagName(nodeName);

		//for all nodeName elements, get all the subelements and put them in a arraylist
		//then execute the necessary operations on the parsed data
		for (int i = 0; i < nodeList.getLength(); i++) 
		{
		    Node node = nodeList.item(i);
		    
		    if (node.getNodeType() == Node.ELEMENT_NODE) 
		    {
		    	ArrayList<NodeList> nodeLists = new ArrayList<NodeList>();
		    	for(Iterator<String> iter = textNodes.iterator(); iter.hasNext(); )
		    	{
		    		NodeList nl = ((Element)node).getElementsByTagName(iter.next());
		    		nodeLists.add(((Element) nl.item(0)).getChildNodes());
		    	}

			    execute(nodeLists);
		    }	    
		}
	}

	/**
	 * This method is called for every node of type nodeName. This method has to be implemented to specify what to do with the
	 * parsed text nodes.
	 * @param nodeLists	A list with the parsed text nodes.
	 */
	public abstract void execute(ArrayList<NodeList> nodeLists);
	
}
