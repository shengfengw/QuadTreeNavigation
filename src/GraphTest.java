import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class GraphTest {

	@Test
	public void testGraph() {
		Graph graph = new Graph(100, 100);
		assertNotNull(graph);
	}

	@Test
	public void testImportPoints() {
		Graph graph = new Graph(100, 100);
		List<Point> list = new LinkedList<>();
		for(int i = 1; i < 50; i++) {
			for(int j = 1; j < 50; j++) {
				list.add(new Point<>(i, j));
			}
		}
		graph.importPoints(list);
	}

	@Test
	public void testGenerateEdges() {
		Graph graph = new Graph(100, 100);
		List<Point> list = new LinkedList<>();
		for(int i = 1; i < 50; i++) {
			for(int j = 1; j < 50; j++) {
				list.add(new Point<>(i, j));
			}
		}
		graph.importPoints(list);
		graph.generateEdges();
	}

	@Test
	public void testUpdateAdjList() {
		Graph graph = new Graph(100, 100);
		List<Point> list = new LinkedList<>();
		for(int i = 1; i < 50; i = i + 5) {
			for(int j = 1; j < 50; j = j + 5) {
				list.add(new Point<>(i, j));
			}
		}
		graph.importPoints(list);
		graph.generateEdges();
		graph.updateAdjList();
	}

	@Test
	public void testUpdateAdjMatrix() {
		Graph graph = new Graph(100, 100);
		List<Point> list = new LinkedList<>();
		for(int i = 1; i < 50; i = i + 5) {
			for(int j = 1; j < 50; j = j + 5) {
				list.add(new Point<>(i, j));
			}
		}
		graph.importPoints(list);
		graph.generateEdges();
		graph.updateAdjList();
		graph.updateAdjMatrix();
	}

	@Test
	public void testBfs() {
		Graph graph = new Graph(100, 100);
		List<Point> list = new LinkedList<>();
		for(int i = 1; i < 50; i = i + 5) {
			for(int j = 1; j < 50; j = j + 5) {
				list.add(new Point<>(i, j));
			}
		}
		graph.importPoints(list);
		graph.generateEdges();
		graph.bfs();
	}

	@Test
	public void testDijkstra() {
		Graph graph = new Graph(100, 100);
		List<Point> list = new LinkedList<>();
		for(int i = 1; i < 50; i = i + 5) {
			for(int j = 1; j < 50; j = j + 5) {
				list.add(new Point<>(i, j));
			}
		}
		graph.importPoints(list);
		graph.generateEdges();
		graph.bfs();
		graph.dijkstra(5, 10);
	}

	@Test
	public void testGetNodes() {
		Graph g = new Graph(10, 10);
		g.getNodes();
	}

	@Test
	public void testSetNodes() {
		Graph g = new Graph(10, 10);
		Point pt = new Point(1, 2);
		ArrayList<Point> ls = new ArrayList<>();
		ls.add(pt);
		g.setNodes(ls);
	}

	@Test
	public void testGetEdges() {
		Graph g = new Graph(10, 10);
		g.getEdges();
	}

	@Test
	public void testSetEdges() {
		Graph g1 = new Graph(10, 10);
		Graph g2 = new Graph(10, 10);
		g2.setEdges(g1.getEdges());
	}

	@Test
	public void testGetAdjList() {
		Graph g = new Graph(10, 10);
		g.getAdjList();
	}

	@Test
	public void testSetAdjList() {
		Graph g1 = new Graph(10, 10);
		Graph g2 = new Graph(10, 10);
		g2.setAdjList(g1.getAdjList());
		
	}

}
