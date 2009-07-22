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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testbench;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.testbench.panel.ConfigurationPanel;

public class TestbenchController extends DefaultController {

	private static final Logger LOGGER = Logger
			.getLogger(TestbenchController.class.getName());

	private TestbenchView testbenchView;

	private MainModel mainModel; 

	private ExplorerTreeController explorerTreeController;

	public final static String CMD_RECOGNIZE = "recognize";

	public TestbenchController(Controller parentController) {
		super(parentController);
		
		mainModel = getLocator().getService(
				MainModel.IDENTIFIER, MainModel.class);
		
		initController();
	}

	private void initController() {
		testbenchView = new TestbenchView(this);

		ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel
				.getAlgorithmList(), NodeInfoFactory.createTestBenchNodeInfo(this));
		explorerTreeController = new ExplorerTreeController(this, testbenchView,
				explorerModel);

		addController(explorerTreeController);
	}

	@Override
	public TabbedView getView() {
		return testbenchView;
	}

	@ExecCmd(name = CMD_RECOGNIZE)
	protected void executeRecognize(Command command) {
		try {
			Algorithm algorithm = AlgorithmFactory
					.createAlgorithm((Configuration) command.getSender());

			// FIXME General Implementation. Add logic to getGesture. Get
			// gesture
			// should ensure that only valid gestures
			// (SampleGesture.numberOfPoints
			// > 5)
			// are provided.

			GestureDevice<?, ?> gestureDevice = getLocator().getService(SwingMouseReaderService.IDENTIFIER,
							GestureDevice.class);

			if (gestureDevice.getGesture() != null) {

				Gesture<?> gesture = gestureDevice.getGesture();
				ResultSet resultSet = algorithm.recognise(gesture);

				if (explorerTreeController.getExplorerTreeView() instanceof ConfigurationPanel) {
					ConfigurationPanel panel = (ConfigurationPanel) explorerTreeController
							.getExplorerTreeView();
					panel.setResultList(resultSet.getResults());
				}
			}

		} catch (AlgorithmException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		LOGGER.info("PropertyChange");

		super.propertyChange(evt);
	}
}
