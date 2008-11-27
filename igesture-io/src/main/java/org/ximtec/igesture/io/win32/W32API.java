/* Copyright (c) 2007 Timothy Wall, All Rights Reserved
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.  
 */

package org.ximtec.igesture.io.win32;

import com.sun.jna.IntegerType;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.win32.StdCallLibrary;

/** Base type for most W32 API libraries.  Provides standard options
 * for unicode/ASCII mappings.  Set the system property <code>w32.ascii</code>
 * to <code>true</code> to default to the ASCII mappings.
 */
public interface W32API extends StdCallLibrary, W32Errors {
    
    
    public static class HANDLE extends PointerType { 
        public HANDLE() { }
        public HANDLE(Pointer p) { super(p); }
    }
    
    public static class HDC extends HANDLE { }
    public static class HICON extends HANDLE { }
    public static class HBITMAP extends HANDLE { }
    public static class HRGN extends HANDLE { }
    public static class HWND extends HANDLE { }
    public static class HINSTANCE extends HANDLE { }
    public static class HMODULE extends HINSTANCE { }
    

    HANDLE INVALID_HANDLE_VALUE = new HANDLE() {//Pointer.PM1
        public void setPointer(Pointer p) { 
            throw new UnsupportedOperationException("Immutable reference");
        }
    };
    
    public static class LONG_PTR extends IntegerType { 
    	public LONG_PTR() { this(0); }
    	public LONG_PTR(long value) { super(Pointer.SIZE, value); }
    }
    public static class LPARAM extends IntegerType { //LONG_PTR
    	public LPARAM() { this(0); }
    	public LPARAM(long value) { super(Pointer.SIZE, value);}//super(value); 
    } 
    public static class WPARAM extends IntegerType { //IntegerType 
    	public WPARAM() { this(0); }
    	public WPARAM(long value) { super(Pointer.SIZE, value); } //super(Pointer.SIZE, value);
    } 
    
    public static class LRESULT extends LONG_PTR { 
    	public LRESULT() { this(0); }
    	public LRESULT(long value) { super(value); }
    } 
    
    public static class ULONG_PTR extends IntegerType {
    	public ULONG_PTR() { super(Pointer.SIZE); }
    	public ULONG_PTR(long value) { super(Pointer.SIZE, value); }
    }
}
