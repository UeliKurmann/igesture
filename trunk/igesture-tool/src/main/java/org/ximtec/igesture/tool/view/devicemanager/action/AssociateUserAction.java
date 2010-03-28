package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerView;
import org.ximtec.igesture.tool.view.devicemanager.DeviceUserAssociation;
import org.ximtec.igesture.tool.view.devicemanager.User;

/**
 * Action to associate a user with a device. It extends {@link org.sigtec.graphix.widget.BasicAction} and implements the {@link javax.swing.event.ListSelectionListener} interface.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class AssociateUserAction extends BasicAction implements ListSelectionListener{

	private static final long serialVersionUID = 1L;
	private DeviceManagerController controller;
	private DeviceManagerView view;
	
	
	public AssociateUserAction(DeviceManagerController controller, DeviceManagerView view)
	{
		this(controller,view,false);
	}
	
	public AssociateUserAction(DeviceManagerController controller, DeviceManagerView view, boolean enabled)
	{
		super(GestureConstants.ASSOCIATE_USER, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;
		this.view = view;
		setEnabled(enabled);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//creat a dialog
		final JDialog dlg = new JDialog();
		dlg.setTitle("Associate User");
		dlg.setLayout(new GridBagLayout());

		// label
		add(new JLabel("Select a user:"),dlg,0,0,1);
		
		// user list
		Set<User> users = controller.getUsers();
		final Map<String, User> map = new HashMap<String, User>();
		for(User user : users)
		{
			map.put(user.toString(), user);
		}
		
		//add users to the list.
		final JList list = new JList(map.keySet().toArray());
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		add(list, dlg, 0, 1, 2);
		
		// buttons
		JButton btnOK = new JButton("OK");
		final DeviceUserAssociation device = view.getSelectedDevice();
		btnOK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					if(list.getSelectionModel().isSelectionEmpty())
						JOptionPane.showMessageDialog(dlg, "Please select a user.", "Warning", JOptionPane.WARNING_MESSAGE);
					else
					{
						User user = map.get(list.getSelectedValues()[0]);
						controller.associateUser(device.getDeviceItem(),user);
						dlg.setVisible(false);
						dlg.dispose();
					}
			}
			
		});
		add(btnOK,dlg,0,2,1);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
				dlg.dispose();				
			}
			
		});
		add(btnCancel,dlg,1,2,1);
		
		dlg.pack();
		dlg.setVisible(true);		
		
	}
	
	/**
	 * Add a component to a dialog with a GridBagLayout.
	 * @param component	The component to add.
	 * @param dlg		The dialog.
	 * @param gridx		The x position.
	 * @param gridy		The y position.
	 * @param gridwidth	The width of the component expressed in number of columns.
	 */
	private void add(JComponent component, JDialog dlg, int gridx, int gridy, int gridwidth)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		
		dlg.add(component,c);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		setEnabled(true);
		//enable the action when something was selected.
	}

}
