import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays the quadtree and the associated graph
 * 
 * @author shengfeng
 *
 */
public class QuadTreeViewer extends JPanel implements IUserInterface {
	int src;
	int dest;
	private QuadTree qt;
	private Graph myGraph;
	private Point currentPoint;
	List<Edge> route ;

	private final static int border = 10;
	private final static int size = 10;
	private final static int radius = 3;

	private final static BasicStroke stroke = new BasicStroke(2.0f);

	/**
	 * The main function
	 * 
	 * @param args arguments of the program
	 */
	public static void main(String args[]) {

		JFrame frame = new JFrame("QuadTree");
		QuadTreeViewer viewer = new QuadTreeViewer(100,100,60);
		viewer.getPath(10, 20);
		frame.getContentPane().add(viewer);
		frame.setSize(viewer.getSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		viewer.viewQuadTree();
	}
	/**
	 * initialize the quadtree and graph object
	 * 
	 * @param maxX horizontal length of the window
	 * @param maxY vertical length of the window
	 * @param num the number of points to generate
	 */
	public QuadTreeViewer(double maxX, double maxY,int num) {
		route = new ArrayList<>();
		qt = new QuadTree(0, 0, maxX, maxY);

		qt.setViewer(this);
		qt.generateQuadTree(num);

		myGraph = new Graph(maxX, maxY);
		myGraph.importPoints(qt.getPoints());
		this.generateEdges();

		setSize((int) maxX * size + 2 * border, (int) maxY * size + 2 * border);

		setBackground(Color.white);
		setForeground(Color.black);
	}

	/**
	 * view the quadtree
	 */
	public void viewQuadTree() {
		visualize(null, 1);
	}

	/**
	 * repaint the quadtree
	 * 
	 * @param p         The current point
	 * @param pauseTime The number of milliseconds to pause
	 */
	public synchronized void visualize(Point p, int pauseTime) {
		currentPoint = p;
		repaint();
		try {
			wait(pauseTime);
		} catch (Exception e) {
		}
	}

	/**
	 * paint the quadtree
	 * 
	 * @param g the graphics object to draw quadtree
	 */
	public void paint(Graphics g) {
		System.out.println("painting");
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		// Make the stroke a thick black line
		g2D.setStroke(stroke);
		// draw the quadnodes
		List<QuadNode> qnArray = qt.getQuadNodes();

		for (QuadNode qn : qnArray) {
			double minX = border + size * qn.getMinX();
			double minY = border + size * qn.getMinY();
			double maxX = border + size * qn.getMaxX();
			double maxY = border + size * qn.getMaxY();
			Shape l1 = new Line2D.Double(minX, minY, minX, maxY);
			g2D.draw(l1);
			Shape l2 = new Line2D.Double(maxX, minY, maxX, maxY);
			g2D.draw(l2);
			Shape l3 = new Line2D.Double(minX, minY, maxX, minY);
			g2D.draw(l3);
			Shape l4 = new Line2D.Double(minX, maxY, maxX, maxY);
			g2D.draw(l4);

		}
		// draw the points
		for (Point p : myGraph.getNodes()) {
			double x = border + size * p.getX();
			double y = border + size * p.getY();
			Shape point = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
			g2D.draw(point);
		}
		// draw the edges
		List<Edge> edges = myGraph.getEdges();
		g2D.setColor(Color.BLUE);
		for (Edge e : edges) {
			double x1 = border + size * e.getStart().getX();
			double y1 = border + size * e.getStart().getY();
			double x2 = border + size * e.getEnd().getX();
			double y2 = border + size * e.getEnd().getY();
			Shape l1 = new Line2D.Double(x1, y1, x2, y2);
			g2D.draw(l1);

		}
		// draw the shortest path
		g2D.setColor(Color.RED);
		for (Edge e : route) {
			double x1 = border + size * e.getStart().getX();
			double y1 = border + size * e.getStart().getY();
			double x2 = border + size * e.getEnd().getX();
			double y2 = border + size * e.getEnd().getY();
			Shape l1 = new Line2D.Double(x1, y1, x2, y2);
			g2D.draw(l1);

		}
		// draw start point and end point
		g2D.setColor(Color.RED);

		double x1 = border + size * myGraph.getNodes().get(src).getX();
		double y1 = border + size * myGraph.getNodes().get(src).getY();
		Shape start = new Ellipse2D.Double(x1 - radius, y1 - radius, 2 * radius, 2 * radius);
		g2D.draw(start);
		g2D.setColor(Color.DARK_GRAY);

		double x2 = border + size * myGraph.getNodes().get(dest).getX();
		double y2 = border + size * myGraph.getNodes().get(dest).getY();
		Shape end = new Ellipse2D.Double(x2 - radius, y2 - radius, 2 * radius, 2 * radius);
		g2D.draw(end);

	}

	@Override
	public List<Edge> getPath(int s, int e) {
		this.src=s;
		this.dest=e;
		this.route=this.myGraph.dijkstra(src, dest);
		return route;

	}

	@Override
	public void generateEdges() {
		myGraph.generateEdges();
		
	}

}
