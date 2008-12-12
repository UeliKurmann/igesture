/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 17.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.batch;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.batch.BatchTools;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.util.XMLTool;


/**
 * Comment
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class BatchController extends DefaultController {

   public static final String CMD_RUN_BATCH = "runBatch";
   public static final String CMD_CANCEL_BATCH = "cancelBatch";
   private static final Logger LOGGER = Logger.getLogger(BatchController.class
         .getName());

   private BatchSwingWorker batchSwingWorker;

   private BatchView view;


   public BatchController() {
      initialize();
   }


   private void initialize() {
      this.view = new BatchView(this);
   }


   @Override
   public JComponent getView() {
      return view;
   }

   @ExecCmd(name=CMD_RUN_BATCH)
   protected void executeBatchRun() {
      view.setRunActionState(false);
      view.setCancelActionState(true);
      view.showProgressBar();
      batchSwingWorker = new BatchSwingWorker();
      batchSwingWorker.execute();
   }
   
   @ExecCmd(name=CMD_CANCEL_BATCH)
   protected void executeBatchCancel() {
      if(batchSwingWorker != null && !batchSwingWorker.isDone()){
         System.out.println(batchSwingWorker.cancel(true));
         System.out.println(batchSwingWorker.getState());
      }
      
      view.hideProgressBar();  
      view.setRunActionState(true);
      view.setCancelActionState(false);
   }


   @Override
   public void propertyChange(PropertyChangeEvent event) {
      super.propertyChange(event);
      view.refresh();
   }

   private class BatchSwingWorker extends SwingWorker<BatchResultSet, Void> {

      File configFile = new File(view.getConfigFile());
      File outputDir = new File(view.getOutputDir());

      
      @Override
      protected BatchResultSet doInBackground() throws Exception {
         
         BatchResultSet resultSet = null;

         if (configFile.exists() && view.getTestSet() != null
               && view.getGestureSet() != null) {

            BatchProcessContainer container = XMLTool
                  .importBatchProcessContainer(configFile);

            BatchProcess batchProcess = new BatchProcess(container);
            batchProcess.setTestSet(view.getTestSet());
            batchProcess.addGestureSet(view.getGestureSet());

            resultSet = batchProcess.run();

            BatchTools.writeResultsOnDisk(outputDir, resultSet);

         }

         return resultSet;
      }


      @Override
      protected void done() {
         super.done();
         view.hideProgressBar();
         view.setRunActionState(true);
         view.setCancelActionState(false);
         
         // display html code
         try {
            String htmlCode = FileUtils.readFileToString(new File(outputDir, "result.html"));
            view.setResult(htmlCode);
         }
         catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not set result as html page.");
         }
         
      }
   }

}
