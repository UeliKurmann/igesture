/**
 * 
 */
package org.ximtec.igesture.tool.view.composite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ximtec.igesture.MultimodalGestureHandler;
import org.ximtec.igesture.MultimodalGestureManager;
import org.ximtec.igesture.MultimodalGestureRecogniser;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.event.GestureHandler;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceManagerListener;
import org.ximtec.igesture.io.GestureEventListener;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.service.DeviceManagerService;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.composite.action.AddRecogniserAction;
import org.ximtec.igesture.tool.view.composite.action.RecogniseAction;
import org.ximtec.igesture.tool.view.composite.action.ResetAction;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CompositeView extends AbstractPanel implements TabbedView, DeviceManagerListener{

	private Map<String, Class> algMapping;
	
	private IDeviceManager deviceManager;
	private MultimodalGestureRecogniser mmrecogniser;
	private MultimodalGestureManager mmmanager;
	
	private List<Recogniser> recognisers;
	private List<Connector> connectors;
	
	private JButton addRecogniserButton, btnRecognise, btnReset;
	private JComboBox cmbSets;
	private JList deviceList, resultList, algList, setList;
	
	public CompositeView(Controller controller) {
		super(controller);
		deviceManager = getController().getLocator().getService(DeviceManagerService.IDENTIFIER, DeviceManagerController.class);
		deviceManager.addDeviceManagerListener(this);
		recognisers = new ArrayList<Recogniser>();
		connectors = new ArrayList<Connector>();
		init();		
	}

	/**
	 * 
	 */
	private void init() {
		setTitle(TitleFactory.createStaticTitle(GestureConstants.COMPOSITE_TEST_BENCH_VIEW));
		setContent(initConfigurationPanel());
		setBottom(initMultimodalPanel());
	}

	/**
	 * @return
	 */
	private JPanel initMultimodalPanel() {
		JPanel configPanel = new JPanel();
		
		JLabel lblSets = getComponentFactory().createLabel(GestureConstants.COMPOSITE_GESTURE_SET);
		cmbSets = new JComboBox(getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class)
		        .getGestureSets().toArray());
		cmbSets.insertItemAt("Select a set", 0);
		cmbSets.setSelectedIndex(0);
		cmbSets.setEnabled(false);
		
		btnRecognise = createButton(GestureConstants.COMPOSITE_RECOGNISE_ACTION,
				new RecogniseAction(getController()), false);
		
		btnReset = createButton(GestureConstants.COMPOSITE_RESET_ACTION,
				new ResetAction(getController()), false);
		
		cmbSets.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				
				if(source.getSelectedIndex() == 0)
				{
					// disable record
					btnRecognise.setEnabled(false);
				}
				else
				{
					GestureSet set = (GestureSet) cmbSets.getSelectedItem();
					// configure multi-modal recogniser
					boolean validConfig = initMultimodalRecogniser(set);
					if(validConfig)
					{
						// enable record
						btnRecognise.setEnabled(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Please select a set that contains composite gestures!","Error",JOptionPane.ERROR_MESSAGE);
						btnRecognise.setEnabled(false);
					}

				}				
			}
			
		});	
		
		configPanel.add(lblSets);
		configPanel.add(cmbSets);
		configPanel.add(btnRecognise);
		configPanel.add(btnReset);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(configPanel,BorderLayout.CENTER);

		resultList = createList(null);
		resultList.setEnabled(false);
		JScrollPane scrollResultList = new JScrollPane(resultList);
		scrollResultList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),"Results"));
		panel.add(scrollResultList,BorderLayout.SOUTH);
		
		return panel;
	}

	/**
	 * @return
	 */
	private JPanel initConfigurationPanel() {		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		algMapping = new HashMap<String, Class>();
		for (Iterator iterator = getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class).getAlgorithms().iterator(); iterator.hasNext();) {
			Class<? extends Algorithm> alg = (Class<? extends Algorithm>) iterator.next();
			algMapping.put(alg.getSimpleName(),alg);
		}
		
		algList = createList(algMapping.keySet());
		panel.add(createScrollableList("Algorithms:", algList),	createGridBagConstraints(0, 0, 1, 0.5));
		setList = createList(getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class).getGestureSets()); 
		panel.add(createScrollableList("Gesture Sets:", setList), createGridBagConstraints(1, 0, 1, 0.5));
		deviceList = createList(getController().getLocator().getService(DeviceManagerService.IDENTIFIER, DeviceManagerController.class).getDevices());
		panel.add(createScrollableList("Devices:", deviceList), createGridBagConstraints(2, 0, 1, 0.5));
		addRecogniserButton = createButton(GestureConstants.COMPOSITE_ADD_RECOGNISER_ACTION,
				new AddRecogniserAction(getController()), false); 
		panel.add(addRecogniserButton, createGridBagConstraints(2, 2, 1, 0.5));
		
		ListSelectionListener listener = new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if(!e.getValueIsAdjusting())
				{
					if(!algList.isSelectionEmpty() && !setList.isSelectionEmpty() && !deviceList.isSelectionEmpty())
						addRecogniserButton.setEnabled(true);
					else
						addRecogniserButton.setEnabled(false);
				}
			}
			
		};
		
		algList.addListSelectionListener(listener);
		setList.addListSelectionListener(listener);
		deviceList.addListSelectionListener(listener);
		
		return panel;
	}
	
	private JScrollPane createScrollableList(String title, JList list)
	{
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),title));
		return scroll;
	}
	
	private JList createList(Collection data)
	{
		JList list = new JList();
		DefaultListModel model = new DefaultListModel();
		list.setModel(model);
		if(data != null)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				model.addElement(object);
			}
		}
		return list;
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
	private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridWidth, double weightx)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridWidth;
		c.fill = GridBagConstraints.BOTH;
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
	

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.core.TabbedView#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.core.TabbedView#getPane()
	 */
	@Override
	public JComponent getPane() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.core.TabbedView#getTabName()
	 */
	@Override
	public String getTabName() {
		return getComponentFactory().getGuiBundle().getName(
				GestureConstants.COMPOSITE_TEST_BENCH_VIEW);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.DeviceManagerListener#updateDeviceManagerListener(int, org.ximtec.igesture.io.AbstractGestureDevice)
	 */
	@Override
	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?, ?> device) {
		if(operation == DeviceManagerListener.ADD)
		{
			((DefaultListModel)deviceList.getModel()).addElement(device);
		} 
		else if(operation == DeviceManagerListener.REMOVE)
		{
			((DefaultListModel)deviceList.getModel()).removeElement(device);
		}
	}
	
	private boolean initMultimodalRecogniser(GestureSet set) {
		//create mm recogniser
		GestureSet compositeGestures = new GestureSet();
		
		List<GestureClass> gestures = set.getGestureClasses();
		for (Iterator iterator = gestures.iterator(); iterator.hasNext();) {
			GestureClass gestureClass = (GestureClass) iterator.next();
			Descriptor d = gestureClass.getDescriptor(CompositeDescriptor.class);
			if(d != null)
				compositeGestures.addGestureClass(gestureClass);
		}
		
		if(compositeGestures.size()>0)
		{
			mmrecogniser = new MultimodalGestureRecogniser(compositeGestures,deviceManager);
			
			mmrecogniser.addGestureHandler(new MultimodalGestureHandler(){

				@Override
				public void handle(String gestureClass) {
					//System.out.println("Multi-modal Gesture Handler: "+gestureClass);
					DefaultListModel model = (DefaultListModel)resultList.getModel();
					model.addElement(gestureClass);
				}
				
			});
			
			//create manager
			mmmanager = new MultimodalGestureManager(mmrecogniser);
			for (Iterator iterator = recognisers.iterator(); iterator.hasNext();) {
				Recogniser recogniser = (Recogniser) iterator.next();
				mmmanager.addRecogniser(recogniser, false);				
			}			
			return true;
		}
		else
			return false;

	}

	private class Connector implements GestureEventListener
	{
		private Recogniser recogniser;
		
		public Connector(Recogniser recogniser)
		{
			this.recogniser = recogniser;
		}
		
		/* (non-Javadoc)
		 * @see org.ximtec.igesture.io.GestureEventListener#handleChunks(java.util.List)
		 */
		@Override
		public void handleChunks(List<?> chunks) {
		}

		/* (non-Javadoc)
		 * @see org.ximtec.igesture.io.GestureEventListener#handleGesture(org.ximtec.igesture.core.Gesture)
		 */
		@Override
		public void handleGesture(Gesture<?> gesture) {
			recogniser.recognise(gesture);
		}
		
	}

	/**
	 * Reset Command
	 */
	public void reset() {
		//reset UI 
		cmbSets.setSelectedIndex(0);
		cmbSets.setEnabled(false);
		btnRecognise.setEnabled(false);
		
		algList.clearSelection();
		algList.setEnabled(true);
		setList.clearSelection();
		setList.setEnabled(true);
		deviceList.clearSelection();
		deviceList.setEnabled(true);
		
		((DefaultListModel)resultList.getModel()).clear();
		
		//reset configuration
		recognisers.clear();
		connectors.clear();
		Object[] dev = deviceList.getSelectedValues();
		for (int i = 0; i < dev.length; i++) {
			AbstractGestureDevice<?,?> device = (AbstractGestureDevice<?,?>)dev[i];
			device.removeAllGestureHandler();
		}
		
		btnReset.setEnabled(false);
	}

	/**
	 * Recognise Command
	 * @param startRecognising true to start recognising, false to stop recognising
	 */
	public void recognise(boolean startRecognising) {
		if(startRecognising == true)
		{
			btnRecognise.setText("Stop");
			mmrecogniser.start();
			cmbSets.setEnabled(false);
			
			deviceList.setEnabled(false);
			algList.setEnabled(false);
			setList.setEnabled(false);
			addRecogniserButton.setEnabled(false);
			btnReset.setEnabled(false);
		}
		else
		{
			btnRecognise.setText("Recognise");
			mmrecogniser.stop();
			cmbSets.setEnabled(true);
			btnReset.setEnabled(true);
		}
	}

	/**
	 * Add Recogniser Command
	 */
	public void addRecogniser() {
		// create config
		Configuration config = new Configuration();
		// add gesture sets
		Object[] sets = setList.getSelectedValues();
		for (int i = 0; i < sets.length; i++) {
			config.addGestureSet((GestureSet)sets[i]);
		}
		// add algorithms
		Object[] algs = algList.getSelectedValues();
		for (int i = 0; i < algs.length; i++) {
			config.addAlgorithm(algMapping.get(algs[i]).getName());
		}
		
		try {
			//create recogniser
			Recogniser recogniser = new Recogniser(config);
			recognisers.add(recogniser);
			
			recogniser.addGestureHandler(new GestureHandler(){

				@Override
				public void handle(ResultSet resultSet) {
			//		System.out.println("GestureHandler: "+resultSet.getGesture().getName());
					DefaultListModel model = (DefaultListModel) resultList.getModel();
					model.addElement(resultSet.getGesture().getName());
				}
				
			});
			
			
			//create connection between device and recogniser
			Connector connector = new Connector(recogniser);
			connectors.add(connector);
			//add connector
			
			Object[] dev = deviceList.getSelectedValues();
			for (int i = 0; i < dev.length; i++) {
				AbstractGestureDevice<?,?> device = (AbstractGestureDevice<?,?>)dev[i];
				device.addGestureHandler(connector);
			}					
			
			cmbSets.setEnabled(true);
			btnReset.setEnabled(true);
		} catch (AlgorithmException e1) {
			e1.printStackTrace();
		}
	}
	
}
