/*
 * @(#)SimpleListModel.java	1.0   Dec 22, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	A simple generic list model
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.utils;

import java.util.Collection;
import java.util.Vector;

import javax.swing.AbstractListModel;


/**
 * Comment
 * 
 * @version 1.0 Dec 22, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
@SuppressWarnings("serial")
public class SimpleListModel<T> extends AbstractListModel {

   private Vector<T> vector;


   public SimpleListModel(Collection<T> collection) {
      vector = new Vector<T>(collection);
   }


   public Object getElementAt(int i) {
      return vector.get(i);
   }


   /**
    * Sets the data of the list model
    * 
    * @param collection
    */
   public void setVector(Collection<T> collection) {
      vector = new Vector<T>(collection);
   }


   public int getSize() {
      return vector.size();
   }

}
