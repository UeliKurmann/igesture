/**
 * 
 */
package org.ximtec.igesture.tool.view.composite;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.tool.core.TabbedView;

/**
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class CompositeController extends DefaultController {

	private CompositeView compositeView;
	
	private boolean recognising = false;
	
	public static final String CMD_RESET = "reset";
	public static final String CMD_RECOGNISE = "recognise";
	public static final String CMD_ADD_RECOGNISER = "addRecogniser";
	
	/**
	 * @param parentController
	 */
	public CompositeController(Controller parentController) {
		super(parentController);
		
		compositeView = new CompositeView(this);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.core.Controller#getView()
	 */
	@Override
	public TabbedView getView() {
		return compositeView;
	}

	 @ExecCmd(name=CMD_RESET)
	 protected void reset()
	 {
		 compositeView.reset();
	 }
	 
	 @ExecCmd(name=CMD_RECOGNISE)
	 protected void recognise()
	 {
		 recognising = ! recognising; 
		 compositeView.recognise(recognising);
	 }
	 
	 @ExecCmd(name=CMD_ADD_RECOGNISER)
	 protected void addRecogniser()
	 {
		 compositeView.addRecogniser();
	 }
}
