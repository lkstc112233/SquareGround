package frame;

import javax.swing.JPanel;

public abstract class Panel extends JPanel implements Refreshable{
	private static final long serialVersionUID = 8478812272127889524L;

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
