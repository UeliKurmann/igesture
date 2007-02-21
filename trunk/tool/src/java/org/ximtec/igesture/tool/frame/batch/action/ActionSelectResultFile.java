package org.ximtec.igesture.tool.frame.batch.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.sigtec.graphix.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.SwingTool;

public class ActionSelectResultFile extends BasicAction {

	private JTextField textField;

	public ActionSelectResultFile(JTextField textField) {
		super(GestureConstants.COMMON_BROWSE, SwingTool.getGuiBundle());
		this.textField = textField;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog((JButton) event.getSource());
			final File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				textField.setText(selectedFile.getPath());
			}
		}
	}

}
