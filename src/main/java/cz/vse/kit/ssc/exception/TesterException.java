/**
 * 
 */
package cz.vse.kit.ssc.exception;

/**
 * @author pavel.sklenar
 *
 */
@SuppressWarnings("serial")
public class TesterException extends RuntimeException {
	
	public TesterException() {
	    super();
	  }

	  public TesterException(String message) {
	    super(message);
	  }

	  public TesterException(Throwable cause) {
	    super(cause);
	  }

	  public TesterException(String message, Throwable cause) {
	    super(message, cause);
	  }


}
