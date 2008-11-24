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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.batch.BatchTools;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.DefaultController;
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


   @Override
   public void execute(Command command) {
      super.execute(command);

      if (CMD_RUN_BATCH.equals(command.getCommand())) {
         LOGGER.log(Level.INFO, "Run Batch Process");
         executeBatchRun();
      }else if (CMD_CANCEL_BATCH.equals(command.getCommand())) {
         LOGGER.log(Level.INFO, "Run Batch Process");
         executeBatchCancel();
      }

   }


   private void executeBatchRun() {
      view.setRunActionState(false);
      view.setCancelActionState(true);
      view.showProgressBar();
      batchSwingWorker = new BatchSwingWorker();
      batchSwingWorker.execute();
   }
   
   private void executeBatchCancel() {
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

      @Override
      protected BatchResultSet doInBackground() throws Exception {
         
         File configFile = new File(view.getConfigFile());
         File outputDir = new File(view.getOutputDir());

         BatchResultSet resultSet = null;

         if (configFile.exists() && view.getTestSet() != null
               && view.getGestureSet() != null) {

            BatchProcessContainer container = XMLTool
                  .importBatchProcessContainer(configFile);

            BatchProcess batchProcess = new BatchProcess(container);
            batchProcess.setTestSet(view.getTestSet());
            batchProcess.addGestureSet(view.getGestureSet());

            resultSet = batchProcess.run();

            BatchTools.writeResulteOnDisk(outputDir, resultSet);

         }

         return resultSet;
      }


      @Override
      protected void done() {
         super.done();
         view.hideProgressBar();
         view.setRunActionState(true);
         view.setCancelActionState(false);
      }

   }

}
