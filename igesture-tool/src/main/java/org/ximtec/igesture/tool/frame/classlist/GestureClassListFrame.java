/*
 * @(#)GestureClassListFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Gesture class list frame.
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


package org.ximtec.igesture.tool.frame.classlist;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.SimpleListModel;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.event.GestureClassListener;
import org.ximtec.igesture.tool.frame.classlist.action.ActionDeleteClass;
import org.ximtec.igesture.tool.frame.classlist.action.ActionOpenEditClassFrame;


/**
 * Gesture class list frame.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureClassListFrame extends BasicInternalFrame implements
      GestureClassListener {

   private ScrollableList scrollableList;

   private AdminTab adminTab;


   public GestureClassListFrame(AdminTab adminTab) {
      super(GestureConstants.GESTURE_CLASS_LIST_FRAME_KEY, GuiTool
            .getGuiBundle());
      SwingTool.initFrame(this);
      this.adminTab = adminTab;
      adminTab.getMainView().getModel().addGestureClassListener(this);
      init();
   }


   private void init() {
      addComponent(SwingTool
            .createLabel(GestureConstants.GESTURE_CLASS_LIST_FRAME_TITLE),
            SwingTool.createGridBagConstraint(0, 0, 2, 1));
      addComponent(createList(), SwingTool.createGridBagConstraint(0, 1, 2, 1));
   } // init


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
   } // createList


   private void showPopUpMenu(MouseEvent e) {
      final JPopupMenu popupMenu = SwingTool.createPopupMenu(new Action[] {
            new ActionOpenEditClassFrame(
                  GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_ADD, adminTab,
                  null),
            new ActionOpenEditClassFrame(
                  GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_EDIT, adminTab,
                  (GestureClass)scrollableList.getSelectedValue()),
            new ActionDeleteClass(
                  GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_DEL, adminTab
                        .getMainView(), (GestureClass)scrollableList
                        .getSelectedValue()) });
      popupMenu.show(e.getComponent(), e.getX(), e.getY());
   } // showPopUpMenu


   public void gestureClassChanged(EventObject event) {
      scrollableList.setModel(new SimpleListModel<GestureClass>(adminTab
            .getMainView().getModel().getGestureClasses()));
   } // gestureClassChanged

}
