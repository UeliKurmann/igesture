package org.ximtec.igesture.tool.core;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;

public abstract class LocateableAction extends BasicAction{
	
	private Locator locator;
	
	/**
	 * Sets the application locator
	 * @param locator
	 */
	public LocateableAction(String key, Locator locator){
		super(key,locator.getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		setLocator(locator);
	}
	
	/**
	 * Returns the applications locator
	 * @return the applications locator
	 */
	public Locator getLocator(){
		return this.locator;
	}
	
	/**
	 * Sets the applications locator
	 * @param locator the applications locator
	 */
	public void setLocator(Locator locator){
		this.locator = locator;
	}

}
