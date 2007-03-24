/*
 * @(#)GuiFactory.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Static methods to create GUI components.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 28, 2006		ukurmann	Initial Release
 * Feb 26, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.CaptureTab;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.TestTab;
import org.ximtec.igesture.tool.frame.algorithm.AlgorithmConfiguration;
import org.ximtec.igesture.tool.frame.algorithm.AlgorithmFrame;
import org.ximtec.igesture.tool.frame.capture.GestureCaptureArea;
import org.ximtec.igesture.tool.frame.classlist.GestureClassListFrame;
import org.ximtec.igesture.tool.frame.classlist.GestureClassViewFrame;
import org.ximtec.igesture.tool.frame.classlist.SampleDescriptorFrame;
import org.ximtec.igesture.tool.frame.classlist.TextDescriptorFrame;
import org.ximtec.igesture.tool.frame.gestureset.GestureSetFrame;
import org.ximtec.igesture.tool.frame.testset.TestSetFrame;
import org.ximtec.igesture.tool.frame.testset.TestSetListFrame;


/**
 * Static methods to create GUI components.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GuiFactory {

   public static BasicInternalFrame createGestureDrawArea(
         GestureToolView mainView, int x, int y) {
      final GestureCaptureArea gestureDrawArea = new GestureCaptureArea(mainView);
      gestureDrawArea.setBounds(x, y, 200, 280);
      return gestureDrawArea;
   } // createGestureDrawArea


   public static BasicInternalFrame createGestureView(
         GestureSetFrame gestureSetArea, GestureToolView mainView, int x, int y) {
      if (gestureSetArea != null) {
         gestureSetArea.setVisible(false);
      }

      gestureSetArea = new GestureSetFrame(mainView);
      gestureSetArea.setBounds(x, y, 200, 280);
      gestureSetArea.setResizable(false);
      return gestureSetArea;
   } // createGestureView


   public static BasicInternalFrame createTestView(TestTab testTab, int x, int y) {
      TestSetFrame testFrame = null;

      if (testFrame != null) {
         testFrame.setVisible(false);
      }

      testFrame = new TestSetFrame(testTab);
      testFrame.setBounds(x, y, 200, 280);
      testFrame.setResizable(false);
      return testFrame;
   } // createTestView


   public static BasicInternalFrame createGestureClassTable(
         GestureClassListFrame gestureClassListFrame, AdminTab adminTab, int x,
         int y) {
      if (gestureClassListFrame != null) {
         gestureClassListFrame.setVisible(false);
      }

      final GestureClassListFrame table = new GestureClassListFrame(adminTab);
      table.setBounds(x, y, 200, 280);
      return table;
   } // createGestureClassTable


   public static BasicInternalFrame createAlgorithmFrame(
         GestureToolView mainView, CaptureTab tab, int x, int y) {
      final AlgorithmFrame frame = new AlgorithmFrame(mainView, tab);
      frame.setBounds(x, y, 200, 400);
      return frame;
   } // createAlgorithmFrame


   public static BasicInternalFrame createClassView(AdminTab adminTab,
         GestureClass gestureClass) {
      final GestureClassViewFrame frame = new GestureClassViewFrame(
            gestureClass, adminTab);
      frame.setBounds(200, 280, 200, 280);
      return frame;
   } // createClassView


   public static BasicInternalFrame createTestSetList(GestureToolView mainView,
         TestSet testSet, int x, int y) {

      final TestSetListFrame testSetListFrame = new TestSetListFrame(mainView,
            testSet);
      testSetListFrame.setBounds(x, y, 200, 560);
      testSetListFrame.setResizable(false);
      return testSetListFrame;
   } // createTestSetList


   public static BasicInternalFrame createDescriptorView(
         GestureToolView mainView, Descriptor descriptor) {
      BasicInternalFrame frame = null;

      if (descriptor instanceof TextDescriptor) {
         frame = new TextDescriptorFrame(mainView, (TextDescriptor)descriptor);
      }
      else if (descriptor instanceof SampleDescriptor) {
         frame = new SampleDescriptorFrame(mainView,
               (SampleDescriptor)descriptor);
      }

      frame.setBounds(400, 0, 200, 560);
      return frame;
   } // createDescriptorView


   public static AlgorithmConfiguration createAlgorithmConfigurationFrame(
         GestureToolView mainView, int x, int y) {
      final AlgorithmConfiguration algorithmConfiguration = new AlgorithmConfiguration(
            mainView);
      algorithmConfiguration.setBounds(x, y, 400, 560);
      return algorithmConfiguration;
   } // createAlgorithmConfigurationFrame

}
