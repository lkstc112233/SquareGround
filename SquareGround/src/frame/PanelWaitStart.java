package frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import main.Helper;


public class PanelWaitStart extends Panel{
	private static final long serialVersionUID = 8478812272127889524L;

	private Font font;
	public PanelWaitStart(){
		this(new Font("Times New Roman",Font.BOLD,15));
	}
	public PanelWaitStart(Font font){
		this.font=font;
	}
	
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2d=(Graphics2D)g;
		g.setFont(font);
		g.setColor(Helper.ColorString);
		FontRenderContext context=g2d.getFontRenderContext();
		String msg;
		if(this.frame.oper.getRoom()!=null)
			msg=""+this.frame.oper.getRoom().getCode()+":"+this.frame.oper.getRoom().getNumberOfPeopleInRoom()+" / "+this.frame.oper.getRoom().getMaxmumOfPeopleInRoom();
		else
			msg="waiting for login";
		Rectangle2D bounds=font.getStringBounds(msg,context);
		int x=(this.getWidth()-(int)(bounds.getWidth()+bounds.getX()))>>1;
		int y=(this.getHeight()-(int)(bounds.getHeight()+bounds.getY()))>>1;
		g.drawString(msg,x,y);
	}
	
	
	

}
