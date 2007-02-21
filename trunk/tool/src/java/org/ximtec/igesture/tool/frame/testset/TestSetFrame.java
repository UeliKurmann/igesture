/*
 * @(#)TestSetFrame.java	1.0   Nov 17, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   test set frame
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 4.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.testset;

import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.TestTab;
import org.ximtec.igesture.tool.event.CurrentGestureListener;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetAdd;
import org.ximtec.igesture.tool.utils.SwingTool;
import org.ximtec.igesture.util.GestureTools;


/**
 * Comment
 * 
 * @version 1.0 Nov 17, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class TestSetFrame extends BasicInternalFrame implements
      CurrentGestureListener {

   private GestureToolView mainView;

   private JLabel gestureImage;

   private JComboBox dropDownMenu;

   private TestTab testTab;

   private Note currentNote;


   public TestSetFrame(TestTab testTab) {
      super(GestureConstants.TESTSET_SELECTION_FRAME_KEY, SwingTool
            .getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = testTab.getMainView();
      this.testTab = testTab;
      this.mainView.getModel().addCurrentGestureListener(this);
      initialize();
   }


   public void initialize() {
      gestureImage = new JLabel();
      gestureImage.setSize(180, 180);
      gestureImage.setPreferredSize(new Dimension(180, 180));
      this.addComponent(gestureImage, SwingTool.createGridBagConstraint(0, 0));
      this.addComponent(createDropDownMenu(), SwingTool.createGridBagConstraint(
            0, 1));
      this.addComponent(SwingTool.createButton(new ActionTestSetAdd(testTab,
            this)), SwingTool.createGridBagConstraint(0, 2));
   }


   public void currentGestureChanged(EventObject event) {
      drawCurrentGesture();
   }


   private void drawCurrentGesture() {
      currentNote = mainView.getModel().getCurrentNote();
      final Note note = (Note) currentNote.clone();

      if (!note.getTraces().isEmpty()) {
         gestureImage.setIcon(new ImageIcon(GestureTools.createNoteImage(note,
               180, 180)));
      }
      this.repaint();
   }


   private JComboBox createDropDownMenu() {
      final GestureClass[] gestureClasses = mainView.getModel()
            .getGestureClasses().toArray(
                  new GestureClass[mainView.getModel().getGestureClasses()
                        .size() + 1]);
      gestureClasses[mainView.getModel().getGestureClasses().size()] = new GestureClass(
            "None");
      dropDownMenu = new JComboBox(gestureClasses);

      return dropDownMenu;
   }


   public GestureSample getGestureSample() {
      final String name = ((GestureClass) dropDownMenu.getSelectedItem())
            .getName();
      return new GestureSample(name, currentNote);
   }
}
