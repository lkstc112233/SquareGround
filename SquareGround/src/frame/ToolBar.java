package frame;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import model.Operater;

public final class ToolBar extends Panel {
	private static final long serialVersionUID = -800095694161787037L;
	
	private Frame frame;
	
	public ToolBar(Frame frame){
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridBagLayout());
		this.show=true;
		this.setup();
		this.frame=frame;
	}
	
	private JPanel[] panels=new JPanel[50];
	private JComponent[] cs=new JComponent[50];
	private int[] cis=new int[50];
	private int imax=0;
	private boolean show;
	
	public void setShow(boolean show){
		this.show=show;
		this.refreshDeep();
	}
	public boolean getShow(){
		return this.show;
	}

    public void refreshDeep(){
    	this.clear();
    	this.setup();
    	this.refresh();
    }
    
	public void clear(){
		for (int i=0;i<imax;i++){
			if (panels[i]!=null){
				if (cs[i]!=null)
					panels[i].remove(cs[i]);
				this.remove(panels[i]);
			}
			cs[i]=panels[i]=null;
		}
	}
	

	public void setup(){
		int i=0,y=0,w,W=6,lef=1;
		if()
		//============================================================================
		cs[i]=new JLabel("=============Square Ground v1.0=============");
		((JLabel)cs[i]).setFont(new Font("ËÎÌו",Font.PLAIN,18));
		panels[i]=Helper.createComponent(this,cs[i],lef,y,W,1,false);
		i++;y++;
		//============================================================================
		cs[i]=new JLabel("IP & port");
		panels[i]=Helper.createComponent(this,cs[i],lef,y,w = 3,1,false);
		i++;
		cs[i]=this.frame.oper.Statu_get(Operater.OperaterStatu.LOAD)?
				new JTextField(20)
				:new JLabel(""+this.frame.oper.get_ip()+":"+this.frame.oper.get_port());
		panels[i]=Helper.createComponent(this,cs[i],w,y,W-w,1,false);
		cis[0]=i;
		i++;y++;
		//============================================================================
		cs[i]=new JLabel("Enter code");
		panels[i]=Helper.createComponent(this,cs[i],lef,y,w = 3,1,false);
		i++;
		cs[i]=this.frame.oper.Statu_get(Operater.OperaterStatu.LOAD)?
				new JTextField(20)
				:new JLabel(""+this.frame.oper.get_code());
		panels[i]=Helper.createComponent(this,cs[i],w,y,W-w,1,false);
		cis[1]=i;
		i++;y++;
		//============================================================================
		cs[i]=this.frame.oper.Statu_get(Operater.OperaterStatu.LOAD)?
				 new JLabel(">>  join the game  <<")
				:new JLabel("<< get out of here >>");
		panels[i]=Helper.createComponent(this,cs[i],lef,y,w = 6,1,false);
		panels[i].addMouseListener(new Listener(){
			public void mouseReleased(MouseEvent e){
				if(ToolBar.this.frame.oper.Statu_get(Operater.OperaterStatu.LOAD)){
					ToolBar.this.frame.oper.logout();
				}else{
					String ip,code;
					int port;
					try{
						String[] ipport=((JTextField)cs[cis[0]]).getText().split(":");
						ip=ipport[0];
						port=Integer.parseInt(ipport[1]);
						code=((JTextField)cs[cis[1]]).getText();
					}catch(Throwable t){
						t.printStackTrace();
						ToolBar.this.frame.debugger.debug("ip&port is error!");
						ToolBar.this.refresh();
						return;
					}
					ToolBar.this.frame.oper.login(ip, port, code);
				}
				ToolBar.this.refreshDeep();
			}
		});//*/
		i++;y++;
		//============================================================================
		//empty
		cs[i]=new JLabel("");
		panels[i]=Helper.createComponent(this,cs[i],0,y,6,1,true);
		i++;y++;
		//============================================================================
		//imax
		imax=i;
		cs[i]=this.getShow()?
				new JLabel(">")
				:new JLabel("<<");
		panels[i]=Helper.createComponent(this,cs[i],0,0,lef,y,true);
		panels[i].addMouseListener(new Listener(){
			public void mouseReleased(MouseEvent e){
				ToolBar.this.setShow(!ToolBar.this.getShow());
			}
		});
	}
	
	
	

}
