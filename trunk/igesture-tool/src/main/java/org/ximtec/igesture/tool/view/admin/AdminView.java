

package org.ximtec.igesture.tool.view.admin;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTree;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;


public class AdminView extends JSplitPane implements TabbedView,
      ExplorerTreeContainer {

   private JScrollPane scrollPaneLeft;


   public AdminView() {
      super(JSplitPane.HORIZONTAL_SPLIT);

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
   public String getName() {
      return "Admin";
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
   }

}
