/**
 * 
 */

package genius.fun.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import genius.fun.main.Dto;
import genius.fun.main.DtoP2;
import genius.fun.main.ImageProc;
import genius.fun.win32.Mouse;
import genius.fun.win32.Point;
import genius.fun.win32.Window;

/**
 * @author Lie
 *
 */

public class TeamAction {
	private Dto dto;
	
	private DtoP2 dtoP2;

	private int beginType = 0;

	int p1SleepTime = 3000;
	int p2SleepTime = 4000;

	public TeamAction(Dto dto,DtoP2 dtoP2) {
		this.dto = dto;
		this.dtoP2 = dtoP2;
	}

	public void p1Action(int hwnd) {
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
			int type = dto.getUIType(f.getAbsolutePath(), beginType,
					Dto.END_TEAM_YH);
			if (type == -1) {
				out.close();
				f.delete();
				return;
			}

			if (type < Dto.BEGIN_TEAM_YH) {
				beginType = type;
			}

			String tempPath = Dto.typeToTemplateTeam.get(type);
			if (tempPath.equals("ever")) {
				Mouse.click(hwnd, 100, 100);
			} else if (tempPath.indexOf("muti") == 0) {
				String[] paths = tempPath.split("\\s");
				for (int i = 1; i < paths.length; ++i) {
					Point clickPoint = dto.getProc().imgMatch(
							new String[] {f.getAbsolutePath(), paths[i] });
					Mouse.click(hwnd, clickPoint.x, clickPoint.y);
					Thread.sleep(2000);
				}
			} else {
				Point clickPoint = dto.getProc().imgMatch(
						new String[] {f.getAbsolutePath(), tempPath });
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			if (type == 12 || type == 13) {
				p1SleepTime = 10000;
			} else {
				p1SleepTime = 3000;
			}
			out.close();
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void p2Action(int hwnd) {
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
			int type = dtoP2.getUIType(f.getAbsolutePath());
			if (type == -1) {
				out.close();
				f.delete();
				return;
			}
			String tempPath = DtoP2.typeToTemplateP2.get(type);
			if (tempPath.equals("ever")) {
				Mouse.click(hwnd, 100, 100);
			} else {
				Point clickPoint = dtoP2.getProc().imgMatch(
						new String[] {f.getAbsolutePath(), tempPath });
				Mouse.click(hwnd, clickPoint.x, clickPoint.y);
			}
			
			//p1SleepTime = 10000;
	
			out.close();
			f.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getter method
	 * @return the p1SleepTime
	 */
	
	public int getP1SleepTime() {
		return p1SleepTime;
	}

	/**
	 * setter method
	 * @param p1SleepTime the p1SleepTime to set
	 */
	
	public void setP1SleepTime(int p1SleepTime) {
		this.p1SleepTime = p1SleepTime;
	}

	/**
	 * getter method
	 * @return the p2SleepTime
	 */
	
	public int getP2SleepTime() {
		return p2SleepTime;
	}

	/**
	 * setter method
	 * @param p2SleepTime the p2SleepTime to set
	 */
	
	public void setP2SleepTime(int p2SleepTime) {
		this.p2SleepTime = p2SleepTime;
	}
	
}
