package cz.vse.kit.ssc.im4java;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.ArrayListOutputConsumer;

import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.utils.DataConvertUtils;

/**
 * Adapter for info command from ImageMagick library
 * @author pavel.sklenar Class InfoImage is based on class {@link Info} Getter methods
 *         are the same.
 */
public class InfoImage extends ImageOperation {
	private Screenshot screenshot;

	/**
	 * Internal hashtable with image-attributes. For images with multiple
	 * scenes, this hashtable holds the attributes of the last scene.
	 */

	private Hashtable<String, String> iAttributes = null;

	/**
	 * List of hashtables with image-attributes. Ther is one element for every
	 * scene in the image.
	 * 
	 */

	private LinkedList<Hashtable<String, String>> iAttribList = new LinkedList<Hashtable<String, String>>();

	/**
	 * Constructor
	 * 
	 * @param screenshot
	 * @throws InfoException
	 */
	public InfoImage(Screenshot screenshot) throws InfoException {
		this.screenshot = screenshot;
		getBaseInfo(screenshot.getImageData());
	}

	/**
	 * Created from method getBaseInfo from class {@link Info}
	 * 
	 */
	private void getBaseInfo(byte[] imageData) throws InfoException {
		// create operation
		IMOperation op = new IMOperation();
		op.ping();
		op.format("%m\n%w\n%h\n%g\n%W\n%H\n%G\n%z\n%r");
		op.addImage();


		try {
			BufferedImage bImageFromConvert = DataConvertUtils.byteArrayToBufferedImage(imageData);
			// execute ...
			IdentifyCmd identify = new IdentifyCmd();
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identify.setOutputConsumer(output);
			identify.run(op, bImageFromConvert);

			// ... and parse result
			ArrayList<String> cmdOutput = output.getOutput();
			Iterator<String> iter = cmdOutput.iterator();

			iAttributes = new Hashtable<String, String>();
			iAttributes.put("Format", iter.next());
			iAttributes.put("Width", iter.next());
			iAttributes.put("Height", iter.next());
			iAttributes.put("Geometry", iter.next());
			iAttributes.put("PageWidth", iter.next());
			iAttributes.put("PageHeight", iter.next());
			iAttributes.put("PageGeometry", iter.next());
			iAttributes.put("Depth", iter.next());
			iAttributes.put("Class", iter.next());
			iAttribList.add(iAttributes);
		} catch (Exception ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Return the image format.
	 */

	public String getImageFormat() {
		return iAttributes.get("Format");
	}

	/**
	 * Return the image format for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public String getImageFormat(int pSceneNr) {
		return iAttribList.get(pSceneNr).get("Format");
	}

	/**
	 * Return the image width.
	 */

	public int getImageWidth() throws InfoException {
		return getImageWidth(iAttribList.size() - 1);
	}

	/**
	 * Return the image width for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public int getImageWidth(int pSceneNr) throws InfoException {
		try {
			return Integer.parseInt(iAttribList.get(pSceneNr).get("Width"));
		} catch (NumberFormatException ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Return the image height.
	 */

	public int getImageHeight() throws InfoException {
		return getImageHeight(iAttribList.size() - 1);
	}

	/**
	 * Return the image height for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public int getImageHeight(int pSceneNr) throws InfoException {
		try {
			return Integer.parseInt(iAttribList.get(pSceneNr).get("Height"));
		} catch (NumberFormatException ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Return the image geometry.
	 */

	public String getImageGeometry() {
		return iAttributes.get("Geometry");
	}

	/**
	 * Return the image geometry for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public String getImageGeometry(int pSceneNr) {
		return iAttribList.get(pSceneNr).get("Geometry");
	}

	/**
	 * Return the image depth. Note that this method just returns an integer
	 * (e.g. 8 or 16), and not a string ("8-bit") like getProperty("Depth")
	 * does.
	 */

	public int getImageDepth() throws InfoException {
		return getImageDepth(iAttribList.size() - 1);
	}

	/**
	 * Return the image depth. Note that this method just returns an integer
	 * (e.g. 8 or 16), and not a string ("8-bit") like getProperty("Depth")
	 * does.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public int getImageDepth(int pSceneNr) throws InfoException {
		String[] depth = iAttribList.get(pSceneNr).get("Depth").split("-|/", 2);
		try {
			return Integer.parseInt(depth[0]);
		} catch (NumberFormatException ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Return the image class.
	 */

	public String getImageClass() {
		return iAttributes.get("Class");
	}

	/**
	 * Return the image class for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public String getImageClass(int pSceneNr) {
		return iAttribList.get(pSceneNr).get("Class");
	}

	/**
	 * Return the page width.
	 * 
	 */

	public int getPageWidth() throws InfoException {
		return getPageWidth(iAttribList.size() - 1);
	}

	/**
	 * Return the page width for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public int getPageWidth(int pSceneNr) throws InfoException {
		try {
			return Integer.parseInt(iAttribList.get(pSceneNr).get("PageWidth"));
		} catch (NumberFormatException ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Return the page height.
	 * 
	 */

	public int getPageHeight() throws InfoException {
		return getPageHeight(iAttribList.size() - 1);
	}

	/**
	 * Return the page height for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public int getPageHeight(int pSceneNr) throws InfoException {
		try {
			return Integer.parseInt(iAttribList.get(pSceneNr).get("PageHeight"));
		} catch (NumberFormatException ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Return the page geometry.
	 * 
	 */

	public String getPageGeometry() {
		return iAttributes.get("PageGeometry");
	}

	/**
	 * Return the page geometry for the given scene.
	 * 
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public String getPageGeometry(int pSceneNr) {
		return iAttribList.get(pSceneNr).get("PageGeometry");
	}

	/**
	 * Return the given property.
	 */

	public String getProperty(String pPropertyName) {
		return iAttributes.get(pPropertyName);
	}

	/**
	 * Return the given property of the given scene.
	 * 
	 * @param pPropertyName
	 *            Name of the property
	 * @param pSceneNr
	 *            Scene-number (zero-based)
	 */

	public String getProperty(String pPropertyName, int pSceneNr) {
		return iAttribList.get(pSceneNr).get(pPropertyName);
	}

	/**
	 * Return an enumeration of all properties.
	 */

	public Enumeration<String> getPropertyNames() {
		return iAttributes.keys();
	}

	/**
	 * @return the screenshot
	 */
	public Screenshot getScreenshot() {
		return screenshot;
	}

}
