/*
 * @(#)$Id:$
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


package org.ximtec.igesture.tool.view.batch;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.ximtec.igesture.tool.core.TabbedView;



/**
 * Comment
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class BatchView extends JPanel implements TabbedView{

   
   
   
   @Override
   public Icon getIcon() {
      return null;
   }

   @Override
   public String getName() {
      // FIXME use resource file
      return "Batch Processing";
   }

   @Override
   public JComponent getPane() {
      return this;
   }

}
