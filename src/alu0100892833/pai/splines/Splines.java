package alu0100892833.pai.splines;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JApplet;



public class Splines extends JApplet {
	private static final long serialVersionUID = -761796052032223616L;
	private static final double HEIGHT_PROPORTION = 1.2;
	private static final double WIDTH_PROPORTION = 1.2;
	
	@Override
	public void init() {
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		SplinesInterpolationControl controller = new SplinesInterpolationControl(SplinesInterpolationControl.APPLET, size);
		setLayout(new BorderLayout());
		add(controller.getSplinePanel(), BorderLayout.CENTER);
		add(controller.getControlPanel(), BorderLayout.SOUTH);
		setSize(size);
		loadInfoImage(controller);
	}
	
	public void loadInfoImage(SplinesInterpolationControl controller) {
		URL urlForInfo = getClass().getResource("img/info.png");		
		ImageIcon picture = new ImageIcon(urlForInfo);
		Image infoPicture = picture.getImage();
		controller.getControlPanel().loadImageForInfo(infoPicture);
	}

	public static void main(String[] args) {
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		@SuppressWarnings("unused")
		SplinesInterpolationControl controller = new SplinesInterpolationControl(SplinesInterpolationControl.STANDALONE, size);
	}

}
