/**
 * 
 */
package cz.vse.kit.ssc.im4java;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Stream2BufferedImage;
import org.im4java.process.Pipe;

import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.utils.DataConvertUtils;

/**
 * @author pavel.sklenar
 * 
 */
public class ConvertImage extends ImageOperation {

	public static Screenshot convertToGray(Screenshot screenshot) throws IM4JavaException {

		IMOperation op = new IMOperation();
		op.addImage();
		op.colorspace("gray");
		op.addImage("png:-");

		ConvertCmd convertCmd = new ConvertCmd();

		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(screenshot.getImageData());

		logCommand(convertCmd, op);

		Stream2BufferedImage s2b = new Stream2BufferedImage();
		convertCmd.setOutputConsumer(s2b);

		Screenshot resScreenshot = new Screenshot();
		try {
			BufferedImage baseBufferedImage = ImageIO.read(inputStreamBaseScreenshot);
			convertCmd.run(op, baseBufferedImage);

			BufferedImage img = s2b.getImage();
			resScreenshot.setImageData(DataConvertUtils.bufferedImageToByteArray(img));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}

		return resScreenshot;
	}

	public static float computeMean(Screenshot screenshot) throws IM4JavaException {

		IMOperation op = new IMOperation();
		op.addImage("-");
		op.format("%[fx:mean]");
		op.addImage("info:");

		ConvertCmd convertCmd = new ConvertCmd();

		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(screenshot.getImageData());

		logCommand(convertCmd, op);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		


		Pipe pipeIn  = new Pipe(inputStreamBaseScreenshot,null);

		Pipe pipeOut = new Pipe(null,fos);
		convertCmd.setInputProvider(pipeIn);
		convertCmd.setOutputConsumer(pipeOut);

		try {
			//BufferedImage baseBufferedImage = ImageIO.read(inputStreamBaseScreenshot);
			convertCmd.run(op);
			return Float.valueOf(fos.toString("UTF-8"));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}
	}
	
	public static float computeCommonMean(float firstMean, float secondMean) throws IM4JavaException {

		IMOperation op = new IMOperation();
		op.addImage("xc:");
		op.format("%[fx:" + String.valueOf(firstMean) + "*"+ String.valueOf(secondMean)+"]");
		op.addImage("info:");

		ConvertCmd convertCmd = new ConvertCmd();

		logCommand(convertCmd, op);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();

		Pipe pipeOut = new Pipe(null,fos);

		convertCmd.setOutputConsumer(pipeOut);

		try {
			convertCmd.run(op);
			return Float.valueOf(fos.toString("UTF-8"));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}
	}
	
	public static float computeStandardDeviation(Screenshot screenshot) throws IM4JavaException {

		IMOperation op = new IMOperation();
		op.addImage();
		op.format("%[fx:standard_deviation]");
		op.addImage("info:");

		ConvertCmd convertCmd = new ConvertCmd();

		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(screenshot.getImageData());

		logCommand(convertCmd, op);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();

		Pipe pipeOut = new Pipe(null,fos);

		convertCmd.setOutputConsumer(pipeOut);

		try {
			BufferedImage baseBufferedImage = ImageIO.read(inputStreamBaseScreenshot);
			convertCmd.run(op, baseBufferedImage);
			return Float.valueOf(fos.toString("UTF-8"));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}
	}
	
	public static Screenshot compute(Screenshot baseScreenshot, Screenshot otherScreenshot, float baseImageMean, float otherImageMean, float commonImageMean) throws IM4JavaException {
		IMOperation op = new IMOperation();
		op.addImage();
		op.addImage();
		op.compose("mathematics");
		op.set("option:compose:args", "1,-"+String.valueOf(baseImageMean) + ",-"+ String.valueOf(otherImageMean) + "," + String.valueOf(commonImageMean));
		op.composite();
		op.addImage("miff:-");

		ConvertCmd convertCmd = new ConvertCmd();

		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(baseScreenshot.getImageData());
		InputStream inputStreamOtherScreenshot = new ByteArrayInputStream(otherScreenshot.getImageData());

		logCommand(convertCmd, op);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();

		Pipe pipeOut = new Pipe(null,fos);
		convertCmd.setOutputConsumer(pipeOut);

		Screenshot resScreenshot = new Screenshot();
		try {
			BufferedImage baseBufferedImage = ImageIO.read(inputStreamBaseScreenshot);
			BufferedImage otherBufferedImage = ImageIO.read(inputStreamOtherScreenshot);
			convertCmd.run(op, baseBufferedImage, otherBufferedImage);
			
			resScreenshot.setImageData(fos.toByteArray());
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}

		return resScreenshot;

	}
	
	public static float computeCommonMeanAndStandardDeviation(Screenshot screenshot, float firstStandardDeviation, float secondStandardDeviation) throws IM4JavaException {

		IMOperation op = new IMOperation();
		//op.addImage("xc:");
		op.addImage("-");
		op.format("%[fx:" + "mean" + "/("+ String.valueOf(firstStandardDeviation) + "*"+ String.valueOf(secondStandardDeviation)+")]");
		op.addImage("info:");

		ConvertCmd convertCmd = new ConvertCmd();

		logCommand(convertCmd, op);
		
		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(screenshot.getImageData());

		ByteArrayOutputStream fos = new ByteArrayOutputStream();

		Pipe pipeIn  = new Pipe(inputStreamBaseScreenshot,null);

		Pipe pipeOut = new Pipe(null,fos);
		
		convertCmd.setInputProvider(pipeIn);

		convertCmd.setOutputConsumer(pipeOut);

		try {
			convertCmd.run(op);
			return Float.valueOf(fos.toString("UTF-8"));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}
	}
	
	public static Screenshot autoLevel(Screenshot screenshot) throws IM4JavaException {

		IMOperation op = new IMOperation();
		op.addImage();
		op.addRawArgs("-auto-level");
		op.addImage("png:-");

		ConvertCmd convertCmd = new ConvertCmd();

		InputStream inputStreamBaseScreenshot = new ByteArrayInputStream(screenshot.getImageData());

		logCommand(convertCmd, op);

		Stream2BufferedImage s2b = new Stream2BufferedImage();
		convertCmd.setOutputConsumer(s2b);

		Screenshot resScreenshot = new Screenshot();
		try {
			BufferedImage baseBufferedImage = ImageIO.read(inputStreamBaseScreenshot);
			convertCmd.run(op, baseBufferedImage);

			BufferedImage img = s2b.getImage();
			resScreenshot.setImageData(DataConvertUtils.bufferedImageToByteArray(img));
		} catch (Exception e) {
			throw new IM4JavaException(e);
		}

		return resScreenshot;
	}

}
