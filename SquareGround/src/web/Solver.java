package web;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public class Solver implements SolverInterface{
	private Scanner cin=new Scanner(System.in);
	private PrintStream cout=System.out;

	@Override
	public void solve(BufferedReader in, BufferedWriter out) throws IOException {
		String res=ce;
		String msg=cin.nextLine();
		if(msg==null) throw new NullMessageException();
		if(msg.length()<=0) throw new EmptyMessageException();
		try{
			String[] list=SolverInterface.split(msg);
			for(int i=0;i<list.length;i++)
				System.out.printf("%s%s",list[i],i<list.length-1?" | ":"\r\n");
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
				Class<?> clazz=Class.forName("web.operation."+list[0]);
				Method meth=clazz.getMethod(list[1],String[].class);
				String[] args=new String[list.length-2];
				for(int i=0;i<args.length;i++)
					args[i]=list[i+2];
				meth.invoke(clazz.newInstance(),(Object)args);
			}catch(ClassNotFoundException | NoSuchMethodException | SecurityException e){
				throw new NoSuchMethodException("²ÎÊý´íÎó");
			}
		}catch(Exception e){
			e.printStackTrace();
			res=re+spacer+e.getMessage();
		}
		cout.print(res+endl);
		cout.flush();

	}

}
