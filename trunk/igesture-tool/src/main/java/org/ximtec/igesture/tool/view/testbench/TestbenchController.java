/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.explorer.NodeInfoImpl;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.testbench.action.AddConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.action.ExportConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.action.RemoveConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.panel.AlgorithmListPanel;
import org.ximtec.igesture.tool.view.testbench.panel.AlgorithmWrapperPanel;
import org.ximtec.igesture.tool.view.testbench.panel.ConfigurationPanel;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;


public class TestbenchController implements Controller {

   private static final Logger LOG = Logger.getLogger(TestbenchController.class
         .getName());

   private static List<NodeInfo> nodeInfos;

   private TestbenchView testbenchView;

   private MainModel mainModel = Locator.getDefault().getService(
         MainModel.IDENTIFIER, MainModel.class);

   private ExplorerTreeController explorerTreeController;


   public TestbenchController() {

      List<Class< ? extends BasicAction>> algorithmListActions = new ArrayList<Class< ? extends BasicAction>>();
      

      List<Class< ? extends BasicAction>> algorithmWrapperActions = new ArrayList<Class< ? extends BasicAction>>();
      algorithmWrapperActions.add(AddConfigurationAction.class);
  
      List<Class< ? extends BasicAction>> configurationActions = new ArrayList<Class< ? extends BasicAction>>();
      configurationActions.add(RemoveConfigurationAction.class);
      configurationActions.add(ExportConfigurationAction.class);
      
     
      nodeInfos = new ArrayList<NodeInfo>();
      nodeInfos.add(new NodeInfoImpl(AlgorithmList.class, "name", "algorithms",
            AlgorithmListPanel.class, algorithmListActions, null));

      nodeInfos.add(new NodeInfoImpl(AlgorithmWrapper.class, "name",
            "configurations", AlgorithmWrapperPanel.class, algorithmWrapperActions, null));

      nodeInfos.add(new NodeInfoImpl(Configuration.class, "name", null,
            ConfigurationPanel.class, configurationActions, null));

      initController();

   }


   private void initController() {
      testbenchView = new TestbenchView();

      ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel
            .getAlgorithmList(), getNodeInfoList());
      explorerTreeController = new ExplorerTreeController(testbenchView,
            explorerModel, getNodeInfoList());

   }


   public Map<Class< ? >, NodeInfo> getNodeInfoList() {
      Map<Class< ? >, NodeInfo> map = new HashMap<Class< ? >, NodeInfo>();
      for (NodeInfo nodeInfo : nodeInfos) {
         map.put(nodeInfo.getType(), nodeInfo);
      }
      return map;
   }


   @Override
   public JComponent getView() {
      return testbenchView;
   }


   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      // FIXME use a list of controller.
      explorerTreeController.propertyChange(evt);

      LOG.info("PropertyChange");

   }
}
