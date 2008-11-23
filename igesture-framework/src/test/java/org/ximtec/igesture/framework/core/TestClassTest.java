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
 * 23.11.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.framework.core;

import org.junit.Assert;
import org.junit.Test;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.TestClass;


/**
 * Comment
 * @version 1.0 23.11.2008
 * @author Ueli Kurmann
 */
public class TestClassTest {

   private static final String SAMPLE1 = "Sample1";
   private static final String TEST_CLASS_NAME_1 = "TestClassName1";


   @Test
   public void test() {

      TestClass testClass = new TestClass(TEST_CLASS_NAME_1);

      Assert.assertEquals(TEST_CLASS_NAME_1, testClass.getName());
      Assert.assertEquals(0, testClass.size());

      testClass.add(new GestureSample(SAMPLE1, null));
      Assert.assertEquals(SAMPLE1, testClass.getGestures().get(0).getName());
      Assert.assertEquals(1, testClass.size());
      
      testClass.add(new GestureSample("Sample2", null));
      Assert.assertEquals(2, testClass.size());
   }

}
