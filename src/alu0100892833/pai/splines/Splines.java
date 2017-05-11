package alu0100892833.pai.splines;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

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
		//loadInfoImage(controller);
	}
	
	/*public void loadInfoImage(SplinesInterpolationControl controller) {
		URL urlForInfo = null;
		Image infoPicture = null;
		try {
			urlForInfo = new URL(getCodeBase(), "img/info.png");
			infoPicture = ImageIO.read(urlForInfo);
			infoPicture = getImage(getDocumentBase(), "img/info.png");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		controller.getControlPanel().loadImageForInfo(infoPicture);
	}*/

	public static void main(String[] args) {
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		@SuppressWarnings("unused")
		SplinesInterpolationControl controller = new SplinesInterpolationControl(SplinesInterpolationControl.STANDALONE, size);
	}

}
