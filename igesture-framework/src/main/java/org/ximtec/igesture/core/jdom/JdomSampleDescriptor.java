/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.core.SampleDescriptor;


/**
 * XML support for the SampleDescriptor class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class JdomSampleDescriptor extends Element {

   public static final String ROOT_TAG = "descriptor";

   public static final String TYPE_ATTRIBUTE = "type";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomSampleDescriptor(SampleDescriptor descriptor) {
      super(ROOT_TAG);
      setAttribute(TYPE_ATTRIBUTE, descriptor.getType().getName());
      setAttribute(UUID_ATTRIBUTE, descriptor.getId());

      for (final Gesture<Note> sample : descriptor.getSamples()) {
         addContent(new JdomGestureSample(sample));
      }

   }


   @SuppressWarnings("unchecked")
   public static Object unmarshal(Element descriptor) {
      String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
      SampleDescriptor gestureDescriptor = new SampleDescriptor();
      gestureDescriptor.setId(uuid);

      for (Element sample : (List<Element>)descriptor.getChildren(JdomGestureSample.ROOT_TAG)) {
         gestureDescriptor.addSample(JdomGestureSample.unmarshal(sample));
      }

      return gestureDescriptor;
   } // unmarshal

}
