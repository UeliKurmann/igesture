/**
 * 
 */
package org.ximtec.igesture.io.tuio;

import java.awt.Dimension;

/**
 * @author Bj�rn Puypepuype@gmail.com
 *
 */
public interface ITuioReader extends TuioListener {
	
	public TuioReaderPanel getPanel();
	
	public TuioReaderPanel getPanel(Dimension dimension);
}
