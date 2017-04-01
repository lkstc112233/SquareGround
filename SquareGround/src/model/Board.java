package model;

public final class Board {
	
	
	public final int n,m;
	private int data[][];
	
	public Board(int n,int m){
		this.n=n;
		this.m=m;
		this.data=new int[n][m];
	}
	
	
	public int get(int i,int j){return data[i][j];}
	public Board set(int i,int j,int x){data[i][j]=x;return this;}
	
	
	
}
