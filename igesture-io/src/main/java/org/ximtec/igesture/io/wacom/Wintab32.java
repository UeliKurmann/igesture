/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Access to wintab32.dll.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Oct 22, 2007     crocimi         Initial Release
 * 
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */



package org.ximtec.igesture.io.wacom;





import org.ximtec.igesture.io.win32.W32API;

import com.sun.jna.Structure;


/** Provides access to the w32 wintab32 library.
 *  
 * @author Michele Croci
 */

public interface Wintab32 extends W32API {

	  
	    
	    static int HWND_BROADCAST = 0xffff;
    	static int WTI_DEFCONTEXT = 3; //first parameter for WTInfo (not move cursor)
    	static int WTI_DEFSYSCTX = 4; //first parameter for WTInfo (move cursor)
    	static int WTI_DEVICES = 100; //status info about device

	    HDC WTOpenW(HWND hwnd, LOGCONTEXTW s, boolean b);
	    
	    boolean WTClose(HDC hDC);
	    
	    int WTInfoW(int category, int index, LOGCONTEXTW s);
	    
	    int WTQueueSizeGet(HDC hdc);
	    
	    //The return value is the number of packets copied in the buffer
	    int WTPacketsGet(HDC hdc, int max, PACKET p);
	    int WTPacketsGet(HDC hdc, int max, PACKET[] p);
	    boolean WTPacket(HDC hdc, int wSerial, PACKET p);
	    void WacomDoSpecialContextHandling(boolean b); 
	    

	    HANDLE WTMgrPacketHookEx(HINSTANCE hmgr, int nType, String module, String hookproc); //not sure
	    
	    
	    public static class LOGCONTEXTW extends Structure {
	    	// see wintab32.dll specification
	    	
			//public String lcName;
			public char[] lcName=new char[40];
			public int	lcOptions;
			public int	lcStatus;
			public int	lcLocks;
			public int	lcMsgBase;
			public int	lcDevice;
			public int	lcPktRate;
			public int	lcPktData; 
			public int	lcPktMode;
			public int	lcMoveMask; 
			public int	lcBtnDnMask;
			public int	lcBtnUpMask;
			public int	lcInOrgX;
			public int	lcInOrgY;
			public int	lcInOrgZ;
			public int	lcInExtX;
			public int	lcInExtY;
			public int	lcInExtZ;
			public int	lcOutOrgX;
			public int	lcOutOrgY;
			public int	lcOutOrgZ;
			public int	lcOutExtX;
			public int	lcOutExtY;
			public int	lcOutExtZ;
			public int	lcSensX;
			public int	lcSensY;
			public int	lcSensZ;
			public boolean	lcSysMode;
			public int		lcSysOrgX;
			public int		lcSysOrgY;
			public int		lcSysExtX;
			public int		lcSysExtY;
			public int	lcSysSensX;
			public int	lcSysSensY;
			
	    }

	    // for specification see wintab.h
	    public static class PACKET extends Structure {
	    	// see wintab32.dll specification
	    	public HDC pkContext;
	    	public int pkStatus;
	    	public int pkTime;
	    	public int pkChanged; 
	    	public int pkSerialNumber;
	    	public int pkCursor;
	    	public int pkButtons;
	    	public int pkX; 
	    	public int pkY; 
	    	public int pkZ; 
	    	public int pkNormalPressure;
	    	public int pkTangentPressure;
	    	public ORIENTATION	pkOrientation;
	    	public ROTATION pkRotation; 

	    }
	    
	    public static class ORIENTATION extends Structure{
	    	public int orAzimuth;
	    	public int orAltitude;
	    	public int orTwist;
	    }
		
		public static class ROTATION extends Structure{
	    	public int	roPitch;
	    	public int roRoll;
	    	public int roYaw;
	    } 

	}



