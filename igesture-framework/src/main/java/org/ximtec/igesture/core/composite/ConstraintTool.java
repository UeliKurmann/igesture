/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.sigtec.ink.Note;
import org.sigtec.ink.NoteTool;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.util.XMLParser;

/**
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class ConstraintTool {
	
	private static final Map<String, String> deviceMapping = new HashMap<String,String>(); 
	
	static{
		
		XMLParser parser = new XMLParser(){

			@Override
			public void execute(ArrayList<NodeList> nodeLists) {
				String name = ((Node)nodeLists.get(0).item(0)).getNodeValue();
				String type = ((Node)nodeLists.get(1).item(0)).getNodeValue();
				deviceMapping.put(name, type);
			}
			
		};
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("name");
		nodes.add("type");
		try {
			URL path = ConstraintTool.class.getClassLoader().getResource("config.xml");
			parser.parse(path.getFile(),"device", nodes);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	public static String getGestureType(String deviceType)
	{
		return deviceMapping.get(deviceType);
	}

	public static boolean isBoundsDiagonalValid(List<Note> notes, double minDistance, double maxDistance)
	{
		boolean valid = true;
		Rectangle2D bounds = NoteTool.getBounds2D(notes);
		double w = bounds.getWidth();
		double h = bounds.getHeight();
		double d = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
		if(d < minDistance || d > maxDistance)
			valid = false;
		return valid;
	}
	
	public static long calculateTimeInMillis(Calendar time)
	{
		long t = 0;
		t += time.get(Calendar.MILLISECOND);
		t += time.get(Calendar.SECOND)*1000;
		t += time.get(Calendar.MINUTE)*60*1000;
		t += time.get(Calendar.HOUR)*24*60*1000;	
		return t;
	}
	
	public static void permute(int level, String permuted, boolean used[], String original, Set<String> results) 
	{
		int length = original.length();
		if (level == length) 
		{
		    results.add(permuted);
		} 
		else 
		{
		    for (int i = 0; i < length; i++) 
		    {
		        if (!used[i]) {
		            used[i] = true;
		            permute(level + 1, permuted + original.charAt(i), used, original, results);
		            used[i] = false;
		        }
		    }
		}
	}
}
