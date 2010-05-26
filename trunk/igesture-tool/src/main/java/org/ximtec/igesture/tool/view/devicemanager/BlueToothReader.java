package org.ximtec.igesture.tool.view.devicemanager;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.util.Constant;

/**
 * Dummy BlueTooth Device
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class BlueToothReader extends AbstractGestureDevice<Note,Point>{
	
	private RemoteDevice device;
	
	public BlueToothReader(String address, String name, RemoteDevice device)
	{
		setName(name);
		setDeviceID(address);
		setConnectionType("Bluetooth");
		setDeviceType(Constant.TYPE_2D);
		
		this.device = device;
	}
	
	@Override
	public void connect() {
		
//		String url = "btspp://"+device.getBluetoothAddress()+":1101;master=false";
		String url = "btspp://localhost:1101;name=ANOTOSTREAMING";
		
		System.out.println("Connecting to " + url);
		
		StreamConnectionNotifier server;
		try{
			server = (StreamConnectionNotifier) Connector.open(url,Connector.READ);
			System.out.println("Waiting for incoming connection...");
	        StreamConnection conn = server.acceptAndOpen();
	        System.out.println("Client Connected...");
	        DataInputStream din   = new DataInputStream(conn.openInputStream());
	        while(true){
	            String cmd = "";
	            char c;
	            while (((c = din.readChar()) > 0) && (c!='\n') ){
	                cmd = cmd + c;
	            }
	            System.out.println("Received " + cmd);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

		
		
		
//		final Object lock = new Object();
//		
//		UUID[] uuidSet = new UUID[1];
//		uuidSet[0]=new UUID(0x0011); //OBEX Object Push service 0x1105
//
//		int[] attrIDs =  new int[] {
//		        0x0100 // Service name
//		};
//
//		try {
//			LocalDevice localDevice = LocalDevice.getLocalDevice();
//			DiscoveryAgent agent = localDevice.getDiscoveryAgent();
//			 agent.searchServices(null,uuidSet,device, new DiscoveryListener(){
//
//				@Override
//				public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {}
//
//				@Override
//				public void inquiryCompleted(int discType) {}
//
//				@Override
//				public void serviceSearchCompleted(int transID, int respCode) {
//					synchronized(lock){
//			            lock.notify();
//			        }
//				}
//
//				@Override
//				public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
//
//			       for (int i = 0; i < servRecord.length; i++) {
//			           String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
//			           if (url == null) {
//			               continue;
//			           }
//
//			           DataElement serviceName = servRecord[i].getAttributeValue(0x0100);
//			           if (serviceName != null) {
//			               System.out.println("service " + serviceName.getValue() + " found " + url);
//			           } else {
//			               System.out.println("service found " + url);
//			           }
//
//			             // if(serviceName.getValue().equals("OBEX Object Push")){
////			                       sendMessageToDevice(url);
//			              //}
//			       }
//
//			   }
//
//
//				private void sendMessageToDevice(String serverURL){
//			        try{
//			            System.out.println("Connecting to " + serverURL);
//
//			            ClientSession clientSession = (ClientSession) Connector.open(serverURL);
//			            HeaderSet hsConnectReply = clientSession.connect(null);
//			            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
//			                System.out.println("Failed to connect");
//			                return;
//			            }
//
//			            HeaderSet hsOperation = clientSession.createHeaderSet();
//			            hsOperation.setHeader(HeaderSet.NAME, "Hello.txt");
//			            hsOperation.setHeader(HeaderSet.TYPE, "text");
//
//			            //Create PUT Operation
//			            Operation putOperation = clientSession.put(hsOperation);
//
//			            // Sending the message
//			            byte data[] = "Hello World !!!".getBytes("iso-8859-1");
//			            OutputStream os = putOperation.openOutputStream();
//			            os.write(data);
//			            os.close();
//
//			            putOperation.close();
//			            clientSession.disconnect(null);
//			            clientSession.close();
//			        }
//			        catch (Exception e) {
//			            e.printStackTrace();
//			        }
//			    }
//
//			 });
//
//			    try {
//			        synchronized(lock){
//			            lock.wait();
//			        }
//			    }
//			    catch (InterruptedException e) {
//			        e.printStackTrace();
//			        return;
//			    }
//			
//		} catch (BluetoothStateException e) {
//			e.printStackTrace();
//		}
		

	}

	@Override
	public void disconnect() {	
	}

	@Override
	public void clear() {
	}

	@Override
	public void dispose() {	
	}

	@Override
	public List<Point> getChunks() {
		return null;
	}

	@Override
	public Gesture<Note> getGesture() {
		return null;
	}

	@Override
	public void init() {		
	}

}
