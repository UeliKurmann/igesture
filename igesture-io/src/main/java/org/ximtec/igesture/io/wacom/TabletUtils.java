
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
	    public long[] getPacket(){
	    	if (hdc == null)
	    		return null;
	    	
	    	long[] val = new long[11];
	    	PACKET p = new PACKET();
	    	int ret = lib.WTPacketsGet(hdc, 1,p);
	    	
	    	if (ret>0){
				val[0] = p.pkTime;
				val[1] = p.pkX;	
				val[2] = p.pkY;	
				val[3] = p.pkZ;	
				val[4] = p.pkOrientation.orAltitude;
				val[5] = p.pkOrientation.orAzimuth;
				val[5] = p.pkOrientation.orTwist;
				val[6] = p.pkRotation.roPitch;
				val[7] = p.pkRotation.roRoll;
				val[8] = p.pkRotation.roYaw;
				val[9] = p.pkNormalPressure;
				val[10] = p.pkTangentPressure;
				return val;
	    	}
	    	
	    	return null;
	    } 
	    
	    /**
         * Get n packets
         * 
         * @param number
         *      the maximal number of packets to be retrieved
         * 
         * @return the received packets
         */
	    
	    public long[][] getPackets(int number){
	    	if (hdc == null)
	    		return null;
	    	
	   	
	    	long[]packet = new long[11];
	    	PACKET p = new PACKET();
	    	int ret = lib.WTPacketsGet(hdc, number,p);
	    	long[][] val = new long[11][ret];
	    	
	    	while (ret>0){
	    	   packet[0] = p.pkTime;
	    	   packet[1] = p.pkX;	
				packet[2] = p.pkY;	
				packet[3] = p.pkZ;	
				packet[4] = p.pkOrientation.orAltitude;
				packet[5] = p.pkOrientation.orAzimuth;
				packet[5] = p.pkOrientation.orTwist;
				packet[6] = p.pkRotation.roPitch;
				packet[7] = p.pkRotation.roRoll;
				packet[8] = p.pkRotation.roYaw;
				packet[9] = p.pkNormalPressure;
				packet[10] = p.pkTangentPressure;
				val[ret]=packet;
	    	}
	    	
	    	return val;
	    } 
	    
	       public int getPackets2(int number){
	            if (hdc == null)
	                return -1;
	            
	            
	            //long[]packet = new long[11];
	            PACKET[] p = new PACKET[number];
	            return lib.WTPacketsGet(hdc, number,p);
	            
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

