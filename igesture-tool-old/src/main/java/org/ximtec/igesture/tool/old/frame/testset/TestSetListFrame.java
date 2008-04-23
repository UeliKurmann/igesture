/*
 * @(#)TestSetListFrame.java    1.0   Nov 15, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Test set list frame.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.old.frame.testset;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.old.GestureConstants;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;
import org.ximtec.igesture.tool.old.event.TestSetListener;
import org.ximtec.igesture.tool.old.frame.classlist.SampleListModel;
import org.ximtec.igesture.tool.old.frame.testset.action.TestSetDeleteAction;
import org.ximtec.igesture.tool.old.frame.testset.action.TestSetDeleteSampleAction;
import org.ximtec.igesture.tool.old.frame.testset.action.TestSetExportAction;
import org.ximtec.igesture.tool.old.frame.testset.action.TestSetImportAction;
import org.ximtec.igesture.tool.old.frame.testset.action.TestSetNewAction;
import org.ximtec.igesture.tool.old.frame.testset.action.TestSetOpenAction;
import org.ximtec.igesture.tool.old.util.CustomCellRenderer;


/**
 * Test set list frame.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetListFrame extends BasicInternalFrame implements
      TestSetListener {

   private GestureToolView mainView;

   private TestSet testSet;

   private ScrollableList scrollableList;

   private ScrollableList testSetList;

   private SampleListModel sampleListModel;


   public TestSetListFrame(GestureToolView mainView, TestSet testSet) {
      super(GestureConstants.TESTSET_VIEW_FRAME_KEY, GestureToolMain.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      mainView.getModel().addTestSetListener(this);
      this.testSet = testSet;
      init();
   }


   private void init() {
      addComponent(createGestureList(), SwingTool.createGridBagConstraint(0, 1,
            2, 1));
      addComponent(createTestSetList(), SwingTool.createGridBagConstraint(0, 0,
            2, 1));
      setVisible(true);
   } // init


   private Component createGestureList() {
      scrollableList = new ScrollableList(null, 180, 420);
      loadGestureListData(testSet);
      scrollableList.getList().setCellRenderer(new CustomCellRenderer());
      scrollableList.getList().addMouseListener(getMouseListener());
      return scrollableList;
   } // createGestureList


   public void loadGestureListData(TestSet testSet) {
      this.testSet = testSet;
     // FIXME sampleListModel = new SampleListModel((List<GestureSample>)testSet.getSamples());
      scrollableList.setModel(sampleListModel);
   } // loadGestureListData


   private Component createTestSetList() {
      testSetList = new ScrollableList(null, 180, 80);
      loadTestSetData();
      testSetList.getList().addMouseListener(getMouseListenerTestSetList());
      return testSetList;
   } // createTestSetList


   public void loadTestSetData() {
      final TestSetListModel listModel = new TestSetListModel(mainView
            .getModel());
      testSetList.setModel(listModel);
   } // loadTestSetData


   private MouseListener getMouseListener() {
      return (new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showPopupMenu(event);
            }
         }
      });

   } // getMouseListener


   private MouseListener getMouseListenerTestSetList() {
      return (new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showTestSetPopupMenu(event);
            }
         }
      });

   } // getMouseListenerTestSetList


   private void showPopupMenu(MouseEvent event) {
      final JPopupMenu popUp = new JPopupMenu();
      popUp.add(new JMenuItem(new TestSetDeleteSampleAction(this)));
      popUp.show(event.getComponent(), event.getX(), event.getY());
   } // showPopupMenu


   private void showTestSetPopupMenu(MouseEvent event) {
      final JPopupMenu popUp = createGestureSetPopUpMenu();
      popUp.show(event.getComponent(), event.getX(), event.getY());
   } // showTestSetPopupMenu


   public void testSetChanged(EventObject event) {
      loadTestSetData();

      if (this.testSet == event.getSource()) {
         loadGestureListData(testSet);
      }

   } // testSetChanged


   private JPopupMenu createGestureSetPopUpMenu() {
      final JPopupMenu menu = SwingTool.createPopupMenu(new Action[] {
            new TestSetOpenAction(testSetList.getList(), this),
            new TestSetNewAction(mainView),
            new TestSetDeleteAction(testSetList.getList(), mainView),
            new TestSetExportAction(testSetList.getList()),
            new TestSetImportAction(mainView) });
      return menu;
   } // createGestureSetPopUpMenu


   private GestureSample getSelectedSample() {
      final JPanel panel = (JPanel)scrollableList.getSelectedValue();
      return (GestureSample)((SampleListModel)scrollableList.getList().getModel())
            .getSample(panel);
   } // getSelectedSample


   public void deleteSelectedSample() {
      testSet.remove(getSelectedSample());
      mainView.getModel().updateDataObject(testSet);
      loadGestureListData(testSet);
   } // deleteSelectedSample


   public void addSample() {
      final GestureSample sample = new GestureSample(new Long(System
            .currentTimeMillis()).toString(), mainView.getModel()
            .getCurrentNote());
      testSet.add(sample);
      mainView.getModel().updateDataObject(testSet);
      loadGestureListData(testSet);
   } // addSample


   /**
    * Returns the test set.
    * 
    * @return the test set.
    */
   public TestSet getTestSet() {
      return testSet;
   } // getTestSet

}
