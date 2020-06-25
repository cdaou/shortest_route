package org.Centralized_Dijkstra.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DijkstraShortestPath {
	
	/**
	 * @author Christos Daoulas
	 */
	
	HashMap<Integer,Node> nodes;
	
	public void computeShortestPaths(Integer sourceID){
		nodes.get(sourceID).setDistance(0);
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(nodes.get(sourceID));
		nodes.get(sourceID).setVisited(true);
		Node actualNode, currentNode;
		double newDistance = Double.MAX_VALUE;
		
		while( !priorityQueue.isEmpty() ){
			// Getting the minimum distance Node from priority queue
			actualNode = priorityQueue.poll();
			for(Edge edge : actualNode.getAdjacenciesList()){
				currentNode = nodes.get(edge.getDestinationID());
				if(!nodes.get(edge.getDestinationID()).isVisited()){
					newDistance = actualNode.getDistance() + edge.getWeight();
					if( newDistance < currentNode.getDistance() ){
						priorityQueue.remove(currentNode);
						nodes.get(edge.getDestinationID()).setDistance(newDistance);
						nodes.get(edge.getDestinationID()).setPrevious(actualNode.getID());
						priorityQueue.add(nodes.get(edge.getDestinationID()));
					}
				}
			}
			actualNode.setVisited(true);
		}
	}
	
	public ArrayList<Integer> getShortestPathTo(Node targetNode){
		ArrayList<Integer> path = new ArrayList<>();
		for(Node node=targetNode;node!=null;node=nodes.get(node.getPrevious())){
			path.add(node.getID());
		}
		Collections.reverse(path);
		return path;
	}
	
	public void setNodes(HashMap<Integer,Node> nodes ) {
		this.nodes=nodes;
	}
	
}