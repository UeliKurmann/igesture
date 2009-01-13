//package org.ximtec.igesture.io.wiimote;
///*
// * @(#)$Id: BatchController.java 663 2008-12-12 22:21:56Z kurmannu $
// *
// * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
// *                  
// *
// * Purpose		: 
// *
// * -----------------------------------------------------------------------
// *
// * Revision Information:
// *
// * Date				Who			Reason
// *
// * 17.04.2008			ukurmann	Initial Release
// *
// * -----------------------------------------------------------------------
// *
// * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
// *
// * This software is the proprietary information of ETH Zurich.
// * Use is subject to license terms.
// * 
// */
//
//import java.beans.PropertyChangeEvent;
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.swing.SwingWorker;
//
//import org.apache.commons.io.FileUtils;
//import org.ximtec.igesture.batch.BatchProcess;
//import org.ximtec.igesture.batch.BatchProcessContainer;
//import org.ximtec.igesture.batch.BatchResultSet;
//import org.ximtec.igesture.batch.BatchTools;
//import org.ximtec.igesture.util.XMLTool;
//
//
///**
// * Comment
// * @version 1.0 17.04.2008
// * @author Ueli Kurmann
// */
//public class TestBatchController extends DefaultController {
//
//   public static final String CMD_RUN_BATCH = "runBatch";
//   public static final String CMD_CANCEL_BATCH = "cancelBatch";
//   private static final Logger LOGGER = Logger.getLogger(TestBatchController.class
//         .getName());
//
//   private BatchSwingWorker batchSwingWorker;
//
//   public TestBatchController() {
//   }
//
//
//   @ExecCmd(name=CMD_RUN_BATCH)
//   protected void executeBatchRun() {
//      batchSwingWorker = new BatchSwingWorker();
//      batchSwingWorker.execute();
//   }
//   
//   @ExecCmd(name=CMD_CANCEL_BATCH)
//   protected void executeBatchCancel() {
//      if(batchSwingWorker != null && !batchSwingWorker.isDone()){
//         System.out.println(batchSwingWorker.cancel(true));
//         System.out.println(batchSwingWorker.getState());
//      }
//
//   }
//
//
//   private class BatchSwingWorker extends SwingWorker<BatchResultSet, Void> {
//
//      File configFile = new File("C:\\configuration.xml");
//      File outputDir = new File("C:\\Output");
//
//      
//      @Override
//      protected BatchResultSet doInBackground() throws Exception {
//         
//         BatchResultSet resultSet = null;
//
//         if (configFile.exists() && view.getTestSet() != null
//               && view.getGestureSet() != null) {
//
//            BatchProcessContainer container = XMLTool
//                  .importBatchProcessContainer(configFile);
//
//            BatchProcess batchProcess = new BatchProcess(container);
//            batchProcess.setTestSet(view.getTestSet());
//            batchProcess.addGestureSet(view.getGestureSet());
//
//            resultSet = batchProcess.run();
//
//            BatchTools.writeResultsOnDisk(outputDir, resultSet);
//
//         }
//
//         return resultSet;
//      }
//
//
//      @Override
//      protected void done() {
//         super.done();
//         
//         // display html code
//         try {
//            String htmlCode = FileUtils.readFileToString(new File(outputDir, "result.html"));
//         }
//         catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Could not set result as html page.");
//         }
//         
//      }
//   }
//
//}
