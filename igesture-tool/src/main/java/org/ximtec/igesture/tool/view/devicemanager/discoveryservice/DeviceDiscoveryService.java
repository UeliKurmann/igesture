package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.util.Set;

import org.ximtec.igesture.io.AbstractGestureDevice;

/**
 * This interface defines the discovery services.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public interface DeviceDiscoveryService {

	/**
	 * Discover and return the found devices.
	 * @return	The found devices.
	 */
	public Set<AbstractGestureDevice<?,?>> discover();
	
	/**
	 * Clean-up the discovery service.
	 */
	public void dispose();
	
}
