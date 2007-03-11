/*
 * @(#)GestureKeyMapping.java   1.0   March 09, 2007
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Gesture - Keyboard Mapping
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

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.io.Win32KeyboardProxy;

public class GestureKeyMapping {
	
	private Integer[] keys;
	private String gestureName;
	
	
	public String getGestureName() {
		return gestureName;
	}
	
	public void setGestureName(String gestureName) {
		this.gestureName = gestureName;
	}
	
	public Integer[] getKeys() {
		return keys;
	}
	
	public void setKeys(Integer[] keys) {
		this.keys = keys;
	}
	
	public void setKeys(String keys){
		List<Integer> codes = new ArrayList<Integer>();
		for(String key:keys.split("\\+")){
			key = key.trim();
			int code = Win32KeyboardProxy.getKey(key);
			if(code > 0){
				codes.add(code);
			}else{
				throw new IllegalStateException("Key not recognised!");
			}
		}
		this.keys = codes.toArray(new Integer[0]);
	}

	public GestureKeyMapping(String gesture, String keys) {
		super();
		setKeys(keys);
		this.gestureName = gesture;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int key:keys){
			sb.append(key+"+");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" -> "+gestureName+"]");
		return sb.toString();
	}
}
