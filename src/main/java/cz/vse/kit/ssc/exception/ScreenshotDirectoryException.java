/**
 * 
 */
package cz.vse.kit.ssc.exception;

/**
 * @author pavel.sklenar
 * 
 */
@SuppressWarnings("serial")
public class ScreenshotDirectoryException extends TesterException {

	public ScreenshotDirectoryException() {
		super("The directory to save screenshots doesn't exist or cannot write.");
	}

}
