/*
 * @(#)BatchTab.java    1.0   Nov 28, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 28, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool;

import org.ximtec.igesture.tool.frame.batch.BatchFrame;


/**
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchTab extends GestureTab {

   public BatchTab() {
      super();
   }


   @Override
   public void init(GestureToolView mainView) {
      super.init(mainView);
      getDesktopPane().add(new BatchFrame(mainView));
   } // init


   @Override
   public String getName() {
      return GestureConstants.GESTUREVIEW_TAB_BATCH;
   } // getName

}
