import java.util.ArrayList;
 
public class Node implements Comparable<Node> {
	
	/**
	 * @author Christos Daoulas
	 */
	private String name;
	private int nodeX, nodeY;
	private ArrayList<Edge> adjacenciesList;
	private ArrayList<Node> next ;
	private ArrayList<Double> distance;
	
	public Node() {
		this.name = "";
		this.adjacenciesList = new ArrayList<>();
		this.distance = new ArrayList<Double>();
	}
		
	public Node(String name, int nodeX, int nodeY) {
		this.name = name;
		this.nodeX = nodeX;
		this.nodeY = nodeY;
		this.adjacenciesList = new ArrayList<>();
		this.next = new ArrayList<>();
		this.distance = new ArrayList<Double>();
	}
	
	public int getX(){
		return this.nodeX;
	}
	
	public int getY(){
		return this.nodeY;
	}
	
	public void addNeighbour(Edge edge) {
		this.adjacenciesList.add(edge);
	}
	
	public String getName() {
		return this.name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	 
	public ArrayList<Edge> getAdjacenciesList() {
		return this.adjacenciesList;
	}
	
	public void setAdjacenciesList(ArrayList<Edge> adjacenciesList) {
		this.adjacenciesList = adjacenciesList;
	}
	
	public Node getNext(int index) {
		if(index >= next.size())
			next.add(null);
		return this.next.get(index);
	}
	
	public void setNext(int index, Node next) {
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