/**
 * 
 */
package org.ximtec.igesture.tool.binding;

import javax.swing.JTextField;

import org.ximtec.igesture.core.composite.Constraint;
import org.ximtec.igesture.core.composite.DefaultConstraint;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class ConstraintTextFieldBinding extends DataBinding<JTextField> {

	private JTextField textField;
	private Constraint constraint;
	
	/**
	 * @param dataObject
	 * @param property
	 */
	public ConstraintTextFieldBinding(JTextField textField, Constraint constraint, String property) {
		super((DefaultConstraint)constraint, property);
		this.textField = textField;
		this.textField.addFocusListener(this);
		this.constraint = constraint;
		updateView();
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.binding.DataBinding#getComponent()
	 */
	@Override
	public JTextField getComponent() {
		return textField;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.binding.DataBinding#updateModel()
	 */
	@Override
	protected void updateModel() {
		setValue(textField.getText());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.binding.DataBinding#updateView()
	 */
	@Override
	protected void updateView() {
		textField.setText(getValue());
	}
	
	@Override
	protected String getValue() {
		return ((Constraint)getObject()).getParameter(getProperty());
    }
	   
	@Override
	protected void setValue(Object value) {
		if(!getValue().equals(value)){
			((Constraint)getObject()).setParameter(getProperty(), (String)value);
		}      
	}

}
