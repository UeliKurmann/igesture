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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;


public class TestbenchController extends DefaultController {

   private static final Logger LOG = Logger.getLogger(TestbenchController.class.getName());

   private static List<NodeInfo> nodeInfos;

   private TestbenchView testbenchView;

   private MainModel mainModel = Locator.getDefault().getService(
         MainModel.IDENTIFIER, MainModel.class);

   private ExplorerTreeController explorerTreeController;


   public TestbenchController() {
      nodeInfos = NodeInfoFactory.createTestBenchNodeInfo();
      initController();
   }


   private void initController() {
      testbenchView = new TestbenchView();

      ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel
            .getAlgorithmList(), getNodeInfoList());
      explorerTreeController = new ExplorerTreeController(testbenchView,
            explorerModel, getNodeInfoList());
      addController(explorerTreeController);
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
      super.propertyChange(evt);
      
      LOG.info("PropertyChange");

   }
}
