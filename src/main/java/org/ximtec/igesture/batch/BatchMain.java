/*
 * @(#)BatchMain.java	1.0   Dec 16, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Front-End Batch Processing
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;

import java.io.File;

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
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.util.XMLTools;


/**
 * Comment
 * 
 * @version 1.0 Dec 16, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class BatchMain {

   private static final String FILE = "file";

   private static final String HTML = "html";

   private static final String XSL = "xsl";

   private static final String XML = "xml";

   private static final String TESTSET = "testset";

   private static final String GESTURESET = "gestureset";

   private static final String CONFIG = "config";

   private static final String HELP = "help";


   /**
    * Front End of the Batch RPocess
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
         e.printStackTrace();
      }

      if (line.getOptions().length == 0 || line.hasOption(HELP)) {
         final HelpFormatter formatter = new HelpFormatter();
         formatter.printHelp("iGesutre - Batch Processing", options);
      }
      else if ((line.hasOption(CONFIG) && line.hasOption(GESTURESET) && line
            .hasOption(TESTSET))) {

         final BatchProcess batchProcess = new BatchProcess(new File(line
               .getOptionValue(CONFIG)));
         batchProcess.addGestureSets(XMLTools.importGestureSet(new File(line
               .getOptionValue(GESTURESET))));

         batchProcess.setTestSet(XMLTools.importTestSet(
               new File(line.getOptionValue(TESTSET))).get(0));

         final BatchResultSet resultSet = batchProcess.run();
         final String xmlDocument = XMLTools.exportBatchResultSet(resultSet);
         if (line.hasOption(XML)) {
            org.sigtec.util.FileHandler.writeFile(line.getOptionValue(XML),
                  xmlDocument);
         }
         if (line.hasOption(XSL) && line.hasOption(HTML)) {
            String htmlPage = "";
            try {
               htmlPage = XMLTools.transform(xmlDocument, line
                     .getOptionValue(XSL));
            }
            catch (final TransformerConfigurationException e) {
               e.printStackTrace();
            }
            catch (final TransformerException e) {
               e.printStackTrace();
            }
            catch (final TransformerFactoryConfigurationError e) {
               e.printStackTrace();
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
   }
}
