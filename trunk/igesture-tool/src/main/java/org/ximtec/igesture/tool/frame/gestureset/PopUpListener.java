/*
 * @(#)PopUpListener.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   PopUp Listener
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


package org.ximtec.igesture.tool.frame.gestureset;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;


public class PopUpListener extends MouseAdapter {

   /** The UserView this MouseAdapter listens to. */
   private GestureSetFrame gestureView;


   /**
    * The default Constructor.
    * 
    * @param gestureView the UserView to listen to
    */
   public PopUpListener(GestureSetFrame gestureView) {
      this.gestureView = gestureView;
   }


   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {

         // find out witch element was selected when popup was triggered
         final JTree tree = (JTree) e.getSource();
         final TreePath closestPathForLocation = tree.getClosestPathForLocation(
               e.getX(), e.getY());

         if (!tree.isPathSelected(closestPathForLocation)) {
            tree.setSelectionPath(closestPathForLocation);
         }

         /**
          * Root Element (add Gesture Set)
          */
         if (tree.getLastSelectedPathComponent() instanceof GestureTreeModel.rootElement) {
            gestureView.getNewGestureSetPopUp().show(e.getComponent(), e.getX(),
                  e.getY());
         }
         /**
          * GestureSet (add a Gesture Class)
          */
         else if (tree.getLastSelectedPathComponent() instanceof GestureSet) {
            tree.getLastSelectedPathComponent();
            gestureView.getAddGestureClassPopUp(
                  (GestureSet) tree.getLastSelectedPathComponent()).show(
                  e.getComponent(), e.getX(), e.getY());
         }
         else if (tree.getLastSelectedPathComponent() instanceof GestureClass) {
            tree.getLastSelectedPathComponent();
            final TreePath path = tree.getSelectionPath();
            final GestureClass gestureClass = (GestureClass) path
                  .getLastPathComponent();
            final GestureSet gestureSet = (GestureSet) path.getParentPath()
                  .getLastPathComponent();

            gestureView.getDelGestureClassPopUp(gestureSet, gestureClass).show(
                  e.getComponent(), e.getX(), e.getY());
         }

      } // endif event is popup event
   }
}
