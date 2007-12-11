/*
 * @(#)HotkeyModel.java	1.0   Dec 11, 2007
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
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

import java.util.Observable;

import org.ximtec.igesture.geco.UserAction.CommandExecutor;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulation;



/**
 * Comment
 * @version 1.0 Dec 11, 2007
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HotKeyModel extends Observable{
   
   public HotKeyModel(){
      key="";
   }
   
   private boolean altSelected;
   private boolean ctrlSelected;
   private boolean shiftSelected;
   
   private String key;
   
   
   /**
    * Inits the state of the buttons.
    */
   public void updateModel(KeyboardSimulation action){
      if (action==null){
         altSelected = false;
         shiftSelected = false;
         ctrlSelected = false;
         key="";
      }
      else{
         altSelected = action.isAltSelected();
         shiftSelected = action.isShiftSelected();
         ctrlSelected = action.isCtrlSelected();
         key=action.getSelectedKey();
      }
      setChanged();
      notifyObservers();
   }//initButtonsState
   
   public void setAltSelected(boolean b){
      altSelected = b;
      setChanged();
      notifyObservers();
   }
   
   public void setShiftSelected(boolean b){
      shiftSelected = b;
      setChanged();
      notifyObservers();
   }
   
   public void setCtrlSelected(boolean b){
      ctrlSelected = b;
      setChanged();
      notifyObservers();
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
      setChanged();
      notifyObservers();
   }
   
   public String getKey(){
      return key;
   }
   
   public String getAllKeys(){
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
