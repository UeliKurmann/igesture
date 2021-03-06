/*
 * @(#)$Id$
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

import java.awt.Dimension;
import java.net.URL;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.HtmlPanel;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;

/**
 * Comment
 * 
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */
public class TestSetsPanel extends AbstractPanel {

  private static final String HTML_FILE = "html/testSetTab.html";

  public TestSetsPanel(Controller controller, TestSetList testSetList) {
    super(controller);
    init();
  }

  private void init() {
    // TODO strings aus property file
    setTitle(TitleFactory.createStaticTitle("Test Set Management"));

    URL path = this.getClass().getClassLoader().getResource(HTML_FILE);

    HtmlPanel htmlPanel = new HtmlPanel(path, new Dimension(400, 400));

    setContent(htmlPanel);
  }

}
