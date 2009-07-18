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
 * 17.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.welcome;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.JComponent;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.util.HtmlPanel;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;

/**
 * Comment
 * 
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class WelcomeView extends AbstractPanel implements TabbedView {

  private static final String HTML_FILE = "html/welcomeTab.html";

  public WelcomeView(Controller controller) {
    super(controller);
    init();
  }

  private void init() {
    setTitle(TitleFactory.createStaticTitle("Welcome to iGesture Tool 1.2"));
    URL path = WelcomeView.class.getClassLoader().getResource(HTML_FILE);
    HtmlPanel htmlPanel = new HtmlPanel(path, new Dimension(400, 400));
    setCenter(htmlPanel);
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getTabName() {
    // FIXME: property file
    return "Welcome";
  }

  @Override
  public JComponent getPane() {
    return this;
  }

  @Override
  public void refresh() {
    super.refresh();
    init();
  }

}
