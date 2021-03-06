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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.sigtec.graphix.widget.BasicButton;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceManagerListener;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.BindingFactory;
import org.ximtec.igesture.tool.binding.MapTextFieldBinding;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.gesturevisualisation.InputComponentPanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanelFactory;
import org.ximtec.igesture.tool.service.DeviceManagerService;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.DeviceListPanel;
import org.ximtec.igesture.tool.view.DeviceListPanelListener;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.testbench.action.RecogniseAction;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Comment
 * 
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class ConfigurationPanel extends AbstractPanel implements DeviceListPanelListener, DeviceManagerListener {

  private static final int INPUTAREA_SIZE = 200;

  public static final String IDENTIFIER = "testbench";
  
  private Configuration configuration;
  private Algorithm algorithm = null;

  private GestureDevice<?, ?> gestureDevice;
  private GestureDevice<?, ?> currentDevice;
  
  private JScrollPane resultList;
  private RecogniseAction recogniseAction;
  
  private DeviceListPanel devicePanel;
  private JPanel cardPanel; 
  
  private Map<String, InputComponentPanel> panelMapping;
  
  private Controller controller;

  public ConfigurationPanel(Controller controller, Configuration configuration) {
    super(controller);
    this.configuration = configuration;
    
    try {
		algorithm = AlgorithmFactory.createAlgorithm(configuration);
	} catch (AlgorithmException e) {
		e.printStackTrace();
	}
    
    this.panelMapping = new HashMap<String, InputComponentPanel>();
    
    DeviceManagerService manager = controller.getLocator().getService(DeviceManagerService.IDENTIFIER, DeviceManagerService.class);
    manager.addDeviceManagerListener(this);
    
    this.controller = controller;
    
    init(manager);

  }

  private void init(IDeviceManager manager) {
    setTitle(TitleFactory.createDynamicTitle(configuration, Configuration.PROPERTY_NAME));    
    setContent(createParameterPanel());
    setBottom(createWorkspace(manager));
  }

  /**
   * Captures a local reference of the gesture input device.
   */
  private void initGestureDevice() {
    gestureDevice = getController().getLocator().getService(SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
  }
  
  private JPanel createWorkspace(IDeviceManager manager) {
    JPanel basePanel = new JPanel();
    
    // input area
    basePanel.setLayout(new BorderLayout());
    
    initGestureDevice();
    
    recogniseAction = new RecogniseAction(getController(), configuration);
    recogniseAction.setEnabled(false);
    
    cardPanel = new JPanel();
	cardPanel.setLayout(new CardLayout());
	cardPanel.setSize(new Dimension(INPUTAREA_SIZE, INPUTAREA_SIZE));
	
//	currentDevice = gestureDevice;
	currentDevice = null;
	
	basePanel.add(cardPanel, BorderLayout.CENTER);
	
	devicePanel = new DeviceListPanel();
    for(AbstractGestureDevice<?,?> device : manager.getDevices())
    	addDevice(device);
    devicePanel.addDevicePanelListener(this);
    basePanel.add(devicePanel,BorderLayout.WEST);

    // Result List
    resultList = new JScrollPane(new JList());
    resultList.setPreferredSize(new Dimension(200, 50));

    basePanel.add(resultList, BorderLayout.SOUTH);
    
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
        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

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
    ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String,String>();
    map.putAll(parameter);
    for (String parameterName : map.keySet()) {
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
  
  private InputComponentPanel createInputPanel(GestureDevice<?,?> device)
  {
		InputComponentPanel inputComponentPanel = new InputComponentPanel();
		inputComponentPanel.setLayout(new FlowLayout());
		GestureDevicePanel inputPanelInstance = InputPanelFactory.createPanel(device);
		inputComponentPanel.setGestureDevicePanel(inputPanelInstance);
		inputComponentPanel.add(inputPanelInstance);
		
	    // buttons
	    JPanel buttonPanel = ComponentFactory.createBorderLayoutPanel();  
	    
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
	
	    JButton recogniseButton = new BasicButton(recogniseAction);
	    //Formatter.formatButton(recogniseButton);
	    
	    buttonPanel.add(createClearSampleButton(device), BorderLayout.NORTH);
	    buttonPanel.add(recogniseButton, BorderLayout.CENTER);
	    buttonPanel.add(comboBox, BorderLayout.SOUTH);
	    
	    inputComponentPanel.add(buttonPanel);	 
		
		return inputComponentPanel;
	}

	private JButton createClearSampleButton(GestureDevice<?,?> gestureDevice) 
	{
	    JButton clearSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_CLEAR,
	        new ClearGestureSampleAction(getController(), gestureDevice));
//	    Formatter.formatButton(clearSampleButton);
	    return clearSampleButton;
	}

  	private void addDevice(AbstractGestureDevice<?,?> device)
	{
	  	//only devices that can provides input that can be recognised by the algorithm
		if(algorithm != null && algorithm.getType() == device.getDeviceType())
		{
			//add input panel
			InputComponentPanel panel = createInputPanel(device);
			panelMapping.put(device.toString(), panel);
			cardPanel.add(panel,device.toString());
			//add device to device list
			devicePanel.addDevice(device);
		}
	}
	
	private void removeDevice(AbstractGestureDevice<?,?> device)
	{
		if(algorithm != null && algorithm.getType() == device.getDeviceType() )
		{
			devicePanel.removeDevice(device);
			//remove input panel
			InputComponentPanel panel = panelMapping.get(device.toString());
			cardPanel.remove(panel);
		}
	}
  
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.view.DeviceListPanelListener#updateDeviceListPanelListener(org.ximtec.igesture.io.AbstractGestureDevice)
	 */
	@Override
	public void updateDeviceListPanelListener(AbstractGestureDevice<?, ?> device) {

		//remove listener from current device
		if(currentDevice != null)
			currentDevice.removeGestureHandler(panelMapping.get(currentDevice.toString()).getGestureDevicePanel());
		//change input panel
		((CardLayout)cardPanel.getLayout()).show(cardPanel, device.toString());
		currentDevice = device;
		
		controller.getLocator().setSharedDevice(IDENTIFIER, currentDevice);
		
		//add listener to new device
		device.addGestureHandler(panelMapping.get(device.toString()).getGestureDevicePanel());
		
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.view.devicemanager.DeviceManagerListener#updateDeviceManagerListener(int, org.ximtec.igesture.io.AbstractGestureDevice)
	 */
	@Override
	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?, ?> device) {
		if(operation == DeviceManagerListener.ADD)
		{
			addDevice(device);
		} 
		else if(operation == DeviceManagerListener.REMOVE)
		{
			removeDevice(device);
		}
		
	}
}
