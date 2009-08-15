/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose      : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 23.03.2008       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testbench.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.sigtec.graphix.widget.BasicButton;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanelFactory;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.testbench.action.RecogniseAction;
import org.ximtex.igesture.tool.binding.BindingFactory;
import org.ximtex.igesture.tool.binding.MapTextFieldBinding;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Comment
 * 
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class ConfigurationPanel extends AbstractPanel {

  private Configuration configuration;

  private GestureDevice<?, ?> gestureDevice;
  private JScrollPane resultList;


  private RecogniseAction recogniseAction;

  public ConfigurationPanel(Controller controller, Configuration configuration) {
    super(controller);
    this.configuration = configuration;
    init();

  }

  private void init() {
    setTitle(TitleFactory.createDynamicTitle(configuration, Configuration.PROPERTY_NAME));

    JPanel basePanel = new JPanel();
   
    
    basePanel.setLayout(new BorderLayout());
    basePanel.add(createParameterPanel(), BorderLayout.NORTH);
    basePanel.add(createWorkspace(), BorderLayout.CENTER);

    basePanel.setOpaque(true);
    basePanel.setBackground(Color.WHITE);
    
    setContent(basePanel);
  }

  private JPanel createWorkspace() {
    JPanel basePanel = new JPanel();

    recogniseAction = new RecogniseAction(getController(), configuration);
    recogniseAction.setEnabled(false);

    // input area
    basePanel.setLayout(new FlowLayout());

    gestureDevice = getController().getLocator().getService(SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
    InputPanel inputPanel = InputPanelFactory.createInputPanel(gestureDevice);
    basePanel.add(inputPanel.getPanel(new Dimension(200, 200)));

    // buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BorderLayout());

    final JComboBox comboBox = new JComboBox(getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class)
        .getGestureSets().toArray());
   

    comboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        configuration.removeAllGestureSets();
        GestureSet gestureSet = (GestureSet) comboBox.getSelectedItem();
        configuration.addGestureSet(gestureSet);
        recogniseAction.setEnabled(true);
      }
    });
    
    comboBox.setSelectedIndex(0);

    comboBox.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        configuration.removeAllGestureSets();
        GestureSet gestureSet = (GestureSet) comboBox.getSelectedItem();
        configuration.addGestureSet(gestureSet);
        recogniseAction.setEnabled(true);
      }

    });

    JButton clearButton = new BasicButton(new ClearGestureSampleAction(getController(), gestureDevice));
    //Formatter.formatButton(clearButton);
    JButton recogniseButton = new BasicButton(recogniseAction);
    //Formatter.formatButton(recogniseButton);
    
    buttonPanel.add(clearButton, BorderLayout.NORTH);
    buttonPanel.add(recogniseButton, BorderLayout.CENTER);
    buttonPanel.add(comboBox, BorderLayout.SOUTH);
    

    basePanel.add(buttonPanel);

    // Result List
    resultList = new JScrollPane(new JList());
    resultList.setPreferredSize(new Dimension(200, 200));

    basePanel.add(resultList);
    
    basePanel.setOpaque(true);
    basePanel.setBackground(Color.WHITE);

    return basePanel;
  }

  @Override
  public void refreshUILogic() {
    super.refreshUILogic();
    gestureDevice.clear();
  }

  private JPanel createParameterPanel() {

    FormLayout layout = new FormLayout(
        "100dlu, 4dlu, 100dlu",
        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

    DefaultFormBuilder builder = new DefaultFormBuilder(layout);
    builder.setDefaultDialogBorder();

    String algorithmName = configuration.getAlgorithms().get(0);

    builder.append(getComponentFactory().createLabel(GestureConstants.CONFIGURATION_PANEL_PARAMETERS));
    builder.nextLine(2);

    builder.append(getComponentFactory().createLabel(GestureConstants.CONFIGURATION_PANEL_NAME));
    JTextField textField = new JTextField();
    BindingFactory.createInstance(textField, configuration, Configuration.PROPERTY_NAME);
    builder.append(textField);
    builder.nextLine(2);

    Map<String, String> parameter = configuration.getParameters(algorithmName);
    for (String parameterName : parameter.keySet()) {
      builder.append(new JLabel(parameterName));
      JTextField paramTextField = new JTextField();
      new MapTextFieldBinding(paramTextField, configuration, parameterName, algorithmName);
      builder.append(paramTextField);
      builder.nextLine(2);
    }
    
    

    JPanel panel = builder.getPanel();
    panel.setOpaque(true);
    panel.setBackground(Color.WHITE);
    return panel;
  }

  public void setResultList(List<Result> classes) {
    resultList.setViewportView(new JList(new Vector<Result>(classes)));
  }
}
