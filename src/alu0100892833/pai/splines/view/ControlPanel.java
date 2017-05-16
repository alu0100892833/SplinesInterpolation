package alu0100892833.pai.splines.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Control panel for the Splines Interpolation program. 
 * It has two buttons, for generating the splines and resetting it. 
 * Also has a text field to specify the number of nodes, which will be created automatically.
 * @author Óscar Darias Plasencia
 * @since 11-5-2017
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = -831179510460750435L;
	private static final int BUTTONS_GAP = 50;
	private static final int VERTICAL_MARGIN = 20;
	private static final Dimension INFO_ICON_DIMENSIONS = new Dimension(20, 20);
	
	private JButton generate, reset;		/* Generate and reset buttons, to start the spline interpolation or resetting it. */
	private JTextField nNodes;				/* Text field where the number of used nodes should be specified */
	private JLabel imageLabel;				/* Label for the info icon */
	private InformationFrame informationFrame;
	
	/**
	 * Constructor that creates the layout.
	 */
	public ControlPanel() {
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER, BUTTONS_GAP, VERTICAL_MARGIN));
		
		generate = new JButton("GENERATE");
		generate.setName("GENERATE");
		reset = new JButton("RESET");
		reset.setName("RESET");
		nNodes = new JTextField("Nº OF NODES");
		nNodes.setHorizontalAlignment(JTextField.CENTER);
		nNodes.setName("NODES-NUMBER");
		
		JPanel generationPanel = new JPanel(new GridLayout(2, 1));
		generationPanel.add(generate);
		generationPanel.add(nNodes);
		
		add(generationPanel);
		add(reset);
	}
	
	/**
	 * Adds a listener to the buttons.
	 * @param al An event object; it has to implement the ActionListener interface.
	 */
	public void addActionListeners(ActionListener al) {
		getGenerate().addActionListener(al);
		getReset().addActionListener(al);
	}
	
	
	/**
	 * Loads the given image in a JLabel and situates it in the bottom-right corner of the panel.
	 * Also adds a listener to show an InformationFrame when the imagen is clicked.
	 * @param infoPicture
	 */
	public void loadImageForInfo(Image infoPicture) {
		Image resized = infoPicture.getScaledInstance(INFO_ICON_DIMENSIONS.width, INFO_ICON_DIMENSIONS.height, 
				Image.SCALE_SMOOTH);
		imageLabel = new JLabel(new ImageIcon(resized));
		imageLabel.setName("INFOLABEL");
		add(imageLabel);
		
		imageLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {  
				informationFrame = new InformationFrame();
				informationFrame.setVisible(true);
		    }  
		});
		revalidate();
		repaint();
	}
	

	
	
	/***************
	 * GETTERS AND SETTERS
	 ***************/
	
	public JButton getGenerate() {
		return generate;
	}

	public void setGenerate(JButton generate) {
		this.generate = generate;
	}

	public JButton getReset() {
		return reset;
	}

	public void setReset(JButton reset) {
		this.reset = reset;
	}

	public JTextField getnNodes() {
		return nNodes;
	}

	public void setnNodes(JTextField nNodes) {
		this.nNodes = nNodes;
	}

	public JLabel getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}

	public InformationFrame getInformationFrame() {
		return informationFrame;
	}

	public void setInformationFrame(InformationFrame informationFrame) {
		this.informationFrame = informationFrame;
	}
	
	
	
	

}
