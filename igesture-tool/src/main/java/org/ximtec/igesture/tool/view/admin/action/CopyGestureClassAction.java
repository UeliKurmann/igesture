/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.view.MainModel;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CopyGestureClassAction extends TreePathAction {

	public CopyGestureClassAction(Controller controller, TreePath treePath) {
		super(GestureConstants.GESTURE_CLASS_COPY, controller, treePath);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//current gesture set
		GestureSet currentGestureSet = (GestureSet) getTreePath().getParentPath().getLastPathComponent();
		//current gesture
		GestureClass gestureClass = (GestureClass) getTreePath().getLastPathComponent();
		
		//get other gesture set names
		List<GestureSet> sets = getController().getLocator().getService(MainModel.IDENTIFIER, MainModel.class).getGestureSets();
		Map<String, GestureSet> mapping = new HashMap<String,GestureSet>();
		for (GestureSet set : sets) {
			mapping.put(set.getName(), set);
		}
		mapping.remove(currentGestureSet.getName());
		//sort gesture set names
		ConcurrentSkipListSet<String> sortedList = new ConcurrentSkipListSet<String>();
		sortedList.addAll(mapping.keySet());
	
		//show choice dialog
		String choice = (String)JOptionPane.showInputDialog(
                null,
                "Choose a Gesture Set",
                "Gesture Set Chooser",
                JOptionPane.PLAIN_MESSAGE,
                null,
                sortedList.toArray(),
                "");
		//if the user made a valid choice
		if ((choice != null) && (choice.length() > 0)) {
			GestureSet newGestureSet = mapping.get(choice.toString());
			//add it to the selected set
			newGestureSet.addGestureClass(gestureClass);
		}	
	}

}
