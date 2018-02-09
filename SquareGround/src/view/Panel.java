package view;

import java.awt.Graphics;


public abstract class Panel extends Content{
	private static final long serialVersionUID = 8478812272127889524L;
	
	@Override
	public void refresh(double dt){
		this.refresh();
	}
	
	@Override
	public final void paint(Graphics g){
		super.paint(g);
		this.draw(g);
		this.frame.drawDebug(g);
	}
	
	protected abstract void draw(Graphics g);
	
	
	

}
