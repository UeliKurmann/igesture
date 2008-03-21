package org.ximtec.igesture.tool.explorer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeView;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class NodeInfoImpl implements NodeInfo {

  private static final Logger LOG = Logger.getLogger(NodeInfoImpl.class);
  
  private String propertyName;
  private String childList;
  private Class<? extends ExplorerTreeView> view;
  private Class<? extends Object> type;

  private List<Class<? extends BasicAction>> popupActions;

  public NodeInfoImpl(Class<? extends Object> type, String propertyName,
      String childList, Class<? extends ExplorerTreeView> view,
      List<Class<? extends BasicAction>> popupActions) {

    this.type = type;
    this.propertyName = propertyName;
    this.childList = childList;
    this.view = view;
    this.popupActions = popupActions;

  }

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
            result.addAll((Collection<?>) o);
          } else if (o instanceof Map) {
            result.addAll(((Map<?, ?>) o).values());
          } else {
            result.add(o);
          }
        } catch (SecurityException e) {
          LOG.error("Reflection Exception.",e);
        } catch (NoSuchFieldException e) {
          LOG.error("Reflection Exception.",e);
        } catch (IllegalArgumentException e) {
          LOG.error("Reflection Exception.",e);
        } catch (IllegalAccessException e) {
          LOG.error("Reflection Exception.",e);
        }
      }
    }

    return result;
  }

  @Override
  public Icon getIcon() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getName(Object node) {
    if (propertyName != null) {
      try {
        return BeanUtils.getProperty(node, propertyName);
      } catch (IllegalAccessException e) {
        LOG.error("Reflection Exception.",e);
      } catch (InvocationTargetException e) {
        LOG.error("Reflection Exception.",e);
      } catch (NoSuchMethodException e) {
        LOG.error("Reflection Exception.",e);
      }
    }
    return type.getName();
  }

  @Override
  public String getTooltip() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public boolean isLeaf(Object object) {
    return getChildren(object).size() == 0;
  }

  @Override
  public ExplorerTreeView getView(Object node) {

    try {
      Constructor<? extends ExplorerTreeView> ctor = view.getConstructor(type);
      return ctor.newInstance(node);
    } catch (InstantiationException e) {
      LOG.warn("Reflection Exception.",e);
    } catch (IllegalAccessException e) {
      LOG.warn("Reflection Exception.",e);
    } catch (SecurityException e) {
      LOG.warn("Reflection Exception.",e);
    } catch (NoSuchMethodException e) {
      LOG.warn("Reflection Exception.",e);
    } catch (IllegalArgumentException e) {
      LOG.warn("Reflection Exception.",e);
    } catch (InvocationTargetException e) {
      LOG.warn("Reflection Exception.",e);
    }
    return null;
  }

  @Override
  public JPopupMenu getPopupMenu(TreePath node) {

    JPopupMenu popupMenu = new JPopupMenu();
    if (popupActions != null) {
      for (Class<? extends BasicAction> actionClass : popupActions) {
        try {
          BasicAction action = actionClass.getConstructor(TreePath.class).newInstance(node);
          popupMenu.add(action);
        } catch (InstantiationException e) {
          LOG.error("Reflection Exception.",e);
        } catch (IllegalAccessException e) {
          LOG.error("Reflection Exception.",e);
        } catch (SecurityException e) {
          LOG.error("Reflection Exception.",e);
        } catch (NoSuchMethodException e) {
          LOG.error("Reflection Exception.",e);
        } catch (IllegalArgumentException e) {
          LOG.error("Reflection Exception.",e);
        } catch (InvocationTargetException e) {
          LOG.error("Reflection Exception.",e);
        }
      }
    }
    return popupMenu;
  }
}
