package org.BellmanFord.run;

public class Edge {
	
	/**
	 * @author Christos Daoulas
	 */
	private double weight;
	private int destination_id;
		
	public double getWeight() {
		return this.weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public int getDestinationID() {
		return this.destination_id;
	}
	
	public void setDestinationID(int destinationNode) {
		this.destination_id = destinationNode;
	}
}