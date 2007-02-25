/*
 * @(#)ActionExecuteRecogniser.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Starts the recogniser
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.algorithm.action;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JList;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.tool.CaptureTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureMainModel;
import org.ximtec.igesture.tool.util.SimpleListModel;
import org.ximtec.igesture.tool.util.SwingTool;


public class ActionExecuteRecogniser extends BasicAction {

   private GestureMainModel mainModel;

   private JList set;

   private JList result;

   private CaptureTab tab;


   public ActionExecuteRecogniser(GestureMainModel mainModel, CaptureTab tab,
         JList set, JList result) {
      super(GestureConstants.COMMON_OK, SwingTool.getGuiBundle());
      this.mainModel = mainModel;
      this.set = set;
      this.result = result;
      this.tab = tab;
   }


   public void actionPerformed(ActionEvent arg0) {
      /**
       * instantiate the algorithm
       */

      try {

         final Configuration config = tab.getCurrentConfiguration();
         config.addGestureSet((GestureSet) this.set.getSelectedValue());
         final Algorithm algo = AlgorithmFactory.createAlgorithm(config);

         final ResultSet resultSet = algo.recognise(mainModel.getCurrentNote());

         final Vector<String> results = new Vector<String>();
         for (final Result result : resultSet.getResults()) {
            results.add(result.getGestureClass().getName() + ": "
                  + result.getAccuracy());
         }
         final SimpleListModel<String> model = new SimpleListModel<String>(
               results);
         this.result.setModel(model);
         this.result.repaint();

      }
      catch (final AlgorithmException e) {
         e.printStackTrace();
      }

   }

}
