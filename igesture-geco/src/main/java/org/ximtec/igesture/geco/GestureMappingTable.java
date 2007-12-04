/*
 * @(#)GestureMappingTable.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@mgail.com
 *
 * Purpose		:  Table containing all the user-defined gesture-mapping
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007	    crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco;

import java.util.Hashtable;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;



/**
 * Table containing all the user-defined gesture-mapping
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@mgail.com
 */
public class GestureMappingTable extends Hashtable<GestureClass,GestureToActionMapping> {
   
   public GestureMappingTable(){
      
   }
   
   /**
    * Constructs a new algorithm frame.
    * 
    * @param gestureClass the mapped gesture.
    * @param action the action.
    */
   public void addMapping(GestureClass gestureClass, GestureToActionMapping mapping){
      put(gestureClass, mapping);
   }//addMapping
   
   
   /**
    * Retrieve the action corresponding to the class
    * 
    * @param gestureClass.
    */
   public GestureToActionMapping getMapping(GestureClass gestureClass){
      return (GestureToActionMapping)get(gestureClass);
   }//getAction
   
   

}
