import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

public class drawGraph extends JComponent {
	
	/**
	 * @author Christos Daoulas
	 */
	private static final long serialVersionUID = -1172182746660515606L;
	private static int radius;
	
	private Ellipse2D.Double ellipse;
	private int nodeX, nodeY;
	private boolean drawEdges = true, drawArrows = true;
	private Map<String, Node> nodeList = new HashMap<>();
	private ArrayList<ArrayList<Node>> allPaths = new ArrayList<ArrayList<Node>>();
	private ArrayList<Node> path = new ArrayList<>();
	private ArrayList<String> destinationList = new ArrayList<>();
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		int x, y, adjacenciesX, adjacenciesY;
		List<Edge> adjacenciesList;
		List<Integer> newCoordinates = new ArrayList<Integer>();
		//IF DRAW CHECKBOX IS SELECTED DRAW ARROWS CONNECTING SOURCE TO DESTINATION NODES
		if(drawEdges){
			//DRAW BLACK ARROWS CONNECTING ALL NODES
			for (Map.Entry<String, Node> e : nodeList.entrySet()) {
				x = e.getValue().getX();
				y = e.getValue().getY();
				adjacenciesList = e.getValue().getAdjacenciesList();
				for(Edge edge : adjacenciesList){
					adjacenciesX = edge.getDestinationNode().getX();
					adjacenciesY = edge.getDestinationNode().getY();
					newCoordinates = findTopology(x+radius, y+radius, adjacenciesX+radius, adjacenciesY+radius, 1.5f);
					drawArrows(g2, newCoordinates.get(0), newCoordinates.get(1), newCoordinates.get(2), newCoordinates.get(3), Color.DARK_GRAY, 1);
				}
			}
			//END
			//IS SHORTEST PATH(S) EXISTS, DRAW RED ARROWS CONNECTING ONLY PATH'S NODES
			if(!path.isEmpty()){
				for(int i=0;i<path.size()-1;i++){
					adjacenciesX = path.get(i+1).getX();
					x = path.get(i).getX();
					adjacenciesY = path.get(i+1).getY();
					y = path.get(i).getY();
					newCoordinates = findTopology(x+radius, y+radius, adjacenciesX+radius, adjacenciesY+radius, 1.5f);
					drawArrows(g2, newCoordinates.get(0), newCoordinates.get(1), newCoordinates.get(2), newCoordinates.get(3), Color.RED, 2);
				}
			}
			//END
		}
		//END
		//DRAW CIRCLE FOR EVERY NODE AND ITS NAME ON IT
		for (Map.Entry<String, Node> v : nodeList.entrySet()) {
			nodeX = v.getValue().getX();
			nodeY = v.getValue().getY();
			ellipse = new Ellipse2D.Double(nodeX, nodeY, radius*2, radius*2);
			g2.setColor(Color.BLACK);
			if(destinationList.contains(v.getKey()))
				g2.setColor(Color.ORANGE);
			if(!path.isEmpty() && (path.get(0).equals(v.getValue()) || path.get(path.size()-1).equals(v.getValue())) )
				g2.setColor(Color.RED);
			g2.fill(ellipse);
			g2.draw(ellipse);
			g2.setColor(Color.WHITE);
			if(destinationList.contains(v.getKey()))
				g2.setColor(Color.BLACK);
			if(!path.isEmpty() && (path.get(0).equals(v.getValue()) || path.get(path.size()-1).equals(v.getValue())) )
				g2.setColor(Color.BLACK);
			g2.setFont(new Font("default", Font.BOLD, 16));
			g2.drawString(v.getValue().getName(), nodeX+radius/2, nodeY+ (3*radius)/2);
		}
	}
	
	public ArrayList<Integer> findTopology(int x1, int y1, int x2, int y2, float factor){
		//CALCULATE NEW COORDINATES, BASED ON NODES TOPOLOGY
		ArrayList<Integer> newCoordinates = new ArrayList<Integer>();
		double angle;
		if (x1<x2 && y1>y2){
			angle = Math.atan((float)(y1-y2)/(x2-x1));
			x1 = (int) (x1 +  (Math.cos(angle) * radius * factor) + 0.5d);
			y1 = (int) (y1 - (Math.sin(angle) * radius * factor) + 0.5d);
			x2 = (int) (x2 - (Math.cos(angle) * radius * factor) + 0.5d);
			y2 = (int) (y2 +  (Math.sin(angle) * radius * factor) + 0.5d);	

		}else if (x1<x2 && y1<y2){
			angle = Math.atan((float)(y2-y1)/(x2-x1));
			x1 = (int) (x1 + (Math.cos(angle) * radius * factor) + 0.5d);
			y1 = (int) (y1 + (Math.sin(angle) * radius * factor) + 0.5d);
			x2 = (int) (x2 - (Math.cos(angle) * radius * factor) + 0.5d);
			y2 = (int) (y2 - (Math.sin(angle) * radius * factor) + 0.5d);

		}else if (x1>x2 && y1>y2){
			angle = Math.atan((float)(y1-y2)/(x1-x2));
			x1 = (int) (x1 - (Math.cos(angle) * radius * factor) + 0.5d);
			y1 = (int) (y1 - (Math.sin(angle) * radius * factor) + 0.5d);
			x2 = (int) (x2 + (Math.cos(angle) * radius * factor) + 0.5d);
			y2 = (int) (y2 + (Math.sin(angle) * radius * factor) + 0.5d);

		}else if (x1>x2 && y1<y2){
			angle = Math.atan((float)(y2-y1)/(x1-x2));
			x1 = (int) (x1 - (Math.cos(angle) * radius * factor) + 0.5d);
			y1 = (int) (y1 + (Math.sin(angle) * radius * factor) + 0.5d);
			x2 = (int) (x2 + (Math.cos(angle) * radius * factor) + 0.5d);
			y2 = (int) (y2 - (Math.sin(angle) * radius * factor) + 0.5d);

		}else if (x1==x2){
			if (y1<y2){
				y1 = y1 + (int) (radius * factor);
				y2 = y2 - (int) (radius * factor);
			}else{
				y1 = y1 - (int) (radius * factor);
				y2 = y2 + (int) (radius * factor);
			}
		}else{
			if (x1>x2){
				x1 = x1 - (int) (radius * factor);
				x2 = x2 + (int) (radius * factor);
			}else{
				x1 = x1 + (int) (radius * factor);
				x2 = x2 - (int) (radius * factor);
			}
		}
		newCoordinates.add(x1);
		newCoordinates.add(y1);
		newCoordinates.add(x2);
		newCoordinates.add(y2);
		return newCoordinates;
	}

	public void drawArrows(Graphics2D g, int x1, int y1, int x2, int y2, Color arrowColor, int thickness) {
		//CREATE LINE WITH ARROW, FROM SOURCE NODE POINTING T ODESTINATION NODE
		final int d = radius + thickness;
		final int width = radius/2 + thickness;	
		final int dx = x2 - x1;
		final int dy = y2 - y1;
		final double square_root = Math.sqrt(dx*dx + dy*dy);
		double xm = square_root - d, xn = xm, ym = width, yn = -width, x;
		double sin = dy / square_root, cos = dx / square_root;
		x = xm*cos - ym*sin + x1;
		ym = xm*sin + ym*cos + y1;
		xm = x;
		x = xn*cos - yn*sin + x1;
		yn = xn*sin + yn*cos + y1;
		xn = x;
		int[] xpoints = {x2, (int) xm, (int) xn};
		int[] ypoints = {y2, (int) ym, (int) yn};
		g.setColor(arrowColor);
		g.setStroke(new BasicStroke(thickness));
		g.drawLine(x1, y1, x2, y2);
		if(drawArrows)
			g.fillPolygon(xpoints, ypoints, 3);
	}

	public void addShortestPath(ArrayList<Node> path){
		allPaths.add(path);
	}
	
	public void setPathIndex(int pathIndex) {
		this.path = this.allPaths.get(pathIndex);
	}
	
	public void clearPaths() {
		allPaths.clear();
	}
	
	public static void setRadius(int _radius){
		radius = _radius;
	}
	
	public void setShowEdges(boolean drawEdges){
		this.drawEdges = drawEdges;
	}
	
	public void setShowArrows(boolean drawArrows){
		this.drawArrows = drawArrows;
	}
	
	public void setNodeList(Map<String, Node> nodeList){
		this.nodeList = nodeList;
	}
	
	public void setDestinationList(ArrayList<String> destinationList){
		this.destinationList=destinationList;
	}
}