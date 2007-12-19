/*
 * @(#)AlgorithmFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Algorithm frame.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.algorithm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.CaptureTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.GestureSetListener;
import org.ximtec.igesture.tool.frame.algorithm.action.ExecuteRecogniserAction;


/**
 * Algorithm frame.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AlgorithmFrame extends BasicInternalFrame implements
      GestureSetListener {

   private GestureToolView mainView;

   private CaptureTab tab;

   private JList setList;

   private JList resultList;


   /**
    * Constructs a new algorithm frame.
    * 
    * @param mainView the main view.
    * @param tab the capture tab to be added.
    */
   public AlgorithmFrame(GestureToolView mainView, CaptureTab tab) {
      super(GestureConstants.ALGORITHM_FRAME_KEY, GuiTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      this.tab = tab;
      this.mainView.getModel().addGestureSetListener(this);
      init();
   }


   /**
    * Initialises the tab.
    */
   private void init() {
      addComponent(createSetList(), SwingTool.createGridBagConstraint(0, 2));
      addComponent(createResultLabel(), SwingTool.createGridBagConstraint(0, 3,
            1, 1, GridBagConstraints.WEST));
      addComponent(createResultList(), SwingTool.createGridBagConstraint(0, 4));
      addComponent(createRunButton(), SwingTool.createGridBagConstraint(0, 0));
      repaint();
   } // init


   private Component createRunButton() {
      return GuiTool.createButton(new ExecuteRecogniserAction(mainView
            .getModel(), tab, setList, resultList), false);
   } // createRunButton


   private Component createSetList() {
      setList = new JList();
      final JScrollPane pane = new JScrollPane(setList);
      pane.setPreferredSize(new Dimension(180, 60));
      updateSetList();
      return pane;
   } // createSetList


   private void updateSetList() {
      final Vector<GestureSet> sets = new Vector<GestureSet>();

      for (final GestureSet set : mainView.getModel().getGestureSets()) {
         sets.add(set);
      }

      setList.setListData(sets);
      this.repaint();
   } // updateSetList


   private Component createResultLabel() {
      return GuiTool
            .createLabel(GestureConstants.ALGORITHM_FRAME_RESULT_LABEL);
   } // createResultLabel


   private Component createResultList() {
      resultList = new JList();
      final JScrollPane pane = new JScrollPane(resultList);
      pane.setPreferredSize(new Dimension(180, 100));
      return pane;
   } // createResultList


   public void gestureSetChanged(EventObject event) {
      updateSetList();
   } // gestureSetChanged

}
