/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 28.11.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.keyboard;

import org.junit.Assert;


/**
 * Comment
 * @version 1.0 28.11.2008
 * @author Ueli Kurmann
 */
public class KeyTest {

   @org.junit.Test
   public void testParseKeyList1() {

      String keyList = "CONTROL + a + B +C +TAB+   VK_1";
      Key[] keys = Key.parseKeyList(keyList);
      Assert.assertEquals(Key.CONTROL, keys[0]);
      Assert.assertEquals(Key.A, keys[1]);
      Assert.assertEquals(Key.B, keys[2]);
      Assert.assertEquals(Key.C, keys[3]);
      Assert.assertEquals(Key.TAB, keys[4]);
      Assert.assertEquals(Key.VK_1, keys[5]);

   }


   @org.junit.Test(expected = IllegalArgumentException.class)
   public void testParseKeyList2() {

      String keyList = "CTRL + asdf + c";
      Key.parseKeyList(keyList);

   }

}
