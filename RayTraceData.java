import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
public class RayTraceData 
{
	Vector loc;
	Vector dir;
	object objectHit;
	public RayTraceData(Vector l, Vector d, object o)
	{
		loc=l;dir=d;objectHit=o;
	}

}
