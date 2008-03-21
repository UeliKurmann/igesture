/*
 * @(#)PopUpListener.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Pop-up listener.
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


package org.ximtec.igesture.tool.explorer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;


/**
 * Pop-up listener.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ExplorerPopup extends MouseAdapter {

   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {

         // lookup selected tree node
         JTree tree = (JTree)e.getSource();
         TreePath closestPathForLocation = tree.getClosestPathForLocation(e.getX(), e.getY());

         // select node
         if (!tree.isPathSelected(closestPathForLocation)) {
            tree.setSelectionPath(closestPathForLocation);
         }
         
         System.out.println(tree.getLastSelectedPathComponent());
         
         // show popup menu
         
      }

   } // mouseReleased

}
