import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DisjointSetTest {

	@Test
	void testMakeSet() {
		Point<Integer> pt = new Point<Integer>(1, 2);
		DisjointSet set = new DisjointSet();
		ArrayList<Point> ls = new ArrayList<>();
		ls.add(pt);
		set.makeSet(ls);
	}

	@Test
	void testUnion() {
		DisjointSet set = new DisjointSet();
		ArrayList<Point> ls = new ArrayList<>();
		Point<Integer> pt1 = new Point<Integer>(1, 2);
		Point<Integer> pt2 = new Point<Integer>(1, 3);
		Point<Integer> pt3 = new Point<Integer>(2, 3);
		Point<Integer> pt4 = new Point<Integer>(2, 4);
		ls.add(pt1);
		ls.add(pt2);
		ls.add(pt3);
		ls.add(pt4);
		set.makeSet(ls);
		set.union(pt1, pt2);
		set.union(pt1, pt3);
		set.union(pt2, pt1);
		set.union(pt4, pt1);
	}

	@Test
	void testFind() {
		DisjointSet set = new DisjointSet();
		Point<Integer> pt1 = new Point<Integer>(1, 2);
		ArrayList<Point> ls = new ArrayList<>();
		ls.add(pt1);
		set.makeSet(ls);
		set.find(pt1);
	}

}
