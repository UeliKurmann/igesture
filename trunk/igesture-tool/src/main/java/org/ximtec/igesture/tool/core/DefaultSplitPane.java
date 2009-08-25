package org.ximtec.igesture.tool.core;

import javax.swing.JSplitPane;

import org.ximtec.igesture.tool.util.ComponentFactory;

public class DefaultSplitPane extends JSplitPane {

  private static final int DIVIDER_LOCATION = 240;
  private Controller controller;

  public DefaultSplitPane(Controller controller) {
    super(JSplitPane.HORIZONTAL_SPLIT);
    
    setDividerLocation(DIVIDER_LOCATION);
    
    this.controller = controller;
    if (this.controller == null) {
      throw new RuntimeException("Controller must not be null.");
    }
  }

  /**
   * Returns the controller instance of the view
   * 
   * @return the controller of the view
   */
  public Controller getController() {
    return controller;
  }

  /**
   * Returns the component factory
   * 
   * @return the component factory
   */
  public ComponentFactory getComponentFactory() {
    return controller.getLocator().getService(ComponentFactory.class.getName(), ComponentFactory.class);
  }

}
