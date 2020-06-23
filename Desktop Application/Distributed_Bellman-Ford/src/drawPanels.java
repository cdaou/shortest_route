import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

public class drawPanels extends JFrame{
	
	/**
	 * @author daoul
	 */
	private static final long serialVersionUID = 5712112955556059056L;

	static private int frameX,frameY,radius;
	
	private JPanel topPanel = new JPanel();
	private JLayeredPane middlePanel = new JLayeredPane();
	private JPanel checkboxPanel = new JPanel();
	private JPanel pathsPanel = new JPanel();
	private JPanel coordinatesPanel = new JPanel();
	private JPanel tempPanel = new JPanel();
	private JLabel labelChooseNode = new JLabel("Choose your source node and press <ENTER>: ");
	private JLabel labelCoordinates = new JLabel(" ");
	private JLabel labelPathInfo = new JLabel("");
	private JCheckBox checkboxLabels = new JCheckBox("Show Labels");
	private JCheckBox checkboxArrows = new JCheckBox("Show Arrows");
	private JCheckBox checkboxEdges = new JCheckBox("Show Edges");
	private HashMap<String, Node> nodeList = new HashMap<String, Node>();
	private HashMap<Edge,JTextField> weights = new HashMap<>();
	private ArrayList<String> destinationList = new ArrayList<>();
	private ArrayList<Edge> tempList = new ArrayList<>();
	protected distributedBellManFord shortestPath = new distributedBellManFord();
	private drawGraph dG = new drawGraph();
	private int pathIndex = 0;
	private String sourceInput = "";
	
	public drawPanels() {
		
		labelChooseNode.setForeground (Color.white);
		labelChooseNode.setFont(new Font("default", Font.BOLD, 14));
		labelCoordinates.setForeground (Color.LIGHT_GRAY);
		labelCoordinates.setFont(new Font("default", Font.BOLD, 16));
		
		//MAXIMIZE APP'S WINDOW
		setPreferredSize(new Dimension(frameX, frameY));
		setExtendedState(Frame.MAXIMIZED_BOTH);
		//END
		//CREATE TOP PANEL
		JSplitPane splitPane = new JSplitPane();
		getContentPane().setLayout(new GridLayout());
		getContentPane().add(splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(40);
		splitPane.setTopComponent(topPanel);
		splitPane.setBottomComponent(tempPanel);
		splitPane.setEnabled(false);
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
		//END
		//CREATE MIDDLE-BOTTOM PANEL
		splitPane = new JSplitPane();
		tempPanel.setLayout(new GridLayout());
		tempPanel.add(splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(frameY - 40);
		tempPanel = new JPanel();
		splitPane.setTopComponent(middlePanel);
		splitPane.setBottomComponent(tempPanel);
		splitPane.setEnabled(false); 
		//END
		//DIVIDE BOTTOM PANEL TO COORDINATES-CHECKBOX-PATHS PANEL
		splitPane = new JSplitPane();
		tempPanel.setLayout(new GridLayout());
		tempPanel.add(splitPane);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(frameX - 400);
		tempPanel = new JPanel();
		splitPane.setLeftComponent(tempPanel);
		splitPane.setRightComponent(coordinatesPanel);
		splitPane.setEnabled(false);
		splitPane = new JSplitPane();
		tempPanel.setLayout(new GridLayout());
		tempPanel.add(splitPane);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(350);
		splitPane.setLeftComponent(checkboxPanel);
		splitPane.setRightComponent(pathsPanel);
		splitPane.setEnabled(false);
		labelPathInfo.setForeground(Color.red);
		labelPathInfo.setFont(new Font("default", Font.CENTER_BASELINE, 18));
		pathsPanel.add(labelPathInfo);
		//END
		//INITIALIZE AND ADD CHECKBOXES TO BOTTOM PANEL
		checkboxLabels.setForeground(Color.WHITE);
		checkboxLabels.setBackground(Color.DARK_GRAY);
		checkboxLabels.setSelected(true);
		checkboxArrows.setForeground(Color.WHITE);
		checkboxArrows.setBackground(Color.DARK_GRAY);
		checkboxArrows.setSelected(true);
		checkboxEdges.setForeground(Color.WHITE);
		checkboxEdges.setBackground(Color.DARK_GRAY);
		checkboxEdges.setSelected(true);
		checkboxPanel.setBackground(Color.DARK_GRAY);
		checkboxPanel.add(checkboxEdges);
		checkboxPanel.add(checkboxLabels);
		checkboxPanel.add(checkboxArrows);
		coordinatesPanel.setBackground(Color.DARK_GRAY);
		coordinatesPanel.add(labelCoordinates);
		pathsPanel.setBackground(Color.DARK_GRAY);
		pack();
		//END
		//INITIALIZE AND ADD SUBMIT SOURCE BUTTON ON TOP PANEL
		topPanel.add(labelChooseNode);
		JTextField givenSource = new JTextField(3);
		topPanel.add(givenSource);
		topPanel.revalidate();
		topPanel.repaint();
		givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
		//END
		//ADD MAP TO BACKGROUND
		JXMapViewer mapViewer = new JXMapViewer();
		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapViewer.setTileFactory(tileFactory);
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);
		// Set the focus
		GeoPosition thessaloniki = new GeoPosition(40.636, 22.942);
		mapViewer.setZoom(2);
		mapViewer.setAddressLocation(thessaloniki);
		mapViewer.setBounds(0, 0, middlePanel.getWidth(), middlePanel.getHeight());
		middlePanel.add(mapViewer,  new Integer(-10));
		transparentBackground back = new transparentBackground();
		back.setDimensions(middlePanel.getWidth(), middlePanel.getHeight());
		back.setBounds(0, 0, middlePanel.getWidth(), middlePanel.getHeight());
		middlePanel.add(back,  new Integer(-9));
		middlePanel.repaint();
		//END
					
		//CREATE AND INITIALIZE CLASS IMPORT DATA
		importData data = new importData(middlePanel,topPanel.getHeight());
		this.nodeList = data.getNodeList();
		this.destinationList = data.getDestinationList();
		//END
		//ADD MOUSE COORDINATES ON BOTTOM PANEL
		middlePanel.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e){
				labelCoordinates.setText("("+e.getX() +","+ (frameY-e.getY())+")");
				coordinatesPanel.repaint();
			}
		});
		//END
		//CHANGE CURSOR SHAPE IN SOURCE NODE TEXT FIELD
		givenSource.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){	
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
				setCursor(cursor);
			}
			public void mouseExited(MouseEvent e){	
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
				setCursor(cursor);
			}
		});
		//END
		//ADD LISTENER FOR CHECKBOXES
		checkboxLabels.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()!=1) {
					removeLabels();
				}else {
					showLabels();
				}
				dG.repaint();
				middlePanel.revalidate();
				middlePanel.repaint();
				
			}
		}); 
		checkboxArrows.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()!=1)
					dG.setShowArrows(false);
				else
					dG.setShowArrows(true);
				middlePanel.revalidate();
				middlePanel.repaint();
			}
		});
		checkboxEdges.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()!=1) {
					dG.setShowEdges(false);
					checkboxArrows.setSelected(false);
					checkboxLabels.setSelected(false);
					checkboxArrows.setEnabled(false);
					checkboxLabels.setEnabled(false);
					removeLabels();
				}else {
					dG.setShowEdges(true);
					checkboxArrows.setSelected(true);
					checkboxLabels.setSelected(true);
					checkboxArrows.setEnabled(true);
					checkboxLabels.setEnabled(true);
					showLabels();
				}
				middlePanel.revalidate();
				middlePanel.repaint();
			}
		});
		//END
		//CREATING RIGHT CLICK EDITING MENU FOR NODES (& STORING THE SELECTION OF THE USER'S FINAL DESTINATION BY LEFT CLICK)
		middlePanel.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e){
			e.translatePoint((int)+radius/2, (int)(radius+radius/2));
			for (Map.Entry<String, Node> v : nodeList.entrySet()) {
				double distance = Point2D.distance(v.getValue().getX(), v.getValue().getY(), e.getX()-radius, e.getY()-radius-(float)radius/2);
				if(distance < radius){
					if(e.getButton() == MouseEvent.BUTTON3){
						JPopupMenu menu = new JPopupMenu();
						JMenuItem delete = new JMenuItem("Delete Node");
						JMenuItem rename = new JMenuItem("Rename Node");
						JMenuItem changeWeight = new JMenuItem("Change Weight");
						JMenuItem deleteConnection = new JMenuItem("Delete Connection");
						menu.add(delete);
						menu.add(rename);
						menu.add(changeWeight);
						menu.add(deleteConnection);
						menu.show(e.getComponent(), e.getX(), e.getY());
						
						delete.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent deleteEvent) { 
								for (Map.Entry<String, Node> node : nodeList.entrySet()) {
									tempList = new ArrayList<Edge>(node.getValue().getAdjacenciesList());
									for(Edge edge : node.getValue().getAdjacenciesList()){
										if(edge.getDestinationNode().getName().equals(v.getKey()) || edge.getSourceNode().getName().equals(v.getKey())){
											tempList.remove(edge);
											weights.get(edge).setVisible(false);
											weights.remove(edge);
										}
									}
									node.getValue().setAdjacenciesList(tempList);
								}
								destinationList.remove(v.getKey());
								nodeList.remove(v.getKey());
								dG.setNodeList(nodeList);
								computePaths();
								if(!sourceInput.isEmpty()) {
									givenSource.setText(sourceInput);
									givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
									try {
										Robot robot = new Robot();
										robot.keyPress(KeyEvent.VK_ENTER);
									} catch (AWTException ex) {
										ex.printStackTrace();
									}
								}
								dG.repaint();
								middlePanel.repaint();
							}
						}); 
						rename.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent renameEvent) {
								String newNodeName = JOptionPane.showInputDialog(middlePanel, "Give the new name:");
								if(newNodeName!=null && newNodeName.length()>0) {
									Node newNode = nodeList.remove(v.getKey());
									nodeList.put(newNodeName, newNode);
									v.getValue().setName(newNodeName);
									dG.setNodeList(nodeList);
									dG.repaint();
									middlePanel.revalidate();
									middlePanel.repaint();
								}
								
							}
						}); 
						changeWeight.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent changeEvent) {
								String destinationNodeName = JOptionPane.showInputDialog(middlePanel, "Ôhe weight of the line connecting node "+v.getValue().getName()+" to which node you want to change?");
								String newWeight="";
								if(destinationNodeName!=null && destinationNodeName.length()>0) {
									if(nodeList.containsKey(destinationNodeName)){
										for(Edge ed : v.getValue().getAdjacenciesList()){
											if(ed.getDestinationNode().getName().equals(destinationNodeName)){
												newWeight = JOptionPane.showInputDialog(middlePanel, "Give the new weight:");
												ed.setWeight(Double.parseDouble(newWeight));
												weights.get(ed).setText(newWeight);
											}
										}
										System.out.println(newWeight);
										if(newWeight.isEmpty()) 
											JOptionPane.showMessageDialog(null,"There is no such edge.");
										computePaths();
										if(!sourceInput.isEmpty()) {
											givenSource.setText(sourceInput);
											givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
											try {
												Robot robot = new Robot();
												robot.keyPress(KeyEvent.VK_ENTER);
											} catch (AWTException e) {
												e.printStackTrace();
											}
										}
									}else{
										JOptionPane.showMessageDialog(null,"Destination node not found.");
									}
									
								}
							}
						});
						deleteConnection.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent changeEvent) {
								String destinationNodeName = JOptionPane.showInputDialog(middlePanel, "The line connecting "+v.getValue().getName()+" to which node you want to change?");
								if(destinationNodeName!=null && destinationNodeName.length()>0) {
									if(nodeList.containsKey(destinationNodeName)){
										for(Edge ed : v.getValue().getAdjacenciesList()){	
											if(ed.getDestinationNode().getName().equals(destinationNodeName)){
												tempList = new ArrayList<Edge>(v.getValue().getAdjacenciesList());
												tempList.remove(ed);
												weights.get(ed).setVisible(false);
												weights.remove(ed);
												v.getValue().setAdjacenciesList(tempList);
												break;
											}
										}
										computePaths();
										if(!sourceInput.isEmpty()) {
											givenSource.setText(sourceInput);
											givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
											try {
												Robot robot = new Robot();
												robot.keyPress(KeyEvent.VK_ENTER);
											} catch (AWTException ex) {
												ex.printStackTrace();
											}
										}
										dG.repaint();
										middlePanel.repaint();
										
									}else{
										JOptionPane.showMessageDialog(null,"Destination node not found.");
									}
								}
							}
						});
						break;
					}else {
						//STORING THE SELECTION OF THE USER'S FINAL DESTINATION BY LEFT CLICK
						if(destinationList.contains(v.getKey())) {
							if(!sourceInput.isEmpty()) {
								
								pathIndex = destinationList.indexOf(v.getKey());
								givenSource.setText(sourceInput);
								topPanel.requestFocusInWindow();
								givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
								try {
									Robot robot = new Robot();
									robot.keyPress(KeyEvent.VK_ENTER);
									
								} catch (AWTException ex) {
									ex.printStackTrace();
								}
								break;
							}
						}
						//END
					}
				}
			}
		}
		});
		//END
		
		//INITIALIZE GRAPH OPTIONS AND ADD IT TO PANEL
		dG.setNodeList(nodeList);
		dG.setDestinationList(destinationList);
		dG.repaint();
		middlePanel.add(dG, new Integer(2));
		dG.setBounds(0, 0, middlePanel.getWidth(), middlePanel.getHeight());
		middlePanel.revalidate();
		middlePanel.repaint();
		drawLabels();
		//END
		
		computePaths();
		
		//ADD LISTENER FOR WEIGHT TEXT FIELDS
		String condition;
		for(HashMap.Entry<Edge,JTextField> en : weights.entrySet()) {
			condition = en.getValue().getText();
			en.getValue().addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						String text = en.getValue().getText();
						double weight = Double.parseDouble(text);
						en.getKey().setWeight(weight);
						en.getValue().repaint();
						computePaths();
						if(!sourceInput.isEmpty()) {
							givenSource.setText(sourceInput);
							topPanel.requestFocusInWindow();
							givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
							try {
								Robot robot = new Robot();
								robot.keyPress(KeyEvent.VK_ENTER);
								
							} catch (AWTException ex) {
								ex.printStackTrace();
							}
						}
						dG.repaint();
						middlePanel.repaint();
						topPanel.requestFocusInWindow();
						givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
					}
				}
			});
			//CHANGE CURSOR SHAPE IN WEIGHTS TEXT FIELDS
			en.getValue().addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e){	
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
					setCursor(cursor);
				}
				public void mouseExited(MouseEvent e){	
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
					setCursor(cursor);
				}
			});
			if(!condition.equals(en.getValue().getText()))
				break;
		}
		//END
		
		//MAIN CODE FOR CALCULATING PATH(S) AND CALLING DRAW GRAPH CLASS
		givenSource.addActionListener(new ActionListener() { 	
			public void actionPerformed(ActionEvent e) { 
				if(nodeList.containsKey(givenSource.getText())){
					sourceInput = givenSource.getText();
					labelChooseNode.setText("You chose node " + sourceInput + ". Try another: ");
					givenSource.requestFocus(); //enables runner in text field so user directly type weight, without moving mouse inside text field
					if(!destinationList.isEmpty()){
						if (nodeList.get(sourceInput).getDistance(pathIndex) < Double.MAX_VALUE){
							labelPathInfo.setText(shortestPath.getShortestPathFrom(pathIndex, nodeList.get(sourceInput))+"   \u2022  "+String.format( "%.2f", nodeList.get(sourceInput).getDistance(pathIndex), 2) );
						}else{
							labelPathInfo.setText("There is no path connecting: "+sourceInput+" to "+destinationList.get(pathIndex));
						}
						dG.setShortestPath(shortestPath.getShortestPathFrom(pathIndex, nodeList.get(sourceInput)));
						dG.revalidate();
						dG.repaint();
					}else{
						JOptionPane.showMessageDialog(null,"Destination list is empty. Choose destination(s) and try again!");
					}
				}else{
					JOptionPane.showMessageDialog(null,"There is no such node. Please, try again!");
					
				}
				givenSource.setText("");
			}
		}); 
		//END
	}

	public static void setFrameX(int _frameX){
		frameX = _frameX;
	}
	
	public static void setFrameY(int _frameY){
		frameY = _frameY;
	}
	
	public static void setRadius(int _radius) {
		radius = _radius;
	}
	
	public void drawLabels() {
		List<Integer> newCoordinates = new ArrayList<Integer>();
		for (Map.Entry<String, Node> e : nodeList.entrySet()) {
			int x = e.getValue().getX();
			int y = e.getValue().getY();
			int adjacenciesX, adjacenciesY;
			for(Edge edge : e.getValue().getAdjacenciesList()){
				adjacenciesX = edge.getDestinationNode().getX();
				adjacenciesY = edge.getDestinationNode().getY();
				//move center of objects to the center of circles
				newCoordinates = dG.findTopology(x+radius, y+radius, adjacenciesX+radius, adjacenciesY+radius, 4f);
				int movingWidth = 24, movingHeight = 17;
				JTextField temp = new JTextField(2);
				temp.setText(Double.toString(edge.getWeight()));
				temp.setFont(new Font("default", Font.CENTER_BASELINE, 14));
				middlePanel.add(temp,new Integer(3));
				temp.setBounds(newCoordinates.get(2) -movingWidth/2, newCoordinates.get(3) -movingHeight/2, movingWidth, movingHeight);
				weights.put(edge, temp);
			}
		}
	}
	
	public void removeLabels() {
		for(JTextField text : weights.values()) {
			text.setVisible(false);
		}
		middlePanel.revalidate();
		middlePanel.repaint();
	}
	
	public void showLabels() {
		for(JTextField text : weights.values()) {
			text.setVisible(true);
			text.repaint();
		}
	}
	
	public void computePaths() {
		for (Map.Entry<String, Node> entry : nodeList.entrySet()) {
			entry.getValue().clearDistanceNext();
		}
		shortestPath.setNodeList(nodeList);
		lineGraph.clearAll();
		for(int index=0;index<destinationList.size();index++) {
			lineGraph.addDestination(destinationList.get(index));
			shortestPath.computeShortestPathsTo(index, nodeList.get(destinationList.get(index)));
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				lineGraph.initAndShowGUI();
			}
		});

	}
}