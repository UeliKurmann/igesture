
package org.ximtec.igesture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ximtec.igesture.util.diff_match_patch;
import org.ximtec.igesture.util.diff_match_patch.Diff;
import org.ximtec.igesture.util.diff_match_patch.Operation;

import org.ximtec.igesture.MultimodalGestureQueue.QueueElement;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.core.composite.Constraint;
import org.ximtec.igesture.event.GestureHandler;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * This class recognises composite gestures.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class MultimodalGestureRecogniser {
	
	private static final Logger LOGGER = Logger.getLogger(MultimodalGestureRecogniser.class.getName());

	private Set<MultimodalGestureHandler> gestureHandlers = new HashSet<MultimodalGestureHandler>();
	
	/** start value for the character mapping */
	private static final int START_CHAR = 35; // # in ASCII table
	private static final int NR_THREADS = 6;
	private static final int PATTERNS_PER_THREAD = 10;
	
	private int patternsPerThread;
	private int nrThreads;
	
	/** mapping from the gesture class to a character representation */
	private Map<String, String> charMapping;
	
	/** mapping from a gesture class to a time window*/
	private Map<String, Calendar> timeWindows;
	
	/** mapping from the patterns to the composite gesture they represent */
	private Map<String, String> patternsMapping;
	/** patterns */
	private String[] patterns;
	
	/** composing gestures */
	private Set<String> gestures;
	
	/** mapping from the composite gesture classes to constraints */
	private Map<String, Constraint> constraintsMapping;
	
	/** queue to push composing gestures in */
	private MultimodalGestureQueue queue;
	
	/** status of the multi-modal recogniser*/
	private boolean running;
	
	/** handles threads */
	private ExecutorService executor;
	/** handles garbage collection */
	private ExecutorService garbagecollector;

	private IDeviceManager manager;
	
	/**
	 * Constructor
	 * @param set	the gesture set to use.
	 */
	public MultimodalGestureRecogniser(GestureSet set, IDeviceManager manager)
	{
		//TODO fire simple gestures that were not used in a composite but are potentially part of a composite
		//TODO use time windows
		//TODO composite of composites
		this.manager = manager;
		patternsPerThread = PATTERNS_PER_THREAD;
		gestures = new HashSet<String>();
		timeWindows = new /*Concurrent*/HashMap<String,Calendar>();
		constraintsMapping = new /*Concurrent*/HashMap<String, Constraint>();
		patternsMapping = new /*Concurrent*/HashMap<String, String>();
		
		/* get composing gesture classes and time windows */
		List<GestureClass> classes = set.getGestureClasses();
		for (Iterator<GestureClass> iterator = classes.iterator(); iterator.hasNext();) {
			GestureClass gestureClass = iterator.next();
			List<Descriptor> descriptors = gestureClass.getDescriptors();
			for (Iterator<Descriptor> iter = descriptors.iterator(); iter.hasNext();) {
				Descriptor descriptor = iter.next();
				if(descriptor instanceof CompositeDescriptor)
				{
					// get the constraint
					Constraint c = ((CompositeDescriptor)descriptor).getConstraint();
					// map the constraint, so you know which constraint belongs with which composite gesture
					constraintsMapping.put(gestureClass.getName(), c);
					// get the composing gestures and hold them
					gestures.addAll(c.getGestureClasses());
					
					/* determine time windows */
					//get the time windows for the composing gestures
					Map<String, Calendar> map = c.determineTimeWindows();
					Set<String> keys = map.keySet();
					//iterate over each composing gesture and ...
					for (Iterator<String> itera = keys.iterator(); itera.hasNext();) {
						String key = itera.next();
						//... if it is in timeWindows ..
						if(timeWindows.containsKey(key))
						{
							Calendar cal = timeWindows.get(key);
							//.. add the largest time window as value
							if(cal.before(map.get(key)))
								timeWindows.put(key, map.get(key));
						}
						else //... if not in timeWindows, add it
						{
							timeWindows.put(key, map.get(key));
						}
					}
					break;
				}
			}
		}
		
		/* generate character representation */
		charMapping = new HashMap<String,String>();
		int ch = START_CHAR;
		//iterate over all composing gestures
		for (Iterator<String> iterator = gestures.iterator(); iterator.hasNext();) {
			String gs = iterator.next();
			//assign a character representation (unicode, starting from startChar)
			charMapping.put(gs, new String(new char[]{(char)ch}));
			ch++;
		}
		
		queue = new MultimodalGestureQueue(charMapping, timeWindows, this);
		
		/* generate patterns */
		//for each constraint (so each composite gesture)
		for (Iterator<String> iterator = constraintsMapping.keySet().iterator(); iterator.hasNext();) {
			String gesture = iterator.next();
			Constraint constraint = constraintsMapping.get(gesture);
			//generate patterns and map them to the composite gesture, so you can know which gesture is recognised
			Set<String> patterns = constraint.generatePatterns(charMapping); 
			for (Iterator<String> iter = patterns.iterator(); iter.hasNext();) {
				String p = iter.next();
				patternsMapping.put(p, gesture);				
			}
		}
		Set<String> s = patternsMapping.keySet();
		System.out.println(patternsMapping.toString());
		patterns = new String[s.size()];
		int i = 0;
		for (Iterator<String> iterator = s.iterator(); iterator.hasNext();i++) {
			patterns[i] = iterator.next();
		}		
	}
	
	/**
	 * Constructor
	 * @param set	The gesture set to use.
	 * @param ppt	The number of patterns per thread (default 10).
	 */
	public MultimodalGestureRecogniser(GestureSet set, IDeviceManager manager, int ppt)
	{
		this(set, manager);
		patternsPerThread = (ppt < 1) ? PATTERNS_PER_THREAD : ppt;
	}
	
	public void start()
	{
		running = true;
		
		/* start thread per ppt patterns */
		//determine number of threads
		double size = (double)patternsMapping.keySet().size();
		nrThreads = 1;
		if(size % patternsPerThread == 0)
			nrThreads = (int) size / patternsPerThread;
		else
			nrThreads = (int) (1 + size / patternsPerThread);
		
		LOGGER.log(Level.INFO,"Multi-modal Recogniser will use "+nrThreads+" thread(s).");
		
		executor = Executors.newFixedThreadPool(nrThreads);
		LOGGER.log(Level.INFO, "Execution thread pool started.");
		
		//garbage collection thread
		Thread t = new GarbageThread(queue);
		t.start();//without firing
		LOGGER.log(Level.INFO, "Garbage thread started.");
	}
	
	public void stop()
	{
		executor.shutdownNow();
		running = false;
		LOGGER.log(Level.INFO,"Execution thread pool stopped.");
	}
	
	/**
	 * @param queueElements 
	 * 
	 */
	public void recognise(QueueElement[] queueElements) 
	{
		System.out.println("MMR recognising...");
		String text = getStringRepresentation(queueElements);
		for(int i = 0; i < nrThreads; i++)
		{
			String[] patternsForTask = Arrays.copyOfRange(patterns, i*patternsPerThread, (i+1)*patternsPerThread);
			executor.execute(new MultimodalRunnable(patternsForTask, text, queueElements, manager));
		}
	}
	
	/**
	 * @param queueElements
	 * @return
	 */
	private String getStringRepresentation(QueueElement[] queueElements) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < queueElements.length; i++) {
			builder.append(queueElements[i].getCharacterRepresentation());
		}
		return builder.toString();
	}	
	
	public Set<String> getComposingGestureClasses()
	{
		return gestures;
	}
	
	/**
	 * @return the queue
	 */
	public MultimodalGestureQueue getQueue() {
		return queue;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	   /**
	    * Fires an event and informs all registered gesture handlers.
	    * 
	    * @param resultSet the result set to be used as an argument for the fired
	    *            event.
	    */
	   protected void fireEvent(final String result) {
		   
		  LOGGER.log(Level.INFO,"MMR result: "+result);
		  
		  Executor executor = Executors.newFixedThreadPool(NR_THREADS);
		  for (final MultimodalGestureHandler gestureHandler : gestureHandlers) {
		     LOGGER.info("Handler: "+gestureHandler.getClass());
		     if (gestureHandler != null) {
		        executor.execute(new Runnable() {
		
		           @Override
		           public void run() {
		              gestureHandler.handle(result);
		           }
		           
		        });
		     }
		  }
	   } // fireEvent
	   
	   /**
	    * Adds a gesture handler to the recogniser. The gesture handler's handle()
	    * method will be invoked every time a new ResultSet has been created (as part
	    * of a recognition process).
	    * @param gestureHandler the gesture handler to be added.
	    */
	   public void addGestureHandler(MultimodalGestureHandler gestureHandler) {
	      gestureHandlers.add(gestureHandler);
	   } // addGestureHandler
	
	
	   /**
	    * Removes a gesture handler from the recogniser.
	    * @param gestureHandler the gesture handler to be removed.
	    */
	   public void removeGestureHandler(MultimodalGestureHandler gestureHandler) {
	      gestureHandlers.remove(gestureHandler);
	   } // removeGestureHandler
	   
	   
	   

	class MultimodalRunnable implements Runnable
	{
		private String[] patterns;
		private String text;
		private QueueElement[] elements;
		private IDeviceManager manager;
		
		public MultimodalRunnable(String[] patterns, String text, QueueElement[] queueElements, IDeviceManager manager)
		{
			this.manager = manager;
			this.patterns = patterns;
			this.text = text;
			this.elements = queueElements;
			
			for (int i = 0; i < queueElements.length; i++) {
				queueElements[i].incrementWindows();
			}
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			
			diff_match_patch dmp = new diff_match_patch();
			dmp.Match_Distance = 1000;
			dmp.Match_Threshold = 0.5f;
			
			//detect pattern
			for (int i = 0; i < patterns.length; i++) {
				
				String pattern = patterns[i];
				ArrayList<Integer> indexes = new ArrayList<Integer>();
							
//				System.out.println(pattern);
				
				if(pattern == null || pattern.length() > text.length())
					continue; //break;
				
				//search potential match
				int loc = dmp.match_main(text, pattern, 0);
				
				if(loc > -1) // if potential match
				{
//					System.out.println("Found potential match at location: "+loc);
					
					//get diffences
					LinkedList<Diff> diffs = dmp.diff_main(text.substring(loc), pattern);
//					System.out.println(diffs.toString());
					// determine indexes of items in queue to use in contraint check
					// + determine if real potential match
					boolean skip = false;
					
					for(Diff d : diffs)
					{		
						if(d.operation == Operation.DELETE) // something extra in queue
						{
							loc += d.text.length();
						}
						else if(d.operation == Operation.EQUAL) // something similar in queue
						{
							int tmp = loc + d.text.length();
							for(int j = loc; j < tmp; j++)
								indexes.add(j);
							loc += d.text.length();
						}
						else if(d.operation == Operation.INSERT) // something short in queue
						{
							skip = true;
							break;
						}
					}
					
					if(skip == false)
					{
//						System.out.println("Indexes:" + indexes.toString());
						//if pattern detected, do condition check
						List<Gesture<?>> gestures = getGestures(indexes);
						String gestureClass = patternsMapping.get(pattern);
						Constraint c = constraintsMapping.get(gestureClass);
						boolean conditionsValid = c.validateConditions(gestures, manager);
						
						//fire if gesture recognised
						//fire if gesture recognised
						if(conditionsValid && identifyGestures(indexes))
						{					
							System.out.println("CONDITIONS VALID");
							fireEvent(gestureClass);							
						}

							//TODO fire resultset instead of string
					}
//					else
//					{
//						System.out.println("no possible match, gestures short");
//					}
					indexes.clear();
				}
//				else
//					System.out.println("Found no potential match.");
			}
			for (int i = 0; i < elements.length; i++) {
				elements[i].decrementWindows();
			}
		}

		/**
		 * @param indexes
		 * @return
		 */
		private List<Gesture<?>> getGestures(ArrayList<Integer> indexes) {
			List<Gesture<?>> list = new ArrayList<Gesture<?>>();
			for (Iterator<Integer> iterator = indexes.iterator(); iterator.hasNext();) {
				Integer index = iterator.next();
				list.add(elements[index].getGesture());			
			}
			return list;
		}
		
		/**
		 * A composite gesture has been identified and validated, mark queue elements
		 * garbage thread will not fire the element as a single gesture
		 * @param indexes
		 */
		private boolean identifyGestures(ArrayList<Integer> indexes)
		{
			for (Iterator iterator = indexes.iterator(); iterator.hasNext();) {
				Integer index = (Integer) iterator.next();
				if(elements[index].isIdentified())
					return false;//if identified in the meantime by another thread, do not identify or a collision occurs
			}
			
			for (Iterator<Integer> iterator = indexes.iterator(); iterator.hasNext();) {
				Integer index = iterator.next();
				elements[index].setIdentified(true);			
			}
			
			return true;
		}
		
	}
	
	class GarbageThread extends Thread
	{
		private static final int SLEEP_TIME = 60000;
		private static final int MIN_QUEUE_SIZE = 5;
		
		private MultimodalGestureQueue queue;
		private int sleepTime;
		private int minQueueSize;
		
		public GarbageThread(MultimodalGestureQueue queue)
		{
			this(queue, SLEEP_TIME, MIN_QUEUE_SIZE);
		}
		
		public GarbageThread(MultimodalGestureQueue queue, int sleep, int queueSize)
		{
			this.queue = queue;
			this.sleepTime = sleep;
			this.minQueueSize = queueSize;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while(true)
			{
				try
				{
					sleep(sleepTime);
				}
				catch(InterruptedException e)
				{}
				
				boolean next = true;
				if(queue.size() > minQueueSize)
				{	
					while(next)
					{
						//get element
						QueueElement elem = queue.peek();
						//if queue not empty and element is not overlapped by time windows
						if(elem != null && elem.getWindows() == 0)
						{
							//remove element from queue
							final QueueElement e = queue.poll();
							
							System.out.println("removed element "+e);
							
//							//if element not part of composite
//							if(!e.isIdentified())
//							{
//								LOGGER.log(Level.INFO,"MMR simple result: "+e.getGesture().getName());
//								
//								//notify registered GestureHandlers from source recogniser
//								Executor executor = Executors.newFixedThreadPool(NR_THREADS);
//								for (final GestureHandler gestureHandler : e.getResultSet().getSource().getGestureHandlers()) {
//									
//									if (gestureHandler != null) {
//										executor.execute(new Runnable() {
//								
//											@Override
//											public void run() {
//												gestureHandler.handle(e.getResultSet());
//											}
//								           
//										});
//									}
//							  	}
//							}
							if(queue.size() > minQueueSize)//always minimum number of items in queue
								next = true; //continue removing from head
							else
								next = false;
						}
						else // element overlapped by time windows, stop removing from head
							next = false;
					}
				}				
			}			
		}		
	}
}
