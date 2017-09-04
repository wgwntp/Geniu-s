/**
 * 
 */

package genius.fun.control;

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

public class CenterControl {
	private Dto dto;
	private ImageProc proc;
	private int sleepTime = 2000;
	public CenterControl() {
		proc = new ImageProc();
		dto = new Dto(proc);
	}
	
	public void start() {
		int hwnd = Window.getHwnd("海马玩模拟器 0.10.6 Beta");
		if (hwnd <= 0) {
			return;
		}
		while (true) {
			try{
				yhSelf(hwnd);
				Thread.sleep(sleepTime);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void yhSelf(int hwnd) {
		try {
			File f = File.createTempFile("snapshoot", ".bmp");
			//System.out.println(f.getAbsolutePath());
			// 写出位图
			BufferedImage buffImage = Window.getImage(hwnd);
			if (buffImage == null) {
				f.delete();
				return;
			}
			FileOutputStream out = new FileOutputStream(f);
			ImageIO.write(buffImage, "bmp", out);
			int type = dto.getUIType(f.getAbsolutePath());
			if(type == -1) {
				out.close();
				f.delete();
				return;
			}
			System.out.println("TypeMatch : " + type);
			String tempPath = Dto.typeToTemplate.get(type);
			if (tempPath.equals("ever")) {
				Mouse.click(hwnd, 500, 500);
			} else {
				Point clickPoint = proc.imgMatch(new String[]{f.getAbsolutePath(), tempPath});
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			out.close();
			f.delete();
			if(type == 4 || type == 5) {
				sleepTime = 10000;
			} else {
				sleepTime = 2000;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
