/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
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
 * 07.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.explorer;

import hacks.IconLoader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.jdom.Element;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeView;


/**
 * TODO: untested code
 * Comment
 * @version 1.0 07.04.2008
 * @author Ueli Kurmann
 */
public class NodeInfoImplJdom extends Element {

   private static final String ACTION = "action";
   private static final String ACTIONS = "actions";
   private static final String TYPE = "type";
   private static final String VIEW = "view";
   private static final String ICON = "icon";
   private static final String CHILD = "child";
   private static final String PROPERTY_NAME = "propertyName";


   public NodeInfoImplJdom(NodeInfoImpl nodeInfo) {

   }


   @SuppressWarnings("unchecked")
   public static NodeInfoImpl unmarshall(Element element) {

      String propertyName = element.getChildText(PROPERTY_NAME);

      StringBuilder childrenList = new StringBuilder();
      for (Element childElement : ((List<Element>)element.getChildren(CHILD))) {
         childrenList.append(childElement.getText());
         childrenList.append(NodeInfoImpl.CHILD_DELIMITER);
      }

      String icon = element.getChildText(ICON);
      String view = element.getChildText(VIEW);
      String type = element.getChildText(TYPE);

      try {
         Class< ? extends ExplorerTreeView> viewClass = (Class< ? extends ExplorerTreeView>)Class.forName(view);
         Class< ? > typeClass = Class.forName(type);
         Icon nodeIcon = IconLoader.getIcon(icon);
         
         Element actions = element.getChild(ACTIONS);
         List<Class< ? extends BasicAction>> actionList = new ArrayList<Class< ? extends BasicAction>>();
         for(Element actionElement: ((List<Element>)actions.getChildren(ACTION))){
            actionList.add((Class< ? extends BasicAction>)Class.forName(actionElement.getText()));
         }
         
         return new NodeInfoImpl(typeClass, propertyName, childrenList.toString(), viewClass, actionList,
               nodeIcon);
      }
      catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      return null;
   }

}
