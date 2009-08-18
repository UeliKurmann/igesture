// automatically generated code
package org.ximtec.igesture.tool.view.testbench;

import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;

public interface ITestBenchView extends TabbedView,
ExplorerTreeContainer{

  public java.lang.String getTabName();

  public javax.swing.JComponent getPane();

  public javax.swing.Icon getIcon();

  public void setView(javax.swing.JComponent arg1);

  public void setTree(org.ximtec.igesture.tool.explorer.ExplorerTree arg1);

  public org.ximtec.igesture.tool.util.ComponentFactory getComponentFactory();

  public org.ximtec.igesture.tool.core.Controller getController();

  public void remove(java.awt.Component arg1);

  public void remove(int arg1);

  public void removeAll();

  public javax.accessibility.AccessibleContext getAccessibleContext();

  public int getOrientation();

  public javax.swing.plaf.SplitPaneUI getUI();

  public java.lang.String getUIClassID();

  public void setUI(javax.swing.plaf.SplitPaneUI arg1);

  public void updateUI();

  public boolean isValidateRoot();

  public void setOrientation(int arg1);

  public java.awt.Component getBottomComponent();

  public int getDividerLocation();

  public int getDividerSize();

  public int getLastDividerLocation();

  public java.awt.Component getLeftComponent();

  public int getMaximumDividerLocation();

  public int getMinimumDividerLocation();

  public double getResizeWeight();

  public java.awt.Component getRightComponent();

  public java.awt.Component getTopComponent();

  public boolean isContinuousLayout();

  public boolean isOneTouchExpandable();

  public void resetToPreferredSizes();

  public void setBottomComponent(java.awt.Component arg1);

  public void setContinuousLayout(boolean arg1);

  public void setDividerLocation(int arg1);

  public void setDividerLocation(double arg1);

  public void setDividerSize(int arg1);

  public void setLastDividerLocation(int arg1);

  public void setLeftComponent(java.awt.Component arg1);

  public void setOneTouchExpandable(boolean arg1);

  public void setResizeWeight(double arg1);

  public void setRightComponent(java.awt.Component arg1);

  public void setTopComponent(java.awt.Component arg1);

  public boolean contains(int arg1, int arg2);

  public java.awt.Point getLocation(java.awt.Point arg1);

  public void print(java.awt.Graphics arg1);

  public java.awt.Dimension getSize(java.awt.Dimension arg1);

  public boolean isOpaque();

  public void disable();

  public void enable();

  public void addNotify();

  public void firePropertyChange(java.lang.String arg1, char arg2, char arg3);

  public void firePropertyChange(java.lang.String arg1, boolean arg2, boolean arg3);

  public void firePropertyChange(java.lang.String arg1, int arg2, int arg3);

  public void removeNotify();

  public void setOpaque(boolean arg1);

  public java.awt.Rectangle getBounds(java.awt.Rectangle arg1);

  public <T extends java.util.EventListener> T[] getListeners(java.lang.Class<T> arg1);

  public java.awt.Dimension getMinimumSize();

  public java.awt.Dimension getPreferredSize();

  public javax.swing.JRootPane getRootPane();

  public int getX();

  public int getY();

  public boolean requestFocus(boolean arg1);

  public void requestFocus();

  public void reshape(int arg1, int arg2, int arg3, int arg4);

  public void setBackground(java.awt.Color arg1);

  public void setDoubleBuffered(boolean arg1);

  public void setMinimumSize(java.awt.Dimension arg1);

  public void setVisible(boolean arg1);

  public float getAlignmentX();

  public float getAlignmentY();

  public java.awt.Insets getInsets(java.awt.Insets arg1);

  public java.awt.Insets getInsets();

  public java.awt.Dimension getMaximumSize();

  public void paint(java.awt.Graphics arg1);

  public void setFocusTraversalKeys(int arg1, java.util.Set<? extends java.awt.AWTKeyStroke> arg2);

  public void setFont(java.awt.Font arg1);

  public void update(java.awt.Graphics arg1);

  public int getBaseline(int arg1, int arg2);

  public java.awt.Component.BaselineResizeBehavior getBaselineResizeBehavior();

  public java.awt.FontMetrics getFontMetrics(java.awt.Font arg1);

  public java.awt.Graphics getGraphics();

  public int getHeight();

  public int getWidth();

  public boolean isDoubleBuffered();

  public void printAll(java.awt.Graphics arg1);

  public void repaint(java.awt.Rectangle arg1);

  public void repaint(long arg1, int arg2, int arg3, int arg4, int arg5);

  public boolean requestFocusInWindow();

  public void setEnabled(boolean arg1);

  public void setForeground(java.awt.Color arg1);

  public void setMaximumSize(java.awt.Dimension arg1);

  public void setPreferredSize(java.awt.Dimension arg1);

  public void addVetoableChangeListener(java.beans.VetoableChangeListener arg1);

  public java.beans.VetoableChangeListener[] getVetoableChangeListeners();

  public void removeVetoableChangeListener(java.beans.VetoableChangeListener arg1);

  public javax.swing.TransferHandler getTransferHandler();

  public void setTransferHandler(javax.swing.TransferHandler arg1);

  public void revalidate();

  public void setAlignmentX(float arg1);

  public void addAncestorListener(javax.swing.event.AncestorListener arg1);

  public void computeVisibleRect(java.awt.Rectangle arg1);

  public javax.swing.JToolTip createToolTip();

  public java.awt.event.ActionListener getActionForKeyStroke(javax.swing.KeyStroke arg1);

  public javax.swing.event.AncestorListener[] getAncestorListeners();

  public boolean getAutoscrolls();

  public javax.swing.border.Border getBorder();

  public javax.swing.JPopupMenu getComponentPopupMenu();

  public int getConditionForKeyStroke(javax.swing.KeyStroke arg1);

  public int getDebugGraphicsOptions();

  public boolean getInheritsPopupMenu();

  public javax.swing.InputVerifier getInputVerifier();

  public java.awt.Component getNextFocusableComponent();

  public java.awt.Point getPopupLocation(java.awt.event.MouseEvent arg1);

  public javax.swing.KeyStroke[] getRegisteredKeyStrokes();

  public java.awt.Point getToolTipLocation(java.awt.event.MouseEvent arg1);

  public java.lang.String getToolTipText(java.awt.event.MouseEvent arg1);

  public java.lang.String getToolTipText();

  public java.awt.Container getTopLevelAncestor();

  public boolean getVerifyInputWhenFocusTarget();

  public java.awt.Rectangle getVisibleRect();

  public void grabFocus();

  public boolean isManagingFocus();

  public boolean isOptimizedDrawingEnabled();

  public boolean isPaintingTile();

  public boolean isRequestFocusEnabled();

  public void paintImmediately(int arg1, int arg2, int arg3, int arg4);

  public void paintImmediately(java.awt.Rectangle arg1);

  public void registerKeyboardAction(java.awt.event.ActionListener arg1, javax.swing.KeyStroke arg2, int arg3);

  public void registerKeyboardAction(java.awt.event.ActionListener arg1, java.lang.String arg2, javax.swing.KeyStroke arg3, int arg4);

  public void removeAncestorListener(javax.swing.event.AncestorListener arg1);

  public boolean requestDefaultFocus();

  public void resetKeyboardActions();

  public void scrollRectToVisible(java.awt.Rectangle arg1);

  public void setAlignmentY(float arg1);

  public void setAutoscrolls(boolean arg1);

  public void setBorder(javax.swing.border.Border arg1);

  public void setComponentPopupMenu(javax.swing.JPopupMenu arg1);

  public void setDebugGraphicsOptions(int arg1);

  public void setInheritsPopupMenu(boolean arg1);

  public void setInputVerifier(javax.swing.InputVerifier arg1);

  public void setNextFocusableComponent(java.awt.Component arg1);

  public void setRequestFocusEnabled(boolean arg1);

  public void setToolTipText(java.lang.String arg1);

  public void setVerifyInputWhenFocusTarget(boolean arg1);

  public void unregisterKeyboardAction(javax.swing.KeyStroke arg1);

  public void add(java.awt.Component arg1, java.lang.Object arg2, int arg3);

  public java.awt.Component add(java.awt.Component arg1, int arg2);

  public java.awt.Component add(java.lang.String arg1, java.awt.Component arg2);

  public java.awt.Component add(java.awt.Component arg1);

  public void add(java.awt.Component arg1, java.lang.Object arg2);

  public void list(java.io.PrintStream arg1, int arg2);

  public void list(java.io.PrintWriter arg1, int arg2);

  public void addPropertyChangeListener(java.beans.PropertyChangeListener arg1);

  public void addPropertyChangeListener(java.lang.String arg1, java.beans.PropertyChangeListener arg2);

  public void applyComponentOrientation(java.awt.ComponentOrientation arg1);

  public java.awt.Component getComponent(int arg1);

  public int getComponentCount();

  public java.util.Set<java.awt.AWTKeyStroke> getFocusTraversalKeys(int arg1);

  public java.awt.FocusTraversalPolicy getFocusTraversalPolicy();

  public void invalidate();

  public boolean isFocusCycleRoot();

  public boolean isFocusCycleRoot(java.awt.Container arg1);

  public void setFocusCycleRoot(boolean arg1);

  public void setLayout(java.awt.LayoutManager arg1);

  public void validate();

  public void addContainerListener(java.awt.event.ContainerListener arg1);

  public boolean areFocusTraversalKeysSet(int arg1);

  public int countComponents();

  public void deliverEvent(java.awt.Event arg1);

  public void doLayout();

  public java.awt.Component findComponentAt(int arg1, int arg2);

  public java.awt.Component findComponentAt(java.awt.Point arg1);

  public java.awt.Component getComponentAt(int arg1, int arg2);

  public java.awt.Component getComponentAt(java.awt.Point arg1);

  public int getComponentZOrder(java.awt.Component arg1);

  public java.awt.Component[] getComponents();

  public java.awt.event.ContainerListener[] getContainerListeners();

  public java.awt.LayoutManager getLayout();

  public java.awt.Point getMousePosition(boolean arg1) throws java.awt.HeadlessException;

  public java.awt.Insets insets();

  public boolean isAncestorOf(java.awt.Component arg1);

  public boolean isFocusTraversalPolicySet();

  public void layout();

  public java.awt.Component locate(int arg1, int arg2);

  public java.awt.Dimension minimumSize();

  public void paintComponents(java.awt.Graphics arg1);

  public java.awt.Dimension preferredSize();

  public void printComponents(java.awt.Graphics arg1);

  public void removeContainerListener(java.awt.event.ContainerListener arg1);

  public void setComponentZOrder(java.awt.Component arg1, int arg2);

  public void setFocusTraversalPolicy(java.awt.FocusTraversalPolicy arg1);

  public void transferFocusBackward();

  public void transferFocusDownCycle();

  public void add(java.awt.PopupMenu arg1);

  public java.lang.String toString();

  public java.lang.String getName();

  public boolean contains(java.awt.Point arg1);

  public java.awt.Dimension size();

  public java.awt.Point getLocation();

  public java.awt.Container getParent();

  public void remove(java.awt.MenuComponent arg1);

  public void setName(java.lang.String arg1);

  public void list();

  public void list(java.io.PrintStream arg1);

  public void list(java.io.PrintWriter arg1);

  public java.awt.Dimension getSize();

  public void setSize(java.awt.Dimension arg1);

  public void setSize(int arg1, int arg2);

  public void resize(java.awt.Dimension arg1);

  public void resize(int arg1, int arg2);

  public void enable(boolean arg1);

  public java.awt.Point location();

  public boolean action(java.awt.Event arg1, java.lang.Object arg2);

  public void firePropertyChange(java.lang.String arg1, double arg2, double arg3);

  public void firePropertyChange(java.lang.String arg1, byte arg2, byte arg3);

  public void firePropertyChange(java.lang.String arg1, short arg2, short arg3);

  public void firePropertyChange(java.lang.String arg1, float arg2, float arg3);

  public void firePropertyChange(java.lang.String arg1, long arg2, long arg3);

  public java.awt.Cursor getCursor();

  public java.awt.Toolkit getToolkit();

  public boolean isDisplayable();

  public void setCursor(java.awt.Cursor arg1);

  public java.awt.Font getFont();

  public boolean postEvent(java.awt.Event arg1);

  public java.awt.Color getBackground();

  public java.awt.Rectangle getBounds();

  public java.awt.Container getFocusCycleRootAncestor();

  public java.awt.GraphicsConfiguration getGraphicsConfiguration();

  public java.awt.im.InputContext getInputContext();

  public java.util.Locale getLocale();

  public java.awt.Point getLocationOnScreen();

  public java.awt.peer.ComponentPeer getPeer();

  public boolean handleEvent(java.awt.Event arg1);

  public void hide();

  public boolean isEnabled();

  public boolean isFocusable();

  public boolean isMinimumSizeSet();

  public boolean isShowing();

  public boolean isVisible();

  public void setBounds(int arg1, int arg2, int arg3, int arg4);

  public void setBounds(java.awt.Rectangle arg1);

  public void setLocation(java.awt.Point arg1);

  public void setLocation(int arg1, int arg2);

  public void show();

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

  public java.awt.image.ColorModel getColorModel();

  public java.awt.event.ComponentListener[] getComponentListeners();

  public java.awt.ComponentOrientation getComponentOrientation();

  public java.awt.dnd.DropTarget getDropTarget();

  public java.awt.event.FocusListener[] getFocusListeners();

  public boolean getFocusTraversalKeysEnabled();

  public java.awt.Color getForeground();

  public java.awt.event.HierarchyBoundsListener[] getHierarchyBoundsListeners();

  public java.awt.event.HierarchyListener[] getHierarchyListeners();

  public boolean getIgnoreRepaint();

  public java.awt.event.InputMethodListener[] getInputMethodListeners();

  public java.awt.im.InputMethodRequests getInputMethodRequests();

  public java.awt.event.KeyListener[] getKeyListeners();

  public java.awt.event.MouseListener[] getMouseListeners();

  public java.awt.event.MouseMotionListener[] getMouseMotionListeners();

  public java.awt.event.MouseWheelListener[] getMouseWheelListeners();

  public java.beans.PropertyChangeListener[] getPropertyChangeListeners(java.lang.String arg1);

  public java.beans.PropertyChangeListener[] getPropertyChangeListeners();

  public boolean gotFocus(java.awt.Event arg1, java.lang.Object arg2);

  public boolean hasFocus();

  public boolean imageUpdate(java.awt.Image arg1, int arg2, int arg3, int arg4, int arg5, int arg6);

  public boolean inside(int arg1, int arg2);

  public boolean isBackgroundSet();

  public boolean isCursorSet();

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

  public void removeComponentListener(java.awt.event.ComponentListener arg1);

  public void removeFocusListener(java.awt.event.FocusListener arg1);

  public void removeHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener arg1);

  public void removeHierarchyListener(java.awt.event.HierarchyListener arg1);

  public void removeInputMethodListener(java.awt.event.InputMethodListener arg1);

  public void removeKeyListener(java.awt.event.KeyListener arg1);

  public void removeMouseListener(java.awt.event.MouseListener arg1);

  public void removeMouseMotionListener(java.awt.event.MouseMotionListener arg1);

  public void removeMouseWheelListener(java.awt.event.MouseWheelListener arg1);

  public void removePropertyChangeListener(java.beans.PropertyChangeListener arg1);

  public void removePropertyChangeListener(java.lang.String arg1, java.beans.PropertyChangeListener arg2);

  public void repaint();

  public void repaint(long arg1);

  public void repaint(int arg1, int arg2, int arg3, int arg4);

  public void setComponentOrientation(java.awt.ComponentOrientation arg1);

  public void setDropTarget(java.awt.dnd.DropTarget arg1);

  public void setFocusTraversalKeysEnabled(boolean arg1);

  public void setFocusable(boolean arg1);

  public void setIgnoreRepaint(boolean arg1);

  public void setLocale(java.util.Locale arg1);

  public void transferFocusUpCycle();

  public int hashCode();

  public boolean equals(java.lang.Object arg1);

}
