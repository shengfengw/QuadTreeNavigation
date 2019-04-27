import java.util.List;

public interface IUserInterface {
	/**
	 * return the path from the point with index s to the point with index e
	 * @param s the index of the start point
	 * @param e the index of the end point
	 * @return the list of edges in the path
	 */
	public List<Edge> getPath(int s,int e);
	public void generateEdges();
}
