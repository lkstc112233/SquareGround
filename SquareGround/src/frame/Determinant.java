package frame;

import java.awt.*;

import javax.swing.JPanel;


import simulation.road.DiscreteThread;

public abstract class Determinant extends DiscreteThread{
	
	private String testname;
		public String getTestName(){return testname;}
		public Determinant setTestName(String testname){
			if(testname==null) testname="Determinand";
			this.testname="<WL> "+testname;return this;
		}
	
	private Refreshable refreshable;
		private Refreshable getRefreshable(){return refreshable;}
		public void setRefreshable(Refreshable refreshable){this.refreshable=refreshable;}
	
	public abstract void draw(Graphics g,JPanel panel,double dt);

	protected void goby(double dt,int times){
		if(this.getRefreshable()!=null){
			this.getRefreshable().refresh(dt);
		}
	}
	
	public void Start(){
		super.Start();
	}
	
}
