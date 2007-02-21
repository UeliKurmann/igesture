package org.ximtec.igesture.tool.frame.batch.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JTextField;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.sigtec.graphix.BasicAction;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.ScrollableList;
import org.ximtec.igesture.tool.utils.SwingTool;
import org.ximtec.igesture.util.XMLTools;

public class ActionStartBatch extends BasicAction {

	private JTextField config;
	private JTextField result;
	private ScrollableList gestureSets;
	private ScrollableList testSets;

	public ActionStartBatch(JTextField config, JTextField result, ScrollableList gestureSets, ScrollableList testSets) {
		super(GestureConstants.COMMON_OK, SwingTool.getGuiBundle());
		this.config = config;
		this.result = result;
		this.gestureSets = gestureSets;
		this.testSets = testSets;
	}


	public void actionPerformed(ActionEvent arg0) {
		File configFile = new File(config.getText());
		File resultFile = new File(result.getText());
		GestureSet gestureSet = (GestureSet)gestureSets.getSelectedValue();
		TestSet testSet = (TestSet)testSets.getSelectedValue();
		
		if(configFile.exists() && resultFile != null && gestureSet != null && testSet != null){
			BatchProcess batchProcess = new BatchProcess(configFile);
			batchProcess.addGestureSet(gestureSet);
			batchProcess.setTestSet(testSet);
			BatchResultSet brs = batchProcess.run();
			String xmlDocument = XMLTools.exportBatchResultSet(brs);
			try {
				String htmlPage = XMLTools.transform(xmlDocument, ActionStartBatch.class.getClassLoader().getResourceAsStream("xml/batch.xsl"));
				FileHandler.writeFile(resultFile.getPath(), htmlPage);
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();
			}
		}
	}
}
