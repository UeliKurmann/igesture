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
 * 07.10.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testset;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultSplitPane;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTree;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;

public class TestSetView extends DefaultSplitPane implements TabbedView, ExplorerTreeContainer, ITestSetView {

  private JScrollPane scrollPaneLeft;

  public TestSetView(Controller controller) {
    super(controller);

    setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    scrollPaneLeft = new JScrollPane();
    scrollPaneLeft.setOpaque(true);
    scrollPaneLeft.setBackground(Color.blue);
    scrollPaneLeft.setForeground(Color.blue);
    setLeftComponent(scrollPaneLeft);
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getTabName() {
    return getComponentFactory().getGuiBundle().getName(GestureConstants.TEST_SET_VIEW);
  }

  @Override
  public JComponent getPane() {
    return this;
  }

  @Override
  public void setTree(ExplorerTree tree) {
    tree.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    scrollPaneLeft.setViewportView(tree);
  }

  @Override
  public void setView(JComponent view) {
    setRightComponent(view);
    //TODO Cleanup
    setDividerLocation(getDividerLocation());
  }

}
