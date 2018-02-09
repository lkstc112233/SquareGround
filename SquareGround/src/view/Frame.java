package view;

import java.awt.*;
import javax.swing.*;

import main.Debugger;
import oper.Operation.Board;

public final class Frame extends JFrame implements Refreshable,oper.Operation.CallBack{
	private static final long serialVersionUID = 2703663016877310106L;
	
	
	private	Panel panel;
		public void setPanelWithOutRefresh(Panel panel){
			this.getContentPane().add(BorderLayout.CENTER,
					(this.panel=panel).setFrame(this));
		}
	private	ToolBar toolbar;
	@SuppressWarnings("unused")
	private	Listener listener;
	private final Debugger debugger;
		public void debug(String msg){debugger.debug(msg);}
		public void debug(String format,Object... args){debugger.debug(format,args);}
		protected void drawDebug(Graphics g){if(this.panel!=null)this.debugger.draw(this.panel,g);}
	protected oper.Operation oper;
	
	
	public Frame(Debugger debugger,oper.Operation oper){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
		Insets scrInsets=Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
		this.setBounds(scrInsets.left,scrInsets.top,scrSize.width*17/20,scrSize.height*9/10);
		//Panel&&ToolBar
		this.getContentPane().add(BorderLayout.CENTER,
				(this.panel=new PanelWaitStart()).setFrame(this));
		this.getContentPane().add(BorderLayout.WEST,
				(this.toolbar=new ToolBar()).setFrame(this));
		//listener
		(this.listener=new MyListener().addTo(this).addTo(this.panel).addTo(toolbar)).setFrame(this);
		//debugger
		this.debugger=debugger;
		//operation
		this.oper=oper;
		oper.setCallBack(this);
	}

	@Override
	public void refresh(){
		if(this.panel!=null)
			this.panel.refresh();
		if(this.toolbar!=null)
			this.toolbar.refresh();
	}
	@Override
	public void refresh(double dt){
		if(this.panel!=null)
			this.panel.refresh(dt);
		if(this.toolbar!=null)
			this.toolbar.refresh(dt);
	}
	@Override
	public void refreshDeep() {
		if(this.panel!=null)
			this.panel.refreshDeep();
		if(this.toolbar!=null)
			this.toolbar.refreshDeep();
	}


	private long timeStart,timeLast,timeEnd;
	@Override
	public void startGame() {
		String debugmsg="Start the Game ... ";
		if(oper.isConnected()==false){
			this.debug(debugmsg+"fail! (lose connection)");
			return;
		}
		if(oper.getRoom()==null){
			this.debug(debugmsg+"fail! (lose room information)");
			return;
		}
		if(oper.getPlayer()==null){
			this.debug(debugmsg+"fail! (lose local player information)");
			return;
		}
		this.debug(debugmsg+"successful!");
		this.timeStart=this.timeEnd=this.timeLast
				=-1;
		//start the game
		this.setPanelWithOutRefresh(new PanelGame());
		//refresh
		this.toolbar.setShowWithoutRefresh(false);
		this.refreshDeep();
	}
	
	public void endGame(){
		this.timeEnd=System.currentTimeMillis();
		this.debug("Game End!(%ds)",(this.timeEnd-this.timeStart+499)/1000);
		this.setPanelWithOutRefresh(new PanelWaitStart());
		//refresh
		this.toolbar.setShowWithoutRefresh(true);
		this.refreshDeep();
	}
	
	@Override
	public void receiveBoard(Board board) {
		@SuppressWarnings("unused")
		long DT=0;
		if(this.timeStart<0)
			this.timeStart=this.timeLast
			=System.currentTimeMillis();
		else{
			long now=System.currentTimeMillis();
			DT=now-this.timeLast;
			this.timeLast=now;
		}
		//paint the board
		//this.refresh(DT/1000.0);//Ö¡Í¬²½...
		//just set, and wait the thread(PanelGame) for painting
		((PanelGame) this.panel).recieveBoard(board);
		//TODO DT is used to calculate the ping
		
	}

	@Override
	public void receiveMessage(String msg) {
		this.debug("receive msg: "+msg);
	}
	
	
	
}
