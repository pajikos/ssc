/**
 * 
 */
package cz.vse.kit.ssc.im4java;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Stream2BufferedImage;

import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.utils.DataConvertUtils;

/**
 * Adapter for composite command from ImageMagick library
 * @author pavel.sklenar
 *
 */
public class CompositeImage extends ImageOperation {
	
	public static Screenshot process(Screenshot baseScreenshot, Screenshot otherScreenshot) throws IM4JavaException {
		
		IMOperation op = new IMOperation();
		op.addImage();
		op.addImage();
		op.compose("difference");
		op.addImage("png:-"); 
		
		CompositeCmd compareCmd = new CompositeCmd();
		
		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(baseScreenshot.getImageData());
		InputStream inputStreamOtherScreenshot = new ByteArrayInputStream(otherScreenshot.getImageData());

		logCommand(compareCmd, op);
		
		Stream2BufferedImage s2b = new Stream2BufferedImage();
		compareCmd.setOutputConsumer(s2b);

		Screenshot resScreenshot = new Screenshot();
		try {
			BufferedImage baseBufferedImage = ImageIO.read(inputStreamBaseScreenshot);
			BufferedImage otherBufferedImage = ImageIO.read(inputStreamOtherScreenshot);
			compareCmd.run(op, baseBufferedImage, otherBufferedImage);
			
			BufferedImage img = s2b.getImage();
			resScreenshot.setImageData(DataConvertUtils.bufferedImageToByteArray(img));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}
		
		return resScreenshot;
		
	}

}
