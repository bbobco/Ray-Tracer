import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
public class RefractingSphere extends sphere 
{
	public RefractingSphere(double x, double y, double z, double radius, double ir)
	{
		super(x,y,z,radius);
		refracts = true;
		indexRefraction = ir;
	}
	
	public Vector getRefractedDir(Vector loc, Vector i, double n1, double n2)
	{
		// not good, this is assuming n1 is always 1.0 as given in raycaster. cant have refracting sphere within a refracting sphere, or something similar
		if(exiting(loc,i)){double temp = n1; n1=n2; n2=temp;}
		return super.getRefractedDir(loc,i,n1,n2);
	}
}
