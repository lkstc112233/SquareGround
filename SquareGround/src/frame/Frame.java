package frame;

import java.awt.*;
import javax.swing.*;

public final class Frame extends JFrame implements Refreshable{
	private static final long serialVersionUID = 2703663016877310106L;
	
	
	private	Panel panel;
	private	ToolBar toolbar;
	private	Listener listener;
	protected Debugger debugger;
	protected model.Operater oper;
	
	
	public Frame(Panel panel,Listener listener,Debugger debugger,model.Operater oper){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
		Insets scrInsets=Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
		this.setBounds(scrInsets.left,scrInsets.top,scrSize.width*17/20,scrSize.height*9/10);
		//Panel&&ToolBar
		this.getContentPane().add(BorderLayout.CENTER,
				this.panel=panel);
		this.getContentPane().add(BorderLayout.WEST,
				this.toolbar=new ToolBar(this));
		//listener
		this.listener=listener.addTo(this).addTo(this.panel).addTo(toolbar);
		//debugger
		this.debugger=debugger;
		//oper
		this.oper=oper;
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
	
	
	
}
