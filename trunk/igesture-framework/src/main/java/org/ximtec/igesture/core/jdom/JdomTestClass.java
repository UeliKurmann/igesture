/*
 * @(#)JdomTestSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;


/**
 * XML support for the TestSet class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomTestClass extends Element {

   public static final String ROOT_TAG = "testClass";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String REFID_ATTRIBUTE = "idref";


   /**
    * Creates a Test Class Jdom Element
    * @param testClass a Test Class instance
    */
   public JdomTestClass(TestClass testClass) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, testClass.getName());
      setAttribute(UUID_ATTRIBUTE, testClass.getId());

      for (final Gesture<Note> sample : testClass.getGestures()) {
         addContent(new JdomGestureSample(sample));
      }
   }


   @SuppressWarnings("unchecked")
   public static TestClass unmarshal(Element setElement) {
      final String name = setElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = setElement.getAttributeValue(UUID_ATTRIBUTE);
      final TestClass testClass = new TestClass(name);
      testClass.setId(uuid);

      for (Element sampleElement : (List<Element>)setElement.getChildren(JdomGestureSample.ROOT_TAG)) {
         testClass.add(JdomGestureSample.unmarshal(sampleElement));
      }

      return testClass;
   } // unmarshal

}
