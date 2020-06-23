package org.Centralized_Dijkstra.run;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 * Servlet implementation class Thesis_servlet
 */
@WebServlet("/SP_servlet")
public class SP_servlet extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;

    /**
     * Default constructor. 
     */
    public SP_servlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int source= Integer.parseInt(request.getParameter("source"));
		Integer[] destinations = {1023, 1013, 1028};	
		HashMap<Integer,Node> nodes = importData();
		long startTime = System.nanoTime();
		DijkstraShortestPath d = new DijkstraShortestPath();
		d.setNodes(nodes);
		d.computeShortestPaths(source);
		long estimatedTime = System.nanoTime() - startTime;
		ArrayList<ArrayList<Integer>> pathList = new ArrayList<>();
		for(int i=0; i<destinations.length;i++) {
			ArrayList<Integer> tempList = new ArrayList<>();
			tempList = d.getShortestPathTo(nodes.get(destinations[i]));
			pathList.add(tempList);
		}
		request.setAttribute("paths_", pathList);
		request.setAttribute("elapsedTime", estimatedTime);
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected HashMap<Integer,Node> importData() {
			String text = "";
			try {
				text = new String(Files.readAllBytes(Paths.get("./data.js")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			StringBuilder sb = new StringBuilder(text);
			sb.deleteCharAt(text.length()-1);
			for(int i=7; i>-1;i--) {
				sb.deleteCharAt(i);
			}
			String str = sb.toString();
			str = str.replace(" \\", "");
			Gson gson = new Gson();  
			Node[] arr = gson.fromJson(str, Node[].class);
			HashMap<Integer,Node> map = new HashMap<Integer,Node>();
			for(int i=0; i<arr.length; i++) {
				map.put(arr[i].getID(), arr[i]);
			}
			return map;
	}

}
