// automatically generated code
package org.ximtec.igesture.tool.view;

public interface IMainView {

  public void removeAllTabs();

  public void setTitlePostfix(java.io.File arg1);

  public void addTab(org.ximtec.igesture.tool.core.TabbedView arg1);

  public org.ximtec.igesture.tool.util.ComponentFactory getComponentFactory();

  public org.ximtec.igesture.tool.core.Controller getController();

  public void remove(java.awt.Component arg1);

  public javax.accessibility.AccessibleContext getAccessibleContext();

  public void setIconImage(java.awt.Image arg1);

  public java.awt.Container getContentPane();

  public java.awt.Component getGlassPane();

  public javax.swing.JLayeredPane getLayeredPane();

  public javax.swing.JRootPane getRootPane();

  public void setLayout(java.awt.LayoutManager arg1);

  public void update(java.awt.Graphics arg1);

  public java.awt.Graphics getGraphics();

  public void repaint(long arg1, int arg2, int arg3, int arg4, int arg5);

  public int getDefaultCloseOperation();

  public javax.swing.JMenuBar getJMenuBar();

  public javax.swing.TransferHandler getTransferHandler();

  public void setContentPane(java.awt.Container arg1);

  public void setDefaultCloseOperation(int arg1);

  public void setGlassPane(java.awt.Component arg1);

  public void setJMenuBar(javax.swing.JMenuBar arg1);

  public void setLayeredPane(javax.swing.JLayeredPane arg1);

  public void setTransferHandler(javax.swing.TransferHandler arg1);

  public void remove(java.awt.MenuComponent arg1);

  public int getState();

  public void setState(int arg1);

  public void addNotify();

  public int getCursorType();

  public int getExtendedState();

  public java.awt.Image getIconImage();

  public java.awt.Rectangle getMaximizedBounds();

  public java.awt.MenuBar getMenuBar();

  public java.lang.String getTitle();

  public boolean isResizable();

  public boolean isUndecorated();

  public void removeNotify();

  public void setCursor(int arg1);

  public void setExtendedState(int arg1);

  public void setMaximizedBounds(java.awt.Rectangle arg1);

  public void setMenuBar(java.awt.MenuBar arg1);

  public void setResizable(boolean arg1);

  public void setTitle(java.lang.String arg1);

  public void setUndecorated(boolean arg1);

  public void setSize(int arg1, int arg2);

  public void setSize(java.awt.Dimension arg1);

  public java.awt.Window getOwner();

  public java.awt.Toolkit getToolkit();

  public void setCursor(java.awt.Cursor arg1);

  public boolean postEvent(java.awt.Event arg1);

  public void addPropertyChangeListener(java.lang.String arg1, java.beans.PropertyChangeListener arg2);

  public void addPropertyChangeListener(java.beans.PropertyChangeListener arg1);

  public void addWindowFocusListener(java.awt.event.WindowFocusListener arg1);

  public void addWindowListener(java.awt.event.WindowListener arg1);

  public void addWindowStateListener(java.awt.event.WindowStateListener arg1);

  public void applyResourceBundle(java.lang.String arg1);

  public void applyResourceBundle(java.util.ResourceBundle arg1);

  public void createBufferStrategy(int arg1);

  public void createBufferStrategy(int arg1, java.awt.BufferCapabilities arg2) throws java.awt.AWTException;

  public void dispose();

  public java.awt.image.BufferStrategy getBufferStrategy();

  public java.awt.Component getFocusOwner();

  public java.util.Set<java.awt.AWTKeyStroke> getFocusTraversalKeys(int arg1);

  public boolean getFocusableWindowState();

  public java.awt.GraphicsConfiguration getGraphicsConfiguration();

  public java.util.List<java.awt.Image> getIconImages();

  public java.awt.im.InputContext getInputContext();

  public <T extends java.util.EventListener> T[] getListeners(java.lang.Class<T> arg1);

  public java.util.Locale getLocale();

  public java.awt.Dialog.ModalExclusionType getModalExclusionType();

  public java.awt.Component getMostRecentFocusOwner();

  public java.awt.Window[] getOwnedWindows();

  public java.awt.event.WindowFocusListener[] getWindowFocusListeners();

  public java.awt.event.WindowListener[] getWindowListeners();

  public java.awt.event.WindowStateListener[] getWindowStateListeners();

  public void hide();

  public boolean isActive();

  public boolean isAlwaysOnTopSupported();

  public boolean isFocused();

  public boolean isLocationByPlatform();

  public boolean isShowing();

  public void pack();

  public void removeWindowFocusListener(java.awt.event.WindowFocusListener arg1);

  public void removeWindowListener(java.awt.event.WindowListener arg1);

  public void removeWindowStateListener(java.awt.event.WindowStateListener arg1);

  public void reshape(int arg1, int arg2, int arg3, int arg4);

  public void setBounds(java.awt.Rectangle arg1);

  public void setBounds(int arg1, int arg2, int arg3, int arg4);

  public void setFocusableWindowState(boolean arg1);

  public void setIconImages(java.util.List<? extends java.awt.Image> arg1);

  public void setLocationByPlatform(boolean arg1);

  public void setLocationRelativeTo(java.awt.Component arg1);

  public void setMinimumSize(java.awt.Dimension arg1);

  public void setModalExclusionType(java.awt.Dialog.ModalExclusionType arg1);

  public void setVisible(boolean arg1);

  public void show();

  public void toBack();

  public void toFront();

  public void add(java.awt.Component arg1, java.lang.Object arg2, int arg3);

  public java.awt.Component add(java.awt.Component arg1, int arg2);

  public java.awt.Component add(java.lang.String arg1, java.awt.Component arg2);

  public java.awt.Component add(java.awt.Component arg1);

  public void add(java.awt.Component arg1, java.lang.Object arg2);

  public void remove(int arg1);

  public void list(java.io.PrintStream arg1, int arg2);

  public void list(java.io.PrintWriter arg1, int arg2);

  public void print(java.awt.Graphics arg1);

  public void removeAll();

  public void invalidate();

  public void applyComponentOrientation(java.awt.ComponentOrientation arg1);

  public java.awt.Component getComponent(int arg1);

  public int getComponentCount();

  public java.awt.FocusTraversalPolicy getFocusTraversalPolicy();

  public java.awt.Dimension getMinimumSize();

  public java.awt.Dimension getPreferredSize();

  public boolean isFocusCycleRoot(java.awt.Container arg1);

  public void validate();

  public void addContainerListener(java.awt.event.ContainerListener arg1);

  public boolean areFocusTraversalKeysSet(int arg1);

  public int countComponents();

  public void deliverEvent(java.awt.Event arg1);

  public void doLayout();

  public java.awt.Component findComponentAt(java.awt.Point arg1);

  public java.awt.Component findComponentAt(int arg1, int arg2);

  public float getAlignmentX();

  public float getAlignmentY();

  public java.awt.Component getComponentAt(java.awt.Point arg1);

  public java.awt.Component getComponentAt(int arg1, int arg2);

  public int getComponentZOrder(java.awt.Component arg1);

  public java.awt.Component[] getComponents();

  public java.awt.event.ContainerListener[] getContainerListeners();

  public java.awt.Insets getInsets();

  public java.awt.LayoutManager getLayout();

  public java.awt.Dimension getMaximumSize();

  public java.awt.Point getMousePosition(boolean arg1) throws java.awt.HeadlessException;

  public java.awt.Insets insets();

  public boolean isAncestorOf(java.awt.Component arg1);

  public boolean isFocusTraversalPolicySet();

  public void layout();

  public java.awt.Component locate(int arg1, int arg2);

  public java.awt.Dimension minimumSize();

  public void paint(java.awt.Graphics arg1);

  public void paintComponents(java.awt.Graphics arg1);

  public java.awt.Dimension preferredSize();

  public void printComponents(java.awt.Graphics arg1);

  public void removeContainerListener(java.awt.event.ContainerListener arg1);

  public void setComponentZOrder(java.awt.Component arg1, int arg2);

  public void setFocusTraversalKeys(int arg1, java.util.Set<? extends java.awt.AWTKeyStroke> arg2);

  public void setFocusTraversalPolicy(java.awt.FocusTraversalPolicy arg1);

  public void setFont(java.awt.Font arg1);

  public void transferFocusBackward();

  public void transferFocusDownCycle();

  public void add(java.awt.PopupMenu arg1);

  public java.lang.String toString();

  public java.lang.String getName();

  public boolean contains(java.awt.Point arg1);

  public boolean contains(int arg1, int arg2);

  public java.awt.Dimension size();

  public java.awt.Point getLocation(java.awt.Point arg1);

  public java.awt.Point getLocation();

  public java.awt.Container getParent();

  public void setName(java.lang.String arg1);

  public void list(java.io.PrintWriter arg1);

  public void list(java.io.PrintStream arg1);

  public void list();

  public java.awt.Dimension getSize(java.awt.Dimension arg1);

  public java.awt.Dimension getSize();

  public void resize(int arg1, int arg2);

  public void resize(java.awt.Dimension arg1);

  public boolean isOpaque();

  public void disable();

  public void enable();

  public void enable(boolean arg1);

  public java.awt.Point location();

  public boolean action(java.awt.Event arg1, java.lang.Object arg2);

  public void firePropertyChange(java.lang.String arg1, char arg2, char arg3);

  public void firePropertyChange(java.lang.String arg1, byte arg2, byte arg3);

  public void firePropertyChange(java.lang.String arg1, double arg2, double arg3);

  public void firePropertyChange(java.lang.String arg1, short arg2, short arg3);

  public void firePropertyChange(java.lang.String arg1, float arg2, float arg3);

  public void firePropertyChange(java.lang.String arg1, long arg2, long arg3);

  public java.awt.Cursor getCursor();

  public boolean isDisplayable();

  public java.awt.Font getFont();

  public java.awt.Color getBackground();

  public java.awt.Rectangle getBounds(java.awt.Rectangle arg1);

  public java.awt.Rectangle getBounds();

  public java.awt.Point getLocationOnScreen();

  public java.awt.peer.ComponentPeer getPeer();

  public int getX();

  public int getY();

  public boolean handleEvent(java.awt.Event arg1);

  public boolean isEnabled();

  public boolean isFocusable();

  public boolean isMinimumSizeSet();

  public boolean isVisible();

  public void requestFocus();

  public void setBackground(java.awt.Color arg1);

  public void setLocation(int arg1, int arg2);

  public void setLocation(java.awt.Point arg1);

  public void show(boolean arg1);

  public java.awt.Point getMousePosition() throws java.awt.HeadlessException;

  public boolean isFocusOwner();

  public boolean isLightweight();

  public boolean isMaximumSizeSet();

  public boolean isPreferredSizeSet();

  public boolean isValid();

  public void transferFocus();

  public void addComponentListener(java.awt.event.ComponentListener arg1);

  public void addFocusListener(java.awt.event.FocusListener arg1);

  public void addHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener arg1);

  public void addHierarchyListener(java.awt.event.HierarchyListener arg1);

  public void addInputMethodListener(java.awt.event.InputMethodListener arg1);

  public void addKeyListener(java.awt.event.KeyListener arg1);

  public void addMouseListener(java.awt.event.MouseListener arg1);

  public void addMouseMotionListener(java.awt.event.MouseMotionListener arg1);

  public void addMouseWheelListener(java.awt.event.MouseWheelListener arg1);

  public java.awt.Rectangle bounds();

  public int checkImage(java.awt.Image arg1, java.awt.image.ImageObserver arg2);

  public int checkImage(java.awt.Image arg1, int arg2, int arg3, java.awt.image.ImageObserver arg4);

  public java.awt.Image createImage(java.awt.image.ImageProducer arg1);

  public java.awt.Image createImage(int arg1, int arg2);

  public java.awt.image.VolatileImage createVolatileImage(int arg1, int arg2);

  public java.awt.image.VolatileImage createVolatileImage(int arg1, int arg2, java.awt.ImageCapabilities arg3) throws java.awt.AWTException;

  public void enableInputMethods(boolean arg1);

  public int getBaseline(int arg1, int arg2);

  public java.awt.Component.BaselineResizeBehavior getBaselineResizeBehavior();

  public java.awt.image.ColorModel getColorModel();

  public java.awt.event.ComponentListener[] getComponentListeners();

  public java.awt.ComponentOrientation getComponentOrientation();

  public java.awt.dnd.DropTarget getDropTarget();

  public java.awt.event.FocusListener[] getFocusListeners();

  public boolean getFocusTraversalKeysEnabled();

  public java.awt.FontMetrics getFontMetrics(java.awt.Font arg1);

  public java.awt.Color getForeground();

  public int getHeight();

  public java.awt.event.HierarchyBoundsListener[] getHierarchyBoundsListeners();

  public java.awt.event.HierarchyListener[] getHierarchyListeners();

  public boolean getIgnoreRepaint();

  public java.awt.event.InputMethodListener[] getInputMethodListeners();

  public java.awt.im.InputMethodRequests getInputMethodRequests();

  public java.awt.event.KeyListener[] getKeyListeners();

  public java.awt.event.MouseListener[] getMouseListeners();

  public java.awt.event.MouseMotionListener[] getMouseMotionListeners();

  public java.awt.event.MouseWheelListener[] getMouseWheelListeners();

  public java.beans.PropertyChangeListener[] getPropertyChangeListeners();

  public java.beans.PropertyChangeListener[] getPropertyChangeListeners(java.lang.String arg1);

  public int getWidth();

  public boolean gotFocus(java.awt.Event arg1, java.lang.Object arg2);

  public boolean hasFocus();

  public boolean imageUpdate(java.awt.Image arg1, int arg2, int arg3, int arg4, int arg5, int arg6);

  public boolean inside(int arg1, int arg2);

  public boolean isBackgroundSet();

  public boolean isCursorSet();

  public boolean isDoubleBuffered();

  public boolean isFocusTraversable();

  public boolean isFontSet();

  public boolean isForegroundSet();

  public boolean keyDown(java.awt.Event arg1, int arg2);

  public boolean keyUp(java.awt.Event arg1, int arg2);

  public boolean lostFocus(java.awt.Event arg1, java.lang.Object arg2);

  public boolean mouseDown(java.awt.Event arg1, int arg2, int arg3);

  public boolean mouseDrag(java.awt.Event arg1, int arg2, int arg3);

  public boolean mouseEnter(java.awt.Event arg1, int arg2, int arg3);

  public boolean mouseExit(java.awt.Event arg1, int arg2, int arg3);

  public boolean mouseMove(java.awt.Event arg1, int arg2, int arg3);

  public boolean mouseUp(java.awt.Event arg1, int arg2, int arg3);

  public void move(int arg1, int arg2);

  public void nextFocus();

  public void paintAll(java.awt.Graphics arg1);

  public boolean prepareImage(java.awt.Image arg1, int arg2, int arg3, java.awt.image.ImageObserver arg4);

  public boolean prepareImage(java.awt.Image arg1, java.awt.image.ImageObserver arg2);

  public void printAll(java.awt.Graphics arg1);

  public void removeComponentListener(java.awt.event.ComponentListener arg1);

  public void removeFocusListener(java.awt.event.FocusListener arg1);

  public void removeHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener arg1);

  public void removeHierarchyListener(java.awt.event.HierarchyListener arg1);

  public void removeInputMethodListener(java.awt.event.InputMethodListener arg1);

  public void removeKeyListener(java.awt.event.KeyListener arg1);

  public void removeMouseListener(java.awt.event.MouseListener arg1);

  public void removeMouseMotionListener(java.awt.event.MouseMotionListener arg1);

  public void removeMouseWheelListener(java.awt.event.MouseWheelListener arg1);

  public void removePropertyChangeListener(java.lang.String arg1, java.beans.PropertyChangeListener arg2);

  public void removePropertyChangeListener(java.beans.PropertyChangeListener arg1);

  public void repaint(long arg1);

  public void repaint(int arg1, int arg2, int arg3, int arg4);

  public void repaint();

  public boolean requestFocusInWindow();

  public void setComponentOrientation(java.awt.ComponentOrientation arg1);

  public void setDropTarget(java.awt.dnd.DropTarget arg1);

  public void setEnabled(boolean arg1);

  public void setFocusTraversalKeysEnabled(boolean arg1);

  public void setFocusable(boolean arg1);

  public void setForeground(java.awt.Color arg1);

  public void setIgnoreRepaint(boolean arg1);

  public void setLocale(java.util.Locale arg1);

  public void setMaximumSize(java.awt.Dimension arg1);

  public void setPreferredSize(java.awt.Dimension arg1);

  public void transferFocusUpCycle();

  public int hashCode();

  public boolean equals(java.lang.Object arg1);

}
