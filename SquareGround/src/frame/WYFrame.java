package frame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.*;

import sim.Helper;
import sim.Simulation;
import simulation.driver.CAR;
import simulation.element.Item;
import simulation.element.WL;
import simulation.road.Road;


public final class WYFrame extends JFrame implements Refreshable{
	private static final long serialVersionUID = -132996451669873531L;

	private WYPanelListener listener;
	
	public final WYPanel panel;
	public final WYToolBar toolbar;
	public final WYCarPool carpool;
	public final WYCarPanel carpanel;

	private CAR choosedCar=null;
		public CAR getChoosedCar(){return choosedCar;}

	public WYFrame(Simulation simulation){
		this.simulation=simulation;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
		Insets scrInsets=Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
		this.setBounds(scrInsets.left,scrInsets.top,scrSize.width*17/20,scrSize.height*9/10);
		//Panel&&ToolBar
		JPanel westP;
		this.getContentPane().add(BorderLayout.WEST,
				westP=new JPanel());
		{
			westP.setLayout(new BorderLayout());
			westP.add(BorderLayout.NORTH,
					toolbar=new WYToolBar(simulation));
			westP.add(BorderLayout.CENTER,
					carpool=new WYCarPool(simulation));
		}
		this.getContentPane().add(BorderLayout.CENTER,
				panel=new WYPanel());
		this.getContentPane().add(BorderLayout.EAST,
				carpanel=new WYCarPanel(simulation)
				)
				;
		//listener
		listener=new WYPanelListener();
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);
		panel.addMouseWheelListener(listener);
		this.addKeyListener(listener);
		panel.addKeyListener(listener);
		toolbar.addKeyListener(listener);
		carpool.addKeyListener(listener);
		carpanel.addKeyListener(listener);
	}
	
	public void refresh(){
		if(this.panel!=null)
			this.panel.updateUI();
		if(this.toolbar!=null)
			this.toolbar.updateUI();
		if(this.carpool!=null)
			this.carpool.refresh();
	}public void refresh(double dt){
		this.refresh();
		if(this.panel!=null)
			this.panel.setDt(dt);
	}
	
	
	

	class WYPanel extends JPanel {
		private static final long serialVersionUID = 1262216091746202566L;
		
		private double dt=1e-2;
			public WYPanel setDt(double dt){this.dt=dt;return this;}
			public double getDt(){return dt;}

		private WL o=new WL();
			public final WL getO(){return o;}
			public final WYPanel setO(WL newo){o=newo;return this;}
		
		private double oq=30,oqmin=0.05,oqmax=240;
			public final double getOQ(){return oq;}
			public final double getOQmin(){return oqmin;}
			public final double getOQmax(){return oqmax;}
			public final WYPanel setOQ(double oq){
				this.oq=Math.max(oqmin,Math.min(oq,oqmax));
				return this;}
			public final WYPanel setOQmin(int oqmin){this.oqmin=oqmin;return this;}
			public final WYPanel setOQmax(int oqmax){this.oqmax=oqmin;return this;}

		public void resetO(){
			o=new WL();oq=30;
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(simulation!=null&&simulation.getTask()!=null)
				simulation.getTask().draw(g,this,dt);
			if(simulation!=null&&simulation.debugger!=null)
				simulation.debugger.draw(panel,g);
			if(carpanel!=null)
				carpanel.refresh(this.dt);
		}

		public final void focus(CAR focusCar) {
			this.o=new WL(this.getWidth()/5-focusCar.getCenter().x*oq,
					this.getHeight()/2-focusCar.getCenter().y*oq);
		}

		public final void drawRoad(Graphics g, JPanel panel, double dt){
			if(simulation!=null&&simulation.getTask()!=null&&simulation.getTask().getRoad()!=null){
				Road road=simulation.getTask().getRoad();
				WL q=new WL(this.getOQ());
				if(!road.drawStreet(o, q, g, panel, dt))return;
				CAR focusCar=simulation.getTask().getFocusCar();
				if(focusCar!=null&&choosedCar!=null&&focusCar==choosedCar){
					Helper.circleCar(focusCar, o, q, g, panel,
							Color.YELLOW,Color.DARK_GRAY);
				}else{
					if(focusCar!=null)
						Helper.circleCar(focusCar, o, q, g, panel,
								Color.LIGHT_GRAY,Color.DARK_GRAY);
					if(choosedCar!=null)
						Helper.circleCar(choosedCar, o, q, g, panel,
								Color.YELLOW,Color.RED);
				}
				if(!road.drawItem(o, q, g, panel, dt))return;
				if(!road.drawEffect(o, q, g, panel, dt))return;
//				if(!road.draw(o,q,g,panel,dt))return;
			//	System.out.println("draw successfully");
			}
		}
	}

	class WYPanelListener extends WYL{
		private WL click;
		private WL dclick;

		private void setClick(MouseEvent e){
			click=e==null?new WL():
				new WL(e.getX(),e.getY()).jian(panel.getO())
				.chu(panel.getOQ());
		}
		
		public void mousePressed(MouseEvent e){
			toolbar.refreshAll();
			if(e.getButton()==MouseEvent.BUTTON3){
				choosedCar=null;
				return;
			}
			this.setClick(e);
			if(simulation!=null&&simulation.getTask()!=null){
				if(simulation.getTask().getFrame().carpool.getChoosedCar()!=null
						&&simulation.getTask().getFrame().carpool.isAdding()
						&&simulation.getTask().getRoad()!=null){
					CAR car=simulation.getTask().getFrame().carpool.getChoosedCar().copy();
					car.setCenter(click);
					simulation.getTask().getRoad().getBagOfItem().add(car);
				}else{
					for(Item it:simulation.getTask().getRoad().getBagOfItem()){
						if(it instanceof CAR && it.checkIn(click)){
							choosedCar=(CAR)it;
							break;
						}
					}
				}
				dclick=click;
			}
		}
		
		public void mouseReleased(MouseEvent e){
			toolbar.refreshAll();
			carpanel.refreshAll();
		}

		public void mouseDragged(MouseEvent e){
			if(e.getButton()==MouseEvent.BUTTON3){
				choosedCar=null;
				return;
			}
			this.setClick(e);
			if(simulation!=null&&simulation.getTask()!=null&&simulation.getTask().getRoad()!=null){
				WL d=click.jian(dclick);
				panel.setO(panel.getO().jia(d.cheng(panel.getOQ())));
			}
		}
		public void mouseMoved(MouseEvent e){
			this.setClick(e);
		}

		public void mouseWheelMoved(MouseWheelEvent e) {
			double up=e.getWheelRotation()>0?-1:1;
			double newoq=panel.getOQ()+up;
			if(newoq<panel.getOQmin()
					||newoq>panel.getOQmax())
				return;
			double r=1.0+up/(double)panel.getOQ();
			WL realclick=click.cheng(panel.getOQ()).jia(panel.getO());
			panel.setO(//panel.getO().cheng(r));
					realclick.jian(
					realclick.jian(panel.getO()).cheng(r)
					));//*/
			panel.setOQ(newoq);
		}

		public void keyPressed(KeyEvent e) {
			toolbar.refreshAll();
			if(e.getKeyCode()==KeyEvent.VK_B){
				try{
					panel.resetO();
				}catch(Exception ee){}
			}
		}
		
	}
	
	
}
