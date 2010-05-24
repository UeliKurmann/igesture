
package org.ximtec.igesture;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureHandler;

/**
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class MultimodalGestureManager implements GestureHandler{
	
	private static final int NUMBER_OF_THREADS = 6;

	private static final Logger LOGGER = Logger.getLogger(MultimodalGestureManager.class.getName());

	private Set<String> composingGestures;
	private Set<Recogniser> recognisers;
	private MultimodalGestureQueue queue;
	
	public MultimodalGestureManager(MultimodalGestureRecogniser recogniser)
	{
		composingGestures = recogniser.getComposingGestureClasses();
		recognisers = new HashSet<Recogniser>();
		queue = recogniser.getQueue();
	}
	
	public void addRecogniser(Recogniser recogniser, boolean mixed)
	{
		recogniser.addMultimodalGestureHandler(this);
		// add recogniser
		recognisers.add(recogniser);
		if(mixed == false)
			// set recogniser in multimodal mode
			recogniser.setMode(Recogniser.MULTIMODAL_MODE);
		else
			// set recogniser in mixed mode
			recogniser.setMode(Recogniser.MIXED_MODE);
	}
	
	public void removeRecogniser(Recogniser recogniser)
	{
		recogniser.removeMultimodalGestureHandler(this);
		// remove recogniser
		recognisers.remove(recogniser);
		// set recogniser in normal mode
		recogniser.setMode(Recogniser.NORMAL_MODE);
	}
	
	public void removeAllRecognisers()
	{
		for(Recogniser r : recognisers)
			removeRecogniser(r);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.event.GestureHandler#handle(org.ximtec.igesture.core.ResultSet)
	 */
	@Override
	public void handle(ResultSet resultSet) {

		//if the gesture is a composing gesture send it to the multimodal recogniser by putting it in the queue
		if(composingGestures.contains(resultSet.getGesture().getName()))
		{
			queue.push(resultSet);
		}
		else if(resultSet.getSource().getMode() != Recogniser.MIXED_MODE)//push it out
		{
			//in mixed mode, the clients of the recogniser are already notified by the source recogniser itself
			//therefore, it is not necessary to notify the clients again about the same event
			fireEvent(resultSet);
		}
	}
	
	
   /**
    * Fires an event and informs all registered gesture handlers.
    * 
    * @param resultSet the result set to be used as an argument for the fired
    *            event.
    */
   protected void fireEvent(final ResultSet resultSet) {
	  
	  Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	  for (final GestureHandler gestureHandler : resultSet.getSource().getGestureHandlers()) {
	     LOGGER.info("Handler: "+gestureHandler.getClass());
	     if (gestureHandler != null) {
	        executor.execute(new Runnable() {
	
	           @Override
	           public void run() {
	              gestureHandler.handle(resultSet);
	           }
	           
	        });
	     }
	  }
   } // fireEvent

}
