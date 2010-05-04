/**
 * 
 */
package org.ximtec.igesture.io.tuio;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.util.Constant;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.Gesture3DDevice;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject;
import org.ximtec.igesture.io.tuio.tuio2D.TuioCursor;
import org.ximtec.igesture.io.tuio.tuio2D.TuioObject;
import org.ximtec.igesture.io.tuio.tuio3D.TuioCursor3D;
import org.ximtec.igesture.io.tuio.tuio3D.TuioObject3D;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

/**
 * Reader that initializes the TuioConnection and handles the events caused by the TuioConnection. There are three ways to use the TuioReader.
 * 1) Manually
 * After performing the gesture, you can get the gesture with getGesture() or getGesture3D() and do the recognition.
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
 *  4. set the modifiers/tuio messages to listen for
 *  
 * @author Björn Puype, bpuype@gmail.com
 * @see TuioConnection
 */
public class TuioReader extends AbstractGestureDevice<Note,Point> implements TuioListener, Gesture3DDevice<RecordedGesture3D, Point3D>{

	
	//*******************************************************************************************************************
	//	DO NOT USE WITH THE WORKBENCH!!!!!!!!!!!!!!!
	//*******************************************************************************************************************
	
	/** List of Notes (2D gestures) */
	private Hashtable<Long,Note> notes = new Hashtable<Long,Note>();
	/** List of RecordedGesture3D (3D gestures) */
	private Hashtable<Long,RecordedGesture3D> gestures = new Hashtable<Long,RecordedGesture3D>();
	
	/** List of AbstractTuioObject */
	private Hashtable<Long,AbstractTuioObject> objectList = new Hashtable<Long,AbstractTuioObject>();
	/** List of AbstractTuioCursor */
	private Hashtable<Long,AbstractTuioCursor> cursorList = new Hashtable<Long,AbstractTuioCursor>();
	
	/** TUIO Connection used */
	protected TuioConnection connection;
	
	/** Recogniser to recognise the gestures */
	private Recogniser recogniser;
	
	private boolean debug = true;
	
	protected GestureSample gesture;
	protected Note lastNoteAdded;
	protected GestureSample3D gesture3D;
	protected RecordedGesture3D lastRecordedGesture3DAdded;
	
	/**
	 * Default Constructor
	 * @param recogniser Recogniser to use.
	 */
	public TuioReader()
	{
		this(TuioConstants.DEFAULT_PORT);
	}
	
	/**
	 * Constructor
	 * @param port	Port to listen to.
	 */
	public TuioReader(int port)
	{
		//customize TuioProcessor with portnumber
		// 			more than one tuioserver can connect and send information
	
		connection = new TuioConnection(port);
		
		setDeviceID(String.valueOf(port));
		setConnectionType("Tuio");
		setDeviceType("2D_3D");
		setName("Tuio Service on Port "+port);
		
		//MODIFIED >
		lastNoteAdded = new Note();
		gesture = new GestureSample(this,"", lastNoteAdded);
		
		lastRecordedGesture3DAdded = new RecordedGesture3D();
		gesture3D = new GestureSample3D(this,"", lastRecordedGesture3DAdded);
		//MODIFIED <
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
			System.out.println("[TUIO]: "+message);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.AbstractGestureDevice#connect()
	 */
	@Override
	public void connect() {
		connection.connect();
		setIsConnected(connection.isConnected());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.AbstractGestureDevice#disconnect()
	 */
	@Override
	public void disconnect() {
		connection.disconnect();
		setIsConnected(connection.isConnected());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#clear()
	 */
	@Override
	public void clear() {
		lastNoteAdded = new Note();
		lastRecordedGesture3DAdded = new RecordedGesture3D();
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#dispose()
	 */
	@Override
	public void dispose() {
		removeAllListener();
		clear();
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#getChunks()
	 */
	@Override
	public List<Point> getChunks() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.Gesture3DDevice#getChunks3D()
	 */
	@Override
	public List<Point3D> getChunks3D() {
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
	 * @see org.ximtec.igesture.io.Gesture3DDevice#getGesture3D()
	 */
	@Override
	public Gesture<RecordedGesture3D> getGesture3D()
	{
		return new GestureSample3D(this,gesture3D.getName(),lastRecordedGesture3DAdded);
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
	public void addTuioMessages(Set<String> modifiers) {
		connection.addTuioListener(this, modifiers);
	}
	
	/**
	 * Stop the processor of listening for and act on messages of the desired profiles.
	 * @param	modifiers	the desired TUIO profiles
	 */
	public void removeTuioMessages(Set<String> modifiers) {
		connection.removeTuioListener(this, modifiers);
	}
	
	/**
	 * Create a AccelerationSample
	 * @param xs	X speed
	 * @param ys	Y speed
	 * @param zs	Z speed
	 * @param ms	motion speed
	 * @param macc	motion acceleration
	 * @param time	timestamp
	 * @return
	 */
	private AccelerationSample createAccelerationSample(double xs, double ys, double zs, double ms, double macc, long time)
	{
		double dt = ms/macc;
		AccelerationSample sample = new AccelerationSample(xs/dt,ys/dt,zs/dt,time);
		return sample;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#addTuioCursor(org.ximtec.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void addTuioCursor(AbstractTuioCursor atcur) {
		if(atcur instanceof TuioCursor)
		{
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
		else if(atcur instanceof TuioCursor3D)
		{
			TuioCursor3D tcur = (TuioCursor3D)atcur;
			if (!cursorList.containsKey(tcur.getSessionID())) 
			{
				cursorList.put(tcur.getSessionID(), tcur);
				
				//TODO
				//calculate timestamp
				long time = tcur.getTuioTime().add(tcur.getStartTime()).getTotalMilliseconds();
				//OR
				//long time = tcur.getTuioTime().getTotalMilliseconds();
				
				//create new recordedgesture3D and add first point to it
				RecordedGesture3D gesture = new RecordedGesture3D();
				Point3D point = new Point3D(tcur.getX(),tcur.getY(),tcur.getZ(),time);
				gesture.add(point);
				
				//create accelerations and an acceleration sample
				Accelerations accelerations = new Accelerations();
				AccelerationSample sample = createAccelerationSample(tcur.getXSpeed(),tcur.getYSpeed(),tcur.getZSpeed(),tcur.getMotionSpeed(),tcur.getMotionSpeed(),time);
				//add sample to accelerations
				accelerations.addSample(sample);
				//set the gesture accelerations
				gesture.setAccelerations(accelerations);
				//save recordedgesture3D
				gestures.put(tcur.getSessionID(), gesture);
			}	 
			debug("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getZ());
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#addTuioObject(org.ximtec.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void addTuioObject(AbstractTuioObject atobj) {
		if(atobj instanceof TuioObject)
		{
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
		else if(atobj instanceof TuioObject3D)
		{
			TuioObject3D tobj = (TuioObject3D)atobj;
			if(!objectList.containsKey(tobj.getSessionID()))
			{
				objectList.put(tobj.getSessionID(),tobj);
				
				//TODO
				//calculate timestamp
				long time = tobj.getTuioTime().add(tobj.getStartTime()).getTotalMilliseconds();
				
				//create new recordedgesture3D and add first point to it
				RecordedGesture3D gesture = new RecordedGesture3D();
				Point3D point = new Point3D(tobj.getX(),tobj.getY(),tobj.getZ(),time);
				gesture.add(point);
				//create accelerations and an acceleration sample
				Accelerations accelerations = new Accelerations();
				AccelerationSample sample = createAccelerationSample(tobj.getXSpeed(),tobj.getYSpeed(),tobj.getZSpeed(),tobj.getMotionSpeed(),tobj.getMotionAccel(),time);
				//add sample to accelerations
				accelerations.addSample(sample);
				//set the gesture accelerations
				gesture.setAccelerations(accelerations);
				//save recordedgesture3D
				gestures.put(tobj.getSessionID(), gesture);

			}
			debug("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getZ());
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#refresh(org.ximtec.tuio.TuioTime)
	 */
	@Override
	public void refresh(TuioTime ftime) {	
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#removeTuioCursor(org.ximtec.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void removeTuioCursor(AbstractTuioCursor atcur) {
		if(atcur instanceof TuioCursor)
		{
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
		else if(atcur instanceof TuioCursor3D)
		{
			TuioCursor3D tcur = (TuioCursor3D)atcur;
			cursorList.remove(tcur.getSessionID());	
			
			lastRecordedGesture3DAdded = gestures.get(tcur.getSessionID());
			fireGestureEvent(gesture3D);
			if(recogniser != null)
			{
				//process recordedGesture3D by recognizing it and notifying listeners
				recogniser.recognise(new GestureSample3D(this,Constant.EMPTY_STRING, lastRecordedGesture3DAdded),false);
						//recogniser automatically performs fireEvent which warns 
						//gestureHandlers of new performed gestures
			}
			
			//remove note from the list
			gestures.remove(tcur.getSessionID());

			debug("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#removeTuioObject(org.ximtec.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void removeTuioObject(AbstractTuioObject atobj) {
		if(atobj instanceof TuioObject)
		{
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
		else if(atobj instanceof TuioObject3D)
		{
			TuioObject3D tobj = (TuioObject3D)atobj;
			objectList.remove(tobj.getSessionID());	
			
			RecordedGesture3D record = gestures.get(tobj.getSessionID());
			//if the recorded gesture contains more than one point3D, then the object was used to perform a gesture
			//otherwise not, so no recognition is TuioCursor tcur = (TuioCursor)atcur;needed.
			if(record.size() > 1)
			{
				lastRecordedGesture3DAdded = record;
				fireGestureEvent(getGesture3D());
				if(recogniser != null)
				{
					//process recordedGesture3D by recognizing it and notifying listeners
					recogniser.recognise(new GestureSample3D(this,Constant.EMPTY_STRING, gestures.get(tobj.getSessionID())),false);
							//recogniser automatically performs fireEvent which warns 
							//gestureHandlers of new performed gestures
				}
			}
			//remove note from the list
			gestures.remove(tobj.getSessionID());
			
			debug("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
		}		
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#updateTuioCursor(org.ximtec.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void updateTuioCursor(AbstractTuioCursor atcur) {
		if(atcur instanceof TuioCursor)
		{
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
		else if(atcur instanceof TuioCursor3D)
		{
			TuioCursor3D tcur = (TuioCursor3D)atcur;
			if(gestures.containsKey(tcur.getSessionID()))
			{
				//TODO
				//calculate timestamp
				long time = tcur.getTuioTime().add(tcur.getStartTime()).getTotalMilliseconds();
				
				//add new point to the corresponding recordedgesture3D
				Point3D p = new Point3D(tcur.getX(),tcur.getY(),tcur.getZ(),time);
				RecordedGesture3D gesture = gestures.get(tcur.getSessionID());
				gesture.add(p);
				//add new acceleration sample to the recordedgesture3D's accelerations
				AccelerationSample sample = createAccelerationSample(tcur.getXSpeed(),tcur.getYSpeed(),tcur.getZSpeed(),tcur.getMotionSpeed(),tcur.getMotionAccel(),time);
				//add sample to accelerations
				Accelerations accelerations = gesture.getAccelerations(); 
				accelerations.addSample(sample);
				
				//DEBUG
				TuioCursor3D demo = (TuioCursor3D)cursorList.get(tcur.getSessionID());
				demo.update(tcur);
			}	
			debug("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.tuio.TuioListener#updateTuioObject(org.ximtec.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void updateTuioObject(AbstractTuioObject atobj) {
		if(atobj instanceof TuioObject)
		{
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
		else if(atobj instanceof TuioObject3D)
		{
			TuioObject3D tobj = (TuioObject3D)atobj;
			if(gestures.containsKey(tobj.getSessionID()))
			{
				//TODO
				//calculate timestamp
				long time = tobj.getTuioTime().add(tobj.getStartTime()).getTotalMilliseconds();
				
				//add new point to the corresponding recordedgesture3D
				Point3D p = new Point3D(tobj.getX(),tobj.getY(),tobj.getZ(),time);
				RecordedGesture3D gesture = gestures.get(tobj.getSessionID());
				gesture.add(p);
				//add new acceleration sample to the recordedgesture3D's accelerations
				AccelerationSample sample = createAccelerationSample(tobj.getXSpeed(),tobj.getYSpeed(),tobj.getZSpeed(),tobj.getMotionSpeed(),tobj.getMotionAccel(),time);
				//add sample to accelerations
				Accelerations accelerations = gesture.getAccelerations(); 
				accelerations.addSample(sample);
				
				//DEBUG
				TuioObject3D demo = (TuioObject3D)objectList.get(tobj.getSessionID());
				demo.update(tobj);
			}
			debug("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getZ());
		}
	}
	
	public TuioReaderPanel getPanel()
	{
		return getPanel(new Dimension(200,200));
	}
	
	public TuioReaderPanel getPanel(Dimension dimension)
	{
		TuioReaderPanel panel = new TuioReaderPanel();
		panel.setSize(dimension);
		panel.setPreferredSize(dimension);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		return panel;
	}
}
