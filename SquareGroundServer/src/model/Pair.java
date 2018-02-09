package model;

public class Pair<T,V>{
	public T x;
	public V y;
	public Pair(T t,V v){
		this.x=t;
		this.y=v;
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(o instanceof Pair)
			return this.x.equals(((Pair<?,?>) o).x) && this.y.equals(((Pair<?,?>) o).y);
		return false;
	}
	

}
