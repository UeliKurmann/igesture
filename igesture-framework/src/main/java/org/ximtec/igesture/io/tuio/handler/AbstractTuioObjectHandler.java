package org.ximtec.igesture.io.tuio.handler;

import java.util.Hashtable;
import java.util.Vector;

public abstract class AbstractTuioObjectHandler<T> extends AbstractTuioHandler{

	protected Hashtable<Long,T> objectList = new Hashtable<Long,T>();
	protected Vector<Long> aliveObjectList = new Vector<Long>();
	protected Vector<Long> newObjectList = new Vector<Long>();

	protected Vector<T> frameObjects = new Vector<T>();
}
