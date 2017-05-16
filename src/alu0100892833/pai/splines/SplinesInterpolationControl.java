package alu0100892833.pai.splines;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import alu0100892833.pai.splines.view.ControlPanel;
import alu0100892833.pai.splines.view.SplinePanel;
import alu0100892833.pai.splines.view.SplinesView;

/**
 * Controller for the Splines interpolation program. It manages every task, sets the listeners... etc.
 * @author Óscar Darias Plasencia
 * @since 11-5-2017
 */
public class SplinesInterpolationControl {
	public static final int STANDALONE = 0;
	public static final int APPLET = 1;
	public static final int POINT_MOVEMENT = 10;
	
	private SplinesView frame;
	private SplinePanel splinePanel;
	private ControlPanel controlPanel;
	private SplinesInterpolation model;
	private Point activePoint;
	
	/**
	 * Constructor with parameters.
	 * @param executionType It says if is going to be executed an applet or a standalone application. It should
	 * 			get the values SplinesInterpolationControl.STANDALONE or SplinesInterpolationControl.APPLET.
	 * @param size The size of the window to be created.
	 */
	public SplinesInterpolationControl(int executionType, Dimension size) {
		ControlsListener cListener = new ControlsListener();
		model = new SplinesInterpolation();
		model.setSpace(size);
		splinePanel = new SplinePanel(model, size);
		splinePanel.setFocusable(true);
		splinePanel.requestFocus();
		splinePanel.addKeyListener(new KeyManaging());
		controlPanel = new ControlPanel();
		
		if (executionType == STANDALONE) {
			initWindow(size);
			loadInfoImage();
		} else if (executionType == APPLET) {
			
		} else {
			System.err.println(executionType + " is not a valid execution type."); 
		}
		
		controlPanel.addActionListeners(cListener);
		splinePanel.addMouseListener(new MousePointing());
		splinePanel.addMouseMotionListener(new MouseMoving());
	}
	
	/**
	 * Creates a frame with the two panels.
	 */
	private void initWindow(Dimension size) {
		frame = new SplinesView(size, splinePanel, controlPanel);
		frame.setTitle("SPLINE INTERPOLATION");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Returns the main frame.
	 * @return
	 */
	public SplinesView getFrame() {
		return frame;
	}
	
	/**
	 * Loads the info image using the loadImageForInfo method of the control panel.
	 */
	private void loadInfoImage() {
		Image picture = null;
		try {
			picture = ImageIO.read(new File("img/info.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		getControlPanel().loadImageForInfo(picture);
	}

	public SplinePanel getSplinePanel() {
		return splinePanel;
	}

	public void setSplinePanel(SplinePanel splinePanel) {
		this.splinePanel = splinePanel;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public SplinesInterpolation getModel() {
		return model;
	}

	public void setModel(SplinesInterpolation model) {
		this.model = model;
	}
	
	
	/**
	 * This method makes sure that every point stays in range.
	 * @param activePoint
	 */
	private boolean checkPointPosition(Point activePoint) {
		if (activePoint.x - SplinesInterpolation.POINT_RADIUS < 0) {
			activePoint.x = SplinesInterpolation.POINT_RADIUS;
			return false;
		}
		else if (activePoint.y - SplinesInterpolation.POINT_RADIUS < 0) {
			activePoint.y = SplinesInterpolation.POINT_RADIUS;
			return false;
		}
		else if (activePoint.x + SplinesInterpolation.POINT_RADIUS > getSplinePanel().getWidth()) {
			activePoint.x = getSplinePanel().getWidth() - SplinesInterpolation.POINT_RADIUS;
			return false;
		}
		else if (activePoint.y + SplinesInterpolation.POINT_RADIUS > getSplinePanel().getHeight()) {
			activePoint.y = getSplinePanel().getHeight() - SplinesInterpolation.POINT_RADIUS;
			return false;
		}
		return true;
	}
	
	
	
	
	
	public Point getActivePoint() {
		return activePoint;
	}

	public void setActivePoint(Point activePoint) {
		this.activePoint = activePoint;
	}





	/**
	 * Listener class to add functionality to the buttons of the control panel.
	 * @author Óscar Darias Plasencia
	 * @since 14-5-2017
	 */
	public class ControlsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == getControlPanel().getGenerate()) {
				if (getSplinePanel().getSplinesModel().getControlPoints().isEmpty()) {
					try {
						int nNodes = Integer.parseInt(getControlPanel().getnNodes().getText());
						getModel().setnNodes(nNodes); 
						getSplinePanel().revalidate();
						getSplinePanel().repaint();
						getControlPanel().specifyActiveNodeCoordinates("MOVE TO START");
					} catch(NumberFormatException exception) {
						System.out.println("NO POINTS SPECIFIED");
					}
				}
			} else if (e.getSource() == getControlPanel().getReset()) {
				getModel().reset();
				getSplinePanel().revalidate();
				getSplinePanel().repaint();
				getControlPanel().specifyActiveNodeCoordinates("NO POINTS YET");
			} else if (e.getSource() == getControlPanel().getAdd()) {
				try {
					int x = Integer.parseInt(getControlPanel().getPositionX().getText().trim());
					int y = Integer.parseInt(getControlPanel().getPositionY().getText().trim());
					Point newPoint = new Point(x, y);
					if (checkPointPosition(newPoint)) {
						getModel().addControlPoint(new Point(x, y));
						getSplinePanel().revalidate();
						getSplinePanel().repaint();
					}
				} catch(NumberFormatException exception) {
					System.err.println("NO VALID POINTS");
				}
			}
			getSplinePanel().requestFocus();
		}
	}
	
	/**
	 * Listener for the keys, so user can interact with the graphics using the keyboard.
	 * @author Óscar Darias Plasencia
	 * @since 14-5-2017
	 */
	public class KeyManaging extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			Point activePoint = getSplinePanel().getCurrentNode();
			if (getSplinePanel().getSelectedNode() != SplinePanel.NO_SELECTED) {
				if (ke.getKeyCode() == KeyEvent.VK_F) {
					getSplinePanel().setSelectedNode(getSplinePanel().getSelectedNode() - 1);
					if (getSplinePanel().getSelectedNode() == SplinePanel.NO_SELECTED)
						getSplinePanel().setSelectedNode(0);
					splinePanel.revalidate();
					splinePanel.repaint();
				} else if (ke.getKeyCode() == KeyEvent.VK_G) {
					getSplinePanel().setSelectedNode(getSplinePanel().getSelectedNode() + 1);
					if (getSplinePanel().getSelectedNode() >= getSplinePanel().getSplinesModel().getControlPoints().size())
						getSplinePanel().setSelectedNode(getSplinePanel().getSplinesModel().getControlPoints().size() - 1);
					splinePanel.revalidate();
					splinePanel.repaint();
				} else if (ke.getKeyCode() == KeyEvent.VK_UP) {
					activePoint.y -= POINT_MOVEMENT; 
					splinePanel.revalidate();
					splinePanel.repaint();
				} else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
					activePoint.y += POINT_MOVEMENT; 
					splinePanel.revalidate();
					splinePanel.repaint();
				} else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
					activePoint.x -= POINT_MOVEMENT; 
					splinePanel.revalidate();
					splinePanel.repaint();
				} else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
					activePoint.x += POINT_MOVEMENT; 
					splinePanel.revalidate();
					splinePanel.repaint();
				}
			}
			checkPointPosition(activePoint);
			activePoint = getSplinePanel().getCurrentNode();
			getControlPanel().specifyActiveNodeCoordinates("[" + activePoint.getX() + ", " + activePoint.getY() + "]");
		}
	}
	
	
	protected class MousePointing extends MouseInputAdapter {
		
		@Override
		public void mouseClicked(MouseEvent me) {
			int x = me.getX();
			int y = me.getY();
			Point reference = getPressedPoint(x, y);
			if (reference == null) {
				getModel().addControlPoint(new Point(x, y));
			}
			getSplinePanel().revalidate();
			getSplinePanel().repaint();
		}
		
		
		@Override
		public void mousePressed(MouseEvent me) {
			setActivePoint(getPressedPoint(me.getX(), me.getY()));
		}
		
		@Override
		public void mouseReleased(MouseEvent me) {
			setActivePoint(null);
		}
		
		public Point getPressedPoint(int x, int y) {
			for (Point point : getSplinePanel().getSplinesModel().getControlPoints()) {
				if ((Math.abs(point.x - x) <= SplinesInterpolation.POINT_RADIUS)
						&& (Math.abs(point.y - y) <= SplinesInterpolation.POINT_RADIUS)) {
					getSplinePanel().setSelectedNode(getSplinePanel().getSplinesModel().getControlPoints().indexOf(point));
					return getSplinePanel().getCurrentNode();
				}
			}
			return null;
		}
	}
	
	protected class MouseMoving extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent me) {
			if (getActivePoint() != null) {
				getActivePoint().x = me.getX();
				getActivePoint().y = me.getY();
				getSplinePanel().revalidate();
				getSplinePanel().repaint();
			}
		}
	}
}


















// END