/*
 * @(#)ClassMatcher.java	1.0   Dec 7, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Instantiated Regex of a gesture class.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 7, 2006		ukurmann	Initial Release
 * Mar 16, 2007     bsigner     Cleanup
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Instantiated Regex of a gesture class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ClassMatcher {

   private String description;

   private Pattern pattern;

   private Parser parser;


   public ClassMatcher(String description) {
      this.description = description;
      init();
   }


   private void init() {
      parser = new Parser(description);
      pattern = parser.getPattern();
   } // init


   /**
    * Tests if a stroke matches the gesture class.
    * @param strokeInfo the stroke to be tested.
    * @return true if there is a match.
    */
   public boolean isMatch(StrokeInfo strokeInfo) {
      final Matcher m = pattern.matcher(strokeInfo.getString());
      final boolean regexMatches = m.matches();
      final boolean constraints = parser.evaluateConstraints(strokeInfo
            .getStatistics());
      return regexMatches && constraints;
   } // isMatch

}
