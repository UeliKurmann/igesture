/*
 * @(#)GestureClassViewFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Detail view of a gesture class
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;

import org.sigtec.graphix.SimpleListModel;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.GestureClassListener;
import org.ximtec.igesture.tool.frame.classlist.action.ActionDeleteDescriptor;
import org.ximtec.igesture.tool.frame.classlist.action.ActionOpenDescriptorDialog;
import org.ximtec.igesture.tool.util.CloseFrameAction;
import org.ximtec.igesture.tool.util.ScrollableList;
import org.ximtec.igesture.tool.util.SwingTool;


public class GestureClassViewFrame extends BasicInternalFrame implements
      GestureClassListener {

   private ListModel descriptorModel;

   private GestureClass gestureClass;

   private GestureToolView mainView;

   private BasicTextField nameTextField;

   private ScrollableList scrollList;

   private AdminTab adminTab;


   public GestureClassViewFrame(GestureClass gestureClass, AdminTab adminTab) {
      super(GestureConstants.GESTURE_CLASS_VIEW_KEY, SwingTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.gestureClass = gestureClass;
      this.mainView = adminTab.getMainView();
      this.adminTab = adminTab;
      mainView.getModel().addGestureClassListener(this);

      if (this.gestureClass != null) {
         descriptorModel = new SimpleListModel<Descriptor>(gestureClass
               .getDescriptors());
      }
      init();
   }


   private void init() {
      this.addComponent(SwingTool
            .createLabel(GestureConstants.GESTURE_CLASS_VIEW_NAME_LABEL),
            SwingTool.createGridBagConstraint(0, 0));

      this.addComponent(createNameTextField(), SwingTool
            .createGridBagConstraint(1, 0));

      this.addComponent(createList(), SwingTool.createGridBagConstraint(0, 1, 2,
            1));

      this.addComponent(createSaveButton(), SwingTool.createGridBagConstraint(0,
            2));

      this.addComponent(SwingTool.createButton(new CloseFrameAction(
            GestureConstants.COMMON_CANCEL)), SwingTool.createGridBagConstraint(
            1, 2));

      this.setVisible(true);
   }


   private Component createNameTextField() {
      nameTextField = SwingTool
            .createTextField(GestureConstants.GESTURE_CLASS_VIEW_NAME_TEXT);
      nameTextField.setText(gestureClass.getName());
      return nameTextField;
   }


   private Component createList() {
      scrollList = SwingTool.createScrollableList(descriptorModel, 180, 140);

      scrollList.getList().addMouseListener(new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showPopUpMenu(event);
            }
         }
      });

      return scrollList;
   }


   private Component createSaveButton() {
      final BasicButton button = SwingTool
            .createButton(GestureConstants.COMMON_SAVE);
      button.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent event) {
            gestureClass.setName(nameTextField.getText());
            mainView.getModel().addGestureClass(gestureClass);
         }
      });
      return button;
   }


   private void showPopUpMenu(MouseEvent e) {
      final JPopupMenu popupMenu = new JPopupMenu();

      final JMenu createMenu = SwingTool.createMenu(SwingTool.getGuiBundle()
            .getName(GestureConstants.COMMON_ADD), new Action[] {
            new ActionOpenDescriptorDialog(
                  GestureConstants.GESTURE_CLASS_VIEW_ADD_SAMPLE, adminTab,
                  gestureClass, null, SampleDescriptor.class),

            new ActionOpenDescriptorDialog(
                  GestureConstants.GESTURE_CLASS_VIEW_ADD_TEXT, adminTab,
                  gestureClass, null, TextDescriptor.class) });

      popupMenu.add(createMenu);
      popupMenu.add(SwingTool.createMenuItem(new ActionOpenDescriptorDialog(
            GestureConstants.COMMON_EDIT, adminTab, gestureClass,
            (Descriptor) scrollList.getList().getSelectedValue(), null)));

      popupMenu.add(SwingTool.createMenuItem(new ActionDeleteDescriptor(
            GestureConstants.COMMON_DEL, mainView, gestureClass,
            (Descriptor) scrollList.getList().getSelectedValue())));

      popupMenu.show(e.getComponent(), e.getX(), e.getY());
   }


   public void gestureClassChanged(EventObject event) {
      scrollList.getList().setModel(
            new SimpleListModel<Descriptor>(gestureClass.getDescriptors()));
   }


   @Override
   public void dispose() {
      this.setVisible(false);
      super.dispose();
   }

}
