/**
 * 
 */
package cz.vse.kit.ssc.im4java;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Stream2BufferedImage;

import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.utils.DataConvertUtils;

/**
 * @author pavel.sklenar
 *
 */
public class CropImage extends ImageOperation {
	
	public static Screenshot process(Screenshot screenshot, int width, int height) throws IM4JavaException {
		IMOperation op = new IMOperation();
		op.addImage();
		op.gravity("north");
		
		op.crop(width, height, 0, 0);
		op.p_repage();

		op.addImage("png:-");  
		ConvertCmd convert = new ConvertCmd();
		InputStream in = new ByteArrayInputStream(screenshot.getImageData());

		logCommand(convert, op);
		
		
		// set up command
		
		Stream2BufferedImage s2b = new Stream2BufferedImage();
		convert.setOutputConsumer(s2b);

		Screenshot resScreenshot = new Screenshot(screenshot);
		// run command and extract BufferedImage from OutputConsumer
		try {
			BufferedImage bImageFromConvert = ImageIO.read(in);
			convert.run(op, bImageFromConvert);
			
			BufferedImage img = s2b.getImage();
			resScreenshot.setImageData(DataConvertUtils.bufferedImageToByteArray(img));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}
		
		
		
		
		return resScreenshot;
		
	}

}
