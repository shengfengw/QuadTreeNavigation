
public class QuadNode{
	private QuadNode nw;
	private QuadNode ne;
	private QuadNode sw;
	private QuadNode se;
	double minX;
	double minY;
	double maxX;
	double maxY;
	Point p;
	
	public QuadNode(double minX,double minY, double maxX, double maxY) {
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
		this.p=null;
	}
	public QuadNode(double minX,double minY, double maxX, double maxY,Point p) {
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
		this.p=p;
	}
	
	public Point getP() {
		return p;
	}
	public void setP(Point p) {
		this.p = p;
	}
	public QuadNode getNw() {
		return nw;
	}
	public void setNw(QuadNode nw) {
		this.nw = nw;
	}
	public QuadNode getNe() {
		return ne;
	}
	public void setNe(QuadNode ne) {
		this.ne = ne;
	}
	public QuadNode getSw() {
		return sw;
	}
	public void setSw(QuadNode sw) {
		this.sw = sw;
	}
	public QuadNode getSe() {
		return se;
	}
	public void setSe(QuadNode se) {
		this.se = se;
	}
	public double getMinX() {
		return minX;
	}
	public void setMinX(double minX) {
		this.minX = minX;
	}
	public double getMinY() {
		return minY;
	}
	public void setMinY(double minY) {
		this.minY = minY;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
}
