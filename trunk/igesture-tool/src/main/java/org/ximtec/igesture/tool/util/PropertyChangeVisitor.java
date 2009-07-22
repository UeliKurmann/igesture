/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.DefaultPropertyChangeNotifier;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.core.Visitor;


public class PropertyChangeVisitor implements Visitor {

   private PropertyChangeListener listener;


   public PropertyChangeVisitor(PropertyChangeListener listener) {
      this.listener = listener;
   }


   @Override
   public void visit(DataObject dataObject) {
      setUp(dataObject);
      dataObject.removePropertyChangeListener(listener);
      dataObject.addPropertyChangeListener(listener); 

   }


   @Override
   public void visit(GestureClass gestureClass) {
      setUp(gestureClass);
      gestureClass.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(GestureSet gestureSet) {
      setUp(gestureSet);
      gestureSet.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(GestureSample gestureSample) {
      setUp(gestureSample);
      gestureSample.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(TestSet testSet) {
      setUp(testSet);
      testSet.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(SampleDescriptor sampleDescriptor) {
      setUp(sampleDescriptor);
      sampleDescriptor.addPropertyChangeListener(listener);

   }


   private void setUp(DataObject dataObject) {
      if (dataObject instanceof DefaultDataObject) {
         try {
            Field field = DefaultPropertyChangeNotifier.class
                  .getDeclaredField("propertyChangeSupport");
            field.setAccessible(true);
            if (field.get(dataObject) == null) {
               PropertyChangeSupport pcs = new PropertyChangeSupport(dataObject);
               field.set(dataObject, pcs);
            }
         }
         catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}
