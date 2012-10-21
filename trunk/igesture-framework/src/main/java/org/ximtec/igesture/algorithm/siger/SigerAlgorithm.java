/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Implementation of the Siger algortithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 6, 2006		ukurmann	Initial Release
 * Mar 16, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.siger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.DefaultAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.util.Constant;


/**
 * Implementation of the Siger algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class SigerAlgorithm extends DefaultAlgorithm {

   private HashMap<ClassMatcher, GestureClass> gestures;

   private static String DEFAULT_MIN_DISTANCE = "5";
   
   public enum Config {
      MIN_DISTANCE
   }
   
   static{
	   /**
	    * Parameter Default Values
	    */
	   DEFAULT_CONFIGURATION.put(Config.MIN_DISTANCE.name(), DEFAULT_MIN_DISTANCE);
   }


   public SigerAlgorithm() {
   }


   public void init(Configuration configuration) throws AlgorithmException {
      gestures = new HashMap<ClassMatcher, GestureClass>();
      final GestureSet gestureSet = configuration.getGestureSet();
      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
         final TextDescriptor descriptor = gestureClass
               .getDescriptor(TextDescriptor.class);
         gestures.put(new ClassMatcher(descriptor.getText()), gestureClass);
      }

   } // init


   public Config[] getConfigParameters() {
      return Config.values();
   } // getConfigParameters


   public ResultSet recognise(Gesture<?> gesture) {
      ResultSet result = new ResultSet();

      if (gesture instanceof GestureSample) {
         Note note = ((GestureSample)gesture).getGesture();

         StrokeInfo si = new StrokeInfo(note);
         List<Result> resultList = new ArrayList<Result>();
         result.setGesture(gesture);

         for (final ClassMatcher regex : gestures.keySet()) {
            if (regex.isMatch(si)) {
               resultList.add(new Result(gestures.get(regex), 1));
            }
         }

         for (final Result r : resultList) {
            r.setAccuracy((double)1 / resultList.size());
            result.addResult(r);
         }
      }

      return result;
   } // recognise

	@Override
	public int getType() {
		return Constant.TYPE_2D;
	}

}
