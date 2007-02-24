/*
 * @(#)ConfigurationListModel.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Configuration List Model
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 4.12.2006		ukurmann	Initial Release
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

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureMainModel;
import org.ximtec.igesture.tool.utils.SimpleListModel;


/**
 * Comment
 * 
 * @version 1.0 Dec 4, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ConfigurationListModel extends SimpleListModel<Configuration> {

   public ConfigurationListModel(GestureMainModel mainModel) {
      super(mainModel.getConfigurations());
   }


   @Override
   public Object getElementAt(int i) {
      final String algoName = ((Configuration) super.getElementAt(i))
            .getAlgorithms().get(0);
      return algoName.substring(algoName.lastIndexOf(".") + 1);
   }


   public Configuration getInstanceAt(int i) {
      return (Configuration) super.getElementAt(i);
   }
}
