package model;

import web.MyServer;

public final class Player implements Comparable<Player>{
	public final String ip;
	public final MyServer.MySocket socket;
	protected Room room=null;
	public Room getRoom(){return room;}
	
	protected Player(MyServer.MySocket socket){
		this.socket=socket;
		this.ip=socket.getSocket().getInetAddress().getHostAddress();
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
