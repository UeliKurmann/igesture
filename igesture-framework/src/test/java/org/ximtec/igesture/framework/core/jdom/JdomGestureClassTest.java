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


package org.ximtec.igesture.framework.core.jdom;

import org.junit.Assert;
import org.junit.Test;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.jdom.JdomGestureClass;


/**
 * Comment
 * @version 1.0 23.11.2008
 * @author Ueli Kurmann
 */
public class JdomGestureClassTest {

   private static final String GESTURE_CLASS_ID = "IdNumber";
   private static final String GESTURE_CLASS_NAME = "Gesture Class Name";


   @Test
   public void test() {
      GestureClass gestureClass = new GestureClass(GESTURE_CLASS_NAME);
      gestureClass.addDescriptor(new SampleDescriptor());
      gestureClass.setId(GESTURE_CLASS_ID);

      JdomGestureClass element = new JdomGestureClass(gestureClass);

      GestureClass imported = JdomGestureClass.unmarshal(element);

      Assert.assertEquals(gestureClass.getName(), imported.getName());
      Assert.assertEquals(gestureClass.getId(), imported.getId());
      Assert.assertEquals(gestureClass.getDescriptor(SampleDescriptor.class)
            .getClass(), imported.getDescriptor(SampleDescriptor.class)
            .getClass());
   }

}
