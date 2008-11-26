/*
 * @(#)$Id: WorkspaceTool.java 456 2008-11-11 09:54:06Z D\signerb $
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 07.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testset.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Comment
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */ 
public class TestSetPanel extends AbstractPanel {

   private TestSet testSet;
 
   public TestSetPanel(Controller controller, TestSet testSet) {
      this.testSet = testSet;
      init();
   }


   private void init() {
      
      setTitle(TitleFactory.createStaticTitle(testSet.getName()));

      // FIXME
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < testSet.size()*4 || i < 4; i++){
         sb.append("pref, 4dlu,");
      }
      
      FormLayout layout = new FormLayout("100dlu, 4dlu, 200dlu", sb.toString());

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      JTextField textField = new JTextField();
      BindingFactory.createInstance(textField, testSet, TestSet.PROPERTY_NAME);
      builder.append(textField);
      builder.nextLine(2);

      builder.append(ComponentFactory.createLabel(GestureConstants.GESTURE_SET_PANEL_NOGC));
      builder.append(new JLabel(Integer.toString(testSet.size())));
      
      JPanel panel = builder.getPanel();
      panel.setOpaque(false);
      setCenter(panel);

       
   }

}
