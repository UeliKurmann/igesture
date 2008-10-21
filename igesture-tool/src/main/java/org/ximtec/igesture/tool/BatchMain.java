/*
 * @(#)BatchMain.java	1.0   Dec 16, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Front-end for the batch processing.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 16, 2006     ukurmann	Initial Release
 * Mar 18, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.sigtec.util.Constant;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.util.XMLTool;


/**
 * Front-end for the batch processing.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchMain {

   private static final Logger LOGGER = Logger.getLogger(BatchMain.class
         .getName());

   private static final String FILE = "file";

   private static final String HTML = "html";

   private static final String XSL = "xsl";

   private static final String XML = "xml";

   private static final String TESTSET = "testset";

   private static final String GESTURESET = "gestureset";

   private static final String CONFIG = "config";

   private static final String HELP = "help";


   /**
    * Front-end for the batch process.
    * 
    * @param args
    */
   public static void main(String args[]) {
      final Options options = new Options();
      options.addOption(createOption(HELP, FILE, "Print this message"));
      options.addOption(createOption(CONFIG, FILE, "XML configuration file"));
      options.addOption(createOption(GESTURESET, FILE, "XML gesture set file"));
      options.addOption(createOption(TESTSET, FILE, "XML test set file"));
      options.addOption(createOption(XML, FILE, "XML output file"));
      options.addOption(createOption(XSL, FILE, "XSLT file"));
      options.addOption(createOption(HTML, FILE, "HTML output file"));
      final CommandLineParser parser = new GnuParser();
      CommandLine line = null;

      try {
         line = parser.parse(options, args);
      }
      catch (final ParseException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      if (line.getOptions().length == 0 || line.hasOption(HELP)) {
         final HelpFormatter formatter = new HelpFormatter();
         formatter.printHelp("iGesutre - Batch Processing", options);
      }
      else if ((line.hasOption(CONFIG) && line.hasOption(GESTURESET) && line
            .hasOption(TESTSET))) {
         BatchProcessContainer container = XMLTool.importBatchProcessContainer(new File(line
               .getOptionValue(CONFIG)));
         
         final BatchProcess batchProcess = new BatchProcess(container);
         batchProcess.addGestureSet(XMLTool.importGestureSet(new File(line
               .getOptionValue(GESTURESET))));
         batchProcess.setTestSet(XMLTool.importTestSet(
               new File(line.getOptionValue(TESTSET))).get(0));
         final BatchResultSet resultSet = batchProcess.run();
         final String xmlDocument = XMLTool.exportBatchResultSet(resultSet);

         if (line.hasOption(XML)) {
            org.sigtec.util.FileHandler.writeFile(line.getOptionValue(XML),
                  xmlDocument);
         }

         if (line.hasOption(XSL) && line.hasOption(HTML)) {
            String htmlPage = Constant.EMPTY_STRING;

            try {
               htmlPage = XMLTool.transform(xmlDocument, line
                     .getOptionValue(XSL));
            }
            catch (final TransformerConfigurationException e) {
               LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
            }
            catch (final TransformerException e) {
               LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
            }
            catch (final TransformerFactoryConfigurationError e) {
               LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
            }

            FileHandler.writeFile(line.getOptionValue(HTML), htmlPage);
         }

      }

   }


   @SuppressWarnings("static-access")
   private static Option createOption(String name, String argument,
         String description) {
      return OptionBuilder.withArgName(argument).hasArg().withDescription(
            description).create(name);
   } // createOption

}
