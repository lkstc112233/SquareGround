package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Main;

import org.dom4j.DocumentException;

import sim.Helper;
import sim.Simulation;
import simulation.drawer.DrawControler;
import simulation.driver.CAR;
import simulation.element.Item;
import simulation.road.Road.Bag;
import simulation.tool.WIN;
import simulation.tool.WOut;



public class WYToolBar extends JPanel {
	private static final long serialVersionUID = -2828919544929623588L;

	private Simulation simulation;
	
	public WYToolBar(Simulation simulation){
		this.simulation=simulation;
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridBagLayout());
		this.setup();
	}
	
	private JPanel[] panels=new JPanel[50];
	private JComponent[] cs=new JComponent[50];
	private int[] cis=new int[50];
	private int imax=0;
	
	public void setup(){
		int i=0,y=0;
		//============================================================================
		cs[i]=new JLabel("===============WY===============");
		((JLabel)cs[i]).setFont(new Font("宋体",Font.PLAIN,18));
		panels[i]=Helper.createComponent(this,cs[i],0,y,6,1,false);
		i++;y++;
		//============================================================================
		cs[i]=new JLabel("Open Task File");
		panels[i]=Helper.createComponent(this,cs[i],0,y,3,1,false);
		panels[i].addMouseListener(new WYL(){
			public void mouseReleased(MouseEvent e){
				JFileChooser fc=new JFileChooser();
				fc.setFileFilter(Helper.file_wyt);
				fc.setCurrentDirectory(new File("D://"));//设置默认目录 打开是程序当前目录
				fc.setDialogTitle("open task file");//自定义选择框标题
				int r=fc.showDialog(null,"打开");//这行代码取代showOpenDialog和showSaveDialog
				if (r==JFileChooser.CANCEL_OPTION||r==JFileChooser.ERROR_OPTION){
					simulation.debugger.debug("Fail to open the file!");
					return;
				}
				File f=fc.getSelectedFile();
				if (f==null){
					simulation.debugger.debug("File does not exist!");
					return;
				}
				try{
					WYTask task=(WYTask)WIN.read(f);
					Simulation nextSim=new Simulation(task);
					nextSim.openFrame();
					simulation.close();
					nextSim.debugger.debug("Open the file successfully!");
					Helper.now_simultion=nextSim;
				}catch(DocumentException ee){
					simulation.debugger.debug("Cannot read the file!");
				}
				refreshAll();
			}
		});
		i++;
		//============================================================================
		if(true){
			//==============================
			cs[i]=new JLabel(
					simulation.getTask().getRoad().isStart()?
							"Pause Simulation"
							:"Start Simulation");
			cis[0]=i;
			panels[i]=Helper.createComponent(this,cs[i],3,y,3,1,false);
			panels[i].addMouseListener(new WYL(){
				public void mouseReleased(MouseEvent e){
					if(simulation==null||simulation.getTask()==null||simulation.getTask().getRoad()==null){
						((JLabel)cs[cis[0]]).setText("ERROR");
						refreshAll();
						return;
					}
					if(simulation.getTask().getRoad().isStart()){
						simulation.getTask().getRoad().pause();
						((JLabel)cs[cis[0]]).setText("Start Simulation");
						simulation.debugger.debug("Simulation is paused!");
					}else{
						simulation.getTask().getRoad().Start();
						((JLabel)cs[cis[0]]).setText("Pause Simulation");
						simulation.debugger.debug("Simulation is started!");
					}
					refreshAll();
				}
			});
			i++;y++;
			//============
			cs[i]=new JLabel("Close");
			cis[1]=i;
			panels[i]=Helper.createComponent(this,cs[i],0,y,1,1,false);
			panels[i].addMouseListener(new WYL(){
				public void mouseReleased(MouseEvent e){
					if(simulation==null){
						((JLabel)cs[cis[1]]).setText("ERROR");
						refreshAll();
						return;
					}
					simulation.close();
					System.exit(0);//TODO exit!
				}
			});
			i++;
			//============
			cs[i]=new JLabel("Save");
			cis[2]=i;
			panels[i]=Helper.createComponent(this,cs[i],1,y,1,1,false);
			panels[i].addMouseListener(new WYL(){
				public void mouseReleased(MouseEvent e){
					if(simulation==null||simulation.getTask()==null){
						((JLabel)cs[cis[2]]).setText("ERROR");
						refreshAll();
						return;
					}
					JFileChooser fc=new JFileChooser();
					fc.setFileFilter(Helper.file_wyt);
					fc.setCurrentDirectory(new File("D:/"));//设置默认目录 打开直接默认D盘
					fc.setDialogTitle("save task file");//自定义选择框标题
					fc.setSelectedFile(new File("Untitled.wyt"));//设置默认文件名
					int r=fc.showDialog(null,"保存");//这行代码取代showOpenDialog和showSaveDialog
					if (r==JFileChooser.CANCEL_OPTION||r==JFileChooser.ERROR_OPTION){
						simulation.debugger.debug("Fail to open the file!");
						return;
					}
				    File f=fc.getSelectedFile();
					WOut.createNewDocument();
					WOut.print(simulation.getTask());
					try{
						WOut.write(f);
						simulation.debugger.debug("Save the file successfully!");
					}catch(IOException ee){
						simulation.debugger.debug("Cannot read the file!");
					}
					refreshAll();
				}
			});
			i++;
			//============
			if(simulation.getTask().getFrame()==null||
					simulation.getTask().getFrame().getChoosedCar()==null){
				//============
				if(simulation.getTask().getFocusCar()!=null){
					cs[i]=new JLabel("Release");
					cis[3]=i;
					panels[i]=Helper.createComponent(this,cs[i],2,y,1,1,false);
					panels[i].addMouseListener(new WYL(){
						public void mouseReleased(MouseEvent e){
							if(simulation==null||simulation.getTask()==null
									||simulation.getTask().getFocusCar()==null){
								((JLabel)cs[cis[3]]).setText("ERROR");
								refreshAll();
								return;
							}
							simulation.getTask().setFocusCar(null);
							refreshAll();
						}
					});
					i++;
				}else{
					//empty
					cs[i]=new JLabel("");
					panels[i]=Helper.createComponent(this,cs[i],2,y,1,1,false);
					i++;
				}
				//============
				if(simulation.getTask().getFrame()==null||simulation.getTask().getFrame().carpool==null||simulation.getTask().getFrame().carpool.getChoosedCar()==null){
					//============
					//empty
					cs[i]=new JLabel("");
					panels[i]=Helper.createComponent(this,cs[i],3,y,1,1,false);
					i++;
				}else{
					//============
					cs[i]=new JLabel(simulation.getTask().getFrame().carpool.isAdding()?
							"Adding":"Add");
					cis[17]=i;
					panels[i]=Helper.createComponent(this,cs[i],3,y,1,1,false);
					panels[i].addMouseListener(new WYL(){
						public void mouseReleased(MouseEvent e){
							if(simulation==null||simulation.getTask()==null
									||simulation.getTask().getFrame()==null
									||simulation.getTask().getFrame().carpool.getChoosedCar()==null){
								((JLabel)cs[cis[17]]).setText("ERROR");
								refreshAll();
								return;
							}
							if(simulation.getTask().getFrame().carpool.isAdding()){
								((JLabel)cs[cis[17]]).setText("Add");
								simulation.getTask().getFrame().carpool.setAdding(false);
							}else{
								((JLabel)cs[cis[17]]).setText("Adding");
								simulation.getTask().getFrame().carpool.setAdding(true);
							}
							refreshAll();
						}
					});
					i++;
				}
				//============
				//empty
				cs[i]=new JLabel("");
				panels[i]=Helper.createComponent(this,cs[i],4,y,2,1,false);
				i++;y++;
			}else{
				//============
				if(simulation.getTask().getFocusCar()!=null){
					//============
					cs[i]=new JLabel("Release");
					cis[4]=i;
					panels[i]=Helper.createComponent(this,cs[i],2,y,1,1,false);
					panels[i].addMouseListener(new WYL(){
						public void mouseReleased(MouseEvent e){
							if(simulation==null||simulation.getTask()==null
									||simulation.getTask().getFocusCar()==null){
								((JLabel)cs[cis[4]]).setText("ERROR");
								refreshAll();
								return;
							}
							simulation.getTask().setFocusCar(null);
							refreshAll();
						}
					});
					i++;
				}else{
					//============
					cs[i]=new JLabel("Focus");
					cis[5]=i;
					panels[i]=Helper.createComponent(this,cs[i],2,y,1,1,false);
					panels[i].addMouseListener(new WYL(){
						public void mouseReleased(MouseEvent e){
							if(simulation==null||simulation.getTask()==null||simulation.getTask().getFrame()==null
									||simulation.getTask().getFrame().getChoosedCar()==null){
								((JLabel)cs[cis[5]]).setText("ERROR");
								refreshAll();
								return;
							}
							simulation.getTask().setFocusCar(simulation.getTask().getFrame().getChoosedCar());
							refreshAll();
						}
					});
					i++;
				}
				//============
				cs[i]=new JLabel("Pool");
				cis[6]=i;
				panels[i]=Helper.createComponent(this,cs[i],3,y,1,1,false);
				panels[i].addMouseListener(new WYL(){
					public void mouseReleased(MouseEvent e){
						if(simulation==null||simulation.getTask()==null||simulation.getTask().getFrame()==null
								||simulation.getTask().getFrame().getChoosedCar()==null){
							((JLabel)cs[cis[6]]).setText("ERROR");
							refreshAll();
							return;
						}
						CAR car=simulation.getTask().getFrame().getChoosedCar().copy();
						if(car==null){
							((JLabel)cs[cis[6]]).setText("ERROR");
							refreshAll();
							return;
						}
						car.setCenter(car.getWH().chu(2));
						//TODO
						simulation.getTask().getFrame().carpool.cars.add(car);
						refreshAll();
					}
				});
				i++;
				//============
				cs[i]=new JLabel("Delete");
				cis[7]=i;
				panels[i]=Helper.createComponent(this,cs[i],4,y,1,1,false);
				panels[i].addMouseListener(new WYL(){
					public void mouseReleased(MouseEvent e){
						if(simulation==null||simulation.getTask()==null||simulation.getTask().getFrame()==null||simulation.getTask().getRoad()==null
								||simulation.getTask().getFrame().getChoosedCar()==null){
							((JLabel)cs[cis[7]]).setText("ERROR");
							refreshAll();
							return;
						}
						CAR car=simulation.getTask().getFrame().getChoosedCar();
						Bag<Item> items=simulation.getTask().getRoad().getBagOfItem();
						if(items.contains(car)){
							synchronized(items){
								items.remove(car);
							}
						}else{
							((JLabel)cs[cis[7]]).setText("ERROR");
							refreshAll();
							return;
						}
						refreshAll();
					}
				});
				i++;
				//============
				cs[i]=new JLabel("Kill");
				cis[8]=i;
				panels[i]=Helper.createComponent(this,cs[i],5,y,1,1,false);
				panels[i].addMouseListener(new WYL(){
					public void mouseReleased(MouseEvent e){
						if(simulation==null||simulation.getTask()==null||simulation.getTask().getFrame()==null||simulation.getTask().getRoad()==null
								||simulation.getTask().getFrame().getChoosedCar()==null){
							((JLabel)cs[cis[8]]).setText("ERROR");
							refreshAll();
							return;
						}
						CAR car=simulation.getTask().getFrame().getChoosedCar();
						car.kill();
						if(simulation.getTask().getRoad().getOutOfStreets()==0){
							//爆炸而亡
							Bag<Item> effects=simulation.getTask().getRoad().getBagOfEffect();
							synchronized(effects){
								effects.add(new Item(car.getCenter(),2,2,0).setBG(DrawControler.gif_Bomb));
							}
						}else{
							Bag<Item> items=simulation.getTask().getRoad().getBagOfItem();
							if(items.contains(car)){
								synchronized(items){
									items.remove(car);
								}
							}else{
								((JLabel)cs[cis[7]]).setText("ERROR");
								refreshAll();
								return;
							}
						}
						refreshAll();
					}
				});
				i++;y++;
			}
			//==============================
			cs[i]=new JLabel(
					simulation.getEadClient()==null?
							"Prepare Sending to EAD"
							:simulation.getEadClient().isStart()?
									"Pause Sending to EAD"
									:"Start Sending to EAD");
			cis[9]=i;
			panels[i]=Helper.createComponent(this,cs[i],0,y,3,1,false);
			panels[i].addMouseListener(new WYL(){
				public void mouseReleased(MouseEvent e){
					if(simulation==null){
						((JLabel)cs[cis[9]]).setText("ERROR");
						refreshAll();
						return;
					}
					if(simulation.getEadClient()==null){
						try{
							String IP=((JTextField)cs[cis[10]]).getText();
							int port=Integer.parseInt(((JTextField)cs[cis[11]]).getText());
							simulation.createEadClient(IP,port);
							((JLabel)cs[cis[9]]).setText("Start Sending to EAD");
							simulation.debugger.debug("Redy to send to EAD!");
						}catch(Exception ee){
							simulation.debugger.debug("Fail to create a client!");
						}
					}else{
						if(simulation.getEadClient().isStart()){
							simulation.getEadClient().pause();
							((JLabel)cs[cis[9]]).setText("Pause Sending to EAD");
						}else{
							try{
								simulation.openEadClient();
								((JLabel)cs[cis[9]]).setText("Pause Sending to EAD");
								simulation.debugger.debug("Start sending to EAD!");
							}catch(Exception ee){
								simulation.debugger.debug("Fail to start sending to EAD!");
							}
						}
					}
					refreshAll();
				}
			});
			i++;
			//==============================
			if(simulation.getEadClient()==null){
				//IP
				cs[i]=new JTextField(Simulation.defaultIP);
				cis[10]=i;
				panels[i]=Helper.createComponent(this,cs[i],3,y,2,1,false);
				i++;
				//port
				cs[i]=new JTextField(""+Simulation.port_client);
				cis[11]=i;
				panels[i]=Helper.createComponent(this,cs[i],5,y,1,1,false);
				i++;y++;
			}else{
				//IP-port
				cs[i]=new JLabel(simulation.getEadClient().getIP()
						+"-"+simulation.getEadClient().getPort());
				panels[i]=Helper.createComponent(this,cs[i],3,y,2,1,false);
				i++;
				//close
				cs[i]=new JLabel("Close");
				cis[12]=i;
				panels[i]=Helper.createComponent(this,cs[i],5,y,1,1,false);
				panels[i].addMouseListener(new WYL(){
					public void mouseReleased(MouseEvent e){
						if(simulation==null||simulation.getEadClient()==null){
							((JLabel)cs[cis[12]]).setText("ERROR");
							refreshAll();
							return;
						}
						simulation.closeEadClient();
						simulation.debugger.debug("Close the client sending to EAD!");
						refreshAll();
					}
				});
				i++;y++;
			}
			//==============================
			//==============================
			cs[i]=new JLabel(
					simulation.getEadServer()==null?
							"Prepare Receiving from EAD"
							:simulation.getEadServer().isStart()?
									"Pause Receiving from EAD"
									:"Start Receiving from EAD");
			cis[13]=i;
			panels[i]=Helper.createComponent(this,cs[i],0,y,3,1,false);
			panels[i].addMouseListener(new WYL(){
				public void mouseReleased(MouseEvent e){
					if(simulation==null){
						((JLabel)cs[cis[13]]).setText("ERROR");
						refreshAll();
						return;
					}
					if(simulation.getEadServer()==null){
						try{
							int port=Integer.parseInt(((JTextField)cs[cis[14]]).getText());
							simulation.createEadServer(port);
							((JLabel)cs[cis[13]]).setText("Start Receiving from EAD");
							simulation.debugger.debug("Redy to receive msg from EAD!");
						}catch(Exception ee){
							simulation.debugger.debug("Fail to create a server!");
						}
					}else{
						if(simulation.getEadServer().isStart()){
							simulation.getEadServer().pause();
							((JLabel)cs[cis[13]]).setText("Start Receiving from EAD");
							simulation.debugger.debug("Pause receiving from EAD!");
						}else{
							try{
								simulation.openEadServer();
								simulation.debugger.debug("Start receiving from EAD!");
								((JLabel)cs[cis[13]]).setText("Pause Receiving from EAD");
							}catch(Exception ee){
								simulation.debugger.debug("Fail to start receiving from EAD!");
							}
						}
					}
					refreshAll();
				}
			});
			i++;
			//==============================
			if(simulation.getEadServer()==null){
				//empty
				cs[i]=new JLabel("");
				panels[i]=Helper.createComponent(this,cs[i],3,y,2,1,false);
				i++;
				//port
				cs[i]=new JTextField(""+Simulation.port_server);
				cis[14]=i;
				panels[i]=Helper.createComponent(this,cs[i],5,y,1,1,false);
				i++;y++;
			}else{
				//empty
				cs[i]=new JLabel("");
				panels[i]=Helper.createComponent(this,cs[i],3,y,1,1,false);
				i++;
				//port
				cs[i]=new JLabel(""+simulation.getEadServer().getPort());
				panels[i]=Helper.createComponent(this,cs[i],4,y,1,1,false);
				i++;
				//close
				cs[i]=new JLabel("Close");
				cis[15]=i;
				panels[i]=Helper.createComponent(this,cs[i],5,y,1,1,false);
				panels[i].addMouseListener(new WYL(){
					public void mouseReleased(MouseEvent e){
						if(simulation==null||simulation.getEadServer()==null){
							((JLabel)cs[cis[15]]).setText("ERROR");
							refreshAll();
							return;
						}
						simulation.closeEadServer();
						simulation.debugger.debug("Close the server receiving from EAD!");
						refreshAll();
					}
				});
				i++;y++;
			}
		}//close if(simulation.getTask().getRoad()!=null){
		else{
			//empty
			cs[i]=new JLabel("");
			panels[i]=Helper.createComponent(this,cs[i],3,y,3,1,false);
			i++;y++;
		}
		//============================================================================
		cs[i]=new JLabel(
				simulation.debugger.isShowingDebugMsg()?
						"Pause Showing Debug Msg"
						:"Start Showing Debug Msg");
		cis[16]=i;
		panels[i]=Helper.createComponent(this,cs[i],0,y,4,1,false);
		panels[i].addMouseListener(new WYL(){
			public void mouseReleased(MouseEvent e){
				if(simulation==null||simulation.debugger==null){
					((JLabel)cs[cis[16]]).setText("ERROR");
					refreshAll();
					return;
				}
				if(simulation.debugger.isShowingDebugMsg()){
					simulation.debugger.pauseShowingDebugMsg();
					((JLabel)cs[cis[16]]).setText("Start Showing Debug Msg");
				}else{
					simulation.debugger.startShowingDebugMsg();
					((JLabel)cs[cis[16]]).setText("Pause Showing Debug Msg");
				}
				refreshAll();
			}
		});
		i++;
		//============================================================================
		cs[i]=new JLabel("<<");
		panels[i]=Helper.createComponent(this,cs[i],4,y,1,1,false);
		panels[i].addMouseListener(new WYL(){
			public void mouseReleased(MouseEvent e){
				Main.what1(simulation);
			}
		});
		i++;
		//============================================================================
		cs[i]=new JLabel(">>");
		panels[i]=Helper.createComponent(this,cs[i],5,y,1,1,false);
		panels[i].addMouseListener(new WYL(){
			public void mouseReleased(MouseEvent e){
				Main.what2(simulation);
			}
		});
		i++;y++;
		//============================================================================
		//empty
		cs[i]=new JLabel("");
		panels[i]=Helper.createComponent(this,cs[i],0,y,6,1,true);
		i++;y++;
		//============================================================================
		//imax
		imax=i;
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
	
    public void refreshAll(){
    	this.clear();
    	this.setup();
    	super.updateUI();
    }
	
	
}
