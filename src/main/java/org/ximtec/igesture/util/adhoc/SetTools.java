

package org.ximtec.igesture.util.adhoc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.util.GestureTools;
import org.ximtec.igesture.util.XMLTools;


public class SetTools {
	
	private static String PATH = "C:/workspace/igesture/src/main/data/sets/";

   public static void main(String[] args) {
	   createMS();
   }

   public static void createGraffitiLetters(){
	   List<GestureSet> list = new ArrayList<GestureSet>();
	   list.add(XMLTools.importGestureSet(new File(PATH+"graffiti_numbers_bea.xml")).get(0));
	   list.add(XMLTools.importGestureSet(new File(PATH+"graffiti_numbers_juerg.xml")).get(0));
	   list.add(XMLTools.importGestureSet(new File(PATH+"graffiti_numbers_stefan.xml")).get(0));
	   list.add(XMLTools.importGestureSet(new File(PATH+"graffiti_numbers_ueli_.xml")).get(0));
	   GestureSet set = GestureTools.combineSampleData(list);
	   
	   XMLTools.exportGestureSet(set, new File(PATH+"graffiti_numbers.xml"));
   
   }
   
   public static void createMS(){
	   List<GestureSet> list = new ArrayList<GestureSet>();
	   list.add(XMLTools.importGestureSet(new File(PATH+"msgestures_ueli.xml")).get(0));
	   list.add(XMLTools.importGestureSet(new File(PATH+"msgestures5.xml")).get(0));
	   GestureSet set = GestureTools.combineSampleData(list);
	   
	   XMLTools.exportGestureSet(set, new File(PATH+"ms_application_gestures.xml"));
   
   }
   
}
