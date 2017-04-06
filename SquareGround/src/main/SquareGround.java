package main;

public class SquareGround {
	
	static public void main(String... args){
		System.out.println("SquareGround start!");
		
		
		
		Debugger debugger=new Debugger();
		oper.Operation oper=new oper.MyOperater();
		view.Frame frame=new view.Frame(debugger,oper);
		
		frame.setVisible(true);
		
		
		System.out.println("SquareGround end!");
	}
	
	
}
