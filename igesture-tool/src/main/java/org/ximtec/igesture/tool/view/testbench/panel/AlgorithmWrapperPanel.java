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
 * 23.03.2008		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.panel;

import org.ximtec.igesture.tool.view.admin.panel.AbstractAdminPanel;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class AlgorithmWrapperPanel extends AbstractAdminPanel {

   public AlgorithmWrapperPanel(AlgorithmWrapper algorithm){
      // FIXME Implement View
      setTitle(algorithm.getAlgorithm().getName());
   }
}
