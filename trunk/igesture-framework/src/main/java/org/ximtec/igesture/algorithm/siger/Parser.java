/*
 * @(#)Parser.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :	Parses the textual description of a gesture class 	
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
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

import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class Parser {

	/**
	 * Tokens of SiGeR's description language
	 * 
	 */
   enum Tokens {
      N, NE, E, SE, S, SW, W, NW, STRAIGHT, DIAGONAL, PROXIMITY;
   }

   /**
    * Operators of Sigers descritpion language
    * @author kurmannu
    *
    */
   public enum Operator {
      EQ, GT, LT, GTE, LTE;
   }

   /**
    * Boolean operators
    * TODO: other operators should be implemented to enhance the description language
    */
   public enum BooleanOperators {
      AND;
   }

   private String expression;

   private String regexExpression;

   private String constraintExpression;

   private Pattern pattern;


   /**
    * Parses an string containing a description
    * @param expression
    */
   public Parser(String expression) {

      this.expression = expression.trim();
      if (this.expression.indexOf(";") > 0) {
         regexExpression = this.expression.substring(0, this.expression
               .indexOf(";"));
         constraintExpression = this.expression.substring(this.expression
               .indexOf(";") + 1);
      }
      else {
         regexExpression = this.expression;
         constraintExpression = "";
      }
   }


   /**
    * Returns the pattern 
    * @return the pattern
    */
   public Pattern getPattern() {
      if (pattern == null) {
         final StringTokenizer tokenizer = new StringTokenizer(regexExpression,
               ",");
         final StringBuilder sb = new StringBuilder();
         sb.append(Constants.START);
         while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            final Direction d = Direction.parse(token);
            if (d != null) {
               sb.append(Constants.getRegex(d));
            }
         }
         sb.append(Constants.END);
         pattern = Pattern.compile(sb.toString());
      }
      return pattern;
   }


   /**
    * Evaluates the constraints
    * @param strokeStatistic the stroke statistics
    * @return true if the constraint is satisfied
    */
   public boolean evaluateConstraints(Statistics strokeStatistic) {
      final String tmpExpression = constraintExpression.replace(
            BooleanOperators.AND.name(), "#");

      final StringTokenizer expressionTokenizer = new StringTokenizer(
            tmpExpression, "#");
      while (expressionTokenizer.hasMoreTokens()) {
         final String thisExpression = expressionTokenizer.nextToken().trim();
         final StringTokenizer tokenizer = new StringTokenizer(thisExpression);
         if (tokenizer.countTokens() == 3) {

            final double left = parseDoubleToken(tokenizer.nextToken().trim(),
                  strokeStatistic);
            final Operator op = Operator.valueOf(tokenizer.nextToken().trim());
            final double right = parseDoubleToken(tokenizer.nextToken().trim(),
                  strokeStatistic);
            if (!evaluateComparation(op, left, right)) {
               return false;
            }
         }
      }
      return true;
   }


   /**
    * Parses a double token
    * @param s a string holding the double
    * @param statistic the statistic
    * @return the double value
    */
   private double parseDoubleToken(String s, Statistics statistic) {
      final double d = 0;
      if (isDouble(s)) {
         return Double.parseDouble(s);
      }
      else {
         final Tokens t = Tokens.valueOf(s);
         if (t != null) {
            return evaluateFunction(t, statistic);
         }
      }
      return d;
   }


   /**
    * Returns true if the input represents a double value
    * @param s
    * @return
    */
   private static boolean isDouble(String s) {
      try {
         Double.parseDouble(s);
         return true;
      }
      catch (final Exception e) {
         return false;
      }
   }


   /**
    * Evaluates the given function
    * @param token
    * @param strokeStatistic
    * @return
    */
   public static double evaluateFunction(Tokens token, Statistics strokeStatistic) {
      switch (token) {
         case N:
            return strokeStatistic.getNorth();
         case NE:
            return strokeStatistic.getNorthEast();
         case E:
            return strokeStatistic.getEast();
         case SE:
            return strokeStatistic.getSouthEast();
         case S:
            return strokeStatistic.getSouth();
         case SW:
            return strokeStatistic.getSouthWest();
         case W:
            return strokeStatistic.getWest();
         case NW:
            return strokeStatistic.getNorthWest();
         case STRAIGHT:
            return strokeStatistic.getStraight();
         case DIAGONAL:
            return strokeStatistic.getDiagonal();
         case PROXIMITY:
            return strokeStatistic.getProximity();
      }
      return 0;
   }


   /**
    * Evaluates a comparaison
    * @param operator
    * @param left
    * @param right
    * @return true if the condition is hold
    */
   public static boolean evaluateComparation(Operator operator, double left,
         double right) {

      switch (operator) {
         case EQ:
            return left == right;
         case GT:
            return left > right;
         case GTE:
            return left >= right;
         case LT:
            return left < right;
         case LTE:
            return left <= right;
      }
      return false;
   }

}
