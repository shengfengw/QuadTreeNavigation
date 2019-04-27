
public interface IQuadTree {

	/**
	 * get the QuadNode containing Point p, return null if not found
	 * @param p the point to find
	 * @return the QuadNode containing p
	 */
	public QuadNode getNode(Point p);
	/**
	 * check if the QuadTree contains Point p
	 * @param p the point to check
	 * @return if the Quadtree contains Point p
	 */
	public boolean contains(Point p);
	/**
	 * remove all nodes within QuadTree
	 */
	public void clear();
	/**
	 * insert Point p into the QuadTree
	 * @param p the point to insert
	 * @return if the insert was successful
	 */
	public boolean insert(Point p);
	/**
	 * Select which quadrant to insert Point p
	 * @param qn the parent node where p should be inserted
	 * @param p the point to insert
	 * @return the children where p should be inserted
	 */
	public QuadNode selectQuad(QuadNode qn,Point p);
	/**
	 * split the node into four quadrants
	 * @param qn the node to split
	 */
	public void split(QuadNode qn);
	/**
	 * get the size of quadtree
	 * @return the size of quadtree
	 */
	public int size() ;
	
}
