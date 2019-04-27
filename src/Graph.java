import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Graph, contains nodes and edges
 * 
 * @author shengfeng
 *
 */
public class Graph {
	List<Point> nodes;
	List<Edge> edges;
	HashMap<Point, HashSet<Edge>> adjList;
	double width;
	double height;
	double[][] adjMatrix;

	public Graph(double width, double height) {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		adjList = new HashMap();
		this.width = width;
		this.height = height;
	}

	/**
	 * import points to the graph
	 * 
	 * @param points the points to import
	 */
	public void importPoints(List<Point> points) {
		this.nodes.addAll(points);

	}

	/**
	 * generate random edges to make the graph a connected graph, first make mst to
	 * ensure connectivity, then add random edges to make cycles
	 */
	public void generateEdges() {
		List<Edge> possibleEdges = new ArrayList<>();
		List<Edge> chosenEdges = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (i == j)
					continue;
				double x1 = nodes.get(i).getX();
				double y1 = nodes.get(i).getY();
				double x2 = nodes.get(j).getX();
				double y2 = nodes.get(j).getY();
				double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				possibleEdges.add(new Edge(nodes.get(i), nodes.get(j), distance));
			}
		}
		// first to ensure connected graph, create mst with Kruskal
		Collections.sort(possibleEdges);
		// do union find
		HashSet<Edge> mst = new HashSet<>();
		DisjointSet dj = new DisjointSet();
		dj.makeSet(this.nodes);
		int e = 0;
		int v = nodes.size();
		int i = 0;
		while (e < v - 1) {

			Edge nextEdge = possibleEdges.get(i++);

			Point start = nextEdge.getStart();
			Point end = nextEdge.getEnd();
			Point p1 = dj.find(start);
			Point p2 = dj.find(end);
			if (p1 == p2) {
				continue;
			} else {
				chosenEdges.add(nextEdge);
				mst.add(nextEdge);
				e++;
				dj.union(p1, p2);
			}

		}

		Collections.shuffle(possibleEdges);

		i = 0;
		// add other randomized edges make cycles, number added is arbitrary
		while (e < v * (v - 1) / 32) { // 32 is an arbitrary constant, should be larger than 2
			if (i >= possibleEdges.size())
				break;
			if (possibleEdges.get(i).getWeight() > (width + height) / 4) { // a arbitrary constraint to make the graph
																			// look cleaner, basically picking shorter
																			// edges
				i++;
				continue;
			}
			if (!mst.contains(possibleEdges.get(i))) {
				chosenEdges.add(possibleEdges.get(i));
				mst.add(possibleEdges.get(i));
				e++;
			}
			i++;
		}
		this.edges = chosenEdges;
		updateAdjList();
		updateAdjMatrix();

	}

	/**
	 * update the adjacency list
	 */
	public void updateAdjList() {
		for (Point p : nodes) {
			this.adjList.put(p, new HashSet<>());
		}
		for (Edge e : edges) {
			Point start = e.getStart();
			Point end = e.getEnd();
			this.adjList.get(start).add(e);
			this.adjList.get(end).add(new Edge(e.getEnd(), e.getStart(), e.getWeight()));
		}
	}

	/**
	 * update the adjacency matrix
	 */
	public void updateAdjMatrix() {
		adjMatrix = new double[nodes.size()][nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				adjMatrix[i][j] = -1;
			}
		}
		for (Edge e : edges) {
			int i = this.getIndexOfNode(e.getStart());
			int j = this.getIndexOfNode(e.getEnd());
			adjMatrix[i][j] = e.getWeight();
			adjMatrix[j][i] = e.getWeight();
		}
	}

	/**
	 * do bfs on the graph, used for testing connectivity
	 * 
	 * @return if the graph is connected
	 */
	public boolean bfs() {
		HashSet<Point> visited = new HashSet<>();
		Queue<Point> q = new LinkedList<>();
		q.add(nodes.get(0));
		visited.add(nodes.get(0));
		while (!q.isEmpty()) {
			Point temp = q.remove();
			for (Edge e : this.edges) {
				if (e.getStart().equals(temp) && !visited.contains(e.getEnd())) {
					q.add(e.getEnd());
					visited.add(e.getEnd());
				} else if (e.getEnd().equals(temp) && !visited.contains(e.getStart())) {
					q.add(e.getStart());
					visited.add(e.getStart());
				}
			}
		}
		return visited.size() == nodes.size();
	}

	/**
	 * run dijkstra on the graph
	 * 
	 * @param srcIndex  the index of the source vertex
	 * @param destIndex the index of the destination vertex
	 * @return a list of the shortest path
	 */
	public List<Edge> dijkstra(int srcIndex, int destIndex) {
		double shortest[] = new double[nodes.size()];

		boolean included[] = new boolean[nodes.size()];
		List<Integer> pred = new ArrayList<>();

		for (int i = 0; i < nodes.size(); i++) {
			shortest[i] = Double.MAX_VALUE;
			included[i] = false;
			pred.add(-2);

		}

		shortest[srcIndex] = 0;

		for (int count = 0; count < nodes.size() - 1; count++) {
			int u = this.pickMinDistanceIndex(shortest, included);
			included[u] = true;
			for (int v = 0; v < nodes.size(); v++) {
				if (included[v])
					continue;
				if (adjMatrix[u][v] > 0 && shortest[u] != Double.MAX_VALUE
						&& shortest[u] + adjMatrix[u][v] < shortest[v]) {
					shortest[v] = shortest[u] + this.generateEdgeFromPoints(u, v).getWeight();
					pred.set(v, u);
				}
			}
		}

		Stack<Edge> s = new Stack<>();
		while (destIndex != srcIndex) {
			if (pred.get(destIndex) < 0)
				break;
			s.push(generateEdgeFromPoints(destIndex, pred.get(destIndex)));
			destIndex = pred.get(destIndex);

		}
		List<Edge> res = new ArrayList<>();
		while (!s.isEmpty()) {
			res.add(s.pop());
		}
		return res;
	}

	/**
	 * generate edge from two indexes of points
	 * 
	 * @param i index of the start node
	 * @param j index of the end node
	 * @return edge start from node with index i and end at node with index j
	 */
	private Edge generateEdgeFromPoints(int i, int j) {
		double x1 = nodes.get(i).getX();
		double y1 = nodes.get(i).getY();
		double x2 = nodes.get(j).getX();
		double y2 = nodes.get(j).getY();
		double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		return new Edge(nodes.get(i), nodes.get(j), distance);
	}

	/**
	 * pick the index of the node with the shortest distance in shortest array
	 * 
	 * @param shortest keeps the shortest distance for dijkstra
	 * @param included keep track of the included vertices
	 * @return the index of the node with the shortest distance
	 */
	private int pickMinDistanceIndex(double[] shortest, boolean[] included) {
		double min = Double.MAX_VALUE;
		int res = -3;
		for (int v = 0; v < nodes.size(); v++) {
			if (!included[v] && shortest[v] < min) {
				min = shortest[v];
				res = v;
			}
		}

		return res;

	}

	/**
	 * get index from node
	 * 
	 * @param p the point to look up
	 * @return the index of point p
	 */
	private int getIndexOfNode(Point p) {
		for (int i = 0; i < nodes.size(); i++) {
			Point pi = nodes.get(i);
			if (pi.getX() == p.getX() && pi.getY() == p.getY()) {
				return i;
			}
		}
		return -99;
	}

	public List<Point> getNodes() {
		return nodes;
	}

	public void setNodes(List<Point> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public HashMap<Point, HashSet<Edge>> getAdjList() {
		return adjList;
	}

	public void setAdjList(HashMap<Point, HashSet<Edge>> adjList) {
		this.adjList = adjList;
	}

}
