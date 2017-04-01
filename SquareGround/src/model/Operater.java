package model;

public interface Operater {
	
	
	static public enum OperaterStatu{
		LOAD,
		
		;
	}
	
	static public class Pair{
		public OperaterStatu statu;
		public boolean ok;
		public Pair(OperaterStatu statu,boolean ok){this.statu=statu;this.ok=ok;}
	}

	public boolean Statu_get(OperaterStatu statu);
	public Operater Statu_set(OperaterStatu statu,boolean ok);
	public Operater Statu_set(Pair p);

	public Pair login(String ip,int port,String code);
	public Pair logout();
	public String get_ip();
	public int get_port();
	public String get_code();
	
}
