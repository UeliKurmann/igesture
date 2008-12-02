/*
 * @(#)$Id: Point3D.java 2008-12-02 arthurvogels $
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	A timestamped point in 3D space. A RecordedGesture3D
 * 					contains a list of these.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         	Reason
 *
 * Dec 02, 2008     arthurvogels    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util;

import javax.vecmath.*;


public class Point3D extends Point3d {

   private long timeStamp = 0;


   /**
    * Constructor
    * 
    */
   public Point3D() {
      super();
   }


   /**
    * Constructor
    * 
    * @param x
    * @param y
    * @param z
    * @param timeStamp
    */
   public Point3D(double x, double y, double z, long timeStamp) {
      super(x, y, z);
      this.timeStamp = timeStamp;
   }


   /**
    * returns the time stamp for this point
    * 
    * @return
    */
   public long getTimeStamp() {
      return timeStamp;
   }


   /**
    * sets the timestamp for this point
    * 
    * @param timeStamp
    */
   public void setTimeStamp(long timeStamp) {
      this.timeStamp = timeStamp;
   }


   /**
    * Returns true if the point has a timestamp, otherwise false
    * 
    * @return
    */
   public boolean hasTimeStamp() {
      return (timeStamp != 0);
   }

}
