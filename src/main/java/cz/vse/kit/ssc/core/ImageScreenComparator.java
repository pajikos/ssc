package cz.vse.kit.ssc.core;

import java.util.Date;

import org.im4java.core.IM4JavaException;

import cz.vse.kit.ssc.exception.Im4JavaCommandException;
import cz.vse.kit.ssc.im4java.CompareImage;
import cz.vse.kit.ssc.im4java.CompositeImage;
import cz.vse.kit.ssc.im4java.ConvertImage;
import cz.vse.kit.ssc.repository.Screenshot;

class ImageScreenComparator {

    private ImageSizeComparator imageSizeComparator;

    protected ImageScreenComparator() {
        this.imageSizeComparator = new ImageSizeComparator();

    }

    /**
     * Compare two screenshots The first param is the base screenshot
     *
     * @param baseScreenshot
     * @param otherScreenshot
     * @return
     */
    protected Screenshot compare(Screenshot baseScreenshot, Screenshot otherScreenshot, int fuzzPercent) {
        if (baseScreenshot == null || otherScreenshot == null) {
            throw new IllegalArgumentException("Cannot compare null screenshot.");
        }
        Screenshot resizedImage = imageSizeComparator.resizeImages2SameSize(baseScreenshot, otherScreenshot);
        try {
            Screenshot processedImage = CompareImage.process(baseScreenshot, resizedImage, fuzzPercent);
            processedImage.setCaptureDate(new Date());
            processedImage.setId(baseScreenshot.getId() + " x " + otherScreenshot.getId());
            processedImage.setBrowserName(baseScreenshot.getBrowserName() + " x " + otherScreenshot.getBrowserName());
            processedImage.setBrowserVersion(baseScreenshot.getBrowserVersion() + " x "
                    + otherScreenshot.getBrowserVersion());
            return processedImage;
        } catch (IM4JavaException e) {
            throw new Im4JavaCommandException("Cannot process a compare image command on the im4java.", e);
        }
    }

    /**
     * Composite difference of two screenshots If result image is too dark,
     * autoLevel=true may be set
     *
     * @param baseScreenshot
     * @param otherScreenshot
     * @param autoLevel
     * @return
     */
    protected Screenshot composeDifference(Screenshot baseScreenshot, Screenshot otherScreenshot, boolean autoLevel) {
        if (baseScreenshot == null || otherScreenshot == null) {
            throw new IllegalArgumentException("Cannot compare null screenshot.");
        }
        Screenshot resizedImage = imageSizeComparator.resizeImages2SameSize(baseScreenshot, otherScreenshot);
        try {
            Screenshot processedImage = CompositeImage.process(baseScreenshot, resizedImage);
            if (autoLevel) {
                processedImage = ConvertImage.autoLevel(processedImage);
            }
            processedImage.setCaptureDate(new Date());
            processedImage.setId(baseScreenshot.getId() + " x " + otherScreenshot.getId());
            processedImage.setBrowserName(baseScreenshot.getBrowserName() + " x " + otherScreenshot.getBrowserName());
            processedImage.setBrowserVersion(baseScreenshot.getBrowserVersion() + " x "
                    + otherScreenshot.getBrowserVersion());
            return processedImage;
        } catch (IM4JavaException e) {
            throw new Im4JavaCommandException("Cannot process a compare image command on the im4java.", e);
        }
    }

}
