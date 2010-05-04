/**
 * 
 */
package org.ximtec.igesture;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.PriorityQueue;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class MultimodalGestureQueue {

	//MARKING STRATEGY!!!!!!!!!
		
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
	
	public synchronized void push(Gesture<?> gesture)
	{
		//put in gesture queue
		queue.add(new QueueElement(gesture,characterMapping.get(gesture.getName())));
		//TODO
		recogniser.executeTasks(getWindowOfQueue());
	}
	
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
	
	private QueueElement[] getWindowOfQueue()
	{
		QueueElement[] arr = new QueueElement[1]; 
		arr = queue.toArray(arr);
		
		long time = arr[arr.length-1].getTime() - timeWindowMapping.get(arr[arr.length-1].getGesture().getName()).getTimeInMillis();
		
		
		//TODO think that because of current time window implementation, there will not be a lot of correctly recognised gestures
		
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i].getTime() >= time)
			{
				index = i;
				break;
			}
		}
		
		return Arrays.copyOfRange(arr, index, arr.length);
	}
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public class QueueElement implements Comparable<QueueElement>//TODO different locks
	{
		private int windows;
		private boolean identified;
		private Gesture<?> gs;
		private String ch;
		private long time;
		
		public QueueElement(Gesture<?> gesture, String character)
		{
			gs = gesture;
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
		 * @return the windows
		 */
		public synchronized int getWindows() {
			return windows;
		}
		
		public synchronized void incrementWindows()
		{
			windows++;
		}
		
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
		public int compareTo(QueueElement o) {
			if(getTime() > o.getTime())
				return 1;
			else if(getTime() < o.getTime())
				return -1;
			else
				return 0; 
		}
	}
}
