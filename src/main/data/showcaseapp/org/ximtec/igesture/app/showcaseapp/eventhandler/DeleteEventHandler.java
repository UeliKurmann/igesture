package org.ximtec.igesture.app.showcaseapp.eventhandler;

import java.awt.Color;
import java.awt.Graphics2D;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;

public class DeleteEventHandler implements EventHandler {

	private Graphics2D graphic;
	
	public DeleteEventHandler(Graphics2D graphic){
		this.graphic = graphic;
	}
	
	public void run(ResultSet resultSet) {
		Note note = resultSet.getNote();
		graphic.setColor(Color.WHITE);
		graphic.fillRect((int) note.getBounds2D().getMinX(), (int) note
				.getBounds2D().getMinY(), (int) note.getBounds2D().getWidth(),
				(int) note.getBounds2D().getHeight());
	}

}
