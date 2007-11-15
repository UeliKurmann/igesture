
/*
 * @(#)TabletUtils.java 1.0   Nov 14, 2007
 *
 * Author       :   Croci Michele, mcroci@gmail.com
 *
 * Purpose      : Provide access to a Tablet PC.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.wacom;

import org.ximtec.igesture.io.wacom.Wintab32.*;
import org.ximtec.igesture.io.win32.W32API.*;

import com.sun.jna.ptr.IntByReference;

public class TabletUtils {

	/** Provide access to a Wacom Tablet PC.
	 * 
	 * @author Michele Croci
	 */
	
	Wintab32 lib = Wintab32.INSTANCE;
	HDC hdc = new HDC();

	    
	 /**
     * Open session with wacom Tablet PC
     */
	    public void open() {
	    	System.out.println("TabletUnit: open()");

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
	        if (hdc != null){
	        	System.out.println("not null");
	        }
	        System.out.println("TabletUnit: open() ok");
	    }
	    
	    /**
	     * Close session with wacom Tablet PC
	     */
	    public void close() {
	    	boolean ret = lib.WTClose(hdc);
	    	System.out.println("return val: "+ret);
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
	    	/*
	    	if(test==0)
	    		return false;
	    	else
	    	{
	    		switch(dataTypeId){
	    		case(DVC_X): if ((lg.lcOutExtX==0)&&(lg.lcOutOrgX==0))
	    						return false;
	    					break;
	    		case(DVC_Y): if ((lg.lcOutExtY==0)&&(lg.lcOutOrgY==0))
					return false;
				break;
	    		case(DVC_Z): if ((lg.lcOutExtZ==0)&&(lg.lcOutOrgZ==0))
					return false;
				break;
	    						
	    		}
	    		return true;
	    	}
	    		*/
	    	//System.out.println (dataTypeId+" - "+test);
	    	//return lib.WTInfoW(Wintab32.WTI_DEFSYSCTX, dataTypeId, lg)!=0;
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

