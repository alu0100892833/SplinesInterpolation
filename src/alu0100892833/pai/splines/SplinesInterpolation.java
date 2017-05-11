package alu0100892833.pai.splines;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Random;


/**
 * Abstract model for interpolating splines. 
 * @author Óscar Darias Plasencia
 * @since 11/05/2017
 */
public class SplinesInterpolation {
	private static final int POINT_RADIUS = 10;
	private static final int POINT_DIAMETER = 2 * POINT_RADIUS;
	private static final int BOTTOM_MARGIN_PROPORTION = 6;
	private static final int RIGHT_MARGIN_PROPORTION = 8;
	
	private int nNodes;
	private Dimension space;
	private HashSet<Point> controlPoints;
	
	public SplinesInterpolation() {
		nNodes = 0;
		controlPoints = new HashSet<>();
	}
	
	
	public Dimension getSpace() {
		return space;
	}

	public void setSpace(Dimension space) {
		this.space = space;
	}

	public int getnNodes() {
		return nNodes;
	}

	public void setnNodes(int nNodes) {
		this.nNodes = nNodes;
		if (nNodes > 0)
			generateControlPoints();
	}
	
	public HashSet<Point> getControlPoints() {
		return controlPoints;
	}

	public void setControlPoints(HashSet<Point> points) {
		this.controlPoints = points;
	}
	
	public void generateControlPoints() {
		Random pointGenerator = new Random();
		while (getControlPoints().size() != getnNodes()) {
			int x = pointGenerator.nextInt(getSpace().width - getSpace().width / RIGHT_MARGIN_PROPORTION);
			int y = pointGenerator.nextInt(getSpace().height - getSpace().height / BOTTOM_MARGIN_PROPORTION);
			controlPoints.add(new Point(x, y)); 
		}
	}

	public void draw(Graphics g) {
		drawControlPoints(g);
	}
	
	private void drawControlPoints(Graphics graphics) {
		for (Point point : getControlPoints()) {
			graphics.fillOval(point.x, point.y, POINT_DIAMETER, POINT_DIAMETER);
		}
	}
	
	public void reset() {
		setnNodes(0);
		controlPoints.clear();
	}

}
