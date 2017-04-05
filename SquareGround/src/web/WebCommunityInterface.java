package web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public interface WebCommunityInterface {

	static public final String endl="\n";
	static public final String spacer=" ";
	static public final String yes="YES";
	static public final String no="NO";
	static public final String re="RE";
	static public final String ce="CE";

	public abstract void solve(BufferedReader in,BufferedWriter out)throws IOException;

	static public String[] split(String s){
		StringBuilder sb=new StringBuilder();
		ArrayList<String> res=new ArrayList<String>();
		boolean flag=false;
		for(int i=0;i<s.length();i++){
			if(flag) flag=false;
			else{
				if(s.charAt(i)=='\\') flag=true;
				else if(s.charAt(i)==spacer.charAt(0)){
					res.add(sb.toString());
					sb=new StringBuilder();
					continue;
				}
			}
			sb.append(s.charAt(i));
		}
		if(sb.length()>0)
			res.add(sb.toString());
		String[] ans=new String[res.size()];
		res.toArray(ans);
		return ans;
	}
	
	static public String toString(Object... args){
		StringBuilder sb=new StringBuilder();
		boolean flag=true;
		for(Object o:args){
			sb.append(o.toString());
			if(!flag){
				flag=false;
				sb.append(spacer);
			}
		}return sb.toString();
	}

	
	public static class NullMessageException extends IOException{
		private static final long serialVersionUID = -8141670965140040008L;
		public NullMessageException(){
			super("read an null string!");
		}
	}
	public static class EmptyMessageException extends IOException{
		private static final long serialVersionUID = 4392825136784750801L;
		public EmptyMessageException(){
			super("read an empty string!");
		}
	}
	
}
