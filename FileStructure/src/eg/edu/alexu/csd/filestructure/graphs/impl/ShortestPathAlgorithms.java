package eg.edu.alexu.csd.filestructure.graphs.impl;

import java.io.File;
import java.util.ArrayList;
import eg.edu.alexu.csd.filestructure.graphs.IGraph;

public class ShortestPathAlgorithms implements IGraph {
	private ArrayList<GraphNode> adjacencyList;
	private ArrayList<Integer> vertexList;
	private ArrayList<Integer> processOrder;
	private GraphReader graphReader;
	private int size;
	public static int infinity = Integer.MAX_VALUE / 2;

	public ShortestPathAlgorithms() {
		this.adjacencyList = new ArrayList<GraphNode>();
		this.processOrder = new ArrayList<Integer>();
		this.vertexList = new ArrayList<Integer>();
		this.size = 0;
	}

	// DONE
	@Override
	public void readGraph(File file) {
		if (file == null) {
			return;
		}
		this.adjacencyList = new ArrayList<GraphNode>();
		this.graphReader = new GraphReader(file);
		this.adjacencyList = graphReader.getContents();
		this.size = graphReader.getEdgesNum();
		this.vertexList = graphReader.setOfVertices();
	}

	// DONE
	@Override
	public int size() {
		return this.size;
	}

	// DONE
	@Override
	public ArrayList<Integer> getVertices() {
		return vertexList;
	}

	// DONE
	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		return adjacencyList.get(v).AdjacentVertices();
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		boolean[] sptSet = new boolean[adjacencyList.size()];
		this.processOrder = new ArrayList<Integer>();
		initializeSingleSource(src);

		for (int counter = 0; counter < adjacencyList.size(); counter++) {
			int u = extractMin(sptSet);
			processOrder.add(u);
			sptSet[u] = true;
			adjacencyList.get(u).dijkstra(distances, sptSet, adjacencyList);
		}
		for (int i = 0; i < distances.length; i++) {
			System.out.println(distances.length + "  -  -  -");
			distances[i] = adjacencyList.get(i).getDistance();
		}
	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		return processOrder;
	}

	// DONE
	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		initializeSingleSource(src);
		for (int i = 1; i < adjacencyList.size() - 1; i++) {
			for (int j = 0; j < adjacencyList.size(); j++) {
				adjacencyList.get(j).relax();
			}
		}
		for (int i = 0; i < distances.length; i++) {
			distances[i] = adjacencyList.get(i).getDistance();
		}
		for (int j = 0; j < adjacencyList.size(); j++) {
			for (int k = 0; k < adjacencyList.get(j).AdjacentVertices().size(); k++) {
				if (!adjacencyList.get(j).bellman()) {
					return false;
				}
			}
		}
		return true;
	}

	public void initializeSingleSource(int s) {
		for (int i = 0; i < adjacencyList.size(); i++) {
			if (i == s) {
				adjacencyList.get(i).setDistance(0);
			} else {
				adjacencyList.get(i).setDistance(infinity);
			}
			adjacencyList.get(i).setPredecessor(null);
		}
	}

	public int extractMin(boolean[] sptSet) {
		int min = Integer.MAX_VALUE / 2;
		int min_index = -1;
		for (int v = 0; v < adjacencyList.size(); v++)
			if (sptSet[v] == false && adjacencyList.get(v).getDistance() <= min) {
				min = adjacencyList.get(v).getDistance();
				min_index = v;
			}
		return min_index;
	}

}
