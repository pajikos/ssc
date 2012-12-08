/**
 * 
 */
package cz.vse.kit.ssc.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Convert Utils
 * 
 * @author pavel.sklenar
 * 
 */
public class DataConvertUtils {

	private static final String IMAGE_FORMAT = "png";

	/**
	 * Convert {@link BufferedImage} to byte array
	 * 
	 * @param bufferedImage
	 * @return
	 * @throws IOException
	 */
	public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, IMAGE_FORMAT, baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	/**
	 * Convert byte array to {@link BufferedImage}
	 * 
	 * @param array
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage byteArrayToBufferedImage(byte[] array) throws IOException {
		InputStream in = new ByteArrayInputStream(array);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		return bImageFromConvert;
	}

}
