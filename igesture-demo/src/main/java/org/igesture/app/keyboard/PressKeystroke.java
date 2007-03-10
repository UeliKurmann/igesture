/*
 * @(#)PressKeystroke.java   1.0   March 09, 2007
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 09.03.2007       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.igesture.app.keyboard;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;
import org.ximtec.igesture.io.Win32KeyboardProxy;

public class PressKeystroke implements EventHandler {

	private Integer[] keys;

	public PressKeystroke(Integer[] keys) {
		this.keys = keys;
	}

	public void run(ResultSet resultSet) {
		Win32KeyboardProxy.pressKey(keys);
	}

}
