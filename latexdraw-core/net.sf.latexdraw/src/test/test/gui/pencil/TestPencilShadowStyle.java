package test.gui.pencil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeShadowCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestShadowStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilShadowStyle extends TestShadowStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeShadowCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectShadowCBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		boolean sel = shadowCB.isSelected();
		checkShadow.execute();
		assertEquals(!sel, pencil.createShapeInstance().hasShadow());
		assertNotEquals(sel, shadowCB.isSelected());
	}

	@Test
	public void testPickShadowColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, checkShadow, updateIns).execute();
		Color col = shadowColB.getValue();
		pickShadCol.execute();
		assertEquals(shadowColB.getValue(), pencil.createShapeInstance().getShadowCol().toJFX());
		assertNotEquals(col, shadowColB.getValue());
	}

	@Test
	public void testIncrementShadowSizePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, checkShadow, updateIns).execute();
		double val = shadowSizeField.getValue();
		incrementshadowSizeField.execute();
		assertEquals(shadowSizeField.getValue(), pencil.createShapeInstance().getShadowSize(), 0.0001);
		assertNotEquals(val, shadowSizeField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementShadowAnglePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, checkShadow, updateIns).execute();
		double val = shadowAngleField.getValue();
		incrementshadowAngleField.execute();
		assertEquals(Math.toRadians(shadowAngleField.getValue()), pencil.createShapeInstance().getShadowAngle(), 0.0001);
		assertNotEquals(val, shadowAngleField.getValue(), 0.0001);
	}
}
