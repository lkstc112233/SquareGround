package web;

import java.io.*;
import java.net.*;


public final class ClientAutoScanner extends Client implements Runnable{
	
	static public interface CallBack{
		public void solve(Client client,String[] msg) throws Throwable;
	}
	
	private CallBack func=null; 
		public void setFunc(CallBack func){
			synchronized(this.func){
				this.func=func;
			}
		}
	private Thread thread;
	private String[] lastMsg=null;
		
	public ClientAutoScanner(String ip,int port,CallBack func) throws UnknownHostException, IOException{
		super(ip,port);
		this.func=func;
		this.thread=new Thread(this);
	}
	public ClientAutoScanner(String ip,int port) throws UnknownHostException, IOException{
		this(ip,port,null);
	}
	
	@Override
	public String[] getLine() throws IOException{
		return lastMsg;
	}
	
	public void start(){
		this.thread.start();
	}
	public void interrupt(){
		this.thread.interrupt();
	}
	public void join() throws InterruptedException{
		this.thread.join();
	}
	
	@Override
	public void run(){
		while(!this.isClosed() && this.in!=null){
			try {
				String msg=in.readLine();
				lastMsg=WebCommunityInterface.split(msg);
				synchronized(this.func){
					if(func!=null)
						this.func.solve(this,lastMsg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
		
}
