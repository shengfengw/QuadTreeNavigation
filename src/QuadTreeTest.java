import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class QuadTreeTest {
	@Test
	public void insertTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		assertFalse(qt.insert(new Point(101, 101)));
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
		assertTrue(qt.getNode(new Point<>(100, 101)) == null);
		
		QuadNode node = new QuadNode(1, 1, 2, 2);
		assertTrue(qt.getNode(node, new Point(1.5, 1.5)) == null);
		
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
	
	@Test
	public void generateQuadTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.generateQuadTree(60);
	}
	
	@Test
	public void getPointsTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		assertFalse(qt.insert(new Point(101, 101)));
		assertTrue(qt.insert(new Point(3, 5)));
		assertTrue(qt.insert(new Point(7, 42)));
		assertTrue(qt.insert(new Point(88, 88)));
		assertTrue(qt.insert(new Point(88, 89)));
		assertTrue(!qt.insert(new Point(88, 89)));
		assertEquals(qt.getPoints().size(), 4);
	}
	
	@Test
	public void setRootTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.setRoot(new QuadNode(1, 1, 2, 2));
	}
	
	@Test
	public void getViewerTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.getViewer();
	}
	
	@Test
	public void setViewerTest() {
		QuadTree qt = new QuadTree(0, 0, 100, 100);
		qt.setViewer(new QuadTreeViewer(0, 0, 2));
	}
}
