/*
 * @(#)JdomGestureSamplen.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML support for GestureSample
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core.jdom;

import org.jdom.Element;
import org.sigtec.ink.Note;
import org.sigtec.ink.inkml.Ink;
import org.sigtec.ink.inkml.Transformer;
import org.sigtec.ink.inkml.jdom.JdomInk;
import org.sigtec.ink.jdom.JdomNote;
import org.ximtec.igesture.core.GestureSample;


public class JdomGestureSample extends Element {

   public static final String ROOT_TAG = "sample";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomGestureSample(GestureSample sample) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, sample.getName());
      setAttribute(UUID_ATTRIBUTE, sample.getID());
      addContent(new JdomNote(sample.getNote()));
   }


   public static Object unmarshal(Element sample) {
      Note note = null;

      if (sample.getChild(JdomNote.ROOT_TAG) != null) {
         note = JdomNote.unmarshal(sample.getChild(JdomNote.ROOT_TAG));
      }
      else if (sample.getChild(JdomInk.ROOT_TAG) != null) {
         final Ink ink = JdomInk.unmarshal(sample.getChild(JdomInk.ROOT_TAG));
         note = Transformer.fromInkML(ink);
      }

      final String name = sample.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = sample.getAttributeValue(UUID_ATTRIBUTE);
      final GestureSample gestureSample = new GestureSample(name, note);
      gestureSample.setID(uuid);
      return gestureSample;
   } // unmarshal

}
