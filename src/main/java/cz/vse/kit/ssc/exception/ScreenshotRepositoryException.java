/**
 * 
 */
package cz.vse.kit.ssc.exception;

/**
 * @author Pajik
 *
 */
@SuppressWarnings("serial")
public class ScreenshotRepositoryException extends TesterException {
	
	public ScreenshotRepositoryException(String message) {
		super(message);
	}
	
	public ScreenshotRepositoryException(Throwable throwable) {
		super(throwable);
	}
	
	public ScreenshotRepositoryException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
