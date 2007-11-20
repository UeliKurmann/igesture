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



/**
 * Comment
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@mgail.com
 */
public class GestureMappingTable extends Hashtable{
   
   public GestureMappingTable(){
      
   }
   
   public void addMapping(String gestureName, GestureActionMapping action){
      put(gestureName, action);
   }
   
   public GestureActionMapping getAction(String gestureName){
      return (GestureActionMapping)get(gestureName);
   }
   
   

}