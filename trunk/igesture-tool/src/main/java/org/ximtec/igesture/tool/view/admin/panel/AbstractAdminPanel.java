package org.ximtec.igesture.tool.view.admin.panel;

import hacks.FontFactory;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;

public abstract class AbstractAdminPanel extends DefaultExplorerTreeView {

  public AbstractAdminPanel() {
    setLayout(new BorderLayout());
    
    this.setForeground(Color.white);
    this.setBackground(Color.white);
    
  }

  public void setTitle(String s) {
    JLabel title = new JLabel(s);
    title.setFont(FontFactory.getArialBold(24));
    this.add(title, BorderLayout.NORTH);
  }

  public void setCenter(JComponent component) {
    this.add(component, BorderLayout.CENTER);
  }
  
  public void setBottom(JComponent component){
    this.add(component, BorderLayout.SOUTH);
  }
  
  

}
