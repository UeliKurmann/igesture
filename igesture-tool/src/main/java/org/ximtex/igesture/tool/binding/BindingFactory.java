/*****************************************************************************************************************
 * (c) Copyright EAO AG, 2007
 * 
 * Project      : Tone-editor Multi Tone Sound Module 56
 * Filename     : $Id: DataBindingFactory.java 93 2007-10-31 10:07:39Z uelikurmann $
 * Programmer   : Ueli Kurmann (UK) / bbv Software Services AG / ueli.kurmann@bbv.ch
 * Creation date: 2007-10-31
 *
 *****************************************************************************************************************
 * Description:
 * ...
 * 
 *****************************************************************************************************************
 * Location:
 * $HeadURL: https://svn.bbv.ch/svn/EAOToneditor/trunk/Implementation/src/ch/bbv/eao/toneditor/gui/databinding/DataBindingFactory.java $
 *
 *****************************************************************************************************************
 * Updates:
 * $LastChangedBy: uelikurmann $
 * $LastChangedDate: 2007-10-31 11:07:39 +0100 (Mi, 31 Okt 2007) $
 * $LastChangedRevision: 93 $
 *
 *****************************************************************************************************************/



package org.ximtex.igesture.tool.binding;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.ximtec.igesture.core.DataObject;

public class BindingFactory {
	
	private static Map<Class<? extends JComponent>, DataBinding<? extends JComponent>> bindingMapping;

    public static DataBinding createInstance(JComponent component, DataObject obj, String property) {
        if(component instanceof JTextField){
        	return new TextFieldBinding((JTextField)component, obj, property);
        }else if(component instanceof JTextArea){
        	return new TextAreaBinding((JTextArea)component, obj, property);
        }
        return null;
    }
}
