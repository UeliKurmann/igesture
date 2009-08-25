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

package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.BindingFactory;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.FormBuilder;

public class TextDescriptorPanel extends DefaultDescriptorPanel<TextDescriptor> {

  public TextDescriptorPanel(Controller controller, TextDescriptor textDescriptor) {
    super(controller, textDescriptor);

    initTitle();

    FormBuilder formBuilder = new FormBuilder();

    JLabel labelText = getComponentFactory().createLabel(GestureConstants.TEXT_DESCRIPTOR_TEXT);
    
    JTextArea textArea = new JTextArea();
    BindingFactory.createInstance(textArea, textDescriptor, TextDescriptor.PROPERTY_TEXT);

    formBuilder.addLeft(labelText);
    formBuilder.addRight(textArea);
    
    JPanel basePanel = ComponentFactory.createBorderLayoutPanel();
    basePanel.add(formBuilder.getPanel(), BorderLayout.NORTH);

    setContent(basePanel);

  }

}
