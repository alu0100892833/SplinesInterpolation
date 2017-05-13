package alu0100892833.pai.splines.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


import alu0100892833.pai.splines.SplinesInterpolation;

public class SplinePanel extends JPanel {
	private static final long serialVersionUID = 2311601177126660266L;
	public static final int NO_SELECTED = -1;
	
	private SplinesInterpolation splinesModel;
	private int selectedNode;

	public SplinePanel(SplinesInterpolation model, Dimension size) {
		setSize(size);
		this.splinesModel = model;
		this.selectedNode = NO_SELECTED;
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!getSplinesModel().getControlPoints().isEmpty())
			splinesModel.draw(g);
	}

	
	
}
