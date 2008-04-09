/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose    : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008   ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.core;

import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;


public interface Controller extends PropertyChangeListener {
   
   /**
    * Add a Controller
    * @param controller
    */
   void addController(Controller controller);
   
   /**
    * Remove a Controller
    * @param controller
    */
   void removeController(Controller controller);
   
   /**
    * Remove all Controller
    */
   void removeAllController();
   
   /**
    * Returns a list of Controller
    */
   List<Controller> getControllers();

   /**
    * Returns the View component belonging to this Controller.
    * @return
    */
   JComponent getView();
   
   void execute(Command command);

}
