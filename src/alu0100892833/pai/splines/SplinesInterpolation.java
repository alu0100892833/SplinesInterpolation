package alu0100892833.pai.splines;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Color;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * Abstract model for interpolating splines. 
 * @author Óscar Darias Plasencia
 * @since 11/05/2017
 */
public class SplinesInterpolation {
	public static final int POINT_RADIUS = 10;
	public static final int POINT_DIAMETER = 2 * POINT_RADIUS;
	public static final int OUTRING = (int) (POINT_RADIUS / 3);
	private static final int BOTTOM_MARGIN_PROPORTION = 4;
	private static final int RIGHT_MARGIN_PROPORTION = 8;
	private static final double X_INCREMENTATION = 0.1;
	private static final int STROKE_THICKNESS = 5;
	private static final Color[] POSSIBLE_COLORS = { Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY };
	
	private int nNodes;							/* Number of nodes or control points */
	private Dimension space;					/* Available space for drawing */
	private ArrayList<Point> controlPoints; 	/* ArrayList with the control points */
	private ArrayList<Point2D.Double> graphic;  /* ArrayList with the generated points */
	private Color controlPointsColor;           /* Control points color */
	private Color graphicColor;					/* Graphics color */
	
	/**
	 * Main constructor
	 */
	public SplinesInterpolation() {
		nNodes = 0;
		controlPoints = new ArrayList<>();
		graphic = new ArrayList<>();
		controlPointsColor = null;
		graphicColor = null;
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
	
	public ArrayList<Point> getControlPoints() {
		return controlPoints;
	}

	public void setControlPoints(ArrayList<Point> points) {
		this.controlPoints = points;
	}
		
	public ArrayList<Point2D.Double> getGraphic() {
		return graphic;
	}

	public void setGraphic(ArrayList<Point2D.Double> graphic) {
		this.graphic = graphic;
	}

	public Color getControlPointsColor() {
		return controlPointsColor;
	}

	public void setControlPointsColor(Color controlPointsColor) {
		this.controlPointsColor = controlPointsColor;
	}

	public Color getGraphicColor() {
		return graphicColor;
	}

	public void setGraphicColor(Color graphicColor) {
		this.graphicColor = graphicColor;
	}

	/**
	 * Generates as many aleatory points as number of nodes.
	 */
	public void generateControlPoints() {
		Random pointGenerator = new Random();
		while (getControlPoints().size() != getnNodes()) {
			int x = pointGenerator.nextInt(getSpace().width - getSpace().width / RIGHT_MARGIN_PROPORTION);
			int y = pointGenerator.nextInt(getSpace().height - getSpace().height / BOTTOM_MARGIN_PROPORTION);
			controlPoints.add(new Point(x, y)); 
		}
	}
	
	/**
	 * Generates the graphics for the established control points.
	 */
	public void generateGraphics() {
		getGraphic().clear();
		MonotoneCubicSpline interpolator = new MonotoneCubicSpline(getControlPoints());
		double xIterator = getControlPoints().get(0).x;
		double lastX = getControlPoints().get(getControlPoints().size() - 1).x;
		while (xIterator <= lastX) {
			Point2D.Double point = new Point2D.Double(xIterator, interpolator.interpolate(xIterator));
			getGraphic().add(point);
			xIterator += X_INCREMENTATION;
		}
	}

	/**
	 * Draws the complete model data.
	 * @param g Graphics where the model data is going to be painted.
	 */
	public void draw(Graphics g, int highlighted) {
		sortControlPoints();
		generateGraphics();
		drawControlPoints(g, highlighted);
		//drawLines(g);
		drawGraphics(g);
	}
	
	/**
	 * Draws the control points.
	 * @param graphics
	 */
	private void drawControlPoints(Graphics graphics, int highlighted) {
		if (getControlPointsColor() == null)
			graphics.setColor(getRandomColor(null));
		setControlPointsColor(graphics.getColor());
		for (Point point : getControlPoints()) {
			graphics.fillOval(point.x - POINT_RADIUS, point.y - POINT_RADIUS, 
					POINT_DIAMETER, POINT_DIAMETER);
			if (getControlPoints().indexOf(point) == highlighted)
				graphics.drawOval(point.x - POINT_RADIUS - OUTRING, point.y - POINT_RADIUS - OUTRING, 
					POINT_DIAMETER + OUTRING * 2, POINT_DIAMETER + OUTRING * 2);
		}
	}
	
	/**
	 * Draws the straight lines between the control points.
	 * @param graphics
	 */
	@SuppressWarnings("unused")
	private void drawLines(Graphics graphics) {
		for (int i = 0; i < getControlPoints().size() - 1; i++) {
			graphics.drawLine(getControlPoints().get(i).x, getControlPoints().get(i).y, 
					getControlPoints().get(i + 1).x, getControlPoints().get(i + 1).y);
		}
	}
	
	/**
	 * Draws the complete interpolation.
	 * @param graphics
	 */
	private void drawGraphics(Graphics graphics) {
		Graphics2D betterGraphics = (Graphics2D) graphics;
		betterGraphics.setStroke(new BasicStroke(STROKE_THICKNESS));
		if (getGraphicColor() == null)
			setGraphicColor(getRandomColor(getControlPointsColor()));
		betterGraphics.setColor(getGraphicColor());
		for (int i = 0; i < getGraphic().size() - 1; i++) {
			betterGraphics.draw(new Line2D.Double(getGraphic().get(i).x, getGraphic().get(i).y,
					getGraphic().get(i + 1).x, getGraphic().get(i + 1).y));
		}
	}
	
	/**
	 * Resets the model to its initial state.
	 */
	public void reset() {
		setnNodes(0);
		controlPoints.clear();
		graphic.clear();
		controlPointsColor = null;
		graphicColor = null;
	}
	
	/**
	 * This method sorts the control points using a custom comparator.
	 */
	public void sortControlPoints() {
		Collections.sort(getControlPoints(), new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				Integer x1 = o1.x;
				Integer x2 = o2.x;
				return x1.compareTo(x2);
			}
		});
	}
	
	/**
	 * Returns a random color, that cannot be the same as the given one by parameter.
	 * @param exception
	 * @return
	 */
	private Color getRandomColor(Color exception) {
		Random random = new Random();
		boolean selected = false;
		Color selection = null;
		while (!selected) {
			selection = POSSIBLE_COLORS[random.nextInt(POSSIBLE_COLORS.length)];
			if (selection != exception)
				selected = true;
		}
		return selection;
	}
	
	
	/**
	 * This class creates a monotone cubic spline, so it can calculate the Y 
	 * that corresponds any X in the splines graphic.
	 * @author Óscar Darias Plasencia
	 * @since 13-5-2017
	 */
	protected class MonotoneCubicSpline {
		
		private List<Point> controlPoints;			/* Control points */
		private double[] interValues;				/* Interpolation values */
 
		/**
		 * Constructor that the interpolation values from the given control points.
		 * This interpolation values can be used to obtain Y = f(X)
		 * @param controlPoints
		 */
		public MonotoneCubicSpline(List<Point> controlPoints) {
			if (controlPoints == null || controlPoints.size() < 2) {
				throw new IllegalArgumentException("There must be at least two control "
						+ "points and the arrays must be of equal length.");
			}

			double[] directors = new double[controlPoints.size() - 1];   
			double[] interpolationValues = new double[controlPoints.size()]; 

			// Compute slopes of secant lines between successive points.
			for (int i = 0; i < controlPoints.size() - 1; i++) {
				double slope = controlPoints.get(i + 1).x - controlPoints.get(i).x; 
				if (slope <= 0f) {
					throw new IllegalArgumentException("The control points must all "
							+ "have strictly increasing X values.");
				}
				directors[i] = (controlPoints.get(i + 1).y - controlPoints.get(i).y) / slope;
			}

			// Initialize the tangents as the average of the secants
			interpolationValues[0] = directors[0];
			for (int i = 1; i < controlPoints.size() - 1; i++)
				interpolationValues[i] = (directors[i - 1] + directors[i]) * 0.5f;
			interpolationValues[controlPoints.size() - 1] = directors[controlPoints.size() - 2];

			// Update the tangents to preserve monotonicity.
			for (int i = 0; i < controlPoints.size() - 1; i++) {
				// Successive Y values are equal
				if (directors[i] == 0f) { 
					interpolationValues[i] = 0f;
					interpolationValues[i + 1] = 0f;
				} else {
					double hickA = interpolationValues[i] / directors[i]; 
					double hickB = interpolationValues[i + 1] / directors[i]; 
					double hypot = (double) Math.hypot(hickA, hickB);
					if (hypot > 9f) { 
						double aux = 3f / hypot;
						interpolationValues[i] = aux * hickA * directors[i];
						interpolationValues[i + 1] = aux * hickB * directors[i]; 
					}
				}
			}
			
			this.controlPoints = controlPoints;
			this.interValues = interpolationValues;
		}

		/**
		 * Interpolates the value of Y = f(X) for the given X. 
		 * In other words, adds X to the domain of the spline.
		 * @param x The X value.
		 * @return The interpolated Y = f(X) value.
		 */
		public double interpolate(double x) {
			// Manage boundary cases
			final int n = controlPoints.size();
			if (Double.isNaN(x))
				return x;
			if (x <= controlPoints.get(0).x)
				return controlPoints.get(0).y;
			if (x >= controlPoints.get(n - 1).x)
				return controlPoints.get(n - 1).y;

			// Find the index 'i' of the last point with the smaller X value.
			int i = 0;
			while (x >= controlPoints.get(i + 1).x) {
				i += 1;
				if (x == controlPoints.get(i).x)
					return controlPoints.get(i).y;
			}

			// Perform cubic spline interpolation.
			double h = controlPoints.get(i + 1).x - controlPoints.get(i).x;
			double t = (x - controlPoints.get(i).x) / h;
			return (controlPoints.get(i).y * (1 + 2 * t) + h * interValues[i] * t) * (1 - t) * (1 - t)
					+ (controlPoints.get(i + 1).y * (3 - 2 * t) + h * interValues[i + 1] * (t - 1)) * t * t;
		}
	}

}























//END
