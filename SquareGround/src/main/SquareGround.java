package main;

public class SquareGround {
	
	static public void main(String... args){
		System.out.println("SquareGround start!");
		
		
		
		frame.Panel panel=new frame.PanelWaitStart();
		frame.Listener listener=new frame.MyListener();
		Debugger debugger=new Debugger();
		model.Operation oper=new model.MyOperater();
		frame.Frame frame=new frame.Frame(panel,listener,debugger,oper);
		
		frame.setVisible(true);
		
		
		System.out.println("SquareGround end!");
	}
	
	
}
