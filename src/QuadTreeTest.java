import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class QuadTreeTest {
	@Test
	public void insertTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		assertTrue(qt.insert(new Point(3, 5)));
		assertTrue(qt.insert(new Point(7, 42)));
		assertTrue(qt.insert(new Point(88, 88)));
		assertTrue(qt.insert(new Point(88, 89)));
		assertTrue(!qt.insert(new Point(88, 89)));
		qt.getNode(new Point(7, 42));
		qt.getNode(new Point(5, 20));
	}

	@Test
	public void getNodeTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.insert(new Point(3, 5));
		qt.insert(new Point(7, 42));
		qt.insert(new Point(88, 88));
		qt.insert(new Point(88, 89));
		assertTrue(qt.getNode(new Point(7, 42)) != null);
		assertTrue(qt.getNode(new Point(5, 20)) == null);
	}

	@Test
	public void containsTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.insert(new Point(3, 5));
		qt.insert(new Point(7, 42));
		qt.insert(new Point(88, 88));
		qt.insert(new Point(88, 89));
		assertTrue(qt.contains(new Point(3, 5)));
		assertTrue(!qt.contains(new Point(16, 9)));
	}

	@Test
	public void sizeTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		assertEquals(qt.size(),1);
		qt.insert(new Point(3, 5));
		assertEquals(qt.size(),1);
	}
	@Test
	public void clearTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.insert(new Point(3, 5));
		qt.insert(new Point(3, 3));
		qt.clear();
		assertEquals(qt.size(),1);
	}
	@Test
	public void selectQuadTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.insert(new Point(77,88));
		qt.split(qt.getRoot());
		qt.insert(new Point(20,88));
		assertEquals((int)qt.selectQuad(qt.getRoot(), new Point(3, 5)).getMaxX(),50);
		assertEquals((int)qt.selectQuad(qt.getRoot(), new Point(3, 5)).getMaxY(),50);

	}
}
