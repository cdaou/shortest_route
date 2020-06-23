public class Edge {
	
	/**
	 * @author Christos Daoulas
	 */
	private double weight;
	private Node sourceNode;
	private Node destinationNode;
	
	public Edge() {
		this.weight = 0;
		this.sourceNode = new Node();
		this.destinationNode = new Node();
	}
		
	public Edge(Node sourceNode, Node destinationNode, double weight) {
		this.weight = weight;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
	}
		
	public double getWeight() {
		return this.weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public Node getSourceNode() {
		return this.sourceNode;
	}
	
	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}
	
	public Node getDestinationNode() {
		return this.destinationNode;
	}
	
	public void setDestinationNode(Node destinationNode) {
		this.destinationNode = destinationNode;
	}
	
}