/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.util.logging.Logger;

import org.ximtec.igesture.io.tuio.TuioReader3D;

/**
 * A Tuio 3D device discovery service. It extends {@link org.ximtec.igesture.tool.view.devicemanager.discoveryservice.AbstractTuioDeviceDiscoveryService}.
 * @author Björn Puypepuype@gmail.com
 *
 */
public class Tuio3DDeviceDiscoveryService extends AbstractTuioDeviceDiscoveryService{

	private static final Logger LOGGER = Logger.getLogger(Tuio3DDeviceDiscoveryService.class.getName());
	
	/**
	 * Constructor
	 */
	public Tuio3DDeviceDiscoveryService()
	{
		super("3D",TuioReader3D.class);
	}

}
