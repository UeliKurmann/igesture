/*
 * @(#)Win32TabletProxy.java   1.0   Oct 22, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      : 	Reads the tablet position.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Oct 22, 2007     crocimi			Initial Release
 * 
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



import java.awt.Point;
import java.util.logging.Logger;

import org.ximtec.igesture.io.wacom.TabletUtils;
import org.ximtec.igesture.io.wacom.Wintab32.ORIENTATION;
import org.ximtec.igesture.io.wacom.Wintab32.PACKET;
import org.ximtec.igesture.io.wacom.Wintab32.ROTATION;





/**
 * Reads the mouse position.
 * 
 * @version 1.0, Oct 2007
 * @author Michele Croci
 *
 */
public class Win32TabletProxy {

   private static final Logger LOGGER = Logger.getLogger(Win32TabletProxy.class
         .getName());


   
   private TabletUtils tabletUtils =  new TabletUtils();
   private PACKET lastPacket = null;

   public Win32TabletProxy(){
	   tabletUtils.open();
   }
   
   public boolean isExtraDataSupported(){
	   return tabletUtils.isDataSupported(14)||tabletUtils.isDataSupported(16)||
	   tabletUtils.isDataSupported(17)||tabletUtils.isDataSupported(18);
   }
   
   /**
    * Get the next packet data from the tablet Pc
    * 
    */
   public void getNextPacket() {

		   //lastPacket = tabletUtils.getOnePacket();
		   lastPacket = tabletUtils.getPacket();
   } 
   
   /**
    * Return the last readed packet
    * 
    */
   public PACKET getLastPacket() {

           return lastPacket;
   } 
   
   /**
    * Returns the pressure of the pen on the table 
    * 
    * @return the timestamp
    */
   public long getTimeStamp() {
	   if (lastPacket!=null) //just in case
		    return lastPacket.pkTime;
	   else
		   return 0;
   } 
   


   /**
    * Returns the pressure of the pen on the table 
    * 
    * @return the pressure (min =0, max = 1023)
    */
   public int getPressure() {
	   if (lastPacket!=null)
	        return lastPacket.pkNormalPressure;
	   else
		   return -1;
   } 
   
   /**
    * Returns the tangent pressure of the pen on the table 
    * 
    * @return the tangent pressure 
    */
   public int getTangentPressure() {
	   if (lastPacket!=null)
	      return lastPacket.pkTangentPressure;
	   else
		   return -1;
   } 
   
   /**
    * Returns the z-value of the cursor position
    * 
    * @return z-value
    */
   public int getzval() {
	   if (lastPacket!=null)
	         return lastPacket.pkZ;
	   else
		   return -1;
   } 
   
   
   
   /**
    * Returns the orientation of the pen on the table 
    * 
    * @return orientation (altitude, azimuth, twist)
    */
   public ORIENTATION getOrientation() {
	   if (lastPacket!=null)
	      return lastPacket.pkOrientation;
	    else
	         return null;
   } 
   
   
   /**
    * Returns the rotation of the pen on the table 
    * 
    * @return rotation (pitch, roll, yaw)
    */
   public ROTATION getRotation() {
	   if (lastPacket!=null){ 
		      return lastPacket.pkRotation;
		   }else
			   return null;
   } 
   
   
   /**
    * Returns the location of the tablet cursor on the screen.
    * 
    * @return the location of the tablet cursor on the screen.
    */
   public Point getLastCursorLocation() {
	   if (lastPacket!=null) //just in case
		   return new Point(lastPacket.pkX, lastPacket.pkY);
	   else
		   return null;
   } 
   
   
   public int buttonPressed(){
      return lastPacket.pkButtons;
   }
   
   
   //public static int LEFT = 0X01;

   //public static int RIGHT = 0X02;

   //public static int MIDDLE = 0X04;

}
