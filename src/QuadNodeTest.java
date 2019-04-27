import static org.junit.Assert.*;

import org.junit.Test;

public class QuadNodeTest {

	@Test
	public void testQuadNodeDoubleDoubleDoubleDouble() {
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0);
		assertNotNull(node);
	}

	@Test
	public void testQuadNodeDoubleDoubleDoubleDoublePoint() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		assertNotNull(node);
	}

	@Test
	public void testGetP() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		assertEquals(node.getP().rank, 0);
	}

	@Test
	public void testSetP() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		Point<String> point1 = new Point<String>(1.0, 2.0);
		node.setP(point1);
		assertEquals(node.getP().rank, 0);
	}

	@Test
	public void testGetNw() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.getNw();
	}

	@Test
	public void testSetNw() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		QuadNode nw = new QuadNode(0.5, 0.5, 1.0, 1.0);
		node.setNw(nw);
	}

	@Test
	public void testGetNe() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.getNe();
	}

	@Test
	public void testSetNe() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		QuadNode ne = new QuadNode(0.5, 0.5, 1.0, 1.0);
		node.setNe(ne);
	}

	@Test
	public void testGetSw() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.getSw();
	}

	@Test
	public void testSetSw() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		QuadNode sw = new QuadNode(0.5, 0.5, 1.0, 1.0);
		node.setSw(sw);
	}

	@Test
	public void testGetSe() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.getSe();
	}

	@Test
	public void testSetSe() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		QuadNode se = new QuadNode(0.5, 0.5, 1.0, 1.0);
		node.setSe(se);
	}

	@Test
	public void testGetMinX() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		assertEquals(node.getMinX(), 1.0, 0.001);
	}

	@Test
	public void testSetMinX() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.setMinX(1.5);
		assertEquals(node.getMinX(), 1.5, 0.001);
	}

	@Test
	public void testGetMinY() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		assertEquals(node.getMinY(), 1.0, 0.001);
	}

	@Test
	public void testSetMinY() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.setMinY(1.5);
		assertEquals(node.getMinY(), 1.5, 0.001);
	}

	@Test
	public void testGetMaxX() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		assertEquals(node.getMaxX(), 2.0, 0.001);
	}

	@Test
	public void testSetMaxX() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.setMaxX(1.5);
	}

	@Test
	public void testGetMaxY() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		assertEquals(node.getMaxY(), 2.0, 0.001);
	}

	@Test
	public void testSetMaxY() {
		Point<String> point = new Point<String>(1.0, 2.0);
		QuadNode node = new QuadNode(1.0, 1.0, 2.0, 2.0, point);
		node.setMaxY(2.5);
	}

}
