import static org.junit.Assert.*;

import org.junit.Test;

public class QuadTreeViewerTest {

	@Test
	public void testWalk() {
		QuadTreeViewer viewer = new QuadTreeViewer(10, 10, 5);
//		viewer.getPath(1, 3);
		viewer.generateEdges();
//		viewer.getPath(1, 3);
		viewer.walk(0.2);
	}
	public void generalTest() {
		QuadTreeViewer qtv = new QuadTreeViewer(100, 100, 60);
		assertTrue(qtv.getPath("1", "2").size() != 0);
	}
	@Test
	public void testQuadTreeViewer() {
		assertNotNull(new QuadTreeViewer(10, 10, 5));
	}

	@Test
	public void testViewQuadTree() {
		QuadTreeViewer viewer = new QuadTreeViewer(10, 10, 5);
		viewer.viewQuadTree();
	}

	@Test
	public void testVisualize() {
		QuadTreeViewer viewer = new QuadTreeViewer(10, 10, 5);
		viewer.visualize(20);
	}

	@Test
	public void testGetPath() {
		QuadTreeViewer viewer = new QuadTreeViewer(10, 10, 5);
	}

	@Test
	public void testGenerateEdges() {
		QuadTreeViewer viewer = new QuadTreeViewer(10, 10, 5);
		viewer.generateEdges();
	}

}
