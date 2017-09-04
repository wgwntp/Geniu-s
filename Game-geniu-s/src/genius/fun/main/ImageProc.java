/**
 * 
 */

package genius.fun.main;

import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCalcHist;
import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCreateHist;
import static org.bytedeco.javacpp.opencv_core.CV_32FC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.CV_HIST_ARRAY;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvRect;
import static org.bytedeco.javacpp.opencv_core.cvRelease;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_core.cvarrToMat;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_COMP_INTERSECT;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvCompareHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvGetMinMaxHistValue;
import static org.bytedeco.javacpp.opencv_imgproc.cvNormalizeHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.CvHistogram;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Size;

/**
 * @author Lie
 *
 */

public class ImageProc {
	private IplImage baseImage;
	private IplImage contrastImage;
	public ImageProc() {
		
	}
	
	public genius.fun.win32.Point imgMatch(String[] args) {
		// read in image default colors
		baseImage = cvLoadImage(args[0]);
		cvSetImageROI(baseImage, cvRect(0,0,baseImage.width(),baseImage.height() - 60));
		Mat sourceColor = cvarrToMat(baseImage);
		Mat sourceGrey = new Mat(sourceColor.size(), CV_8UC1);
		cvtColor(sourceColor, sourceGrey, COLOR_BGR2GRAY);
		// load in template in grey
		Mat template = imread(args[1], CV_LOAD_IMAGE_GRAYSCALE);// int = 0
		// Size for the result image
		Size size = new Size(sourceGrey.cols() - template.cols() + 1,
				sourceGrey.rows() - template.rows() + 1);
		Mat result = new Mat(size, CV_32FC1);
		matchTemplate(sourceGrey, template, result, TM_CCORR_NORMED);

		DoublePointer minVal = new DoublePointer();
		DoublePointer maxVal = new DoublePointer();
		Point min = new Point();
		Point max = new Point();
		minMaxLoc(result, minVal, maxVal, min, max, null);
		genius.fun.win32.Point clickPoint = new genius.fun.win32.Point();
		clickPoint.x = ((max.x() + (max.x() + template.cols())) / 2);
		clickPoint.y = ((max.y() + (max.y() + template.rows())) / 2);
		baseImage.release();
		sourceColor.release();
		sourceGrey.release();
		result.release();
		cvReleaseImage(baseImage);
		baseImage = null;
		sourceColor = null;
		sourceGrey = null;
		result = null;
		return clickPoint;
	}
	
	public double histMatch(String filePath, String compareFilePath) {
		baseImage = cvLoadImage(filePath);
		CvHistogram hist = getHueHistogram(baseImage);

		contrastImage = cvLoadImage(compareFilePath);
		CvHistogram hist1 = getHueHistogram(contrastImage);
		//CV_COMP_CHISQR
		double matchValue = cvCompareHist(hist, hist1, CV_COMP_INTERSECT);
		baseImage.release();
		contrastImage.release();
		cvReleaseImage(baseImage);
		cvReleaseImage(contrastImage);
		baseImage = null;
		contrastImage = null;
		hist.release();
		hist1.release();
		return matchValue;
	}
	
	private CvHistogram getHueHistogram(IplImage image) {
		if (image == null || image.nChannels() < 1)
			new Exception("Error!");

		IplImage greyImage = cvCreateImage(image.cvSize(), image.depth(), 1);
		cvCvtColor(image, greyImage, CV_RGB2GRAY);

		// bins and value-range
		int numberOfBins = 256;
		float minRange = 0f;
		float maxRange = 255f;
		// Allocate histogram object
		int dims = 1;
		int[] sizes = new int[] {numberOfBins };
		int histType = CV_HIST_ARRAY;
		float[] minMax = new float[] {minRange, maxRange };
		float[][] ranges = new float[][] {minMax };
		int uniform = 1;
		CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, uniform);
		// Compute histogram
		int accumulate = 0;
		IplImage mask = null;
		IplImage[] aux = new IplImage[] {greyImage };

		cvCalcHist(aux, hist, accumulate, null);
		cvNormalizeHist(hist, 1);

		cvGetMinMaxHistValue(hist, minMax, minMax, sizes, sizes);
		greyImage.release();
		cvRelease(greyImage);
		greyImage = null;
		return hist;
	}

}
