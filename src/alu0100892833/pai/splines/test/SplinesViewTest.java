package alu0100892833.pai.splines.test;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.junit.Test;

import alu0100892833.pai.splines.SplinesInterpolationControl;
import alu0100892833.pai.splines.view.SplinesView;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import static java.awt.event.KeyEvent.*;

/**
 * AssertJ test class for the Splines View.
 * Testing the splines interpolation program.
 * @author Óscar Darias Plasencia
 * @since 15-5-2017
 */
public class SplinesViewTest extends AssertJSwingJUnitTestCase {
	private static final double HEIGHT_PROPORTION = 1.2;
	private static final double WIDTH_PROPORTION = 1.2;
	private static final Integer DECLARING_POINTS = 7;

	private FrameFixture frame;
	private SplinesView program;

	@Override
	protected void onSetUp() {
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		program = GuiActionRunner.execute(() -> new SplinesInterpolationControl(SplinesInterpolationControl.STANDALONE, size).getFrame());
		frame = new FrameFixture(robot(), program);
		frame.show();	
	}
	
	/**
	 * Tests the correct execution.
	 */
	@Test 
	public void testRun() {
		frame.panel("CONTROL").button("GENERATE").click();
		assertTrue(program.getSplinePanel().getSplinesModel().getControlPoints().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getGraphic().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getnNodes() == 0);
		
		frame.panel("CONTROL").textBox("NODES-NUMBER").enterText(DECLARING_POINTS.toString());
		frame.panel("CONTROL").button("GENERATE").click();
		assertTrue(!program.getSplinePanel().getSplinesModel().getControlPoints().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getControlPoints().size() == DECLARING_POINTS);
		assertTrue(!program.getSplinePanel().getSplinesModel().getGraphic().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getnNodes() != 0);
	}
	
	/**
	 * Tests that reset works correctly.
	 */
	@Test
	public void testReset() {
		frame.panel("CONTROL").textBox("NODES-NUMBER").enterText(DECLARING_POINTS.toString());
		frame.panel("CONTROL").button("GENERATE").click();
		assertTrue(!program.getSplinePanel().getSplinesModel().getControlPoints().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getControlPoints().size() == DECLARING_POINTS);
		assertTrue(!program.getSplinePanel().getSplinesModel().getGraphic().isEmpty());
		frame.panel("CONTROL").button("RESET").click();
		assertTrue(program.getSplinePanel().getSplinesModel().getControlPoints().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getGraphic().isEmpty());
		assertTrue(program.getSplinePanel().getSplinesModel().getnNodes() == 0);
	}
	
	/**
	 * Tests information frame opens.
	 */
	@Test
	public void testInfoFrame() {
		frame.panel("CONTROL").label("INFOLABEL").click();
		assertTrue(program.getControlPanel().getInformationFrame().isVisible());
	}
	
	/**
	 * Tests keyboard input.
	 */
	@Test
	public void testKeyboardInput() {
		frame.panel("CONTROL").textBox("NODES-NUMBER").enterText(DECLARING_POINTS.toString());
		frame.panel("CONTROL").button("GENERATE").click();
		frame.panel("SPLINE-PANEL").pressAndReleaseKeys(VK_G);
		assertTrue(program.getSplinePanel().getSelectedNode() == 1);
		frame.panel("SPLINE-PANEL").pressAndReleaseKeys(VK_G);
		assertTrue(program.getSplinePanel().getSelectedNode() == 2);
		frame.panel("SPLINE-PANEL").pressAndReleaseKeys(VK_F);
		assertTrue(program.getSplinePanel().getSelectedNode() == 1);
	}

}


















// END