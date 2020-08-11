import java.awt.geom.Point2D;

import ThreeDimensional.Vector;


public class window 
{
	double cx, cy;
	double width;
	double height;
	
	public window(double cxx, double cyy, double w, double h)
	{
		cx=cxx;cy=cyy; width=w;height=h;
	}
	public boolean inWindow(double u, double w)
	{
		if(u>cx-width && u<cx+width && w>cy-height && w<cy+height) return true;
		return false;
	}
}
