import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * QuadTree contains QuadNodes and associated Points
 * @author shengfeng
 *
 */
public class QuadTree implements IQuadTree {
	private QuadNode root;
	private QuadTreeViewer viewer;
	
	public QuadTree(double minX,double minY, double maxX, double maxY) {
		this.root=new QuadNode(minX,minY,maxX,maxY);
	}
	


	@Override
	public QuadNode getNode(Point p) {
		return this.getNode(this.root, p);
	}
	
	public QuadNode getNode(QuadNode qn,Point<Integer> p) {
		if(p.getX()>qn.getMaxX()||p.getX()<qn.getMinX()||p.getY()>qn.getMaxY()||p.getY()<qn.getMinY()) {
			System.out.println("error getting point, out of bound");
			return null;
		}
		//first case, empty leaf
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()==null) {
			System.out.println("point not found");
			return null;
		}
		//second case, non-empty leaf, split the quadrant and reinsert
		if(qn.getSe()==null&&qn.getSw()==null&&qn.getNe()==null&&qn.getNw()==null&&qn.getP()!=null) {
			if(qn.getP().getX()==p.getX()&&qn.getP().getY()==p.getY()) {
				System.out.println("found point");
				return qn;
			}else {
				System.out.println("point not found");
				return null;
			}

		}
		//third case, non-leaf, go to its corresponding child
		if(qn.getSe()!=null||qn.getSw()!=null||qn.getNe()!=null||qn.getNw()!=null) {
			return this.getNode(this.selectQuad(qn, p), p);
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
		// TODO Auto-generated method stub
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
	//	System.out.println("splitting");
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
			this.insert(new Point(x,y));
		}
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



	public QuadTreeViewer getViewer() {
		return viewer;
	}



	public void setViewer(QuadTreeViewer viewer) {
		this.viewer = viewer;
	}



	@Override
	public int size() {
		return this.getQuadNodes().size();
	}

}
