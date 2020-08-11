import static java.lang.Math.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
import static java.lang.System.*;

public class sphere extends object 
{
	Vector center;
	double r;
	
	public sphere(double x, double y, double z, double radius )
	{
		super("sphere");
		center = new Vector(x,y,z); r=radius;
	}
	public sphere(double x, double y, double z, double radius, material m )
	{this(x,y,z,radius);mat=m;}
	

	public double getDistIntersectionLine(Vector loc, Vector dir) //returns 0 if no interection
	{
		//this is simplified, so not actual abc of quadratic equation
		//assuming line begins at (0,0,0)
		
		Vector tempcenter = Vector.subtract(center, loc);
		
		//EXTRA HERE IMPORTANT : could cause shadow problems, if statement below as well
		double extra =	raycaster.extra*2; //more math operations done here, so gonna raise extra
	//	boolean locInside = fastmath.getVectorMagnitude(tempcenter) <= (r + extra);
		if(entering(loc,dir) && !refracts) return raycaster.extra+raycaster.extra/4.0; //if the direction of ray is similar to direction of vector from location to center, it is trying to travel through the sphere
		
		double b = Vector.dot(dir,tempcenter);
		double ac = Vector.dot(tempcenter,tempcenter) - r*r;
		
		double discriminant = b*b - ac; 
		if(discriminant < 0 ) return 0;
		
		double root = Math.sqrt(discriminant);
		double d = b + root;
		if((b-root) < (d)) // abs(b-root) and abs(d)
			return b-root;

		return d;					
	}
	

	
	public boolean isInside(Vector loc)
	{
		Vector tempCenter = Vector.subtract(center,loc);
		if(tempCenter.mag() < r+raycaster.extra)
			return true;
		return false;
	}
	public boolean entering(Vector loc, Vector dir)
	{
		if(!isInside(loc)) return false;
		if(Vector.dot(dir,getNormal(loc)) < 0) return true;
		return false;
	}
	public boolean exiting(Vector loc, Vector dir)
	{
		if(!isInside(loc)) return false;
		if(Vector.dot(dir,getNormal(loc)) > 0) return true;
		return false;
	}
	
	public Vector getNormal(Vector loc)
	{
		Vector n = Vector.subtract(loc , center);
		n.normalize();
		return n;
	}
	public void move(Vector move){
		center.add(move);
	}
	
	public float[] getTextureRGB(Vector loc)
	{
		return null; //TEMPORARY
	}
	public float[] getPatchRGB(Vector loc)
	{
		return null;
	}
	
}
