package gist8010;

//==============================================================================
// File         : MainFrame.java
//
// Current Author: Robert Hewlett
//
// Previous Author: None
//
// Contact Info: rob.hewy@gmail.com
//
// Purpose : 
//
// Dependencies: None
//
// Modification Log :
//    --> Created JAN-12-2014 (fl)
//    --> Updated MMM-DD-YYYY (fl)
//
// =============================================================================
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;

import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import com.esri.client.toolkit.overlays.DrawingOverlay;
import com.esri.client.toolkit.overlays.DrawingOverlay.DrawingMode;
import com.esri.core.geometry.Geometry;
import com.esri.core.map.Graphic;
import com.esri.core.portal.Portal.GetAuthCodeCallback;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.client.toolkit.overlays.DrawingCompleteListener;
import com.esri.client.toolkit.overlays.DrawingCompleteEvent;
import com.rob.arj.utils.WKTGeometryEngine;

import javax.swing.JTable;

public class MainFrame {

	private JFrame frame;
	private JMap map;
	private Connection conn;
	private GraphicsLayer graphicsLayer;
	private DrawingOverlay drawingOverlay;

	// ===========================================================
	// Basic Symbology for points line and polygons
	// import com.esri.core.symbol.MarkerSymbol;
	// import com.esri.core.symbol.SimpleFillSymbol;
	// import com.esri.core.symbol.SimpleLineSymbol;
	// import com.esri.core.symbol.SimpleMarkerSymbol;
	// import java.awt.Color;
	// ===========================================================
	private final SimpleLineSymbol polyLineSym = new SimpleLineSymbol(
			new Color(0, 0, 150), 3);
	private final SimpleLineSymbol polyLineFreeSym = new SimpleLineSymbol(
			new Color(200, 0, 0), 3);
	private final SimpleLineSymbol lineSym = new SimpleLineSymbol(new Color(0,
			0, 150), 3);
	private final SimpleFillSymbol rectangleSym = new SimpleFillSymbol(
			new Color(200, 0, 0, 60), new SimpleLineSymbol(
					new Color(200, 0, 0), 3));
	final SimpleLineSymbol dottedLine = new SimpleLineSymbol(Color.BLACK, 2);
	private final SimpleFillSymbol polygonSym = new SimpleFillSymbol(new Color(
			0, 0, 0, 80), dottedLine);
	private final SimpleMarkerSymbol pointSym = new SimpleMarkerSymbol(
			Color.RED, 15, SimpleMarkerSymbol.Style.DIAMOND);
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			// ==============================================================================
			// Method : windowClosing
			//
			// Current Author: Robert Hewlett
			//
			// Previous Author: None
			//
			// Contact Info: rob.hewy@gmail.com
			//
			// Purpose : Close the app cleaning by disposing the map
			//
			// Dependencies: None
			//
			// Modification Log :
			// --> Created JAN-12-2014 (fl)
			// --> Updated MMM-DD-YYYY (fl)
			//
			// =============================================================================
			public void windowClosing(WindowEvent arg0) {
				getMap().dispose();
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		map = new JMap();
		frame.getContentPane().add(map, BorderLayout.CENTER);

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnPolygontool = new JButton("");
		btnPolygontool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDrawingOverlay().setUp(DrawingMode.POLYGON,
						getThis().polygonSym, getBlankAttributes());

			}
		});
		btnPolygontool.setIcon(new ImageIcon(MainFrame.class
				.getResource("/resources/pin-red.png")));
		toolBar.add(btnPolygontool);

		JButton btnClear = new JButton("");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection conn = getConnection();
					Statement sql = conn.createStatement();

					sql.executeUpdate("DELETE FROM gist_8010_m03.areas_of_interest");

					getGraphicsLayer().removeAll();

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		btnClear.setIcon(new ImageIcon(MainFrame.class
				.getResource("/resources/folder-sync.png")));
		toolBar.add(btnClear);

		JButton btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conn = getConnection();
				if (conn == null) {
					JOptionPane.showMessageDialog(null,
							"Connect to the DB first");
				} else {
					try {

						PreparedStatement pStat = conn
								.prepareStatement("INSERT INTO gist_8010_m03.areas_of_interest (name, description, geom) VALUES(?,?,st_GeomFromText(?,3005))");

						if (conn != null) {
							Graphic tmpGraphic;
							if (getGraphicsLayer().getNumberOfGraphics() != 0) {
								int[] ids = getGraphicsLayer().getGraphicIDs();
								for (int i = 0; i < ids.length; i++) {
									tmpGraphic = getGraphicsLayer().getGraphic(
											ids[i]);

									String name = (String) tmpGraphic
											.getAttributeValue("name");
									pStat.setString(1, name);

									String desc = (String) tmpGraphic
											.getAttributeValue("description");

									pStat.setString(2, desc);

									String wkt = WKTGeometryEngine
											.geomToWKT(tmpGraphic.getGeometry());

									System.out.println(wkt);

									pStat.setString(3, wkt);
									if (tmpGraphic.getGeometry().getType() == Geometry.Type.POLYGON) {
										pStat.executeUpdate();
									}
								}
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Could not connect to the database");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnSave.setIcon(new ImageIcon(MainFrame.class
				.getResource("/resources/database.png")));
		toolBar.add(btnSave);

		JButton btnLine = new JButton("");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDrawingOverlay().setUp(DrawingMode.POLYLINE, lineSym,
						getBlankAttributes());
			}
		});
		btnLine.setIcon(new ImageIcon(MainFrame.class
				.getResource("/resources/pencil.png")));
		toolBar.add(btnLine);

		JButton btnRectangle = new JButton("");
		btnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDrawingOverlay().setUp(DrawingMode.POLYGON_RECTANGLE,
						polygonSym, getBlankAttributes());
			}
		});
		btnRectangle.setIcon(new ImageIcon(MainFrame.class
				.getResource("/resources/post-it.png")));
		toolBar.add(btnRectangle);

		drawingOverlay = new DrawingOverlay();
		drawingOverlay
				.addDrawingCompleteListener(new DrawingCompleteListener() {
					public void drawingCompleted(DrawingCompleteEvent event) {
						getGraphicsLayer().addGraphic(
								getDrawingOverlay().getAndClearGraphic());
					}
				});
		frame.getContentPane().add(drawingOverlay, BorderLayout.SOUTH);

		map.addMapOverlay(drawingOverlay);

		table = new JTable();
		frame.getContentPane().add(table, BorderLayout.EAST);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmConnect = new JMenuItem("Connect");
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectionInfoDialog connDia = new ConnectionInfoDialog();
				connDia.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

				connDia.setVisible(true);

				if (connDia.getConnectionInfo() == null) {
					System.out.println("The user clicked cancel");
				} else {
					try {
						// ===========================================================
						// Load the postgresql driver
						// ===========================================================
						Class.forName("org.postgresql.Driver");
						// ===========================================================
						// Create a connection using the connection info
						//
						// Things to do:
						// 1. Add a private field called conn of type connection
						// private Connection conn;
						// 2. Add the following imports:
						// import java.sql.Connection;
						// import java.sql.DriverManager;
						// ===========================================================
						String dbURL = connDia.getConnectionInfo()
								.getDatabaseURL();
						String userName = connDia.getConnectionInfo()
								.getUserName();
						String passWord = connDia.getConnectionInfo()
								.getUserPassword();

						// ===========================================================
						// make a connection with the connection info
						// ===========================================================
						conn = DriverManager.getConnection(dbURL, userName,
								passWord);

						System.out.println("connected to the db .....");

					} catch (Exception e) {
						e.printStackTrace();

					}

				}

			}
		});
		mnFile.add(mntmConnect);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			// ==============================================================================
			// Method : actionPerformed
			//
			// Current Author: Robert Hewlett
			//
			// Previous Author: None
			//
			// Contact Info: rob.hewy@gmail.com
			//
			// Purpose : Close the app cleaning by disposing the map via a menu
			// selection
			//
			// Dependencies: None
			//
			// Modification Log :
			// --> Created JAN-12-2014 (fl)
			// --> Updated MMM-DD-YYYY (fl)
			//
			// =============================================================================
			public void actionPerformed(ActionEvent arg0) {
				getMap().dispose();
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		// ===========================================================
		// Add the BC basemap map service
		// ===========================================================
		ArcGISTiledMapServiceLayer tiledLayer = new ArcGISTiledMapServiceLayer(
				"http://maps.gov.bc.ca/arcserver/rest/services/Province/albers_cache/MapServer");
		map.getLayers().add(tiledLayer);
		graphicsLayer = new GraphicsLayer();

		map.getLayers().add(graphicsLayer);

	}

	public JFrame getFrame() {
		return frame;
	}

	public JMap getMap() {
		return map;
	}

	// ===========================================================
	// Simple getter to return the private connection
	// ===========================================================
	public Connection getConnection() {
		return this.conn;
	}

	// ===========================================================
	// Simple getter to return the private graphics layer
	//
	// To do ensure you have a
	// 1. Add a private field at the top of the class:
	// private GraphicsLayer graphicsLayer;
	// 2. Add an import:
	// import com.esri.map.GraphicsLayer;
	// ===========================================================
	public GraphicsLayer getGraphicsLayer() {
		return graphicsLayer;
	}

	public DrawingOverlay getDrawingOverlay() {
		return drawingOverlay;
	}

	public MainFrame getThis() {
		return this;
	}

	// ===========================================================
	// Simple getter to return a set of blank attributes for a
	// a graphic
	//
	// 1. Add imports:
	// import java.util.HashMap;
	// import java.util.Map;
	// ===========================================================
	public Map<String, Object> getBlankAttributes() {
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("name", "Polygon_NNN");
		list.put("description", "A simple test polygon");
		return list;
	}

}
