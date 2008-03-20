/*****************************************************************************************************************
 * (c) Copyright EAO AG, 2007
 * 
 * Project      : Tone-editor Multi Tone Sound Module 56
 * Filename     : $Id: TextAreaBinding.java 93 2007-10-31 10:07:39Z uelikurmann $
 * Programmer   : Ueli Kurmann (UK) / bbv Software Services AG / ueli.kurmann@bbv.ch
 * Creation date: 2007-10-31
 *
 *****************************************************************************************************************
 * Description:
 * ...
 * 
 *****************************************************************************************************************
 * Location:
 * $HeadURL: https://svn.bbv.ch/svn/EAOToneditor/trunk/Implementation/src/ch/bbv/eao/toneditor/gui/databinding/TextAreaBinding.java $
 *
 *****************************************************************************************************************
 * Updates:
 * $LastChangedBy: uelikurmann $
 * $LastChangedDate: 2007-10-31 11:07:39 +0100 (Mi, 31 Okt 2007) $
 * $LastChangedRevision: 93 $
 *
 *****************************************************************************************************************/

package org.ximtex.igesture.tool.binding;

import javax.swing.JTextArea;

import org.ximtec.igesture.core.DataObject;

public class TextAreaBinding extends DataBinding<JTextArea> {

	private JTextArea textArea;

	public TextAreaBinding(JTextArea textField, DataObject obj, String property) {
		super(obj, property);
		this.textArea = textField;
		this.textArea.addFocusListener(this);
		updateView();
	}

	@Override
	public JTextArea getComponent() {
		return textArea;
	}

	@Override
	public void updateView() {
		textArea.setText(getValue());
	}

	@Override
	public void updateModel() {
		setValue(textArea.getText());
	}
}
