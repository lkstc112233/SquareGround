package model;

public interface Operation {
	//==========================================================================================
	//设置服务器IP和端口
	public void connectServer(String ip,int port);
	public boolean isConnected();
	public String getIP();
	public int getPort();
	//获取玩家和房间
	public Player getPlayer();
	public Room getRoom();

	//==========================================================================================
	static public interface MessageCallBack{
		public void getMsg(String o);
	}
	//==========================================================================================
	static public interface Player {
		//设置玩家键值，以IP划分
		public void setIP(String ip);
		public String getIP();
		//从字符串解析当前玩家信息
		public Player parse(String s);
		public String toString();
		//属性
		public int getScore();
		public boolean isPlaying();
		public int getID();
		//创建一个房间，并且返回一个独一无二的邀请码
		public String createRoom();
		//凭借邀请码进入房间，返回是否成功进入
		public boolean loginRoom(String code);
		//获取房间邀请码
		public String getRoomCode();
		//操作
		public void logoutRoom();
		public void left();
		public void right();
		public void up();
		public void down();
		public void sendMsg();
		public void setMsgCallBack(MessageCallBack callback);
	}
	
	//==========================================================================================
	static public interface Room {
		//设置房间键值，以邀请码code划分
		public void setCode(String code);
		public String getCode();
		//从字符串解析当前房间信息
		public Room parse(String s);
		public String toString();
		//属性
		public int getNumberOfPeopleInRoom();
		public int getMaxmumOfPeopleInRoom();
		public boolean isGameStart();
		public java.util.List<String> getPlayerIPs();
		public int[][] getMap();
	}
	
	
	
}
