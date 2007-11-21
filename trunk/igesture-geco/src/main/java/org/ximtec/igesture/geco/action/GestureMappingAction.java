/*
 * @(#)GestureActionMapping.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Interface describing general Action for Gesture mapping
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007 	crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.action;

import org.ximtec.igesture.core.GestureClass;



/**
 * Interface describing general Action for Gesture mapping
 * 
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public abstract class GestureMappingAction {
   
   public GestureClass gestureClass=null;
   
   public int test=0;
   
   public void executeAction(){}



}
