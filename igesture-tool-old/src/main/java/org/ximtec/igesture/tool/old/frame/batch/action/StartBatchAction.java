/*
 * @(#)StartBatchAction.java  1.0   Dec 4, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 04, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.batch.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.util.XMLTool;


/**
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class StartBatchAction extends BasicAction {

   private static final Logger LOGGER = Logger.getLogger(StartBatchAction.class
         .getName());

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "StartBatchAction";

   private static final String BATCH_RESOURCE = "xml/batch.xsl";

   private JTextField config;
   private JTextField result;
   private ScrollableList gestureSets;
   private ScrollableList testSets;


   public StartBatchAction(JTextField config, JTextField result,
         ScrollableList gestureSets, ScrollableList testSets) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.config = config;
      this.result = result;
      this.gestureSets = gestureSets;
      this.testSets = testSets;
   }


   public void actionPerformed(ActionEvent event) {
      File configFile = new File(config.getText());
      File resultFile = new File(result.getText());
      GestureSet gestureSet = (GestureSet)gestureSets.getSelectedValue();
      TestSet testSet = (TestSet)testSets.getSelectedValue();

      if (configFile.exists() && resultFile != null && gestureSet != null
            && testSet != null) {
         BatchProcess batchProcess = new BatchProcess(configFile);
         batchProcess.addGestureSet(gestureSet);
         batchProcess.setTestSet(testSet);
         BatchResultSet brs = batchProcess.run();
         String xmlDocument = XMLTool.exportBatchResultSet(brs);

         try {
            String htmlPage = XMLTool.transform(xmlDocument,
                  StartBatchAction.class.getClassLoader().getResourceAsStream(
                        BATCH_RESOURCE));
            FileHandler.writeFile(resultFile.getPath(), htmlPage);
         }
         catch (TransformerConfigurationException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }
         catch (TransformerException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }
         catch (TransformerFactoryConfigurationError e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

      }

   } // actionPerformed

}
