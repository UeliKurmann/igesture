/*
 * @(#)BaseAction.java	1.0   07.06.2006
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		:   Base action for all actions.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jun 07, 2006     bsigner		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.utils;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.sigtec.util.GuiBundle;


/**
 * Base action for all actions.
 * 
 * @version 1.0 Jun 2006
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class BaseAction extends AbstractAction {

   public BaseAction(String keyName, GuiBundle guiBundle) {
      super();
      init(keyName, guiBundle);
   }


   /**
    * Initialises the action
    * 
    * @param keyName
    * @param guiBundle
    */
   private void init(String keyName, GuiBundle guiBundle) {
      final String name = guiBundle.getName(keyName);

      if (name != null) {
         putValue(NAME, name);
      }

      final KeyStroke mnemonicKey = guiBundle.getMnemonicKey(keyName);

      if (mnemonicKey != null) {
         putValue(MNEMONIC_KEY, mnemonicKey.getKeyCode());
      }

      final String shortDescription = guiBundle.getShortDescription(keyName);

      if (shortDescription != null) {
         putValue(SHORT_DESCRIPTION, shortDescription);
      }

      final String longDescription = guiBundle.getLongDescription(keyName);

      if (longDescription != null) {
         putValue(LONG_DESCRIPTION, longDescription);
      }

      final KeyStroke acceleratorKey = guiBundle.getAcceleratorKey(keyName);

      if (acceleratorKey != null) {
         putValue(ACCELERATOR_KEY, acceleratorKey.getKeyCode());
      }

      final Icon smallIcon = guiBundle.getSmallIcon(keyName);

      if (smallIcon != null) {
         putValue(SMALL_ICON, smallIcon);
      }

   } // init

}
