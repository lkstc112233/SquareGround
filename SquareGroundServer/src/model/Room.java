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
		this.game=new Game(n,m,this.players.size(),this.players);
		this.game.start();
	}
	

	public Game game=null;
	
	public class Game extends Thread{
		//TODO Game
		private final int n,m;
		private int board[][];
		private final int numberPlayerAdd;
		
		protected Game(int n,int m,int numberPlayerAdd,Set<Player> players){
			this.n=n;
			this.m=m;
			this.board=new int[n][m];
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					board[i][j]=0;
				}
			}
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
		
		private boolean checkRun(){
			for(Pair<Integer,Point> p:points.values())
				if(p.y!=null) return true;
			return false;
		}

		static public final long dt=1000;
		@Override
		public void run(){
			while(checkRun()){
				long st=System.currentTimeMillis();
				for(Pair<Integer,Point> p:points.values())
					synchronized(p.y){
						if(p.y!=null){
							//alive player
							Pair<Integer,Integer> tmp=new Pair<Integer,Integer>
								(p.y.x,p.y.y);
							p.y.move();
							
						}
					}
				long end=System.currentTimeMillis();
				if(end-st<dt){
					try {
						Thread.sleep(dt-end+st);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		@Override
		public String toString(){
			List<Object>res=new ArrayList<Object>();
			res.add(n);res.add(m);
			res.add(this.numberPlayerAdd);
			for(int i=0;i<n;i++)for(int j=0;j<m;j++)
				res.add(board[i][j]);
			return web.WebCommunityInterface.toString(res.toArray());
		}
		
		public void operateLeft(Player p){
			//int index=this.points.get(p).x;
			Point pp=this.points.get(p).y;
			synchronized(pp){
				pp.dir=Point.Direction.L;
			}
		}
		public void operateRight(Player p){
			Point pp=this.points.get(p).y;
			synchronized(pp){
				pp.dir=Point.Direction.R;
			}
		}
		public void operateUp(Player p){
			Point pp=this.points.get(p).y;
			synchronized(pp){
				pp.dir=Point.Direction.U;
			}
		}
		public void operateDown(Player p){
			Point pp=this.points.get(p).y;
			synchronized(pp){
				pp.dir=Point.Direction.D;
			}
		}
	}
	
	
}
