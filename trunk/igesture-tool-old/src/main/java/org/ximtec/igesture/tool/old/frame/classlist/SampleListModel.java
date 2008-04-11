/*
 * @(#)SampleListModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Sample list model.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.classlist;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.util.GestureTool;


/**
 * Sample list model.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SampleListModel extends AbstractListModel {

   private Vector<JPanel> data;

   private HashMap<JPanel, GestureSample> mapping;

   private List<GestureSample> samples;


   public SampleListModel(List<GestureSample> samples) {
      data = new Vector<JPanel>();
      this.samples = samples;
      this.mapping = new HashMap<JPanel, GestureSample>();
      init();
   }


   private void init() {
      mapping.clear();
      data.removeAllElements();

      for (final GestureSample sample : samples) {
         final JPanel panel = createImage((Note)sample.getGesture().clone());
         mapping.put(panel, sample);
         data.add(panel);
      }

   } // init


   public GestureSample getSample(JPanel panel) {
      return mapping.get(panel);
   } // getSample


   public Object getElementAt(int i) {
      return data.get(i);
   } // getElementAt


   public int getSize() {
      return data.size();
   } // getSize


   private JPanel createImage(Note note) {
      final JPanel pane = new JPanel();

      if (!note.getTraces().isEmpty()) {
         pane.add(new JLabel(new ImageIcon(GestureTool.createNoteImage(note,
               100, 100))));
      }

      return pane;
   } // createImage

}
