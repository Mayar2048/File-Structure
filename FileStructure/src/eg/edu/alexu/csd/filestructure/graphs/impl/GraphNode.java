package eg.edu.alexu.csd.filestructure.graphs.impl;

import java.awt.Point;
import java.util.ArrayList;

import eg.edu.alexu.csd.filestructure.graphs.IGraphNode;

public class GraphNode implements IGraphNode {
	private ArrayList<Point> list;
	private ArrayList<Integer> adjacentVertices;
	private ArrayList<GraphNode> neighbours;
	private int distance;
	private boolean used;
	
	public boolean getUsed() {
		return used;
	}

	public void setUsed(boolean flag) {
		this.used = flag;
	}

	private IGraphNode predecessor;

	public GraphNode() {
		this.list = new ArrayList<Point>();
		this.adjacentVertices = new ArrayList<Integer>();
		this.neighbours = new ArrayList<GraphNode>();
		this.distance = ShortestPathAlgorithms.infinity;
		this.predecessor = null;
	}

	@Override
	public void setDistance(int dis) {
		this.distance = dis;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public IGraphNode getPredecessor() {
		return this.predecessor;
	}

	@Override
	public void setPredecessor(IGraphNode node) {
		this.predecessor = node;
	}

	@Override
	public ArrayList<Integer> AdjacentVertices() {
		return adjacentVertices;
	}

	@Override
	public ArrayList<Point> edges() {
		return list;
	}

	@Override
	public void addEdge(Point point) {
		this.adjacentVertices.add((int) point.getX());
		this.list.add(point);
	}

	@Override
	public int weight(int v) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getX() == v) {
				return (int) list.get(i).getY();
			}
		}
		return 0;
	}
	
	public ArrayList<GraphNode> nodeNeighbours(){
		return neighbours;
	}
	
	public void addNeighbour(GraphNode node) {
		neighbours.add(node);
	}
	
	public void relax() {
		for(int i = 0; i < neighbours.size(); i++) {
			int dis = neighbours.get(i).getDistance();
			int weight = (int) edges().get(i).getY();
			if(dis > this.distance + weight) {
				neighbours.get(i).setDistance(this.distance + weight);
				neighbours.get(i).setPredecessor(this);
			}
		}
	}
	
	public boolean bellman() {
		for(int i = 0; i < neighbours.size(); i++) {
			int dis = neighbours.get(i).getDistance();
			int weight = (int) edges().get(i).getY();
			if(dis > this.distance + weight) {
				return false;
			}
		}
		return true;
	}
	
	public void dijkstra(int[] distances, boolean[] sptSet ,ArrayList<GraphNode> list) {
		for(int i = 0; i < neighbours.size(); i++) {
			int dis = neighbours.get(i).getDistance();
			int weight = (int) edges().get(i).getY();
			int neighbourIndex = list.indexOf(neighbours.get(i));
			if(dis > this.distance + weight && !sptSet[neighbourIndex]) {
				neighbours.get(i).setDistance(this.distance + weight);
				neighbours.get(i).setPredecessor(this);
			}
		}
	}
}
