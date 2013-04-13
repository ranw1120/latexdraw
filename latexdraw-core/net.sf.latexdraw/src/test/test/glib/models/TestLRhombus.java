package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIRhombus;

public class TestLRhombus<T extends IRhombus> extends TestIRhombus<T> {
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createRhombus(false);
		shape2 = (T) DrawingTK.getFactory().createRhombus(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IRhombus.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		IRhombus rho = DrawingTK.getFactory().createRhombus(false);

		assertEquals(4, rho.getNbPoints());
		HelperTest.assertEqualsDouble(rho.getPtAt(0).getY(), rho.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(rho.getPtAt(1).getX(), rho.getPtAt(3).getX());

		rho = DrawingTK.getFactory().createRhombus(DrawingTK.getFactory().createPoint(), 20, 40, true);

		HelperTest.assertEqualsDouble(4, rho.getNbPoints());
		HelperTest.assertEqualsDouble(rho.getPtAt(0).getY(), rho.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(rho.getPtAt(1).getX(), rho.getPtAt(3).getX());
		HelperTest.assertEqualsDouble(20., rho.getPtAt(2).getX()-rho.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(40., rho.getPtAt(3).getY()-rho.getPtAt(1).getY());

		try {
			rho = DrawingTK.getFactory().createRhombus(null, 10, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			rho = DrawingTK.getFactory().createRhombus(DrawingTK.getFactory().createPoint(Double.NaN, 0), 10, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			rho = DrawingTK.getFactory().createRhombus(DrawingTK.getFactory().createPoint(1, 1), -10, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			rho = DrawingTK.getFactory().createRhombus(DrawingTK.getFactory().createPoint(1, 1), 10, -10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
	}
}
