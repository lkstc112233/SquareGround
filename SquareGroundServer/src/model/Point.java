package model;

public class Point extends Pair<Integer,Integer>{
	public Direction dir;
	
	static enum Direction{
		L(-1,0),R(1,0),U(0,1),D(0,-1)
		;
		final int dx,dy;
		Direction(int dx,int dy){
			this.dx=dx;
			this.dy=dy;
		}
	}
	
	public Point(int x,int y){
		super(x,y);
	}
	public void move(){
		super.x+=dir.dx;
		super.y+=dir.dy;
	}
}
