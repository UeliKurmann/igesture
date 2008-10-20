/*
 * @(#)$Id:$
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
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

import org.apache.commons.io.FileUtils;
import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.view.MainController;
import org.ximtec.igesture.util.XMLTool;


/**
 * Comment
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class BatchController extends DefaultController {

   private static final String XSL_HTML = "xml/batch.xsl";
   private static final String OUT_FILE_HTML = "result.html";
   private static final String OUT_FILE_XML = "result.xml";
   public static final String CMD_RUN_BATCH = "runBatch";
   private static final Logger LOGGER = Logger.getLogger(BatchController.class
         .getName());

   private Executor executor = Executors.newFixedThreadPool(1);
   private CompletionService<BatchResultSet> completionService = new ExecutorCompletionService<BatchResultSet>(
         executor);

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
      }

   }


   private void executeBatchRun() {

      JDialog progressbar = new JDialog();

      JProgressBar progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);
      progressbar.add(progressBar);
      progressbar.pack();
      progressbar.setVisible(true);

      File configFile = new File(view.getConfigFile());

      MainController mc = org.ximtec.igesture.tool.locator.Locator.getDefault()
            .getService(MainController.IDENTIFIER, MainController.class);
      mc.execute(new Command(MainController.CMD_START_WAITING));
      if (configFile.exists() && view.getTestSet() != null
            && view.getGestureSet() != null) {
         BatchProcessContainer container = XMLTool
               .importBatchProcessContainer(configFile);
         BatchProcess batchProcess = new BatchProcess(container);
         batchProcess.setTestSet(view.getTestSet());
         batchProcess.addGestureSet(view.getGestureSet());
         completionService.submit(batchProcess);

         try {
            // FIXME code not tested!
            BatchResultSet resultSet = completionService.take().get();
            XMLTool.exportBatchResultSet(resultSet, new File(new File(view
                  .getOutputDir()), OUT_FILE_XML));
            String html = XMLTool.transform(null, BatchController.class
                  .getResourceAsStream(XSL_HTML));
            FileUtils.writeStringToFile(new File(new File(view.getOutputDir()),
                  OUT_FILE_HTML), html);

         }
         catch (Exception e) {
            e.printStackTrace();
         }
      }
      
      progressbar.setVisible(false);
      progressbar.dispose();

   }


   @Override
   public void propertyChange(PropertyChangeEvent event) {
      super.propertyChange(event);
      view.refresh();
   }

}
