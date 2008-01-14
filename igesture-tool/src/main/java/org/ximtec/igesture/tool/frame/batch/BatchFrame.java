/*
 * @(#)BatchFrame.java  1.0   Dec 4, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 04, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.batch;

import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.SimpleListModel;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.GestureSetListener;
import org.ximtec.igesture.tool.event.TestSetListener;
import org.ximtec.igesture.tool.frame.batch.action.SelectConfigFileAction;
import org.ximtec.igesture.tool.frame.batch.action.SelectResultFileAction;
import org.ximtec.igesture.tool.frame.batch.action.StartBatchAction;


/**
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchFrame extends BasicInternalFrame implements
      GestureSetListener, TestSetListener {

   private GestureToolView mainView;
   private JTextField configPath;
   private JTextField outputPath;
   private ScrollableList gestureSets;
   private ScrollableList testSets;


   public BatchFrame(GestureToolView mainView) {
      super(GestureConstants.GESTUREVIEW_TAB_BATCH, GuiTool.getGuiBundle());
      this.mainView = mainView;
      this.setResizable(true);
      mainView.getModel().addGestureSetListener(this);
      mainView.getModel().addTestSetListener(this);
      init();
   }


   private void init() {
      setSize(400, 300);
      setVisible(true);
      JLabel configLabel = GuiTool
            .createLabel(GestureConstants.BATCHPROCESSING_CONFIG);
      JLabel gestureSetLabel = GuiTool
            .createLabel(GestureConstants.BATCHPROCESSING_GESTURESET);
      JLabel testSetLabel = GuiTool
            .createLabel(GestureConstants.BATCHPROCESSING_TESTSET);
      JLabel outputLabel = GuiTool
            .createLabel(GestureConstants.BATCHPROCESSING_OUTPUT);
      configPath = new JTextField();
      configPath.setPreferredSize(new Dimension(100, 20));

      outputPath = new JTextField();
      outputPath.setPreferredSize(new Dimension(100, 20));
      JButton configButton = SwingTool.createButton(new SelectConfigFileAction(
            configPath));
      addComponent(configLabel, SwingTool.createGridBagConstraint(0, 0));
      addComponent(configPath, SwingTool.createGridBagConstraint(1, 0));
      addComponent(configButton, SwingTool.createGridBagConstraint(2, 0));
      gestureSets = createList(new SimpleListModel<GestureSet>(mainView
            .getModel().getGestureSets()));
      addComponent(gestureSetLabel, SwingTool.createGridBagConstraint(0, 1));
      addComponent(gestureSets, SwingTool.createGridBagConstraint(1, 1, 2, 1));
      testSets = createList(new SimpleListModel<TestSet>(mainView.getModel()
            .getTestSets()));
      addComponent(testSetLabel, SwingTool.createGridBagConstraint(0, 2));
      addComponent(testSets, SwingTool.createGridBagConstraint(1, 2, 2, 1));
      JButton outputButton = SwingTool.createButton(new SelectResultFileAction(
            outputPath));
      addComponent(outputLabel, SwingTool.createGridBagConstraint(0, 3));
      addComponent(outputPath, SwingTool.createGridBagConstraint(1, 3));
      addComponent(outputButton, SwingTool.createGridBagConstraint(2, 3));
      JButton startButton = SwingTool.createButton(new StartBatchAction(
            configPath, outputPath, gestureSets, testSets));
      addComponent(startButton, SwingTool.createGridBagConstraint(0, 4));
   } // init


   private ScrollableList createList(
         SimpleListModel< ? extends DataObject> listModel) {
      ScrollableList list = new ScrollableList(listModel, 200, 50);
      return list;
   } // createList


   public void gestureSetChanged(EventObject event) {
      gestureSets.setModel(new SimpleListModel<GestureSet>(mainView.getModel()
            .getGestureSets()));
   } // gestureSetChanged


   public void testSetChanged(EventObject event) {
      testSets.setModel(new SimpleListModel<TestSet>(mainView.getModel()
            .getTestSets()));
   } // testSetChanged

}
