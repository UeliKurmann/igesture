/*
 * @(#)ActionOpenDescriptorDialog.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Opens the descriptor frame.
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


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Opens the descriptor frame.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionOpenDescriptorDialog extends BasicAction {

   private Descriptor descriptor;

   private GestureClass gestureClass;

   private GestureToolView mainView;

   private AdminTab adminTab;

   private Class< ? extends Descriptor> descriptorType;


   public ActionOpenDescriptorDialog(String key, AdminTab adminTab,
         GestureClass gestureClass, Descriptor descriptor,
         Class< ? extends Descriptor> descriptorType) {
      super(key, SwingTool.getGuiBundle());

      if (GestureConstants.COMMON_EDIT.equals(key)) {
         putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_OPEN));
      }
      else {
         putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      }

      this.mainView = adminTab.getMainView();
      this.descriptor = descriptor;
      this.gestureClass = gestureClass;
      this.adminTab = adminTab;
      this.descriptorType = descriptorType;
   }


   public void actionPerformed(ActionEvent event) {
      if (descriptor == null) {

         if (descriptorType.equals(SampleDescriptor.class)) {
            descriptor = new SampleDescriptor();
         }
         else if (descriptorType.equals(TextDescriptor.class)) {
            descriptor = new TextDescriptor(TextDescriptor.class.getSimpleName());
         }

         gestureClass.addDescriptor(descriptor);
         mainView.getModel().updateDataObject(gestureClass);
      }

      adminTab.addDescriptorView(descriptor);
   } // actionPerformed

}
