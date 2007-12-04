/*
 * @(#)SortedListModel.java	1.0   Nov 22, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Provide a model for sorted JList
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 22, 2007		crocimi    	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;



/**
 * Provide a model for sorted JList
 * @version 1.0 Nov 22, 2007
 * @author Michele Croci, mcroci@gmail.com
 */

 public class SortedListModel<T> extends AbstractListModel {


    SortedSet model;
    
    public SortedListModel(Comparator<T> c) {
       model = new TreeSet<T>(c);
     }//SortedListModel
     
    
    public SortedListModel() {
       model = new TreeSet<T>();
       }//SortedListModel
    

    public int getSize() {
      return model.size();
    }//getSize

    public Object getElementAt(int index) {
      return model.toArray()[index];
    }//getElementAt


    public void add(Object element) {
      if (model.add(element)) {
        fireContentsChanged(this, 0, getSize());
      }
    }//add
    
    public void addAll(List<Object> elements) {
       model.addAll(elements);
       fireContentsChanged(this, 0, getSize());
     }//addAll

    public void clear() {
      model.clear();
      fireContentsChanged(this, 0, getSize());
    }//clear

    public boolean contains(Object element) {
      return model.contains(element);
    }//contains

    public Object firstElement() {
      return model.first();
    }//firstElement

    public Iterator iterator() {
      return model.iterator();
    }//iterator

    public Object lastElement() {
      // Return the appropriate element
      return model.last();
    }//lastElement

    public boolean removeElement(Object element) {
      boolean removed = model.remove(element);
      if (removed) {
        fireContentsChanged(this, 0, getSize());
      }
      return removed;   
    }//removeElement
   
}


