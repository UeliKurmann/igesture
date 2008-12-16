/*
 * @(#)$Id: WorkspaceTool.java 456 2008-11-11 09:54:06Z D\signerb $
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
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.TestClass;


/**
 * XML support for the TestSet class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
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

      for (final Gesture< ? > sample : testClass.getGestures()) {

         if (sample instanceof GestureSample) {
            addContent(new JdomGestureSample((GestureSample)sample));
         }else if(sample instanceof GestureSample3D){
            // FIXME implement
            //addContent(new JdomGestureSample3D((GestureSample3D)sample));
         }
      }
   }


   @SuppressWarnings("unchecked")
   public static TestClass unmarshal(Element setElement) {
      final String name = setElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = setElement.getAttributeValue(UUID_ATTRIBUTE);
      final TestClass testClass = new TestClass(name);
      testClass.setId(uuid);

      for (Element sampleElement : (List<Element>)setElement
            .getChildren(JdomGestureSample.ROOT_TAG)) {
         testClass.add(JdomGestureSample.unmarshal(sampleElement));
      }

      return testClass;
   } // unmarshal

}
