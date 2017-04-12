package model;

import java.util.*;
import web.MyServer.MySocket;

import static model.Room.charset;
import static model.Room.CodeLength;

public final class Hall {
	private Hall(){
		
	}
	
	static public final Hall instance=new Hall();
	
	protected Map<String,Player> players=new HashMap<String,Player>();
	public Player getPlayer(String ip){
		return players.get(ip);
	}
	
	private final Map<String,Room> rooms=new HashMap<String,Room>();
	public Room RoomGet(String code){
		return rooms.get(code);
	}
	protected void RoomRemove(String code){
		rooms.remove(code);
	}
	public List<Room> RoomGetAll(){
		return new ArrayList<Room>(rooms.values());
	}
	public Room RoomCreate(){
		StringBuilder sb;
		do{
			sb=new StringBuilder();
			for(int i=0;i<CodeLength;i++){
				int x=(int)(Math.random()*charset.length());
				sb.append(charset.charAt(x));
			}
		}while(this.rooms.containsKey(sb.toString()));
		Room res=new Room(sb.toString());
		rooms.put(res.code,res);
		return res;
	}
	
	
	public Player PlayerConnect(MySocket socket){
		Player res=new Player(socket);
		synchronized(this.players){
			this.players.put(res.ip,res);
		}
		return res;
	}
	public boolean PlayerLoginRoom(Player p,Room room){
		if(p.room!=null)
			this.PlayerLogoutRoom(p);
		return room.addPlayer(p);
	}
	public void PlayerLogoutRoom(Player p){
		if(p.room==null) return;
		synchronized(this.players){
			this.players.put(p.ip,p);
		}
		p.room.removePlayer(p);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
