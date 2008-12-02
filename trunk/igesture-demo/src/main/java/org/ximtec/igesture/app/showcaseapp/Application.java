/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
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


package org.ximtec.igesture.app.showcaseapp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.util.Constant;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.app.showcaseapp.descriptor.ArrowDescriptor;
import org.ximtec.igesture.app.showcaseapp.descriptor.LineDescriptor;
import org.ximtec.igesture.app.showcaseapp.descriptor.RectangleDescriptor;
import org.ximtec.igesture.app.showcaseapp.descriptor.TriangleDescriptor;
import org.ximtec.igesture.app.showcaseapp.eventhandler.DeleteEventHandler;
import org.ximtec.igesture.app.showcaseapp.eventhandler.DrawEventHandler;
import org.ximtec.igesture.app.showcaseapp.eventhandler.RejectEventHandler;
import org.ximtec.igesture.app.showcaseapp.eventhandler.StyleEventHandler;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.DigitalDescriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.GestureActionManager;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureEventListener;
import org.ximtec.igesture.io.mouseclient.MouseReader;
import org.ximtec.igesture.util.XMLTool;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Application implements GestureEventListener {

   private static final Logger LOGGER = Logger.getLogger(Application.class
         .getName());

   private Recogniser recogniser;

   private GestureDevice<Note, Point> client;

   private JFrame frame;

   private BufferedImage bufferedImage;


   public Application() {
      initialiseGUI();
      initGestures();
   }


   private void initGestures() {
      Configuration configuration = XMLTool.importConfiguration(ClassLoader
            .getSystemResourceAsStream("rubineconfiguration.xml"));
      GestureSet gestureSet = XMLTool.importGestureSet(ClassLoader
            .getSystemResourceAsStream("demogestures.xml"));
      GestureActionManager eventManager = new GestureActionManager();
      eventManager.registerRejectEvent(new RejectEventHandler());
      Style style = new Style();
      StyleEventHandler styleEventHandler = new StyleEventHandler(style);
      Graphics2D graphic = (Graphics2D)bufferedImage.getGraphics();
      DrawEventHandler drawEventHandler = new DrawEventHandler(graphic, style);
      gestureSet.getGestureClass("Rectangle").addDescriptor(
            DigitalDescriptor.class, new RectangleDescriptor());
      gestureSet.getGestureClass("LeftRight").addDescriptor(
            DigitalDescriptor.class, new LineDescriptor());
      gestureSet.getGestureClass("Triangle").addDescriptor(
            DigitalDescriptor.class, new TriangleDescriptor());
      gestureSet.getGestureClass("Arrow").addDescriptor(DigitalDescriptor.class,
            new ArrowDescriptor());
      eventManager.registerEventHandler("Rectangle", drawEventHandler);
      eventManager.registerEventHandler("LeftRight", drawEventHandler);
      eventManager.registerEventHandler("Triangle", drawEventHandler);
      eventManager.registerEventHandler("Arrow", drawEventHandler);
      eventManager.registerEventHandler("Delete",
            new DeleteEventHandler(graphic));
      eventManager.registerEventHandler("Red", styleEventHandler);
      eventManager.registerEventHandler("Black", styleEventHandler);
      eventManager.registerEventHandler("Yellow", styleEventHandler);
      eventManager.registerEventHandler("Thin", styleEventHandler);
      eventManager.registerEventHandler("Fat", styleEventHandler);
      configuration.addGestureSet(gestureSet);
      

      try {
         recogniser = new Recogniser(configuration);
         recogniser.addGestureHandler(eventManager);
      }
      catch (AlgorithmException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      client = new MouseReader();
      client.init();
      client.addGestureHandler(this);

   } // initGestures


   private void initialiseGUI() {
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(440, 620);
      bufferedImage = new BufferedImage(420, 600, BufferedImage.TYPE_INT_ARGB);
      bufferedImage.getGraphics().setColor(Color.WHITE);
      bufferedImage.getGraphics().fillRect(0, 0, 420, 600);
      bufferedImage.getGraphics().setColor(Color.BLACK);
      JLabel label = new JLabel();
      label.setSize(420, 620);
      label.setIcon(new ImageIcon(bufferedImage));
      frame.add(label);
      frame.setVisible(true);
   } // initialiseGUI




   public static void main(String[] args) {
      new Application();
   }


   @Override
   public void handleChunks(List< ? > chunks) {
      // TODO Auto-generated method stub

   }


   @Override
   public void handleGesture(Gesture< ? > gesture) {

      if (gesture.getGesture() instanceof Note) {
         Note note = (Note)gesture.getGesture();
         Note clone = (Note)note.clone();
         clone.scale(2);

         recogniser.recognise(clone);
         frame.repaint();
      }
   }

}
