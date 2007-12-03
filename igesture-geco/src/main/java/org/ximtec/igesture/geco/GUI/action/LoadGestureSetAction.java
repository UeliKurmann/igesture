/*
 * @(#)ActionLoadGestureSet.java	1.0   Nov 20, 2007
 *
 * Author		:	 Michele Croci, mcroci@gmail.com
 *
 * Purpose		: 
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.geco.xml.XMLGeco;



/**
 * Comment
 * @version 1.0 Nov 20, 2007
 * @author  Michele Croci, mcroci@gmail.com
 */
public class LoadGestureSetAction extends BasicAction {
   
   

   private GecoMainView mainView;
   
   private static final String XML_EXTENSION = "xml";


   public LoadGestureSetAction(GecoMainView mainView) {
      super(GecoConstants.LOAD_GESTURE_SET, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Open a dialog to allow the user to choose a gesture set.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
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
                mainView.getModel().loadGestureSet(loadGestureSet(selectedFile));
//                mainView.getModel().getConfiguration().addGestureSet(mainView.getModel().getGestureSet());
                //update view
                mainView.updateGestureList();
             }
         }
      } else if (status == JFileChooser.CANCEL_OPTION) {
        
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
