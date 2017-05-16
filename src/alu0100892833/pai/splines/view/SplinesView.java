package alu0100892833.pai.splines.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Frame view for the splines interpolation program.
 * @author Óscar Darias Plasencia
 * @since 15-5-2017
 */
public class SplinesView extends JFrame {
	private static final long serialVersionUID = 1790581179296173200L;
	
	private SplinePanel splinePanel;
	private ControlPanel controlPanel;
	
	/**
	 * Constructor with parameters.
	 * @param size Dimensions of the window.
	 * @param splines Panel for the splines interpolation graphic.
	 * @param control Control panel.
	 */
	public SplinesView(Dimension size, SplinePanel splines, ControlPanel control) {
		splinePanel = splines;
		controlPanel = control;
		setLayout(new BorderLayout());
		add(splinePanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		setSize(size);
		
		splinePanel.setName("SPLINE-PANEL");
		controlPanel.setName("CONTROL");
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
	
	

}

