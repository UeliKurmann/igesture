package org.ximtec.igesture.app.showcaseapp.eventhandler;

import java.awt.Graphics2D;

import org.sigtec.ink.Note;
import org.ximtec.igesture.app.showcaseapp.Style;
import org.ximtec.igesture.core.DigitalDescriptor;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;

public class DrawEventHandler implements EventHandler {

	private Graphics2D graphic;
	private Style style;
	
	
	public DrawEventHandler(Graphics2D graphic,Style style){
		this.graphic = graphic;
		this.style = style;
	}
	
	public void run(ResultSet resultSet) {
		Result result = resultSet.getResult();
		Note note = resultSet.getNote();
		System.out.println(result.getName());
		graphic.setStroke(style.getStroke());
		graphic.setColor(style.getColor());
		DigitalDescriptor descriptor = result.getGestureClass().getDescriptor(DigitalDescriptor.class);
		descriptor.getDigitalObject(graphic, note);
	}
}
