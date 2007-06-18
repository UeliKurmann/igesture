package org.ximtec.igesture.framework.algorithm.feature.test;

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

	@org.junit.Before
	public void setUp() {
		note = NoteTool.importXML(ClassLoader
				.getSystemResourceAsStream(NOTE_XML));
	}

	@org.junit.Test
	public void feature1() {
		org.junit.Assert.assertEquals(compute(F1.class), -0.2425356338943292);
	}

	@org.junit.Test
	public void feature2() {
		org.junit.Assert.assertEquals(compute(F2.class), -0.9701424979308327);
	}

	@org.junit.Test
	public void feature3() {
		org.junit.Assert.assertEquals(compute(F3.class), 235.79456557493563);
	}

	@org.junit.Test
	public void feature4() {
		org.junit.Assert.assertEquals(compute(F4.class), 1.0125700761060759);
	}

	@org.junit.Test
	public void feature5() {
		org.junit.Assert.assertEquals(compute(F5.class), 120.97410570093801);
	}

	@org.junit.Test
	public void feature6() {
		org.junit.Assert.assertEquals(compute(F6.class), 0.9946917938265513);
	}

	@org.junit.Test
	public void feature7() {
		org.junit.Assert.assertEquals(compute(F7.class), -0.10289915108550529);
	}

	@org.junit.Test
	public void feature8() {
		org.junit.Assert.assertEquals(compute(F8.class), 469.8368105034607);
	}

	@org.junit.Test
	public void feature9() {
		org.junit.Assert.assertEquals(compute(F9.class), 1.240849226681367);
	}

	@org.junit.Test
	public void feature10() {
		org.junit.Assert.assertEquals(compute(F10.class), 7.565923162004228);
	}

	@org.junit.Test
	public void feature11() {
		org.junit.Assert.assertEquals(compute(F11.class), 3.054971844940879);
	}

	@org.junit.Test
	public void feature12() {
		org.junit.Assert.assertEquals(compute(F12.class), 1.5114150572791722);
	}

	@org.junit.Test
	public void feature13() {
		org.junit.Assert.assertEquals(compute(F13.class), 841.0);
	}

	@org.junit.Test
	public void feature14() {
		org.junit.Assert.assertEquals(compute(F14.class), 3.883779985652067);
	}

	@org.junit.Test
	public void feature15() {
		org.junit.Assert.assertEquals(compute(F15.class), 2.0);
	}

	@org.junit.Test
	public void feature16() {
		org.junit.Assert.assertEquals(compute(F16.class), 0.45247292600776656);
	}

	@org.junit.Test
	public void feature17() {
		org.junit.Assert.assertEquals(compute(F17.class), 0.8869103272628072);
	}

	@org.junit.Test
	public void feature18() {
		org.junit.Assert.assertEquals(compute(F18.class), 0.8672841925557736);
	}

	@org.junit.Test
	public void feature19() {
		org.junit.Assert.assertEquals(compute(F19.class), 0.539242596680645);
	}

	@org.junit.Test
	public void feature20() {
		org.junit.Assert.assertEquals(compute(F20.class), 0.5130487439605235);
	}

	@org.junit.Test
	public void feature21() {
		org.junit.Assert.assertEquals(compute(F21.class), 1.0);
	}

	@org.junit.Test
	public void feature22() {
		org.junit.Assert.assertEquals(compute(F22.class), 0.0);
	}

	@org.junit.Test
	public void feature23() {
		org.junit.Assert.assertEquals(compute(F23.class), 0.0);
	}

	@org.junit.Test
	public void feature24() {
		org.junit.Assert.assertEquals(compute(F24.class), 1.0);
	}

	@org.junit.Test
	public void feature25() {
		org.junit.Assert.assertEquals(compute(F25.class), 1.0);
	}

	public double compute(Class<? extends Feature> feature) {
		try {
			return FeatureTool.createFeature(feature.getName()).compute(note);
		} catch (FeatureException e) {
			throw new RuntimeException();
		}
	}

}
