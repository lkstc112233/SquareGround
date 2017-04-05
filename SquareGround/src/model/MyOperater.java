package model;

import java.util.List;

public class MyOperater implements Operation{

	@Override
	public void connectServer(String ip, int port) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room getRoom() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	//==========================================================================================
	static public class MyPlayer implements Operation.Player{

		@Override
		public void setIP(String ip) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getIP() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Player parse(String s) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getScore() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isPlaying() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getID() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String createRoom() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean loginRoom(String code) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getRoomCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void logoutRoom() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void left() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void right() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void up() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void down() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sendMsg() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setMsgCallBack(MessageCallBack callback) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	//==========================================================================================
	static public class MyRoom implements Operation.Room{

		@Override
		public void setCode(String code) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Room parse(String s) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getNumberOfPeopleInRoom() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getMaxmumOfPeopleInRoom() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isGameStart() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<String> getPlayerIPs() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int[][] getMap() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}


}
