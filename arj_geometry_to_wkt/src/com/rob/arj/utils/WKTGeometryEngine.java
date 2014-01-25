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

public class WKTGeometryEngine
{
	public static String geomToWKT(Geometry geom)
	{
		// ============================================================
		// 'They' say StringBuoder is faster than StringBuffer
		// ===========================================================
		StringBuilder wkt = new StringBuilder();

		// ============================================================
		// Convert an ARJ point to a WKT point
		// ===========================================================
		if (geom.getType() == Geometry.Type.POINT)
		{
			Point tmpPT = (Point) geom;
			wkt.append(String.format("POINT(%s %s)", tmpPT.getX(), tmpPT.getY()));

		}
		// ============================================================
		// Convert an ARJ polygon to a WKT polygon
		// ============================================================
		else if (geom.getType() == Geometry.Type.POLYGON)
		{
			MultiPath tmpPath = (MultiPath) geom;
			printAllPoints(tmpPath);

			// ============================================================
			// Start the WKT for the polygon
			// ===========================================================
			wkt.append("POLYGON(");

			int pathStart, pathEnd;
			for (int i = 0; i < tmpPath.getPathCount(); i++)
			{
				wkt.append("(");
				// ===========================================================
				// Depending which drawing mode was used to capture the polygon
				// there are some geometry issues to solve:
				// DrawingMode.POLYGON_RECTANGLE has the traditional connect the
				// dot
				// finish where you end geometry --> normal conversion
				// DrawingMode.POLYGON does not and there are 2 issues:
				// 1.) There is a stutter: the first point/node is duplicated
				// 2.) The paths do not explicitly close
				// Currently I leave the duplicate in to keep the logic simple
				// Also, if the last point of the path does not equal the first
				// (it will not for DrawingMode.POLYGON) then punch out the
				// start point to close the polygon
				// ===========================================================
				// ===========================================================
				// There can be multiple paths in the polygon:
				// 1.) disjoint rings
				// 2.) donuts
				// Set the start and end for the current path
				// ===========================================================
				pathStart = tmpPath.getPathStart(i);
				pathEnd = pathStart + tmpPath.getPathSize(i);
				// ===========================================================
				// Set the start and end points
				// ===========================================================
				Point startPT = tmpPath.getPoint(pathStart);
				Point endPT = tmpPath.getPoint(pathEnd - 1);
				// ===========================================================
				// punch out all the points for a path starting at the first
				// point
				// ===========================================================
				for (int j = pathStart; j < pathEnd; j++)
				{
					wkt.append(getWKTxy(tmpPath.getPoint(j)));
					// ===========================================================
					// do not add a comma if this is the last point
					// ===========================================================
					if (j < pathEnd - 1)
					{
						wkt.append(" , ");
					}
				} // end the for all the points in a ring
					// ===========================================================
					// close the ring using the start point if the end sand
					// start
					// points are not the same
					// ===========================================================
				if (startPT.getX() != endPT.getX() || startPT.getY() != endPT.getY())
				{
					wkt.append(" , " + getWKTxy(startPT));
				}
				// ===========================================================
				// close the ring
				// ===========================================================
				wkt.append(")");
				// ===========================================================
				// add a separator if there is more than one ring and not the
				// this ring in not the last ring
				// ===========================================================
				if (tmpPath.getPathCount() > 0 && i < tmpPath.getPathCount() - 1)
				{
					wkt.append(" , ");
				}

			} // end the for all the rings in a polygon
				// ===========================================================
				// close the polygon
				// ===========================================================
			wkt.append(")");

		}
		// ============================================================
		// Convert an ARJ Polyline to a WKT polygon
		// ============================================================
		else if (geom.getType() == Geometry.Type.POLYLINE)
		{
			MultiPath tmpPath = (MultiPath) geom;
			printAllPoints(tmpPath);
			// ============================================================
			// Start the WKT for the polygon
			// ===========================================================
			wkt.append("MULTILINESTRING(");

			int pathStart, pathEnd;
			for (int i = 0; i < tmpPath.getPathCount(); i++)
			{
				wkt.append("(");
				// ===========================================================
				// Get the starting index and set the end
				// =============================================================
				pathStart = tmpPath.getPathStart(i);
				pathEnd = pathStart + tmpPath.getPathSize(i);
				// ===========================================================
				// punch out all the points for a path
				// ===========================================================
				for (int j = pathStart; j < pathEnd; j++)
				{
					wkt.append(getWKTxy(tmpPath.getPoint(j)));
					if (j < pathEnd - 1)
					{
						wkt.append(" , ");
					}
				} // end the for all the points in a path

				// ===========================================================
				// close the WKT line-string within the multi-line-string
				// ===========================================================
				wkt.append(")");
				// ===========================================================
				// add a separator if there is more than one line-string
				// in the multi-line-string
				// ===========================================================
				if (tmpPath.getPathCount() > 0 && i < tmpPath.getPathCount() - 1)
				{
					wkt.append(" , ");
				}

			} // end the for each path

			// ===========================================================
			// close the WKT multi-line-string
			// ===========================================================
			wkt.append(")");

		}
		return wkt.toString();
	}
	// ===========================================================
	// convenience method to keep the code clean
	// ===========================================================
	private static String getWKTxy(Point pt)
	{
		return String.format("%s %s", pt.getX(), pt.getY());
	}
	// ===========================================================
	// Debugging method to determine what might be causing problems
	// List out all the points for a piece of geometry
	// ===========================================================
	private static void printAllPoints(MultiPath path)
	{
		Point pt;
		for (int i = 0; i < path.getPointCount(); i++)
		{
			pt = path.getPoint(i);
			System.out.println(String.format("%s %s", pt.getX(), pt.getY()));
		}
	}

}
