/*
 * @(#)XMLStorageEngine.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML storage engine implementation.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DataObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * XML storage engine implementation.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class XMLStorageEngine extends DefaultStorageEngine{

   private static final Logger LOGGER = Logger.getLogger(XMLStorageEngine.class
         .getName());

   private File xmlFile;

   private HashMap<Class< ? extends DataObject>, List<DataObject>> dataObjects;


   public XMLStorageEngine(String filename) {
      xmlFile = new File(filename);

      if (xmlFile.exists()) {
         dataObjects = deserialize(xmlFile);
      }
      else {
         dataObjects = new HashMap<Class< ? extends DataObject>, List<DataObject>>();
      }

   }


   /**
    * Serialize the internal data structure to an XML file.
    */
   private void serialize(
         HashMap<Class< ? extends DataObject>, List<DataObject>> objects,
         File file) {
      final XStream xstream = new XStream(new DomDriver());
      final String xml = xstream.toXML(objects);

      try {
         final FileWriter fw = new FileWriter(file);
         fw.write(xml);
         fw.flush();
         fw.close();
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

   } // serialize


   /**
    * Deserialise an XML file.
    * 
    * @param file the file to be deserialised.
    * @return the internal data structure handling the data objects.
    */
   @SuppressWarnings("unchecked")
   private HashMap<Class< ? extends DataObject>, List<DataObject>> deserialize(
         File file) {
      final XStream xstream = new XStream(new DomDriver());
      HashMap<Class< ? extends DataObject>, List<DataObject>> dataObjects = null;

      try {
         final FileReader fr = new FileReader(file);
         dataObjects = (HashMap<Class< ? extends DataObject>, List<DataObject>>)xstream
               .fromXML(fr);
         fr.close();
      }
      catch (final FileNotFoundException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final ClassCastException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      if (dataObjects == null) {
         dataObjects = new HashMap<Class< ? extends DataObject>, List<DataObject>>();
      }

      return dataObjects;
   }


   public void dispose() {
      serialize(dataObjects, xmlFile);
   } // dispose


   @SuppressWarnings("unchecked")
   public <T extends DataObject> T load(final Class<T> clazz, final String id) {
      T dataObject = null;

      if (dataObjects.get(clazz) != null) {

         for (final DataObject tmp : dataObjects.get(clazz)) {

            if (tmp.getID().equals(id)) {
               dataObject = (T)tmp;
               break;
            }

         }

      }

      return dataObject;
   } // load


   @SuppressWarnings("unchecked")
   public <T extends DataObject> List<T> load(Class<T> clazz) {
      if (dataObjects.get(clazz) != null) {
         return (List<T>)dataObjects.get(clazz);
      }

      return new ArrayList<T>();
   } // load


   public void store(DataObject dataObject) {
      addDataObject(dataObject);
   } // store


   public void update(DataObject dataObject) {
      addDataObject(dataObject);
   } // update


   public void remove(DataObject dataObject) {
      removeDataObject(dataObject);
   } // remove


   /**
    * Adds a data object to the object container.
    * 
    * @param dataObject the data object to be added.
    */
   private void addDataObject(DataObject dataObject) {
      // create a list for a specific type if it doesn't exist
      if (dataObjects.get(dataObject.getClass()) == null) {
         dataObjects.put(dataObject.getClass(), new ArrayList<DataObject>());
      }

      // only add dataObject if it isn't already present in the list
      if (!dataObjects.get(dataObject.getClass()).contains(dataObject)) {
         dataObjects.get(dataObject.getClass()).add(dataObject);
      }

   } // addDataObject


   /**
    * Removes a data object from the object container.
    * 
    * @param dataObject the data object to be removed.
    */
   private void removeDataObject(DataObject dataObject) {
      if (dataObjects.get(dataObject.getClass()) != null) {
         dataObjects.get(dataObject.getClass()).remove(dataObject);
      }

   } // removeDataObject

}
