/*
 * @(#)$Id:$
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

   public NodeInfoImplJdom(NodeInfoImpl nodeInfo) {

   }


   public static NodeInfoImpl unmarshall(Element element) {

      String propertyName = element.getChildText("propertyName");

      StringBuilder childrenList = new StringBuilder();
      for (Element childElement : ((List<Element>)element.getChildren("child"))) {
         childrenList.append(childElement.getText());
         childrenList.append(NodeInfoImpl.CHILD_DELIMITER);
      }

      String icon = element.getChildText("icon");
      String view = element.getChildText("view");
      String type = element.getChildText("type");

      try {
         Class< ? extends ExplorerTreeView> viewClass = (Class< ? extends ExplorerTreeView>)Class.forName(view);
         Class< ? > typeClass = Class.forName(type);
         Icon nodeIcon = IconLoader.getIcon(icon);
         
         Element actions = element.getChild("actions");
         List<Class< ? extends BasicAction>> actionList = new ArrayList<Class< ? extends BasicAction>>();
         for(Element actionElement: ((List<Element>)actions.getChildren("action"))){
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
