/*
 * @(#)AlgorithmConfiguration.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Algorithm Configuration
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.algorithm;

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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.ConfigurationListener;
import org.ximtec.igesture.tool.frame.algorithm.action.ActionCreateConfiguration;
import org.ximtec.igesture.tool.frame.algorithm.action.ActionDeleteConfiguration;
import org.ximtec.igesture.tool.frame.algorithm.action.ActionExportConfiguration;
import org.ximtec.igesture.tool.frame.algorithm.action.ActionLoadConfiguration;
import org.ximtec.igesture.tool.frame.algorithm.action.ActionSaveConfiguration;
import org.ximtec.igesture.tool.utils.ScrollableList;
import org.ximtec.igesture.tool.utils.SwingTool;


public class AlgorithmConfiguration extends BasicInternalFrame implements
      ConfigurationListener {

   public static String ALGORITHM_NAME = "ALGORITHM";

   private GestureToolView mainView;

   private JScrollPane scrollPane;

   private ScrollableList configurationList;

   private JButton saveButton;

   private Configuration currentConfiguration;

   private List<ConfigParameter> configurationParameters;


   public AlgorithmConfiguration(GestureToolView mainView) {
      super(GestureConstants.ALGORITHM_FRAME_KEY, SwingTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      mainView.getModel().addConfigurationListener(this);
      initialise();
   }


   private void initialise() {
      scrollPane = new JScrollPane(null);
      scrollPane.setAutoscrolls(true);
      scrollPane.setPreferredSize(new Dimension(380, 300));

      saveButton = new JButton();
      saveButton.setAction(new ActionSaveConfiguration(this));

      addComponent(createConfigurationList(), SwingTool.createGridBagConstraint(
            0, 0));
      addComponent(scrollPane, SwingTool.createGridBagConstraint(0, 1));
      addComponent(saveButton, SwingTool.createGridBagConstraint(0, 2));

      loadConfiguration(null);
   }


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
   }


   private JPopupMenu createPopupMenu() {
      final JPopupMenu menu = new JPopupMenu();

      menu.add(SwingTool.createMenuItem(new ActionLoadConfiguration(this)));
      menu.add(SwingTool.createMenuItem(new ActionDeleteConfiguration(this)));
      menu.add(createAlgorithmPopupMenu());
      menu.add(SwingTool.createMenuItem(new ActionExportConfiguration(this)));

      return menu;
   }


   public JMenu createAlgorithmPopupMenu() {
      final JMenu menu = new JMenu();
      menu.setText(SwingTool.getGuiBundle().getName(
            GestureConstants.CONFIG_CREATE_ACTION));
      for (final String name : mainView.getModel().getAlgorithms()) {
         menu.add(SwingTool.createMenuItem(new ActionCreateConfiguration(this,
               name)));
      }
      return menu;
   }


   public void loadConfiguration(Configuration configuration) {

      if (configuration != null) {
         final String algorithmName = getAlgorithmName(configuration);

         currentConfiguration = configuration;
         configurationParameters = new ArrayList<ConfigParameter>();
         configurationParameters.add(new ConfigParameter(ALGORITHM_NAME,
               algorithmName));

         for (final String key : configuration.getAlgorithmParameters(
               algorithmName).keySet()) {
            configurationParameters.add(new ConfigParameter(key, configuration
                  .getAlgorithmParameters(algorithmName).get(key)));
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
   }


   public Configuration createConfiguration(String name) {
      final Algorithm algorithm = AlgorithmFactory.createAlgorithmInstance(name);
      final Configuration configuration = new Configuration();
      configuration.addAlgorithm(algorithm.getClass().getCanonicalName());
      for (final Enum e : algorithm.getConfigParameters()) {
         configuration.addAlgorithmParameter(algorithm.getClass().getName(), e
               .name(), algorithm.getDefaultParameter(e.name()));
      }
      return configuration;
   }


   private static Algorithm getAlgorithm(Configuration config) {
      Algorithm algorithm = null;
      if (!config.getAlgorithms().isEmpty()) {
         algorithm = AlgorithmFactory.createAlgorithmInstance(config
               .getAlgorithms().get(0));
      }
      return algorithm;
   }


   private static String getAlgorithmName(Configuration configuration) {
      return getAlgorithm(configuration).getClass().getCanonicalName();
   }


   public void updateCurrentConfiguration() {
      final String currentAlgorithmName = getAlgorithmName(currentConfiguration);
      String newAlgorithmName = null;

      for (final ConfigParameter cp : configurationParameters) {
         if (cp.getParameterName().equals(ALGORITHM_NAME)) {
            newAlgorithmName = cp.getParameterValue();
         }
      }

      if (!newAlgorithmName.equals(currentAlgorithmName)) {
         currentConfiguration.removeAlgorithmParameter(currentAlgorithmName);
         currentConfiguration.removeAlgorithm(currentAlgorithmName);
         currentConfiguration.addAlgorithm(newAlgorithmName);
      }

      for (final ConfigParameter cp : configurationParameters) {
         if (!cp.getParameterName().equals(ALGORITHM_NAME)) {
            currentConfiguration.addAlgorithmParameter(newAlgorithmName, cp
                  .getParameterName(), cp.getParameterValue());
         }
      }
      mainView.getModel().updateDataObject(currentConfiguration);
   }


   public void deleteCurrentConfiguration() {
      final Configuration config = ((ConfigurationListModel) configurationList
            .getList().getModel()).getInstanceAt(configurationList
            .getSelectedIndex());
      mainView.getModel().removeConfiguration(config);
   }


   public void openSelectedConfiguration() {
      final Configuration config = ((ConfigurationListModel) configurationList
            .getList().getModel()).getInstanceAt(configurationList
            .getSelectedIndex());
      loadConfiguration(config);
   }


   public void configurationChanged(EventObject event) {
      configurationList
            .setModel(new ConfigurationListModel(mainView.getModel()));
   }


   public GestureToolView getMainView() {
      return mainView;
   }


   public Configuration getCurrentConfiguration() {
      return currentConfiguration;
   }

   /**
    * Inner helper class for handling the parameter/value tuples
    * 
    * @author kurmannu
    * @version 1.0
    * @since igesture
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
      }


      public String getParameterValue() {
         return fieldValue.getText().trim();
      }


      public JTextField getTextField() {
         fieldValue.setPreferredSize(new Dimension(200, 20));
         return fieldValue;
      }


      public JLabel getLabel() {
         return labelName;
      }
   }
}
