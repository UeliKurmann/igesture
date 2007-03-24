/*
 * @(#)TestSetListModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Test set list model.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
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


/**
 * Test set list model.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetListModel extends AbstractListModel {

   private GestureMainModel mainModel;

   private HashMap<String, TestSet> mapping;


   public TestSetListModel(GestureMainModel mainModel) {
      this.mainModel = mainModel;
      this.mapping = new HashMap<String, TestSet>();

      for (int i = 0; i < getSize(); i++) {
         mapping.put((String)getElementAt(i), getTestSet(i));
      }

   }


   /**
    * Returns the name of the test set at position index.
    * 
    * @param index the position of the test set whose name has to be returned.
    * @return the name of the test set at position index.
    */
   public Object getElementAt(int index) {
      return mainModel.getTestSets().get(index).getName();
   } // getElementAt


   /**
    * Returns the test set at position index.
    * 
    * @param index the position of the test set to be returned.
    * @return the test set at position index.
    */
   private TestSet getTestSet(int index) {
      return mainModel.getTestSets().get(index);
   } // getTestSet


   /**
    * Returns the number of test sets.
    */
   public int getSize() {
      return mainModel.getTestSets().size();
   } // getSize


   /**
    * Returns the test set for a given name.
    * 
    * @param name the name of the test set.
    * @return the test set for a given name.
    */
   public TestSet getTestSet(String name) {
      return mapping.get(name);
   } // getTestSet

}
