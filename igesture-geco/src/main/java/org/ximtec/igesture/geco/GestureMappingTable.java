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
 * Comment
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@mgail.com
 */
public class GestureMappingTable extends Hashtable{
   
   public GestureMappingTable(){
      
   }
   
   public void addMapping(GestureClass gestureClass, GestureToActionMapping action){
      put(gestureClass, action);
   }
   
   public GestureToActionMapping getAction(GestureClass gestureClass){
      return (GestureToActionMapping)get(gestureClass);
   }
   
   

}
