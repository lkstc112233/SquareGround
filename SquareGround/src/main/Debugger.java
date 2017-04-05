package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;

public class Debugger {
	
	private boolean showdebugmsg=false;
		public boolean isShowingDebugMsg(){return this.showdebugmsg;}
		public Debugger startShowingDebugMsg(){
			this.showdebugmsg=true;
			this.debug("start debugging...");
			return this;
		}
		public void pauseShowingDebugMsg(){this.showdebugmsg=false;}
	
	private int size;
	public final LinkedBlockingQueue<String> msgs;
	
	public Debugger(){
		this(20);
	}
	public Debugger(int size){
		this.size=size>0?size:0;
		msgs=new LinkedBlockingQueue<String>(this.size);
	}
	
	public void debug(String msg){
		if(msg==null) return;
		if(!msgs.offer(msg)){
			msgs.poll();
			this.debug(msg);
		}
	}
	
	private Font font=new Font("Times New Roman",Font.BOLD,15);
		public void setFont(Font f){font=f;}
	private Color color=Color.RED;
		public void setColor(Color c){color=c;}
	private int margin=10;
	
	public void draw(JPanel panel,Graphics g){
		if(!showdebugmsg) return;
		if(this.msgs.isEmpty()) return;
		Graphics2D g2d=(Graphics2D)g;
		g.setFont(font);
		g.setColor(color);
		FontRenderContext context=g2d.getFontRenderContext();
		Rectangle2D bounds;
		int y=panel.getHeight()-margin;
		String[] msgs = new String[this.size];
		this.msgs.toArray(msgs);
		for(int i=this.msgs.size();(--i)>=0;){
			bounds=font.getStringBounds(msgs[i],context);
			y-=bounds.getHeight()+bounds.getY();
			g.drawString(msgs[i],0,y);
			y-=bounds.getHeight()+bounds.getY()+margin;
		}
	}
	
	
	
}
