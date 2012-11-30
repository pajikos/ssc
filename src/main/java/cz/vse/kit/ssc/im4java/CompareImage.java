/**
 * 
 */
package cz.vse.kit.ssc.im4java;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.im4java.core.CompareCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Stream2BufferedImage;

import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.utils.DataConvertUtils;

/**
 * @author pavel.sklenar
 *
 */
public class CompareImage extends ImageOperation {
	
	public static Screenshot process(Screenshot baseScreenshot, Screenshot otherScreenshot, int fuzzPercent) throws IM4JavaException {
		
		IMOperation op = new IMOperation();
		if (fuzzPercent > 100 || fuzzPercent < 0) {
			throw new IM4JavaException("Fuzz must be number between 0 - 100.");
		}
		if (fuzzPercent != 0) {
			op.fuzz((double)fuzzPercent, true);
		}
		op.addImage();
		op.addImage();
		op.addImage("png:-"); 
		
		CompareCmd compareCmd = new CompareCmd();
		
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
