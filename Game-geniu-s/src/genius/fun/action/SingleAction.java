/**
 * 
 */

package genius.fun.action;

import static org.bytedeco.javacpp.opencv_core.cvRelease;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;

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
			dto.setHwnd(hwnd);
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
			IplImage baseImage = cvLoadImage(f.getAbsolutePath());
			out.close();
			f.delete();
			int type = dto.getUIType(baseImage, beginType, Dto.END_SINGLE_YH);
			if (type == -1) {
				releaseImg(baseImage);
				return;
			}
			
			if (type == -2) {
				beginType = 0;
				releaseImg(baseImage);
				return;
			}
			//为了提高效率，前几个截图在后面进入御魂界面后用不到
			if(type < Dto.BEGIN_SINGLE_YH) {
				beginType = type;
			}
			String tempPath = Dto.typeToTemplate.get(type);
			if (tempPath != null) {
				if (tempPath.equals("ever")) {
					Random random = new Random(System.currentTimeMillis());
					int offset = random.nextInt() % 30;
					Mouse.click(hwnd, 100 + offset, 100 + offset);
				} else {
					Point clickPoint = proc.imgMatch(baseImage, tempPath );
					Mouse.click(hwnd, clickPoint.x, clickPoint.y);
				}
			
				if (type == 4 || type == 5) {
					sleepTime = 10000;
				} else {
					sleepTime = 2000;
				}
			}
			releaseImg(baseImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void yyhSelf(int hwnd) {

		try {
			dto.setHwnd(hwnd);
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
			System.out.println(f.getAbsolutePath());
			IplImage baseImage = cvLoadImage(f.getAbsolutePath());
			out.close();
			f.delete();
			int type = dto.getUIType(baseImage, beginType, Dto.END_SINGLE_YYH);
			if (type == -1) {
				releaseImg(baseImage);
				return;
			}
			
			if (type == -2) {
				beginType = 0;
				releaseImg(baseImage);
				return;
			}
			//为了提高效率，前几个截图在后面进入御魂界面后用不到
			if(type < Dto.BEGIN_SINGLE_YYH) {
				beginType = type;
			}
			String tempPath = Dto.typeToSingleYYHTemplate.get(type);
			if (tempPath != null) {
				if (tempPath.equals("ever")) {
					Random random = new Random(System.currentTimeMillis());
					int offset = random.nextInt() % 30;
					Mouse.click(hwnd, 100 + offset, 100 + offset);
				} else {
					Point clickPoint = proc.imgMatch(baseImage, tempPath );
					Mouse.click(hwnd, clickPoint.x, clickPoint.y);
				}
				
				if (type == 19 || type == 20) {
					sleepTime = 10000;
				} else if(type == 21 || type == 22) {
					sleepTime = 4000;
				} else {
					sleepTime = 2000;
				}
			}
			releaseImg(baseImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ylhbSelf(int hwnd) {

		try {
			dto.setHwnd(hwnd);
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
			System.out.println(f.getAbsolutePath());
			IplImage baseImage = cvLoadImage(f.getAbsolutePath());
			out.close();
			f.delete();
			int type = dto.getUIType(baseImage, beginType, Dto.END_SINGLE_YLHB);
			if (type == -1) {
				releaseImg(baseImage);
				return;
			}
			
			if (type == -2) {
				beginType = 0;
				releaseImg(baseImage);
				return;
			}
			//为了提高效率，前几个截图在后面进入御魂界面后用不到
			if(type < Dto.BEGIN_SINGLE_YLHB) {
				beginType = type;
			}
			String tempPath = Dto.typeToSingleYL3Template.get(type);
			System.out.println("type : " + type);
			if (tempPath.equals("ever")) {
				Random random = new Random(System.currentTimeMillis());
				int offset = random.nextInt() % 30;
				Mouse.click(hwnd, 100 + offset, 100 + offset);
			} else {
				Point clickPoint = proc.imgMatch(baseImage, tempPath );
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			if (type == 28 || type == 29) {
				sleepTime = 10000;
			} else if(type == 30 || type == 31) {
				sleepTime = 4000;
			} else {
				sleepTime = 2000;
			}
			releaseImg(baseImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ylbzzSelf(int hwnd) {

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
			System.out.println(f.getAbsolutePath());
			IplImage baseImage = cvLoadImage(f.getAbsolutePath());
			out.close();
			f.delete();
			int type = dto.getUIType(baseImage, beginType, Dto.END_SINGLE_YLBZZ);
			if (type == -1) {
				releaseImg(baseImage);
				return;
			}
			//为了提高效率，前几个截图在后面进入御魂界面后用不到
			if(type < Dto.BEGIN_SINGLE_YLBZZ) {
				beginType = type;
			}
			String tempPath = Dto.typeToSingleYL2Template.get(type);
			System.out.println("type : " + type);
			if (tempPath.equals("ever")) {
				Random random = new Random(System.currentTimeMillis());
				int offset = random.nextInt() % 30;
				Mouse.click(hwnd, 100 + offset, 100 + offset);
			} else {
				Point clickPoint = proc.imgMatch(baseImage, tempPath );
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			if (type == 32 || type == 33) {
				sleepTime = 10000;
			} else if(type == 34 || type == 35) {
				sleepTime = 4000;
			} else {
				sleepTime = 2000;
			}
			releaseImg(baseImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ylSelf(int hwnd) {

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
			System.out.println(f.getAbsolutePath());
			IplImage baseImage = cvLoadImage(f.getAbsolutePath());
			out.close();
			f.delete();
			int type = dto.getUIType(baseImage, beginType, Dto.END_SINGLE_YLSL);
			if (type == -1) {
				releaseImg(baseImage);
				return;
			}
			//为了提高效率，前几个截图在后面进入御魂界面后用不到
			if(type < Dto.BEGIN_SINGLE_YLSL) {
				beginType = type;
			}
			String tempPath = Dto.typeToSingleYL1Template.get(type);
			if (tempPath.equals("ever")) {
				Random random = new Random(System.currentTimeMillis());
				int offset = random.nextInt() % 30;
				Mouse.click(hwnd, 100 + offset, 100 + offset);
			} else {
				Point clickPoint = proc.imgMatch(baseImage, tempPath );
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			
			if (type == 24 || type == 25) {
				sleepTime = 10000;
			} else if(type == 26 || type == 27) {
				sleepTime = 4000;
			} else {
				sleepTime = 2000;
			}
			releaseImg(baseImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void releaseImg(IplImage img ) {
		img.release();
		cvRelease(img);
		img = null;
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
