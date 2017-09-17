/**
 * 
 */

package genius.fun.main;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

import java.util.HashMap;
import java.util.Map;

import org.bytedeco.javacpp.opencv_core.CvHistogram;
import org.bytedeco.javacpp.opencv_core.IplImage;

import genius.fun.util.JavaCVUtil;

/**
 * @author Lie
 *
 */

public class DtoP2 {
	private static int P2_TYPE_T_MAIN = 1;
	private static int P2_TYPE_T_ZB = 2;
	private static int P2_TYPE_T_SL = 3;
	private static int P2_TYPE_T_CG = 4;
	private static int P2_TYPE_T_SB = 5;
	
	private static String[] p2UiImgPath = {
			"UIType/P2/1.png",
			"UIType/P2/2.png",
			"UIType/P2/3.png",
			"UIType/P2/4.png",
			"UIType/P2/5.png"
	};
	private Map<Integer, CvHistogram> uiImgHists = new HashMap<>();
	
	
	public static Map<Integer, String> typeToTemplateP2 = new HashMap<>();
	
	static {
		typeToTemplateP2.put(P2_TYPE_T_MAIN, "template/t_jr.png");
		typeToTemplateP2.put(P2_TYPE_T_ZB, "template/t_zb.png");
		typeToTemplateP2.put(P2_TYPE_T_SL, "ever");
		typeToTemplateP2.put(P2_TYPE_T_CG, "ever");
		typeToTemplateP2.put(P2_TYPE_T_SB, "ever");
	}
	
private ImageProc proc;
	
	public DtoP2(ImageProc proc) {
		this.proc = proc;
	}
	
	public void init() {
		System.out.println("Initing ...");
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < p2UiImgPath.length; ++i) {
			uiImgHists.put(i, JavaCVUtil.getHueHistogram(cvLoadImage(p2UiImgPath[i])));
		}
		System.out.println("Init finish ! Time :" + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	
	public int getUIType(IplImage baseImage) {
		int res = -1;
		double highest = 0;
		Map<Double, Integer> relation = new HashMap<>();
		for(int i = 0 ; i < p2UiImgPath.length; ++i) {
			double point = proc.histMatch(uiImgHists.get(i), baseImage);
			highest = Math.max(highest, point);
			relation.put(point, i+1);
			if (highest > 0.91) {
				break;
			}
		}
		res = relation.get(highest);
		System.out.println("P2 > Match : " + res + " Point : " + highest);
		//
		if (res == 1) {
			if(highest > 0.97) {
				return res;
			}
		} else if(res == 4) {
			if(highest > 0.75) {
				return res;
			}
		}else {
			if (highest > 0.91) {
				return res;
			}
		}
		
		return -1;
	}
	
	/**
	 * getter method
	 * @return the proc
	 */
	
	public ImageProc getProc() {
		return proc;
	}

	/**
	 * setter method
	 * @param proc the proc to set
	 */
	
	public void setProc(ImageProc proc) {
		this.proc = proc;
	}
}
