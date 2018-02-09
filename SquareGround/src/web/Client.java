package web;

import java.io.*;
import java.net.*;


public class Client {
	
	protected Socket socket;
	
	static public final String charset="UNICODE"; 
	
	public Client(String ip,int port) throws UnknownHostException, IOException{
		socket=new Socket(ip,port);
		out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),charset));
		in=new BufferedReader(new InputStreamReader(socket.getInputStream(),charset));
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
