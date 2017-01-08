package test.views.jfx;

import java.util.Arrays;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewSingleShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

abstract class TestViewSingleShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> {
	protected T view;
	protected S model;
	protected R border;

	protected abstract S createModel();

	private T createView() {
		return (T) ViewFactory.INSTANCE.createView(createModel()).get();
	}

	@Before
	public void setUp() {
		BadaboomCollector.INSTANCE.clear();
		view = createView();
		model = view.getModel();
		border = view.getBorder();
	}

	@Test
	public void testLineColor() {
		model.setLineColour(DviPsColors.BITTERSWEET);
		assertEquals(DviPsColors.BITTERSWEET, ShapeFactory.INST.createColorFX((javafx.scene.paint.Color) border.getStroke()));
	}

	@Test
	public void testLineThickness() {
		if(model.isThicknessable()) {
			model.setThickness(10d);
			assertEquals(10d, border.getStrokeWidth(), 0.00d);
		}
	}

	@Test
	public void testDoubleLineThickness() {
		if(model.isThicknessable()) {
			model.setHasDbleBord(true);
			assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.00d);
		}
	}

	@Test
	public void testLineStylePlainLineCap() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.SOLID);
			assertEquals(StrokeLineCap.SQUARE, border.getStrokeLineCap());
		}
	}

	@Test
	public void testLineStyleDashedSep() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(model.getDashSepBlack(), model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDashedLineCap() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(StrokeLineCap.BUTT, border.getStrokeLineCap());
		}
	}

	@Test
	public void testDashSepBlackBefore() {
		if(model.isLineStylable()) {
			model.setDashSepBlack(21d);
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepBlack() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			model.setDashSepBlack(21d);
			assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepWhite() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			model.setDashSepWhite(2.451);
			assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepWhiteBefore() {
		if(model.isLineStylable()) {
			model.setDashSepWhite(2.451);
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedLineCap() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(StrokeLineCap.ROUND, border.getStrokeLineCap());
		}
	}

	@Test
	public void testLineStyleDottedSepSimpleLine() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepDoubleLine() {
		if(model.isLineStylable()) {
			if(model.isDbleBorderable()) {
				model.setHasDbleBord(true);
			}
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepDoubleLineAfterLineStyle() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			if(model.isDbleBorderable()) {
				model.setHasDbleBord(true);
			}
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepDoubleLineDoubleSep() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			if(model.isDbleBorderable()) {
				model.setHasDbleBord(true);
				model.setDbleBordSep(33d);
			}
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}


	@Test
	public void testLineStyleDottedSepBefore() {
		if(model.isLineStylable()) {
			model.setDotSep(23.23);
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepAfter() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			model.setDotSep(23.23);
			assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
		}
	}
}