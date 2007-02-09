

package org.ximtec.igesture.tool.frame.testset;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.TestSetListener;
import org.ximtec.igesture.tool.frame.classlist.SampleListModel;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetDelete;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetDeleteSample;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetExport;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetImport;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetNew;
import org.ximtec.igesture.tool.frame.testset.action.ActionTestSetOpen;
import org.ximtec.igesture.tool.utils.CustomCellRenderer;
import org.ximtec.igesture.tool.utils.ScrollableList;
import org.ximtec.igesture.tool.utils.SwingTool;


public class TestSetListFrame extends BasicInternalFrame implements
      TestSetListener {

   private GestureToolView mainView;

   private TestSet testSet;

   private ScrollableList scrollableList;

   private ScrollableList testSetList;

   private SampleListModel sampleListModel;


   public TestSetListFrame(GestureToolView mainView, TestSet testSet) {
      super(GestureConstants.TESTSET_VIEW_FRAME_KEY, SwingTool.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      mainView.getModel().addTestCaseListener(this);
      this.testSet = testSet;
      initialize();
   }


   private void initialize() {
      addComponent(createGestureList(), SwingTool.createGridBagConstraint(0, 1,
            2, 1));

      addComponent(createTestSetList(), SwingTool.createGridBagConstraint(0, 0,
            2, 1));

      this.setVisible(true);
   }


   private Component createGestureList() {
      scrollableList = new ScrollableList(null, 180, 420);
      loadGestureListData(testSet);
      scrollableList.getList().setCellRenderer(new CustomCellRenderer());
      scrollableList.getList().addMouseListener(getMouseListener());

      return scrollableList;
   }


   public void loadGestureListData(TestSet testSet) {
      this.testSet = testSet;
      sampleListModel = new SampleListModel(testSet.getSamples());
      scrollableList.setModel(sampleListModel);
   }


   private Component createTestSetList() {
      testSetList = new ScrollableList(null, 180, 80);
      loadTestSetData();
      testSetList.getList().addMouseListener(getMouseListenerTestSetList());
      return testSetList;
   }


   public void loadTestSetData() {
      final TestSetListModel listModel = new TestSetListModel(mainView
            .getModel());
      testSetList.setModel(listModel);
   }


   private MouseListener getMouseListener() {
      return (new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showPopupMenu(event);
            }
         }
      });
   }


   private MouseListener getMouseListenerTestSetList() {
      return (new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
               showTestSetPopupMenu(event);
            }
         }
      });
   }


   private void showPopupMenu(MouseEvent event) {
      final JPopupMenu popUp = new JPopupMenu();
      popUp.add(SwingTool.createMenuItem(new ActionTestSetDeleteSample(this)));
      popUp.show(event.getComponent(), event.getX(), event.getY());
   }


   private void showTestSetPopupMenu(MouseEvent event) {
      final JPopupMenu popUp = createGestureSetPopUpMenu();
      popUp.show(event.getComponent(), event.getX(), event.getY());
   }


   public void testSetChanged(EventObject event) {
      loadTestSetData();
      if (this.testSet == event.getSource()) {
         loadGestureListData(testSet);
      }
   }


   private JPopupMenu createGestureSetPopUpMenu() {
      final JPopupMenu menu = SwingTool.createPopupMenu(new Action[] {
            new ActionTestSetOpen(testSetList.getList(), this),
            new ActionTestSetNew(mainView),
            new ActionTestSetDelete(testSetList.getList(), mainView),
            new ActionTestSetExport(testSetList.getList()),
            new ActionTestSetImport(mainView) });
      return menu;
   }


   private GestureSample getSelectedSample() {
      final JPanel panel = (JPanel) scrollableList.getSelectedValue();
      return ((SampleListModel) scrollableList.getList().getModel())
            .getSample(panel);
   }


   public void deleteSelectedSample() {
      testSet.remove(getSelectedSample());
      mainView.getModel().updateDataObject(testSet);
      loadGestureListData(testSet);
   }


   public void addSample() {
      final GestureSample sample = new GestureSample(new Long(System
            .currentTimeMillis()).toString(), mainView.getModel()
            .getCurrentNote());

      testSet.add(sample);
      mainView.getModel().updateDataObject(testSet);
      loadGestureListData(testSet);
   }


   /**
    * Returns the TestSet
    * 
    * @return
    */
   public TestSet getTestSet() {
      return testSet;
   }
}
