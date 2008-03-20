package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class GestureSetPanel extends DefaultExplorerTreeView {
	
	public GestureSetPanel(GestureSet gestureSet){
		
		setLayout(new BorderLayout());
		
		FormLayout layout = new FormLayout(
				"100dlu, 4dlu, pref",
				"pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();

		builder.append(new JLabel("Gesture Set " + gestureSet.getName()));
		builder.nextLine(4);

		builder.append(new JLabel("Name"));
		JTextField textField = new JTextField();
		
		BindingFactory.createInstance(textField, gestureSet, GestureSet.PROPERTY_NAME);
		
		
		builder.append(textField);
		builder.nextLine(2);

		builder.append(new JLabel("Number of Gesture Classes"));
		
		JPanel panel = builder.getPanel();
		panel.setOpaque(false);

		this.add(panel,BorderLayout.CENTER);

	}
	
	
	
	

}
