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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.batch.action.RunBatchAction;
import org.ximtec.igesture.tool.view.batch.action.SelectConfigFileAction;
import org.ximtec.igesture.tool.view.batch.action.SelectOutputDirAction;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Comment
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class BatchView extends AbstractPanel implements TabbedView {

   private static final int NEXT_LINE = 2;
   private GestureSet gestureSet;
   private TestSet testSet;
   private Controller controller;
   
   private JTextField configFileTextField;
   private JTextField outDirTextField;


   public BatchView(Controller controller) {
      this.controller = controller;
      setTitle(TitleFactory.createStaticTitle(GestureConstants.BATCH_VIEW_NAME));
      init();
   }


   private void init() {
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
            "80dlu, 4dlu, 140dlu, 4dlu, 80dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      // select configuration
      createConfigurationSelection(builder);

      // select gesture set
      createGestureSetSelection(builder);

      // select test set
      createTestSetSelection(builder);

      // select output dir
      createOutputDirSelection(builder);

      createRunButton(builder);

      return builder.getPanel();
   }


   private void createGestureSetSelection(DefaultFormBuilder builder) {
      // select gesture set
      builder.append(ComponentFactory
            .createLabel(GestureConstants.BATCH_GESTURESET));

      final JComboBox combo = new JComboBox(createArray(getModel()
            .getGestureSets().toArray(), "Select Gesture Set"));

      combo.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            Object obj = combo.getSelectedItem();
            if (obj instanceof GestureSet) {
               gestureSet = (GestureSet)obj;
            }
         }
      });
      builder.append(combo);
      builder.nextLine(NEXT_LINE);
   }


   private void createConfigurationSelection(DefaultFormBuilder builder) {
      // configuration xml file
      JLabel label = ComponentFactory.createLabel(GestureConstants.BATCH_CONFIG);
      builder.append(label);

      configFileTextField = new JTextField();
      builder.append(configFileTextField);

      JButton browseButton = ComponentFactory.createButton(
            GestureConstants.BATCH_BROWSE_CONFIG, new SelectConfigFileAction(this));
      
      Formatter.formatButton(browseButton);
      builder.append(browseButton);

      builder.nextLine(NEXT_LINE);
   }


   private void createTestSetSelection(DefaultFormBuilder builder) {
      builder.append(ComponentFactory
            .createLabel(GestureConstants.BATCH_TESTSET));

      final JComboBox combo = new JComboBox(createArray(getModel().getTestSets()
            .toArray(), "Select Test Set"));

      combo.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            Object obj = combo.getSelectedItem();
            if (obj instanceof TestSet) {
               testSet = (TestSet)obj;
            }
         }
      });

      builder.append(combo);
      builder.nextLine(NEXT_LINE);
   }


   private void createOutputDirSelection(DefaultFormBuilder builder) {
      builder.append(ComponentFactory
            .createLabel(GestureConstants.BATCH_OUTPUT_DIR));
      
      outDirTextField = new JTextField();
      
      builder.append(outDirTextField);
      JButton browseButton = ComponentFactory.createButton(
            GestureConstants.BATCH_BROWSE_OUTPUT, new SelectOutputDirAction(this));
      Formatter.formatButton(browseButton);
      builder.append(browseButton);
      builder.nextLine(NEXT_LINE);
   }


   private void createRunButton(DefaultFormBuilder builder) {
      JButton runButton = ComponentFactory.createButton(GestureConstants.BATCH_RUN,
            new RunBatchAction(controller));
      
      Formatter.formatButton(runButton);
      
      builder.append(runButton);

      builder.nextLine(NEXT_LINE);
   }


   /**
    * Returns the main model
    * @return
    */
   private MainModel getModel() {
      return Locator.getDefault().getService(MainModel.IDENTIFIER,
            MainModel.class);
   }


   private Object[] createArray(Object[] originalArray, String title) {
      Object[] result = new Object[originalArray.length + 1];

      for (int i = 1; i < result.length; i++) {
         result[i] = originalArray[i - 1];
      }
      result[0] = title;

      return result;
   }


   @Override
   public void refresh() {
      super.refresh();
      // TODO: optimize refresh mechanism
      init();
   }


   public TestSet getTestSet() {
      return testSet;
   }


   public GestureSet getGestureSet() {
      return gestureSet;
   }


   public String getConfigFile() {
      return configFileTextField.getText();
   }


   public String getOutputDir() {
      return outDirTextField.getText();
   }


   public void setConfigFile(String configFile) {
      configFileTextField.setText(configFile);
   }


   public void setOutputDir(String outputDir) {
      outDirTextField.setText(outputDir);
   }
}