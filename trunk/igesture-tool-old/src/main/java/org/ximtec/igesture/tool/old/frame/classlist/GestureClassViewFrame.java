/*
 * @(#)GestureClassViewFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Detail view of a gesture class.
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


package org.ximtec.igesture.tool.old.frame.classlist;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.SimpleListModel;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.old.AdminTab;
import org.ximtec.igesture.tool.old.GestureConstants;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;
import org.ximtec.igesture.tool.old.event.GestureClassListener;
import org.ximtec.igesture.tool.old.frame.classlist.action.ActionOpenDescriptorDialog;
import org.ximtec.igesture.tool.old.frame.classlist.action.DeleteDescriptorAction;
import org.ximtec.igesture.tool.old.util.CloseFrameAction;
import org.ximtec.igesture.tool.old.util.GuiConstant;


/**
 * Detail view of a gesture class.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureClassViewFrame extends BasicInternalFrame implements
      GestureClassListener {

   private ListModel descriptorModel;

   private GestureClass gestureClass;

   private GestureToolView mainView;

   private BasicTextField nameTextField;

   private ScrollableList scrollList;

   private AdminTab adminTab;


   public GestureClassViewFrame(GestureClass gestureClass, AdminTab adminTab) {
      super(GestureConstants.GESTURE_CLASS_VIEW_KEY, GestureToolMain.getGuiBundle());
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
      this.addComponent(GuiTool.createLabel(
            GestureConstants.GESTURE_CLASS_VIEW_NAME_LABEL, GestureToolMain
                  .getGuiBundle()), SwingTool.createGridBagConstraint(0, 0));
      addComponent(createNameTextField(), SwingTool
            .createGridBagConstraint(1, 0));
      addComponent(createList(), SwingTool.createGridBagConstraint(0, 1, 2, 1));
      addComponent(createSaveButton(), SwingTool.createGridBagConstraint(0, 2));
      addComponent(GuiTool.createButton(new CloseFrameAction(
            GuiConstant.CANCEL_FRAME_ACTION)), SwingTool
            .createGridBagConstraint(1, 2));
      setVisible(true);
   } // init


   private Component createNameTextField() {
      nameTextField = GuiTool.createTextField(
            GestureConstants.GESTURE_CLASS_VIEW_NAME_TEXT, GestureToolMain
                  .getGuiBundle());
      nameTextField.setText(gestureClass.getName());
      return nameTextField;
   } // createNameTextField


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
   } // createList


   private Component createSaveButton() {
      final JButton button = GuiTool.createButton(GestureConstants.COMMON_SAVE,
            GestureToolMain.getGuiBundle());
      button.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent event) {
            gestureClass.setName(nameTextField.getText());
            mainView.getModel().addGestureClass(gestureClass);
         }
      });

      return button;
   } // createSaveButton


   private void showPopUpMenu(MouseEvent e) {
      final JPopupMenu popupMenu = new JPopupMenu();
      final JMenu createMenu = SwingTool.createMenu(GestureToolMain.getGuiBundle()
            .getName(GestureConstants.COMMON_ADD), new Action[] {
            new ActionOpenDescriptorDialog(
                  GestureConstants.GESTURE_CLASS_VIEW_ADD_SAMPLE, adminTab,
                  gestureClass, null, SampleDescriptor.class),

            new ActionOpenDescriptorDialog(
                  GestureConstants.GESTURE_CLASS_VIEW_ADD_TEXT, adminTab,
                  gestureClass, null, TextDescriptor.class) });
      popupMenu.add(createMenu);
      popupMenu.add(new JMenuItem(new ActionOpenDescriptorDialog(
            GestureConstants.COMMON_EDIT, adminTab, gestureClass,
            (Descriptor)scrollList.getList().getSelectedValue(), null)));
      popupMenu.add(new JMenuItem(new DeleteDescriptorAction(mainView,
            gestureClass, (Descriptor)scrollList.getList().getSelectedValue())));
      popupMenu.show(e.getComponent(), e.getX(), e.getY());
   } // showPopUpMenu


   public void gestureClassChanged(EventObject event) {
      scrollList.getList().setModel(
            new SimpleListModel<Descriptor>(gestureClass.getDescriptors()));
   } // gestureClassChanged


   @Override
   public void dispose() {
      this.setVisible(false);
      super.dispose();
   } // dispose

}
