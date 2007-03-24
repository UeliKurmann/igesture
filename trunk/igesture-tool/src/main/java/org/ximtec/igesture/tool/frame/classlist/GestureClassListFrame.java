/*
 * @(#)GestureClassListFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Gesture Class List Frame
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
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;

import org.sigtec.graphix.SimpleListModel;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.event.GestureClassListener;
import org.ximtec.igesture.tool.frame.classlist.action.ActionDeleteClass;
import org.ximtec.igesture.tool.frame.classlist.action.ActionOpenEditClassFrame;
import org.ximtec.igesture.tool.util.ScrollableList;
import org.ximtec.igesture.tool.util.SwingTool;


public class GestureClassListFrame extends BasicInternalFrame implements
      GestureClassListener {

   private ScrollableList scrollableList;

   private AdminTab adminTab;


   public GestureClassListFrame(AdminTab adminTab) {
      super(GestureConstants.GESTURE_CLASS_LIST_FRAME_KEY, SwingTool
            .getGuiBundle());
      SwingTool.initFrame(this);
      this.adminTab = adminTab;
      adminTab.getMainView().getModel().addGestureClassListener(this);
      init();
   }


   private void init() {
      this.addComponent(SwingTool
            .createLabel(GestureConstants.GESTURE_CLASS_LIST_FRAME_TITLE),
            SwingTool.createGridBagConstraint(0, 0, 2, 1));

      this.addComponent(createList(), SwingTool.createGridBagConstraint(0, 1, 2,
            1));
   }


   private Component createList() {
      final ListModel listModel = new SimpleListModel<GestureClass>(adminTab
            .getMainView().getModel().getGestureClasses());

      scrollableList = new ScrollableList(listModel, 180, 160);
      scrollableList.getList().addMouseListener(new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showPopUpMenu(event);
            }
         }
      });
      return scrollableList;
   }


   private void showPopUpMenu(MouseEvent e) {
      final JPopupMenu popupMenu = SwingTool.createPopupMenu(new Action[] {
            new ActionOpenEditClassFrame(
                  GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_ADD, adminTab,
                  null),

            new ActionOpenEditClassFrame(
                  GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_EDIT, adminTab,
                  (GestureClass) scrollableList.getSelectedValue()),

            new ActionDeleteClass(
                  GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_DEL, adminTab
                        .getMainView(), (GestureClass) scrollableList
                        .getSelectedValue()) });

      popupMenu.show(e.getComponent(), e.getX(), e.getY());
   }


   public void gestureClassChanged(EventObject event) {
      scrollableList.setModel(new SimpleListModel<GestureClass>(adminTab
            .getMainView().getModel().getGestureClasses()));
   }
}
