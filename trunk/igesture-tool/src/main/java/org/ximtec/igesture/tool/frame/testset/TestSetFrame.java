/*
 * @(#)TestSetFrame.java	1.0   Nov 17, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Test set frame.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 17, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.frame.testset;

import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.TestTab;
import org.ximtec.igesture.tool.event.CurrentGestureListener;
import org.ximtec.igesture.tool.frame.testset.action.TestSetAddAction;
import org.ximtec.igesture.util.GestureTool;


/**
 * Test set frame.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetFrame extends BasicInternalFrame implements
      CurrentGestureListener {

   private GestureToolView mainView;

   private JLabel gestureImage;

   private JComboBox dropDownMenu;

   private TestTab testTab;

   private Note currentNote;


   public TestSetFrame(TestTab testTab) {
      super(GestureConstants.TESTSET_SELECTION_FRAME_KEY, GuiTool
            .getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = testTab.getMainView();
      this.testTab = testTab;
      this.mainView.getModel().addCurrentGestureListener(this);
      init();
   }


   public void init() {
      gestureImage = new JLabel();
      gestureImage.setSize(180, 180);
      gestureImage.setPreferredSize(new Dimension(180, 180));
      addComponent(gestureImage, SwingTool.createGridBagConstraint(0, 0));
      addComponent(createDropDownMenu(), SwingTool.createGridBagConstraint(0, 1));
      addComponent(GuiTool.createButton(new TestSetAddAction(testTab, this)),
            SwingTool.createGridBagConstraint(0, 2));
   } // init


   public void currentGestureChanged(EventObject event) {
      drawCurrentGesture();
   } // currentGestureChanged


   private void drawCurrentGesture() {
      currentNote = mainView.getModel().getCurrentNote();
      final Note note = (Note)currentNote.clone();

      if (!note.getTraces().isEmpty()) {
         gestureImage.setIcon(new ImageIcon(GestureTool.createNoteImage(note,
               180, 180)));
      }

      this.repaint();
   } // drawCurrentGesture


   private JComboBox createDropDownMenu() {
      final GestureClass[] gestureClasses = mainView.getModel()
            .getGestureClasses().toArray(
                  new GestureClass[mainView.getModel().getGestureClasses()
                        .size() + 1]);
      gestureClasses[mainView.getModel().getGestureClasses().size()] = new GestureClass(
            "None");
      dropDownMenu = new JComboBox(gestureClasses);
      return dropDownMenu;
   } // createDropDownMenu


   public GestureSample getGestureSample() {
      final String name = ((GestureClass)dropDownMenu.getSelectedItem())
            .getName();
      return new GestureSample(name, currentNote);
   } // getGestureSample

}
