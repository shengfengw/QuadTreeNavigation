import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EdgeTest {

	@Test
	void testGetStart() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge = new Edge(start, end, 3);
		assertTrue(edge.getStart().equals(start));
	}

	@Test
	void testSetStart() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge = new Edge(start, end, 3);
		Point pt = new Point(2, 1);
		edge.setStart(pt);
	}

	@Test
	void testGetEnd() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge = new Edge(start, end, 3);
		assertTrue(edge.getEnd().equals(end));
	}

	@Test
	void testSetEnd() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge = new Edge(start, end, 3);
		Point pt = new Point(2, 1);
		edge.setEnd(pt);
	}

	@Test
	void testGetWeight() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge = new Edge(start, end, 3);
		assertEquals(3, edge.getWeight());
	}

	@Test
	void testSetWeight() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge = new Edge(start, end, 3);
		edge.setWeight(4);
	}

	@Test
	void testCompareTo() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge1 = new Edge(start, end, 3);
		Edge edge2 = new Edge(end, start, 3);
		edge1.compareTo(edge2);
	}

	@Test
	void testEqualsObject() {
		Point start = new Point(1, 2);
		Point end = new Point(1, 1);
		Edge edge1 = new Edge(start, end, 3);
		Edge edge2 = new Edge(end, start, 3);
		assertFalse(edge1.equals(edge2));
		assertTrue(edge1.equals(edge1));
	}

}
