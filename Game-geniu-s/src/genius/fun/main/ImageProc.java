
package genius.fun.main;

import static org.bytedeco.javacpp.opencv_core.CV_32FC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.cvRect;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_core.cvarrToMat;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_COMP_INTERSECT;
import static org.bytedeco.javacpp.opencv_imgproc.TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvCompareHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.CvHistogram;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Size;

import genius.fun.util.JavaCVUtil;

/**
 * @author Lie
 *
 */

public class ImageProc {
	public ImageProc() {
		
	}
	
	public genius.fun.win32.Point imgMatch(IplImage baseImage, String tempPath) {
		// read in image default colors
		cvSetImageROI(baseImage, cvRect(0,0,baseImage.width(),baseImage.height() - 60));
		Mat sourceColor = cvarrToMat(baseImage);
		Mat sourceGrey = new Mat(sourceColor.size(), CV_8UC1);
		cvtColor(sourceColor, sourceGrey, COLOR_BGR2GRAY);
		// load in template in grey
		Mat template = imread(tempPath, CV_LOAD_IMAGE_GRAYSCALE);// int = 0
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
		template.release();
		template._deallocate();
		sourceColor.release();
		sourceGrey.release();
		result.release();
		sourceColor = null;
		sourceGrey = null;
		result = null;
		template = null;
		return clickPoint;
	}
	
	public double histMatch(CvHistogram hist , IplImage compareImage) {
		CvHistogram hist1 = JavaCVUtil.getHueHistogram(compareImage);
		//CV_COMP_CHISQR
		double matchValue = cvCompareHist(hist, hist1, CV_COMP_INTERSECT);
		hist1.release();
		return matchValue;
	}
}
