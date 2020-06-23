import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class importData {
	
	static private String coordinatesFilePath, connectionsFilePath;
	static private int frameY;
	
	private HashMap<String, Node> nodeList = new HashMap<String, Node>();
	private ArrayList<String> destinationList = new ArrayList<>();
	
	public importData(JLayeredPane workingPanel, int margin){
		BufferedReader fileCoordinates = null, fileConnections = null;
		String line, source, destination;
		double weight;
		Node addNode; 
		ArrayList<String> list;
		String[] splitStrings;
		Boolean show = true;
		
		//-----READ COORDINATES AND CONNECTION FILES-----//
		try {	
			//READ COORDINATES FILE
			fileCoordinates = new BufferedReader(new FileReader(coordinatesFilePath));
			line = fileCoordinates.readLine(); //avoid the title
			line = fileCoordinates.readLine();
			while (line != null) {
				splitStrings = line.split(",");
				list = new ArrayList<String>();
				for ( String s : splitStrings )
					list.add(s);
				addNode = new Node(list.get(0), Integer.parseInt(list.get(1)), frameY - Integer.parseInt(list.get(2)));
				if((addNode.getX()>workingPanel.getWidth() || java.lang.Math.abs(addNode.getY()-frameY)>workingPanel.getHeight()+3*margin || java.lang.Math.abs(addNode.getY()-frameY)<3*margin) && show){
					if (JOptionPane.showConfirmDialog(null, "Ôhe coordinates of a node go out of the screen boundaries("+workingPanel.getWidth()+","+workingPanel.getHeight()+"). Would you like to proceed?", "Warning!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
					show = false;
				}
				nodeList.put(list.get(0), addNode);
				if(list.get(3).equals("1"))
					destinationList.add(list.get(0));
				line = fileCoordinates.readLine();
			}
			if(fileCoordinates!=null)
				fileCoordinates.close();
			//READ CONNECTIONS FILE
			fileConnections = new BufferedReader(new FileReader(connectionsFilePath));
			line = fileConnections.readLine(); //agnoise ton titlo
			line = fileConnections.readLine();
			while (line != null) {
				//split and transform string line to numbers
				splitStrings = line.split(",");
				list = new ArrayList<String>();
				for ( String s : splitStrings )
					list.add(s);
				source = list.get(0); //Current edge - source node
				destination = list.get(1);//Current edge - destination node
				weight = Double.parseDouble(list.get(2));//Current edge - weight of edge
				nodeList.get(source).addNeighbour(new Edge(nodeList.get(source), nodeList.get(destination), weight));
				line = fileConnections.readLine();				
			}
			if(fileConnections!=null)
				fileConnections.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(workingPanel, "Input file(s) not found. Try again!", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(workingPanel, "Something went wrong, reading the file(s). Try again!", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		//-----END READING FILES-----//
	}
	
	public static void setCoordinatesFilePath(String _coordinatesFilePath){
		coordinatesFilePath = _coordinatesFilePath;
	}
	
	public static void setCoonectionsFilePath(String _connectionsFilePath){
		connectionsFilePath = _connectionsFilePath;
	}
	
	public static void setFrameY(int _frameY){
		frameY = _frameY;
	}
	
	public HashMap<String, Node> getNodeList(){
		return nodeList;
	}
	
	public ArrayList<String> getDestinationList(){
		return destinationList;
	}
}
