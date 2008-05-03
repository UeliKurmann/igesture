package org.ximtec.igesture.framework.algorithm.feature;

import org.junit.Before;
import org.sigtec.ink.Note;
import org.sigtec.ink.NoteTool;
import org.ximtec.igesture.algorithm.feature.F1;
import org.ximtec.igesture.algorithm.feature.F10;
import org.ximtec.igesture.algorithm.feature.F11;
import org.ximtec.igesture.algorithm.feature.F12;
import org.ximtec.igesture.algorithm.feature.F13;
import org.ximtec.igesture.algorithm.feature.F14;
import org.ximtec.igesture.algorithm.feature.F15;
import org.ximtec.igesture.algorithm.feature.F16;
import org.ximtec.igesture.algorithm.feature.F17;
import org.ximtec.igesture.algorithm.feature.F18;
import org.ximtec.igesture.algorithm.feature.F19;
import org.ximtec.igesture.algorithm.feature.F2;
import org.ximtec.igesture.algorithm.feature.F20;
import org.ximtec.igesture.algorithm.feature.F21;
import org.ximtec.igesture.algorithm.feature.F22;
import org.ximtec.igesture.algorithm.feature.F23;
import org.ximtec.igesture.algorithm.feature.F24;
import org.ximtec.igesture.algorithm.feature.F25;
import org.ximtec.igesture.algorithm.feature.F3;
import org.ximtec.igesture.algorithm.feature.F4;
import org.ximtec.igesture.algorithm.feature.F5;
import org.ximtec.igesture.algorithm.feature.F6;
import org.ximtec.igesture.algorithm.feature.F7;
import org.ximtec.igesture.algorithm.feature.F8;
import org.ximtec.igesture.algorithm.feature.F9;
import org.ximtec.igesture.algorithm.feature.Feature;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.algorithm.feature.FeatureTool;

public class FeatureTest {

	private static final String NOTE_XML = "note.xml";

	private Note note;

	@Before
	public void setUp() {
		note = NoteTool.importXML(ClassLoader
				.getSystemResourceAsStream(NOTE_XML));
	}

	@org.junit.Test
	public void feature1() {
		org.junit.Assert.assertEquals(-0.2425356338943292,compute(F1.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature2() {
		org.junit.Assert.assertEquals( -0.9701424979308327,compute(F2.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature3() {
		org.junit.Assert.assertEquals( 235.79456557493563,compute(F3.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature4() {
		org.junit.Assert.assertEquals( 1.0125700761060759,compute(F4.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature5() {
		org.junit.Assert.assertEquals(120.97410570093801,compute(F5.class), 
				0.0000005);
	}

	@org.junit.Test
	public void feature6() {
		org.junit.Assert.assertEquals( 0.9946917938265513,compute(F6.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature7() {
		org.junit.Assert.assertEquals( -0.10289915108550529,compute(F7.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature8() {
		org.junit.Assert.assertEquals( 469.8368105034607,compute(F8.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature9() {
		org.junit.Assert.assertEquals( 1.240849226681367,compute(F9.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature10() {
		org.junit.Assert.assertEquals( 7.565923162004228,compute(F10.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature11() {
		org.junit.Assert.assertEquals(3.054971844940879,compute(F11.class), 
				0.0000005);
	}

	@org.junit.Test
	public void feature12() {
		org.junit.Assert.assertEquals( 1.5114150572791722,compute(F12.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature13() {
		org.junit.Assert.assertEquals( 841.0,compute(F13.class), 0.0000005);
	}

	@org.junit.Test
	public void feature14() {
		org.junit.Assert.assertEquals(2.0 ,compute(F14.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature15() {
		org.junit.Assert.assertEquals(0.45247292600776656 , compute(F15.class),0.0000005);
	}
	
	@org.junit.Test
	public void feature16() {
		org.junit.Assert.assertEquals(0.8672841925557736 ,compute(F16.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature17() {
		org.junit.Assert.assertEquals(0.539242596680645 ,compute(F17.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature18() {
		org.junit.Assert.assertEquals(0.4619416320 ,compute(F18.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature19() {
		org.junit.Assert.assertEquals(0.5130487439605235,compute(F19.class), 
				0.0000005);
	}

	@org.junit.Test
	public void feature20() {
		org.junit.Assert.assertEquals(1.0 ,compute(F20.class),
				0.0000005);
	}

	@org.junit.Test
	public void feature21() {
		org.junit.Assert.assertEquals( 3.883779985652067, compute(F21.class),0.0000005);
	}

	@org.junit.Test
	public void feature22() {
		org.junit.Assert.assertEquals( 0.0, compute(F22.class),0.0000005);
	}

	@org.junit.Test
	public void feature23() {
		org.junit.Assert.assertEquals( 0.0, compute(F23.class),0.0000005);
	}

	@org.junit.Test
	public void feature24() {
		org.junit.Assert.assertEquals( 1.0, compute(F24.class),0.0000005);
	}

	@org.junit.Test
	public void feature25() {
		org.junit.Assert.assertEquals(1.0, compute(F25.class), 0.0000005);
	}
	
	@org.junit.Test
	public void feature26() {
		//org.junit.Assert.assertEquals(compute(F26.class), 1.0, 0.0000005);
	}

	public double compute(Class<? extends Feature> feature) {
		try {
			return FeatureTool.createFeature(feature.getName()).compute(note);
		} catch (FeatureException e) {
			throw new RuntimeException();
		}
	}

}
