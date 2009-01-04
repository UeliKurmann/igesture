/*
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :   Constructs an XML representation of a Point3D object.
 *                  The 'timestamp' element is optional.
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

import org.jdom.Element;
import org.sigtec.jdom.element.JdomDoubleElement;
import org.sigtec.jdom.element.JdomLongElement;


/**
 * Constructs an XML representation for a Point3D object. The 'timestamp' element is optional.
 * @version 1.0, Jan 2009
 * @author Arthur Vogels, arthur.vogels@gmail.com
 */
public class JdomPoint3D extends Element {

   public static final String ROOT_TAG = "point3d";
   public static final String POINT_ELEMENT_X = "x";
   public static final String POINT_ELEMENT_Y = "y";
   public static final String POINT_ELEMENT_Z = "z";
   public static final String POINT_ELEMENT_TIMESTAMP = "timestamp";

   public JdomPoint3D(Point3D point) {
      super(ROOT_TAG);
      addContent(new JdomDoubleElement(POINT_ELEMENT_X, point.getX()));
      addContent(new JdomDoubleElement(POINT_ELEMENT_Y, point.getY()));
      addContent(new JdomDoubleElement(POINT_ELEMENT_Z, point.getZ()));

      if (point.hasTimeStamp()) {
         addContent(new JdomLongElement(POINT_ELEMENT_TIMESTAMP, point
               .getTimeStamp()));
      }

   } // JdomPoint3D


   public static Point3D unmarshal(Element point) {
      Point3D newPoint = new Point3D(Double.parseDouble(point
            .getChildText(POINT_ELEMENT_X)), Double.parseDouble(point
            .getChildText(POINT_ELEMENT_Y)), Double.parseDouble(point
                    .getChildText(POINT_ELEMENT_Z)), 0);
      setTimestamp(newPoint, point);
      return newPoint;
   } // unmarshal


   private static void setTimestamp(Point3D point, Element source) {
      Element timestamp = source.getChild(POINT_ELEMENT_TIMESTAMP);

      if (timestamp != null) {
         point.setTimeStamp(Long.parseLong(timestamp.getText()));
      }

   } // setTimestamp

}
