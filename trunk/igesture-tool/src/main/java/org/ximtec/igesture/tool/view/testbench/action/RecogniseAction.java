/*
 * @(#)$Id: AddConfigurationAction.java 465 2008-03-23 23:14:59Z kurmannu $
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
 * 23.03.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.testbench.panel.ConfigurationPanel;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class RecogniseAction extends BasicAction {

   private static final int MIN_POINTS = 5;
   private Configuration configuration;
   private ConfigurationPanel panel;


   public RecogniseAction(Configuration configuration, ConfigurationPanel panel) {
      super(GestureConstants.RECONGISE, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.configuration = configuration;
      this.panel = panel;
   }
   
   @Override
   public void actionPerformed(ActionEvent arg0) {
      
      try {
         Algorithm algorithm = AlgorithmFactory.createAlgorithm(configuration);
         Note note = panel.getCurrentNote();
         if(note.getPoints().size() >= MIN_POINTS){
            ResultSet resultSet = algorithm.recognise(note);
            panel.setResultList(resultSet.getResults()); 
         }
      }
      catch (AlgorithmException e) {
         e.printStackTrace();
      }

   }

}
