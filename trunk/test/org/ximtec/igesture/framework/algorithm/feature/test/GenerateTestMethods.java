package org.ximtec.igesture.framework.algorithm.feature.test;

import org.sigtec.ink.Note;
import org.sigtec.ink.NoteTool;
import org.ximtec.igesture.algorithm.feature.F1;
import org.ximtec.igesture.algorithm.feature.Feature;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.algorithm.feature.FeatureTool;

public class GenerateTestMethods {

	private static final String NOTE_XML = "note.xml";
	
	private static Note note;
	
	public static void main(String[] args){
		note = NoteTool.importXML(ClassLoader
				.getSystemResourceAsStream(NOTE_XML));
		
		for(int i = 1; i<=25; i++){
			
			print(i);
			
		}
		
	}
	
	public static double compute(Class<? extends Feature> feature){
		try {
			return FeatureTool.createFeature(feature.getName()).compute(note);
		} catch (FeatureException e) {
			throw new RuntimeException();
		}
	}
	
	public static void print(int i){
		try {
			Class c = Class.forName("org.ximtec.igesture.algorithm.feature.F"+i);
			System.out.println("@org.junit.Test");
			System.out.println("public void feature"+i+"(){");
			System.out.println("org.junit.Assert.assertEquals(compute(F"+i+".class),"+ compute(c)+");");
			System.out.println("}");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
