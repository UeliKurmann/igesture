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
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.io.mouseclient.SwingMouseReader;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.testbench.panel.ConfigurationPanel;


public class TestbenchController extends DefaultController {

   private static final Logger LOGGER = Logger
         .getLogger(TestbenchController.class.getName());

   private TestbenchView testbenchView;

   private MainModel mainModel = Locator.getDefault().getService(
         MainModel.IDENTIFIER, MainModel.class);

   private ExplorerTreeController explorerTreeController;

   public final static String CMD_RECOGNIZE = "recognize";


   public TestbenchController() {
      initController();
   }


   private void initController() {
      testbenchView = new TestbenchView();

      ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel
            .getAlgorithmList(), NodeInfoFactory.createTestBenchNodeInfo());
      explorerTreeController = new ExplorerTreeController(testbenchView,
            explorerModel);

      addController(explorerTreeController);
   }


   @Override
   public TabbedView getView() {
      return testbenchView;
   }

   @ExecCmd(name=CMD_RECOGNIZE)
   protected void executeRecognize(Command command) {
      try {
         Algorithm algorithm = AlgorithmFactory
               .createAlgorithm((Configuration)command.getSender());

         SwingMouseReader gestureDevice = Locator.getDefault().getService(
               InputDeviceClientService.IDENTIFIER, SwingMouseReader.class);

         if (gestureDevice.getGesture() != null && gestureDevice.getGesture().getGesture() instanceof Note) {

            Note note = (Note)gestureDevice.getGesture().getGesture();
            int MIN_POINTS = 5;
            if (note.getPoints().size() >= MIN_POINTS) {

               ResultSet resultSet = algorithm.recognise(new GestureSample(
                     Constant.EMPTY_STRING, note));

               if (explorerTreeController.getExplorerTreeView() instanceof ConfigurationPanel) {
                  ConfigurationPanel panel = (ConfigurationPanel)explorerTreeController
                        .getExplorerTreeView();
                  panel.setResultList(resultSet.getResults());
               }
            }
         }
      }
      catch (AlgorithmException e) {
         e.printStackTrace();
      }

   }


   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      LOGGER.info("PropertyChange");

      super.propertyChange(evt);
   }
}
