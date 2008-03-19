package org.ximtec.igesture.tool.view.admin;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.explorer.NodeInfoImpl;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;
import org.ximtec.igesture.tool.view.admin.panel.DefaultPanel;
import org.ximtec.igesture.tool.view.admin.panel.DescriptorPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureClassPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureSetPanel;

public class AdminController{
	
	private static List<NodeInfo> nodeInfo;
	
	public AdminController(){
		
		nodeInfo = new ArrayList<NodeInfo>();
		nodeInfo.add(new NodeInfoImpl(GestureSet.class,"name","gestureClasses;name",GestureSetPanel.class));
		nodeInfo.add(new NodeInfoImpl(GestureClass.class,"name","descriptors;name",GestureClassPanel.class));
		nodeInfo.add(new NodeInfoImpl(SampleDescriptor.class,"name",null,DescriptorPanel.class));
		nodeInfo.add(new NodeInfoImpl(String.class, "", null, DefaultPanel.class));
	
	}
	
	public List<NodeInfo> getNodeInfoList(){
		return nodeInfo;
	}

	
	

}
