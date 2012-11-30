/**
 * 
 */
package cz.vse.kit.ssc.exception;

/**
 * @author pavel.sklenar
 * 
 */
@SuppressWarnings("serial")
public class DefaultBrowserCapabilityException extends TesterException {

	public DefaultBrowserCapabilityException() {
		super();
	}

	public DefaultBrowserCapabilityException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return createMessage(super.getMessage());
	}

	private String createMessage(String originalMessage) {
		String baseMessage = "You must set the default browser capability.";
		if (originalMessage != null) {
			return originalMessage + " " + baseMessage;
		}
		return baseMessage;

	}

}
