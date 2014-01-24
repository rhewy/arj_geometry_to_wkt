package com.rob.arj.utils;

//==============================================================================
// File         : WKTGeometryEngine.java
//
// Current Author: Robert Hewlett
//
// Previous Author: None
//
// Contact Info: rob.hewy@gmail.com
//
// Purpose : Help the ArcGIS Runtime for Java convert its base
//           geometry to well known text
//
// Dependencies: ArcGIS_Runtime_Java.jar (see the imports)
//
// Modification Log :
//    --> Created JAN-21-2014 (fl)
//    --> Updated JAN-21-2014 (fl)
//          --> Added support for Polyline to MultiLineString
//          --> Added support for Rectangle Polygons to Polygon
//          
// =============================================================================
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;

public class WKTGeometryEngine {
	public static String geomToWKT(Geometry geom) {
		StringBuilder wkt = new StringBuilder();

		if (geom.getType() == Geometry.Type.POINT) {
			Point tmpPT = (Point) geom;
			wkt.append(String.format("POINT(%s %s)", tmpPT.getX(), tmpPT.getY()));
			
		} else if (geom.getType() == Geometry.Type.POLYGON) {
			MultiPath tmpPath = (MultiPath) geom;
			printAllPoints(tmpPath);

			// ============================================================
			// Start the WKT for the polygon
			// ===========================================================
			wkt.append("POLYGON(");

			int pathStart, pathEnd;
			for (int i = 0; i < tmpPath.getPathCount(); i++) {
				wkt.append("(");
				// ===========================================================
				// With the multipath based polygon created using the drawing
				// overlay
				// the first point (for some strange reason) is repeated.
				// We called this the studder
				// =============================================================
				// ============================================================
				// need to save the start (pt0) and jump over the start and
				// start at the end (pt1) for the first ring only
				// ===========================================================

				pathStart = tmpPath.getPathStart(i);
				pathEnd = pathStart + tmpPath.getPathSize(i);

				Point startPT = tmpPath.getPoint(pathStart);
				Point endPT = tmpPath.getPoint(pathEnd - 1);
				
				// ===========================================================
				// punch out all the points for a path starting at the first
				// point
				// ===========================================================
				for (int j = pathStart; j < pathEnd; j++) {
					wkt.append(getWKTxy(tmpPath.getPoint(j)));
					if (j < pathEnd - 1) {
						wkt.append(" , ");
					}
				} // end the for all the points in a ring

				// ===========================================================
				// close the ring using the start point if the end
				// ===========================================================
				if (startPT.getX() != endPT.getX()
						|| startPT.getY() != endPT.getY()) {
					wkt.append(" , " + getWKTxy(startPT));
				}
				// ===========================================================
				// close the ring
				// ===========================================================
				wkt.append(")");
				// ===========================================================
				// add a separator if there is more than one ring and not the
				// last ring
				// ===========================================================
				if (tmpPath.getPathCount() > 0
						&& i < tmpPath.getPathCount() - 1) {
					wkt.append(" , ");
				}

			} // end the for all the rings in a polygon
				// ===========================================================
				// close the polygon
				// ===========================================================
			wkt.append(")");

		} else if (geom.getType() == Geometry.Type.POLYLINE) {
			MultiPath tmpPath = (MultiPath) geom;
			printAllPoints(tmpPath);
			// ============================================================
			// Start the WKT for the polygon
			// ===========================================================
			wkt.append("MULTILINESTRING(");

			int pathStart, pathEnd;
			for (int i = 0; i < tmpPath.getPathCount(); i++) {
				wkt.append("(");
				// ===========================================================
				// Get the starting index and set the end
				// =============================================================
				pathStart = tmpPath.getPathStart(i);
				pathEnd = pathStart + tmpPath.getPathSize(i);
				// ===========================================================
				// punch out all the points for a path starting at the end (ot1)
				// ===========================================================
				for (int j = pathStart; j < pathEnd; j++) {
					wkt.append(getWKTxy(tmpPath.getPoint(j)));
					if (j < pathEnd - 1) {
						wkt.append(" , ");
					}
				} // end the for all the points in a ring

				// ===========================================================
				// close the ring
				// ===========================================================
				wkt.append(")");
				// ===========================================================
				// add a separator if there is more than one ring and not the
				// last ring
				// ===========================================================
				if (tmpPath.getPathCount() > 0
						&& i < tmpPath.getPathCount() - 1) {
					wkt.append(" , ");
				}

			} // end the for all the rings in a polygon
				// ===========================================================
				// close the polygon
				// ===========================================================
			wkt.append(")");

		}
		return wkt.toString();
	}

	private static String getWKTxy(Point pt) {
		return String.format("%s %s", pt.getX(), pt.getY());
	}

	private static void printAllPoints(MultiPath path) {
		Point pt;
		for (int i = 0; i < path.getPointCount(); i++) {
			pt = path.getPoint(i);
			System.out.println(String.format("%s %s", pt.getX(), pt.getY()));
		}
	}

}
