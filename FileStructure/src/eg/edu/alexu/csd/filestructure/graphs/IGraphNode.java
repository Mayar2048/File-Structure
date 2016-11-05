package eg.edu.alexu.csd.filestructure.graphs;

import java.awt.Point;
import java.util.ArrayList;

public interface IGraphNode {

	public void setDistance(int dis);

	public int getDistance();

	public void setPredecessor(IGraphNode node);

	public IGraphNode getPredecessor();

	public ArrayList<Integer> AdjacentVertices();

	public ArrayList<Point> edges();

	public void addEdge(Point point);

	public int weight(int v);

}
