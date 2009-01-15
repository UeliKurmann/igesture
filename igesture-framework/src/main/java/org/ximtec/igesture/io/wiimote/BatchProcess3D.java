package org.ximtec.igesture.io.wiimote;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.core.BatchAlgorithm;
import org.ximtec.igesture.batch.core.BatchParameter;
import org.ximtec.igesture.configuration.Configuration;

public class BatchProcess3D extends BatchProcess {

	public BatchProcess3D(BatchProcessContainer container) {
		super(container);
	}

	   /**
	    * A recursive method which iterates through all possible permutations.
	    * 
	    * @param algorithm the name of the algorithm.
	    * @param parameters the BatchParameter instance.
	    * @param index the index (position) of the parameter.
	    * @param configuration the current configuration.
	    * @param configurations the list of all generated configurations.
	    */
	   protected static void permuteParameters(String algorithm,
	         List<BatchParameter> parameters, int index,
	         Configuration configuration, List<Configuration> configurations) {

	      /**
	       * Abort Condition. The last parameter is reached so one configuration is
	       * complete and can be added to the list.
	       */
	      if (index == parameters.size()) {
	         configurations.add((Configuration)configuration.clone());
	         return;
	      }

	      final BatchParameter param = parameters.get(index);
	      processSimpleParameter(param, configuration, algorithm, parameters, index,
	            configurations);
	      processPowerSetParameter(param, configuration, algorithm, parameters,
	            index, configurations);
	      processSequenceParameter(param, configuration, algorithm, parameters,
	            index, configurations);
	      processForLoopParameter(param, configuration, algorithm, parameters,
	            index, configurations);
	   } // permuteParameters
}
