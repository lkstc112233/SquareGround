package frame;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import main.Helper;

public final class ToolBar extends Panel {
	private static final long serialVersionUID = -800095694161787037L;
	
	
	public ToolBar(){
		super();
		this.setLayout(new GridBagLayout());
		this.show=true;
		this.setup();
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

	@Override
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
		if(this.getShow()){
			//============================================================================
			cs[i]=new JLabel("============="+Helper.Name+" "+Helper.Version+"=============");
			((JLabel)cs[i]).setFont(new Font("宋体",Font.PLAIN,18));
			panels[i]=Helper.createComponent(this,cs[i],lef,y,W,1,false);
			i++;y++;
			//============================================================================
			cs[i]=new JLabel("IP & port");
			panels[i]=Helper.createComponent(this,cs[i],lef,y,w = 3,1,false);
			i++;
			cs[i]=this.frame.oper.isConnected()?
					new JTextField(20)
					:new JLabel(""+this.frame.oper.getIP()+":"+this.frame.oper.getPort());
			panels[i]=Helper.createComponent(this,cs[i],w,y,W-w,1,false);
			cis[0]=i;
			i++;y++;
			if(!this.frame.oper.isConnected()){
				cs[i]=new JLabel(">> Connect to the Server <<");
				panels[i]=Helper.createComponent(this,cs[i],w,y,W,1,false);
				cis[1]=i;
				panels[i].addMouseListener(new Listener(){
					public void mouseReleased(MouseEvent e){
						//~~~~~~~~~~~~~~
						//连接到服务器
						String ip;
						int port;
						try{
							String[] ipport=((JTextField)cs[cis[0]]).getText().split(":");
							ip=ipport[0];
							port=Integer.parseInt(ipport[1]);
							ToolBar.this.frame.oper.connectServer(ip, port);
						}catch(Throwable t){
							t.printStackTrace();
							ToolBar.this.frame.debugger.debug("ip&port is error!");
						}
						ToolBar.this.frame.debugger.debug(ToolBar.this.frame.oper.isConnected()?
								"Server connected!"
								:"Fail to connect the Server!");
						ToolBar.this.frame.refreshDeep();
					}
				});//*/
				i++;y++;
			}
			if(this.frame.oper.isConnected() && this.frame.oper.getPlayer()!=null){
				//============================================================================
				cs[i]=new JLabel("Room Code");
				panels[i]=Helper.createComponent(this,cs[i],lef,y,w = 3,1,false);
				i++;
				cs[i]=this.frame.oper.getRoom()==null?
						new JTextField(20)
						:new JLabel(""+this.frame.oper.getRoom().getCode());
				panels[i]=Helper.createComponent(this,cs[i],w,y,W-w,1,false);
				cis[2]=i;
				i++;y++;
				//============================================================================
				cs[i]=this.frame.oper.getRoom()==null?
						 new JLabel(">>  join the game  <<")
						:new JLabel("<< get out of here >>");
				panels[i]=Helper.createComponent(this,cs[i],lef,y,w = 6,1,false);
				panels[i].addMouseListener(new Listener(){
					public void mouseReleased(MouseEvent e){
						if(this.frame.oper.getRoom()!=null){
							//~~~~~~~~~~~~~~
							//退出房间
							ToolBar.this.frame.oper.getPlayer().logoutRoom();
							ToolBar.this.frame.debugger.debug(ToolBar.this.frame.oper.getRoom()==null?
									"Get out of the Room successfully!"
									:"Fail to get out of the Room!");
						}else{
							String code=((JTextField)cs[cis[2]]).getText();
							if(code==null||code.length()<=0){
								//~~~~~~~~~~~~~~
								//创建room
								code=ToolBar.this.frame.oper.getPlayer().createRoom();
								((JTextField)cs[cis[2]]).setText(code);
								ToolBar.this.frame.debugger.debug(ToolBar.this.frame.oper.getRoom()!=null?
										("Get a new Room code: "+code+"!")
										:"Fail to get a new Room code!");
							}else{
								//~~~~~~~~~~~~~~
								//连接到room
								ToolBar.this.frame.oper.getPlayer().loginRoom(code);
							}
							ToolBar.this.frame.debugger.debug(ToolBar.this.frame.oper.getRoom()!=null?
									"Get in Room successfully!"
									:"Fail to get in the Room!");
						}
						ToolBar.this.frame.refreshDeep();
					}
				});//*/
				i++;y++;
			}
		}
		//============================================================================
		//empty
		cs[i]=new JLabel("");
		panels[i]=Helper.createComponent(this,cs[i],lef,y,6,1,true);
		i++;y++;
		//============================================================================
		cs[i]=this.getShow()?
				new JLabel(">")
				:new JLabel("<<");
		panels[i]=Helper.createComponent(this,cs[i],0,0,lef,y,true);
		panels[i].addMouseListener(new Listener(){
			public void mouseReleased(MouseEvent e){
				ToolBar.this.setShow(!ToolBar.this.getShow());
			}
		});
		//============================================================================
		//imax
		imax=i;
	}
	
	
	

}
