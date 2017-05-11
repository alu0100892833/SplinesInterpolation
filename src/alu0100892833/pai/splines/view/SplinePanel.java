package alu0100892833.pai.splines.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


import alu0100892833.pai.splines.SplinesInterpolation;

public class SplinePanel extends JPanel {
	private static final long serialVersionUID = 2311601177126660266L;
	
	private SplinesInterpolation splinesModel;

	public SplinePanel(SplinesInterpolation model, Dimension size) {
		setSize(size);
		this.splinesModel = model;
	}

	public SplinesInterpolation getSplinesModel() {
		return splinesModel;
	}

	public void setSplinesModel(SplinesInterpolation splinesModel) {
		this.splinesModel = splinesModel;
	}
	
	
	@Override
	protected void paintComponent(Graphics g){
	    splinesModel.draw(g);
	}

}
