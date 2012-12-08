/**
 * 
 */
package cz.vse.kit.ssc.core;

import org.im4java.core.IM4JavaException;

import cz.vse.kit.ssc.exception.Im4JavaCommandException;
import cz.vse.kit.ssc.im4java.ConvertImage;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * @author pavel.sklenar
 * 
 */
class ImageSimilarityComparator {
	private ImageSizeComparator imageSizeComparator;

	protected ImageSimilarityComparator() {
		this.imageSizeComparator = new ImageSizeComparator();
	}

	/**
	 * Compute similarity from two screenshots
	 * 
	 * @param baseScreenshot
	 * @param otherScreenshot
	 * @return
	 */
	protected float computeSimilarity(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		if (baseScreenshot == null || otherScreenshot == null) {
			throw new IllegalArgumentException("Cannot compute a similarity with a null screenshot.");
		}
		Screenshot resizedImage = imageSizeComparator.resizeImages2SameSize(baseScreenshot, otherScreenshot);
		try {
			Screenshot grayBaseScreenshot = ConvertImage.convertToGray(baseScreenshot);
			Screenshot grayOtherScreenshot = ConvertImage.convertToGray(resizedImage);
			float grayBaseScreenshotMean = ConvertImage.computeMean(grayBaseScreenshot);
			float grayOtherScreenshotMean = ConvertImage.computeMean(grayOtherScreenshot);
			float grayBaseScreenshotStandardDeviation = ConvertImage.computeStandardDeviation(grayBaseScreenshot);
			float grayOtherScreenshotDeviation = ConvertImage.computeStandardDeviation(grayOtherScreenshot);
			float commonImageMean = ConvertImage.computeCommonMean(grayBaseScreenshotMean, grayOtherScreenshotMean);
			Screenshot computeScreenshot = ConvertImage.compute(grayBaseScreenshot, grayOtherScreenshot,
					grayBaseScreenshotMean, grayOtherScreenshotMean, commonImageMean);
			// float secondCommonMean =
			// ConvertImage.computeMean(computeScreenshot);
			// SscFileUtils.saveScreenshotToFile(computeScreenshot, "D:/temp",
			// "huhu");
			float result = ConvertImage.computeCommonMeanAndStandardDeviation(computeScreenshot,
					grayBaseScreenshotStandardDeviation, grayOtherScreenshotDeviation);
			// result = Math.round(result*100.0f)/100.0f;
			// System.err.println(result);
			if (result > 1.00) {
				result = 1.00f;
			}
			return result;
		} catch (IM4JavaException e) {
			throw new Im4JavaCommandException("Cannot compute the similarity of the screenshots.", e);
		}

	}

}
