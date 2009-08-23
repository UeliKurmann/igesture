/*
 * @(#)$Id: WorkspaceTool.java 456 2008-11-11 09:54:06Z D\signerb $
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 07.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testset.panel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.BindingFactory;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.FormBuilder;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;

/**
 * Comment
 * 
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */
public class TestSetPanel extends AbstractPanel {

  private TestSet testSet;

  public TestSetPanel(Controller controller, TestSet testSet) {
    super(controller);
    this.testSet = testSet;
    init();
  }

  private void init() {

    setTitle(TitleFactory.createStaticTitle(testSet.getName()));

    FormBuilder formBuilder = new FormBuilder();

    JTextField textField = new JTextField();
    BindingFactory.createInstance(textField, testSet, TestSet.PROPERTY_NAME);
    formBuilder.addLeft(getComponentFactory().createLabel(GestureConstants.TESTCLASS_NAME));
    formBuilder.addRight(textField);
    formBuilder.nextLine();

    formBuilder.addLeft(getComponentFactory().createLabel(GestureConstants.GESTURE_SET_PANEL_NOGC));
    formBuilder.addRight(new JLabel(Integer.toString(testSet.size())));
    
    JPanel basePanel = ComponentFactory.createBorderLayoutPanel();
    basePanel.add(formBuilder.getPanel(), BorderLayout.NORTH);
    
    setContent(basePanel);

  }

}
