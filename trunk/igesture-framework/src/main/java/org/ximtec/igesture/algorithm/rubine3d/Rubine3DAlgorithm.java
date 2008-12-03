package org.ximtec.igesture.algorithm.rubine3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.util.Point3D;
import org.ximtec.igesture.util.RecordedGesture3D;



public class Rubine3DAlgorithm implements Algorithm {

	@Override
	public Enum<?>[] getConfigParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultParameterValue(String parameterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Configuration configuration) throws AlgorithmException {
		// TODO Auto-generated method stub
		
	}

	
	/** Recognizes a Gesture<?> (or not..)
	 * 
	 */
	public ResultSet recognise(Gesture<?> gesture) throws AlgorithmException {
		if (gesture instanceof GestureSample3D){
			return this.recognise((GestureSample3D)gesture);
		}
		else {
			throw new AlgorithmException(ExceptionType.Recognition); //Is this correct?
		}
	}
	
	/** Recognizes a GestureSample3D and returns a Resultset
	 * 
	 */
	public ResultSet recognise(GestureSample3D gesture) throws AlgorithmException {
		List<Gesture<Note>> planes = splitToPlanes(gesture);		//Split gesture into planes

		//TODO What to do with the configuration??
		
		Recogniser recogniser = new Recogniser(new Configuration());//New Recogniser
		
		Iterator<Gesture<Note>> iterator = planes.iterator();		//Iterator on planes
		List<ResultSet> sets = new ArrayList<ResultSet>();			//Create list for the 3 ResultSets
		//Run the rubine algorithm 3 times for the planes, add resultsets to sets.
		while(iterator.hasNext()){
			sets.add(recogniser.recognise(iterator.next()));		//Recognise current plane
		}		
		//Combine sets to one ResultSet and return
		return combineResultSets(sets);								
	}
	
	
	/** Splits RecordedGesture3D gesture into three 2D planes.
	 * The X-Plane is defined as the plane in 3D space where x=0.
	 * The Y-Plane is defined as the plane in 3D space where y=0.
	 * The Z-Plane is defined as the plane in 3D space where z=0.
	 * 
	 * @param gesture
	 * @return
	 */
	private List<Gesture<Note>> splitToPlanes(GestureSample3D inputGesture){
		Iterator<Point3D> iterator = inputGesture.getGesture().iterator();	//Iterator on the list of Point3D in gesture
		Trace traceX = new Trace();											//X plane Trace
		Trace traceY = new Trace();											//Y plane Trace
		Trace traceZ = new Trace();											//Z plane Trace
		Point3D point3d;													//Working variable
		//Project all 3d points in gesture on planes
		while(iterator.hasNext()){
			point3d = (Point3D)iterator.next();
			//Add points to 2d traces
			traceX.add(new Point(point3d.getX(),point3d.getZ(),point3d.getTimeStamp()));
			traceY.add(new Point(point3d.getY(),point3d.getZ(),point3d.getTimeStamp()));
			traceZ.add(new Point(point3d.getX(),point3d.getY(),point3d.getTimeStamp()));			
		}
		//Put traces into Notes
		Note noteX = new Note();											//X plane Note
		Note noteY = new Note();											//Y plane Note
		Note noteZ = new Note();											//Z plane Note
		noteX.add(traceX);
		noteY.add(traceY);
		noteZ.add(traceZ);
		//Add planes to returnlist
		List<Gesture<Note>> returnList = new ArrayList<Gesture<Note>>();	//Return variable
		returnList.add(new GestureSample("X-plane", noteX));
		returnList.add(new GestureSample("Y-plane", noteY));
		returnList.add(new GestureSample("Z-plane", noteZ));
		//Return list with three planes
		return returnList;													 
	}
	
	
	/** Combines resultsets from the three 2D planes into one final resultset for the 3D gesture
	 * 
	 * @param sets
	 * @return
	 */
	private ResultSet combineResultSets(List<ResultSet> sets){
		
		//TODO Combine resultsets.
		
		return null;
	}

}
