/*
 * @(#)AlgorithmFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Algorithm Frame
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


package org.ximtec.igesture.tool.frame.algorithm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.graphix.widget.BasicLabel;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.CaptureTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.GestureSetListener;
import org.ximtec.igesture.tool.frame.algorithm.action.ActionExecuteRecogniser;
import org.ximtec.igesture.tool.utils.SwingTool;


public class AlgorithmFrame extends BasicInternalFrame implements
      GestureSetListener {

   private GestureToolView mainView;

   private CaptureTab tab;

   private JList setList;

   private JList resultList;


   /**
    * This is the default constructor
    */
   public AlgorithmFrame(GestureToolView mainView, CaptureTab tab) {
      super(GestureConstants.ALGORITHM_FRAME_KEY, SwingTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      this.tab = tab;

      this.mainView.getModel().addGestureSetListener(this);

      initialize();
   }


   /**
    * This method initializes this
    * 
    * @return void
    */
   private void initialize() {

      this
            .addComponent(createSetList(), SwingTool.createGridBagConstraint(0,
                  2));
      this.addComponent(createResultLable(), SwingTool.createGridBagConstraint(
            0, 3, 1, 1, GridBagConstraints.WEST));
      this.addComponent(createResultList(), SwingTool.createGridBagConstraint(0,
            4));
      this.addComponent(createRunButton(), SwingTool.createGridBagConstraint(0,
            0));

      this.repaint();
   }


   private Component createRunButton() {
      return SwingTool.createButton(new ActionExecuteRecogniser(mainView
            .getModel(), tab, setList, resultList));
   }


   private Component createSetList() {
      setList = new JList();
      final JScrollPane pane = new JScrollPane(setList);
      pane.setPreferredSize(new Dimension(180, 60));

      updateSetList();
      return pane;
   }


   private void updateSetList() {
      final Vector<GestureSet> sets = new Vector<GestureSet>();
      for (final GestureSet set : mainView.getModel().getGestureSets()) {
         sets.add(set);
      }
      setList.setListData(sets);
      this.repaint();
   }


   private Component createResultLable() {
      final BasicLabel label = SwingTool
            .createLabel(GestureConstants.ALGORITHM_FRAME_RESULT_LABEL);
      return label;
   }


   private Component createResultList() {
      resultList = new JList();
      final JScrollPane pane = new JScrollPane(resultList);
      pane.setPreferredSize(new Dimension(180, 100));
      return pane;
   }


   public void gestureSetChanged(EventObject event) {
      updateSetList();
   }
}
