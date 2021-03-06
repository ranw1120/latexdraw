package net.sf.latexdraw.res2po;
import java.io.*;

/**
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * @author Arnaud BLOUIN
 */
public class Res2Pot {
	public static void main(final String[] args) throws IOException {
		if(args.length<2)
			throw new IllegalArgumentException();
		
		generatePots(new File(args[0]), new File(args[1]));
	}

	
	public static void generatePots(final File baseDir, final File outputDir) throws IOException {
		FileFilter filter = new PropertiesFilter();
		File[] filesBase = baseDir.listFiles(filter);
		
		for(int i=0; i<filesBase.length; i++)
			try(
				FileWriter fwPOT		= new FileWriter(new File(outputDir+File.separator+
											filesBase[i].getPath().substring(filesBase[i].getPath().lastIndexOf(File.separator), 
											filesBase[i].getPath().lastIndexOf(".")) + ".pot"));
				BufferedWriter bwPOT	= new BufferedWriter(fwPOT);
				FileReader flB 		= new FileReader(filesBase[i]);
				BufferedReader brB 	= new BufferedReader(flB);) {
					 Res2Po.generatePot(brB, bwPOT);
				 }
	}
}
