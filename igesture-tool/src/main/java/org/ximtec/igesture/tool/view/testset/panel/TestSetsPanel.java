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
 * 07.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testset.panel;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;



/**
 * Comment
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */
public class TestSetsPanel extends AbstractPanel {
   
   public TestSetsPanel(Controller controller, TestSetList testSetList){
      setTitle(TitleFactory.createStaticTitle("Test Sets Panel"));
   }

}
