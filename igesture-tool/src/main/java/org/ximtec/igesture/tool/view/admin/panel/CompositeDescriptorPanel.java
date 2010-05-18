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
import java.net.URL;
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
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.core.composite.Constraint;
import org.ximtec.igesture.core.composite.DefaultConstraint;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceManagerListener;
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
import org.ximtec.igesture.util.XMLParser;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CompositeDescriptorPanel extends DefaultDescriptorPanel<CompositeDescriptor> implements DeviceManagerListener{

	private static final Logger LOGGER = Logger.getLogger(CompositeDescriptorPanel.class.getName());
	
	private static final int INPUTAREA_SIZE = 200;
	
	private static final String SELECT_SET = "Select a set";
	private static final String SELECT_GESTURE = "Select a gesture";
	private static final String SELECT_DEVICE_TYPE = "Select a device type";
	
	private JComboBox cmbSets, cmbGestures, cmbDevices;
	private JCheckBox chkUser, chkDevices;
	private JSpinner spinUser;
	private JList devicesList, gestureList;
	private JButton btnRemove, btnClear, btnAdd;
	
	private IDeviceManager deviceManager;
	
	private Map<String, String> deviceMapping;
	
	private Constraint constraint;
	
	public CompositeDescriptorPanel(Controller controller, CompositeDescriptor descriptor) {
		super(controller, descriptor);
		
		deviceManager = controller.getLocator().getService(DeviceManagerService.IDENTIFIER, IDeviceManager.class);
		deviceManager.addDeviceManagerListener(this);
		constraint = descriptor.getConstraint();
		((DefaultConstraint)constraint).addPropertyChangeListener(controller.getParent().getParent());
		init();
	}

	private void init() 
	{	
		initTitle();		
		initParameterSection();
	    initInputSection();
	}
	
	/**
	 * Create the parameter section to configure the constraint parameters
	 */
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
	    
	    // get the parameters from the constraint and add them to the panel
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
	 * Creates the input area to add gestures to the constraint.
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
			URL path = CompositeDescriptorPanel.class.getClassLoader().getResource("config.xml");
			parser.parse(path.getFile(),"device", nodes);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"Could not parse configuration file (config.xml - devices).",e);
		};
		
		//*** UI ***//
		/* choose gesture set */
		JLabel lblSets = getComponentFactory().createLabel(GestureConstants.COMPOSITE_DESCRIPTOR_GESTURE_SETS);
		cmbSets = new JComboBox(getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class)
		        .getGestureSets().toArray());
		cmbSets.insertItemAt(SELECT_SET, 0);
		cmbSets.setSelectedIndex(0);
		/* choose gesture from the set */
		JLabel lblGestures = getComponentFactory().createLabel(GestureConstants.COMPOSITE_DESCRIPTOR_GESTURE_CLASSES);
		cmbGestures = new JComboBox(new Object[]{SELECT_GESTURE});
		cmbGestures.setEnabled(false);
		
		cmbSets.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// if a gesture set is selected, show the gestures of that set
				
				JComboBox source = (JComboBox) e.getSource();
				
				cmbGestures.removeAllItems();
				cmbGestures.addItem(SELECT_GESTURE);
				
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
		
		/* enable and specify user */
		chkUser = createCheckBox("User:",KeyEvent.VK_U);
		spinUser = new JSpinner(new SpinnerNumberModel(0,0,100,1));
		spinUser.setEnabled(false);
		chkUser.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				//enable or disable user selection
				JCheckBox source = (JCheckBox) e.getSource();
				spinUser.setEnabled(source.isSelected());
			}
			
		});		
		
		/* enable devices and specify kind and specific devices */
		// device type list
		chkDevices = createCheckBox("Device(s):", KeyEvent.VK_D);
		cmbDevices = new JComboBox(deviceMapping.keySet().toArray());
		cmbDevices.setEnabled(false);
		cmbDevices.insertItemAt(SELECT_DEVICE_TYPE, 0);
		cmbDevices.setSelectedIndex(0);
		// specific device list
		devicesList = new JList();
		DefaultListModel devicesModel = new DefaultListModel();
		devicesList.setModel(devicesModel);
		devicesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		devicesList.setEnabled(false);
		// make specific device list scrollable
		final JScrollPane scrollDevicesList = new JScrollPane(devicesList);
		scrollDevicesList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
				"Devices"));		
		scrollDevicesList.setPreferredSize(new Dimension(INPUTAREA_SIZE,INPUTAREA_SIZE/2));
		scrollDevicesList.setEnabled(false);
		
		chkDevices.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// enable or disable device type and device selection
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
				//if the selected device type changed, update the device list ...
				if(source.getSelectedIndex() != 0)//index 0: "select device type"
				{
					//... by getting devices of that type from the device manager
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
		// add components to the content panel
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
				new RemoveGestureClassFromConstraintAction(getController(),this),false); 
		btnClear = createButton(
				GestureConstants.REMOVE_ALL_GESTURE_CLASSES_FROM_CONSTRAINT,
				new RemoveAllGestureClassesFromConstraintAction(getController(),this),false);
		
		cmbGestures.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox)e.getSource();
				// if a valid gesture was selected, allow adding the gesture to the constraint
				if(source.getSelectedIndex() == 0)
					btnAdd.setEnabled(false);
				else
					btnAdd.setEnabled(true);
				
			}
			
		});
		
		contentPanel.add(btnAdd,createGridBagConstraints(1,2,1,1,0.5));
		contentPanel.add(btnRemove,createGridBagConstraints(0,4,1,1,0.5));
		contentPanel.add(btnClear,createGridBagConstraints(1,4,1,1,0.5));
			
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.add(contentPanel,BorderLayout.CENTER);

		// list that displays the composing gestures
		gestureList = new JList();
		DefaultListModel gesturesModel = new DefaultListModel();
		gestureList.setModel(gesturesModel);
		gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//if the constraint already contains composing gestures, ...
		List<DefaultConstraintEntry> entries = getDescriptor().getConstraint().getGestureEntries(); 
		if(entries != null && !entries.isEmpty())
		{
			//...add them to the list ...
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				DefaultConstraintEntry entry = (DefaultConstraintEntry) iterator.next();
				gesturesModel.addElement(entry);
			}
			
			//... and enable removal of the composing gestures
			btnRemove.setEnabled(true);
			btnClear.setEnabled(true);
		}
		
		// make the gesture list scrollable
		JScrollPane scrollResultList = new JScrollPane(gestureList);
		scrollResultList.setPreferredSize(new Dimension(basePanel.getWidth(),INPUTAREA_SIZE/2));
		basePanel.add(scrollResultList,BorderLayout.SOUTH);
		
		setBottom(basePanel);
	}
	
	/**
	 * Create GridBagConstraint
	 * @param component	The component to add.
	 * @param gridx		The x position.
	 * @param gridy		The y position.
	 * @param gridWidth	The width of the component in number of columns.
	 * @param gridHeight The height of the component in number of rows.
	 * @param weightx	
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
	
	/**
	 * Create a button
	 * @param key		key to look up in the guiBundle
	 * @param action	action to bind to the button
	 * @param enabled	true to enable the button
	 * @return
	 */
	private JButton createButton(String key, Action action, boolean enabled)
	{
		JButton btn = getComponentFactory().createButton(key,action);
		btn.setEnabled(enabled);
		Formatter.formatButton(btn);
		return btn;
	}
	
	/**
	 * Create a checkbox
	 */
	private JCheckBox createCheckBox(String text, int mnemonic)
	{
		JCheckBox chk = new JCheckBox(text);
		chk.setMnemonic(mnemonic);
		return chk;
	}
	
	/**
	 * Remove the selected gesture class from the list and constraint
	 */
	public void removeGestureClass()
	{
		// remove selected gesture class from model/list
		DefaultListModel model = (DefaultListModel)gestureList.getModel();
		DefaultConstraintEntry entry = (DefaultConstraintEntry) gestureList.getSelectedValue();
	    model.removeElement(entry);
	    // remove gesture class from constraint
	    getDescriptor().getConstraint().removeGestureClass(entry);
	    
	    // if there are no elements left, disable remove actions
	    if(model.getSize() == 0)
	    {
	    	btnRemove.setEnabled(false);
	    	btnClear.setEnabled(false);
	    }
	}
	
	/**
	 * Remove all gesture classes from the list and constraint
	 */
	public void removeAllGestureClasses()
	{
		// remove all gesture classes from model/list
		DefaultListModel model = (DefaultListModel)gestureList.getModel();
		model.removeAllElements();
		// remove all gesture classes from constraint
		getDescriptor().getConstraint().removeAllGestureClasses();
		
		// disable remove actions
    	btnRemove.setEnabled(false);
    	btnClear.setEnabled(false);
	}
	
	/**
	 * Add a gesture class to the list and constraint
	 */
	public void addGestureClass()
	{		
		String gestureClass = null, deviceType = null;
		int user = -1;
		Set<String> devices = null;
		
		// get the name of the gesture class (name + uuid)
		GestureClass gs = (GestureClass)cmbGestures.getSelectedItem();
		gestureClass = gs.getName()+":"+gs.getId();
		
		// if user specified, get the user
		if(chkUser.isSelected())
			user = ((Integer)spinUser.getValue()).intValue();
		
		// if device type specified, get the device type
		if(chkDevices.isSelected())
		{
			deviceType = cmbDevices.getSelectedItem().toString();
			// if specific devices were selected, get the IDs
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
			// add the gesture class to the constraint
			if(chkUser.isSelected() && chkDevices.isSelected())
				getDescriptor().getConstraint().addGestureClass(gestureClass, user, deviceType, devices);
			else if(chkUser.isSelected())
				getDescriptor().getConstraint().addGestureClass(gestureClass, user);
			else if(chkDevices.isSelected())
				getDescriptor().getConstraint().addGestureClass(gestureClass, deviceType, devices);
			else
				getDescriptor().getConstraint().addGestureClass(gestureClass);
			
			// get the last added gesture and display it in the list
			List<DefaultConstraintEntry> entries = getDescriptor().getConstraint().getGestureEntries();
			DefaultConstraintEntry entry = entries.get(entries.size()-1);			
			DefaultListModel model = (DefaultListModel)gestureList.getModel();
		    model.addElement(entry);
		}
		catch(IllegalArgumentException e)
		{
			// if an illegal argument was entered by the user, notify the user and allow correction
			LOGGER.log(Level.SEVERE,"Constraint Configuration Error.",e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Constraint Configuration Error", JOptionPane.ERROR_MESSAGE);
		}
	    
		// now that data was added, enable removal of gesture classes
	    btnClear.setEnabled(true);
	    btnRemove.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.DeviceManagerListener#updateDeviceManagerListener(int, org.ximtec.igesture.io.AbstractGestureDevice)
	 */
	@Override
	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?, ?> device) {
		//if a valid device type is selected, update the device list if devices of that type are added or removed 
		if(cmbDevices.getSelectedIndex() != 0) // index 0: "select a device type"
		{
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
