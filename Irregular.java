import static java.lang.Math.*;
import static java.lang.System.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
public class Irregular extends object 
{
	// z = -1 / (x^2 + y^2)
	Vector center;
	public Irregular(double x, double y, double z, material mater){this(x,y,z);mat=mater;}
	public Irregular(double x, double y, double z)
	{
		super("irregular");
		center = new Vector(x,y,z);
	}


	public Vector getNormal(Vector loc) 
	{
		//TEMP NOT CORRECT
		return new Vector(0,0,1);
	}


	public Vector getPointIntersectionLine(Vector loc, Vector dir) 
	{
		double t = getDistIntersectionLine(loc,dir);
		if(t==0) return null;
		
		Vector v = Vector.add(loc, Vector.multiply(dir, t));
		v.mag = t;
		return v;
	}


	public double getDistIntersectionLine(Vector loc, Vector dir) 
	{
		
		Vector temp = Vector.subtract(center,loc); //relative to the location
		double x=dir.x; double y=dir.y; double z=dir.z;
		double xo =temp.x; double yo=temp.y; double zo =temp.z;
		
		double a = x*x*z + y*y*z;
		double b = x*x*zo + y*y*zo + 2*xo*x*z + 2*yo*y*z;
		double c = xo*xo*z + yo*yo*z +2*xo*x*zo +2*yo*y*zo;
		double d = xo*xo*zo + yo*yo*zo + 1;
		
		double val1 = 2*b*b*b - 9*a*b*c + 27*a*a*d;
		double val2 = val1*val1 - 4*pow((b*b-3*a*c),3);
		if(val2<0) return 0;
		val2=sqrt(val2);
		
		
		double t =  pow(	(   1 /
		//			----------------
					 (z*(x*x+y*y))  ) , 1.0/3.0);	
		
		out.println(t);
		return t;	
	}


	public float[] getTextureRGB(Vector loc) 
	{
		
		return null;
	}
	public float[] getPatchRGB(Vector loc)
	{
		return null;
	}
}
