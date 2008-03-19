package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.sigtec.ink.NoteTool;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;
import org.ximtec.igesture.tool.old.util.JNote;
import org.ximtec.igesture.util.GestureTool;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class DescriptorPanel extends DefaultExplorerTreeView {
	
	public DescriptorPanel(SampleDescriptor descriptor){
		
		FormLayout layout = new FormLayout(
				"pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref",
				"pref, pref, pref, pref, pref, pref,  pref,  pref,  pref,  pref,  pref,  pref");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();

		builder.append(new JLabel("Descriptor has "+descriptor.getSamples().size()+" samples."));
		builder.nextLine(4);
		
		for(GestureSample sample:descriptor.getSamples()){
			JLabel label = new JLabel(new ImageIcon(GestureTool.createNoteImage(sample.getNote(),100, 100)));
			builder.append(label);
		}
		
		
		JPanel panel = builder.getPanel();
		panel.setOpaque(false);
		panel.setAutoscrolls(true);
		
		
		this.add(panel);
		
		this.setOpaque(true);
		this.setForeground(Color.white);
		this.setBackground(Color.white);
		
	}
	
	
	
	

}
