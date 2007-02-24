/*
 * @(#)TestSetListModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Test set list model
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.testset;

import java.util.HashMap;

import javax.swing.AbstractListModel;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureMainModel;


public class TestSetListModel extends AbstractListModel {

   private GestureMainModel mainModel;

   private HashMap<String, TestSet> mapping;


   public TestSetListModel(GestureMainModel mainModel) {
      this.mainModel = mainModel;
      this.mapping = new HashMap<String, TestSet>();

      for (int i = 0; i < getSize(); i++) {
         mapping.put((String) getElementAt(i), getTestSet(i));
      }
   }


   /**
    * Returns the name of the ith TestSet
    */
   public Object getElementAt(int index) {
      return mainModel.getTestSets().get(index).getName();
   }


   /**
    * Returns the ith TestSet
    * 
    * @param index
    * @return
    */
   private TestSet getTestSet(int index) {
      return mainModel.getTestSets().get(index);
   }


   /**
    * Returns the number of TestSets
    */
   public int getSize() {
      return mainModel.getTestSets().size();
   }


   /**
    * Returns the TestSet which belongs to the given name
    * 
    * @param name the name of the TestSet
    * @return the TestSet
    */
   public TestSet getTestSet(String name) {
      return mapping.get(name);
   }
}
