package org.ximtec.igesture.app.showcaseapp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class Style {
	
	private Color color;
	private Stroke stroke;
	
	public Style(){
		color = Color.BLACK;
		stroke = new BasicStroke(1.0f);
	}
	
	public Color getColor(){
		return color;
	}

	public Stroke getStroke(){
		return stroke;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void setStroke(Stroke stroke){
		this.stroke = stroke;
	}
}
