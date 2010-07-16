package org.ximtec.igesture.io.tuio;

/**
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class TuioConstants {

	/** Default port used by tuio protocol */
	public final static int DEFAULT_PORT = 3333;

	/** listen for /tuio/2Dobj events */
	public static final String TUIO_OBJECT = "/tuio/2Dobj";
	/** listen for /tuio/2Dcur events */
	public static final String TUIO_CURSOR = "/tuio/2Dcur";
	/** listen for /tuio/3Dobj events */
	public static final String TUIO_OBJECT_3D = "/tuio/3Dobj";
	/** listen for /tuio/3Dcur events */
	public static final String TUIO_CURSOR_3D = "/tuio/3Dcur";
	
	
	public static final String XML_TUIO_PROFILES = "tuioprofiles.xml";
	public static final String XML_TUIO_PROFILES_NODE = "association";
	public static final String XML_NODE_PROFILE = "profile";
	public static final String XML_NODE_HANDLER = "handler";
}
