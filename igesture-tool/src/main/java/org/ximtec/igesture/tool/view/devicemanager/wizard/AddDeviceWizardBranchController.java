/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager.wizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardBranchController;
import org.netbeans.spi.wizard.WizardController;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPanelProvider;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.Recogniser;

/**
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class AddDeviceWizardBranchController extends WizardBranchController {
	
	public AddDeviceWizardBranchController(DeviceManagerController manager) {
		super(new AddDeviceWizardProvider(manager));
	}

	protected WizardPanelProvider getPanelProviderForStep (String step, Map settings) {
        String which = (String) settings.get ("recogniser");
        if (which == null) {
            return null;
        } else if ("Existing Recogniser".equals(which)) {
            return getExistingPanel();
        } else if ("New Recogniser".equals(which)) {
            return getNewPanel();
        } else {
            throw new IllegalArgumentException (which);
        }
    }

	private WizardPanelProvider getNewPanel() {
		return new NewRecogniserWizardProvider();
	}

	/**
	 * @return
	 */
	private WizardPanelProvider getExistingPanel() {
		return new ExistingRecogniserWizardProvider();
	}
	
	private class ExistingRecogniserWizardProvider extends WizardPanelProvider
	{
		public ExistingRecogniserWizardProvider()
		{
			super("Add Device ...", new String[]{"existing"}, new String[]{"Choose a Recogniser"});
		}

		/* (non-Javadoc)
		 * @see org.netbeans.spi.wizard.WizardPanelProvider#createPanel(org.netbeans.spi.wizard.WizardController, java.lang.String, java.util.Map)
		 */
		@Override
		protected JComponent createPanel(final WizardController controller, String id, final Map settings)
		{
			controller.setProblem("You must select a Recogniser");
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			final JList recogniserList = new JList();
			final DefaultListModel model = new DefaultListModel();
			recogniserList.setModel(model);
			recogniserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			//TODO fill model
			model.addElement("Recogniser 1");
			model.addElement("Recogniser 2");
			
			recogniserList.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(!e.getValueIsAdjusting())
					{
						controller.setProblem(null);						
						settings.put("recogniser", model.getElementAt(recogniserList.getSelectedIndex()));
						System.out.println(model.getElementAt(recogniserList.getSelectedIndex()));
					}
				}
			});
			
			JScrollPane scrollList = new JScrollPane(recogniserList);
			scrollList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Recognisers"));
			panel.add(scrollList,BorderLayout.CENTER);
			return panel;
		}
		
		@Override
		public Object finish(Map wizardData) throws WizardException {
			
			//summary
			String[] items = new String[wizardData.size()];
			items[0] = "Connection:\t" + wizardData.get("connection");
			items[1] = "Device:\t" + wizardData.get("device");
			items[2] = "User:\t" + wizardData.get("user");
			items[3] = "Recogniser:\t" + wizardData.get("recogniser");
			
			//result
			ArrayList<Object> result = new ArrayList<Object>();
			result.add(wizardData.get("device"));
			result.add(wizardData.get("user"));
			result.add(wizardData.get("recogniser"));
			
			return Summary.create(items,result);
		}
		
	}
	
	private class NewRecogniserWizardProvider extends WizardPanelProvider
	{
		
		public NewRecogniserWizardProvider()
		{
			super("Add device...", new String[]{"new"}, new String[]{"Create a new Recogniser"});
		}

		/* (non-Javadoc)
		 * @see org.netbeans.spi.wizard.WizardPanelProvider#createPanel(org.netbeans.spi.wizard.WizardController, java.lang.String, java.util.Map)
		 */
		@Override
		protected JComponent createPanel(final WizardController controller, String id, final Map settings) {
			
			controller.setProblem("Select at least one gesture set and one algorithm");
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			
			final JList algList = new JList();
			final DefaultListModel algModel = new DefaultListModel();
			algList.setModel(algModel);
			algModel.addElement("Rubine");
			
			JScrollPane algScroll = new JScrollPane(algList);
			algScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Algorithms"));
			
			final JList setList = new JList();
			final DefaultListModel setModel = new DefaultListModel();
			setList.setModel(setModel);
			setModel.addElement("2D");
			setModel.addElement("3D");
			
			JScrollPane setScroll = new JScrollPane(setList);
			setScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Gesture Sets"));
			
			ListSelectionListener l = new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(!e.getValueIsAdjusting())
					{
						if(algList.getSelectedIndex() > -1 && setList.getSelectedIndex() > -1)
						{
							Algorithm a = (Algorithm) algModel.getElementAt(algList.getSelectedIndex());
							GestureSet gs = (GestureSet) setModel.getElementAt(setList.getSelectedIndex());
							Configuration config = new Configuration();
							config.addAlgorithm(a.getClass().getName());
							config.addGestureSet(gs);
							try {
								Recogniser recogniser = new Recogniser(config);
								settings.put("recogniser", recogniser);
							} catch (AlgorithmException e1) {
								e1.printStackTrace();
							}
							//TODO chkbox multimodal
							controller.setProblem(null);
						}
					}
					
				}
				
			};
			
			algList.addListSelectionListener(l);
			setList.addListSelectionListener(l);
			
			JPanel listPanel = new JPanel();
			listPanel.add(algScroll);
			listPanel.add(setScroll);
			
			JPanel optionsPanel = new JPanel();
			JCheckBox chkMultimodal = new JCheckBox("Multi-modal Support");
			optionsPanel.add(chkMultimodal);
			
			panel.add(listPanel,BorderLayout.CENTER);
			panel.add(optionsPanel, BorderLayout.SOUTH);
			return panel;
		}
		
		@Override
		public Object finish(Map wizardData) throws WizardException {
			
			//summary
			String[] items = new String[wizardData.size()];
			items[0] = "Connection:\t" + wizardData.get("connection");
			items[1] = "Device:\t" + wizardData.get("device");
			items[2] = "User:\t" + wizardData.get("user");
			items[3] = "Recogniser:\t" + wizardData.get("recogniser");
			
			//result
			ArrayList<Object> result = new ArrayList<Object>();
			result.add(wizardData.get("device"));
			result.add(wizardData.get("user"));
			result.add(wizardData.get("recogniser"));
			
			return Summary.create(items,result);
		}
		
	}

}
