/*
 * @(#)SampleDescriptorView.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Shows a sample descriptor.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.classlist;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolMain;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.frame.classlist.action.AddSampleAction;
import org.ximtec.igesture.tool.frame.classlist.action.DeleteSampleAction;
import org.ximtec.igesture.tool.util.CloseFrameAction;
import org.ximtec.igesture.tool.util.CustomCellRenderer;
import org.ximtec.igesture.tool.util.GuiConstant;


/**
 * Shows a sample descriptor.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SampleDescriptorFrame extends BasicInternalFrame {

   private GestureToolView mainView;

   private SampleDescriptor descriptor;

   private ScrollableList scrollableList;

   private SampleListModel sampleListModel;


   public SampleDescriptorFrame(GestureToolView mainView,
         SampleDescriptor descriptor) {
      super(GestureConstants.SAMPLE_DESCRIPTOR_KEY, GestureToolMain.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      this.descriptor = descriptor;
      init();
   }


   private void init() {
      addComponent(createList(), SwingTool.createGridBagConstraint(0, 1, 2, 1));
      addComponent(createAddButton(), SwingTool.createGridBagConstraint(0, 0));
      addComponent(createCloseButton(), SwingTool.createGridBagConstraint(1, 0));
      setVisible(true);
   } // init


   private Component createAddButton() {
      return GuiTool.createButton(new AddSampleAction(this));
   } // createAddButton


   private Component createCloseButton() {
      return GuiTool.createButton(new CloseFrameAction(
            GuiConstant.CLOSE_FRAME_ACTION));
   } // createCloseButton


   private Component createList() {
      sampleListModel = new SampleListModel(descriptor.getSamples());
      scrollableList = new ScrollableList(sampleListModel, 180, 480);
      scrollableList.getList().addMouseListener(getMouseListener());
      scrollableList.getList().setCellRenderer(new CustomCellRenderer());
      return scrollableList;
   } // createList


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


   private void showPopupMenu(MouseEvent event) {
      final JPopupMenu popUp = SwingTool
            .createPopupMenu(new Action[] { new DeleteSampleAction(this) });
      popUp.show(event.getComponent(), event.getX(), event.getY());
   } // showPopupMenu


   private GestureSample getSelectedSample() {
      final JPanel panel = (JPanel)scrollableList.getSelectedValue();
      return ((SampleListModel)scrollableList.getList().getModel())
            .getSample(panel);
   } // getSelectedSample


   public void deleteSelectedSample() {
      descriptor.removeSample(getSelectedSample());
      mainView.getModel().updateDataObject(descriptor);
      reloadList();
   } // deleteSelectedSample


   public void addSample() {
      final GestureSample sample = new GestureSample(new Long(System
            .currentTimeMillis()).toString(), mainView.getModel()
            .getCurrentNote());
      descriptor.addSample(sample);
      mainView.getModel().updateDataObject(descriptor);
      reloadList();
   } // addSample


   private void reloadList() {
      scrollableList.setModel(new SampleListModel(descriptor.getSamples()));
   } // reloadList

}
