/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.svg;

import java.awt.geom.Point2D;
import java.util.List;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.parsers.svg.AbstractPointsElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;

/**
 * An SVG generator for shapes composed of points.
 * @author Arnaud BLOUIN
 */
abstract class LModifiablePointsGenerator<S extends IModifiablePointsShape> extends LShapeSVGGenerator<S> {
	/**
	 * Creates a generator for IModifiablePointsShape.
	 * @param modShape The source shape used to generate the SVG element.
	 */
	protected LModifiablePointsGenerator(final S modShape) {
		super(modShape);
	}



	/**
	 * Sets the latexdraw polygon using the given SVG element that contains points.
	 * @param ape The source SVG element used to define the latexdraw shape.
	 * @since 3.0
	 */
	protected void setSVGModifiablePointsParameters(final AbstractPointsElement ape) {
		setSVGParameters(ape);
		final List<Point2D> ptsPol = ape.getPoints2D();

		if(ptsPol==null)
			throw new IllegalArgumentException();

		for(final Point2D pt : ptsPol)
			shape.addPoint(ShapeFactory.INST.createPoint(pt.getX(), pt.getY()));
	}




	/**
	 * Sets the points of the modifiable points shape using the given SVG element.
	 * @param elt The SVG element that contains the points to set.
	 * @since 3.0
	 */
	protected void initModifiablePointsShape(final SVGPathElement elt) {
		final SVGPathSegList segs = elt.getSegList();
		SVGPathSeg seg;
		final int size = segs.get(segs.size()-1) instanceof SVGPathSegClosePath ? segs.size()-1 : segs.size();
		int i;
		Point2D pt = new Point2D.Double();// Creating a point to support when the first path element is relative.

		for(i=0; i<size; i++) {
			seg = segs.get(i);

			if(!(seg instanceof SVGPathSegLineto))
				throw new IllegalArgumentException("The given SVG path element is not a polygon."); //$NON-NLS-1$

			pt = ((SVGPathSegLineto)seg).getPoint(pt);
			shape.addPoint(ShapeFactory.INST.createPoint(pt.getX(), pt.getY()));
		}

		setSVGParameters(elt);
		applyTransformations(elt);
	}
}
