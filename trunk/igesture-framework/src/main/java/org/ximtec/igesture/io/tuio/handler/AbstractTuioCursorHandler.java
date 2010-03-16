package org.ximtec.igesture.io.tuio.handler;

import java.util.Hashtable;
import java.util.Vector;

public abstract class AbstractTuioCursorHandler<T> extends AbstractTuioHandler {

	protected Hashtable<Long,T> cursorList = new Hashtable<Long,T>();
	protected Vector<Long> aliveCursorList = new Vector<Long>();
	protected Vector<Long> newCursorList = new Vector<Long>();
	protected Vector<T> frameCursors = new Vector<T>();

	protected Vector<T> freeCursorList = new Vector<T>();
	protected int maxCursorID = -1;
}
