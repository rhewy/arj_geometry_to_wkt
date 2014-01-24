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
//    --> Updated MMM-DD-YYYY (fl)
//
// =============================================================================
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;

public class WKTGeometryEngineV01 {
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
				// With the multipath and drawing overlay the start point is
				// first point (pt0) and the end point is the second point (pt1)
				// then the rest of the points follow in the list
				// =============================================================
				// ============================================================
				// need to save the start (pt0) and jump over the start and
				// start at the end (pt1) for the first ring only
				// ===========================================================
				Point startEndPT = tmpPath.getPoint(tmpPath.getPathStart(i));
				if (i == 0) {
					pathStart = tmpPath.getPathStart(i) + 1;
					pathEnd = pathStart + tmpPath.getPathSize(i) - 1;
				} else {
					pathStart = tmpPath.getPathStart(i);
					pathEnd = pathStart + tmpPath.getPathSize(i);
				}
				
				// ===========================================================
				// punch out all the points for a path starting at the end (ot1)
				// ===========================================================
				for (int j = pathStart; j < pathEnd; j++) {
					wkt.append(getWKTxy(tmpPath.getPoint(j)));
					wkt.append(" , ");
				} // end the for all the points in a ring
					// ===========================================================
					// close the ring using the start point (pt0)
					// ===========================================================
				wkt.append(getWKTxy(startEndPT));
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
