/*
 * @(#)WacomUtils.java 1.0   Nov 14, 2007
 *
 * Author       :   Croci Michele, mcroci@gmail.com
 *
 * Purpose      :   Provide access to a Wacom Tablet PC.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 14, 2007    crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.wacom;

import org.ximtec.igesture.io.keyboard.KeyboardUtils.Kernel32;
import org.ximtec.igesture.io.wacom.Wintab32.LOGCONTEXTW;
import org.ximtec.igesture.io.wacom.Wintab32.PACKET;
import org.ximtec.igesture.io.win32.User32;
import org.ximtec.igesture.io.win32.User32.MSG;
import org.ximtec.igesture.io.win32.W32API.HANDLE;
import org.ximtec.igesture.io.win32.W32API.HDC;
import org.ximtec.igesture.io.win32.W32API.HINSTANCE;
import org.ximtec.igesture.io.win32.W32API.HWND;
import org.ximtec.igesture.io.win32.W32API.LPARAM;
import org.ximtec.igesture.io.win32.W32API.LRESULT;
import org.ximtec.igesture.io.win32.W32API.WPARAM;

import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

public class WacomUtils extends Thread{

	/** Provide access to a Wacom Tablet PC.
	 * 
	 * @author Michele Croci
	 */
	
	Wintab32 lib = Wintab32.INSTANCE;
	HDC hdc = new HDC();
	TabletProc tabletListener;
	HANDLE handle;
	HINSTANCE hinst;
	
	private WacomCallback wCallback;

	
	public WacomUtils() {}
	
    public WacomUtils(WacomCallback wCallback) {
       this.wCallback = wCallback;
    }
	
	   
	   private void init(){
          hinst = Kernel32.INSTANCE.GetModuleHandle(null);

          tabletListener = new TabletProc() {

             public LRESULT callback(int code, WPARAM wParam, LPARAM lParam) {
                PACKET p = getPacket();
                if((p!=null)){
                    fireEvent(p);
                }
                
                //discard 2 packets, otherwise too slow!
                PACKET[] arr = new PACKET[2];
                getPackets(2, arr);
               
                return User32.INSTANCE.CallNextHookEx(handle, code, wParam, lParam);
             }
          };
           handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_MOUSE_LL, tabletListener, hinst,0);
	   }
	   
	   private void fireEvent(PACKET p){
	      wCallback.callbackFunction(p.pkX, p.pkY, p.pkZ, p.pkStatus, p.pkNormalPressure, p.pkTangentPressure,
	             p.pkTime, p.pkOrientation, p.pkRotation, p.pkButtons);
	   }


	    
	 /**
     * Open session with wacom Tablet PC
     */
	    public void open() {

	    	HWND hwnd = new HWND();
	    	hwnd.setPointer(new IntByReference(Wintab32.HWND_BROADCAST).getPointer());//getPointer(0)
	    	int packetmode = 0;
	    	
	    	LOGCONTEXTW lg =  new LOGCONTEXTW();
	    	lib.WTInfoW(Wintab32.WTI_DEFSYSCTX, 0, lg);
	    	lg.lcPktMode= packetmode;
	    	//information to be retrieved, see wintab.h for values
	    	lg.lcPktData = 0x3FFF;
	    	lg.lcMoveMask = 0x3FFF; //0x180
	    	lg.lcBtnUpMask = lg.lcBtnDnMask;

	        hdc=lib.WTOpenW(hwnd, lg, true);
	      //  lib.WacomDoSpecialContextHandling(true);
	    }
	    
	    /**
	     * Close session with wacom Tablet PC
	     */
	    public void close() {
	    	boolean ret = lib.WTClose(hdc);
	    	lib.WacomDoSpecialContextHandling(false);
	    }
	    
	    
	    /**
	     * Get single packet
	     * 
	     * @return the packet (saved in an array)
	     */
	    public PACKET getPacket(){
	    	if (hdc == null)
	    		return null;
	    	
	    	PACKET p = new PACKET();
	    	lib.WTPacketsGet(hdc, 1,p);
	    	return p;

	    } 
	    
	    /**
         * Get single packet, discard 3
         * 
         * @return the packet (saved in an array)
         */
        public PACKET getOnePacket(){
           PACKET[] arr = new PACKET[10];
           if (hdc == null)
              return null;
          
          PACKET p = new PACKET();
          lib.WTPacketsGet(hdc, 1,p);
          lib.WTPacketsGet(hdc, 3,arr);//discard 3 packets
          return p;

        } 
	    
	    /**
         * Get n packets
         * 
         * @param number
         *      the maximal number of packets to be retrieved
         * 
         * @param arr
         *      the array where packets have to be saved
         * 
         * @return the received packets
         */
	    
	    public int getPackets(int number, PACKET[] arr){
	    	if (hdc == null)
	    		return -1;

	    	int ret = lib.WTPacketsGet(hdc, number,arr);
	    	return ret;
	    } 
	    
	    
	    public interface TabletProc extends StdCallCallback {

	       LRESULT callback(int code, WPARAM wParam, LPARAM lParam);
	    }

	    
	    /**
         * Register callback function
         */
	    private void registerHook(){
	       handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD_LL, tabletListener, hinst, 0); 
	    }
	    
	    
        /**
         * Run method
         */
	    public void run(){

	          init();
	     
	          registerHook();
	          open();
	           MSG msg = new MSG();
	           while(User32.INSTANCE.GetMessageW(msg,null,0,0)!=0){
	               //User32.INSTANCE.DispatchMessage(msg);
	           }
	       close();
	   }
	    
	     /**
         * Determine if a particular type of data can be retrieved or not
         * 
         * @param datatype
         *      the type representing the data
         * 
         * @return true if the data is supported, false otherwise
         */
	    
	    public boolean isDataSupported(int dataTypeId){
	    	int test;
	    	HWND hwnd = new HWND();
	    	hwnd.setPointer(new IntByReference(Wintab32.HWND_BROADCAST).getPointer());//getPointer(0)
	    	
	    	LOGCONTEXTW lg =  new LOGCONTEXTW();
	    	
	    	test = lib.WTInfoW(Wintab32.WTI_DEVICES, dataTypeId, lg);
	    	return test!=0;
	    }
	    
	    
	    //data types
		public static final int DVC_X			= 	12;
		public static final int DVC_Y			=	13;
		public static final int DVC_Z			=	14;
		public static final int DVC_NPRESSURE	=	15;
		public static final int DVC_TPRESSURE	=	16;
		public static final int DVC_ORIENTATION	=	17;
		public static final int DVC_ROTATION	=	18; /* 1.1 */
		
	}

