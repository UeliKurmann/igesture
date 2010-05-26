/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.util.logging.Logger;

import org.ximtec.igesture.io.tuio.TuioReader2D;

/**
 * A Tuio 2D device discovery service. It extends {@link org.ximtec.igesture.tool.view.devicemanager.discoveryservice.AbstractTuioDeviceDiscoveryService}.
 * @author Björn Puypepuype@gmail.com
 *
 */
public class Tuio2DDeviceDiscoveryService extends AbstractTuioDeviceDiscoveryService{

	private static final Logger LOGGER = Logger.getLogger(Tuio2DDeviceDiscoveryService.class.getName());
	
	/**
	 * Constructor
	 */
	public Tuio2DDeviceDiscoveryService()
	{
		super("2D",TuioReader2D.class);
	}
	
}
