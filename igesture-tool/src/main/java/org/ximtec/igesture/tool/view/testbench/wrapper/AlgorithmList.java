/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testbench.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.DefaultPropertyChangeNotifier;
import org.ximtec.igesture.tool.view.MainModel;

public class AlgorithmList extends DefaultPropertyChangeNotifier {

  public static final String PROPERTY_SETS = "algorithms";

  public List<AlgorithmWrapper> algorithms;

  public AlgorithmList(MainModel mainModel) {
    algorithms = new ArrayList<AlgorithmWrapper>();
  }

  public void addAlgorithm(AlgorithmWrapper algorithmWrapper) {
    algorithms.add(algorithmWrapper);
    propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, 0, null, algorithmWrapper);
  }

  public List<AlgorithmWrapper> getAlgorithms() {
    return algorithms;
  }

  public String getName() {
    // FIXME constant
    return "Algorithm List";
  }

  @Override
  public String toString() {
    return getName();
  }
}
