/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	MyScript wrapper.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 15, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.myscript;

import org.sigtec.ink.Note;
import org.sigtec.ink.recognition.MyScript;
import org.sigtec.ink.recognition.Recogniser;
import org.sigtec.ink.recognition.Result;
import org.ximtec.igesture.algorithm.DefaultAlgorithm;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration.Config;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.ResultSet;


/**
 * MyScript wrapper.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HWRecogniser extends DefaultAlgorithm {

   private Recogniser recogniser;


   public void init(Configuration configuration) {
      recogniser = new Recogniser();
      recogniser.loadResource(MyScript.getResource(MyScript.EN_UK_AK_CUR));
      recogniser.loadResource(MyScript.getResource(MyScript.EN_UK_LK_TEXT));
   } // init


   public ResultSet recognise(Gesture< ? > gesture) {
      ResultSet resultSet = new ResultSet();

      if (gesture instanceof GestureSample) {
         Note note = ((GestureSample)gesture).getGesture();
         Note clone = (Note)note.clone();
         Result result = recogniser.recognise(clone);
         resultSet.addResult(new org.ximtec.igesture.core.Result(
               new GestureClass(result.getText()), result.getConfidence()));
      }

      return resultSet;
   } // recognise


   public Config[] getConfigParameters() {
      return RubineConfiguration.Config.values();
   } // getConfigParameters

}
