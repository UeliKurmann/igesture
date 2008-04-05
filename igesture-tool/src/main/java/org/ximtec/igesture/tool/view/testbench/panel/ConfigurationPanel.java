/*
 * @(#)$Id$
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
 * 23.03.2008		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.panel;

import hacks.JNote;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.panel.AbstractAdminPanel;
import org.ximtec.igesture.tool.view.testbench.action.RecogniseAction;
import org.ximtex.igesture.tool.binding.BindingFactory;
import org.ximtex.igesture.tool.binding.MapTextFieldBinding;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class ConfigurationPanel extends AbstractAdminPanel {

   private Configuration configuration;

   private JNote note;
   private InputDeviceClient client;


   public ConfigurationPanel(Configuration configuration) {

      this.configuration = configuration;
      init();

   }


   private void init() {
      setTitle("Configuration");

      JTextArea textArea = new JTextArea();

      textArea
            .setText("- Configuration Properties \n- Gesture Set Selection\n- Capture Area\n- Result List");

      JPanel basePanel = new JPanel();
      basePanel.setLayout(new BorderLayout());
      basePanel.add(createParameterPanel(), BorderLayout.NORTH);
      basePanel.add(createWorkspace(), BorderLayout.CENTER);

      setCenter(basePanel);
   }


   private JPanel createWorkspace(){
      JPanel basePanel = new JPanel();

      // input area
      basePanel.setLayout(new FlowLayout());

      note = new JNote(200, 200);
      
      client = Locator.getDefault().getService(InputDeviceClientService.IDENTIFIER,
            InputDeviceClient.class);
      client.addInputHandler(note);
      
    

      basePanel.add(note);

      // buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); 
      
      
      final JComboBox comboBox = new JComboBox(Locator.getDefault().getService(MainModel.IDENTIFIER, MainModel.class).getGestureSets().toArray());
      
      comboBox.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseReleased(MouseEvent arg0) {
            configuration.addGestureSet((GestureSet)comboBox.getSelectedItem());
         }
      });
      
      buttonPanel.add(new BasicButton(new ClearGestureSampleAction(note)));
      buttonPanel.add(new BasicButton(new RecogniseAction(configuration, this)));
      buttonPanel.add(comboBox);     
      
      basePanel.add(buttonPanel);

      // Result List
      JScrollPane scrollPane = new JScrollPane(new JList());
      
      basePanel.add(scrollPane);
      
      return basePanel;
   }
   
   public Note getCurrentNote(){
      return client.createNote(0, System.currentTimeMillis(), 70);
   }


   public void reload() {
      note.clear();
   }


   private JPanel createParameterPanel() {

      FormLayout layout = new FormLayout(
            "100dlu, 4dlu, 200dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      // FIXME resource file
      String algorithmName = configuration.getAlgorithms().get(0);

      builder.append(new JLabel("Parameters: " + algorithmName));
      builder.nextLine(2);

      builder.append(new JLabel("Name"));
      JTextField textField = new JTextField();
      BindingFactory.createInstance(textField, configuration, Configuration.PROPERTY_NAME);
      builder.append(textField);
      builder.nextLine(2);
      
      
      Algorithm algorithm = AlgorithmFactory
            .createAlgorithmInstance(algorithmName);
      for (Enum< ? > e : algorithm.getConfigParameters()) {
         configuration.addParameter(algorithmName, e.name(), algorithm
               .getDefaultParameterValue(e.name()));
      }

      Map<String, String> parameter = configuration.getParameters(algorithmName);
      for (String parameterName : parameter.keySet()) {
         builder.append(new JLabel(parameterName));
         JTextField paramTextField = new JTextField();
         new MapTextFieldBinding(paramTextField,configuration, parameterName, algorithmName);
         builder.append(paramTextField);
         builder.nextLine(2);
      }

      return builder.getPanel();
   }

}
