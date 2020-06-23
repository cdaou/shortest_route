import java.util.ArrayList;
import javax.swing.JFrame;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series; 
 
public class lineGraph{
	
	//defining a series
	@SuppressWarnings("rawtypes")
	private static ArrayList<Series> series = new ArrayList<Series>();
	private static String title;
	private static JFrame frame = new JFrame();
	static JFXPanel fxPanel = new JFXPanel();

	public static void initAndShowGUI() {
		// This method is invoked on the EDT thread
		frame.setVisible(false);
		frame = new JFrame();
		frame.setTitle("Shortest Path");
		fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.revalidate();
		frame.repaint();
		frame.setSize(800, 600);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});
		
	}

	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Iterations");
		xAxis.setMinorTickCount(0);
		yAxis.setLabel("Distance");
		//creating the chart
		final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.setTitle("Iterations until the shortest path from the node " +title+" is stabilized");
		lineChart.setCreateSymbols(true);
		
		for(int i=0; i<series.size();i++) {
			lineChart.getData().add(series.get(i));
		}
		Scene scene  = new Scene(lineChart,800,600);
		fxPanel.setScene(scene);
	}
	
	public static void addPoint(int index, int x, double y) {
		series.get(index).getData().add(new XYChart.Data<Integer,Double>( x, y));
	}
	
	public static void addDestination(String destination) {
		series.add(new Series());
		series.get(series.size()-1).setName(destination);
	}
	
	public static void setTitle(String _title) {
		title = _title;
	}
	
	public static void clearAll() {
		series.clear();
	}
}