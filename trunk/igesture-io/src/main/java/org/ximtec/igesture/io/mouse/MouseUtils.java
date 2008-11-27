/*
 * @(#)$Id$
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

import java.util.EnumSet;

import org.ximtec.igesture.io.win32.User32;
import org.ximtec.igesture.io.win32.Win32;
import org.ximtec.igesture.io.win32.User32.MSG;
import org.ximtec.igesture.io.win32.User32.POINT;
import org.ximtec.igesture.io.win32.W32API.HANDLE;
import org.ximtec.igesture.io.win32.W32API.HINSTANCE;
import org.ximtec.igesture.io.win32.W32API.LPARAM;
import org.ximtec.igesture.io.win32.W32API.LRESULT;
import org.ximtec.igesture.io.win32.W32API.WPARAM;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


/**
 * 
 * @author Michele Croci, mcroci@gmail.com
 * @author Ueli Kurmann, ueli.kurmann@bbv.ch
 * @version 1.0
 */
public class MouseUtils implements Runnable {

   private static User32 USER32 = Win32.USER32_INSTANCE;
   
   private HINSTANCE hinst;
   private MouseProc mouseListener;
   private HANDLE handle;

   private final MouseEventListener mouseCallback;

   public enum MouseButton {
      LEFT(0x01), RIGHT(0X02), MIDDLE(0X04);

      private int buttonId;


      private MouseButton(int i) {
         this.buttonId = i;
      }


      public int getButtonId() {
         return buttonId;
      }
   }


   public MouseUtils(MouseEventListener mouseCallback) {
      this.mouseCallback = mouseCallback;     
   }


   /**
    * Initialization
    */
   public void init() {
      hinst = Win32.KERNEL32_INSTANCE.GetModuleHandle(null);
      mouseListener = new MouseProc() {

         public LRESULT callback(int code, WPARAM wParam, LPARAM lParam) {

            POINT p = new POINT();
            USER32.GetCursorPos(p);

            EnumSet<MouseButton> buttons = EnumSet.noneOf(MouseButton.class);
            for (MouseButton button : MouseButton.values()) {
               if (USER32.GetAsyncKeyState(button.buttonId)) {
                  buttons.add(button);
               }
            }
            
            mouseCallback.mouseMoved(p.x, p.y);
            mouseCallback.mouseButtonPressed(buttons);
            mouseCallback.mouseEvent(p.x, p.y, buttons);

            return USER32.CallNextHookEx(handle, code, wParam, lParam);
         }
      };
      handle = USER32.SetWindowsHookExW(User32.WH_MOUSE_LL, mouseListener,
            hinst, 0);
   }


  
   /**
    * Finalize thread
    */
   public interface MouseProc extends StdCallCallback {

      LRESULT callback(int code, WPARAM wParam, LPARAM lParam);
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run() {
      init();
      registerHook();
      MSG msg = new MSG();
      while ((USER32.GetMessageW(msg, null, 0, 0) != 0)) {
         
      }
   }

   /**
    * Register callback function
    */
   public void registerHook() {
      handle = USER32.SetWindowsHookExW(User32.WH_KEYBOARD_LL, mouseListener,
            hinst, 0);
   }


   /**
    * Unregister callback function
    */
   public void unregisterHook() {
      USER32.UnhookWindowsHookExW(handle);
   }
   
   /**
    * Finalize thread
    */
   protected void finalize() throws Throwable {
      unregisterHook();
      super.finalize();
   }


}
