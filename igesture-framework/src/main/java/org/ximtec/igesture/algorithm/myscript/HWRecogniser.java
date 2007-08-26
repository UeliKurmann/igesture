/*
 * @(#)HWRecogniser.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm.Config;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.ResultSet;


/**
 * MyScript wrapper.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HWRecogniser extends DefaultAlgorithm {

   private Recogniser recogniser;


   public void init(Configuration configuration) {
      recogniser = new Recogniser();
      recogniser.loadResource(MyScript.getResource(MyScript.EN_UK_AK_CUR));
      recogniser.loadResource(MyScript.getResource(MyScript.EN_UK_LK_TEXT));
   } // init


   public ResultSet recognise(Note note) {
      final Note clone = (Note)note.clone();
      final Result result = recogniser.recognise(clone);
      final ResultSet resultSet = new ResultSet();
      resultSet.addResult(new org.ximtec.igesture.core.Result(new GestureClass(
            result.getText()), result.getConfidence()));
      fireEvent(resultSet);
      return resultSet;
   } // recognise


   public Enum[] getConfigParameters() {
      return Config.values();
   } // getConfigParameters

}