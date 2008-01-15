/*
 * @(#)ActionLoadGestureSet.java	1.0   Nov 20, 2007
 *
 * Author		:	 Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Load a gesture set
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 	 Nov 20, 2007	crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.geco.xml.XMLGeco;



/**
 * Load a gesture set
 * 
 * @version 0.9, Nov 20, 2007
 * @author  Michele Croci, mcroci@gmail.com
 */
public class LoadGestureSetAction extends BasicAction {
   
   

   private MainView mainView;
   
   private static final String XML_EXTENSION = "xml";


   public LoadGestureSetAction(MainView mainView) {
      super(Constant.LOAD_GESTURE_SET, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Open a dialog to allow the user to choose a gesture set.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      //ask confirm
      int n=0;
      if(!mainView.getModel().getMappings().isEmpty()){
      n = JOptionPane.showConfirmDialog(
            mainView,
            Constant.LOAD_GESTURE_SET_CONFIRMATION,
            Constant.EMPTY_STRING,
            JOptionPane.YES_NO_OPTION);
      }
      if (n==0){
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileFilter(new ExtensionFileFilter(XML_EXTENSION,
               new String[] {XML_EXTENSION}));
         int status = fileChooser.showOpenDialog(null);
         if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile != null){
               String ext = selectedFile.getName().substring(selectedFile.getName().length()-3,
                     selectedFile.getName().length());
               if(ext.equals(XML_EXTENSION)){
                   //update model
                   mainView.getModel().clearData();
                   mainView.getModel().loadGestureSet(loadGestureSet(selectedFile));
                   mainView.getModel().setGestureSetFileName(selectedFile.getAbsolutePath());
                   //update view
                   mainView.updateGestureList();
                   mainView.enableSaveButton();
                }
            }
         }
      }
         
   } // actionPerformed
   
   
   /**
    * Open a dialog to allow the user to choose a gesture set.
    * 
    * @param event the action event.
    */
   public GestureSet loadGestureSet(File file){
     return XMLGeco.importGestureSet(file).get(0); 
   }

}