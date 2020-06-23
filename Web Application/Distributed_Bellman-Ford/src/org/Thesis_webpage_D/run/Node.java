package org.Thesis_webpage_D.run;

public class Node implements Comparable<Node> {
	private int ID;
	private String name;
	private double latitude, longitude; 
	private int previous = 0;
	private boolean visited = false;
	private double distance = Double.MAX_VALUE;
	private Edge[] Edge;
	
	public double getLat(){
		return this.latitude;
	}
	
	public double getLon(){
		return this.longitude;
	}
	
	public double getDistance(){
		return this.distance;
	}
	
	public boolean isVisited(){
		return this.visited;
	}
	
	public int getPrevious(){
		return this.previous;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Edge[] getAdjacenciesList(){
		return this.Edge;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public void setPrevious(int previous) {
		this.previous = previous;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public int compareTo(Node otherNode) {
		return Double.compare(this.distance, otherNode.getDistance());
	}
}