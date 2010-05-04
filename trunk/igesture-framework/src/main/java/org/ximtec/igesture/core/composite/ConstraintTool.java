/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.awt.geom.Rectangle2D;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.sigtec.ink.Note;
import org.sigtec.ink.NoteTool;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class ConstraintTool {

	public static boolean isBoundsDiagonalValid(List<Note> notes, double minDistance, double maxDistance)
	{
		boolean valid = true;
		Rectangle2D bounds = NoteTool.getBounds2D(notes);
		double w = bounds.getWidth();
		double h = bounds.getHeight();
		double d = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
		if(d < minDistance || d > maxDistance)
			valid = false;
		return valid;
	}
	
	public static long calculateTimeInMillis(Calendar time)
	{
		long t = 0;
		t += time.get(Calendar.MILLISECOND);
		t += time.get(Calendar.SECOND)*1000;
		t += time.get(Calendar.MINUTE)*60*1000;
		t += time.get(Calendar.HOUR)*24*60*1000;	
		return t;
	}
	
	public static void permute(int level, String permuted, boolean used[], String original, Set<String> results) 
	{
		int length = original.length();
		if (level == length) 
		{
		    results.add(permuted);
		} 
		else 
		{
		    for (int i = 0; i < length; i++) 
		    {
		        if (!used[i]) {
		            used[i] = true;
		            permute(level + 1, permuted + original.charAt(i), used, original, results);
		            used[i] = false;
		        }
		    }
		}
	}
}
