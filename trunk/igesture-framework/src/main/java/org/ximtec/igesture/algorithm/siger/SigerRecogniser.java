/*
 * @(#)SigerRecogniser.java	1.0   Dec 6, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Implementation of the Siger algortithm
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.siger;

import java.util.HashMap;

import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.DefaultAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.TextDescriptor;


/**
 * Comment
 * 
 * @version 1.0 Dec 6, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class SigerRecogniser extends DefaultAlgorithm {

   private HashMap<ClassMatcher, GestureClass> gestures;

   public enum Config {
      MIN_DISTANCE
   }


   public SigerRecogniser() {

   }


   public void init(Configuration configuration) throws AlgorithmException {
      gestures = new HashMap<ClassMatcher, GestureClass>();
      final GestureSet gestureSet = configuration.getGestureSet();
      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
         final TextDescriptor descriptor = gestureClass
               .getDescriptor(TextDescriptor.class);
         gestures.put(new ClassMatcher(descriptor.getText()), gestureClass);
      }
   }


   public Enum[] getConfigParameters() {
      return Config.values();
   }


   public ResultSet recognise(Note note) {
      final StrokeInfo si = new StrokeInfo(note);
      final ResultSet result = new ResultSet();
      result.setNote(note);
      for (final ClassMatcher regex : gestures.keySet()) {
         if (regex.isMatch(si)) {
            result.add(new Result(gestures.get(regex), 1));
         }
      }

      for (final Result r : result.getResults()) {
         r.setAccuracy((double) 1 / result.size());
      }

      fireEvent(result);

      return result;
   }

}
