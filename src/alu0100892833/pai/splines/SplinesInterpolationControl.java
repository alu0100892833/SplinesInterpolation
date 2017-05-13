package alu0100892833.pai.splines;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

import alu0100892833.pai.splines.view.ControlPanel;
import alu0100892833.pai.splines.view.SplinePanel;

/**
 * Controller for the Splines interpolation program. It manages every task, sets the listeners... etc.
 * @author Óscar Darias Plasencia
 * @since 11-5-2017
 */
public class SplinesInterpolationControl {
	public static final int STANDALONE = 0;
	public static final int APPLET = 1;
	public static final int POINT_MOVEMENT = 10;
	
	private SplinePanel splinePanel;
	private ControlPanel controlPanel;
	private SplinesInterpolation model;
	
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
		splinePanel.addMouseListener(new PointSelection());
	}
	
	/**
	 * Creates a frame with the two panels.
	 */
	private void initWindow(Dimension size) {
		JFrame frame = new JFrame("SPLINE INTERPOLATION");
		frame.setLayout(new BorderLayout());
		frame.add(splinePanel, BorderLayout.CENTER);
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(size);
		frame.setVisible(true);
	}
	
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
					} catch(NumberFormatException exception) {
						exception.printStackTrace();
					}
				}
			} else if (e.getSource() == getControlPanel().getReset()) {
				getModel().reset();
				getSplinePanel().revalidate();
				getSplinePanel().repaint();
			}
			getSplinePanel().requestFocus();
		}
	}
	
	public class KeyManaging extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			Point activePoint = getSplinePanel().getCurrentNode();
			if (getSplinePanel().getSelectedNode() != SplinePanel.NO_SELECTED) {
				if (ke.getKeyCode() == KeyEvent.VK_F) {
					getSplinePanel().setSelectedNode(getSplinePanel().getSelectedNode() - 1);
				} else if (ke.getKeyCode() == KeyEvent.VK_G) {
					getSplinePanel().setSelectedNode(getSplinePanel().getSelectedNode() + 1);
					if (getSplinePanel().getSelectedNode() >= getSplinePanel().getSplinesModel().getControlPoints().size())
						getSplinePanel().setSelectedNode(SplinePanel.NO_SELECTED);
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
		}
	}
	
	
	protected class PointSelection extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent me) {
			int x = me.getX();
			int y = me.getY();
			for (Point point : getSplinePanel().getSplinesModel().getControlPoints()) {
				if ((Math.abs(point.x - x) <= SplinesInterpolation.POINT_RADIUS)
						&& (Math.abs(point.y - y) <= SplinesInterpolation.POINT_RADIUS))
					getSplinePanel().setSelectedNode(getSplinePanel().getSplinesModel().getControlPoints().indexOf(point));
			}
			getSplinePanel().requestFocus();
		}
	}
	
}


















// END