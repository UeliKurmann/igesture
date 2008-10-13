/*
 * @(#)$Id:$
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
 * 17.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.batch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.MainModel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Comment
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class BatchView extends AbstractPanel implements TabbedView {

   public BatchView() {
      setTitle(TitleFactory.createStaticTitle(GestureConstants.BATCH_VIEW_NAME));
      setCenter(createParameterPanel());
   }


   @Override
   public Icon getIcon() {
      return null;
   }


   @Override
   public String getName() {
      return ComponentFactory.getGuiBundle().getName(
            GestureConstants.BATCH_VIEW_NAME);
   }


   @Override
   public JComponent getPane() {
      return this;
   }


   private JPanel createParameterPanel() {

      FormLayout layout = new FormLayout(
            "100dlu, 4dlu, 100dlu, 4dlu, 40dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      // configuration xml file
      builder.append(ComponentFactory
            .createLabel(GestureConstants.CONFIGURATION_PANEL_NAME));
      JTextField textField = new JTextField();
      builder.append(textField);
      JButton configBrowseButton = new JButton();
      builder.append(configBrowseButton);
      builder.nextLine(2);

      // select gesture set
      builder.append(ComponentFactory
            .createLabel(GestureConstants.BATCH_GESTURESET));

      final JComboBox gestureSetCombo = new JComboBox(Locator.getDefault().getService(
            MainModel.IDENTIFIER, MainModel.class).getGestureSets().toArray());

      gestureSetCombo.addActionListener(new ActionListener() {
      
         @Override
         public void actionPerformed(ActionEvent arg0) {
            GestureSet gestureSet = (GestureSet)gestureSetCombo.getSelectedItem();
         }
      });
      builder.append(gestureSetCombo);
      builder.nextLine(4);
      
   // select gesture set
      builder.append(ComponentFactory
            .createLabel(GestureConstants.BATCH_GESTURESET));

      final JComboBox TestSetCombo = new JComboBox(Locator.getDefault().getService(
            MainModel.IDENTIFIER, MainModel.class).getTestSets().toArray());

      TestSetCombo.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            GestureSet gestureSet = (GestureSet)TestSetCombo.getSelectedItem();
         }
      });
      builder.append(TestSetCombo);


      return builder.getPanel();
   }

}
