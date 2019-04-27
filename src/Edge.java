/**
 * Edges, containing start,end and weight
 * @author shengfeng
 *
 */
public class Edge implements Comparable {
	private Point start;
	private Point end;
	private double weight;
	public Edge(Point start,Point end,double weight) {
		this.start=start;
		this.end=end;
		this.weight=weight;
	}
	public Point getStart() {
		return start;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public Point getEnd() {
		return end;
	}
	public void setEnd(Point end) {
		this.end = end;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public int compareTo(Object o) {
		return ((Double)this.weight).compareTo(((Edge)o).getWeight());
	}
	@Override
	public boolean equals(Object o) {
		return (this.start.equals(((Edge)o).getStart()))&&this.end.equals(((Edge)o).getEnd());
	}
}
