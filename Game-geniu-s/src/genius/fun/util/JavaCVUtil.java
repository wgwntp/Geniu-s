/**
 * 
 */

package genius.fun.util;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * @author Lie
 *
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.CvHistogram;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

/**
 * 功能说明：JavaCV工具类
 * 
 * @author:linghushaoxia
 * @time:2016年3月31日上午8:49:25
 * @version:1.0 为中国羸弱的技术, 撑起一片自立自强的天空!
 * 
 */
public class JavaCVUtil {
	/**
	 * 
	 * 功能说明:显示图像
	 * 
	 * @param mat
	 *            要显示的mat类型图像
	 * @param title
	 *            窗口标题
	 * @time:2016年3月31日下午1:28:01
	 * @author:linghushaoxia
	 * @exception:
	 * 
	 */
	public static void imShow(Mat mat, String title) {
		// opencv自带的显示模块，跨平台性欠佳，转为Java2D图像类型进行显示
		ToMat converter = new OpenCVFrameConverter.ToMat();
		CanvasFrame canvas = new CanvasFrame(title, 1);
		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.showImage(converter.convert(mat));

	}

	/**
	 * 
	 * 功能说明:保存mat到指定路径
	 * 
	 * @param mat
	 *            要保存的Mat
	 * @param filePath
	 *            保存路径
	 * @time:2016年3月31日下午8:39:50
	 * @author:linghushaoxia
	 * @exception:
	 * 
	 */
	public static boolean imWrite(Mat mat, String filePath) {
		// 不包含中文，直接使用opencv原生方法进行保存
		if (!containChinese(filePath)) {
			return opencv_imgcodecs.imwrite(filePath, mat);
		}
		try {
			/**
			 * 将mat转为java的BufferedImage
			 */
			ToMat convert = new ToMat();
			Frame frame = convert.convert(mat);
			Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
			BufferedImage bufferedImage = java2dFrameConverter.convert(frame);
			ImageIO.write(bufferedImage, "PNG", new File(filePath));

			return true;
		} catch (Exception e) {
			System.out.println("保存文件出现异常:" + filePath);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * 功能说明:判断字符是否包含中文
	 * 
	 * @param inputString
	 * @return boolean
	 * @time:2016年4月9日下午9:55:47
	 * @author:linghushaoxia
	 * @exception:
	 * 
	 */
	private static boolean containChinese(String inputString) {
		// 四段范围，包含全面
		String regex = "[\\u4E00-\\u9FA5\\u2E80-\\uA4CF\\uF900-\\uFAFF\\uFE30-\\uFE4F]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputString);
		return matcher.find();
	}

	public static void imgMatch(String[] args) {
		// read in image default colors
		IplImage baseImage = cvLoadImage(args[0]);
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
		matchTemplate(sourceGrey, template, result, CV_TM_CCORR_NORMED);

		DoublePointer minVal = new DoublePointer();
		DoublePointer maxVal = new DoublePointer();
		Point min = new Point();
		Point max = new Point();
		minMaxLoc(result, minVal, maxVal, min, max, null);
		System.out.println(maxVal + " " + minVal);
		rectangle(sourceColor,
				new Rect(max.x(), max.y(), template.cols(), template.rows()),
				randColor(), 2, 0, 0);
		genius.fun.win32.Point clickPoint = new genius.fun.win32.Point();
		clickPoint.x = ((max.x() + (max.x() + template.cols())) / 2);
		clickPoint.y = ((max.y() + (max.y() + template.rows())) / 2);
		System.out.println("x : " + clickPoint.x + " y : " + clickPoint.y);
		imshow("Original marked", sourceColor);
		imshow("Ttemplate", template);
		imshow("Results matrix", result);
		waitKey(0);
		destroyAllWindows();
		baseImage.release();
		sourceColor.release();
		sourceGrey.release();
		result.release();
		cvReleaseImage(baseImage);
		baseImage = null;
		sourceColor = null;
		sourceGrey = null;
		result = null;
	}
	
	private static void cvTemplateMatch(String[] args) {
		IplImage src = cvLoadImage(args[0], 0);  
	    IplImage tmp = cvLoadImage(args[1], 0);  
	    IplImage result = cvCreateImage(cvSize(src.width() -tmp.width()+1,src.height()-tmp.height() + 1),IPL_DEPTH_32F,1);
	    cvZero(result);

	   // cvSetImageROI(src, new CvRect(370, 175, 624, 398));
	    //cvSetImageROI(result, new CvRect(370, 175, 624 - tmp.width() + 1, 398 - tmp.height() + 1));

	    cvMatchTemplate(src, tmp, result, CV_TM_CCORR);

	    DoublePointer minVal = new DoublePointer();
		DoublePointer maxVal = new DoublePointer();
	    CvPoint minLoc = new CvPoint();
	    CvPoint maxLoc = new CvPoint();

	    cvMinMaxLoc(result, minVal, maxVal, minLoc, maxLoc,null);
	    System.out.println(minVal + "" + maxVal + "" + result.highValue());
	    CvPoint point = new CvPoint();
	    point.x(maxLoc.x() + tmp.width());
	    point.y(maxLoc.y() + tmp.height());
	    
	    cvRectangle(src, maxLoc, point, CvScalar.GREEN, 2, 8, 0);
	    cvShowImage("Lena Image", src);
	    cvWaitKey(0);
	    cvReleaseImage(src);
	    cvReleaseImage(tmp);
	    cvReleaseImage(result);
	}

	// some usefull things.
	public static Scalar randColor() {
		int b, g, r;
		b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
		g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
		r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
		return new Scalar(b, g, r, 0);
	}

	public static List<Point> getPointsFromMatAboveThreshold(Mat m, float t) {
		List<Point> matches = new ArrayList<Point>();
		FloatIndexer indexer = m.createIndexer();
		for (int y = 0; y < m.rows(); y++) {
			for (int x = 0; x < m.cols(); x++) {
				if (indexer.get(y, x) > t) {
					System.out.println(
							"(" + x + "," + y + ") = " + indexer.get(y, x));
					matches.add(new Point(x, y));
				}
			}
		}
		return matches;
	}

	public static double HistMatch(String filePath, String compareFilePath) {
		IplImage baseImage = cvLoadImage(filePath);
		CvHistogram hist = getHueHistogram(baseImage);

		IplImage contrastImage = cvLoadImage(compareFilePath);
		CvHistogram hist1 = getHueHistogram(contrastImage);
		//CV_COMP_CHISQR
		double matchValue = cvCompareHist(hist, hist1, CV_COMP_INTERSECT);
		baseImage.release();
		contrastImage.release();
		hist.release();
		hist1.release();
		cvReleaseImage(baseImage);
		cvReleaseImage(contrastImage);
		baseImage = null;
		contrastImage = null;
		hist = null;
		hist1 = null;
		return matchValue;
	}

	public static CvHistogram getHueHistogram(IplImage image) {
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

	public static void main(String[] args) {
		//cvTemplateMatch(new String[]{"UIType/P2/2.png", "template/t_zb.png"});
		imgMatch(new String[]{"UIType/2.png", "template/t_yl.png"});
		//System.out.println(HistMatch("UIType/11.png", "UIType/11.png"));
	}
}
