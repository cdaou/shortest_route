import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class DijkstraShortestPath {
	
	/**
	 * @author Christos Daoulas
	 */
	public void computeShortestPaths(Node sourceNode){
		sourceNode.setDistance(0);
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(sourceNode);
		sourceNode.setVisited(true);
		Node actualNode, currentNode;
		double newDistance = Double.MAX_VALUE;
		
		while( !priorityQueue.isEmpty() ){
			// Getting the minimum distance Node from priority queue
			actualNode = priorityQueue.poll();
			for(Edge edge : actualNode.getAdjacenciesList()){
				currentNode = edge.getDestinationNode();
				if(!currentNode.isVisited()){
					newDistance = actualNode.getDistance() + edge.getWeight();
					if( newDistance < currentNode.getDistance() ){
						priorityQueue.remove(currentNode);
						currentNode.setDistance(newDistance);
						currentNode.setPrevious(actualNode);
						priorityQueue.add(currentNode);
					}
				}
			}
			actualNode.setVisited(true);
		}
	}
	
	public ArrayList<Node> getShortestPathTo(Node targetNode){
		ArrayList<Node> path = new ArrayList<>();
		for(Node node=targetNode;node!=null;node=node.getPrevious()){
			path.add(node);
		}
		Collections.reverse(path);
		return path;
	}
	
}

	