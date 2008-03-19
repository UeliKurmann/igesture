package org.ximtec.igesture.tool.locator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Locator {
	
	private static Locator defaultLocator;
	
	private Map<String, Service> services;
	
	public Locator(){
		services = new HashMap<String, Service>();
	}
	
	public void addService(Service service){
		services.put(service.getIdentifier(), service);
	}
	
	public void removeService(Service service){
		services.remove(service.getIdentifier());
	}
	
	public Service getService(String name){
		return services.get(name);
	}
	
	public <T> T getService(String name, Class<T> type){
		return (T)services.get(name);
	}
	
	public Set<String> getServices(){
		return services.keySet();
	}
	
	public static Locator getDefault(){
		if(defaultLocator == null){
			defaultLocator = new Locator();
		}
		return defaultLocator;
	}
}
