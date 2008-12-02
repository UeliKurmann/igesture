package org.ximtec.igesture.util;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.ResultSet;



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
		List<ResultSet> sets = new ArrayList<ResultSet>();			//Create list for 3 ResultSets
		
		//Here the rubine algorithm 3 times for the planes, add resultsets to sets.
		
		return combineResultSets(sets);								//Combine to one ResultSet and return
	}
	
	/** Splits RecordedGesture3D gesture into three 2D planes.
	 * 
	 * @param gesture
	 * @return
	 */
	private List<Gesture<Note>> splitToPlanes(GestureSample3D inputGesture){
		RecordedGesture3D gesture = inputGesture.getGesture();
		Note noteX = new Note();											//X plane Note
		Note noteY = new Note();											//Y plane Note
		Note noteZ = new Note();											//Z plane Note
		List<Gesture<Note>> returnList = new ArrayList<Gesture<Note>>();	//Return variable
		
		//TODO Split to planes (find a way, projection of points in 3D space onto planes)
		
		Gesture<Note> planeX = new GestureSample("X-plane", noteX);			//Put notes into gestures
		Gesture<Note> planeY = new GestureSample("Y-plane", noteY);
		Gesture<Note> planeZ = new GestureSample("Z-plane", noteZ);
		
		returnList.add(planeX);												//Add planes to returnlist
		returnList.add(planeY);
		returnList.add(planeZ);
		return returnList;													//Return list with three planes 
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
