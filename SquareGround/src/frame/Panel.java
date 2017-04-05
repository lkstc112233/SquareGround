package frame;

import javax.swing.JPanel;

import main.Helper;

public abstract class Panel extends JPanel implements Refreshable,FrameSon{
	private static final long serialVersionUID = 8478812272127889524L;
	
	protected Frame frame=null;
	
	public Panel(){
		this.setBackground(Helper.ColorBackground);
	}
	
	@Override
	public Panel setFrame(Frame frame){
		this.frame=frame;
		return this;
	}
	
	@Override
	public void refresh(){
		this.updateUI();
	}

	@Override
	public void refresh(double dt){
		this.refresh();
	}
	
	@Override
	public void refreshDeep(){
		this.refresh();
	}
	
	
	
	
	

}
