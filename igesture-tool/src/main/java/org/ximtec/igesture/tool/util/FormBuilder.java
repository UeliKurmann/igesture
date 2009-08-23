/*
 * @(#)$Id: Formatter.java 741 2009-08-15 09:40:16Z kurmannu $
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose    : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.08.2009 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.sigtec.graphix.GridBagLayouter;

/**
 * This is a simple two column form builder.
 * 
 * @author Ueli Kurmann
 * 
 */
public class FormBuilder {

  public static final int LEFT = 1;
  public static final int RIGHT = 2;

  private int line;
  private JPanel panel;

  public FormBuilder() {
    panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    line = 1;
  }

  public void nextLine() {
    line++;
  }

  public void addLeft(JComponent component) {
    addComponent(component, LEFT, 0.0);
  }

  public void addRight(JComponent component) {
    addComponent(component, RIGHT, 1.0);
  }

  private void addComponent(JComponent component, int position, double weight) {

    GridBagLayouter.addComponent(panel, component, position, line, 1, 1, 5, 5, 5, 5, weight, 1,
        GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);
  }

  public JPanel getPanel() {
    return panel;
  }

}
