package view;

import javax.swing.JPanel;

import main.Helper;

public abstract class Content extends JPanel implements Refreshable,FrameSon{
	private static final long serialVersionUID = -6803857272974058126L;
	

	protected Frame frame=null;
	
	public Content(){
		this.setBackground(Helper.ColorBackground);
	}
	
	@Override
	public final Content setFrame(Frame frame){
		this.frame=frame;
		return this;
	}
	
	@Override
	public final void refresh(){
		this.updateUI();
	}

	@Override
	public void refresh(double dt){
		
	}
	
	@Override
	public void refreshDeep(){
		this.refresh();
	}

}
