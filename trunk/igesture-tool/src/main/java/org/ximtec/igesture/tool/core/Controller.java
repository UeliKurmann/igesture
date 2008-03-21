package org.ximtec.igesture.tool.core;

import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

public interface Controller extends PropertyChangeListener{
  
  JComponent getView();

}
