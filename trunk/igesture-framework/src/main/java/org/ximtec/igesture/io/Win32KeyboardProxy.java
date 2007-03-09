/*
 * @(#)Win32KeyboardProxy.java   1.0   March 09, 2007
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Simulates a keyboard 
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

package org.ximtec.igesture.io;

import java.io.IOException;

import sun.misc.ServiceConfigurationError;

import com.eaio.nativecall.IntCall;
import com.eaio.nativecall.NativeCall;

public class Win32KeyboardProxy {

	public static int SHIFT = 0x10;
	public static int CONTROL = 0x11;
	public static int ALT = 0x12;
	
	public static int VK_0 = 0x30;
	public static int VK_1 = 0x31;
	public static int VK_2 = 0x32;
	public static int VK_3 = 0x33;
	public static int VK_4 = 0x34;
	public static int VK_5 = 0x35;
	public static int VK_6 = 0x36;
	public static int VK_7 = 0x37;
	public static int VK_8 = 0x38;
	public static int VK_9 = 0x39;
	
	public static int A = 0X41;
	public static int B = 0X42;
	public static int C = 0X43;
	public static int D = 0X44;
	public static int E = 0X45;
	public static int F = 0X46;
	public static int G = 0X47;
	public static int H = 0X48;
	public static int I = 0X49;
	public static int J = 0X4A;
	public static int K = 0X4B;
	public static int L = 0X4C;
	public static int M = 0X4D;
	public static int N = 0X4E;
	public static int O = 0X4F;
	public static int P = 0X50;
	public static int Q = 0X51;
	public static int R = 0X52;
	public static int S = 0X53;
	public static int T = 0X54;
	public static int U = 0X55;
	public static int V = 0X56;
	public static int W = 0X57;
	public static int X = 0X58;
	public static int Y = 0X59;
	public static int Z = 0X5A;
	
	public static int F1 = 0x70;
	public static int F2 = 0x71;
	public static int F3 = 0x72;
	public static int F4 = 0x73;
	public static int F5 = 0x74;
	public static int F6 = 0x75;
	public static int F7 = 0x76;
	public static int F8 = 0x77;
	public static int F9 = 0x78;
	public static int F10 = 0x79;
	public static int F11 = 0x7A;
	public static int F12 = 0x7B;

	private static int KEY_DOWN = 0x0;
	private static int KEY_UP = 0x2;

	static {
		try {
			NativeCall.init();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final UnsatisfiedLinkError e) {
			e.printStackTrace();
		} catch (final UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final ServiceConfigurationError e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		pressKey(new int[]{ALT,F4});
	}
	
	public static void pressKey(int[] keys){
		for(int key:keys){
			keyDown(key);
		}
		for(int key:keys){
			keyUp(key);
		}
	}
	
	public static void pressKey(int key){
		keyDown(key);
		keyUp(key);
	}
	public static void pressKey(int key1, int key2){
		pressKey(new int[]{key1,key2});
	}
	
	public static void pressKey(int key1, int key2, int key3){
		pressKey(new int[]{key1,key2,key3});
	}
	
	private static void keyDown(int key) {
		nativeKey(key, KEY_DOWN);
	}

	private static void keyUp(int key) {
		nativeKey(key, KEY_UP);
	}
	
	private static void nativeKey(int key, int state){
		final IntCall ic = new IntCall("user32", "keybd_event");
		ic.executeCall(new Object[] { key, 0, state, 0 });
		ic.destroy();
	}
}
