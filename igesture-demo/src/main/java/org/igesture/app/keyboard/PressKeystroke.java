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
		System.out.println(resultSet.getResult().getGestureClassName());
		Win32KeyboardProxy.pressKey(keys);
	}

}
