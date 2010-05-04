/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :	The main recogniser component.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Feb 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureHandler;
import org.ximtec.igesture.util.XMLTool;


/**
 * The main recogniser component.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Recogniser {

   private static final int NUMBER_OF_THREADS = 6;

   private static final Logger LOGGER = Logger.getLogger(Recogniser.class.getName());

   private List<Algorithm> algorithms;

   private Set<GestureHandler> gestureHandlers = new HashSet<GestureHandler>();
   private Set<GestureHandler> multimodalGestureHandlers = new HashSet<GestureHandler>(); //multimodal gesture managers

//   private boolean multimodalMode = false;
   public static final int MIXED_MODE = 1;
   public static final int MULTIMODAL_MODE = 2;
   public static final int NORMAL_MODE = 0;
   private int mode = NORMAL_MODE;
  

   /**
    * Creates a new recogniser.
    * 
    * @param config the configuration object.
    * @throws AlgorithmException if the recogniser could not be created.
    */
   public Recogniser(Configuration config) throws AlgorithmException {
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Creates a new recogniser.
    * 
    * @param config the configuration object.
    * @param gestureHandler the gesture handler to be added.
    * @throws AlgorithmException if the recogniser could not be created.
    */
   public Recogniser(Configuration config, GestureHandler gestureHandler)
         throws AlgorithmException {
      final Configuration clone = (Configuration)config.clone();
      addGestureHandler(config.getGestureHandler());
      clone.setGestureHandler(gestureHandler);
      algorithms = AlgorithmFactory.createAlgorithms(clone);
   }


   /**
    * Creates a new recogniser.
    * 
    * @param config the configuration object.
    * @param gestureSet the gesture set to be used.
    * @throws AlgorithmException if the recogniser could not be created.
    */
   public Recogniser(Configuration config, GestureSet gestureSet)
         throws AlgorithmException {
      config.addGestureSet(gestureSet);
      addGestureHandler(config.getGestureHandler());
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Creates a new recogniser.
    * 
    * @param config the input stream from which the XML configuration can be
    *            read.
    * @throws AlgorithmException if the recogniser could not be created.
    */
   public Recogniser(InputStream config) throws AlgorithmException {
      this(XMLTool.importConfiguration(config));
   }


   /**
    * Creates a new recogniser.
    * 
    * @param config the input stream from where the XML configuration can be
    *            read.
    * @param set the input stream from where the gesture set in XML format can be
    *            read.
    * @param gestureHandler the gesture handler to be informed about results.
    * @throws AlgorithmException if the recogniser could not be created.
    */
   public Recogniser(InputStream config, InputStream set,
         GestureHandler gestureHandler) throws AlgorithmException {
      final Configuration configuration = XMLTool.importConfiguration(config);
      configuration.setGestureHandler(gestureHandler);
      configuration.addGestureSet(XMLTool.importGestureSet(set));
      addGestureHandler(configuration.getGestureHandler());
      algorithms = AlgorithmFactory.createAlgorithms(configuration);
   }


   /**
    * Adds a gesture handler to the recogniser. The gesture handler's handle()
    * method will be invoked every time a new ResultSet has been created (as part
    * of a recognition process).
    * @param gestureHandler the gesture handler to be added.
    */
   public void addGestureHandler(GestureHandler gestureHandler) {
      gestureHandlers.add(gestureHandler);
   } // addGestureHandler


   /**
    * Removes a gesture handler from the recogniser.
    * @param gestureHandler the gesture handler to be removed.
    */
   public void removeGestureHandler(GestureHandler gestureHandler) {
      gestureHandlers.remove(gestureHandler);
   } // removeGestureHandler


   /**
    * Fires an event and informs all registered gesture handlers.
    * 
    * @param resultSet the result set to be used as an argument for the fired
    *            event.
    */
   protected void fireEvent(final ResultSet resultSet) {
      
	  Set<GestureHandler> set = new HashSet<GestureHandler>();
	  switch(mode)
	  {
	  	case MULTIMODAL_MODE:
	  		set.addAll(multimodalGestureHandlers);
	  		break;
	  	case MIXED_MODE:
	  	case NORMAL_MODE:
	  		set.addAll(gestureHandlers);
	  }
	  
	  Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	  for (final GestureHandler gestureHandler : set) {
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


   /**
    * Recognises a gesture. The method uses all algorithms and returns a
    * combination of the results returned by the different algorithms if
    * 'recogniseAll' is set to true or the result of the first algorithm that
    * recognised the given gesture if 'recogniseAll' is set to false.
    * 
    * @param gesture the gesture to be recognised.
    * @param recogniseAll true if the combination of all algorithms has to be
    *            returned, false if only the result of the first algorithm that
    *            recognises the result has to be returned.
    * @return the result set containing the recognised gesture classes.
    */
   public ResultSet recognise(Gesture< ? > gesture, boolean recogniseAll) {
      final ResultSet result = new ResultSet(this);

      for (final Algorithm algorithm : algorithms) {

         try {
            for (final Result r : algorithm.recognise(gesture).getResults()) {
               result.addResult(r);
            }
         }
         catch (AlgorithmException e) {
            LOGGER.info(e.getMessage());
         }

         if (!result.isEmpty() && !recogniseAll) {
            break;
         }

      }
      LOGGER.info(result.toString());
      
      //MODIFY >
      
      // based on the resultset, determine the gesture class of the gesture
      List<Result> results = result.getResults();
      Result maxResult = null;
      double maxAccuracy = 0.0;
		
      for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
    	  Result r = iterator.next();
    	  if(r.getAccuracy() > maxAccuracy)
    	  {
    		  maxAccuracy = r.getAccuracy();
    		  maxResult = r;
    	  }
      }
      //set the name of the gesture to that of the gesture class
      if(maxResult != null)
      {
    	  gesture.setName(maxResult.getGestureClassName()+":"+maxResult.getGestureClass().getId());
//    	  fireEvent(result);
      }
      else
    	  gesture.setName("");
      //MODIFY <
      
      result.setGesture(gesture);
      fireEvent(result);
      return result;
   } // recognise


   /**
    * Recognises a gesture. The method uses all registered algorithms and stops
    * as soon as the first algorithm returns a result.
    * 
    * @param gesture the gesture to be recognised.
    * @return the result set containing the recognised gesture classes.
    */
   public ResultSet recognise(Gesture< ? > gesture) {
      return recognise(gesture, false);
   } // recognise


   /**
    * Recognises a gesture. The method uses all registered algorithms and stops
    * as soon as the first algorithm returns a result.
    * 
    * @param note the note to be recognised.
    * @return the result set containing the recognised gesture classes.
    */
   public ResultSet recognise(Note note) {
      return recognise(note, false);
   } // recognise


   /**
    * Recognises a gesture. The method uses all algorithms and returns a
    * combination of the results returned by the different algorithms if
    * 'recogniseAll' is set to true or the result of the first algorithm that
    * recognised the given note if 'recogniseAll' is set to false.
    * 
    * @param note the note to be recognised.
    * @param recogniseAll true if the combination of all algorithms has to be
    *            returned, false if only the result of the first algorithm that
    *            recognises the result has to be returned.
    * @return the result set containing the recognised gesture classes.
    */
   public ResultSet recognise(Note note, boolean recogniseAll) {
      return recognise(new GestureSample(Constant.EMPTY_STRING, note));
   } // recognise

   
   /*
    * Returns the list of algorithms.
    */
   public List<Algorithm> getAlgorithms() {
      return algorithms;
   } // getAlgorithms

   
   // FIXME: remove?
   public static Recogniser newRecogniser(String configFile,
         String gestureSetFile) throws AlgorithmException {
      GestureSet gestureSet = XMLTool.importGestureSet(new File(gestureSetFile));
      Configuration configuration = XMLTool.importConfiguration(new File(
            configFile));
      return new Recogniser(configuration, gestureSet);
   }
   
   
   /**
    * Get the associated gesture handlers.
    */
   public Set<GestureHandler> getGestureHandlers()
   {
	   return gestureHandlers;
   }
   
   /**
    * Change the mode of this recogniser. 
    * If the recogniser is in multimodal mode, the gestures are first sent to the multimodal 
    * gesture manager that further handles the notification of interested listeners.
    * If the recogniser is in normal mode, the gestures are fired immediately to the interested listeners.
    * If the recogniser is in mixed mode, both behaviors are used.
    * @param mode	The value to set
    */
   public void setMode(int mode)
   {
	   this.mode = mode;
   }

   /**
    * Get the mode of this recogniser
    * @return the mode
    */
   public int getMode() {
	   return mode;
   }
   


/**
	* Add a multimodal gesture handler to the recogniser.
    */
   public void addMultimodalGestureHandler(GestureHandler handler)
   {
	   multimodalGestureHandlers.add(handler);
   }
   
   /**
    * Remove a multimodal gesture handler from the recogniser
    */
   public void removeMultimodalGestureHandler(GestureHandler handler)
   {
	   multimodalGestureHandlers.remove(handler);
   }

}
