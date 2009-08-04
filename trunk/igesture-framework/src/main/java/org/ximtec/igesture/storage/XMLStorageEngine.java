/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class XMLStorageEngine extends DefaultFileStorageEngine {

  private static final Logger LOGGER = Logger.getLogger(XMLStorageEngine.class.getName());

  /**
   * Create the XML Storage Engine
   * 
   * @param filename
   */
  public XMLStorageEngine(String filename) {
    super(new File(filename));
  }

  /**
   * Serialize the internal data structure to an XML file.
   */
  @Override
  protected void serialize(HashMap<Class<? extends DataObject>, List<DataObject>> objects, File file) {
    final XStream xstream = new XStream(new DomDriver());
    final String xml = xstream.toXML(objects);

    try {
      final FileWriter fw = new FileWriter(file);
      fw.write(xml);
      fw.flush();
      fw.close();
    } catch (final IOException e) {
      LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
    }

  } // serialize

  /**
   * Deserialise an XML file.
   * 
   * @param file
   *          the file to be deserialised.
   * @return the internal data structure handling the data objects.
   */
  @SuppressWarnings("unchecked")
  protected HashMap<Class<? extends DataObject>, List<DataObject>> deserialize(File file) {
    final XStream xstream = new XStream(new DomDriver());
    HashMap<Class<? extends DataObject>, List<DataObject>> dataObjects = null;

    if (!file.exists()) {
      return new HashMap<Class<? extends DataObject>, List<DataObject>>();
    }

    try {
      final FileReader fr = new FileReader(file);
      dataObjects = (HashMap<Class<? extends DataObject>, List<DataObject>>) xstream.fromXML(fr);
      fr.close();
    } catch (final FileNotFoundException e) {
      LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
    } catch (final IOException e) {
      LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
    } catch (final ClassCastException e) {
      LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
    }
    if (dataObjects == null) {
      dataObjects = new HashMap<Class<? extends DataObject>, List<DataObject>>();
    }

    return dataObjects;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.ximtec.igesture.storage.StorageEngine#copyTo(java.io.File)
   */
  @Override
  public synchronized void copyTo(File file) {
    setStorageFile(file);
    commit();
  }

}
