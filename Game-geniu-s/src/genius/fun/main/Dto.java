/**
 * 
 */

package genius.fun.main;

import java.util.HashMap;
import java.util.Map;

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
	
	public static int BEGIN_SINGLE_YH = 4;
	public static int END_SINGLE_YH = 7;
	
	public static int BEGIN_TEAM_YH = 12;
	public static int END_TEAM_YH = 18;
	
	private static String[] uiImgPath = {
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
			"UIType/18.png"
	};
	
	public static Map<Integer, String> typeToTemplate = new HashMap<>();
	public static Map<Integer, String> typeToTemplateTeam = new HashMap<>();
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
	} 
	
	private ImageProc proc;
	
	public Dto(ImageProc proc) {
		this.proc = proc;
	}
	
	public int getUIType(String path, int beginType, int endType) {
		int res = -1;
		double highest = 0;
		Map<Double, Integer> relation = new HashMap<>();
		for(int i = beginType ; i < endType; ++i) {
			double point = proc.histMatch(uiImgPath[i], path);
			highest = Math.max(highest, point);
			relation.put(point, i+1);
			if (highest > 0.91) {
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
		} else if(res == 11 || res ==14 || res == 18) {
			if(highest > 0.75) {
				return res;
			}
		} else {
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
