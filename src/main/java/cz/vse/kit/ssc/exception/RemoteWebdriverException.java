/**
 * 
 */
package cz.vse.kit.ssc.exception;

/**
 * @author pavel.sklenar
 * 
 */
@SuppressWarnings("serial")
public class RemoteWebdriverException extends TesterException {

	public RemoteWebdriverException(Throwable cause) {
		super("CompatibilityTester accept only instance of RemoteWebDriver",
				cause);
	}
	
	public RemoteWebdriverException(String message) {
		super(message);
	}

}
