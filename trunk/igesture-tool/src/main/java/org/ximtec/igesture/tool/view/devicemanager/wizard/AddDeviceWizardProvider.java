package org.ximtec.igesture.tool.view.devicemanager.wizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardController;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPanelProvider;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.DeviceDiscoveryService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;

/**
 * GUI and logic implementation of the wizard. It extends {@link org.netbeans.spi.wizard.WizardPanelProvider}.
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class AddDeviceWizardProvider extends WizardPanelProvider {

	private int selectedIndexDevice = 0;
	private DeviceManagerController manager;
	private final Map<String, DeviceDiscoveryService> discoveryMapping;

	protected AddDeviceWizardProvider(DeviceManagerController manager)
	{
		super("Add Device ...", new String[]{"connection", "device", "user"/*, "recogniser"*/}, new String[]{
				"Choose a connection type",
				"Choose a device to connect",
				"Associate the device with a user"/*,
				"Associate a recogniser"*/
		});
		this.manager = manager;
		discoveryMapping = manager.getDiscoveryMapping();
	}
	
	@Override
	protected JComponent createPanel(final WizardController controller, String id, final Map settings) {
		
		if("connection".equals(id))// STEP 1
		{
			String problem = "You must select a connection type";
			controller.setProblem(problem);	
			return createRadioButtonsPanel(discoveryMapping.keySet(), "connection", problem, controller, settings);
		}
		else if("device".equals(id)) // STEP 2
		{
			controller.setProblem("Choose a device to connect");
			
			// get the discovery service based on the user's choice in the previous step
			String key = (String) settings.get("connection");
			DeviceDiscoveryService service = discoveryMapping.get(key);
			// discover devices
			Set<AbstractGestureDevice<?,?>> devices = service.discover();
			
			final Object[] discoveredDevices = devices.toArray(); 
			                           
			//add the devices to a list
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			JList list = new JList(discoveredDevices);
			list.setName("device");
			list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scroll = new JScrollPane(list);
			panel.add(scroll, BorderLayout.CENTER);
			
			//cleanup the service
			service.dispose();
			
			//listen to the selection of the user
			list.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					
					if(!e.getValueIsAdjusting())
					{
						//change to a different index
						if(!(e.getFirstIndex() == selectedIndexDevice))
							selectedIndexDevice = e.getFirstIndex();
						else
							selectedIndexDevice = e.getLastIndex();
						controller.setProblem(null);
						
						settings.put("device", discoveredDevices[selectedIndexDevice]);
						
					}
				}
				
			});
			
			return panel;
		}
		else //if("user".equals(id)) // STEP 3
		{
			controller.setProblem("Associate the device with a user");
			
			// get the users and add them to the list
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			final JList list = new JList(manager.getUsers().toArray());
			list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scroll = new JScrollPane(list);
			panel.add(scroll, BorderLayout.CENTER);
			
			// listen to the selection of the user
			list.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					
					if(!e.getValueIsAdjusting())
					{
						controller.setProblem(null);
						Object result = list.getSelectedValue();
						settings.put("user", result);
					}
				}
				
			});
			
			return panel;

		}/*
		else //if("choice".equals("id")) // STEP 4 (BRANCH)
		{
			String problem = "You must select a recogniser type";
			controller.setProblem(problem);
			Set<String> names = new HashSet<String>();
			names.add("Existing Recogniser");
			names.add("New Recogniser");
			return createRadioButtonsPanel(names, "recogniser", problem, controller, settings);
		}*/
	}
	
	private JPanel createRadioButtonsPanel(Set<String> names, final String key, final String problem, final WizardController controller, final Map settings)
	{	
		JPanel panel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		final ArrayList<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
		
		for(String name : names)
		{
			JRadioButton rdb = new JRadioButton();
			rdb.setText(name);
			rdb.setName(name);
			rdb.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			group.add(rdb);
			panel.add(rdb);
			radioButtons.add(rdb);
		}
		
		//actionlistener for the radiobuttons
		ActionListener l = new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean isSelected = false;
				JRadioButton choice = null;
				for(JRadioButton btn : radioButtons)
				{
					if(btn.isSelected())
					{
						isSelected = true;
						choice = btn;
						break;
					}
				}
				if(!isSelected)//no next if nothing selected
				{
					controller.setProblem(problem);
				}
				else
				{
					controller.setProblem(null);
					//save the users choice
					settings.put(key, choice.getText());
				}
			}
			
		};
		
		for(JRadioButton rdb : radioButtons)
		{
			rdb.addActionListener(l);
		}
		
		return panel;

	}
	

	@Override
	public Object finish(Map wizardData) throws WizardException {
		
		//summary
		String[] items = new String[wizardData.size()];
		items[0] = "Connection:\t" + wizardData.get("connection");
		items[1] = "Device:\t" + wizardData.get("device");
		items[2] = "User:\t" + wizardData.get("user");
		
		//result
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(wizardData.get("device"));
		result.add(wizardData.get("user"));
		
		return Summary.create(items,result);
	}
}
