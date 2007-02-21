/*
 * @(#)MagicommPenSocket.java    1.0   Apr 13, 2006
 *
 * Author       :   Beat Signer, signer@inf.ethz.ch
 *
 * Purpose      :   Magicomm G303 pen receiving information from the
 *                  streaming server.
 *                  
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Apr 13, 2005     bsigner     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.ipaper.anoto.r41.message.AnotoMessage;
import org.ximtec.ipaper.anoto.r41.message.CoordinateMessage;
import org.ximtec.ipaper.anoto.r41.message.NewSessionMessage;
import org.ximtec.ipaper.anoto.r41.message.PenUpMessage;
import org.ximtec.ipaper.io.AbstractInputDevice;
import org.ximtec.ipaper.io.InputDeviceEvent;
import org.ximtec.ipaper.io.MagicommPenEvent;


/**
 * Magicomm G303 pen reading information from the streaming server.
 * 
 * @version 1.0 Apr 2006
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MagicommPenSocketModified extends AbstractInputDevice implements
      ButtonDevice {

   HashSet<ButtonDeviceEventListener> penUpListener = new HashSet<ButtonDeviceEventListener>();

   private static final Logger LOGGER = Logger
         .getLogger(MagicommPenSocketModified.class.getName());

   /** Default name of the device */
   private static final String DEFAULT_DEVICE_NAME = "magicomm G303";

   /** Default port number of the device */
   private static final int DEFAULT_PORT = 1020;

   /**
    * If we assume a max of 100 positions per second it is ok to check for new
    * positions every 10ms, i.e. after a check the thread can sleep for 10ms.
    */
   private static final int THREAD_SLEEP_TIME = 10;

   private String deviceName;

   private int port;


   /**
    * Constructs a new MagicommPen (with default parameters).
    */
   public MagicommPenSocketModified() {
      this(DEFAULT_PORT, DEFAULT_DEVICE_NAME);
   }


   /**
    * Constructs a new MagicommPen.
    * 
    * @param port the port on which the MagicommPen is listening.
    */
   public MagicommPenSocketModified(int port) {
      this(port, DEFAULT_DEVICE_NAME);
   }


   /**
    * Constructs a new MagicommPen with socket connection.
    * 
    * @param port the port on which the MagicommPen is listening.
    * @param deviceName the device's name (label).
    */
   public MagicommPenSocketModified(int port, String deviceName) {
      this.port = port;
      this.deviceName = deviceName;
      init();
   }


   /**
    * Initialises the pen device.
    */
   private void init() {
      try {
         final ServerSocket server = new ServerSocket(port);
         final Socket socket = server.accept(); // TODO: make concurrent
         final BufferedInputStream input = new BufferedInputStream(socket
               .getInputStream());
         final Thread socketHandler = new Thread(new MagicommSocketHandler(
               socket, input, this));
         socketHandler.start();
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      LOGGER.info(deviceName + " initialised: port = " + port);
   } // init


   private synchronized void handleData(InputStream input) {
      final byte[] lengthByte = new byte[2];

      try {
         final int messageType = input.read() & 0xFF;
         input.read(lengthByte, 0, 2);
         final int length = Long.valueOf(
               (lengthByte[1] & 0xFF) + (lengthByte[0] & 0xFF) * Constant.EXP_8)
               .intValue();
         final byte[] data = new byte[length];
         input.read(data, 0, length);

         switch (messageType) {
            case AnotoMessage.TYPE_NEW_SESSION:
               final NewSessionMessage newSessionMessage = new NewSessionMessage(
                     data);
               LOGGER.log(Level.INFO, newSessionMessage.toString());
               break;
            case AnotoMessage.TYPE_COORDINATE:
               final CoordinateMessage coordinateMessage = new CoordinateMessage(
                     data);
               fireInputDeviceEvent(new MagicommPenEvent(coordinateMessage));
               break;
            case AnotoMessage.TYPE_PEN_UP:
               final PenUpMessage penupMessage = new PenUpMessage(data);
               firePenUpEvent(new InputDeviceEvent() {

                  public long getTimestamp() {
                     return System.currentTimeMillis();
                  }
               });
               System.out.println(penupMessage.toString());
               break;
            default:
               System.out.println(">>> MESSAGE_TYPE = " + messageType);
               break;
         }

      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

   } // handleData


   /**
    * Creates one or more Magicomm G303 pens.
    * 
    * @param ports the com port numbers of the pens to be created. The port
    *           numbers have to be separated by a space (e.g. 'COM1 COM5').
    * @return a list containing the created Magicomm G303 pens.
    */
   public static List<MagicommPenSocketModified> createPens(String ports) {
      final List<MagicommPenSocketModified> pens = new Vector<MagicommPenSocketModified>();
      final StringTokenizer tokens = new StringTokenizer(ports);

      while (tokens.hasMoreTokens()) {
         pens.add(new MagicommPenSocketModified(Integer.parseInt(tokens
               .nextToken())));
      }

      return pens;
   } // createPens

   private class MagicommSocketHandler implements Runnable {

      private Socket socket;

      private InputStream input;

      private MagicommPenSocketModified pen;


      public MagicommSocketHandler(Socket socket, InputStream input,
            MagicommPenSocketModified pen) {
         this.socket = socket;
         this.input = input;
         this.pen = pen;
      }


      public void run() {
         while (socket.isBound()) {

            try {

               if (input.available() > 0) {
                  pen.handleData(input);
               }

               try {
                  Thread.sleep(THREAD_SLEEP_TIME);
               }
               catch (final InterruptedException e) {
               }

            }
            catch (final IOException e) {
               LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
            }

         }

         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>><SOCKET CLOSED");
      } // run

   } // MagicommSocketHandler


   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      penUpListener.add(listener);

   }


   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      penUpListener.remove(listener);
   }


   protected synchronized void firePenUpEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : penUpListener) {
         listener.handleButtonPressedEvent(event);
      }
   } //

}