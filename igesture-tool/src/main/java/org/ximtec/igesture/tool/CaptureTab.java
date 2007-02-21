/*
 * @(#)CaptureTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Capturing Tab
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

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.frame.algorithm.AlgorithmConfiguration;
import org.ximtec.igesture.tool.utils.GuiFactory;


/**
 * Comment
 * 
 * @version 1.0 Nov 28, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class CaptureTab extends GestureTab {

   private AlgorithmConfiguration algorithmConfiguration;


   public CaptureTab() {
      super();
   }


   @Override
   public String getName() {
      return GestureConstants.GESTUREVIEW_TAB_RECOGNISER;
   }


   @Override
   public void init(GestureToolView mainView) {
      super.init(mainView);

      getDesktopPane()
            .add(GuiFactory.createGestureDrawArea(getMainView(), 0, 0));
      getDesktopPane().add(
            GuiFactory.createAlgorithmFrame(getMainView(), this, 200, 0));
      algorithmConfiguration = GuiFactory.createAlgorithmConfigurationFrame(
            getMainView(), 400, 0);
      getDesktopPane().add(algorithmConfiguration);
   }


   /**
    * Retruns the selected configuration from the "Configuration Frame"
    * 
    * @return
    */
   public Configuration getCurrentConfiguration() {
      return (Configuration) algorithmConfiguration.getCurrentConfiguration()
            .clone();
   }

}
