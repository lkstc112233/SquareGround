package web;

import java.io.*;
import java.net.*;


public class Client extends Socket{
	
	static public final String charset="UNICODE"; 
	
	public Client(String ip,int port) throws UnknownHostException, IOException{
		super(ip,port);
		out=new BufferedWriter(new OutputStreamWriter(this.getOutputStream(),charset));
		in=new BufferedReader(new InputStreamReader(this.getInputStream(),charset));
	}
	
	protected BufferedWriter out=null;
	protected BufferedReader in=null;
	
	public void write(Object... args) throws IOException{
		if(out==null) return;
		out.write(WebCommunityInterface.toString(args)+WebCommunityInterface.endl);
		out.flush();
	}
	
	public String[] getLine() throws IOException{
		if(in==null) return null;
		return WebCommunityInterface.split(in.readLine());
	}
	 
	
	
	
}
