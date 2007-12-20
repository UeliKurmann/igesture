package org.ximtec.igesture.io.win32;




import com.sun.jna.Native;
import com.sun.jna.Structure;




public interface User32 extends W32API{//StdCallLibrary {
	User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, DEFAULT_OPTIONS);
	
	LRESULT CallNextHookEx(
		    HANDLE hhk,
		    int nCode,
		    WPARAM wParam,
		    LPARAM lParam
		);
	
	
	
	int GetAsyncKeyState(int input);
	int GetCursorPos(POINT p);
	int GetCurrentThreadId();


	
	HANDLE SetWindowsHookExW(
		    int idHook,
		    StdCallCallback lpfn,
		    HINSTANCE hMod,
		    long dwThreadId //int
		);

	
	boolean UnhookWindowsHookExW(      
			HANDLE hhk
		);
	int WH_KEYBOARD_LL = 13;
	int WH_KEYBOARD = 2;
	int WH_MOUSE = 7;
	int WH_MOUSE_LL = 14;
	
	int GetMessageW( //boolean?      
		    MSG lpMsg,
		    HWND hWnd,
		    int wMsgFilterMin,
		    int wMsgFilterMax
		);
	
	int PeekMessage(      
		    MSG lpMsg,
		    HWND hWnd,
		    int wMsgFilterMin,
		    int wMsgFilterMax,
		    int wRemoveMsg
		);
	
	
	public static class MSG extends Structure {
	    public HWND hWnd;
	    public int message;
	    public int wParam; //WPARAM?
	    public int lParam; //LPARAM?
	    public int time; //long?
	    public POINT pt;
	    
	}
	
	
	public static class POINT extends Structure {
		public int x;
		public int y;
	}
	
	public LRESULT DispatchMessage(MSG msg);
	
	public boolean TranslateMessage(MSG msg);
	
	//C specification:
	
	/*
	 * 
	 BOOL TranslateMessage(lpMsg);
	 * 
	 BOOL PeekMessage(      
	    LPMSG lpMsg,
	    HWND hWnd,
	    UINT wMsgFilterMin,
	    UINT wMsgFilterMax,
	    UINT wRemoveMsg
	);
	 * 
	 * 
	 BOOL GetMessage(      
	    LPMSG lpMsg,
	    HWND hWnd,
	    UINT wMsgFilterMin,
	    UINT wMsgFilterMax
	);
	
	HHOOK SetWindowsHookEx(      
	    int idHook,
	    HOOKPROC lpfn,
	    HINSTANCE hMod,
	    DWORD dwThreadId
	);

	LRESULT CallNextHookEx(      
	    HHOOK hhk,
	    int nCode,
	    WPARAM wParam,
	    LPARAM lParam
	);

	LRESULT DispatchMessageW(
		const MSG *lpmsg
	);

	typedef struct {
		HWND hwnd;
		UINT message;
		WPARAM wParam;
		LPARAM lParam;
		DWORD time;
		POINT pt;
		} MSG;
    pt Ws POINTAPI

	typedef struct {
		int x;
		int y;
	} POINT;

	 */
}