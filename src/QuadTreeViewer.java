import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private QuadNode currQN;
	List<Edge> route;
	static QuadTreeViewer viewer;
	private static int userDefinedSize;
	private static String userDefinedSrc;
	private static String userDefinedDes;

	private final static int border = 30;
	private final static int size = 12;
	private final static int radius = 3;

	private final static BasicStroke st = new BasicStroke(2.0f);

	public void walk(double speed) {
		for (int i = 0; i < route.size(); i++) {
			currentPoint = new Point(viewer.route.get(i).getStart().getX(), route.get(i).getStart().getY());
			while (true) {
				double x = currentPoint.getX();
				double y = currentPoint.getY();
				double dx = viewer.route.get(i).getEnd().getX() - viewer.route.get(i).getStart().getX();
				double dy = viewer.route.get(i).getEnd().getY() - viewer.route.get(i).getStart().getY();
				double dd = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				x = x + dx / dd * speed;
				y = y + dy / dd * speed;
				currentPoint.setX(x);
				currentPoint.setY(y);
				currQN = qt.getCurrentNode(currentPoint);
				double dist = Math.sqrt(Math.pow(x - viewer.route.get(i).getEnd().getX(), 2)
						+ Math.pow(y - viewer.route.get(i).getEnd().getY(), 2));
				if (dist <= 0.20) {
					break;
				}
				viewer.visualize(10);
			}
		}
	}

	/**
	 * The main function
	 * 
	 * @param args arguments of the program
	 */
	public static void main(String args[]) {
		JFrame frame = new JFrame("QuadTree");
		viewer = new QuadTreeViewer(100, 100, 0);

		final JTextField sizeT = new JTextField(10);
		sizeT.setBounds(5, 1, 5, 5);
		JButton setSizeButton = new JButton("Set Point Size");
		setSizeButton.setBounds(5, 1, 5, 5);
		// add size listener
		setSizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userDefinedSize = Integer.parseInt(sizeT.getText());
				if (userDefinedSize != 0) {
					viewer = new QuadTreeViewer(100, 100, userDefinedSize);
					viewer.getPath("", "");
					frame.getContentPane().add(viewer);
					viewer.viewQuadTree();

				}else {
					viewer = new QuadTreeViewer(100, 100, 0);
					viewer.qt.importRealPoints();
					viewer.qt.importRealEdges();
					viewer.myGraph.importPoints(viewer.qt.getPoints());
					viewer.myGraph.setEdges(viewer.qt.getEdges());
					frame.getContentPane().add(viewer);
					viewer.viewQuadTree();

				}

			}
		});

		viewer.add(setSizeButton);
		viewer.add(sizeT);

		final JTextField sourceT = new JTextField(10);
		sourceT.setBounds(5, 1, 5, 5);
		JButton setSrcButton = new JButton("Set Source");
		setSrcButton.setBounds(50, 1, 5, 5);
		// add src listener
		setSrcButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userDefinedSrc = sourceT.getText();


			}
		});
		viewer.add(setSrcButton);
		viewer.add(sourceT);

		final JTextField desT = new JTextField(10);
		desT.setBounds(5, 1, 5, 5);
		JButton setDesButton = new JButton("Set Destination");
		setDesButton.setBounds(100, 1, 5, 5);
		// add des listener
		setDesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userDefinedDes = desT.getText();
				if (userDefinedSize != 0) {
					viewer.generateEdges();
					viewer.getPath(userDefinedSrc, userDefinedDes);
					Runnable runnable = () -> {
						viewer.walk(0.08);
					};
					Thread t = new Thread(runnable);
					t.start();
				}else {
					viewer.myGraph.updateAdjMatrix();
					viewer.getPath(userDefinedSrc, userDefinedDes);
					Runnable runnable = () -> {
						viewer.walk(0.08);
					};
					Thread t = new Thread(runnable);
					t.start();
				}
			}
		});
		viewer.add(setDesButton);
		viewer.add(desT);

		frame.getContentPane().add(viewer);
		frame.setSize(viewer.getSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * initialize the quadtree and graph object
	 * 
	 * @param maxX horizontal length of the window
	 * @param maxY vertical length of the window
	 * @param num  the number of points to generate
	 */
	public QuadTreeViewer(double maxX, double maxY, int num) {
		route = new ArrayList<>();
		qt = new QuadTree(0, 0, maxX, maxY);

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
		visualize(1);
	}

	/**
	 * repaint the quadtree
	 * 
	 * @param p         The current point
	 * @param pauseTime The number of milliseconds to pause
	 */
	public synchronized void visualize(int pauseTime) {
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
		// System.out.println("painting");
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		// set the stroke
		g2D.setStroke(st);
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
		g.setFont(new Font("TimesRoman", 1, 13)); 

		for (Point p : myGraph.getNodes()) {
			double x = border + size * p.getX();
			double y = border + size * p.getY();
			Shape point = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
			g2D.draw(point);
			int tmp;
			String label="";
			if(p.getValue() instanceof Integer) {
				tmp = (int) p.getValue();
				label = String.valueOf(tmp);
			}else if(p.getValue() instanceof String) {
				label=(String)p.getValue();
			}
			float xx = (float) p.getX();
			float yy = (float) p.getY();

			g2D.drawString(label, (float) x + radius, (float) y + radius);
			g2D.draw(point);
		}
		// draw the edges
		List<Edge> edges = myGraph.getEdges();
		g2D.setColor(Color.BLUE);
		for (Edge e : edges) {
		//	System.out.println(e.getStart().getValue());
			double x1 = border + size * e.getStart().getX();
			double y1 = border + size * e.getStart().getY();
		//	System.out.println(e.getEnd().getValue());

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
		if(myGraph.getNodes().size()==0)  return;

		
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

		// draw the current point
		if (currentPoint != null) {
			double x3 = border + size * currentPoint.getX();
			double y3 = border + size * currentPoint.getY();
			Shape curr = new Ellipse2D.Double(x3 - 2 * radius, y3 - 2 * radius, 4 * radius, 4 * radius);
			g2D.draw(curr);
		}

		// draw the current quadnode
		if (currQN != null) {
			g2D.setColor(Color.RED);

			double minX = border + size * currQN.getMinX();
			double minY = border + size * currQN.getMinY();
			double maxX = border + size * currQN.getMaxX();
			double maxY = border + size * currQN.getMaxY();
			Shape l1 = new Line2D.Double(minX, minY, minX, maxY);
			g2D.draw(l1);
			Shape l2 = new Line2D.Double(maxX, minY, maxX, maxY);
			g2D.draw(l2);
			Shape l3 = new Line2D.Double(minX, minY, maxX, minY);
			g2D.draw(l3);
			Shape l4 = new Line2D.Double(minX, maxY, maxX, maxY);
			g2D.draw(l4);
		}
		

	}

//	@Override
//	public List<Edge> getPath(int s, int e) {
//
//		List<Point> list = this.myGraph.nodes;
//
//		for (int i = 0; i < list.size(); i++) {
//			if ((int) list.get(i).value == s) {
//				this.src = i;
//			}
//			if ((int) list.get(i).value == e) {
//				this.dest = i;
//			}
//		}
//		this.route = this.myGraph.dijkstra(src, dest);
//		return route;
//
//	}
	@Override
	public List<Edge> getPath(String s, String e) {

		List<Point> list = this.myGraph.nodes;

		for (int i = 0; i < list.size(); i++) {
			if (((String) (list.get(i).value)).equals(s)) {
				this.src = i;
			}
			if (((String) (list.get(i).value)).equals(e)) {
				this.dest = i;
			}
		}
		this.route = this.myGraph.dijkstra(src, dest);
		return route;

	}

	@Override
	public void generateEdges() {
		myGraph.generateEdges();

	}

}
