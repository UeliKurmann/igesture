/**
 * 
 */
package org.ximtec.igesture.io.tuio;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject;
import org.ximtec.igesture.io.tuio.tuio2D.TuioCursor;
import org.ximtec.igesture.io.tuio.tuio2D.TuioObject;
import org.ximtec.igesture.util.Constant;

/**
 * Reader that initializes the TuioConnection and handles the events caused by the TuioConnection. There are three ways to use the TuioReader.
 * 1) Manually
 * After performing the gesture, you can get the gesture with getGesture() and do the recognition.
 * 2) GestureEventListener interface
 * Register with the TuioReader as a GestureEventListener, it will notify you when a gesture was performed. The recognition of the performed
 * gesture can then be done.
 * 3) Configure the TuioReader with a Recogniser
 * When a gesture was performed, the recogniser will automatically try to recognise the gesture and notify GestureHandlers.
 *  
 * General steps to use:
 * 	1. create the TuioReader
 * 	2. connect to a tuio service
 *  3. In case of GestureEventListener : register as a listener
 *     In case of Recogniser : set this TuioReaders recogniser
 *  
 * @author Bjorn Puype, bpuype@gmail.com
 * @see TuioConnection
 */
public class TuioReader2D extends AbstractGestureDevice<Note, Point> implements ITuioReader {

	/** List of Notes (2D gestures) */
	private Hashtable<Long,Note> notes = new Hashtable<Long,Note>();
	
	/** List of AbstractTuioObject */
	private Hashtable<Long,AbstractTuioObject> objectList = new Hashtable<Long,AbstractTuioObject>();
	/** List of AbstractTuioCursor */
	private Hashtable<Long,AbstractTuioCursor> cursorList = new Hashtable<Long,AbstractTuioCursor>();
	
	/** TUIO Connection used */
	private TuioConnection connection;
	
	/** Recogniser to recognise the gestures */
	private Recogniser recogniser;
	
	private boolean debug = false;
	
	private GestureSample gesture;
	private Note lastNoteAdded;
	
	/**
	 * Default Constructor
	 */
	public TuioReader2D()
	{
		this(new Integer(TuioConstants.DEFAULT_PORT));
	}
	
	/**
	 * Constructor
	 * @param port	Port to listen to.
	 */
	public TuioReader2D(Integer port)
	{
		//customize TuioProcessor with portnumber
		// 			more than one tuioserver can connect and send information
	
		connection = new TuioConnection(port.intValue());
		
		setDeviceID(String.valueOf(port));
		setConnectionType(Constant.CONNECTION_TUIO);
		setDeviceType(Constant.TYPE_2D);
		setName("TUIO Service on Port "+port);
		
		lastNoteAdded = new Note();
		gesture = new GestureSample(this,"", lastNoteAdded);
	}
	
	/**
	 * Set the recogniser to use.
	 * @param recogniser	Recogniser to use.
	 */
	public void setRecogniser(Recogniser recogniser)
	{
		this.recogniser = recogniser;
	}
	
	private void debug(String message)
	{
		if(debug)
			System.out.println("["+getName()+"]: "+message);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.AbstractGestureDevice#connect()
	 */
	@Override
	public void connect() {
		connection.connect();
		setIsConnected(connection.isConnected());
		
		Set<String> list = new HashSet<String>();
		list.add(TuioConstants.TUIO_CURSOR);
		list.add(TuioConstants.TUIO_OBJECT);
		addTuioMessages(list);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.AbstractGestureDevice#disconnect()
	 */
	@Override
	public void disconnect() {
		//TODO remove tuio messages?
		connection.disconnect();
		setIsConnected(connection.isConnected());		
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#clear()
	 */
	@Override
	public void clear() {
		lastNoteAdded = new Note();
		gesture = new GestureSample(this,"",lastNoteAdded);
		fireGestureEvent(gesture);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#dispose()
	 */
	@Override
	public void dispose() {
		removeAllListener();
		lastNoteAdded = new Note();
		gesture = new GestureSample(this,"",lastNoteAdded);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#getChunks()
	 */
	@Override
	public List<Point> getChunks() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#getGesture()
	 */
	@Override
	public Gesture<Note> getGesture() {
		return new GestureSample(this,gesture.getName(),lastNoteAdded);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#init()
	 */
	@Override
	public void init() {
	}
	
	/**
	 * Set-up the processor to listen for and act on messages of the desired profiles.
	 * @param	modifiers	the desired TUIO profiles	
	 */
	private void addTuioMessages(Set<String> modifiers) {
		connection.addTuioListener(this, modifiers);
	}
	
	/**
	 * Stop the processor of listening for and act on messages of the desired profiles.
	 * @param	modifiers	the desired TUIO profiles
	 */
	private void removeTuioMessages(Set<String> modifiers) {
		connection.removeTuioListener(this, modifiers);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#addTuioCursor(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void addTuioCursor(AbstractTuioCursor atcur) {
		TuioCursor tcur = (TuioCursor)atcur;
		if (!cursorList.containsKey(tcur.getSessionID())) {
			cursorList.put(tcur.getSessionID(), tcur);
			
			//TODO
			//calculate timestamp
			long time = tcur.getTuioTime().add(tcur.getStartTime()).getTotalMilliseconds();
			
			//create new note and add first point to it
			Note note = new Note();
			Trace trace = new Trace();
			Point point = new Point(tcur.getX(),tcur.getY(),time);
			trace.add(point);
			note.add(trace);
			//save note
			notes.put(tcur.getSessionID(), note);
		}	 
		debug("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY());		
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#addTuioObject(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void addTuioObject(AbstractTuioObject atobj) {
		TuioObject tobj = (TuioObject)atobj;
		if(!objectList.containsKey(tobj.getSessionID()))
		{
			objectList.put(tobj.getSessionID(),tobj);
			
			//TODO
			//calculate timestamp
			long time = tobj.getTuioTime().add(tobj.getStartTime()).getTotalMilliseconds();
			
			//create new note and add first point to it
			Note note = new Note();
			Trace trace = new Trace();
			Point point = new Point(tobj.getX(),tobj.getY(),time);
			trace.add(point);
			note.add(trace);
			//save note
			notes.put(tobj.getSessionID(), note);
		}
		debug("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#refresh(org.ximtec.igesture.io.tuio.TuioTime)
	 */
	@Override
	public void refresh(TuioTime ftime) {		
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#removeTuioCursor(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void removeTuioCursor(AbstractTuioCursor atcur) {
		TuioCursor tcur = (TuioCursor)atcur;
		cursorList.remove(tcur.getSessionID());	
		
		lastNoteAdded = notes.get(tcur.getSessionID());
		fireGestureEvent(getGesture());
		if(recogniser != null)
		{	
			//process note by recognizing it and notifying listeners		
			recogniser.recognise(lastNoteAdded);
					//recogniser automatically performs fireEvent which warns 
					//gestureHandlers of new performed gestures
		}
		
		//remove note from the list
		notes.remove(tcur.getSessionID());
		
		debug("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#removeTuioObject(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void removeTuioObject(AbstractTuioObject atobj) {
		TuioObject tobj = (TuioObject)atobj;
		objectList.remove(tobj.getSessionID());
		
		Note note = notes.get(tobj.getSessionID());
		Trace trace = note.get(0);
		
		//if the trace contains more than one point, then the object was used to perform a gesture
		//otherwise not, so no recognition is needed
		if(trace.size() > 1)
		{
			lastNoteAdded = note;
			fireGestureEvent(getGesture());
			if(recogniser != null)
			{
				//process note by recognizing it and notifying listeners		
				recogniser.recognise(notes.get(tobj.getSessionID()));
						//recogniser automatically performs fireEvent which warns 
						//gestureHandlers of new performed gestures
			}
		}
		//remove note from the list
		notes.remove(tobj.getSessionID());
		
		debug("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#updateTuioCursor(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void updateTuioCursor(AbstractTuioCursor atcur) {
		TuioCursor tcur = (TuioCursor)atcur;
		if(notes.containsKey(tcur.getSessionID()))
		{
			//TODO
			//calculate timestamp
			long time = tcur.getTuioTime().add(tcur.getStartTime()).getTotalMilliseconds();
			
			//add new point to the corresponding note
			Point p = new Point(tcur.getX(),tcur.getY(),time);
			Note note = notes.get(tcur.getSessionID());
			note.get(0).add(p);
			
			//DEBUG
			TuioCursor demo = (TuioCursor)cursorList.get(tcur.getSessionID());
			demo.update(tcur);
		}
		debug("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#updateTuioObject(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void updateTuioObject(AbstractTuioObject atobj) {
		TuioObject tobj = (TuioObject)atobj;
		if(notes.containsKey(tobj.getSessionID()))
		{
			//TODO
			//calculate timestamp
			long time = tobj.getTuioTime().add(tobj.getStartTime()).getTotalMilliseconds();
			
			//add new point to the corresponding note
			Point p = new Point(tobj.getX(),tobj.getY(),time);
			Note note = notes.get(tobj.getSessionID());
			note.get(0).add(p);
			
			//DEBUG
			TuioObject demo = (TuioObject)objectList.get(tobj.getSessionID());
			demo.update(tobj);
		}
		debug("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()+" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
	}
	
	@Override
	public TuioReaderPanel getPanel()
	{
		return getPanel(new Dimension(200,200));
	}
	
	@Override
	public TuioReaderPanel getPanel(Dimension dimension)
	{
		TuioReaderPanel panel = new TuioReaderPanel(this,TuioReaderPanel.TYPE_2D);
		panel.setSize(dimension);
		panel.setPreferredSize(dimension);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		return panel;
	}
}
