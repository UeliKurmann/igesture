/*
 * @(#)WacomTestClass.java	1.0   Nov 14, 2007
 *
 * Author		:	
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 							Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.test;

import org.ximtec.igesture.io.keyboard.KeyboardUtils;
import org.ximtec.igesture.io.mouse.MouseUtils;
import org.ximtec.igesture.io.wacom.TabletUtils;
import org.ximtec.igesture.io.wacom.Wintab32.PACKET;




public class TestClass {
   
   public static void main(String[] args) throws InterruptedException {
      
/*
      new Thread(){
          public void run(){
              KeyboardUtils hook = new KeyboardUtils();
              hook.registerHook();
          }
      }.start();
      

      new Thread(){
          public void run(){
              MouseUtils hook = new MouseUtils();
              hook.registerHook();
          }
      }.start();
      
*/
   
  
      
      new Thread(){
          public void run(){
             TabletUtils tu = new TabletUtils();
              tu.open();
          }
      }.start();

   
  
      
      /*
         TabletUtils tu =  new TabletUtils();
         System.out.println("Start");
   
         tu.open();
         
         boolean b= true;
         int i=0;
         while (b){
             
             PACKET[] arr= new PACKET[10];
             int ret = tu.getPackets(10, arr);
             if (ret>0){
                System.out.print("time: "+arr[0].pkTime+" - x: "+arr[0].pkX+" - y: "+arr[0].pkY+" - z:"+arr[0].pkZ);
                System.out.println(" - altitude: "+arr[0].pkOrientation.orAltitude+" - pressure: "+arr[0].pkNormalPressure);
                 
             }
             Thread.sleep(100);
         }
     
         tu.close();
      */
      
      
      /*
              new Thread(){
                  public void run(){
                      Keyboard hook = new Keyboard();
                      hook.registerHook();
                  }
              }.start();
      */      
              
              //KeyboardUtils ku = new KeyboardUtils();

          /*  System.out.println("x: "+tu.isDataSupported(TabletUtils.DATA_X));
              System.out.println("y: "+tu.isDataSupported(TabletUtils.DATA_Y));
              System.out.println("z: "+tu.isDataSupported(TabletUtils.DATA_Z));
              System.out.println("array size: "+tu.isDataSupported(TabletUtils.DATA_ARRAY_SIZE));
              System.out.println("buttons: "+tu.isDataSupported(TabletUtils.DATA_BUTTONS));
              System.out.println("cursor: "+tu.isDataSupported(TabletUtils.DATA_CURSOR));
              System.out.println("orientation azimuth: "+tu.isDataSupported(TabletUtils.DATA_ORIENTATION_AZIMUTH));
              System.out.println("orientation twist: "+tu.isDataSupported(TabletUtils.DATA_ORIENTATION_TWIST));
              System.out.println("pressure: "+tu.isDataSupported(TabletUtils.DATA_PRESSURE));
              System.out.println("rotation pitch: "+tu.isDataSupported(TabletUtils.DATA_ROTATION_PITCH));
              System.out.println("rotation yaw: "+tu.isDataSupported(TabletUtils.DATA_ROTATION_YAW));
              System.out.println("orientation altitude: "+tu.isDataSupported(TabletUtils.DATA_ORIENTATION_ALTITUDE));
              System.out.println("rotation roll: "+tu.isDataSupported(TabletUtils.DATA_ROTATION_ROLL));
              System.out.println("tangent pressure: "+tu.isDataSupported(TabletUtils.DATA_TANGENT_PRESSURE));
      */
          //  for(int i=1;i<20;i++)
              //  System.out.println(i+": "+tu.isDataSupported(i));
              
              
              

              
              /*
              boolean b= true;
              int i=0;
              while (b){
              
                  
                  long[] arr= tu.getPacket();
                  System.out.println("ti");
                  if (arr != null){
                      //System.out.println(arr[0]+" - "+arr[1]+" - "+arr[2]+" - "+arr[3]);
                  
                      System.out.print("time: "+arr[0]+" - x: "+arr[1]+" - y: "+arr[2]+" - z:"+arr[3]);
                      System.out.println(" - angle: "+arr[4]+" - altitude: "+arr[5]+" - pressure: "+arr[6]);
                      
                  }
                  Thread.sleep(100);
              }
              
              */

          }


}
