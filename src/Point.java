
public class Point<T> {
	double x;
	double y;
	T value;
	Point parent;
	int rank;
	
	Point(double x,double y,T value){
		this.x=x;
		this.y=y;
		this.value=value;
		this.parent=null;
		rank=0;
	}
	Point(double x,double y){
		this.x=x;
		this.y=y;
		this.value=null;
	}
	@Override
	public boolean equals(Object o) {
		return (this.x==((Point)o).getX()&&this.y==((Point)o).getY());
		
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public Point getParent() {
		return parent;
	}
	public void setParent(Point parent) {
		this.parent = parent;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

}
