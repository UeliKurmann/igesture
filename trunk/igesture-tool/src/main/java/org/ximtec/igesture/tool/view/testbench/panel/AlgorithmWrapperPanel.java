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

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class AlgorithmWrapperPanel extends AbstractPanel {

   public AlgorithmWrapperPanel(Controller controller, AlgorithmWrapper algorithm){
      // FIXME Implement View
      setTitle(TitleFactory.createStaticTitle(algorithm.getAlgorithm().getSimpleName()));
   }
}