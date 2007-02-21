package org.ximtec.igesture.tool;

import javax.swing.JLabel;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.tool.frame.batch.BatchFrame;
import org.ximtec.igesture.tool.utils.SwingTool;

public class BatchTab extends GestureTab {

	public BatchTab() {
		super();
	}

	@Override
	public void init(GestureToolView mainView) {
		super.init(mainView);
		
		getDesktopPane().add(new BatchFrame(mainView));
		
	}

	@Override
	public String getName() {
		return GestureConstants.GESTUREVIEW_TAB_BATCH;
	}
	
	

}
