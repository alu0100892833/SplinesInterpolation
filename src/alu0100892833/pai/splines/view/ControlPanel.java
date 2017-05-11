package alu0100892833.pai.splines.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	
	private JButton generate, reset;		/* Generate and reset buttons, to start the spline interpolation or resetting it. */
	private JTextField nNodes;				/* Text field where the number of used nodes should be specified */
	
	/**
	 * Constructor that creates the layout.
	 */
	public ControlPanel() {
		super();
		setLayout(new FlowLayout());
		
		generate = new JButton("GENERATE");
		generate.setName("GENERATE");
		reset = new JButton("RESET");
		reset.setName("RESET");
		nNodes = new JTextField("Nº OF NODES");
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
	public void addListener(ActionListener al) {
		getGenerate().addActionListener(al);
		getReset().addActionListener(al);
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
	
	

}
