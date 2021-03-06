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
package net.sf.latexdraw.actions;

import javafx.scene.layout.Pane;
import org.malai.action.Action;

/**
 * This trait encapsulates the template menu.
 * @author Arnaud Blouin
 */
public interface TemplateAction extends Action {
	/**
	 * @param pane The pane that contains the templates.
	 */
	void setTemplatesPane(final Pane pane);

	Pane getTemplatesPane();

	@Override
	default boolean canDo() {
		return getTemplatesPane() != null;
	}
}