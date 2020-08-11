import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
public class RefractingPlane extends plane 
{
	public RefractingPlane(Vector p, Vector q, Vector r, double ir, material m){this(p,q,r, ir);mat = m;}
	public RefractingPlane(double x1,double y1,double z1, double x2, double y2, double z2, double x3, double y3, double z3,double ir, material m){super(x1,y1,z1,x2,y2,z2,x3,y3,z3);indexRefraction=ir;mat=m;}
	public RefractingPlane(double x1,double y1,double z1, double x2, double y2, double z2, double x3, double y3, double z3, double ir){super(x1,y1,z1,x2,y2,z2,x3,y3,z3); indexRefraction = ir;}
	public RefractingPlane(Vector p, Vector q, Vector r, double ir)
	{
		super(p,q,r,null,-1,-1);
		refracts = true;
		indexRefraction = ir;
	}
	public Vector getRefractedDir(Vector i, Vector loc)
	{
		return this.getRefractedDir(i,loc, 1 , indexRefraction);
	}
}
