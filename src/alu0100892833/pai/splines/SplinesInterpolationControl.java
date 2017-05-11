package alu0100892833.pai.splines;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


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
		model = new SplinesInterpolation();
		model.setSpace(size);
		splinePanel = new SplinePanel(model, size);
		controlPanel = new ControlPanel();
		
		if (executionType == STANDALONE) {
			initWindow(size);
			loadInfoImage();
		} else if (executionType == APPLET) {
			
		} else {
			System.err.println(executionType + " is not a valid execution type."); 
		}
		
		controlPanel.addActionListeners(new ControlsListener());
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
				try {
					int nNodes = Integer.parseInt(getControlPanel().getnNodes().getText());
					getModel().setnNodes(nNodes); 
					getSplinePanel().revalidate();
					getSplinePanel().repaint();
				} catch(NumberFormatException exception) {
					exception.printStackTrace();
				}
			} else if (e.getSource() == getControlPanel().getReset()) {
				getModel().reset();
				getSplinePanel().revalidate();
				getSplinePanel().repaint();
			}
		}
	}
	
	
	
}


















// END