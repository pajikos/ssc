package cz.vse.kit.ssc.core;

import org.im4java.core.IM4JavaException;
import org.im4java.core.InfoException;

import cz.vse.kit.ssc.exception.Im4JavaCommandException;
import cz.vse.kit.ssc.im4java.CropImage;
import cz.vse.kit.ssc.im4java.ExtendImage;
import cz.vse.kit.ssc.im4java.InfoImage;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Screenshot size comparator
 * 
 * @author pavel.sklenar
 * 
 */
class ImageSizeComparator {

	protected ImageSizeComparator() {
	}

	/**
	 * Return an instance of screenshot, which has the same size as the base
	 * screenshot
	 * 
	 * @param baseScreenshot
	 * @param screenshotToResize
	 * @return
	 */
	protected Screenshot resizeImages2SameSize(Screenshot baseScreenshot, Screenshot screenshotToResize) {
		if (baseScreenshot == null || screenshotToResize == null) {
			throw new IllegalArgumentException("Cannot resize a null screenshot.");
		}
		InfoImage baseScreenshotInfo = getInfoAboutImage(baseScreenshot);
		InfoImage screenshotToResizeInfo = getInfoAboutImage(screenshotToResize);

		// Screenshot croppedScreenshot = doExtentIfRequired(baseScreenshotInfo,
		// screenshotToResizeInfo);
		// InfoImage croppedScreenshotInfo =
		// getInfoAboutImage(croppedScreenshot);
		//
		// return doCropIfRequired(baseScreenshotInfo, croppedScreenshotInfo);
		Screenshot doExtentIfRequired = doExtentIfRequired(baseScreenshotInfo, screenshotToResizeInfo);
		// SscFileUtils.saveScreenshotToFile(doExtentIfRequired, "D:/temp/ssc",
		// "sameSize");
		return doExtentIfRequired;

	}

	/**
	 * Get Immutable info object about screenshot
	 * 
	 * @param screenshot
	 * @return
	 */
	private InfoImage getInfoAboutImage(Screenshot screenshot) {
		try {
			return new InfoImage(screenshot);
		} catch (InfoException e) {
			throw new Im4JavaCommandException("Cannot get info about image from the command on the im4java.", e);
		}

	}

	/**
	 * Crop the other screenshot, if the base screenshot is smaller.
	 * 
	 * @param baseScreenshotInfo
	 * @param screenshotToResizeInfo
	 * @return
	 */
	private Screenshot doCropIfRequired(InfoImage baseScreenshotInfo, InfoImage screenshotToResizeInfo) {
		Screenshot resultScreenshot = screenshotToResizeInfo.getScreenshot();
		try {
			int baseWidth = baseScreenshotInfo.getImageWidth();
			int baseHeight = baseScreenshotInfo.getImageHeight();
			int otherWidth = screenshotToResizeInfo.getImageWidth();
			int otherHeight = screenshotToResizeInfo.getImageHeight();
			if ((baseWidth < otherWidth) || (baseHeight < otherHeight)) {
				resultScreenshot = CropImage.process(screenshotToResizeInfo.getScreenshot(), baseWidth, baseHeight);
			}
		} catch (IM4JavaException e) {
			throw new Im4JavaCommandException("Cannot process a crop image command on the im4java.", e);
		}

		return resultScreenshot;
	}

	/**
	 * Extent the other screenshot if the base screenshot is bigger.
	 * 
	 * @param baseScreenshotInfo
	 * @param screenshotToResizeInfo
	 * @return
	 */
	private Screenshot doExtentIfRequired(InfoImage baseScreenshotInfo, InfoImage screenshotToResizeInfo) {
		Screenshot resultScreenshot = screenshotToResizeInfo.getScreenshot();
		try {
			int baseWidth = baseScreenshotInfo.getImageWidth();
			int baseHeight = baseScreenshotInfo.getImageHeight();

			int otherWidth = screenshotToResizeInfo.getImageWidth();
			int otherHeight = screenshotToResizeInfo.getImageHeight();

			if ((baseWidth != otherWidth) || (baseHeight != otherHeight)) {
				resultScreenshot = ExtendImage.process(screenshotToResizeInfo.getScreenshot(), baseWidth, baseHeight);
			}
		} catch (IM4JavaException e) {
			throw new Im4JavaCommandException("Cannot process a extent image command on the im4java.", e);
		}

		return resultScreenshot;
	}

}
