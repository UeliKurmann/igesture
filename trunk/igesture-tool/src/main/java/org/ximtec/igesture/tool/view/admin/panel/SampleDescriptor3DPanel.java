/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.graphix.GridBagLayouter;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceManagerListener;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.gesturevisualisation.GesturePanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputComponentPanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanelFactory;
import org.ximtec.igesture.tool.service.DeviceManagerService;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.FontFactory;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.view.DeviceListPanel;
import org.ximtec.igesture.tool.view.DeviceListPanelListener;
import org.ximtec.igesture.tool.view.admin.action.AddGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureSampleAction;
import org.ximtec.igesture.util.Constant;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

/**
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class SampleDescriptor3DPanel extends DefaultDescriptorPanel<SampleDescriptor3D> implements
				DeviceListPanelListener, DeviceManagerListener {

	private static final int INPUTAREA_SIZE = 200;
	private static final int SPACE_SIZE = 5;
	private static final int SAMPLE_SIZE = 100;
	 
	private GestureDevice<?, ?> gestureDevice;
	private GestureDevice<?, ?> currentDevice;
	
	private Map<Gesture<RecordedGesture3D>, JPanel> sampleCache;
	  
	private DeviceListPanel devicePanel;
	private JPanel cardPanel; 
	
	private Map<String, InputComponentPanel> panelMapping;
	
	/**
	 * Constructor
	 * @param controller
	 * @param descriptor
	 */
	public SampleDescriptor3DPanel(Controller controller, final SampleDescriptor3D descriptor) {
		super(controller, descriptor);
		
		this.sampleCache = new HashMap<Gesture<RecordedGesture3D>, JPanel>();
	    this.panelMapping = new HashMap<String, InputComponentPanel>();
	    
	    // component listener to handle resize actions
	    addComponentListener(new ComponentAdapter() {
	      @Override
	      public void componentResized(ComponentEvent e) {
	        initSampleSection(descriptor.getSamples());
	      }
	    });

	    DeviceManagerService manager = controller.getLocator().getService(DeviceManagerService.IDENTIFIER, DeviceManagerService.class);
	    manager.addDeviceManagerListener(this);
	    
	    init(descriptor, manager);
	}

	private JButton createAddSampleButton(SampleDescriptor3D sampleDescriptor3D, GestureDevice<?, ?> device) {
		    JButton addSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_ADD,
		        new AddGestureSampleAction(getController(), sampleDescriptor3D, device));
		    Formatter.formatButton(addSampleButton);
		    return addSampleButton;
	}

	  private JButton createClearSampleButton(GestureDevice<?,?> gestureDevice) {
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
	  private synchronized JPanel createSampleIcon(final Gesture<RecordedGesture3D> sample) {

	    if (sampleCache.containsKey(sample)) {
	      return sampleCache.get(sample);
	    }

	    GesturePanel gesturePanel = InputPanelFactory.createGesturePanel(sample);
	    final JPanel panel = gesturePanel.getPanel(new Dimension(SAMPLE_SIZE, SAMPLE_SIZE));
	    panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	    panel.addMouseListener(new SampleIconMouseListener(new RemoveGestureSampleAction(getController(), getDescriptor(),
	        sample), panel));
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
	   * 
	   * @param descriptor
	 * @param manager 
	   */
	  private void init(SampleDescriptor3D descriptor, IDeviceManager manager) {
	    initTitle();
	    initSampleSection(descriptor.getSamples());
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
		
		currentDevice = null;
		
	    
	    JLabel title = getComponentFactory().createLabel(GestureConstants.SAMPLE_DESCRIPTOR_3D_TITLE);
	    title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    title.setFont(FontFactory.getArialBold(18));
	    
	    basePanel.add(title, BorderLayout.NORTH);
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
	  private synchronized void initSampleSection(List<Gesture<RecordedGesture3D>> samples) {

	    JPanel panel = new JPanel();

	    panel.setLayout(new GridBagLayout());

	    // if the component has size 0 (before it is placed into another container
	    // component, don't visualize the samples
	    if (getWidth() > 0) {

	      int x = 0;
	      int y = 1;

	      // compute the number of samples shown in a row
	      int elementsPerRow = (getWidth() - 20) / (SAMPLE_SIZE + 20);
	      elementsPerRow = elementsPerRow * 2 - 1;

	      // add a line of space elements before the first row
	      GridBagLayouter.addComponent(panel, createSpacerPanel(SPACE_SIZE), 0, 0);

	      // iterate over all the samples
	      for (final Gesture<RecordedGesture3D> sample : samples) {
	        GridBagLayouter.addComponent(panel, createSampleIcon(sample), x, y);
	        GridBagLayouter.addComponent(panel, createSpacerPanel(SPACE_SIZE), x + 1, y);
	        if (x + 1 >= elementsPerRow) {
	          x = 0;
	          y++;
	          // add a line (spacer) between two sample rows
	          GridBagLayouter.addComponent(panel, createSpacerPanel(SPACE_SIZE), 0, y);
	          y++;
	        } else {
	          // element + spacer = 2
	          x = x + 2;
	        }

	      }
	    }

	    panel.setOpaque(true);
	    panel.setAutoscrolls(true);
	    setContent(panel);
	  }

	  @Override
	  public void refreshUILogic() {
	    super.refreshUILogic();
	    gestureDevice.clear();
	    initSampleSection(getDescriptor().getSamples());
	  }

	  /**
	   * Context Menu for the sample elements
	   * 
	   * @author UeliKurmann
	   * 
	   */
	  public static class SampleIconMouseListener extends MouseAdapter {
	    private final Action action;
	    private final JPanel gesturePanel;

	    public SampleIconMouseListener(Action action, JPanel gesturePanel) {
	      this.gesturePanel = gesturePanel;
	      this.action = action;
	    }

	    @Override
	    public void mouseClicked(MouseEvent arg0) {
	      popUp(arg0);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
	      popUp(e);
	    }
	    
	    @Override
	    public void mousePressed(MouseEvent e) {
	      popUp(e);
	    }

	    private void popUp(MouseEvent e) {
	      if (e.isPopupTrigger()) {
	        JPopupMenu menu = new JPopupMenu();
	        JMenuItem item = new JMenuItem();
	        item.setAction(action);
	        menu.add(item);
	        menu.show(gesturePanel, e.getX(), e.getY());
	      }
	    }
	  }

		/* (non-Javadoc)
		 * @see org.ximtec.igesture.tool.view.DevicePanelListener#update(org.ximtec.igesture.io.AbstractGestureDevice)
		 */
		@Override
		public void updateDeviceListPanelListener(AbstractGestureDevice<?, ?> device) {
			
			//remove listener from current device
			if(currentDevice != null)//!panelMapping.isEmpty())
				currentDevice.removeGestureHandler(panelMapping.get(currentDevice.toString()).getGestureDevicePanel());
			//change input panel
			((CardLayout)cardPanel.getLayout()).show(cardPanel, device.toString());
			currentDevice = device;
			
			//add listener to new device
			device.addGestureHandler(panelMapping.get(device.toString()).getGestureDevicePanel());
			
			repaint();
//			paintAll(getGraphics());
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
		    buttonPanel.add(createAddSampleButton(getDescriptor(),device),BorderLayout.NORTH);//TODO
		    buttonPanel.add(createClearSampleButton(device),BorderLayout.CENTER);
		    
		    inputComponentPanel.add(buttonPanel);	 
			
			return inputComponentPanel;
		}

		private void addDevice(AbstractGestureDevice<?,?> device)
		{
			if(Constant.TYPE_3D.equals(device.getDeviceType()))
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
			if(Constant.TYPE_3D.equals(device.getDeviceType()))
			{
				devicePanel.removeDevice(device);
				//remove input panel
				InputComponentPanel panel = panelMapping.get(device.toString());
				cardPanel.remove(panel);
			}
		}
		
		/* (non-Javadoc)
		 * @see org.ximtec.igesture.tool.view.devicemanager.DeviceManagerListener#update(int, org.ximtec.igesture.io.AbstractGestureDevice)
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
