package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class GestureClassPanel extends DefaultExplorerTreeView {

	public GestureClassPanel(GestureClass gestureClass) {

		FormLayout layout = new FormLayout(
				"pref, 4dlu, pref, 8dlu, pref, 4dlu, pref",
				"pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();

		builder.append(new JLabel("GestureClass " + gestureClass.getName()));
		builder.nextLine(4);

		builder.append(new JLabel("Name"));
		JTextField textField = new JTextField();
		textField.setText(gestureClass.getName());
		builder.append(textField);
		builder.nextLine(2);

		builder.append(new JLabel("Number of Descriptors"));
		builder.append(new JLabel(Integer.toString(gestureClass
				.getDescriptors().size())));
		builder.nextLine(2);
		for (Descriptor descriptor : gestureClass.getDescriptors()) {
			builder.append(new JLabel("Descriptor Name"));
			builder.append(new JLabel(descriptor.getType().getName()));
			builder.nextLine(2);
		}

		JPanel panel = builder.getPanel();
		panel.setOpaque(false);

		this.add(panel);

		this.setOpaque(true);
		this.setForeground(Color.blue);
		this.setBackground(Color.blue);
	}

}
