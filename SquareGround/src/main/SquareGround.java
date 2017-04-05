package main;

public class SquareGround {
	
	static public void main(String... args){
		System.out.println("SquareGround start!");
		
		
		
		frame.Panel panel=new frame.PanelWaitStart();
		frame.Listener listener=new frame.MyListener();
		Debugger debugger=new Debugger();
		model.Operater oper;
		frame.Frame frame=new frame.Frame(panel,listener,debugger,oper);
		
		
		
		System.out.println("SquareGround end!");
	}
	
	
}
