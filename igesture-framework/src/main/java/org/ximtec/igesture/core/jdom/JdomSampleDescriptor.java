/*
 * @(#)JdomSampleDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML support for the SampleDescriptor class.
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
import org.ximtec.igesture.core.SampleDescriptor;


/**
 * XML support for the SampleDescriptor class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomSampleDescriptor extends Element {

   public static final String ROOT_TAG = "descriptor";

   public static final String TYPE_ATTRIBUTE = "type";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomSampleDescriptor(SampleDescriptor descriptor) {
      super(ROOT_TAG);
      setAttribute(TYPE_ATTRIBUTE, descriptor.getType().getName());
      setAttribute(UUID_ATTRIBUTE, descriptor.getID());

      for (final GestureSample sample : descriptor.getSamples()) {
         addContent(new JdomGestureSample(sample));
      }

   }


   @SuppressWarnings("unchecked")
   public static Object unmarshal(Element descriptor) {
      final String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
      final SampleDescriptor gestureDescriptor = new SampleDescriptor();
      gestureDescriptor.setID(uuid);

      for (final Element sample : (List<Element>)descriptor
            .getChildren(JdomGestureSample.ROOT_TAG)) {
         gestureDescriptor.addSample((GestureSample)JdomGestureSample
               .unmarshal(sample));
      }

      return gestureDescriptor;
   } // unmarshal

}
