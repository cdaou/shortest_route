package org.BellmanFord.run;

import java.util.ArrayList;
import java.util.HashMap;

public class BellManFordShortestPath {
	
	/**
	 * @author Christos Daoulas
	 */
	private HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
	
	public void computeShortestPathsTo(int index, Node destinationNode){
		long startTime = System.nanoTime();
		long duration, endTime;
		destinationNode.setDistance(index,0);
		
		do {
			nodeList.values().parallelStream().forEach((node) -> {
				
				double newDistance = Double.MAX_VALUE;
				node.setNext(index, null); //in case node has no path to every destination node
				if(node.getAdjacenciesList().length!=0) {
					for(Edge edge : node.getAdjacenciesList()) {
						if(nodeList.get(edge.getDestinationID()).getDistance(index) < Double.MAX_VALUE) {
							newDistance = nodeList.get(edge.getDestinationID()).getDistance(index) + edge.getWeight();
							if(newDistance <= node.getDistance(index)) {
								node.setDistance(index, newDistance);
								node.setNext(index, nodeList.get(edge.getDestinationID()).getID());
							}
						}
					}
				}
				
			});
			
			endTime = System.nanoTime();
			duration = (endTime - startTime);
		}while(duration<100000000);
	}
	
	public ArrayList<Integer> getShortestPathFrom(int index, Node sourceNode){
		ArrayList<Integer> path = new ArrayList<>();
		for(Node node=sourceNode;node!=null;node=nodeList.get(node.getNext(index))){
			path.add(node.getID());
		}
		return path;
	}
	
	public void setNodeList(HashMap<Integer, Node> nodeList){
		this.nodeList = nodeList;
	}
}