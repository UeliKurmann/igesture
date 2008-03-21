/*****************************************************************************************************************
 * (c) Copyright EAO AG, 2007
 * 
 * Project      : Tone-editor Multi Tone Sound Module 56
 * Filename     : $Id: DataBindingContext.java 93 2007-10-31 10:07:39Z uelikurmann $
 * Programmer   : Ueli Kurmann (UK) / bbv Software Services AG / ueli.kurmann@bbv.ch
 * Creation date: 2007-10-31
 *
 *****************************************************************************************************************
 * Description:
 * ...
 * 
 *****************************************************************************************************************
 * Location:
 * $HeadURL: https://svn.bbv.ch/svn/EAOToneditor/trunk/Implementation/src/ch/bbv/eao/toneditor/gui/databinding/DataBindingContext.java $
 *
 *****************************************************************************************************************
 * Updates:
 * $LastChangedBy: uelikurmann $
 * $LastChangedDate: 2007-10-31 11:07:39 +0100 (Mi, 31 Okt 2007) $
 * $LastChangedRevision: 93 $
 *
 *****************************************************************************************************************/



package org.ximtex.igesture.tool.binding;

import java.util.HashMap;

import javax.swing.JComponent;

import org.ximtec.igesture.core.DataObject;

public class DataBindingContext {

    private HashMap<JComponent, DataBinding<?>> context;

    public DataBindingContext() {
        context = new HashMap<JComponent, DataBinding<?>>();
    }

    public JComponent addBinding(DataBinding<?> binding) {
        context.put(binding.getComponent(), binding);
        return binding.getComponent();
    }

    public DataBinding<?> addBinding(JComponent component, DataObject obj, String property) {
        DataBinding<?> db = BindingFactory.createInstance(component, obj, property);
        addBinding(db);
        return db; 
    }
 
    public void updateAll() {
        for (DataBinding<?> binding : context.values()) {
            binding.updateModel();
        }
    }
    
    public void loadAll(){
        for(DataBinding<?> binding : context.values()){
            binding.updateView();
        }
    }

    public void update(JComponent component) {
        DataBinding<?> binding = context.get(component);
        if (binding != null) {
            binding.updateModel();
        }
    }
}
