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
	
	private static String[] uiImgPath = {
			"UIType/1.png",
			"UIType/2.png",
			"UIType/3.png",
			"UIType/4.png",
			"UIType/5.png",
			"UIType/6.png",
			"UIType/7.png"
	};
	
	public static Map<Integer, String> typeToTemplate = new HashMap<>();
	static {
		typeToTemplate.put(TYPE_MAIN, "template/t_ts.png");
		typeToTemplate.put(TYPE_TS, "template/t_yh.png");
		typeToTemplate.put(TYPE_YH, "template/t_ds.png");
		typeToTemplate.put(TYPE_DS, "template/t_tz.png");
		typeToTemplate.put(TYPE_YHZB, "template/t_zb.png");
		typeToTemplate.put(TYPE_YHCG, "ever");
		typeToTemplate.put(TYPE_YHSB, "ever");
	} 
	
	private ImageProc proc;
	
	public Dto(ImageProc proc) {
		this.proc = proc;
	}
	
	public int getUIType(String path) {
		int res = -1;
		double highest = 0;
		Map<Double, Integer> relation = new HashMap<>();
		for(int i = 0 ; i < uiImgPath.length; ++i) {
			double point = proc.histMatch(uiImgPath[i], path);
			highest = Math.max(highest, point);
			relation.put(point, i+1);
		}
		res = relation.get(highest);
		//
		if (res == 2 || res == 6) {
			if (highest > 0.6) {
				return res;
			}
		} else {
			if (highest > 0.91) {
				return res;
			}
		}
		
		
		return -1;
	}
}
