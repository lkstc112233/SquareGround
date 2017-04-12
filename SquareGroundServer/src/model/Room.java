package model;

import java.util.*;

public final class Room implements Comparable<Room>{
	public final String code;
	protected Room(String code,int MaxmumOfPeopleInRoom){
		this.code=code;
		this.MaxmumOfPeopleInRoom=MaxmumOfPeopleInRoom;
	}
	protected Room(String code){
		this(code,3);
	}
	
	public final int MaxmumOfPeopleInRoom;

	static public final int CodeLength=6;
	static public final String charset="abcdefghijklmnopqrstuvwxyz1234567890";
	
	
	
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(o instanceof Room)
			return ((Room) o).code.equals(this.code);
		return false;
	}
	@Override
	public int compareTo(Room r){
		return this.code.compareTo(r.code);
	}
	
	
	private final Set<Player> players=new HashSet<Player>();
	protected synchronized boolean addPlayer(Player p){
		if(players.size()>=MaxmumOfPeopleInRoom) return false;
		players.add(p);
		p.room=this;
		return true;
	}
	protected synchronized boolean containsPlayer(Player p){
		return players.contains(p);
	}
	protected synchronized void removePlayer(Player p){
		if(this.containsPlayer(p))
			players.remove(p);
		p.room=null;
	}
	
	public synchronized Set<Player> getAllPlayers(){
		return this.players;
	}
	
	
	public void GameStart(int n,int m){
		this.game=new Game(n,m,this.players.size(),this.players.size());
	}
	

	public Game game=null;
	
	public class Game{
		//TODO Game
		private final int num;
		private int board[][];
		private final int numberPlayerAdd;
		
		protected Game(int n,int m,int num,int numberPlayerAdd,Set<Player> players){
			this.board=new int[n][m];
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					board[i][j]=0;
				}
			}
			this.num=num;
			this.numberPlayerAdd=numberPlayerAdd;
			this.points=new HashMap<Player,Pair<Integer,Point>>();
			List<Point> startPoints=new ArrayList<Point>();
			for(Player p:players){
				Point pt=new Point(0,0);
				
				startPoints.add(pt);
			}
			int index=1;
			for(Player p:players){
				points.put(p,new Pair<Integer, Point>(index,startPoints.get(index)));
				index++;
			}
		}
		
		private final Map<Player,Pair<Integer,Point>> points;
		
		@Override
		public String toString(){
			StringBuilder sb=new StringBuilder();
			return sb.toString();
		}
		
		public void operateLeft(Player p){
			int index=Room.this.players.
		}
		public void operateRight(Player p){
			
		}
		public void operateUp(Player p){
			
		}
		public void operateDown(Player p){
			
		}
	}
	
	
}
