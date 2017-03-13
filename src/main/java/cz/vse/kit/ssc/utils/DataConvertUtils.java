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
     * Convert {@link BufferedImage} into array of bytes
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(bufferedImage, IMAGE_FORMAT, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            return imageInByte;
        }
    }

    /**
     * Convert array of bytes into {@link BufferedImage}
     *
     * @param array
     * @return
     * @throws IOException
     */
    public static BufferedImage byteArrayToBufferedImage(byte[] array) throws IOException {
        try (InputStream in = new ByteArrayInputStream(array)) {
            BufferedImage bImageFromConvert = ImageIO.read(in);
            return bImageFromConvert;
        }
    }

}
