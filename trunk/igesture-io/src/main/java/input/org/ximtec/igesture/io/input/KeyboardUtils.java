package input;

import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import win32.User32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
//import com.sun.jna.examples.unix.X11;
//import com.sun.jna.examples.unix.X11.Display;
//import com.sun.jna.examples.win32.User32;

/** Provide access to the local keyboard state.  Note that this is meaningless
 * on a headless system and some VNC setups.
 * 
 * @author twall
 */


public class KeyboardUtils {
    static final NativeKeyboardUtils INSTANCE; 
    static {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("KeyboardUtils requires a keyboard");
        }
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            INSTANCE = new W32KeyboardUtils();
        }
        else if (os.startsWith("Mac")) {
        	INSTANCE=null;
            //INSTANCE = new MacKeyboardUtils();
            //throw new UnsupportedOperationException("No support (yet) for " + os);
        }
        else {
        	INSTANCE=null;
            //INSTANCE = new X11KeyboardUtils();
        }
    }
    
    public static boolean isPressed(int keycode, int location) {
        return INSTANCE.isPressed(keycode, location);
    }
    public static boolean isPressed(int keycode) {
        return INSTANCE.isPressed(keycode);
    }
    
    private static abstract class NativeKeyboardUtils {
        public abstract boolean isPressed(int keycode, int location);
        public boolean isPressed(int keycode) {
            return isPressed(keycode, KeyEvent.KEY_LOCATION_UNKNOWN);
        }
    }
    
    private static class W32KeyboardUtils extends NativeKeyboardUtils {
        private int toNative(int code, int loc) {
            if ((code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z)
                || (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9)) {
                return code;
            }
            if (code == KeyEvent.VK_SHIFT) {
                if ((loc & KeyEvent.KEY_LOCATION_RIGHT) != 0) {
                    return User32.VK_RSHIFT; 
                }
                if ((loc & KeyEvent.KEY_LOCATION_LEFT) != 0) {
                    return User32.VK_LSHIFT; 
                }
                return User32.VK_SHIFT;
            }
            if (code == KeyEvent.VK_CONTROL) {
                if ((loc & KeyEvent.KEY_LOCATION_RIGHT) != 0) {
                    return User32.VK_RCONTROL; 
                }
                if ((loc & KeyEvent.KEY_LOCATION_LEFT) != 0) {
                    return User32.VK_LCONTROL; 
                }
                return User32.VK_CONTROL;
            }
            if (code == KeyEvent.VK_ALT) {
                if ((loc & KeyEvent.KEY_LOCATION_RIGHT) != 0) {
                    return User32.VK_RMENU; 
                }
                if ((loc & KeyEvent.KEY_LOCATION_LEFT) != 0) {
                    return User32.VK_LMENU; 
                }
                return User32.VK_MENU;
            }
            return 0;
        }
        public boolean isPressed(int keycode, int location) {
            User32 lib = User32.INSTANCE;
            return (lib.GetAsyncKeyState(toNative(keycode, location)) & 0x8000) != 0;
        }
    }
}
