package web;

import java.io.*;
import java.net.*;
import java.util.HashMap;


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
	
	public long timeout=10*1000;
	private HashMap<Long,String[]> querySet=new HashMap<Long,String[]>();
	static public class ClientAutoScannerTimeoutException extends Throwable{
		private static final long serialVersionUID = -7290327826552275056L;
		public ClientAutoScannerTimeoutException(){super();}
		public ClientAutoScannerTimeoutException(String msg){super(msg);}
	}
	public String[] query(String... args) throws IOException, ClientAutoScannerTimeoutException{
		long time=System.currentTimeMillis();
		synchronized(this.querySet){
			this.querySet.put(time,null);
		}
		this.write(time,args);
		String[] res=null;
		while(res==null){
			long now=System.currentTimeMillis();
			if(now-time>timeout){
				throw new ClientAutoScannerTimeoutException(String.format(
						"quert timeout(%d).",WebCommunityInterface.toString(time,args)));
			}
		}
		return res;
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
