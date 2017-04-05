package frame;

import java.awt.event.KeyEvent;

public class MyListener extends Listener implements FrameSon{
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_UP){
			this.frame.oper.getPlayer().up();
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			this.frame.oper.getPlayer().down();
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			this.frame.oper.getPlayer().left();
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			this.frame.oper.getPlayer().right();
		}else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			this.frame.oper.getPlayer().logoutRoom();
		}
	}
	
}
