package org.ximtec.igesture.tool;

import org.ximtec.igesture.tool.frame.batch.BatchFrame;

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
