/*
 * @(#)JdomTestSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   XML support for the TestSet class.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core.jdom;

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;


/**
 * XML support for the TestSet class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomTestSet extends Element {

   public static final String ROOT_TAG = "testSet";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String REFID_ATTRIBUTE = "idref";


   public JdomTestSet(TestSet testSet) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, testSet.getName());
      setAttribute(UUID_ATTRIBUTE, testSet.getId());

      for (TestClass testClass : testSet.getTestClasses()) {
         addContent(new JdomTestClass(testClass));
      }

   }


   @SuppressWarnings("unchecked")
   public static TestSet unmarshal(Element setElement) {
      final String name = setElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = setElement.getAttributeValue(UUID_ATTRIBUTE);
      final TestSet testSet = new TestSet(name);
      testSet.setId(uuid);

      for (final Element sampleElement : (List<Element>)setElement
            .getChildren(JdomGestureSample.ROOT_TAG)) {
         testSet.add((GestureSample)JdomGestureSample.unmarshal(sampleElement));
      }

      return testSet;
   } // unmarshal

}
