/*
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :   Constructs an XML representation of a RecordedGesture3D represented
 *                  by a list of Point3D's.
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

package org.ximtec.igesture.util;

import java.util.List;

import org.jdom.Element;


/**
 * Constructs an XML representation of a RecordedGesture3D represented by a list of Point3D's.
 * @version 1.0, Jan 2009
 * @author Arthur Vogels, arthur.vogels@gmail.com
 */
public class JdomRecordedGesture3D extends Element {

   public static final String ROOT_TAG = "recordedgesture3d";


   public JdomRecordedGesture3D(RecordedGesture3D gesture) {
      super(ROOT_TAG);

      for (Point3D point : gesture.getPoints()) {
         addContent(new JdomPoint3D(point));
      }
   }


   @SuppressWarnings("unchecked")
   public static RecordedGesture3D unmarshal(Element gesture) {
      RecordedGesture3D newGesture = new RecordedGesture3D();

      for (Element point : (List<Element>)gesture.getChildren(JdomPoint3D.ROOT_TAG)) {
         newGesture.add(JdomPoint3D.unmarshal(point));
      }

      return newGesture;
   } // unmarshal

}
