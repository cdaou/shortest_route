package org.Thesis_webpage_BF.run;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	
	/**
	 * @author Christos Daoulas
	 */
	private int ID;
	private String name;
	private double latitude, longitude;
	private ArrayList<Integer> next = new ArrayList<Integer>();
	private ArrayList<Double> distance;
	private Edge[] Edge;
	
	public Node() {
		this.name = "";
		this.distance = new ArrayList<Double>();
	}
		
	public Node(String name, int latitude, int longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.next = new ArrayList<>();
		this.distance = new ArrayList<Double>();
	}
	
	public double getLat(){
		return this.latitude;
	}
	
	public double getLong(){
		return this.longitude;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	 
	public Edge[] getAdjacenciesList(){
		return this.Edge;
	}
	
	public Integer getNext(int index) {
		if(index >= next.size())
			next.add(null);
		return this.next.get(index);
	}
	
	public void setNext(int index, Integer next) {
		if(index < this.next.size())
			this.next.set(index, next);
		else {
			this.next.add(next);
		}	
	}
	
	public double getDistance(int index) {
		if(index >= distance.size())
			distance.add(Double.MAX_VALUE);
		return this.distance.get(index);
	}
	
	public void setDistance(int index, double distance) {
		if(index < this.distance.size())
			this.distance.set(index, distance);
		else {
			this.distance.add(distance);
		}
			
	}
	
	public void clearDistanceNext() {
		this.next = new ArrayList<>();
		this.distance = new ArrayList<Double>();
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public int compareTo(Node otherNode) {
		return Double.compare(this.distance.get(0), otherNode.getDistance(0));
	}
}