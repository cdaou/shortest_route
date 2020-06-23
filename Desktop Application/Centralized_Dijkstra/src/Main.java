import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFrame;

public class Main {
	
	/**
	 * @author Christos Daoulas
	 */
	public static void main(String[] args) {
		final int radius = 12; //Change the radius of the nodes
		String connectionsFilePath = "./Connections.txt";
		String coordinatesFilePath = "./Coordinates.txt";
		
		final JFrame window = new JFrame();
		window.setExtendedState(Frame.MAXIMIZED_BOTH);
		window.setTitle("Dijkstra's Shortest Path");
		window.setVisible(true);
		final int frameX = window.getWidth();
		final int frameY = window.getHeight();
		
		importData.setCoonectionsFilePath(connectionsFilePath);
		importData.setCoordinatesFilePath(coordinatesFilePath);
		importData.setFrameY(frameY);
		drawPanels.setFrameX(frameX);
		drawPanels.setFrameY(frameY);
		drawPanels.setRadius(radius);
		drawGraph.setRadius(radius);
		window.setVisible(false);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				final drawPanels frame = new drawPanels();
				frame.setTitle("Dijkstra Shortest Path");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
				
			}
		});
	}
}
