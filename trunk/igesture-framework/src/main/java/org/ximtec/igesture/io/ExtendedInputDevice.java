/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Extension of the AbstractInputDevice class
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 16, 2008		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;



/**
 * Extension of the AbstractInputDevice class
 * @version 1.0 Jan 16, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class ExtendedInputDevice extends org.sigtec.input.AbstractInputDevice{
   
   protected boolean stop = false;
   
   public void stopLoop(){
      stop=true;
   }


}
