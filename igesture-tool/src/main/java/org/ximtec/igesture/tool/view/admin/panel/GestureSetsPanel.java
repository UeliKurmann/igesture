

package org.ximtec.igesture.tool.view.admin.panel;

import org.ximtec.igesture.tool.view.RootSet;


public class GestureSetsPanel extends AbstractAdminPanel {

   RootSet rootSet;


   public GestureSetsPanel(RootSet rootSet) {
      this.rootSet = rootSet;
      init();
   }


   private void init() {
      setTitle("Gesture Sets");
   }

}
