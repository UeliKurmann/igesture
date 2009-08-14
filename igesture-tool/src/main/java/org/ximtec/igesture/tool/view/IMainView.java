package org.ximtec.igesture.tool.view;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.WindowListener;
import java.io.File;

import org.ximtec.igesture.tool.core.TabbedView;

public interface IMainView {

  public abstract void addTab(TabbedView view); // addTab

  public abstract void removeAllTabs(); // removeAllTabs

  /**
   * Sets the second part of the title banner. (filename)
   * 
   * @param file
   */
  public abstract void setTitlePostfix(File file);

  public abstract void addWindowListener(WindowListener mainWindowAdapter);

  public abstract Point getLocation();

  public abstract void setCursor(Cursor predefinedCursor);

}