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

public class Dto {
	
	private static int TYPE_MAIN = 1;
	private static int TYPE_TS = 2;
	private static int TYPE_YH = 3;
	private static int TYPE_DS= 4;
	private static int TYPE_YHZB = 5;
	private static int TYPE_YHCG = 6;
	private static int TYPE_YHSB = 7;
	private static int TYPE_T_CJDW = 8;
	private static int TYPE_T_CJ = 9;
	private static int TYPE_T_ZDMAIN = 10;
	private static int TYPE_T_YQ = 11;
	private static int TYPE_T_KS = 12;
	private static int TYPE_T_ZB = 13;
	private static int TYPE_T_CG = 14;
	private static int TYPE_T_JX = 15;
	private static int TYPE_T_SB = 16;
	private static int TYPE_T_SBJX = 17;
	private static int TYPE_T_SL = 18;
	private static int TYPE_YYH = 19;
	private static int TYPE_YYH_ZB = 20;
	private static int TYPE_YYH_SL = 21;
	private static int TYPE_YYH_SB = 22;
	private static int TYPE_YL_XZ = 23;
	private static int TYPE_YL_TZSL = 24;
	private static int TYPE_YL_ZB = 25;
	private static int TYPE_YL_SL = 26;
	private static int TYPE_YL_SB = 27;
	
	public static int BEGIN_SINGLE_YH = 4;
	public static int END_SINGLE_YH = 7;
	
	public static int BEGIN_TEAM_YH = 12;
	public static int END_TEAM_YH = 18;
	
	public static int BEGIN_SINGLE_YYH = 19;
	public static int END_SINGLE_YYH = 22;
	
	public static int BEGIN_SINGLE_YL = 24;
	public static int END_SINGLE_YL = 27;
	
	private String[] uiImgPath = {
			"UIType/1.png",
			"UIType/2.png",
			"UIType/3.png",
			"UIType/4.png",
			"UIType/5.png",
			"UIType/6.png",
			"UIType/7.png",
			"UIType/8.png",
			"UIType/9.png",
			"UIType/10.png",
			"UIType/11.png",
			"UIType/12.png",
			"UIType/13.png",
			"UIType/14.png",
			"UIType/15.png",
			"UIType/16.png",
			"UIType/17.png",
			"UIType/18.png",
			"UIType/19.png",
			"UIType/20.png",
			"UIType/21.png",
			"UIType/22.png",
			"UIType/23.png",
			"UIType/24.png",
			"UIType/25.png",
			"UIType/26.png",
			"UIType/27.png",
	};
	
	private Map<Integer, CvHistogram> uiImgHists = new HashMap<>();
	
	public static Map<Integer, String> typeToTemplate = new HashMap<>();
	public static Map<Integer, String> typeToTemplateTeam = new HashMap<>();
	public static Map<Integer, String> typeToSingleYYHTemplate = new HashMap<>();
	public static Map<Integer, String> typeToSingleYLTemplate = new HashMap<>();
	static {
		typeToTemplate.put(TYPE_MAIN, "template/t_ts.png");
		typeToTemplate.put(TYPE_TS, "template/t_yh.png");
		typeToTemplate.put(TYPE_YH, "template/t_ds.png");
		typeToTemplate.put(TYPE_DS, "template/t_tz.png");
		typeToTemplate.put(TYPE_YHZB, "template/t_zb.png");
		typeToTemplate.put(TYPE_YHCG, "ever");
		typeToTemplate.put(TYPE_YHSB, "ever");
		//team
		typeToTemplateTeam.put(TYPE_MAIN, "template/t_ts.png");
		typeToTemplateTeam.put(TYPE_TS, "template/t_yh.png");
		typeToTemplateTeam.put(TYPE_YH, "template/t_ds.png");
		typeToTemplateTeam.put(TYPE_DS, "template/t_zd.png");
		typeToTemplateTeam.put(TYPE_T_CJDW, "template/t_cjdw.png");
		typeToTemplateTeam.put(TYPE_T_CJ, "template/t_cj.png");
		typeToTemplateTeam.put(TYPE_T_ZDMAIN, "template/t_yq.png");
		typeToTemplateTeam.put(TYPE_T_YQ, "muti template/t_dszd.png template/t_yq2.png");
		typeToTemplateTeam.put(TYPE_T_KS, "template/t_kszd.png");
		typeToTemplateTeam.put(TYPE_T_ZB, "template/t_zb.png");
		typeToTemplateTeam.put(TYPE_T_CG, "ever");
		typeToTemplateTeam.put(TYPE_T_JX, "template/t_qd.png");
		typeToTemplateTeam.put(TYPE_T_SB, "ever");
		typeToTemplateTeam.put(TYPE_T_SBJX, "template/t_qd.png");
		typeToTemplateTeam.put(TYPE_T_SL, "ever");
		
		typeToSingleYYHTemplate.put(TYPE_MAIN, "template/t_ts.png");
		typeToSingleYYHTemplate.put(TYPE_TS, "template/t_yh.png");
		typeToSingleYYHTemplate.put(TYPE_YH, "template/t_yyh.png");
		typeToSingleYYHTemplate.put(TYPE_YYH, "template/t_yyhtz.png");
		typeToSingleYYHTemplate.put(TYPE_YYH_ZB, "template/t_zb.png");
		typeToSingleYYHTemplate.put(TYPE_YYH_SL, "ever");
		typeToSingleYYHTemplate.put(TYPE_YYH_SB, "ever");
		
		typeToSingleYLTemplate.put(TYPE_MAIN, "template/t_ts.png");
		typeToSingleYLTemplate.put(TYPE_TS, "template/t_yl.png");
		typeToSingleYLTemplate.put(TYPE_YL_XZ, "template/t_sl.png");
		typeToSingleYLTemplate.put(TYPE_YL_TZSL, "template/t_yltz.png");
		typeToSingleYLTemplate.put(TYPE_YL_ZB, "template/t_zb.png");
		typeToSingleYLTemplate.put(TYPE_YL_SL, "ever");
		typeToSingleYLTemplate.put(TYPE_YL_SB, "ever");
	} 
	
	private ImageProc proc;
	
	public Dto(ImageProc proc) {
		this.proc = proc;
	}
	
	public void init() {
		System.out.println("Initing ...");
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < uiImgPath.length; ++i) {
			uiImgHists.put(i, JavaCVUtil.getHueHistogram(cvLoadImage(uiImgPath[i])));
		}
		System.out.println("Init finish ! Time :" + (System.currentTimeMillis() - startTime) + "ms");
	}

	public int getUIType(IplImage baseImage, int beginType, int endType) {
		if (uiImgHists.size() == 0) {
			System.out.println("Please Init Dto !");
			return -1;
		}
		int res = -1;
		double highest = 0;
		Map<Double, Integer> relation = new HashMap<>();
		for(int i = beginType ; i < endType; ++i) {
			double point = proc.histMatch(uiImgHists.get(i), baseImage);
			highest = Math.max(highest, point);
			relation.put(point, i+1);
			if (highest > 0.90) {
				break;
			}
		}
		res = relation.get(highest);
		System.out.println("P1 > Match : " + res + " Point : " + highest);
		//
		if (res == 1 || res == 2 || res == 6) {
			if (highest > 0.6) {
				return res;
			}
		} else if(res == 11 || res ==14 || res == 18 || res == 21 ||res == 22) {
			if(highest > 0.75) {
				return res;
			}
		} else if(res == 20) {
			if (highest > 0.95) {
				return res;
			}
		} else {
			if (highest > 0.90) {
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
