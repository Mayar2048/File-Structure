package eg.edu.alexu.csd.filestructure.graphs.impl;

import java.io.File;

import eg.edu.alexu.csd.filestructure.graphs.IGraph;

public class Main {

	public static void main(String[] args) {
		IGraph graph = new ShortestPathAlgorithms();
		System.out.println(graph.size());
		System.out.println(graph.getVertices().toString());
		graph.readGraph(new File("safey.txt"));
		int[] distances = new int[graph.getVertices().size()];
		graph.runDijkstra(0, distances);
		System.out.println(graph.size());
		System.out.println("---------------------------");
		for(int i = 0; i < distances.length; i++)
			System.out.println(distances[i] + "   ");
		
	}

}
