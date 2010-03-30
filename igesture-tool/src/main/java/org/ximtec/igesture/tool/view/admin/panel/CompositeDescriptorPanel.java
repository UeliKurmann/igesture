/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.core.CompositeDescriptor;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.util.FontFactory;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.view.MainModel;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CompositeDescriptorPanel extends DefaultDescriptorPanel<CompositeDescriptor> implements ListSelectionListener{

	public static final int OPTION_USERS = 0;
	public static final int OPTION_DEVICE_CLASS = 1;
	public static final int OPTION_DEVICE_SPECIFIC = 2;
	private static final int INPUTAREA_SIZE = 200;
	
	private MainModel mainModel;
	
	private JScrollPane resultList;
	
	private GuiBundleService guiBundle;
	
	public CompositeDescriptorPanel(Controller controller, CompositeDescriptor descriptor) {
		super(controller, descriptor);
		
		mainModel = controller.getLocator().getService(MainModel.IDENTIFIER, MainModel.class);
		guiBundle = controller.getLocator().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class);
		init();
	}

	private void init() 
	{	
		initTitle();
		initSampleSection();
		initInputSection();		
	}
	
	/**
	 * Visualizes the samples. The GridBagLayout is used. The number of elements
	 * in a row are computed dynamically. Between two gesture elements a space
	 * element is placed.
	 */
	private synchronized void initSampleSection()
	{
		JPanel panel = new JPanel();
	    panel.setLayout(new GridBagLayout());
		
	    //TODO @see SampleDescriptorPanel
	    
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
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		
		//title
		JLabel title = getComponentFactory().createLabel(GestureConstants.COMPOSITE_DESCRIPTOR_TITLE);
	    title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    title.setFont(FontFactory.getArialBold(18));
	    basePanel.add(title, BorderLayout.NORTH);
		
	    //algorithm list
		JList algorithmList = new JList();
		DefaultListModel model = new DefaultListModel();
		algorithmList.setModel(model);
		algorithmList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		algorithmList.getSelectionModel().addListSelectionListener(this);
		JScrollPane scrollAlgList = new JScrollPane(algorithmList);
		scrollAlgList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
				guiBundle.getName(GestureConstants.ALGORITHM_LIST_NAME)));		
		scrollAlgList.setPreferredSize(new Dimension(INPUTAREA_SIZE,INPUTAREA_SIZE));
		
		for(Class<? extends Algorithm> alg : mainModel.getAlgorithms())
			model.addElement(alg.getSimpleName());		
		
		basePanel.add(scrollAlgList,BorderLayout.WEST);
		
		//output
		resultList = new JScrollPane(new JList());
		basePanel.add(resultList,BorderLayout.CENTER);
		
		//controls
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(createRecordGestureButton(),BorderLayout.NORTH);
		buttonPanel.add(createSaveButton(),BorderLayout.SOUTH);
		
		JPanel chkPanel = new JPanel();
		chkPanel.setLayout(new BorderLayout());
		chkPanel.add(createCheckBox("Users",KeyEvent.VK_U,OPTION_USERS),BorderLayout.NORTH);
		chkPanel.add(createCheckBox("Device Classes",KeyEvent.VK_C,OPTION_DEVICE_CLASS),BorderLayout.CENTER);
		chkPanel.add(createCheckBox("Specific Devices",KeyEvent.VK_S,OPTION_DEVICE_SPECIFIC),BorderLayout.SOUTH);
		
//		buttonPanel.add(createCheckBox("Users",KeyEvent.VK_U,OPTION_USERS));
//		buttonPanel.add(createCheckBox("Device Classes",KeyEvent.VK_C,OPTION_DEVICE_CLASS));
//		buttonPanel.add(createCheckBox("Specific Devices",KeyEvent.VK_S,OPTION_DEVICE_SPECIFIC));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(buttonPanel,BorderLayout.NORTH);
		leftPanel.add(chkPanel,BorderLayout.SOUTH);
//		buttonPanel.setPreferredSize(new Dimension(140,NPUTAREA_SIZE));
		basePanel.add(leftPanel,BorderLayout.EAST);
		
		setBottom(basePanel);
	}
	
	private JButton createRecordGestureButton()
	{
		JButton btn = new RecordButton();
		Formatter.formatButton(btn);
		return btn;
	}
	
	private JButton createSaveButton()
	{
		JButton btn = new JButton("Save");
		Formatter.formatButton(btn);
		return btn;
	}
	
	private JCheckBox createCheckBox(String title, int option, int mnemonic)
	{
		JCheckBox chk = new JCheckBox(title);
		chk.setMnemonic(mnemonic);
		chk.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				//TODO
				
			}
			
		});
		return chk;
	}

	public void setResultList(List<Result> classes) {
	    resultList.setViewportView(new JList(new Vector<Result>(classes)));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
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
					if(recording == true)
					{
						//TODO
						setText("Stop");
					}
					else
					{
						//TODO
						setText("Record");
					}
					recording = !recording;
				}
				
			});
		}
	}

}
