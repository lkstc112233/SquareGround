package web;

import java.io.*;
import java.net.*;
import java.util.*;

import model.Hall;
import model.Player;

public class MyServer extends ServerSocket implements Runnable{
	
	static public final String charset="UNICODE"; 
	
	private Map<String, MySocket> sockets=new HashMap<String,MySocket>();
	protected MySocket getSocket(String ip){
		return this.sockets.get(ip);
	}
	
	
	public MyServer(int port) throws IOException{
		super(port);
	}
	
	@Override
	public void run(){
		while(!this.isClosed()){
			Socket socket=null;
			System.out.printf("++++(%d)waiting\n",sockets.size());
			try {
				socket = this.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.printf("++++get one\n");
			if(socket!=null){
				try {
					MySocket ms=new MySocket(socket);
					sockets.put(ms.ip,ms);
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	@Override
	public void close() throws IOException{
		if(sockets!=null)
			for(MySocket ms:this.sockets.values())
				ms.interrupt();
		super.close();
	}
	
	//TODO MyScoket
	public final class MySocket extends ClientAutoScanner implements ClientAutoScanner.CallBack{
		
		private String ip;
		
		public MySocket(Socket _socket) throws UnsupportedEncodingException, IOException{
			super(_socket,null);
			super.setFunc(this);
			ip=super.socket.getInetAddress().getHostAddress();
			this.start();
		}
		
		public void solve(ClientAutoScanner client, String[] msg) throws Throwable {
			if(msg!=null && msg.length>0){
				if(msg[1].equals("connect")){
					Hall.instance.PlayerConnect((MySocket)client);
					client.write(msg[0],MySocket.this.ip);
				}else if(msg[1].equals("getIP")){
					client.write(msg[0],MySocket.this.ip);
				}else if(msg[1].equals("getScore")){
					client.write(msg[0],0);
				}else if(msg[1].equals("isPlaying")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					client.write(msg[0],p.getRoom().game!=null);
				}else if(msg[1].equals("getID")){
					client.write(msg[0],0);
				}else if(msg[1].equals("createRoom")){
					model.Room r=Hall.instance.RoomCreate();
					client.write(msg[0],r.code);
				}else if(msg[1].equals("loginRoom") && msg.length>2){
					model.Room r=Hall.instance.RoomGet(msg[2]);
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					boolean res=Hall.instance.PlayerLoginRoom(p,r);
					client.write(msg[0],res?"true":"false");
				}else if(msg[1].equals("logoutRoom")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					Hall.instance.PlayerLogoutRoom(p);
					client.write(msg[0],"true");
				}else if(msg[1].equals("left")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null || r.game==null)
						client.write(msg[0],"false");
					else{
						r.game.operateLeft(p);
						client.write(msg[0],"true");
					}
				}else if(msg[1].equals("right")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null || r.game==null)
						client.write(msg[0],"false");
					else{
						r.game.operateRight(p);
						client.write(msg[0],"true");
					}
				}else if(msg[1].equals("up")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null || r.game==null)
						client.write(msg[0],"false");
					else{
						r.game.operateUp(p);
						client.write(msg[0],"true");
					}
				}else if(msg[1].equals("down")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null || r.game==null)
						client.write(msg[0],"false");
					else{
						r.game.operateDown(p);
						client.write(msg[0],"true");
					}
				}else if(msg[1].equals("sendMsg") && msg.length>2){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null)
						client.write(msg[0],"false");
					else{
						for(Player tmp:r.getAllPlayers()){
							MyServer.this.getSocket(tmp.ip).write(System.currentTimeMillis(),"receiveMessage",msg[2]);
						}
					}
				}else if(msg[1].equals("getCode")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null)
						client.write(msg[0],"false");
					else
						client.write(msg[0],r.code);
				}else if(msg[1].equals("getNumberOfPeopleInRoom")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null)
						client.write(msg[0],"false");
					else
						client.write(msg[0],r.getAllPlayers().size());
				}else if(msg[1].equals("getMaxmumOfPeopleInRoom")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null)
						client.write(msg[0],"false");
					else
						client.write(msg[0],r.MaxmumOfPeopleInRoom);
				}else if(msg[1].equals("isGameStart")){
					model.Player p=Hall.instance.getPlayer(MySocket.this.ip);
					model.Room r=p.getRoom();
					if(r==null)
						client.write(msg[0],"false");
					else
						client.write(msg[0],r.game==null?"false":"true");
				}
			}
		}
		
		@Override
		public void run() {
			System.err.println("ServerSocket("+ip+") is running!");
			super.run();
			System.out.printf("[%d]>><<END",ip);
		}
		
		
		
	}

	
}
