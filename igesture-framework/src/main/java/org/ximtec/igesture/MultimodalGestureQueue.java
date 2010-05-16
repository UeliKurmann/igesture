/**
 * 
 */
package org.ximtec.igesture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.ResultSet;

/**
 * This class represents the input queue of the multi-modal gesture recogniser.
 * 
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class MultimodalGestureQueue {

	private PriorityQueue<QueueElement> queue;
	private Map<String, String> characterMapping;
	private Map<String, Calendar> timeWindowMapping;
	private MultimodalGestureRecogniser recogniser;
	
	public MultimodalGestureQueue(Map<String, String> charMapping, Map<String, Calendar> timeWindows, MultimodalGestureRecogniser owner)
	{
		queue = new PriorityQueue<QueueElement>();
		characterMapping = charMapping;
		timeWindowMapping = timeWindows;
		recogniser = owner;
	}
	
	/**
	 * Push an element in the queue
	 * @param resultSet	The recognised gesture as a ResultSet
	 */
	public synchronized void push(ResultSet resultSet)
	{
		//put in gesture queue
		queue.add(new QueueElement(resultSet,characterMapping.get(resultSet.getGesture().getName())));
		//execute a recognition step
		if(recogniser.isRunning())
		{
			//avoid java.util.concurrent.RejectedExecutionException by asking the thread pool to execute something 
			//if the pool is not running
			recogniser.recognise(getWindowOfQueue());
		}
	}
	
	/**
	 * Remove an element from the queue
	 * @return true if the element was removed
	 */
	public synchronized boolean remove()
	{
		boolean sleep = false;
		//remove head from gesture queue
		QueueElement elem = queue.peek();
		if(elem != null && elem.getWindows() == 0)
		{
			queue.poll();
			sleep = true;
		}	
		return sleep;
	}
	
	/**
	 * Retrieves but does not remove the head of the queue, <i>null</i> if empty
	 * @return
	 */
	public synchronized QueueElement peek()
	{
		return queue.peek();
	}
	
	/**
	 * Retrieves and removes the head of the queue, <i>null</i> if empty
	 * @return
	 */
	public synchronized QueueElement poll()
	{
		return queue.poll();
	}
	
	/**
	 * Get a part of the queue that fits within the time window of element last put in the queue
	 * @return
	 */
	private QueueElement[] getWindowOfQueue()
	{
		QueueElement[] arr = new QueueElement[1]; 
		arr = queue.toArray(arr);
	
		// calculate time window
		Calendar cal = timeWindowMapping.get(arr[arr.length-1].getGesture().getName());
		int millis = cal.get(Calendar.MILLISECOND);
		int seconds = cal.get(Calendar.SECOND);
		int minutes = cal.get(Calendar.MINUTE);
		int hours = cal.get(Calendar.HOUR);
		millis += seconds*1000+minutes*60*1000+hours*60*60*1000;

//		System.out.println(millis);
		
		// calculate start time of window
		long time = arr[arr.length-1].getTime() - millis;
		
		//TODO think that because of current time window implementation, there will not be a lot of correctly recognised gestures
		
		//get all gestures performed after time 'time'
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i].getTime() >= time)
			{
				index = i;
				break;
			}
		}
		
		// remove all identified gestures
//		List<QueueElement> list = Arrays.asList(Arrays.copyOfRange(arr, index, arr.length));
		ArrayList<QueueElement> elements = new ArrayList<QueueElement>();
		elements.addAll(Arrays.asList(Arrays.copyOfRange(arr, index, arr.length)));
		for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
			QueueElement queueElement = (QueueElement) iterator.next();
			if(queueElement.isIdentified())
				iterator.remove();
		}
		return elements.toArray(new QueueElement[1]);
		//return Arrays.copyOfRange(arr, index, arr.length);
	}
	
	public synchronized boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public synchronized int size()
	{
		return queue.size();
	}	
	
	/**
	 * This class represents an element in the queue and encapsulates different information.
	 * @author Björn Puype, bpuype@gmail.com
	 *
	 */
	public class QueueElement implements Comparable<QueueElement>//TODO different locks
	{
		private int windows; // the number of time windows overlapping this gesture
		private boolean identified; // is the element part of a recognised and validated composite?
		private Gesture<?> gs; // the gesture sample
		private ResultSet result; // the resultset, is used to fire a single gesture if is not identified as part of a composite
		private String ch; // the character representation of the gesture
		private long time; // the start time stamp of the gesture
		
		/**
		 * Constructor
		 */
		public QueueElement(ResultSet resultSet, String character)
		{
			result = resultSet;
			gs = resultSet.getGesture();
			windows = 0;
			identified = false;
			ch = character;
			if(gs instanceof GestureSample)
			{
				time = ((GestureSample)gs).getGesture().getStartPoint().getTimestamp();
//				System.out.println("QueueElement: GestureSample");
			} 
			else if(gs instanceof GestureSample3D)
			{
				time = ((GestureSample3D)gs).getGesture().getStartPoint().getTimeStamp();
//				System.out.println("QueueElement: GestureSample3D");
			}			
		}

		/**
		 * @return the identified
		 */
		public synchronized boolean isIdentified() {
			return identified;
		}

		/**
		 * @param identified the identified to set
		 */
		public synchronized void setIdentified(boolean identified) {
			this.identified = identified;
		}

		/**
		 * The number of time windows overlapping this gesture.
		 * @return the windows
		 */
		public synchronized int getWindows() {
			return windows;
		}
		
		/**
		 * Increment the number of time windows by 1.
		 */
		public synchronized void incrementWindows()
		{
			windows++;
		}
		
		/**
		 * Decrement the number of time windows by 1.
		 */
		public synchronized void decrementWindows()
		{
			windows--;
		}

		/**
		 * @return the gs
		 */
		public Gesture<?> getGesture() {
			return gs;
		}

		/**
		 * @return the ch
		 */
		public String getCharacterRepresentation() {
			return ch;
		}

		/**
		 * @return the time
		 */
		public long getTime() {
			return time;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(QueueElement o) {// for the ordering in the priority queue, time based
			if(getTime() > o.getTime())
				return 1;
			else if(getTime() < o.getTime())
				return -1;
			else
				return 0; 
		}
		
		public ResultSet getResultSet()
		{
			return result;
		}
		
		public String toString()
		{
			return "[QueueElement ["+gs.getName() + " "+ getTime()+"]]";
		}
	}
}
