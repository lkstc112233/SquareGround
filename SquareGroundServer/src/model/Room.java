package model;

import java.util.*;

public class Room {
	public final String code;
	private Room(String code){
		this.code=code;
	}
	
	static public final int CodeLength=6;
	static public final String charset="abcdefghijklmnopqrstuvwxyz1234567890";
	
	static public Room createRoom(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<CodeLength;i++){
			int x=(int)(Math.random()*charset.length());
			sb.append(charset.charAt(x));
		}
		return new Room(sb.toString());
	}
	
	private final Set<Player> players=new HashSet<Player>();
	public synchronized void addPlayer(Player p){
		players.add(p);
	}
	public synchronized boolean containsPlayer(Player p){
		return players.contains(p);
	}
	public synchronized void removePlayer(Player p){
		if(this.containsPlayer(p))
			players.remove(p);
	}
	
	
	
	
	
	
}
