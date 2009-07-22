/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Test MouseUtils
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 18, 2008		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.mouse;

import java.util.EnumSet;
import java.util.concurrent.Executors;

import org.ximtec.igesture.io.mouse.MouseUtils.MouseButton;


/**
 * Comment
 * @version 1.0 Jan 18, 2008
 * @author Michele Croci, mcroci@gmail.com
 * @author Ueli Kurmann
 */
public class MouseExample {

   /**
    * @param args
    */
   public static void main(String[] args) throws Exception {

      MouseUtils mouseUtils = new MouseUtils(new MouseHandler() {
       
         @Override
         public void handleMouseEvent(int x, int y, EnumSet<MouseButton> buttons) {
            System.out.println("x: " + x + " - y: " + y);
            System.out.println(buttons);

         }

      });

      Executors.newSingleThreadExecutor().execute(mouseUtils);

   }

}
