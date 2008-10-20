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

import java.awt.Dimension;
import java.net.URL;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.HtmlPanel;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class AlgorithmListPanel extends AbstractPanel {

   public AlgorithmListPanel(Controller controller, AlgorithmList algorithmList){
      // FIXME Implement View
      setTitle(TitleFactory.createStaticTitle("Test Bench"));
      
      URL path = AlgorithmListPanel.class.getClassLoader().getResource("html/testBenchTab.html");

      HtmlPanel htmlPanel = new HtmlPanel(path, new Dimension(400,400));
      
      setCenter(htmlPanel);      
   }
}
