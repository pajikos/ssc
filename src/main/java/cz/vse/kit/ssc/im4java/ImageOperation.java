/**
 * 
 */
package cz.vse.kit.ssc.im4java;

import java.util.LinkedList;

import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parent class for command adapters
 * @author pavel.sklenar
 * 
 */
public abstract class ImageOperation {

	protected static final Logger LOG = LoggerFactory
			.getLogger(ImageOperation.class);

	protected static void logCommand(ImageCommand command, IMOperation op) {
		StringBuilder stringWriter = new StringBuilder();
		LinkedList<String> linkedList = command.getCommand();
		LinkedList<String> cmdArgs = op.getCmdArgs();
		for (String string : linkedList) {
			stringWriter.append(string);
			stringWriter.append(" ");
		}
		for (String string : cmdArgs) {
			stringWriter.append(string);
			stringWriter.append(" ");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(stringWriter.toString());
		}
	}

}
