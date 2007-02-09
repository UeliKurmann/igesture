/*
 * @(#)ClassMatcher.java	1.0   Dec 7, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Instantiated Regex of a gesture class
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


package org.ximtec.igesture.algorithm.siger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Comment
 * 
 * @version 1.0 Dec 7, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
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
   }


   public boolean isMatch(StrokeInfo strokeInfo) {
      final Matcher m = pattern.matcher(strokeInfo.getString());
      final boolean regexMatches = m.matches();
      final boolean constraints = parser.evaluateConstraints(strokeInfo
            .getStatistics());

      return regexMatches && constraints;
   }
}
