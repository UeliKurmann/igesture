/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtex.igesture.tool.binding;

import java.util.HashMap;

import javax.swing.JComponent;

import org.ximtec.igesture.core.DataObject;


public class DataBindingContext {

   private HashMap<JComponent, DataBinding< ? >> context;


   public DataBindingContext() {
      context = new HashMap<JComponent, DataBinding< ? >>();
   }


   public JComponent addBinding(DataBinding< ? > binding) {
      context.put(binding.getComponent(), binding);
      return binding.getComponent();
   }


   public DataBinding< ? > addBinding(JComponent component, DataObject obj,
         String property) {
      DataBinding< ? > db = BindingFactory.createInstance(component, obj,
            property);
      addBinding(db);
      return db;
   }


   public void updateAll() {
      for (DataBinding< ? > binding : context.values()) {
         binding.updateModel();
      }
   }


   public void loadAll() {
      for (DataBinding< ? > binding : context.values()) {
         binding.updateView();
      }
   }


   public void update(JComponent component) {
      DataBinding< ? > binding = context.get(component);
      if (binding != null) {
         binding.updateModel();
      }
   }
}
