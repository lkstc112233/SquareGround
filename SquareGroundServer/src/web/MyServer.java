package web;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

public class MyServer extends ServerSocket implements Runnable{
	
	static public final String charset="UNICODE"; 
	
	private HashMap<Integer,MySocket> sockets;
	private int cnt;
	
	
	public MyServer(int port) throws IOException{
		super(port);
		sockets=new HashMap<Integer,MySocket>();
		cnt=0;
	}
	@Override

	public void run(){
		while(!this.isClosed()){
			Socket socket=null;
			System.out.printf("++++(%d/%d)waiting\n",sockets.size(),cnt);
			try {
				socket = this.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.printf("++++get one\n");
			if(socket!=null){
				try {
					sockets.put(cnt,new MySocket(cnt,socket));
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				cnt++;
				if(cnt==1000000007)
					cnt=0;
			}
		}
	}
	
	@Override
	public void close() throws IOException{
		if(sockets!=null)
			for(Entry<Integer, MySocket> it:this.sockets.entrySet())
				it.getValue().interrupt();
		super.close();
	}
	
	//TODO MyScoket
	public final class MySocket extends ClientAutoScanner{
		
		private int id;
		
		public MySocket(int _id,Socket _socket) throws UnsupportedEncodingException, IOException{
			super(_socket,new CallBack(){
				public void solve(ClientAutoScanner client, String[] msg) throws Throwable {
					
				}
			});
			id=_id;
			this.start();
		}
		
		@Override
		public void run() {
			System.err.println("ServerSocket("+id+") is running!");
			super.run();
			System.out.printf("[%d]>><<END",id);
		}
		
		
		
	}

	
}
