/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :	Parses the textual description of a gesture class.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
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

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.sigtec.util.Constant;


/**
 * Parses the textual description of a gesture class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Parser {

   /**
    * Tokens of SiGeR's description language.
    * 
    */
   enum Tokens {
      N, NE, E, SE, S, SW, W, NW, STRAIGHT, DIAGONAL, PROXIMITY;
   }

   /**
    * Operators of Siger's description language.
    * 
    */
   public enum Operator {
      EQ, GT, LT, GTE, LTE;
   }

   /**
    * Boolean operators.
    */
   // TODO: other operators should be implemented to enhance the description
   // language
   public enum BooleanOperators {
      AND;
   }

   private String expression;

   private String regexExpression;

   private String constraintExpression;

   private Pattern pattern;


   /**
    * Parses a string containing a description.
    * @param expression the string to be parsed.
    */
   public Parser(String expression) {
      this.expression = expression.trim();

      if (this.expression.indexOf(Constant.SEMICOLON) > 0) {
         regexExpression = this.expression.substring(0, this.expression
               .indexOf(Constant.SEMICOLON));
         constraintExpression = this.expression.substring(this.expression
               .indexOf(Constant.SEMICOLON) + 1);
      }
      else {
         regexExpression = this.expression;
         constraintExpression = Constant.EMPTY_STRING;
      }

   }


   /**
    * Returns the pattern.
    * @return the pattern.
    */
   public Pattern getPattern() {
      if (pattern == null) {
         final StringTokenizer tokenizer = new StringTokenizer(regexExpression,
               Constant.COMMA);
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
   } // getPattern


   /**
    * Evaluates the constraints.
    * @param strokeStatistic the stroke statistics.
    * @return true if the constraint is satisfied.
    */
   public boolean evaluateConstraints(Statistics strokeStatistic) {
      final String tmpExpression = constraintExpression.replace(
            BooleanOperators.AND.name(), Constant.HASH);
      final StringTokenizer expressionTokenizer = new StringTokenizer(
            tmpExpression, Constant.HASH);

      while (expressionTokenizer.hasMoreTokens()) {
         final String thisExpression = expressionTokenizer.nextToken().trim();
         final StringTokenizer tokenizer = new StringTokenizer(thisExpression);

         if (tokenizer.countTokens() == 3) {
            final double left = parseDoubleToken(tokenizer.nextToken().trim(),
                  strokeStatistic);
            final Operator op = Operator.valueOf(tokenizer.nextToken().trim());
            final double right = parseDoubleToken(tokenizer.nextToken().trim(),
                  strokeStatistic);

            if (!evaluateComparison(op, left, right)) {
               return false;
            }

         }

      }
      return true;
   } // evaluateConstraints


   /**
    * Parses a double token.
    * @param token a string holding the double.
    * @param statistic the statistic.
    * @return the double value.
    */
   private double parseDoubleToken(String token, Statistics statistic) {
      final double d = 0;

      if (isDouble(token)) {
         return Double.parseDouble(token);
      }
      else {
         final Tokens t = Tokens.valueOf(token);

         if (t != null) {
            return evaluateFunction(t, statistic);
         }

      }
      return d;
   } // parseDoubleToken


   /**
    * Returns true if the input represents a double value.
    * @param s the string to checked.
    * @return true if the input is a double value.
    */
   private static boolean isDouble(String s) {
      try {
         Double.parseDouble(s);
         return true;
      }
      catch (final Exception e) {
         return false;
      }

   } // isDouble


   /**
    * Evaluates the given function.
    * @param token the token to be used.
    * @param strokeStatistic the statistics to be used.
    * @return the function's result.
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
   } // evaluateFunction


   /**
    * Evaluates a comparison
    * @param operator the comparison operator.
    * @param left the value of the left operand.
    * @param right the value of the right operand.
    * @return true if the condition defined by the operator is true.
    */
   public static boolean evaluateComparison(Operator operator, double left,
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
   } // evaluateComparison

}
