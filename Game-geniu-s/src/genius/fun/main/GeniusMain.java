package genius.fun.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import genius.fun.win32.Window;

public class GeniusMain {
	public static void main(String[] args) {
		int hwnd = Window.getHwnd("海马玩模拟器 0.10.6 Beta");
		if (hwnd <= 0) {
			return;
		}
		
		try {
			File f = File.createTempFile("snapshoot", ".bmp");
			System.out.println(f.getAbsolutePath());
			// 写出位图
			BufferedImage buffImage = Window.getImage(hwnd);
			if (buffImage == null) {
				return;
			}
			ImageIO.write(buffImage, "bmp", new FileOutputStream(f));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
