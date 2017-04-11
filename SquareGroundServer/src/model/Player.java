package model;

import web.MyServer;

public class Player implements Comparable<Player>{
	public final String ip;
	public final MyServer.MySocket socket;
	
	public Player(MyServer.MySocket socket){
		this.ip=socket.getSocket().getInetAddress().getHostAddress();
		this.socket=socket;
	}
	
	public boolean equals(Object o){
		if(o==null) return false;
		if(!(o instanceof Player))
			return false;
		return ((Player) o).ip.equals(this.ip);
	}

	@Override
	public int compareTo(Player o) {
		return ip.compareTo(o.ip);
	}
	
	
	
	
	
}
