package org.ximtec.igesture.tool.frame.batch;

import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.GestureSetListener;
import org.ximtec.igesture.tool.event.TestSetListener;
import org.ximtec.igesture.tool.frame.batch.action.ActionSelectConfigFile;
import org.ximtec.igesture.tool.frame.batch.action.ActionSelectResultFile;
import org.ximtec.igesture.tool.frame.batch.action.ActionStartBatch;
import org.ximtec.igesture.tool.utils.ScrollableList;
import org.ximtec.igesture.tool.utils.SimpleListModel;
import org.ximtec.igesture.tool.utils.SwingTool;

public class BatchFrame extends BasicInternalFrame implements
		GestureSetListener, TestSetListener {

	private GestureToolView mainView;
	private JTextField configPath;
	private JTextField outputPath;
	private ScrollableList gestureSets;
	private ScrollableList testSets;

	public BatchFrame(GestureToolView mainView) {
		super(GestureConstants.GESTUREVIEW_TAB_BATCH, SwingTool.getGuiBundle());
		this.mainView = mainView;
		this.setResizable(true);
		mainView.getModel().addGestureSetListener(this);
		mainView.getModel().addTestSetListener(this);
		init();
	}

	private void init() {
		this.setSize(400, 300);
		this.setVisible(true);
		JLabel configLabel = SwingTool.createLabel(GestureConstants.BATCHPROCESSING_CONFIG);
		JLabel gestureSetLabel = SwingTool.createLabel(GestureConstants.BATCHPROCESSING_GESTURESET);
		JLabel testSetLabel = SwingTool.createLabel(GestureConstants.BATCHPROCESSING_TESTSET);
		JLabel outputLabel = SwingTool.createLabel(GestureConstants.BATCHPROCESSING_OUTPUT);

		configPath = new JTextField();
		configPath.setPreferredSize(new Dimension(100, 20));

		outputPath = new JTextField();
		outputPath.setPreferredSize(new Dimension(100, 20));

		JButton configButton = SwingTool
				.createButton(new ActionSelectConfigFile(configPath));

		this.addComponent(configLabel, SwingTool.createGridBagConstraint(0, 0));
		this.addComponent(configPath, SwingTool.createGridBagConstraint(1, 0));
		this
				.addComponent(configButton, SwingTool.createGridBagConstraint(
						2, 0));

		gestureSets = createList(new SimpleListModel<GestureSet>(mainView
				.getModel().getGestureSets()));
		this.addComponent(gestureSetLabel, SwingTool.createGridBagConstraint(0,
				1));
		this.addComponent(gestureSets, SwingTool.createGridBagConstraint(1, 1,
				2, 1));

		testSets = createList(new SimpleListModel<TestSet>(mainView.getModel()
				.getTestSets()));
		this
				.addComponent(testSetLabel, SwingTool.createGridBagConstraint(
						0, 2));
		this.addComponent(testSets, SwingTool.createGridBagConstraint(1, 2, 2,
				1));

		JButton outputButton = SwingTool
				.createButton(new ActionSelectResultFile(outputPath));

		this.addComponent(outputLabel, SwingTool.createGridBagConstraint(0, 3));
		this.addComponent(outputPath, SwingTool.createGridBagConstraint(1, 3));
		this
				.addComponent(outputButton, SwingTool.createGridBagConstraint(
						2, 3));

		JButton startButton = SwingTool.createButton(new ActionStartBatch(
				configPath, outputPath, gestureSets, testSets));
		this.addComponent(startButton, SwingTool.createGridBagConstraint(0, 4));

	}

	private ScrollableList createList(
			SimpleListModel<? extends DataObject> listModel) {
		ScrollableList list = new ScrollableList(listModel, 200, 50);
		return list;
	}

	
	public void gestureSetChanged(EventObject event) {
		gestureSets.setModel(new SimpleListModel<GestureSet>(mainView
				.getModel().getGestureSets()));
	}

	public void testSetChanged(EventObject event) {
		testSets.setModel(new SimpleListModel<TestSet>(mainView.getModel()
				.getTestSets()));
	}
}
