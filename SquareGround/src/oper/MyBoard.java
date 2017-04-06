package oper;

public final class MyBoard implements Operation.Board {
	
	
	public final int n,m;
	private int data[][];
	
	public MyBoard(int n,int m){
		this.n=n;
		this.m=m;
		this.data=new int[n][m];
	}
	

	@Override
	public int get(int i,int j){return data[i][j];}
	@Override
	public int rows() {return n;}
	@Override
	public int cols() {return m;}
	
	public MyBoard set(int i,int j,int x){data[i][j]=x;return this;}


	
	
	
}
