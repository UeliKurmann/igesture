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

import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor;
import org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject;
import org.ximtec.igesture.io.tuio.tuio3D.TuioCursor3D;
import org.ximtec.igesture.io.tuio.tuio3D.TuioObject3D;
import org.ximtec.igesture.util.Constant;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

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
 * @author Björn Puypepuype@gmail.com
 * @see TuioConnection
 */
public class TuioReader3D extends AbstractGestureDevice<RecordedGesture3D, Point3D> implements ITuioReader{
	
	/** List of RecordedGesture3D (3D gestures) */
	private Hashtable<Long,RecordedGesture3D> gestures = new Hashtable<Long,RecordedGesture3D>();
	
	/** List of AbstractTuioObject */
	private Hashtable<Long,AbstractTuioObject> objectList = new Hashtable<Long,AbstractTuioObject>();
	/** List of AbstractTuioCursor */
	private Hashtable<Long,AbstractTuioCursor> cursorList = new Hashtable<Long,AbstractTuioCursor>();
	
	/** TUIO Connection used */
	private TuioConnection connection;
	
	/** Recogniser to recognise the gestures */
	private Recogniser recogniser;
	
	private boolean debug = false;

	private GestureSample3D gesture3D;
	private RecordedGesture3D lastRecordedGesture3DAdded;
	
	/**
	 * Default Constructor
	 */
	public TuioReader3D()
	{
		this(new Integer(TuioConstants.DEFAULT_PORT));
	}
	
	/**
	 * Constructor
	 * @param port	Port to listen to.
	 */
	public TuioReader3D(Integer port)
	{
		//customize TuioProcessor with portnumber
		// 			more than one tuioserver can connect and send information
	
		connection = new TuioConnection(port.intValue());
		
		setDeviceID(String.valueOf(port));
		setConnectionType("Tuio");
		setDeviceType(Constant.TYPE_3D);
		setName("Tuio Service on Port "+port);
		
		lastRecordedGesture3DAdded = new RecordedGesture3D();
		gesture3D = new GestureSample3D(this,"", lastRecordedGesture3DAdded);
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
		
		Set<String> list = new HashSet<String>();
		list.add(TuioConstants.TUIO_CURSOR_3D);
		list.add(TuioConstants.TUIO_OBJECT_3D);
		addTuioMessages(list);
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
		lastRecordedGesture3DAdded = new RecordedGesture3D();
		gesture3D = new GestureSample3D(this,"",lastRecordedGesture3DAdded);
		fireGestureEvent(gesture3D);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#dispose()
	 */
	@Override
	public void dispose() {
		removeAllListener();
		lastRecordedGesture3DAdded = new RecordedGesture3D();
		gesture3D = new GestureSample3D(this,"",lastRecordedGesture3DAdded);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#getChunks()
	 */
	@Override
	public List<Point3D> getChunks() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureDevice#getGesture()
	 */
	@Override
	public Gesture<RecordedGesture3D> getGesture() {
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
	 * @see org.ximtec.igesture.io.tuio.TuioListener#addTuioCursor(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void addTuioCursor(AbstractTuioCursor atcur) {
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

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#addTuioObject(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void addTuioObject(AbstractTuioObject atobj) {
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
		TuioCursor3D tcur = (TuioCursor3D)atcur;
		cursorList.remove(tcur.getSessionID());	
		
		lastRecordedGesture3DAdded = gestures.get(tcur.getSessionID());
		fireGestureEvent(gesture3D);
		if(recogniser != null)
		{
			//process recordedGesture3D by recognizing it and notifying listeners
			recogniser.recognise(new GestureSample3D(this,org.sigtec.util.Constant.EMPTY_STRING, lastRecordedGesture3DAdded),false);
					//recogniser automatically performs fireEvent which warns 
					//gestureHandlers of new performed gestures
		}
		
		//remove note from the list
		gestures.remove(tcur.getSessionID());

		debug("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#removeTuioObject(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void removeTuioObject(AbstractTuioObject atobj) {
		TuioObject3D tobj = (TuioObject3D)atobj;
		objectList.remove(tobj.getSessionID());	
		
		RecordedGesture3D record = gestures.get(tobj.getSessionID());
		//if the recorded gesture contains more than one point3D, then the object was used to perform a gesture
		//otherwise not, so no recognition is TuioCursor tcur = (TuioCursor)atcur;needed.
		if(record.size() > 1)
		{
			lastRecordedGesture3DAdded = record;
			fireGestureEvent(getGesture());
			if(recogniser != null)
			{
				//process recordedGesture3D by recognizing it and notifying listeners
				recogniser.recognise(new GestureSample3D(this,org.sigtec.util.Constant.EMPTY_STRING, gestures.get(tobj.getSessionID())),false);
						//recogniser automatically performs fireEvent which warns 
						//gestureHandlers of new performed gestures
			}
		}
		//remove note from the list
		gestures.remove(tobj.getSessionID());
		
		debug("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#updateTuioCursor(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioCursor)
	 */
	@Override
	public void updateTuioCursor(AbstractTuioCursor atcur) {
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

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.tuio.TuioListener#updateTuioObject(org.ximtec.igesture.io.tuio.interfaces.AbstractTuioObject)
	 */
	@Override
	public void updateTuioObject(AbstractTuioObject atobj) {
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
	
	@Override
	public TuioReaderPanel getPanel()
	{
		return getPanel(new Dimension(200,200));
	}
	
	@Override
	public TuioReaderPanel getPanel(Dimension dimension)
	{
		TuioReaderPanel panel = new TuioReaderPanel(this,TuioReaderPanel.TYPE_3D);
		panel.setSize(dimension);
		panel.setPreferredSize(dimension);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		return panel;
	}

}
