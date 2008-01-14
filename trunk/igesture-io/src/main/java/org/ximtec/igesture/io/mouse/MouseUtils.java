/*
 * @(#)MouseUtils.java 1.0   Nov 14, 2007
 *
 * Author       :   Croci Michele, mcroci@gmail.com
 *
 * Purpose      :  Provide access to API for managing a mouse
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


package org.ximtec.igesture.io.mouse;

import org.ximtec.igesture.io.keyboard.KeyboardUtils.Kernel32;
import org.ximtec.igesture.io.win32.User32;
import org.ximtec.igesture.io.win32.User32.MSG;
import org.ximtec.igesture.io.win32.User32.POINT;
import org.ximtec.igesture.io.win32.W32API.HANDLE;
import org.ximtec.igesture.io.win32.W32API.HINSTANCE;
import org.ximtec.igesture.io.win32.W32API.LPARAM;
import org.ximtec.igesture.io.win32.W32API.LRESULT;
import org.ximtec.igesture.io.win32.W32API.WPARAM;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


public class MouseUtils {

   HINSTANCE hinst;
   MouseProc msListener;
   HANDLE handle;


   public MouseUtils() {

      hinst = Kernel32.INSTANCE.GetModuleHandle(null);
      msListener = new MouseProc() {

         public LRESULT callback(int code, WPARAM wParam, LPARAM lParam) {
            //System.out.println("mouse callback code: " + code + " wParam "
            //      + wParam + " lParam " + lParam);
            // getCoordinates!!
             POINT p = new POINT();
             User32.INSTANCE.GetCursorPos(p);
             System.out.println("x: "+p.x+" - y: "+p.y);

            return User32.INSTANCE.CallNextHookEx(handle, code, wParam, lParam);
         }
      };
     // handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_MOUSE_LL, msListener,
       //     hinst, Kernel32.INSTANCE.GetCurrentThreadId());
      
       handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_MOUSE_LL, msListener, hinst,0);

   }


   protected void finalize() throws Throwable {
      User32.INSTANCE.UnhookWindowsHookExW(handle);
      super.finalize();
   }


   public static void main(String[] args) {
      MouseUtils instance = new MouseUtils();
      try {
         MSG msg = new MSG();
         while (User32.INSTANCE.GetMessageW(msg, null, 0, 0) != 0) {
            // translate message
            // User32.INSTANCE.DispatchMessageW(msg);
         }
         // TODO: unregister hook!
      }
      catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public interface MouseProc extends StdCallCallback {

      LRESULT callback(int code, WPARAM wParam, LPARAM lParam);
   }
   
   
   public void registerHook(){
      handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD_LL, msListener, hinst, 0);
      //handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD_LL, msListener,  hinst, Kernel32.INSTANCE.GetCurrentThreadId());
      //Kernel32.INSTANCE.GetCurrentThreadId()
      //System.out.println("error: "+Kernel32.INSTANCE.GetLastError());
      start_loop();
  }
  
   void start_loop(){
          MSG msg = new MSG();
          while(User32.INSTANCE.GetMessageW(msg,null,0,0)!=0){
              User32.INSTANCE.DispatchMessage(msg);
              //translateMessage
              msg = new MSG();
          }
  }

}
