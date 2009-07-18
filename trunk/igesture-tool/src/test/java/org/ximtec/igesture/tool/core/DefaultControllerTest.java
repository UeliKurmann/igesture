package org.ximtec.igesture.tool.core;

import javax.swing.Icon;
import javax.swing.JComponent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.ximtec.igesture.tool.locator.Locator;

public class DefaultControllerTest {

  private String tabNameParent = "Tab1";
  private String tabNameChild = "Tab2";
  private static final String commandParent = "cmd1";
  private static final String commandChild = "cmd2";
  private int checker = 0;
  private static final int checkParent = 1;
  private static final int checkChild = 2;

  private DefaultController parentController;

  private DefaultController childController;

  @Before
  public void setUp() {

    parentController = new DefaultController(null) {

      @Override
      public TabbedView getView() {

        return new TabbedView() {

          @Override
          public Icon getIcon() {
            return null;
          }

          @Override
          public JComponent getPane() {
            return null;
          }

          @Override
          public String getTabName() {
            return tabNameParent;
          }

        };
      }

      @SuppressWarnings("unused")
      @ExecCmd(name = commandParent)
      public void cmd1() {
        checker = checkParent;
      }

    };

    childController = new DefaultController(null) {

      @Override
      public TabbedView getView() {

        return new TabbedView() {

          @Override
          public Icon getIcon() {
            return null;
          }

          @Override
          public JComponent getPane() {
            return null;
          }

          @Override
          public String getTabName() {
            return tabNameChild;
          }

        };
      }

      @SuppressWarnings("unused")
      @ExecCmd(name = commandChild)
      public void cmd2() {
        checker = checkChild;
      }

    };

    parentController.addController(childController);
  }

  @Test
  public void testGetView() {
    TabbedView view = parentController.getView();
    Assert.assertNotNull(view);
    Assert.assertEquals(tabNameParent, view.getTabName());
  }

  @Test
  public void testControllerHierarchy() {
    Assert.assertNull(parentController.getParent());
    Assert.assertTrue(parentController.isRoot());
    Assert.assertEquals(parentController, childController.getParent());
    Assert.assertTrue(!childController.isRoot());
    Assert.assertEquals(1, parentController.getControllers().size());
    Assert.assertEquals(childController, parentController.getControllers().get(0));

    parentController.removeAllControllers();
    Assert.assertEquals(0, parentController.getControllers().size());
  }

  @Test
  public void testLocator() {
    Assert.assertNull(parentController.getLocator());
    Assert.assertNull(childController.getLocator());

    Locator locator = new Locator();
    parentController.setLocator(locator);
    Assert.assertEquals(locator, parentController.getLocator());
    Assert.assertEquals(locator, childController.getLocator());
  }

  @Test
  public void executeTest() {
    checker = 0;
    Assert.assertEquals(0, checker);
    parentController.execute(new Command(commandParent));
    Assert.assertEquals(checkParent, checker);

    checker = 0;
    Assert.assertEquals(0, checker);
    childController.execute(new Command(commandParent));
    Assert.assertEquals(checkParent, checker);

    checker = 0;
    Assert.assertEquals(0, checker);
    childController.execute(new Command(commandChild));
    Assert.assertEquals(checkChild, checker);

    checker = 0;
    Assert.assertEquals(0, checker);
    parentController.execute(new Command(commandChild));
    Assert.assertEquals(0, checker);

  }

}
