import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * used for creating mst with Kruskal
 * @author shengfeng
 *
 */
public class DisjointSet {
	/**
	 * initialize the points so that the parent of each points to itself
	 * @param points the points to initialize
	 */
	public void makeSet(List<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			points.get(i).setParent(points.get(i));
			points.get(i).setRank(0);
		}
	}

	/**
	 * union point p1 and point p2
	 * 
	 * @param p1
	 * @param p2
	 */
	public void union(Point p1,Point p2) {
		Point parent1 = find(p1);
		Point parent2 = find(p2);
		
		// if parents are same, then return
		if (parent1 == parent2)
			return;
		
		// else union two parents
		if (parent1.getRank() == parent2.getRank()) {
			int temp = parent1.getRank();
			parent2.setParent(parent1);
			parent1.setRank(temp + 1);
		} else if (parent1.getRank() < parent2.getRank()) {
			parent1.setParent(parent2);
		} else if (parent1.getRank() > parent2.getRank()) {
			parent2.setParent(parent1);
		}
	}

	/**
	 * Find the parent of point p
	 * @param p
	 * @return the parent of p
	 */
	public Point find(Point p) {
		Point temp = p;
		Set<Point> pts = new HashSet<>();
		while (temp.getParent() != temp) {
			pts.add(temp);
			temp = temp.getParent();
		}
		for (Point p1 : pts) {
			p1.setParent(temp);
		}
		return temp;
	}
}
