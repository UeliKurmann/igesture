package org.ximtec.igesture.algorithm.rubine3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
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
		List<Gesture<Note>> planes = gesture.splitToPlanes();		//Split gesture into planes

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
	
	
	/** Combines resultsets from the three 2D planes into one final resultset for the 3D gesture
	 * 
	 * @param sets
	 * @return
	 */
	private ResultSet combineResultSets(List<ResultSet> sets){
		
		//TODO Combine resultsets.
		
		//Determine weights of the different planes. have a look at the amount of movement per plane?
		//Also use global configuration
		
		return null;
	}

}
