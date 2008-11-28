/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, ueli.kurmann@bbv.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 28.11.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Key definition. This enum is used to simluate keyboard input.
 * @version 1.0 28.11.2008
 * @author Ueli Kurmann
 */
public enum Key {
   
   
   
   SHIFT(0x10), CTRL(0x11), ALT(0x12), DELETE(0x2E), CLEAR(0x0C), BACKSPACE(0x08), TAB(
         0x09), ENTER(0x0D), ESCAPE(0x1B), SPACE(0x20),

   VK_0(0x30), VK_1(0x31), VK_2(0x32), VK_3(0x33), VK_4(0x34), VK_5(0x35), VK_6(
         0x36), VK_7(0x37), VK_8(0x38), VK_9(0x39),

   A(0X41), B(0X42), C(0X43), D(0X44), E(0X45), F(0X46), G(0X47), H(0X48), I(
         0X49), J(0X4A), K(0X4B), L(0X4C), M(0X4D), N(0X4E), O(0X4F), P(0X50), Q(
         0X51), R(0X52), S(0X53), T(0X54), U(0X55), V(0X56), W(0X57), X(0X58), Y(
         0X59), Z(0X5A),

   F1(0x70), F2(0x71), F3(0x72), F4(0x73), F5(0x74), F6(0x75), F7(0x76), F8(0x77), F9(
         0x78), F10(0x79), F11(0x7A), F12(0x7B),

   LEFT(0x25), UP(0x26), RIGHT(0x27), DOWN(0x28), PAGE_UP(0x21), PAGE_DOWN(0x22), END(
         0x23), HOME(0x24),

   KEY_DOWN(0x0), KEY_UP(0x2);

   private static final String REGEX = "\\+";
   private static final String KEY_NOT_RECOGNISED = "Key not recognised!";
   
   private int keyId;

   /**
    * Private constructor to initialize the enum. 
    * @param keyId
    */
   private Key(int keyId) {
      this.keyId = keyId;
   }

   /**
    * Returns the integer representation of the key.
    * @return the integer representation of the key.
    */
   public int getKeyId() {
      return keyId;
   }
   
   /**
    * Parses a string list of keys
    * Delimiter: +
    * E.g.: ALT + TAB  
    * @param keys
    * @return
    */
   public static Key[] parseKeyList(String keys) throws IllegalArgumentException {
      List<Key> codes = new ArrayList<Key>();

      for (String keyAsString : keys.split(REGEX)) {
         
         try {
            keyAsString = keyAsString.trim().toUpperCase();
            Key key = Key.valueOf(keyAsString);
            codes.add(key);
         }
         catch (Exception e) {
            throw new IllegalArgumentException(KEY_NOT_RECOGNISED);
         }
         
      }

     return codes.toArray(new Key[0]);
   } // parseKeyList

}
