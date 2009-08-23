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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;
import org.ximtec.igesture.tool.util.ComponentFactory;

public abstract class AbstractPanel extends DefaultExplorerTreeView {

  private JScrollPane centerPane;
  private Controller controller;

  public AbstractPanel(Controller controller) {
    this.controller = controller;
    if (controller == null) {
      throw new RuntimeException("Controller must not be null.");
    }
    setLayout(new BorderLayout());
    centerPane = new JScrollPane();
    centerPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    setBackground(Color.LIGHT_GRAY);
    setOpaque(true);
    this.add(centerPane, BorderLayout.CENTER);
  }

  /**
   * Sets the title component
   * 
   * @param component
   *          the title component
   */
  public void setTitle(JComponent component) {
    this.add(component, BorderLayout.NORTH);
  }

  /**
   * Sets the content area.
   * 
   * @param component
   *          the content area.
   */
  public void setContent(JComponent component) {
    component.setBackground(Color.white);
    component.setOpaque(true);
   
    centerPane.setViewportView(component);
  }

  /**
   * Sets the footer of the panel.
   * 
   * @param component
   *          the footer of the panel.
   */
  public void setBottom(JComponent component) {
    this.add(component, BorderLayout.SOUTH);
  }

  /**
   * Returns the controller
   * 
   * @return the controller
   */
  protected Controller getController() {
    return this.controller;
  }

  /**
   * Returns the component factory. The Controller and it's locator is used to
   * get the applications component factory.
   * 
   * @return the component factory
   */
  protected ComponentFactory getComponentFactory() {
    if (controller != null) {
      return controller.getLocator().getService(ComponentFactory.class.getName(), ComponentFactory.class);
    }
    return null;
  }

  @Override
  public final void refresh() {
    if (SwingUtilities.isEventDispatchThread()) {
      refreshUILogic();
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          refreshUILogic();
        }
      });
    }
  }

  /**
   * Abstract implementations of the refreshUILogic method. This implementation
   * is empty. Per default nothing has to be updated.
   */
  protected void refreshUILogic() {

  }

}
