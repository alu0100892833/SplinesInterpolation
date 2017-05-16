package alu0100892833.pai.splines.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


import alu0100892833.pai.splines.SplinesInterpolation;

/**
 * Main panel for drawing the interpolation graphics.
 * @author Óscar Darias Plasencia
 * @since 14-5-2017
 */
public class SplinePanel extends JPanel {
	private static final long serialVersionUID = 2311601177126660266L;
	public static final int NO_SELECTED = -1;
	
	private SplinesInterpolation splinesModel;
	private int selectedNode;

	/**
	 * Main constructor.
	 * @param model The model data for the interpolation.
	 * @param size Size of the panel.
	 */
	public SplinePanel(SplinesInterpolation model, Dimension size) {
		setSize(size);
		this.splinesModel = model;
		this.selectedNode = 0;
	}

	public SplinesInterpolation getSplinesModel() {
		return splinesModel;
	}

	public void setSplinesModel(SplinesInterpolation splinesModel) {
		this.splinesModel = splinesModel;
	}	
	
	public int getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(int selectedNode) {
		this.selectedNode = selectedNode;
	}
	
	public Point getCurrentNode() {
		return getSplinesModel().getControlPoints().get(getSelectedNode());
	}

	/**
	 * Overloaded paintComponent to draw the model data if not null.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!getSplinesModel().getControlPoints().isEmpty())
			splinesModel.draw(g, getSelectedNode());
	}

	
	
}
