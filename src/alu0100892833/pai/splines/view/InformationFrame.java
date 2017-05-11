package alu0100892833.pai.splines.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Ventana que muestra información acerca de la práctica desarrollada.
 * Simplemente sitúa un grupo de JLabel con el texto descriptivo.
 * @author alu0100892833
 * @since 2-5-2017
 */
public class InformationFrame extends JFrame {
	private static final long serialVersionUID = 892626450037153753L;
	private static final double WIDTH_PROPORTION = 3;
	private static final double HEIGHT_PROPORTION = 3;
	private static final String SUBJECT = "PROGRAMACIÓN DE APLICACIONES INTERACTIVAS";
	private static final String EXERCISE = "PRÁCTICA 14 - Interpolación de Splines";
	private static final String DESCRIPTION = "Aplicación interactiva en Java para interpolación de splines cúbicos.";
	private static final String AUTHOR_NAME = "Óscar Darias Plasencia";

	public InformationFrame() throws HeadlessException {
		super();
		setTitle("INFORMATION");
		setLocationRelativeTo(null);
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		setSize(size);
		showInformation();
	}

	/**
	 * Establece el tipo de Layout a FlowLayout y le añade toda la información que se quiere mostrar.
	 */
	private void showInformation() {
		setLayout(new FlowLayout(FlowLayout.LEADING));
		add(new JLabel(SUBJECT));
		add(new JLabel(EXERCISE));
		add(new JLabel(DESCRIPTION));
		add(new JLabel(AUTHOR_NAME));
	}

}