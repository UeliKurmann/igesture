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
import java.beans.PropertyChangeListener;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;


/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class AddConfigurationAction extends BasicAction {

   private TreePath treePath;


   public AddConfigurationAction(TreePath treePath) {
      super(GestureConstants.CONFIGURATION_ADD, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {

      AlgorithmWrapper algorithWrapper = (AlgorithmWrapper)treePath
            .getLastPathComponent();
      Configuration configuration = new Configuration();
      String algorithmName = algorithWrapper.getAlgorithm().getName();
      Algorithm algorithm = AlgorithmFactory
      .createAlgorithmInstance(algorithmName);
      
      configuration.addAlgorithm(algorithmName);
      for (Enum< ? > e : algorithm.getConfigParameters()) {
         configuration.addParameter(algorithmName, e.name(), algorithm
               .getDefaultParameterValue(e.name()));
      }
      algorithWrapper.addConfiguration(configuration);

      for (PropertyChangeListener listener : algorithWrapper.getListeners()) {
         configuration.addPropertyChangeListener(listener);
      }

   }

}