package web;

import java.io.*;
import java.net.*;
import java.util.HashMap;


public class ClientAutoScanner extends Thread{
	
	static public interface CallBack{
		public void solve(ClientAutoScanner client,String[] msg) throws Throwable;
	}
	
	private CallBack func=null; 
		public void setFunc(CallBack func){
			synchronized(this.func){
				this.func=func;
			}
		}

	
	static public final String charset="UNICODE";
	protected Socket socket;
		public Socket getSocket(){return socket;}
	protected BufferedWriter out=null;
	protected BufferedReader in=null;

	public ClientAutoScanner(Socket socket,CallBack func) throws UnknownHostException, IOException{
		this.socket=socket;
		out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),charset));
		in=new BufferedReader(new InputStreamReader(socket.getInputStream(),charset));
		this.func=func;
	}
	public ClientAutoScanner(String ip,int port,CallBack func) throws UnknownHostException, IOException{
		this(new Socket(ip,port),func);
	}
	public ClientAutoScanner(String ip,int port) throws UnknownHostException, IOException{
		this(ip,port,null);
	}
	public ClientAutoScanner(Socket socket) throws UnknownHostException, IOException{
		this(socket,null);
	}
	

	public void write(Object... args) throws IOException{
		if(out==null) return;
		out.write(WebCommunityInterface.toString(args)+WebCommunityInterface.endl);
		out.flush();
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
		while(!this.socket.isClosed() && this.in!=null){
			try {
				String msg=in.readLine();
				String[] tmp=WebCommunityInterface.split(msg);
				synchronized(this.func){
					if(func!=null)
						this.func.solve(this,tmp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
		
}
