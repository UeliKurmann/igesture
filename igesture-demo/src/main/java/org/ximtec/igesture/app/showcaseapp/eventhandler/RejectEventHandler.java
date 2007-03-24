/*
 * @(#)RejectEventHandler.java 1.0   Nov 24, 2006
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
 * Nov 24, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.app.showcaseapp.eventhandler;

import org.sigtec.sound.SoundTool;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class RejectEventHandler implements EventHandler {

   private static final String RESOURCES_SOUND_RINGOUT_WAV = "resources/sound/ringout.wav";
   private String filename;


   public RejectEventHandler(String filename) {
      this.filename = filename;
   }


   public RejectEventHandler() {
      this(RESOURCES_SOUND_RINGOUT_WAV);
   }


   public void run(ResultSet resultSet) {
      SoundTool.play(filename);
   } // run

}
