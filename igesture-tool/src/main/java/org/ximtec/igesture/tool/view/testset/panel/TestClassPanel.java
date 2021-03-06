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
 * 07.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testset.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sigtec.graphix.GridBagLayouter;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceManagerListener;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.BindingFactory;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.gesturevisualisation.GesturePanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputComponentPanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanelFactory;
import org.ximtec.igesture.tool.service.DeviceManagerService;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.DeviceListPanel;
import org.ximtec.igesture.tool.view.DeviceListPanelListener;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.panel.SampleDescriptorPanel;
import org.ximtec.igesture.tool.view.testset.action.AddSampleAction;
import org.ximtec.igesture.tool.view.testset.action.RemoveSampleAction;

/**
 * Comment
 * 
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */

public class TestClassPanel extends AbstractPanel implements DeviceListPanelListener, DeviceManagerListener {

  private static final int INPUTAREA_SIZE = 200;
  private static final int SPACE_SIZE = 5;
  private static final int SAMPLE_SIZE = 100;

  private GestureDevice<?, ?> gestureDevice;
  private GestureDevice<?, ?> currentDevice;

  private Map<Gesture<?>, JPanel> sampleCache;
  private TestClass testClass;
  
  private DeviceListPanel devicePanel;
  private JPanel cardPanel; 
  
  private Map<String, InputComponentPanel> panelMapping;

  /**
   * Constructor
   * 
   * @param controller
   * @param descriptor
   */
  public TestClassPanel(Controller controller, TestClass testClass) {
    super(controller);
    this.testClass = testClass;
    this.sampleCache = new HashMap<Gesture<?>, JPanel>();
    this.panelMapping = new HashMap<String, InputComponentPanel>();

    // component listener to handle resize actions
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        initSampleSection(TestClassPanel.this.testClass.getGestures());
      }
    });
    
    DeviceManagerService manager = controller.getLocator().getService(DeviceManagerService.IDENTIFIER, DeviceManagerService.class);
    manager.addDeviceManagerListener(this);

    init(manager);

  }

  private JButton createAddSampleButton(GestureDevice<?, ?> device) {
    JButton addSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_ADD,
        new AddSampleAction(getController(), testClass, device));
    Formatter.formatButton(addSampleButton);
    return addSampleButton;
  }

  private JButton createClearSampleButton(GestureDevice<?, ?> gestureDevice) {
    JButton clearSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_CLEAR,
        new ClearGestureSampleAction(getController(), gestureDevice));
    Formatter.formatButton(clearSampleButton);
    return clearSampleButton;
  }

  /**
   * Returns the visualization of a gesture. A hash map is used to cache these
   * visualizations. Otherwise every update of the view requires the computation
   * of all samples.
   * 
   * The cache is very simple and it's elements are never removed. This simple
   * implementation is sufficient, because of the short life-cycle of the
   * explorer tree views.
   * 
   * @param sample
   * @return
   */
  private synchronized JPanel createSampleIcon(final Gesture<?> sample) {

    if (sampleCache.containsKey(sample)) {
      return sampleCache.get(sample);
    }

    GesturePanel gesturePanel = InputPanelFactory.createGesturePanel(sample);
    final JPanel panel = gesturePanel.getPanel(new Dimension(SAMPLE_SIZE, SAMPLE_SIZE));
    panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    RemoveSampleAction action = new RemoveSampleAction(getController(), TestClassPanel.this.testClass, sample);

    panel.addMouseListener(new SampleDescriptorPanel.SampleIconMouseListener(action, panel));
    panel.setOpaque(true);
    panel.setBackground(Color.WHITE);
    sampleCache.put(sample, panel);

    return panel;

  }

  /**
   * Returns a white, square spacer element (JPanel) of the given size.
   * 
   * @param size
   *          the size of the space element
   * @return the space element
   */
  private JPanel createSpacerPanel(int size) {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setOpaque(true);
    panel.setBackground(Color.WHITE);
    panel.setPreferredSize(new Dimension(size, size));
    return panel;
  }

  /**
   * Initialize the Sample Descriptor View
 * @param manager 
   * 
   * @param descriptor
   */
  private void init(IDeviceManager manager) {
    initTitle();
    initSampleSection(testClass.getGestures());
    initInputSection(manager);
  }

  /**
   * Captures a local reference of the gesture input device.
   */
  private void initGestureDevice() {
    gestureDevice = getController().getLocator().getService(SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
  }

  /**
   * Creates the input area to capture new gestures.
 * @param manager 
   * 
   * @param descriptor
   */
  private void initInputSection(IDeviceManager manager) {
	  JPanel basePanel = new JPanel();
	    
	    // input area
	    basePanel.setLayout(new BorderLayout());

	    initGestureDevice();
	    
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		cardPanel.setSize(new Dimension(INPUTAREA_SIZE, INPUTAREA_SIZE));
		
		currentDevice = gestureDevice;
		
	    basePanel.add(cardPanel, BorderLayout.CENTER);
	    
	    devicePanel = new DeviceListPanel();
	    for(AbstractGestureDevice<?,?> device : manager.getDevices())
	    	addDevice(device);
	    devicePanel.addDevicePanelListener(this);
	    basePanel.add(devicePanel,BorderLayout.WEST);
	    
	    setBottom(basePanel);	  
  }

  /**
   * Visualizes the samples. The GridBagLayout is used. The number of elements
   * in a row are computed dynamically. Between two gesture elements a space
   * element is placed.
   */
  private synchronized void initSampleSection(List<Gesture<?>> samples) {

    JPanel title = new JPanel();
    title.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel label = getComponentFactory().createLabel(GestureConstants.TESTCLASS_NAME);
    title.add(label);
    JTextField textField = new JTextField();
    Formatter.formatTextField(textField);
    title.add(textField);
    BindingFactory.createInstance(textField, testClass, TestClass.PROPERTY_NAME);

    title.setOpaque(true);
    title.setBackground(Color.WHITE);
    
    JPanel samplePanel = new JPanel();

    samplePanel.setLayout(new GridBagLayout());

    // if the component has size 0 (before it is placed into another container
    // component, don't visualize the samples
    if (getWidth() > 0) {

      int x = 0;
      int y = 1;

      // compute the number of samples shown in a row
      int elementsPerRow = (getWidth() - 20) / (SAMPLE_SIZE + 20);
      elementsPerRow = elementsPerRow * 2 - 1;

      // add a line of space elements before the first row
      GridBagLayouter.addComponent(samplePanel, createSpacerPanel(SPACE_SIZE), 0, 0);

      // iterate over all the samples
      for (final Gesture<?> sample : samples) {
        GridBagLayouter.addComponent(samplePanel, createSampleIcon(sample), x, y);
        GridBagLayouter.addComponent(samplePanel, createSpacerPanel(SPACE_SIZE), x + 1, y);
        if (x + 1 >= elementsPerRow) {
          x = 0;
          y++;
          // add a line (spacer) between two sample rows
          GridBagLayouter.addComponent(samplePanel, createSpacerPanel(SPACE_SIZE), 0, y);
          y++;
        } else {
          // element + spacer = 2
          x = x + 2;
        }

      }
    }

    samplePanel.setOpaque(true);
    samplePanel.setBackground(Color.WHITE);
    samplePanel.setAutoscrolls(true);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.setOpaque(true);
    contentPanel.setBackground(Color.WHITE);

    contentPanel.add(title, BorderLayout.NORTH);
   
    contentPanel.add(samplePanel, BorderLayout.CENTER);

    setContent(contentPanel);
  }

  /**
   * Sets the title of the form
   */
  private void initTitle() {

    StringBuilder sb = new StringBuilder();
    sb.append(testClass.getName());

    JLabel titleLabel = TitleFactory.createStaticTitle(sb.toString());
    BindingFactory.createInstance(titleLabel, testClass, TestClass.PROPERTY_NAME);
    setTitle(titleLabel);
  }

  @Override
  public void refreshUILogic() {
    super.refreshUILogic();
    gestureDevice.clear();
    initSampleSection(testClass.getGestures());
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
		
		//add listener to new device
		device.addGestureHandler(panelMapping.get(device.toString()).getGestureDevicePanel());
		
		repaint();
	}
	
	private InputComponentPanel createInputPanel(GestureDevice<?,?> device)
	{
		InputComponentPanel inputComponentPanel = new InputComponentPanel();
		inputComponentPanel.setLayout(new FlowLayout());
		GestureDevicePanel inputPanelInstance = InputPanelFactory.createPanel(device);
		inputComponentPanel.setGestureDevicePanel(inputPanelInstance);
		inputComponentPanel.add(inputPanelInstance);
		
		// buttons
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new BorderLayout());
	    buttonPanel.add(createAddSampleButton(device),BorderLayout.NORTH);
	    buttonPanel.add(createClearSampleButton(device),BorderLayout.CENTER);
	    
	    inputComponentPanel.add(buttonPanel);	 
		
		return inputComponentPanel;
	}
	
	private void addDevice(AbstractGestureDevice<?,?> device)
	{
//		if(device.getDeviceType() == "2D")
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
//		if(device.getDeviceType() == "2D")
		{
			devicePanel.removeDevice(device);
			//remove input panel
			InputComponentPanel panel = panelMapping.get(device.toString());
			cardPanel.remove(panel);
		}
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
