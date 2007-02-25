/*
 * @(#)SampleDescriptorView.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Shows a sample descriptor
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


package org.ximtec.igesture.tool.frame.classlist;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.frame.classlist.action.ActionAddSample;
import org.ximtec.igesture.tool.frame.classlist.action.ActionDeleteSample;
import org.ximtec.igesture.tool.util.CloseFrameAction;
import org.ximtec.igesture.tool.util.CustomCellRenderer;
import org.ximtec.igesture.tool.util.ScrollableList;
import org.ximtec.igesture.tool.util.SwingTool;


public class SampleDescriptorFrame extends BasicInternalFrame {

   private GestureToolView mainView;

   private SampleDescriptor descriptor;

   private ScrollableList scrollableList;

   private SampleListModel sampleListModel;


   public SampleDescriptorFrame(GestureToolView mainView,
         SampleDescriptor descriptor) {
      super(GestureConstants.SAMPLE_DESCRIPTOR_KEY, SwingTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      this.descriptor = descriptor;
      initialize();
   }


   private void initialize() {

      addComponent(createList(), SwingTool.createGridBagConstraint(0, 1, 2, 1));

      addComponent(createAddButton(), SwingTool.createGridBagConstraint(0, 0));
      addComponent(createCloseButton(), SwingTool.createGridBagConstraint(1, 0));

      this.setVisible(true);
   }


   private Component createAddButton() {
      return SwingTool.createButton(new ActionAddSample(
            GestureConstants.COMMON_ADD, this));
   }


   private Component createCloseButton() {
      return SwingTool.createButton(new CloseFrameAction(
            GestureConstants.COMMON_CLOSE));
   }


   private Component createList() {
      sampleListModel = new SampleListModel(descriptor.getSamples());
      scrollableList = new ScrollableList(sampleListModel, 180, 480);
      scrollableList.getList().addMouseListener(getMouseListener());
      scrollableList.getList().setCellRenderer(new CustomCellRenderer());
      return scrollableList;
   }


   private MouseListener getMouseListener() {
      return (new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showPopupMenu(event);
            }
         }
      });
   }


   private void showPopupMenu(MouseEvent event) {
      final JPopupMenu popUp = SwingTool
            .createPopupMenu(new Action[] { new ActionDeleteSample(
                  GestureConstants.COMMON_DEL, this) });

      popUp.show(event.getComponent(), event.getX(), event.getY());
   }


   private GestureSample getSelectedSample() {
      final JPanel panel = (JPanel) scrollableList.getSelectedValue();
      return ((SampleListModel) scrollableList.getList().getModel())
            .getSample(panel);
   }


   public void deleteSelectedSample() {
      descriptor.removeSample(getSelectedSample());
      mainView.getModel().updateDataObject(descriptor);
      reloadList();
   }


   public void addSample() {
      final GestureSample sample = new GestureSample(new Long(System
            .currentTimeMillis()).toString(), mainView.getModel()
            .getCurrentNote());

      descriptor.addSample(sample);
      mainView.getModel().updateDataObject(descriptor);
      reloadList();
   }


   private void reloadList() {
      scrollableList.setModel(new SampleListModel(descriptor.getSamples()));
   }
}
