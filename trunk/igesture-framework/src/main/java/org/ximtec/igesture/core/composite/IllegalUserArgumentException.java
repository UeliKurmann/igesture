/**
 * 
 */
package org.ximtec.igesture.core.composite;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class IllegalUserArgumentException extends IllegalArgumentException {

	private static final String ERROR_MESSAGE_USER = "The user cannot be specified for a cardinality based constraint.\n" +
	"The user field was not saved";
	private static final String LOGGER_MESSAGE_USER = "The user cannot be specified for a cardinality based constraint.\n" +
	"The gesture class was added without the user field.";
	
	public IllegalUserArgumentException()
	{
		super(ERROR_MESSAGE_USER);
	}
	
	public String getLoggerMessage()
	{
		return LOGGER_MESSAGE_USER;
	}
}
