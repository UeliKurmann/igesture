/*
 * @(#)JdomTestSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   XML support for TestSet
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core.jdom;

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.TestSet;


public class JdomTestSet extends Element {

   public static final String ROOT_TAG = "testSet";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String REFID_ATTRIBUTE = "idref";


   public JdomTestSet(TestSet testSet) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, testSet.getName());
      setAttribute(UUID_ATTRIBUTE, testSet.getID());
      for (final GestureSample sample : testSet.getSamples()) {
         addContent(new JdomGestureSample(sample));
      }
   }


   @SuppressWarnings("unchecked")
   public static Object unmarshal(Element setElement) {
      final String name = setElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = setElement.getAttributeValue(UUID_ATTRIBUTE);
      final TestSet testSet = new TestSet(name);
      testSet.setID(uuid);
      for (final Element sampleElement : (List<Element>) setElement
            .getChildren(JdomGestureSample.ROOT_TAG)) {
         testSet.add((GestureSample) JdomGestureSample.unmarshal(sampleElement));
      }
      return testSet;
   } // unmarshal

}