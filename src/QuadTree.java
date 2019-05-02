import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**
 * QuadTree contains QuadNodes and associated Points
 * @author shengfeng
 *
 */
public class QuadTree implements IQuadTree {
	
	private QuadNode root;
	private List<Point<String>> points;
	private List<Edge> edges;
	private HashMap<String,Point<String>> labelToPoint;
	
	public QuadTree(double minX,double minY, double maxX, double maxY) {
		this.root=new QuadNode(minX,minY,maxX,maxY);
		this.edges=new ArrayList<>();
		this.labelToPoint=new HashMap<>();
	}
	


	@Override
	public QuadNode getNode(Point p) {
		try {
			return this.getNode(this.root, p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public QuadNode getNode(QuadNode qn,Point<Integer> p) throws Exception {
		if(p.getX()>qn.getMaxX()||p.getX()<qn.getMinX()||p.getY()>qn.getMaxY()||p.getY()<qn.getMinY()) {
			throw new Exception("Out of bound");

		}
		//first case, empty leaf
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()==null) {
			throw new Exception("Point not found");

		}
		
		//second case, non-empty leaf, split the quadrant and reinsert
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()!=null) {
			if(qn.getP().getX()==p.getX()&&qn.getP().getY()==p.getY()) {
				return qn;
			}else {
				throw new Exception("Point not found");
			}

		}
		
		//third case, non-leaf, go to its corresponding child
		if(qn.getSe()!=null||qn.getSw()!=null||qn.getNe()!=null||qn.getNw()!=null) {
			return this.getNode(this.selectQuad(qn, p), p);
		}
		return null;
	}
	public QuadNode getCurrentNode(Point p) {
		try {
			return this.getCurrentNode(this.root, p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public QuadNode getCurrentNode(QuadNode qn,Point<Integer> p) throws Exception {
		if(p.getX()>qn.getMaxX()||p.getX()<qn.getMinX()||p.getY()>qn.getMaxY()||p.getY()<qn.getMinY()) {
			throw new Exception("Out of bound");
		}
		
		//first case, empty leaf
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()==null) {
			return qn;
		}
		
		//second case, non-empty leaf, split the quadrant and reinsert
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()!=null) {
			return qn;
		}
		
		//third case, non-leaf, go to its corresponding child
		if(qn.getSe()!=null||qn.getSw()!=null||qn.getNe()!=null||qn.getNw()!=null) {
			return this.getCurrentNode(this.selectQuad(qn, p), p);
		}
		return null;
	}
	

	@Override
	public boolean contains(Point p) {
		return !(getNode(p)==null);
	}

	@Override
	public void clear() {
		this.root.setNe(null);
		this.root.setNw(null);
		this.root.setSe(null);
		this.root.setSw(null);
	}

	public boolean insert(QuadNode qn, Point<Integer> p) {
		if(p.getX()>qn.getMaxX()||p.getX()<qn.getMinX()||p.getY()>qn.getMaxY()||p.getY()<qn.getMinY()) {
			System.out.println("error inserting point, out of bound");
			return false;
		}
		
		//first case, empty leaf
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()==null) {
			qn.setP(p);
			return true;
		}
		
		//second case, non-empty leaf, split the quadrant and reinsert
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()!=null) {
			if(qn.getP().getX()==p.getX()&&qn.getP().getY()==p.getY()) {
				System.out.println("Duplicate points");
				return false;
			}
			this.split(qn);
			this.insert(qn, p);
			return true;
		}
		
		//third case, non-leaf, go to its corresponding child
		if(qn.getSe()!=null||qn.getSw()!=null||qn.getNe()!=null||qn.getNw()!=null) {
			return this.insert(this.selectQuad(qn, p), p);
		}
		return false;
	}
	@Override
	public boolean insert(Point p) {
	//	System.out.println("inserting point "+p.getX()+ "  "+p.getY());
		return this.insert(this.root,p);
	}

	@Override
	public QuadNode selectQuad(QuadNode qn, Point p) {
		
		// get the sub node of point p
		double midX=(qn.getMinX()+qn.getMaxX())/2;
		double midY=(qn.getMinY()+qn.getMaxY())/2;
		if(p.getX()<midX) {
			if(p.getY()<midY) {
				return qn.getNw();
			}else {
				return qn.getSw();
			}
		}else {
			if(p.getY()<midY) {
				return qn.getNe();
			}else {
				return qn.getSe();
			}
		}
	}

	@Override
	public void split(QuadNode qn) {
		
		//first copy the content p of original node
		Point<Integer> p=qn.getP();
		double midX=(qn.getMinX()+qn.getMaxX())/2;
		double midY=(qn.getMinY()+qn.getMaxY())/2;
		qn.setNw(new QuadNode(qn.getMinX(),qn.getMinY(),midX,midY));
		qn.setNe(new QuadNode(midX,qn.getMinY(),qn.getMaxX(),midY));
		qn.setSw(new QuadNode(qn.getMinX(),midY,midX,qn.getMaxY()));
		qn.setSe(new QuadNode(midX,midY,qn.getMaxX(),qn.getMaxY()));
		this.insert(qn, p);
		qn.setP(null);
		
	}
	
	/**
	 * generate a quadtree by inserting random points
	 * @param n the number of points to generate
	 */
	public void generateQuadTree(int n) {
		Random r=new Random();
		for(int i=0;i<n;i++) {
			double x=r.nextDouble()*(this.root.getMaxX()-this.root.getMinX())+this.root.getMinX();
			double y=r.nextDouble()*(this.root.getMaxY()-this.root.getMinY())+this.root.getMinY();

			this.insert(new Point<String>(x,y, Integer.toString(i)));

		}
	}
	
	/**
	 * import station name, lat and long
	 */
	public void importRealPoints() {
		List<Point<String>> pList=new ArrayList<>();
		//top ten city from philadelphia
		pList.add(new Point<String>(40.750580,-73.993584,"New York"));
		pList.add(new Point<String>(39.292970,-79.345040,"Washington"));
		pList.add(new Point<String>(40.065030,-77.406750,"Lancaster"));
		pList.add(new Point<String>(40.295850,-76.704490,"Harrisburg"));
		pList.add(new Point<String>(39.341480,-76.403610,"Baltimore"));
		pList.add(new Point<String>(40.7344,-74.1642,"Newark"));
		pList.add(new Point<String>(40.043540,-75.479310,"Paoli"));
	//	pList.add(new Point<String>(39.1774,-76.6684,"BWI Airport"));
		pList.add(new Point<String>(40.032970,-75.631540,"Exton"));
	//	pList.add(new Point<String>(40.731510,-74.174390,"Newark-EWR"));
		//top ten city from new york
		pList.add(new Point<String>(40.000700,-75.272150,"Philadelphia"));
		pList.add(new Point<String>(42.292210,-71.053880,"Boston"));
		pList.add(new Point<String>(42.6411,-73.7413,"Albany-Rennsselaer"));
		pList.add(new Point<String>(42.361230,-71.067800,"Back Bay"));
		pList.add(new Point<String>(42.124710,-71.182990,"Route 128"));
		pList.add(new Point<String>(38.895150,-77.216630,"Providence"));
		pList.add(new Point<String>(39.820690,-75.466360,"Wilmington"));
		//top ten city from washington
		pList.add(new Point<String>(40.073040,-74.724320,"Metropark"));
		pList.add(new Point<String>(40.272030,-74.684920,"Trenton"));
		pList.add(new Point<String>(36.927690,-82.194840,"Richmond"));
		pList.add(new Point<String>(41.053429,-73.538734,"Stamford"));
		
		this.convertToCoordinates(pList);
		for(Point<String> p:pList) {
			this.insert(p);
		//	System.out.println(p.getX()+" "+p.getY());
		}
		for(Point<String> p:pList) {
			this.labelToPoint.put(p.getValue(), p);
		}
		this.points=pList;

	}
	/**
	 * convert long and lat to x and y coordinate
	 * @param pList
	 */
	public void convertToCoordinates(List<Point<String>> pList) {
		double minLat=999;
		double maxLat=-999;
		double minLong=999;
		double maxLong=-999;
		for(Point<String> p:pList) {
			minLat=Math.min(minLat, p.getX());
			maxLat=Math.max(maxLat, p.getX());
			minLong=Math.min(minLong, p.getY());
			maxLong=Math.max(maxLong, p.getY());
		}
		//since the area is small, use approximation to create x and y on a 2d plane
		double radius=3958.8;
		double minY=minLat*Math.PI/180*radius;
		double maxY=maxLat*Math.PI/180*radius;
		double minX=minLong*Math.PI/180*radius;
		double maxX=maxLong*Math.PI/180*radius;
		
		//scale the points on a 100*100 map
		for(Point<String> p:pList) {
			double x=0.5+(p.getY()*Math.PI/180*radius-minX)*99/(maxX-minX);
			double y=0.5+(p.getX()*Math.PI/180*radius-minY)*99/(maxY-minY);
			p.setX(x);
			p.setY(100-y);
		}
	}
	
	public void importRealEdges() {
		List<Edge> edges=new ArrayList<>(); 
		//From philly
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("New York"),91));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Washington"),135));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Lancaster"),68));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Harrisburg"),104));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Baltimore"),95));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Newark"),81));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Paoli"),19));
		edges.add(new Edge(labelToPoint.get("Philadelphia"),labelToPoint.get("Exton"),29));
		//from ny
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Washington"),226));
	//	edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Philadelphia"),91));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Boston"),231));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Albany-Rennsselaer"),142));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Baltimore"),185));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Back Bay"),230));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Route 128"),220));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Providence"),188));
		edges.add(new Edge(labelToPoint.get("New York"),labelToPoint.get("Wilmington"),117));
		//from washington
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("New York"),226));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Philadelphia"),135));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Baltimore"),41));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Newark"),216));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Wilmington"),110));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Metropark"),202));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Trenton"),168));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Richmond"),109));
		edges.add(new Edge(labelToPoint.get("Washington"),labelToPoint.get("Stamford"),262));
		
		List<Edge> reverse=new ArrayList<>(); 
		for(Edge e:edges) {
			reverse.add(new Edge(e.getEnd(),e.getStart(),e.getWeight()));
		}
		edges.addAll(reverse);
		this.edges=edges;

	}
	/**
	 * get all the quadnodes within the quadtree
	 * @return a list of all the quadnodes
	 */
	public List<QuadNode> getQuadNodes(){
		List<QuadNode> res=new ArrayList<>();
		getQuadNodesHelper(res,this.root);
		return res;		
	}
	
	public void getQuadNodesHelper(List<QuadNode> l,QuadNode qn) {
		if(qn==null) {
			return;
		}
		l.add(qn);
		
		// check the subnode of qn
		getQuadNodesHelper(l,qn.getNw());
		getQuadNodesHelper(l,qn.getNe());
		getQuadNodesHelper(l,qn.getSw());
		getQuadNodesHelper(l,qn.getSe());
	}
	/**
	 * get all the points within the quadtree
	 * @return a list of all the points
	 */
	public List<Point> getPoints(){
		List<Point> res=new ArrayList<>();
		getPointsHelper(res,this.root);
		return res;	
	}
	
	public void getPointsHelper(List<Point> l,QuadNode qn) {
		if(qn==null) {
			return;
		}
		
		// check the point of subnode of qn
		if(qn.getP()!=null)l.add(qn.getP());
		getPointsHelper(l,qn.getNw());
		getPointsHelper(l,qn.getNe());
		getPointsHelper(l,qn.getSw());
		getPointsHelper(l,qn.getSe());
	}

	public QuadNode getRoot() {
		return root;
	}

	public void setRoot(QuadNode root) {
		this.root = root;
	}

	@Override
	public int size() {
		return this.getQuadNodes().size();
	}



	public List<Edge> getEdges() {
		return edges;
	}



	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}



	public HashMap<String, Point<String>> getLabelToPoint() {
		return labelToPoint;
	}



	public void setLabelToPoint(HashMap<String, Point<String>> labelToPoint) {
		this.labelToPoint = labelToPoint;
	}



	public void setPoints(List<Point<String>> points) {
		this.points = points;
	}

}
