/*
 * @(#)AdminTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Gesture Administration Tab
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.frame.classlist.GestureClassListFrame;
import org.ximtec.igesture.tool.frame.classlist.GestureClassViewFrame;
import org.ximtec.igesture.tool.frame.gestureset.GestureSetFrame;
import org.ximtec.igesture.tool.utils.GuiFactory;


/**
 * Comment
 * 
 * @version 1.0 Nov 28, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
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

   }


   public void addClassView(GestureClass gestureClass) {
      if (descriptorFrame != null) {
         descriptorFrame.dispose();
         descriptorFrame = null;
      }
      if (gestureClassViewFrame != null) {
         gestureClassViewFrame.dispose();
         gestureClassViewFrame = null;
      }

      gestureClassViewFrame = (GestureClassViewFrame) GuiFactory
            .createClassView(this, gestureClass);
      getDesktopPane().add(gestureClassViewFrame);
   }


   public void addDescriptorView(Descriptor descriptor) {
      if (descriptorFrame != null) {
         descriptorFrame.dispose();
         descriptorFrame = null;
      }
      descriptorFrame = GuiFactory.createDescriptorView(mainView, descriptor);
      getDesktopPane().add(descriptorFrame);
   }


   @Override
   public String getName() {
      return GestureConstants.GESTUREVIEW_TAB_ADMIN;
   }

}
