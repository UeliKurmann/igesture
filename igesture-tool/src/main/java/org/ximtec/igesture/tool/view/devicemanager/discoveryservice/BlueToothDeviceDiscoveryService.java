package org.ximtec.igesture.tool.view.devicemanager.discoveryservice;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import org.ximtec.igesture.io.AbstractGestureDevice;

/**
 * A BlueTooth device discovery service. It implements the {@link org.ximtec.igesture.tool.view.devicemanager.discoveryservice.DeviceDiscoveryService} and {@link javax.bluetooth.DiscoveryListener} interfaces.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class BlueToothDeviceDiscoveryService implements DeviceDiscoveryService, DiscoveryListener {

	private static final Logger LOGGER = Logger.getLogger(BlueToothDeviceDiscoveryService.class.getName());
	
	private Object lock;
	/**
	 * found bluetooth devices
	 */
	private Vector<RemoteDevice> remoteDevices;
	/**
	 * created devices
	 */
	private Set<AbstractGestureDevice<?,?>> devices;
	private BlueToothDeviceConverter convertor;
	
	/**
	 * Constructor
	 */
	public BlueToothDeviceDiscoveryService()
	{
		devices = new HashSet<AbstractGestureDevice<?,?>>();
		remoteDevices = new Vector<RemoteDevice>();
		lock = new Object();
		convertor = new BlueToothDeviceConverter();
		
	}
	
	@Override
	public Set<AbstractGestureDevice<?, ?>> discover() {
		
		LOGGER.log(Level.INFO,"Device discovery started!");
		
		String bluecoveVersion = LocalDevice.getProperty("bluecove");
        if(!bluecoveVersion.equals("")) {
            String l2capFeature = LocalDevice.getProperty("bluecove.feature.l2cap");

            if(l2capFeature.equals("true")) {
                // set min id for Bluecove
                System.setProperty("bluecove.jsr82.psm_minimum_off", "true");
            }
        }
		
		
		LocalDevice localDevice;
		try {
			localDevice = LocalDevice.getLocalDevice();
			LOGGER.log(Level.INFO,"Your Computers Bluetooth MAC: " + localDevice.getBluetoothAddress());
			LOGGER.log(Level.INFO,"Starting device inquiry...");
	        DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();
			discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
		} catch (BluetoothStateException e) {
			LOGGER.log(Level.SEVERE,"Problem with BlueTooth connection.",e);
		}

        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
        	LOGGER.log(Level.SEVERE,"Problems during device discovery.",e);
        }

        LOGGER.log(Level.INFO,"Device discovery completed!");
        
		return devices;
	}

	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass deviceClass) {
		LOGGER.log(Level.INFO,"Device discovered: " + btDevice.getBluetoothAddress());
		try {
			LOGGER.log(Level.INFO,"Device: "+btDevice.getFriendlyName(false)
					+", minor: "+ deviceClass.getMinorDeviceClass()
					+", major: "+deviceClass.getMajorDeviceClass());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING,"Could not get friendly name from BlueTooth device",e);
			LOGGER.log(Level.INFO,"Device: minor: "+ deviceClass.getMinorDeviceClass()
					+", major: "+deviceClass.getMajorDeviceClass());
		}
		
		//if the devices was not found yet
        if (!remoteDevices.contains(btDevice))
        {
        	//add it to the found devices
            remoteDevices.addElement(btDevice);
            //create a AbstractGestureDevice
            AbstractGestureDevice<?, ?> dev = convertor.createDevice(btDevice, deviceClass);
            //add it to the created devices
            if(dev != null)
            	devices.add(dev);
        }
		
	}

	@Override
	public void inquiryCompleted(int discType) {
		synchronized (this.lock) {
            this.lock.notify();
        }
		
		switch (discType) 
		{
        case DiscoveryListener.INQUIRY_COMPLETED :
            LOGGER.log(Level.INFO,"INQUIRY_COMPLETED");
            break;
            
        case DiscoveryListener.INQUIRY_TERMINATED :
        	LOGGER.log(Level.INFO,"INQUIRY_TERMINATED");
            break;
            
        case DiscoveryListener.INQUIRY_ERROR :
        	LOGGER.log(Level.WARNING,"INQUIRY_ERROR");
            break;

        default :
        	LOGGER.log(Level.WARNING,"Unknown Response Code");
            break;
		}
	}

	@Override
	public void serviceSearchCompleted(int transID, int respCode) {
		//not necessary
	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		//not necessary
	}

	@Override
	public void dispose() {
		devices.clear();
		remoteDevices.clear();	
	}

}
