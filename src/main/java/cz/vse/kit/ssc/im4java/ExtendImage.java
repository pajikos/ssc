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
 * Adapter for extend command from ImageMagick library
 *
 * @author pavel.sklenar
 *
 */
public class ExtendImage extends ImageOperation {

    public static Screenshot process(Screenshot screenshot, int width, int height) throws IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage();
        op.gravity("north");
        op.background("white");
        op.extent(width, height);

        op.addImage("png:-");
        ConvertCmd convert = new ConvertCmd();

        logCommand(convert, op);

        Stream2BufferedImage s2b = new Stream2BufferedImage();
        convert.setOutputConsumer(s2b);
        Screenshot resScreenshot = new Screenshot(screenshot);
        // run command and extract BufferedImage from OutputConsumer
        try (InputStream in = new ByteArrayInputStream(screenshot.getImageData())) {
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
