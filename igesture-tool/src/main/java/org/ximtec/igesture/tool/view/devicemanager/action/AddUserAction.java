package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.io.IUser;
import org.ximtec.igesture.io.User;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;

/**
 * Action to add a user. It extends {@link org.sigtec.graphix.widget.BasicAction}.
 * @author Björn Puypepuype@gmail.com
 *
 */
public class AddUserAction extends BasicAction {

	private DeviceManagerController controller;
	
	public AddUserAction(DeviceManagerController controller)
	{
		super(GestureConstants.ADD_USER, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final JDialog dlg = new JDialog();
		dlg.setTitle("Add User");
//		dlg.setLayout(new GridBagLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		
		// labels
		add(new JLabel("Name:"),contentPanel,0,0,1);
		add(new JLabel("Initials:"),contentPanel,0,1,1);
		
		// text fields
		final JTextField txtName = new JTextField();
		Formatter.formatTextField(txtName);
		add(txtName,contentPanel,1,0,2);
		final JTextField txtInitials = new JTextField();
		Formatter.formatTextField(txtInitials);
		add(txtInitials,contentPanel,1,1,2);
		
		// buttons
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtName.getText().isEmpty() || txtInitials.getText().isEmpty())
					JOptionPane.showMessageDialog(dlg, "Please enter a username and initials.", "Warning", JOptionPane.WARNING_MESSAGE);
				else
				{
					if(controller.areInitialsValid(txtInitials.getText()))
					{
						controller.addUser(new User(txtName.getText(),txtInitials.getText()));
						dlg.setVisible(false);
						dlg.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(dlg, "The initials must be unique!\n" +
								"Please enter other initials.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			
		});
		Formatter.formatButton(btnOK);
		add(btnOK,contentPanel,1,2,1);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
				dlg.dispose();				
			}
			
		});
		Formatter.formatButton(btnCancel);
		add(btnCancel,contentPanel,2,2,1);
		
		dlg.setContentPane(contentPanel);
		dlg.setPreferredSize(new Dimension(400,120));
		dlg.setResizable(false);
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
	private void add(JComponent component, JPanel dlg, int gridx, int gridy, int gridwidth)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		
		dlg.add(component,c);
	}
}
