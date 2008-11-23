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
import org.ximtec.igesture.core.TestSet;



/**
 * Comment
 * @version 1.0 23.11.2008
 * @author Ueli Kurmann
 */
public class TestSetTest {
   
   private static final String SAMPLE2 = "Sample2";
   private static final String SAMPLE1 = "Sample1";
   private static final String TEST_SET_NAME1 = "TestSetName1";

   @Test
   public void test(){
      TestSet testSet = new TestSet(TEST_SET_NAME1);
      
      Assert.assertEquals(TEST_SET_NAME1, testSet.getName());
      Assert.assertEquals(1, testSet.size());
      Assert.assertEquals(TestSet.NOISE, testSet.getTestClasses().get(0).getName());
      
      testSet.add(new GestureSample(SAMPLE1, null));
      testSet.add(new GestureSample(SAMPLE2, null));
      
      Assert.assertEquals(3, testSet.size());
      
      Assert.assertEquals(TestSet.NOISE, testSet.getTestClass(TestSet.NOISE).getName());
      Assert.assertEquals(SAMPLE1, testSet.getTestClass(SAMPLE1).getName()); 
      
   }

}
