/*
 * @(#)OpenProjectAction.java	1.0   Nov 15, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Open an existing gesture map project.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2007		crocimi    Initial Release
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
import java.util.List;

import javax.swing.JFileChooser;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.geco.xml.XMLImportGeco;



/**
* Open an existing gesture map project.
* 
* @version 1.0, Nov 2006
* @author Michele Croci, mcroci@gmail.com
*/
public class OpenProjectAction extends BasicAction {

  private GecoMainView mainView;
  
  private static final String XML_EXTENSION = "xml";


  public OpenProjectAction(GecoMainView mainView) {
     super(GecoConstants.OPEN_PROJECT_ACTION, GuiTool.getGuiBundle());
     this.mainView = mainView;
  }


  /**
   * Creates a new mapping
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
              XMLImportGeco XMLImport = new XMLImportGeco();
              XMLImport.importProject(selectedFile);
           
              //set file path??
              //update model
              mainView.getModel().loadGestureSet(XMLImport.getGestureSet());
              List<GestureToActionMapping> list = XMLImport.getMappings();
              for(GestureToActionMapping mapping: list){
                 mainView.getModel().addMapping(mapping);
              }
              //update view
              String projectName = selectedFile.getName();
              projectName = projectName.substring(0, projectName.length()-4);
              mainView.initProjectView(projectName);
              mainView.enableMenuItem();
              
              
            }
        }
     } else if (status == JFileChooser.CANCEL_OPTION) {
       
     }
     


  } // actionPerformed
  
}
