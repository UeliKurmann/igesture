/*
 * @(#)$Id: Mapping.java 689 2009-07-22 00:10:27Z bsigner $
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   MainView Interface
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Aug 14, 2008     ukurmann    Initial Release
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

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.WindowListener;
import java.io.File;

import org.ximtec.igesture.tool.core.TabbedView;

public interface IMainView {

  public void addTab(TabbedView view);

  public void removeAllTabs();

  public void setTitlePostfix(File file);

  public void addWindowListener(WindowListener mainWindowAdapter);

  public Point getLocation();

  public void setCursor(Cursor predefinedCursor);

}