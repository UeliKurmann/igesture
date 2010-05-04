/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ximtec.igesture.MultimodalGestureManager;
import org.ximtec.igesture.MultimodalGestureRecogniser;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.signature.SiGridAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.core.composite.Constraint;
import org.ximtec.igesture.core.composite.DefaultConstraint;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.core.composite.IntervalConstraint;
import org.ximtec.igesture.core.composite.ProximitySequenceConstraint;
import org.ximtec.igesture.core.composite.SequenceConstraint;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceManagerListener;
import org.ximtec.igesture.io.GestureEventListener;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.ConstraintTextFieldBinding;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.service.DeviceManagerService;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.admin.action.AddGestureClassToConstraintAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveAllGestureClassesFromConstraintAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureClassFromConstraintAction;
import org.ximtec.igesture.tool.view.devicemanager.XMLParser;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CompositeDescriptorPanel extends DefaultDescriptorPanel<CompositeDescriptor> implements DeviceManagerListener{

	private static final Logger LOGGER = Logger.getLogger(CompositeDescriptorPanel.class.getName());
	
	private static final int INPUTAREA_SIZE = 200;
	
//	private MainModel mainModel;
	
	private JComboBox cmbSets, cmbGestures, cmbDevices;
	private JCheckBox chkUser, chkDevices;
	private JSpinner spinUser;
	private JList devicesList, resultList;
	private JButton btnRemove, btnClear, btnAdd;
	
//	private GuiBundleService guiBundle;
	
	private IDeviceManager deviceManager;
	private MultimodalGestureRecogniser mmrecogniser;
	private MultimodalGestureManager mmmanager;
	
	private Map<String, String> deviceMapping;
	
	private Constraint constraint;
	
	public CompositeDescriptorPanel(Controller controller, CompositeDescriptor descriptor) {
		super(controller, descriptor);
		
		deviceManager = controller.getLocator().getService(DeviceManagerService.IDENTIFIER, IDeviceManager.class);
		deviceManager.addDeviceManagerListener(this);
//		mainModel = controller.getLocator().getService(MainModel.IDENTIFIER, MainModel.class);
//		guiBundle = controller.getLocator().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class);
		constraint = descriptor.getConstraint();
		init();
	}

	private void init() 
	{	
		initTitle();		
		initParameterSection();
	    initInputSection();
//		initMultimodalRecogniser();
	}
	
	/**
	 * 
	 */
	private void initMultimodalRecogniser() {
		//create mm recogniser
		Object[] objects = getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class)
        .getGestureSets().toArray();
		GestureSet simpleGestures = (GestureSet) objects[0];
		
//		GestureClass clazz = new GestureClass("sequence");
//		Constraint constraint = new ProximitySequenceConstraint();
//		Set<String> ids = new HashSet<String>();
//		ids.add("3333");
//		constraint.addGestureClass("square",0,TuioReader2D.class.getSimpleName(),ids);
//		constraint.addGestureClass("line",0);
//		try {
//			((ProximitySequenceConstraint)constraint).setMinTime("00:00:01.000");
//			((ProximitySequenceConstraint)constraint).setMaxTime("00:01:00.000");
//			((ProximitySequenceConstraint)constraint).setDistanceUnit(Constant.MM);
//			((ProximitySequenceConstraint)constraint).setMinDistance(0);
//			((ProximitySequenceConstraint)constraint).setMaxDistance(100);
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
		
		GestureClass clazz = new GestureClass("interval");
		Constraint constraint = new IntervalConstraint();
		constraint.addGestureClass("square");
		constraint.addGestureClass("line");
		try {
			((IntervalConstraint)constraint).setDuration("00:00:30.000");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Descriptor d = new CompositeDescriptor(constraint);
		clazz.addDescriptor(d);
		GestureSet compositeGestures = new GestureSet("composites");
		compositeGestures.addGestureClass(clazz);		
		
		mmrecogniser = new MultimodalGestureRecogniser(compositeGestures,deviceManager);
		//create manager
		mmmanager = new MultimodalGestureManager(mmrecogniser);
		//subscribe recognisers
		Configuration config = new Configuration();
		config.addGestureSet(simpleGestures);
		config.addAlgorithm(SiGridAlgorithm.class.getName());
		
		try {
			final Recogniser recogniser = new Recogniser(config);
			for(Iterator<AbstractGestureDevice<?,?>> iterator = deviceManager.getDevices().iterator();iterator.hasNext();)
			{
				AbstractGestureDevice<?,?> device = iterator.next();
				device.addGestureHandler(new GestureEventListener(){

					@Override
					public void handleChunks(List<?> chunks) {
					}

					@Override
					public void handleGesture(Gesture<?> gesture) {
						recogniser.recognise(gesture);
					}
					
				});
			}
			mmmanager.addRecogniser(recogniser, false);
		} catch (AlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	private void initParameterSection()
	{		
		JPanel panel = new JPanel();
										
		FormLayout layout = new FormLayout(
		        "100dlu, 4dlu, 100dlu",
		        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

	    DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	    builder.setDefaultDialogBorder();

	    builder.append(getComponentFactory().createLabel(GestureConstants.CONFIGURATION_PANEL_PARAMETERS));
	    builder.nextLine(2);

	    Map<String, String> parameter = getDescriptor().getConstraint().getParameters();
	    ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String,String>();
	    map.putAll(parameter);
	    for (String parameterName : parameter.keySet()) {
	      builder.append(new JLabel(parameterName));
	      JTextField paramTextField = new JTextField();
	      new ConstraintTextFieldBinding(paramTextField, (DefaultConstraint)constraint, parameterName);
	      builder.append(paramTextField);
	      builder.nextLine(2);
	    }
	    
	    JPanel paramPanel = builder.getPanel();
	    paramPanel.setOpaque(true);
	    paramPanel.setBackground(Color.WHITE);

		panel.add(paramPanel,BorderLayout.CENTER);

	    panel.setOpaque(true);
	    panel.setAutoscrolls(true);
	    setContent(panel);
	}
	
	/**
	 * Creates the input area to capture new gestures.
	 * @param manager 
	 */
	private void initInputSection()
	{		
		/* create device mapping */
		deviceMapping = new HashMap<String, String>();
		XMLParser parser = new XMLParser(){

			@Override
			public void execute(ArrayList<NodeList> nodeLists) {
				String name = ((Node)nodeLists.get(0).item(0)).getNodeValue();
				String clazz = ((Node)nodeLists.get(1).item(0)).getNodeValue();
				deviceMapping.put(name, clazz);
			}
			
		};
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("name");
		nodes.add("class");
		try {
			parser.parse("/home/bjorn/develop/igesture/igesture-tool/src/main/resources/config.xml","device", nodes);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"Could not parse configuration file (config.xml - devices).",e);
		};
		
		/* UI */
		//choose gesture set and gesture
		JLabel lblSets = new JLabel("Gesture sets:");
		cmbSets = new JComboBox(getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class)
		        .getGestureSets().toArray());
		cmbSets.insertItemAt("Select a set", 0);
		cmbSets.setSelectedIndex(0);
		
		JLabel lblGestures = new JLabel("Gesture classes:");
		cmbGestures = new JComboBox(new Object[]{"Select a gesture"});
		cmbGestures.setEnabled(false);
		
		cmbSets.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JComboBox source = (JComboBox) e.getSource();
				
				cmbGestures.removeAllItems();
				cmbGestures.addItem("Select a gesture");
				
				if(source.getSelectedIndex() == 0)
				{
					cmbGestures.setEnabled(false);
				}
				else
				{
					cmbGestures.setEnabled(true);
					GestureSet set = (GestureSet) cmbSets.getSelectedItem();

					Object[] items = set.getGestureClasses().toArray();
					for (int i = 0; i < items.length; i++) {
						cmbGestures.addItem(items[i]);
					}
				}				
			}
			
		});	
		
		// enable and specify user
		chkUser = createCheckBox("User:",KeyEvent.VK_U);
		spinUser = new JSpinner(new SpinnerNumberModel(0,0,100,1));
		spinUser.setEnabled(false);
		chkUser.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox source = (JCheckBox) e.getSource();
				spinUser.setEnabled(source.isSelected());
			}
			
		});		
		
		// enable devices and specify kind and specific devices
		chkDevices = createCheckBox("Device(s):", KeyEvent.VK_D);
		cmbDevices = new JComboBox(deviceMapping.keySet().toArray());
		cmbDevices.setEnabled(false);
		cmbDevices.insertItemAt("Select a device type", 0);
		cmbDevices.setSelectedIndex(0);
		devicesList = new JList();
		DefaultListModel model = new DefaultListModel();
		devicesList.setModel(model);
		devicesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		devicesList.setEnabled(false);
		final JScrollPane scrollDevicesList = new JScrollPane(devicesList);
		scrollDevicesList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
				"Devices"));		
		scrollDevicesList.setPreferredSize(new Dimension(INPUTAREA_SIZE,INPUTAREA_SIZE/2));
		scrollDevicesList.setEnabled(false);
		chkDevices.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox source = (JCheckBox)e.getSource();
				boolean selected = source.isSelected();
				cmbDevices.setEnabled(selected);
				scrollDevicesList.setEnabled(selected);
				devicesList.setEnabled(selected);
			}
			
		});
		cmbDevices.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox)e.getSource();
				if(source.getSelectedIndex() != 0)
				{
					DefaultListModel model = new DefaultListModel();
					for(Iterator<AbstractGestureDevice<?,?>> iterator = deviceManager.getDevices().iterator(); iterator.hasNext();)
					{
						AbstractGestureDevice< ?, ?> device = iterator.next();
						if(device.getClass().getName().equals(deviceMapping.get(source.getSelectedItem())))//TODO reflection
						{
							model.addElement(device.getDeviceID());
						}
					}
					devicesList.setModel(model);
				}
				else
					devicesList.setModel(new DefaultListModel());
			}
			
		});
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.add(lblSets,createGridBagConstraints(0,0,1,1,0.5));
		contentPanel.add(cmbSets,createGridBagConstraints(1,0,1,1,0.5));
		contentPanel.add(lblGestures,createGridBagConstraints(0,1,1,1,0.5));
		contentPanel.add(cmbGestures,createGridBagConstraints(1,1,1,1,0.5));
		contentPanel.add(chkUser,createGridBagConstraints(2,0,1,1,0.5));
		contentPanel.add(spinUser,createGridBagConstraints(3,0,1,1,0.5));
		contentPanel.add(chkDevices,createGridBagConstraints(2,1,1,1,0.5));
		contentPanel.add(cmbDevices,createGridBagConstraints(3,1,1,1,0.5));
		contentPanel.add(scrollDevicesList,createGridBagConstraints(3,2,1,3,0.5));
		
		btnAdd = createButton(
				GestureConstants.ADD_GESTURE_CLASS_TO_CONSTRAINT,
				new AddGestureClassToConstraintAction(getController(),this),false);
		btnRemove = createButton(
				GestureConstants.REMOVE_GESTURE_CLASS_FROM_CONSTRAINT,
				new RemoveGestureClassFromConstraintAction(getController(),this,getDescriptor()),false); 
		btnClear = createButton(
				GestureConstants.REMOVE_ALL_GESTURE_CLASSES_FROM_CONSTRAINT,
				new RemoveAllGestureClassesFromConstraintAction(getController(),this,getDescriptor()),false);
		
		cmbGestures.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox)e.getSource();
				if(source.getSelectedIndex() == 0)
					btnAdd.setEnabled(false);
				else
					btnAdd.setEnabled(true);
				
			}
			
		});
		
		contentPanel.add(btnAdd,createGridBagConstraints(1,2,1,1,0.5));
		contentPanel.add(btnRemove,createGridBagConstraints(1,3,1,1,0.5));
		contentPanel.add(btnClear,createGridBagConstraints(1,4,1,1,0.5));
			
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.add(contentPanel,BorderLayout.CENTER);

		//output
		resultList = new JList();
		resultList.setModel(new DefaultListModel());
		resultList.setEnabled(false);
		
		List<DefaultConstraintEntry> entries = getDescriptor().getConstraint().getGestureEntries(); 
		if(entries != null && !entries.isEmpty())
		{
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				DefaultConstraintEntry entry = (DefaultConstraintEntry) iterator.next();
				printGestureClass(entry.getGesture(), entry.getUser(), entry.getDeviceType(), entry.getDevices());
			}
		}
		
		JScrollPane scrollResultList = new JScrollPane(resultList);
		scrollResultList.setPreferredSize(new Dimension(basePanel.getWidth(),INPUTAREA_SIZE/2));
		basePanel.add(scrollResultList,BorderLayout.SOUTH);
		
		setBottom(basePanel);
	}
	
	/*
	 * Create GridBagConstraint
	 * @param component	The component to add.
	 * @param gridx		The x position.
	 * @param gridy		The y position.
	 * @param gridWidth	The width of the component in number of columns.
	 */
	private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight, double weightx)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridWidth;
		c.gridheight = gridHeight;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = weightx;
		return c;		
	}
	
	private JButton createButton(String key, Action action, boolean enabled)
	{
		JButton btn = getComponentFactory().createButton(key,action);
		btn.setEnabled(enabled);
		Formatter.formatButton(btn);
		return btn;
	}
	
	private JCheckBox createCheckBox(String title, int mnemonic)
	{
		JCheckBox chk = new JCheckBox(title);
		chk.setMnemonic(mnemonic);
		return chk;
	}
	
	private class RecordButton extends JButton
	{
		private boolean recording;
		
		public RecordButton()
		{
			recording = false;
			setText("Record");
			addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					recording = !recording;
					if(recording == true)
					{
						//TODO
						setText("Stop");
						mmrecogniser.start();
					}
					else
					{
						//TODO
						setText("Record");
						mmrecogniser.stop();
					}					
				}
				
			});
		}
	}
	
	public void removeGestureClass()
	{
		DefaultListModel model = (DefaultListModel)resultList.getModel();
	    model.removeElementAt(model.getSize()-1);
	    if(model.getSize() == 0)
	    {
	    	btnRemove.setEnabled(false);
	    	btnClear.setEnabled(false);
	    }
	}
	
	public void removeAllGestureClasses()
	{
		DefaultListModel model = (DefaultListModel)resultList.getModel();
		model.removeAllElements();
    	btnRemove.setEnabled(false);
    	btnClear.setEnabled(false);
	}
	
	public void addGestureClass()
	{		
		String gestureClass = null, deviceType = null;
		int user = -1;
		Set<String> devices = null;
		
		GestureClass gs = (GestureClass)cmbGestures.getSelectedItem();
		gestureClass = gs.getName()+":"+gs.getId();
		
		if(chkUser.isSelected())
			user = ((Integer)spinUser.getValue()).intValue();
		if(chkDevices.isSelected())
		{
			deviceType = cmbDevices.getSelectedItem().toString();		
			if(!devicesList.isSelectionEmpty())
			{
				devices = new HashSet<String>();
				Object[] values = devicesList.getSelectedValues();
				for (int i = 0; i < values.length; i++) {
					devices.add(values[i].toString());
				}
			}
		}
		try
		{
			if(chkUser.isSelected() && chkDevices.isSelected())
				getDescriptor().getConstraint().addGestureClass(gestureClass, user, deviceType, devices);
			else if(chkUser.isSelected())
				getDescriptor().getConstraint().addGestureClass(gestureClass, user);
			else if(chkDevices.isSelected())
				getDescriptor().getConstraint().addGestureClass(gestureClass, deviceType, devices);
			else
				getDescriptor().getConstraint().addGestureClass(gestureClass);
			
			printGestureClass(gestureClass, user, deviceType, devices);
		}
		catch(IllegalArgumentException e)
		{
			LOGGER.log(Level.SEVERE,"Constraint Configuration Error.",e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Constraint Configuration Error", JOptionPane.ERROR_MESSAGE);
		}
	    
	    btnClear.setEnabled(true);
	    btnRemove.setEnabled(true);
	}
	
	public void printGestureClass(String gestureClass, int user, String deviceType, Set<String> deviceIDs)
	{		
		StringBuilder builder = new StringBuilder("Gesture: ");
		builder.append(gestureClass);
		
		builder.append(", User: ");
		if(user == -1)
			builder.append("any");
		else
			builder.append(user);	
		
		builder.append(", Device Type: ");
		if(deviceType != null)
		{ 
			builder.append(deviceType);
			builder.append(", Device ID(s): ");
			if(deviceIDs != null && !deviceIDs.isEmpty())
			{
				for (Iterator iterator = deviceIDs.iterator(); iterator.hasNext();) {
					String id = (String) iterator.next();
					builder.append(id);
					if(iterator.hasNext())
						builder.append(" - ");
				}
			}
			else
				builder.append("any");				
		}
		else
			builder.append("any");
		
		DefaultListModel model = (DefaultListModel)resultList.getModel();
	    model.addElement(builder.toString());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.DeviceManagerListener#updateDeviceManagerListener(int, org.ximtec.igesture.io.AbstractGestureDevice)
	 */
	@Override
	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?, ?> device) {
		if(cmbDevices.getSelectedIndex() != 0)
		{
			System.out.println(device.getClass().getSimpleName());
			System.out.println(cmbDevices.getSelectedItem().toString());
			if(operation == DeviceManagerListener.ADD && device.getClass().getSimpleName().equals(cmbDevices.getSelectedItem().toString()))
			{
				((DefaultListModel)devicesList.getModel()).addElement(device.getDeviceID());
			} 
			else if(operation == DeviceManagerListener.REMOVE && device.getClass().getSimpleName().equals(cmbDevices.getSelectedItem().toString()))
			{
				((DefaultListModel)devicesList.getModel()).removeElement(device.getDeviceID());
			}
		}
	}

}
