package org.ximtec.igesture.app.showcaseapp.eventhandler;

import java.awt.BasicStroke;
import java.awt.Color;

import org.ximtec.igesture.app.showcaseapp.Style;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;

public class StyleEventHandler implements EventHandler {
	private Style style;
	
	public StyleEventHandler(Style style){
		this.style = style;
	}

	public void run(ResultSet resultSet) {
		String command = resultSet.getResult().getGestureClassName();
		if(command.equals("Red")){
			style.setColor(Color.RED);
		}else if(command.equals("Black")){
			style.setColor(Color.BLACK);
		}else if(command.equals("Yellow")){
			style.setColor(Color.YELLOW);
		}else if(command.equals("Thin")){
			style.setStroke(new BasicStroke(1.0f));
		}else if(command.equals("Fat")){
			style.setStroke(new BasicStroke(3.0f));
		}
	}

}
