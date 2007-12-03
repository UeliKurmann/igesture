/*
 * @(#)NewProjectDialog.java	1.0   Dec 3, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  New project dialog
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.graphics.SwingTool;



/**
 *  New project dialog
 *  
 * @version 1.0 Dec 3, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class NewProjectDialog extends BasicDialog{
   
   
   private GecoMainView view;
   
   public NewProjectDialog(GecoMainView view){
      this.view=view;
      init();
   }
   
   private void init(){

      this.setTitle(GecoConstants.NEW_PROJECT_DIALOG_TITLE);
      this.setLayout(new GridBagLayout());

      this.add(GuiTool.createLabel(GecoConstants.PROJECT_NAME),  new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(10,10,10,10),0,0 ) );
      
      this.add(GuiTool.createTextField(GecoConstants.PROJECT_NAME_TEXT_FIELD),  
            new GridBagConstraints(1,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10,10,10,10),0,0 ) );
      
      //dialog.add(GuiTool.createLabel(GecoConstants.PROJECT_FILE));    
   }

}
