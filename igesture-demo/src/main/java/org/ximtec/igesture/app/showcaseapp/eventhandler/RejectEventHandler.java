package org.ximtec.igesture.app.showcaseapp.eventhandler;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;

public class RejectEventHandler implements EventHandler {

	private static final String RESOURCES_SOUND_RINGOUT_WAV = "resources/sound/ringout.wav";
	private String filename;
	
	public RejectEventHandler(String filename){
		this.filename = filename;
	}
	
	public RejectEventHandler(){
		this(RESOURCES_SOUND_RINGOUT_WAV);
	}
	
	public void run(ResultSet resultSet) {
		//SoundTool.play(filename);
	}
}
