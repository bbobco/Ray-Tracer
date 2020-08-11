import static java.lang.Math.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
import static java.lang.System.*;
public class NormalMap
{
	double intensity = 1;
	double scale = 0.1;
	String xm,ym;
	public NormalMap(){}
	public NormalMap(double intense)
	{
		intensity = intense;
	}
	public NormalMap(String xmod, String ymod)
	{
		xm=xmod;ym=ymod;
	}
	public Vector getNormal(Vector loc,Vector norm, Vector tangent1, Vector tangent2)
	{
		Vector normal = new Vector(norm.x,norm.y,norm.z);
		double x = Vector.dot(loc,tangent1);
		double y = Vector.dot(loc,tangent2);

		double xmod = (cos(x*scale)) * intensity;
		double ymod = (cos(y*scale)) * intensity;
		
		normal.scale(1.0-abs(xmod)); normal.add(Vector.multiply(tangent1,xmod));
		normal.normalize();
		normal.scale(1.0-abs(ymod)); normal.add(Vector.multiply(tangent2,ymod));
		normal.normalize();
		
		return normal;
	}
}
