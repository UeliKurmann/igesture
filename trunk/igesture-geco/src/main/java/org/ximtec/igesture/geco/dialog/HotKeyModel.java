/*
 * @(#)HotkeyModel.java	1.0   Dec 11, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					bsigner		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.dialog;


/**
 * Comment
 * @version 1.0 Dec 11, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class HotKeyModel{

   
   public void HotKeyModel(boolean alt, boolean ctrl, boolean shift, String key){
      ctrlSelected = ctrl;
      altSelected = alt;
      shiftSelected = shift;
      this.key = key;
   }
   
   private boolean altSelected;
   private boolean ctrlSelected;
   private boolean shiftSelected;
   
   private String key;
   
   
   public void setAltSelected(boolean b){
      altSelected = b;
   }
   
   public void setShiftSelected(boolean b){
      shiftSelected = b;
   }
   
   public void setCtrlSelected(boolean b){
      ctrlSelected = b;
   }
   
   public boolean isAltSelected(){
      return altSelected;
   }
   
   public boolean isShiftSelected(){
      return shiftSelected ;
   }
   
   public boolean isCtrlSelected(){
      return ctrlSelected;
   }
   
   public void setKey(String s){
      key=s;
   }
   
   public String getKey(){
      return key;
   }
   
   public String toString(){
      String allKeys="";
      if(ctrlSelected)
         allKeys+="CONTROL+";
      if(shiftSelected)
         allKeys+="SHIFT+";
      if(altSelected)
         allKeys+="ALT+";

      allKeys+=key;
      return allKeys;
   }


   
   

}
