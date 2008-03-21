/*
 * @(#)AlgorithmConfiguration.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Algorithm configuration.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.algorithm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.old.GestureConstants;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;
import org.ximtec.igesture.tool.old.event.ConfigurationListener;
import org.ximtec.igesture.tool.old.frame.algorithm.action.CreateConfigurationAction;
import org.ximtec.igesture.tool.old.frame.algorithm.action.DeleteConfigurationAction;
import org.ximtec.igesture.tool.old.frame.algorithm.action.ExportConfigurationAction;
import org.ximtec.igesture.tool.old.frame.algorithm.action.OpenConfigurationAction;
import org.ximtec.igesture.tool.old.frame.algorithm.action.SaveConfigurationAction;


/**
 * Algorithm configuration.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AlgorithmConfiguration extends BasicInternalFrame implements
      ConfigurationListener {

   public static String ALGORITHM_NAME = "ALGORITHM";

   private GestureToolView mainView;

   private JScrollPane scrollPane;

   private ScrollableList configurationList;

   private Configuration currentConfiguration;

   private List<ConfigParameter> configurationParameters;


   public AlgorithmConfiguration(GestureToolView mainView) {
      super(GestureConstants.ALGORITHM_FRAME_KEY, GestureToolMain.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      mainView.getModel().addConfigurationListener(this);
      init();
   }


   private void init() {
      scrollPane = new JScrollPane(null);
      scrollPane.setAutoscrolls(true);
      scrollPane.setPreferredSize(new Dimension(380, 300));
      JButton saveButton = GuiTool
            .createButton(new SaveConfigurationAction(this));
      addComponent(createConfigurationList(), SwingTool.createGridBagConstraint(
            0, 0));
      addComponent(scrollPane, SwingTool.createGridBagConstraint(0, 1));
      addComponent(saveButton, SwingTool.createGridBagConstraint(0, 2));
   } // init


   private Component createConfigurationList() {
      final ConfigurationListModel listModel = new ConfigurationListModel(
            mainView.getModel());
      configurationList = new ScrollableList(listModel, 180, 100);
      configurationList.getList().addMouseListener(new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
               createPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }
         }
      });

      return configurationList;
   } // createConfigurationList


   private JPopupMenu createPopupMenu() {
      final JPopupMenu menu = new JPopupMenu();
      menu.add(new JMenuItem(new OpenConfigurationAction(this)));
      menu.add(new JMenuItem(new DeleteConfigurationAction(this)));
      menu.add(createAlgorithmPopupMenu());
      menu.add(new JMenuItem(new ExportConfigurationAction(this)));
      return menu;
   } // createPopupMenu


   public JMenu createAlgorithmPopupMenu() {
      final JMenu menu = new JMenu();
      menu.setText(GestureToolMain.getGuiBundle().getName(
            GestureConstants.CONFIG_CREATE_ACTION));

      for (final String name : mainView.getModel().getAlgorithms()) {
         menu.add(new JMenuItem(new CreateConfigurationAction(this, name)));
      }

      return menu;
   } // createAlgorithmPopupMenu


   public void loadConfiguration(Configuration configuration) {
      if (configuration != null) {
         final String algorithmName = getAlgorithmName(configuration);
         currentConfiguration = configuration;
         configurationParameters = new ArrayList<ConfigParameter>();
         configurationParameters.add(new ConfigParameter(ALGORITHM_NAME,
               algorithmName));

         for (final String key : configuration.getParameters(algorithmName)
               .keySet()) {
            configurationParameters.add(new ConfigParameter(key, configuration
                  .getParameters(algorithmName).get(key)));
         }

         final JPanel panel = new JPanel();
         panel.setLayout(new GridBagLayout());
         int i = 0;

         for (final ConfigParameter cp : configurationParameters) {
            panel.add(cp.getLabel(), SwingTool.createGridBagConstraint(0, i, 1,
                  1, GridBagConstraints.WEST));
            panel
                  .add(cp.getTextField(), SwingTool
                        .createGridBagConstraint(1, i));
            i++;
         }

         scrollPane.setViewportView(panel);
      }

   } // loadConfiguration


   public Configuration createConfiguration(String name) {
      final Algorithm algorithm = AlgorithmFactory.createAlgorithmInstance(name);
      final Configuration configuration = new Configuration();
      configuration.addAlgorithm(algorithm.getClass().getCanonicalName());

      for (final Enum<?> e : algorithm.getConfigParameters()) {
         configuration.addParameter(algorithm.getClass().getName(), e.name(),
               algorithm.getDefaultParameterValue(e.name()));
      }

      return configuration;
   } // createConfiguration


   private static Algorithm getAlgorithm(Configuration config) {
      Algorithm algorithm = null;

      if (!config.getAlgorithms().isEmpty()) {
         algorithm = AlgorithmFactory.createAlgorithmInstance(config
               .getAlgorithms().get(0));
      }

      return algorithm;
   } // getAlgorithm


   private static String getAlgorithmName(Configuration configuration) {
      return getAlgorithm(configuration).getClass().getCanonicalName();
   } // getAlgorithmName


   public void updateCurrentConfiguration() {
      final String currentAlgorithmName = getAlgorithmName(currentConfiguration);
      String newAlgorithmName = null;

      for (final ConfigParameter cp : configurationParameters) {

         if (cp.getParameterName().equals(ALGORITHM_NAME)) {
            newAlgorithmName = cp.getParameterValue();
         }

      }

      if (!newAlgorithmName.equals(currentAlgorithmName)) {
         currentConfiguration.removeParameters(currentAlgorithmName);
         currentConfiguration.removeAlgorithm(currentAlgorithmName);
         currentConfiguration.addAlgorithm(newAlgorithmName);
      }

      for (final ConfigParameter cp : configurationParameters) {

         if (!cp.getParameterName().equals(ALGORITHM_NAME)) {
            currentConfiguration.addParameter(newAlgorithmName, cp
                  .getParameterName(), cp.getParameterValue());
         }

      }
      mainView.getModel().updateDataObject(currentConfiguration);
   } // updateCurrentConfiguration


   public void deleteCurrentConfiguration() {
      final Configuration config = ((ConfigurationListModel)configurationList
            .getList().getModel()).getInstanceAt(configurationList
            .getSelectedIndex());
      mainView.getModel().removeConfiguration(config);
   } // deleteCurrentConfiguration


   public void openSelectedConfiguration() {
      final Configuration config = ((ConfigurationListModel)configurationList
            .getList().getModel()).getInstanceAt(configurationList
            .getSelectedIndex());
      loadConfiguration(config);
   } // openSelectedConfiguration


   public void configurationChanged(EventObject event) {
      configurationList
            .setModel(new ConfigurationListModel(mainView.getModel()));
   } // configurationChanged


   public GestureToolView getMainView() {
      return mainView;
   } // getMainView


   public Configuration getCurrentConfiguration() {
      return currentConfiguration;
   } // getCurrentConfiguration

   /**
    * Inner helper class for handling the parameter/value tuples.
    * 
    * @version 1.0, Nov 2006
    * @author Ueli Kurmann, kurmannu@ethz.ch
    * @author Beat Signer, signer@inf.ethz.ch
    */
   private class ConfigParameter {

      private JLabel labelName;

      private JTextField fieldValue;

      private String key;


      public ConfigParameter(String key) {
         this.key = key;
         labelName = new JLabel(key);
         fieldValue = new JTextField();
      }


      public ConfigParameter(String key, String value) {
         this(key);
         fieldValue.setText(value);
      }


      public String getParameterName() {
         return key.trim();
      } // getParameterName


      public String getParameterValue() {
         return fieldValue.getText().trim();
      } // getParameterValue


      public JTextField getTextField() {
         fieldValue.setPreferredSize(new Dimension(200, 20));
         return fieldValue;
      } // getTextField


      public JLabel getLabel() {
         return labelName;
      } // getLabel

   }

}
