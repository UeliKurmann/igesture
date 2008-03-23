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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.util;

import java.beans.PropertyChangeListener;

import org.ximtec.igesture.core.DataObject;
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
      dataObject.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(GestureClass gestureClass) {
      gestureClass.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(GestureSet gestureSet) {
      gestureSet.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(GestureSample gestureSample) {
      gestureSample.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(TestSet testSet) {
      testSet.addPropertyChangeListener(listener);

   }


   @Override
   public void visit(SampleDescriptor sampleDescriptor) {
      sampleDescriptor.addPropertyChangeListener(listener);

   }

}
