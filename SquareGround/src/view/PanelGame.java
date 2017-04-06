package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import main.Helper;
import oper.Operation.Board;


public class PanelGame extends Panel{
	private static final long serialVersionUID = -7360109882668050142L;
	
	private Board board;
		public void recieveBoard(Board board){this.board=board;}
	protected Determinant thread;
	
	private Font font;
	public PanelGame(){
		this(new Font("Times New Roman",Font.BOLD,15));
	}
	public PanelGame(Font font){
		this.font=font;
		thread=new Determinant();
		thread.setRefreshable(this);
	}
	
	
	@Override
	public void draw(Graphics g){
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
	
	
	public void drawBoard(Graphics g){
		if(board==null)
			return;
		int n=board.rows();
		int m=board.cols();
		//TODO draw board
	}
	

}
