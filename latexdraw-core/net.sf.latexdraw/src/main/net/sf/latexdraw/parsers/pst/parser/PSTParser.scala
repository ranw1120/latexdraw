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
package net.sf.latexdraw.parsers.pst.parser

import java.text.ParseException

import net.sf.latexdraw.models.interfaces.shape.IGroup

import scala.collection.mutable.ListBuffer

/**
 * Defines a PST parser.
 * @author Arnaud BLOUIN
 */
class PSTParser extends PSTAbstractParser with PSTCodeParser {
	@throws(classOf[ParseException])
	def parsePSTCode(content : String) : Option[IGroup] = {
		val tokens = new lexical.Scanner("{\n" + content + "\n}\n")
		val result = phrase(parsePSTCode(new PSTContext(false)))(tokens)

		PSTParser._errorLogs.foreach{msg => println(msg)}

		result match {
			case Success(tree, _) =>
				if(tree.size==1 && tree.getShapeAt(0).isInstanceOf[IGroup])
					Some(tree.getShapeAt(0).asInstanceOf[IGroup])
				else Some(tree)
			case e: NoSuccess => throw new ParseException(result.toString, -1)
		}
	}
}


/**
 * Companion object of the PST parser used to encapsulate shared elements.
 */
object PSTParser {
	protected val _errorLogs = ListBuffer[String]()

	/**
	 * Adds the given message to the error loggers.
	 */
	def errorLogs_+=(msg : String) {
		if(_errorLogs!=null)
			_errorLogs += msg
	}


	/** The error logger. */
	def errorLogs = _errorLogs

	/**
	 * Cleans the parsing logs.
	 */
	def cleanErrors() {
		_errorLogs.clear
	}
}
