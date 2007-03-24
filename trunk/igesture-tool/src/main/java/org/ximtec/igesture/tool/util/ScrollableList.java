/*
 * @(#)ScrollableList.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Implementation of a scrollable list.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;


/**
 * Implementation of a scrollable list.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ScrollableList extends JScrollPane {

   private JList list;


   public ScrollableList(ListModel listModel, int width, int height) {
      super();
      setPreferredSize(new Dimension(width, height));
      setAutoscrolls(true);

      if (listModel == null) {
         list = new JList();
      }
      else {
         list = new JList(listModel);
      }

      list.setVisible(true);
      list.setBorder(new LineBorder(Color.BLACK));
      list.setAutoscrolls(true);
      setViewportView(list);
   }


   public JList getList() {
      return list;
   } // getList


   public int getSelectedIndex() {
      return list.getSelectedIndex();
   }


   public Object getSelectedValue() {
      return list.getSelectedValue();
   } // getSelectedValue


   public void setModel(ListModel model) {
      list.setModel(model);
   } // setModel
}
