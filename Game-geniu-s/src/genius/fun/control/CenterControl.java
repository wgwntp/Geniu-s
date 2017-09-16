/**
 * 
 */

package genius.fun.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import genius.fun.action.SingleAction;
import genius.fun.action.TeamAction;
import genius.fun.main.Dto;
import genius.fun.main.DtoP2;
import genius.fun.main.ImageProc;
import genius.fun.win32.Mouse;
import genius.fun.win32.Window;

/**
 * @author Lie
 *
 */

public class CenterControl {
	private Dto dto;
	private DtoP2 dtoP2;
	private ImageProc proc;
	public CenterControl() {
		proc = new ImageProc();
		dto = new Dto(proc);
		dtoP2 = new DtoP2(proc);
	}
	
	public void start() {
		int hwnd = Window.getHwnd("海马玩模拟器 0.10.6 Beta");
		if (hwnd <= 0) {
			return;
		}
		SingleAction single = new SingleAction(dto, proc);
		while (true) {
			try{
				single.yhSelf(hwnd);
				Thread.sleep(single.getSleepTime());
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void startTeam() {
		P1Thread p1 = new P1Thread();
		p1.start();
		P2Thread p2 = new P2Thread();
		p2.start();
	}
	
	class P1Thread extends Thread {
		@Override
		public void run() {
			int hwnd = Window.getHwnd("海马玩模拟器 0.10.6 Beta");
			if (hwnd <= 0) {
				return;
			}
			TeamAction team = new TeamAction(dto,dtoP2);
			while (true) {
				try{
					team.p1Action(hwnd);
					Thread.sleep(team.getP1SleepTime());
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	class P2Thread extends Thread {
		@Override
		public void run() {
			int hwnd = Window.getHwnd("海马玩模拟器_1 0.10.6 Beta");
			if (hwnd <= 0) {
				return;
			}
			TeamAction team = new TeamAction(dto,dtoP2);
			while (true) {
				try{
					Mouse.click(hwnd, 84, 181);
					Thread.sleep(team.getP2SleepTime());
					Mouse.click(hwnd, 664, 352);
					Thread.sleep(team.getP2SleepTime());
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
