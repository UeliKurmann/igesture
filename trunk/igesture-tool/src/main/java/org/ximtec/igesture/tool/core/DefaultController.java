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
 * 09.04.2008			ukurmann	Initial Release
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

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;



/**
 * Comment
 * @version 1.0 09.04.2008
 * @author Ueli Kurmann
 */
public abstract class DefaultController implements Controller {
   
   private List<Controller> controllers;

   public DefaultController(){
      controllers = new ArrayList<Controller>();
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.Controller#addController(org.ximtec.igesture.tool.core.Controller)
    */
   @Override
   public void addController(Controller controller) {
      controllers.add(controller);
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.Controller#getControllers()
    */
   @Override
   public List<Controller> getControllers() {
      return controllers;

   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.Controller#removeAllController()
    */
   @Override
   public void removeAllController() {
      controllers.clear();
   }


   @Override
   public void removeController(Controller controller) {
      controllers.remove(controller);

   }
   
   @Override
   public void execute(Command command) {
       for(Controller controller:controllers){
          controller.execute(command);
       }
   }


   @Override
   public void propertyChange(PropertyChangeEvent event) {
      for(Controller controller:getControllers()){
         controller.propertyChange(event);
      }
   }

}
