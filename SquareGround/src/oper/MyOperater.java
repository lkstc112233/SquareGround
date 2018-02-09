package oper;

import java.io.IOException;
import web.ClientAutoScanner;
import web.ClientAutoScanner.ClientAutoScannerTimeoutException;

public final class MyOperater implements Operation{

	private web.ClientAutoScanner client=null;
	
	public MyOperater(){
	}
	
	private final String[] query(String... args){
		try {
			String[] msg=this.client.query(args);
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClientAutoScannerTimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	private web.ClientAutoScanner.CallBack solver=new web.ClientAutoScanner.CallBack(){
		public void solve(web.ClientAutoScanner client, String[] msg) throws Throwable {
			if(msg!=null && msg.length>1){
				if(msg[1].equals("startGame")){
					synchronized(MyOperater.this.func){
						MyOperater.this.func.startGame();
					}
				}else if(msg[1].equals("endGame")){
					synchronized(MyOperater.this.func){
						MyOperater.this.func.endGame();
					}
				}else if(msg[1].equals("receiveBoard")){
					MyBoard board=null;
					try{
						int n=Integer.parseInt(msg[2]);
						int m=Integer.parseInt(msg[3]);
						int numberPlayerAdd=Integer.parseInt(msg[4]);
						int k=5;
						board=new MyBoard(n,m,numberPlayerAdd);
						for(int i=0;i<n;i++) for(int j=0;j<m;j++)
							board.data[i][j]=Integer.parseInt(msg[k++]);
					}catch(NumberFormatException e){
						e.printStackTrace();
						board=null;
					}
					if(board!=null){
						synchronized(MyOperater.this.func){
							MyOperater.this.func.receiveBoard(board);
						}
					}
				}else if(msg[1].equals("receiveMessage")){
					synchronized(MyOperater.this.func){
						MyOperater.this.func.receiveMessage(msg[1]);
					}
				}
			}
		}
	};
	@Override
	public void connectServer(String ip, int port) {
		try {
			this.client=new ClientAutoScanner(ip,port,solver);
		} catch (IOException e) {
			e.printStackTrace();
			client=null;
			return;
		}
		this.client.start();
		//player
		this.player=null;
		this.getPlayer();
	}
	protected CallBack func=null;
	@Override
	public void setCallBack(CallBack callback) {
		synchronized(this.func){
			this.func=callback;
		}
	}

	@Override
	public boolean isConnected() {
		return client!=null && !client.getSocket().isClosed() && !client.getSocket().isInputShutdown() && !client.getSocket().isOutputShutdown(); 
	}

	@Override
	public String getIP() {
		return client.getSocket().getInetAddress().getHostAddress();
	}

	@Override
	public int getPort() {
		return client.getSocket().getLocalPort();
	}

	
	protected Player player;
	@Override
	public Player getPlayer() {
		if(this.player!=null)
			return this.player;
		String[] res=this.query("connect");/*ע�������������connect������player��IP*/
		if(res==null || res.length<=1)
			return null;
		return this.player=new MyPlayer(res[1]);
	}

	protected Room room;
	@Override
	public Room getRoom() {
		return room;
	}
	
	

	//==========================================================================================
	public class MyPlayer implements Operation.Player{
		private String ip;
		public MyPlayer(String ip){
			this.ip=ip;
		}
		
		//@Override
		//public void setIP(String ip) {
		//}

		@Override
		public String getIP() {
			if(this.ip!=null) return ip;
			String[] res=MyOperater.this.query("getIP");
			if(res==null || res.length<=1)
				return null;
			return this.ip=res[1];
		}

		@Override
		public int getScore() {
			String[] res=MyOperater.this.query("getScore");
			if(res==null || res.length<=1)
				return -1;
			try{
				return Integer.parseInt(res[1]);
			}catch(NumberFormatException e){
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		public boolean isPlaying() {
			String[] res=MyOperater.this.query("isPlaying");
			if(res==null || res.length<=1)
				return false;
			return Boolean.parseBoolean(res[1]);
		}

		@Override
		public int getID() {
			String[] res=MyOperater.this.query("getID");
			if(res==null || res.length<=1)
				return -1;
			try{
				return Integer.parseInt(res[1]);
			}catch(NumberFormatException e){
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		public String createRoom() {
			String[] res=MyOperater.this.query("createRoom");
			if(res==null || res.length<=1){
				MyOperater.this.room=null;
				return null;
			}
			MyOperater.this.room=new MyRoom(res[1]);
			return res[1];
		}

		@Override
		public boolean loginRoom(String code) {
			String[] res=MyOperater.this.query("loginRoom",code);
			if(res==null || res.length<=1){
				MyOperater.this.room=null;
				return false;
			}
			MyOperater.this.room=new MyRoom(code);
			return true;
		}
		
		@Override
		public String getRoomCode() {
			if(MyOperater.this.room==null) return null;
			return MyOperater.this.room.getCode();
		}

		@Override
		public void logoutRoom() {
			try {
				MyOperater.this.client.write(System.currentTimeMillis(),"logoutRoom");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void left() {
			try {
				MyOperater.this.client.write(System.currentTimeMillis(),"left");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void right() {
			try {
				MyOperater.this.client.write(System.currentTimeMillis(),"right");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void up() {
			try {
				MyOperater.this.client.write(System.currentTimeMillis(),"up");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void down() {
			try {
				MyOperater.this.client.write(System.currentTimeMillis(),"down");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendMsg(String msg) {
			try {
				MyOperater.this.client.write(System.currentTimeMillis(),"sendMsg",msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	//==========================================================================================
	public class MyRoom implements Operation.Room{
		private String code;
		public MyRoom(String code){
			this.code=code;
		}

		@Override
		public String getCode() {
			if(this.code!=null) return code;
			String[] res=MyOperater.this.query("getCode");
			if(res==null || res.length<=1)
				return null;
			return this.code=res[1];
		}
		
		@Override
		public int getNumberOfPeopleInRoom() {
			String[] res=MyOperater.this.query("getNumberOfPeopleInRoom");
			if(res==null || res.length<=1)
				return -1;
			try{
				return Integer.parseInt(res[1]);
			}catch(NumberFormatException e){
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		public int getMaxmumOfPeopleInRoom() {
			String[] res=MyOperater.this.query("getMaxmumOfPeopleInRoom");
			if(res==null || res.length<=1)
				return -1;
			try{
				return Integer.parseInt(res[1]);
			}catch(NumberFormatException e){
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		public boolean isGameStart() {
			String[] res=MyOperater.this.query("isGameStart");
			if(res==null || res.length<=1)
				return false;
			return Boolean.parseBoolean(res[1]);
		}

		
	}


}
