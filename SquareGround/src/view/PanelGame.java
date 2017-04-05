package view;

import java.awt.*;

import main.Helper;
import oper.Operation.Board;


public class PanelGame extends Panel{
	private static final long serialVersionUID = -7360109882668050142L;
	
	private Board board;
		public void recieveBoard(Board board){this.board=board;}
	protected Determinant thread;
	
	public PanelGame(){
		thread=new Determinant();
		thread.setRefreshable(this);
	}
	
	
	@Override
	public void draw(Graphics g){
		this.drawBoard(g);
	}
	
	
	public void drawBoard(Graphics g){
		if(board==null)
			return;
		Graphics2D g2d=(Graphics2D)g;
		int n=board.rows();
		int m=board.cols();
		int w=this.getWidth();
		int h=this.getHeight();
		int a=(int)Math.min(w*0.9/m,h*0.9/n);
		int margin_w=(w-a*m)/2;
		int margin_h=(h-a*n)/2;
		
		for(int i=0;i<n;i++) for(int j=0;j<m;j++){
			int x=board.get(i,j)-1;
			int statu=0;
			if(x>Helper.numberPlayerAdd){
				x-=Helper.numberPlayerAdd;
				statu=1;
			}else if(x<0){
				x=-x;
				statu=-1;
			}
			if(x<0 || x>=Helper.ColorPlayer.size())
				continue;
			if(statu==0){//fill
				g2d.setColor(Helper.ColorPlayer.get(x));
				g2d.fillRect(margin_w+j*a,margin_h+i*a,a,a);
			}
			else if(statu<0){//road
				g2d.setColor(Helper.ColorPlayer_road.get(x));
				g2d.fillRect(margin_w+j*a,margin_h+i*a,a,a);
			}else{//role
				g2d.setColor(Helper.ColorPlayer.get(x));
				g2d.fill3DRect(margin_w+j*a,margin_h+i*a,a,a,true);
				g2d.setColor(Color.RED);
				g2d.fill3DRect(margin_w+j*a+(a-4)/2,margin_h+i*a+(a-4)/2,4,4,true);
			}
		}
		
		g2d.setColor(Helper.ColorBoardEdge);
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.drawLine(margin_w,margin_h,w-margin_w,0);
		g2d.drawLine(margin_w,margin_h,0,h-margin_h);
		g2d.drawLine(margin_w,0,w-margin_w,h-margin_h);
		g2d.drawLine(0,margin_h,w-margin_w,h-margin_h);
		g2d.setStroke(new BasicStroke());
	}
	

}
