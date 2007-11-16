/*
 * @(#)ConfigurationListModel.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Configuration list model.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 04, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.algorithm;

import org.sigtec.graphix.SimpleListModel;
import org.sigtec.util.Constant;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureMainModel;


/**
 * Configuration list model.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ConfigurationListModel extends SimpleListModel<Configuration> {

   public ConfigurationListModel(GestureMainModel mainModel) {
      super(mainModel.getConfigurations());
   }


   @Override
   public Object getElementAt(int index) {
      final String algoName = ((Configuration)super.getElementAt(index))
            .getAlgorithms().get(0);
      return algoName.substring(algoName.lastIndexOf(Constant.DOT) + 1);
   } // getElementAt


   public Configuration getInstanceAt(int index) {
      return (Configuration)super.getElementAt(index);
   } // getInstanceAt

}
