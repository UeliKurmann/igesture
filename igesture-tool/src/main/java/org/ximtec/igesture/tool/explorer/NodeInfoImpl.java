/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import org.apache.commons.beanutils.BeanUtils;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeView;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;


/**
 * Implementation of the NodeInfo interface. Reflection and dynamic class loading
 * is used to get children, views, etc.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class NodeInfoImpl implements NodeInfo {

   private static final Logger LOG = Logger.getLogger(NodeInfoImpl.class
         .getName());

   private String propertyName;
   private String childList;
   private Class< ? extends ExplorerTreeView> view;
   private Class< ? extends Object> type;

   private List<Class< ? extends BasicAction>> popupActions;


   /**
    * Constructor
    * @param type the type (class) of the node
    * @param propertyName the name of the property containing the name of the
    *            node.
    * @param childList a list of properties (as ";" separated strings) which
    *            should be used as children.
    * @param view the view belonging the node. The view MUST HAVE a constructor
    *            with the node instance as the only parameter.
    * @param popupActions a list of actions.
    */
   public NodeInfoImpl(Class< ? extends Object> type, String propertyName,
         String childList, Class< ? extends ExplorerTreeView> view,
         List<Class< ? extends BasicAction>> popupActions) {

      this.type = type;
      this.propertyName = propertyName;
      this.childList = childList;
      this.view = view;
      this.popupActions = popupActions;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getChildren(java.lang.Object)
    */
   @Override
   public List<Object> getChildren(Object node) {
      List<Object> result = new ArrayList<Object>();
      if (childList != null) {
         for (String name : childList.split(";")) {

            try {
               Field field = type.getDeclaredField(name);
               field.setAccessible(true);
               Object o = field.get(node);
               if (o instanceof Collection) {
                  result.addAll((Collection< ? >)o);
               }
               else if (o instanceof Map) {
                  result.addAll(((Map< ? , ? >)o).values());
               }
               else {
                  result.add(o);
               }
            }
            catch (SecurityException e) {

               LOG.log(Level.SEVERE, "Can't access children.", e);
            }
            catch (NoSuchFieldException e) {
               LOG.log(Level.SEVERE, "Can't access children.", e);
            }
            catch (IllegalArgumentException e) {
               LOG.log(Level.SEVERE, "Can't access children.", e);
            }
            catch (IllegalAccessException e) {
               LOG.log(Level.SEVERE, "Can't access children.", e);
            }
         }
      }

      return result;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getIcon()
    */
   @Override
   public Icon getIcon() {
      // TODO Auto-generated method stub
      return null;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getName(java.lang.Object)
    */
   @Override
   public String getName(Object node) {
      if (propertyName != null) {
         try {
            return BeanUtils.getProperty(node, propertyName);
         }
         catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "Can't get the node name.", e);
         }
         catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, "Can't get the node name.", e);
         }
         catch (NoSuchMethodException e) {
            LOG.log(Level.SEVERE, "Can't get the node name.", e);
         }
      }
      return type.getName();
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getTooltip()
    */
   @Override
   public String getTooltip() {
      // TODO Auto-generated method stub
      return null;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getType()
    */
   @Override
   public Class< ? > getType() {
      return type;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#isLeaf(java.lang.Object)
    */
   @Override
   public boolean isLeaf(Object object) {
      return getChildren(object).size() == 0;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getView(java.lang.Object)
    */
   @Override
   public ExplorerTreeView getView(Object node) {

      try {
         Constructor< ? extends ExplorerTreeView> ctor = view
               .getConstructor(type);
         return ctor.newInstance(node);
      }
      catch (InstantiationException e) {
         LOG.log(Level.SEVERE, "Can't create the view.", e);
      }
      catch (IllegalAccessException e) {
         LOG.log(Level.SEVERE, "Can't create the view.", e);
      }
      catch (SecurityException e) {
         LOG.log(Level.SEVERE, "Can't create the view.", e);
      }
      catch (NoSuchMethodException e) {
         LOG.log(Level.SEVERE, "Can't create the view.", e);
      }
      catch (IllegalArgumentException e) {
         LOG.log(Level.SEVERE, "Can't create the view.", e);
      }
      catch (InvocationTargetException e) {
         LOG.log(Level.SEVERE, "Can't create the view.", e);
      }
      return null;
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.explorer.core.NodeInfo#getPopupMenu(javax.swing.tree.TreePath)
    */
   @Override
   public JPopupMenu getPopupMenu(TreePath node) {

      JPopupMenu popupMenu = new JPopupMenu();
      if (popupActions != null) {
         for (Class< ? extends BasicAction> actionClass : popupActions) {
            try {
               BasicAction action = actionClass.getConstructor(TreePath.class)
                     .newInstance(node);
               popupMenu.add(action);
            }
            catch (InstantiationException e) {
               LOG.log(Level.SEVERE, "Can't create Popup Menu.", e);
            }
            catch (IllegalAccessException e) {
               LOG.log(Level.SEVERE, "Can't create Popup Menu.", e);
            }
            catch (SecurityException e) {
               LOG.log(Level.SEVERE, "Can't create Popup Menu.", e);
            }
            catch (NoSuchMethodException e) {
               LOG.log(Level.SEVERE, "Can't create Popup Menu.", e);
            }
            catch (IllegalArgumentException e) {
               LOG.log(Level.SEVERE, "Can't create Popup Menu.", e);
            }
            catch (InvocationTargetException e) {
               LOG.log(Level.SEVERE, "Can't create Popup Menu.", e);
            }
         }
      }
      return popupMenu;
   }
}
