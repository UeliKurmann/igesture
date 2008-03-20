/*****************************************************************************************************************
 * (c) Copyright EAO AG, 2007
 * 
 * Project      : Tone-editor Multi Tone Sound Module 56
 * Filename     : $Id: TextFieldBinding.java 96 2007-10-31 14:44:18Z uelikurmann $
 * Programmer   : Ueli Kurmann (UK) / bbv Software Services AG / ueli.kurmann@bbv.ch
 * Creation date: 2007-10-31
 *
 *****************************************************************************************************************
 * Description:
 * ...
 * 
 *****************************************************************************************************************
 * Location:
 * $HeadURL: https://svn.bbv.ch/svn/EAOToneditor/trunk/Implementation/src/ch/bbv/eao/toneditor/gui/databinding/TextFieldBinding.java $
 *
 *****************************************************************************************************************
 * Updates:
 * $LastChangedBy: uelikurmann $
 * $LastChangedDate: 2007-10-31 15:44:18 +0100 (Mi, 31 Okt 2007) $
 * $LastChangedRevision: 96 $
 *
 *****************************************************************************************************************/

package org.ximtex.igesture.tool.binding;

import javax.swing.JTextField;

import org.ximtec.igesture.core.DataObject;

public class TextFieldBinding extends DataBinding<JTextField> {

	private JTextField textField;

	public TextFieldBinding(JTextField textField, DataObject obj, String property) {
		super(obj, property);
		this.textField = textField;
		this.textField.addFocusListener(this);
		updateView();
	}

	@Override
	public JTextField getComponent() {
		return textField;
	}

	@Override
	public void updateView() {
		textField.setText(getValue());
	}

	@Override
	public void updateModel() {
		setValue(textField.getText());
	}
}
