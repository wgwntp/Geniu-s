/**
 * 
 */

package genius.fun.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import genius.fun.main.Dto;
import genius.fun.main.ImageProc;
import genius.fun.win32.Mouse;
import genius.fun.win32.Point;
import genius.fun.win32.Window;

/**
 * @author Lie
 *
 */

public class SingleAction {

	private Dto dto;
	
	private ImageProc proc;

	private int sleepTime = 2000;

	private int beginType = 0;

	public SingleAction(Dto dto, ImageProc proc) {
		this.dto = dto;
		this.proc = proc;
	}

	public void yhSelf(int hwnd) {

		try {
			File f = File.createTempFile("snapshoot", ".bmp");
			// System.out.println(f.getAbsolutePath());
			// 写出位图
			BufferedImage buffImage = Window.getImage(hwnd);
			if (buffImage == null) {
				f.delete();
				return;
			}
			FileOutputStream out = new FileOutputStream(f);
			ImageIO.write(buffImage, "bmp", out);
			int type = dto.getUIType(f.getAbsolutePath(), beginType, Dto.END_SINGLE_YH);
			if (type == -1) {
				out.close();
				f.delete();
				return;
			}
			//为了提高效率，前几个截图在后面进入御魂界面后用不到
			if(type < Dto.BEGIN_SINGLE_YH) {
				beginType = type;
			}
			String tempPath = Dto.typeToTemplate.get(type);
			if (tempPath.equals("ever")) {
				Mouse.click(hwnd, 100, 100);
			} else {
				Point clickPoint = proc.imgMatch(
						new String[] {f.getAbsolutePath(), tempPath });
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			out.close();
			f.delete();
			if (type == 4 || type == 5) {
				sleepTime = 10000;
			} else {
				sleepTime = 2000;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * getter method
	 * 
	 * @return the sleepTime
	 */

	public int getSleepTime() {
		return sleepTime;
	}

	/**
	 * setter method
	 * 
	 * @param sleepTime
	 *            the sleepTime to set
	 */

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	/**
	 * getter method
	 * 
	 * @return the beginType
	 */

	public int getBeginType() {
		return beginType;
	}

	/**
	 * setter method
	 * 
	 * @param beginType
	 *            the beginType to set
	 */

	public void setBeginType(int beginType) {
		this.beginType = beginType;
	}

}
