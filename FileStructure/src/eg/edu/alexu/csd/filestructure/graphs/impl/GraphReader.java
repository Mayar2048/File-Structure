package eg.edu.alexu.csd.filestructure.graphs.impl;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphReader {
	
	private ArrayList<GraphNode> graph;
	private ArrayList<Integer> verList;
	private File file;
	private int verticesNum;
	private int edgesNum;

	public GraphReader(File file) {
		this.graph = new ArrayList<GraphNode>();
		this.verList = new ArrayList<Integer>();
		this.file = file;
	}

	public ArrayList<GraphNode> getContents() {
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null;
				line = input.readLine();
				String[] arr = line.split(" ");
				this.setVerticesNum(arr[0]);
				this.setEdgesNum(arr[1]);
				
				checkLowerBound(verticesNum);
				checkLowerBound(edgesNum);
				
				for(int i = 0; i < verticesNum; i++) {
					graph.add(new GraphNode());
				}
				
				for (int i = 0; i < edgesNum; i++) {
					line = input.readLine();
					String[] edge = line.split(" ");
					int node1 = parseValue(edge[0]);
					int node2 = parseValue(edge[1]);
					int weight = parseValue(edge[2]);
					
					checkUpperBound(node1);
					checkUpperBound(node2);
					
					GraphNode firstNode = graph.get(node1);
					GraphNode secondNode = graph.get(node2);
					firstNode.addEdge(new Point(node2,weight));
					firstNode.addNeighbour(secondNode);
					
					if(!verList.contains(node1)){
						graph.get(node1).setUsed(true);
						verList.add(node1);
					}
					if(!verList.contains(node2)){
						graph.get(node2).setUsed(true);
						verList.add(node2);
					}
				}
				for(int i = 0; i < graph.size(); ){
					if(graph.get(i).getUsed()) {
						i++;
					}else {
						graph.remove(i);
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException();
		}
		return graph;
	}

	private void setVerticesNum(String str) {
		verticesNum = Integer.parseInt(str);
	}

	public int getVerticesNum() {
		return verticesNum;
	}

	private void setEdgesNum(String str) {
		edgesNum = Integer.parseInt(str);
	}

	public int getEdgesNum() {
		return edgesNum;
	}

	private int parseValue(String str) {
		return Integer.parseInt(str);
	}

	public ArrayList<Integer> setOfVertices() {
		return verList;
	}
	
	private boolean checkLowerBound(int num) {
		if(num < 0) {
			throw new RuntimeException();
		}
		return true;
	}
	
	private boolean checkUpperBound(int num) {
		if(num >= verticesNum) {
			throw new RuntimeException();
		}
		return true;
	}
}
