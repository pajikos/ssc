package cz.vse.kit.ssc.exception;

/**
 * @author pavel.sklenar
 * 
 */
@SuppressWarnings("serial")
public class FilenameException extends TesterException {

	public FilenameException() {
		super("The directory to save screenshots doesn't exist or cannot write.");
	}
	
	public FilenameException(String message) {
		super(message);
	}

}
