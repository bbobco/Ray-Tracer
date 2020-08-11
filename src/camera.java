import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
import static java.lang.Math.*;
class camera
{
	static Vector loc = new Vector();
	static Vector dir = new Vector(0,1,0);
	static Vector defaultdir = new Vector(0,1,0);
	static double perspectiveDistance = world.swidth/2; // distance from camera to virtual screen 
	static double xcos=1,xsin=0,zcos=1,zsin=0;
		


	public static void setdir(double x, double y , double z)
	{
		dir.x=x;dir.y=y;dir.z=z; dir.normalize();
		Vector xdir = new Vector(1,0,0);
		Vector zdir = new Vector(0,0,1);
		
		xcos = xdir.x * dir.x;  if(dir.x==defaultdir.x) xcos=1;
		zcos = zdir.z * dir.z; if(dir.z==defaultdir.z) zcos=1;
		xsin = sqrt(1-xcos*xcos);
		zsin = sqrt(1-zcos*zcos);
	
	}
	
	public static Vector getdir(){return dir;}
	public static void setloc(double x, double y, double z){loc.x=x; loc.y=y; loc.z=z;}
	public static void move(Vector move){loc.add(move);}
}