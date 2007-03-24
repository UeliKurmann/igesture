/*
 * @(#)GestureSetFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Frame showing the gesture sets.
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


package org.ximtec.igesture.tool.frame.gestureset;

import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.GestureSetListener;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionAddGestureClass;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionAddGestureSet;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionCreateTestSet;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionDelGestureClass;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionDelGestureSet;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionExportGestureSet;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionExportIPaperForm;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionExportPDFGestureSet;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionImportGestureSet;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Frame showing the gesture sets.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureSetFrame extends BasicInternalFrame implements
      GestureSetListener {

   private GestureToolView mainView;

   private GestureTreeModel gestureTreeModel;

   private GestureTree gestureTree;


   public GestureSetFrame(GestureToolView mainView) {
      super(GestureConstants.GESTURE_SET_FRAME_KEY, SwingTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      this.gestureTreeModel = new GestureTreeModel(mainView.getModel());
      mainView.getModel().addGestureSetListener(this);
      init();
   }


   /**
    * Initialises the gesture set frame.
    */
   private void init() {
      gestureTree = new GestureTree(gestureTreeModel);
      final JScrollPane pane = new JScrollPane(gestureTree);
      pane.setPreferredSize(new Dimension(180, 180));
      addComponent(pane, SwingTool.createGridBagConstraint(0, 0));
      final PopUpListener popUpListener = new PopUpListener(this);
      gestureTree.addMouseListener(popUpListener);
   } // init


   public JPopupMenu getNewGestureSetPopUp() {
      return SwingTool.createPopupMenu(new Action[] {
            new ActionAddGestureSet(gestureTreeModel),
            new ActionImportGestureSet(mainView) });
   } // getNewGestureSetPopUp


   public JPopupMenu getAddGestureClassPopUp(GestureSet set) {
      return SwingTool.createPopupMenu(new Action[] {
            new ActionAddGestureClass(gestureTreeModel, set),
            new ActionDelGestureSet(gestureTreeModel, set),
            new ActionExportGestureSet(set), new ActionExportPDFGestureSet(set),
            new ActionCreateTestSet(set), new ActionExportIPaperForm(set) });
   } // getAddGestureClassPopUp


   public JPopupMenu getDelGestureClassPopUp(GestureSet set,
         GestureClass gestureClass) {
      return SwingTool.createPopupMenu(new Action[] { new ActionDelGestureClass(
            gestureTreeModel, gestureClass, set) });
   } // getDelGestureClassPopUp


   /**
    * Returns the selected item.
    * @return the selected item.
    */
   public Object getSelectedItem() {
      return gestureTree.getLastSelectedPathComponent();
   } // getSelectedItem


   public void gestureSetChanged(EventObject event) {
      gestureTree.setModel(new GestureTreeModel(mainView.getModel()));
   } // gestureSetChanged

}
