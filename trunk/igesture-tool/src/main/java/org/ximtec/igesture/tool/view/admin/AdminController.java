package org.ximtec.igesture.tool.view.admin;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.log4j.Logger;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.explorer.NodeInfoImpl;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.RootSet;
import org.ximtec.igesture.tool.view.admin.action.AddGestureClassAction;
import org.ximtec.igesture.tool.view.admin.action.ExportGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.ExportPDFGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.ImportGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveDescriptorAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureClassAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureSetAction;
import org.ximtec.igesture.tool.view.admin.panel.DefaultPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureClassPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureSetPanel;
import org.ximtec.igesture.tool.view.admin.panel.SampleDescriptorPanel;

public class AdminController implements Controller{
  
  private static final Logger LOG = Logger.getLogger(AdminController.class);
	
	private static List<NodeInfo> nodeInfos;
	
	private AdminView adminView;
	
	private MainModel mainModel = Locator.getDefault().getService(MainModel.IDENTIFIER, MainModel.class);
	
	private ExplorerTreeController explorerTreeController;
	
	public AdminController(){
	  
	  List<Class<? extends BasicAction>> rootActions = new ArrayList<Class<? extends BasicAction>>();
    rootActions.add(ImportGestureSetAction.class);
    rootActions.add(RemoveGestureSetAction.class);
    
	  List<Class<? extends BasicAction>> setActions = new ArrayList<Class<? extends BasicAction>>();
	  setActions.add(AddGestureClassAction.class);
	  setActions.add(ExportGestureSetAction.class);
	  setActions.add(ExportPDFGestureSetAction.class);
	  
	  List<Class<? extends BasicAction>> classActions = new ArrayList<Class<? extends BasicAction>>();
    classActions.add(RemoveGestureClassAction.class);
    
    List<Class<? extends BasicAction>> descriptorActions = new ArrayList<Class<? extends BasicAction>>();
    descriptorActions.add(RemoveDescriptorAction.class);
	  
		
		nodeInfos = new ArrayList<NodeInfo>();
		nodeInfos.add(new NodeInfoImpl(RootSet.class, "name","sets",DefaultPanel.class, rootActions));
		nodeInfos.add(new NodeInfoImpl(GestureSet.class, "name","gestureClasses",GestureSetPanel.class, setActions));
		nodeInfos.add(new NodeInfoImpl(GestureClass.class, "name","descriptors",GestureClassPanel.class, classActions));
		nodeInfos.add(new NodeInfoImpl(SampleDescriptor.class, "name",null,SampleDescriptorPanel.class, descriptorActions));
		nodeInfos.add(new NodeInfoImpl(GestureSample.class, "name",null,DefaultPanel.class, null));
		nodeInfos.add(new NodeInfoImpl(String.class, "", null, DefaultPanel.class, null));
	
		initController();
		
	}
	
	private void initController(){
	  adminView = new AdminView();
	  
	  ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel.getTestGestureSet(), getNodeInfoList());
    explorerTreeController = new ExplorerTreeController(adminView, explorerModel, getNodeInfoList());
	  
	}
	
	public List<NodeInfo> getNodeInfoList(){
		return nodeInfos;
	}

  @Override
  public JComponent getView() {
    return adminView;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // FIXME use a list of controller. 
    explorerTreeController.propertyChange(evt);
    
    LOG.info("PropertyChange");
    
  }
}
