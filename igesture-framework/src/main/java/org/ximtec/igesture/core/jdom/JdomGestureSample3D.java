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
import org.ximtec.igesture.util.additions3d.Note3D;
import org.ximtec.igesture.util.additions3d.jdom.JdomNote3D;


/**
 * XML support for the GestureSample3D class.
 * 
 * @version 1.0, Jan 2009
 * @author Arthur Vogels, arthur.vogels@gmail.com
 */
public class JdomGestureSample3D extends Element {

   public static final String ROOT_TAG = "sample";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomGestureSample3D(Gesture<Note3D>  sample) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, sample.getName());
      setAttribute(UUID_ATTRIBUTE, ((GestureSample3D)sample).getId());
      addContent(new JdomNote3D(sample.getGesture()));
   }


   public static Gesture<Note3D> unmarshal(Element sample) {
      Note3D gesture = null;

      if (sample.getChild(JdomNote3D.ROOT_TAG) != null) {
         gesture = JdomNote3D.unmarshal(sample.getChild(JdomNote3D.ROOT_TAG));
      }

      final String name = sample.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = sample.getAttributeValue(UUID_ATTRIBUTE);
      final GestureSample3D gestureSample = new GestureSample3D(name, gesture);
      gestureSample.setId(uuid);
      return gestureSample;

   } // unmarshal

}
