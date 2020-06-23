import java.util.ArrayList;
 
public class Node implements Comparable<Node> {
	
	/**
	 * @author Christos Daoulas
	 */
	private String name;
	private int nodeX, nodeY;
	private ArrayList<Edge> adjacenciesList;
	private boolean visited = false;
	private Node previous = null;
	private double distance = Double.MAX_VALUE;
	
	public Node() {
		this.name = "";
		this.adjacenciesList = new ArrayList<>();
		nodeX = 0;
		nodeY = 0;
	}
		
	public Node(String name, int nodeX, int nodeY) {
		this.name = name;
		this.adjacenciesList = new ArrayList<>();
		this.nodeX = nodeX;
		this.nodeY = nodeY;
	
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
 
	public boolean isVisited() {
		return this.visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public Node getPrevious() {
		return this.previous;
	}
	
	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	public double getDistance() {
		return this.distance;
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