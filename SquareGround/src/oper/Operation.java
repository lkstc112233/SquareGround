package oper;

public interface Operation {
	//==========================================================================================
	//���÷�����IP�Ͷ˿�
	public void connectServer(String ip,int port);
	public boolean isConnected();
	public String getIP();
	public int getPort();
	//���ûص�����
	public void setCallBack(CallBack callback);
	//��ȡ��Һͷ���
	public Player getPlayer();
	public Room getRoom();

	//==========================================================================================
	static public interface CallBack{
		public void startGame();
		public void endGame();
		public void receiveBoard(Board board);
		public void receiveMessage(String msg);
	}
	static public interface Board{
		public int get(int i,int j);
		public int rows();
		public int cols();
		public int numberPlayerAdd();
	}
	//==========================================================================================
	static public interface Player {
		//������Ҽ�ֵ����IP����
		//public void setIP(String ip);
		public String getIP();
		//���ַ���������ǰ�����Ϣ
		//public Player parse(String s);
		//public String toString();
		//����
		public int getScore();
		public boolean isPlaying();
		public int getID();
		//����һ�����䣬���ҷ���һ����һ�޶���������
		public String createRoom();
		//ƾ����������뷿�䣬�����Ƿ�ɹ�����
		public boolean loginRoom(String code);
		//��ȡ����������
		public String getRoomCode();
		//����
		public void logoutRoom();
		public void left();
		public void right();
		public void up();
		public void down();
		public void sendMsg(String msg);
	}
	
	//==========================================================================================
	static public interface Room {
		//���÷����ֵ����������code����
		//public void setCode(String code);
		public String getCode();
		//���ַ���������ǰ������Ϣ
		//public Room parse(String s);
		//public String toString();
		//����
		public int getNumberOfPeopleInRoom();
		public int getMaxmumOfPeopleInRoom();
		public boolean isGameStart();
		//public java.util.List<String> getPlayerIPs();
		//public Board getMap();
	}
	
	
	
}
