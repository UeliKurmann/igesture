/*
 * @(#)AdminTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Gesture administration tab.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 28, 2006		ukurmann	Initial Release
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


package org.ximtec.igesture.tool.old;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.old.frame.classlist.GestureClassListFrame;
import org.ximtec.igesture.tool.old.frame.classlist.GestureClassViewFrame;
import org.ximtec.igesture.tool.old.frame.gestureset.GestureSetFrame;
import org.ximtec.igesture.tool.old.util.GuiFactory;


/**
 * Gesture administration tab.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AdminTab extends GestureTab {

   private BasicInternalFrame descriptorFrame = null;

   private final GestureClassListFrame gestureClassListFrame = null;

   private GestureClassViewFrame gestureClassViewFrame = null;

   private final GestureSetFrame gestureSetArea = null;


   public AdminTab() {
      super();
   }


   @Override
   public void init(GestureToolView mainView) {
      super.init(mainView);
      getDesktopPane().add(GuiFactory.createGestureDrawArea(mainView, 0, 0));
      getDesktopPane().add(
            GuiFactory.createGestureView(gestureSetArea, mainView, 0, 280));
      getDesktopPane().add(
            GuiFactory.createGestureClassTable(gestureClassListFrame, this, 200,
                  0));
   } // init


   public void addClassView(GestureClass gestureClass) {
      if (descriptorFrame != null) {
         descriptorFrame.dispose();
         descriptorFrame = null;
      }

      if (gestureClassViewFrame != null) {
         gestureClassViewFrame.dispose();
         gestureClassViewFrame = null;
      }

      gestureClassViewFrame = (GestureClassViewFrame)GuiFactory.createClassView(
            this, gestureClass);
      getDesktopPane().add(gestureClassViewFrame);
   } // addClassView


   public void addDescriptorView(Descriptor descriptor) {
      if (descriptorFrame != null) {
         descriptorFrame.dispose();
         descriptorFrame = null;
      }

      descriptorFrame = GuiFactory.createDescriptorView(mainView, descriptor);
      getDesktopPane().add(descriptorFrame);
   } // addDescriptorView


   @Override
   public String getName() {
      return GestureConstants.GESTUREVIEW_TAB_ADMIN;
   } // getName

}
