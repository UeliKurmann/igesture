/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	XML support for the GestureClass class.
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
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.core.composite.jdom.JdomCompositeDescriptor;


/**
 * XML support for the GestureClass class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class JdomGestureClass extends Element {

   public static final String ROOT_TAG = "class";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomGestureClass(GestureClass gestureClass) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, gestureClass.getName());
      setAttribute(UUID_ATTRIBUTE, gestureClass.getId());

      for (final Descriptor descriptor : gestureClass.getDescriptors()) {

         if (descriptor instanceof SampleDescriptor) {
            addContent(new JdomSampleDescriptor((SampleDescriptor)descriptor));
         }
         else if(descriptor instanceof SampleDescriptor3D)
         {
        	addContent(new JdomSampleDescriptor3D((SampleDescriptor3D)descriptor));
         }
         else if (descriptor instanceof TextDescriptor) {
            addContent(new JdomTextDescriptor((TextDescriptor)descriptor));
         }
         else if(descriptor instanceof CompositeDescriptor) {
        	 addContent(new JdomCompositeDescriptor((CompositeDescriptor)descriptor));
         }
         else
         {
        	 throw new RuntimeException("Could not save this descriptor type: "+descriptor.getType().getName());
         }

      }

   }


   @SuppressWarnings("unchecked")
   public static GestureClass unmarshal(Element gestureClassElement) {
      final String name = gestureClassElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = gestureClassElement.getAttributeValue(UUID_ATTRIBUTE);
      final GestureClass gestureClass = new GestureClass(name);
      gestureClass.setId(uuid);

      for (final Element descriptorElement : (List<Element>)gestureClassElement
            .getChildren(JdomSampleDescriptor.ROOT_TAG)) {
         final SampleDescriptor sampleDescriptor = (SampleDescriptor)JdomSampleDescriptor
               .unmarshal(descriptorElement);
         gestureClass.addDescriptor(sampleDescriptor);
      }
      
      for (final Element descriptorElement : (List<Element>)gestureClassElement
              .getChildren(JdomSampleDescriptor3D.ROOT_TAG)) {
           final SampleDescriptor3D sampleDescriptor = (SampleDescriptor3D)JdomSampleDescriptor3D
                 .unmarshal(descriptorElement);
           gestureClass.addDescriptor(sampleDescriptor);
        }

      for (final Element descriptorElement : (List<Element>)gestureClassElement
            .getChildren(JdomTextDescriptor.ROOT_TAG)) {
         final TextDescriptor textDescriptor = (TextDescriptor)JdomTextDescriptor
               .unmarshal(descriptorElement);
         gestureClass.addDescriptor(textDescriptor);
      }

      for (final Element descriptorElement : (List<Element>)gestureClassElement
            .getChildren(JdomCompositeDescriptor.ROOT_TAG)) {
         final CompositeDescriptor compositeDescriptor = (CompositeDescriptor)JdomCompositeDescriptor
               .unmarshal(descriptorElement);
         gestureClass.addDescriptor(compositeDescriptor);
      }
      
      return gestureClass;
   } // unmarshal

}
