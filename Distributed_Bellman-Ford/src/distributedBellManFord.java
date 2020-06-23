import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class distributedBellManFord {
	
	/**
	 * @author Christos Daoulas
	 */
	private Map<String, Node> nodeList = new HashMap<String, Node>();
	
	public void computeShortestPathsTo(int index, Node destinationNode){
		long startTime = System.nanoTime();
		long duration, endTime;
		destinationNode.setDistance(index,0);
		lineGraph.setTitle("B");
		int counter = 1;
		
		do {
			nodeList.values().parallelStream().forEach((node) -> {
				double newDistance = Double.MAX_VALUE;
				node.setNext(index, null); //in case node has no path to every destination node
				if(!node.getAdjacenciesList().isEmpty()) {
					for(Edge edge : node.getAdjacenciesList()) {
						if(edge.getDestinationNode().getDistance(index) < Double.MAX_VALUE) {
							newDistance =edge.getDestinationNode().getDistance(index) + edge.getWeight();
							if(newDistance <= node.getDistance(index)) {
								node.setDistance(index, newDistance);
								node.setNext(index, edge.getDestinationNode());
							}
						}
					}
				}
				
			});
			
			if(counter < 11) {
				if(nodeList.get("B").getDistance(index) < Double.MAX_VALUE)
					lineGraph.addPoint(index, counter++, nodeList.get("B").getDistance(index) );
				else
					lineGraph.addPoint(index, counter++, 50 );
			}
			endTime = System.nanoTime();
			duration = (endTime - startTime);
		}while(duration<100000000);
	}
	
	public ArrayList<Node> getShortestPathFrom(int index, Node sourceNode){
		ArrayList<Node> path = new ArrayList<>();
		for(Node node=sourceNode;node!=null;node=node.getNext(index)){
			path.add(node);
		}
		return path;
	}
	
	public void setNodeList(Map<String, Node> nodeList){
		this.nodeList = nodeList;
	}
}