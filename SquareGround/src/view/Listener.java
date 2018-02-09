package view;

import java.awt.Component;
import java.awt.event.*;

public abstract class Listener implements FrameSon,MouseListener,MouseMotionListener,MouseWheelListener,KeyListener{
	
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	public void mouseDragged(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	
	public void mouseWheelMoved(MouseWheelEvent e){}
	
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	
	public final Listener addTo(Component c){
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.addMouseWheelListener(this);
		c.addKeyListener(this);
		return this;
	}
	
	protected Frame frame;
	@Override
	public Listener setFrame(Frame frame){
		this.frame=frame;
		return this;
	}
}
