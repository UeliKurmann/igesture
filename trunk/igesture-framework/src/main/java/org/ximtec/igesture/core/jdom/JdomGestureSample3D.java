/*
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      : 	XML support for the GestureSample3D class.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 04, 2009     vogelsar    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core.jdom;

import org.jdom.Element;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.util.JdomRecordedGesture3D;
import org.ximtec.igesture.util.RecordedGesture3D;


/**
 * XML support for the GestureSample3D class.
 * 
 * @version 1.0, Jan 2009
 * @author Arthur Vogels, arthur.vogels@gmail.com
 */
public class JdomGestureSample3D extends Element {

   public static final String ROOT_TAG = "gesturesample3d";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomGestureSample3D(Gesture<RecordedGesture3D>  sample) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, sample.getName());
      setAttribute(UUID_ATTRIBUTE, ((GestureSample3D)sample).getId());
      addContent(new JdomRecordedGesture3D(sample.getGesture()));
   }


   public static Gesture<RecordedGesture3D> unmarshal(Element sample) {
      RecordedGesture3D gesture = null;

      if (sample.getChild(JdomRecordedGesture3D.ROOT_TAG) != null) {
         gesture = JdomRecordedGesture3D.unmarshal(sample.getChild(JdomRecordedGesture3D.ROOT_TAG));
      }

      final String name = sample.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = sample.getAttributeValue(UUID_ATTRIBUTE);
      final GestureSample3D gestureSample = new GestureSample3D(name, gesture);
      gestureSample.setId(uuid);
      return gestureSample;

   } // unmarshal

}
