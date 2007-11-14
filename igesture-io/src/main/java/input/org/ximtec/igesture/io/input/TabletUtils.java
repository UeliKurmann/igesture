package org.ximtec.igesture.io.input;

import win32.Wintab32;
import win32.W32API.HDC;
import win32.W32API.HWND;
import win32.Wintab32.LOGCONTEXTW;
import win32.Wintab32.PACKET;

import com.sun.jna.ptr.IntByReference;

public class TabletUtils {

	/** Provide access to a Tablet PC.
	 * 
	 * 
	 * @author Michele Croci
	 */
	
	Wintab32 lib = Wintab32.INSTANCE;
	HDC hdc = new HDC();

	    
	    public void open() {
	    	System.out.println("TabletUnit: open()");

	    	HWND hwnd = new HWND();
	    	hwnd.setPointer(new IntByReference(Wintab32.HWND_BROADCAST).getPointer(0));
	    	int packetmode = 0;
	    	
	    	LOGCONTEXTW lg =  new LOGCONTEXTW();
	    	lib.WTInfoW(Wintab32.WTI_DEFSYSCTX, 0, lg);
	    	lg.lcPktMode= packetmode;
	    	lg.lcPktData = 0x3FFF;//information to be retrieved?
	    	// see wintab.h for values
	    	lg.lcMoveMask = 0x3FFF; //0x180
	    	lg.lcBtnUpMask = lg.lcBtnDnMask;

	    	
	        hdc=lib.WTOpenW(hwnd, lg, true);
	        if (hdc != null){
	        	System.out.println("not null");
	        }
	        System.out.println("TabletUnit: open() ok");
	    }
	    
	    public void close() {
	    	boolean ret = lib.WTClose(hdc);
	    	System.out.println("return val: "+ret);
	    }
	    
	    
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
	    
	    public long[] getPackets(int number){
	    	if (hdc == null)
	    		return null;
	    	
	    	long[] val = new long[11];
	    	PACKET p = new PACKET();
	    	int ret = lib.WTPacketsGet(hdc, number,p);
	    	
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
	    
	    public boolean isDataSupported(int dataTypeId){
	    	int test;
	    	HWND hwnd = new HWND();
	    	hwnd.setPointer(new IntByReference(Wintab32.HWND_BROADCAST).getPointer(0));
	    	
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
	    
	/*	public static final int DVC_NAME		=	1;
		public static final int DVC_HARDWARE	=	2;
		public static final int DVC_NCSRTYPES	=	3;
		public static final int DVC_FIRSTCSR	=	4;
		public static final int DVC_PKTRATE		=	5;
		public static final int DVC_PKTDATA		=	6;
		public static final int DVC_PKTMODE		=	7;
		public static final int DVC_CSRDATA		=	8;
		public static final int DVC_XMARGIN		=	9;
		public static final int DVC_YMARGIN		=	10;
		public static final int DVC_ZMARGIN		=	11;
		*/
		public static final int DVC_X			= 	12;
		public static final int DVC_Y			=	13;
		public static final int DVC_Z			=	14;
		public static final int DVC_NPRESSURE	=	15;
		public static final int DVC_TPRESSURE	=	16;
		public static final int DVC_ORIENTATION	=	17;
		public static final int DVC_ROTATION	=	18; /* 1.1 */
		/*
		public static final int DVC_PNPID		=	19; 
		public static final int DVC_MAX			=	19;
*/
	
	}

