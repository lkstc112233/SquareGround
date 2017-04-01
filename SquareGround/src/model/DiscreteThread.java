package model;

public abstract class DiscreteThread extends Thread {
	
	private boolean start=false;
	private boolean first=true;
		public void Start(){
			if(first){this.start();first=false;}
			this.start=true;
		}
		public void pause(){this.start=false;}
		public boolean isStart(){return start;}
	
	private boolean END=false;
		/** You can use this method only once! */
		public void End(){start=END=true;}
		public boolean getEND(){return this.END;}
	
	private double dt=0.01;
		public double getDt(){return dt;}
		public boolean setDt(long dt){if(dt<10L)return false;this.dt=dt/1000.0;return true;}
	private int Ttimes=1;
		public int getTtimes(){return Ttimes;}
		public void setTtimes(int Ttimes){this.Ttimes=Ttimes<1?1:Ttimes;}
	private long dt_sleep=(long)(dt*1000);
		public long getDtSleep(){return dt_sleep;}
		public boolean setDtSleep(long dt){if(dt<2L)return false;this.dt_sleep=dt;return true;}
	
	
	public void run(){
		long sta,end;
		while(!END){
			while(start){
				sta=System.currentTimeMillis();
				goby(dt/(double)Ttimes,Ttimes);//goby need to be synchronized when it is calculating.
				end=System.currentTimeMillis();
				try{
					if((sta=dt_sleep-sta+end)>0)
						Thread.sleep(sta);
					else System.out.println("waca!");
				}catch(InterruptedException ine){
				}catch(Exception e){e.printStackTrace();}
			}
			try{Thread.sleep(100L);
			}catch(InterruptedException ine){
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	protected abstract void goby(double dt,int times);
	
	public DiscreteThread copy(DiscreteThread d){
		if(d==null) return null;
		d.dt=dt;
		d.END=END;
		d.first=first;
		d.start=start;
		d.Ttimes=Ttimes;
		return d;
	}
}
