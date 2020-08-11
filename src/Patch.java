import static java.lang.System.*;
import static java.lang.Math.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;

public class Patch
{
	static float area =1000;
	static float defaultSpace = 50;
	float space = defaultSpace;
	static int rows = (int)(area/defaultSpace);
	float diagonalDistance =  (float)sqrt((space)*(space) + (space)*(space));
//	static float diagonalDistance =  (float)sqrt((space/2)*(space/2) + (space/2)*(space/2));
	static float scale = raycaster.lscale;
	
	float[] rgb = new float[3]; // rgb at its location
	float[] radianceRGB = new float[3]; // gives to other objects
	float[] illuminatedRGB = new float[3]; //recieved from other objects
	Vector loc;
	float u,w;
	
	public Patch(Vector location, float uu, float ww, float customSpace)
	{
		this(location,uu,ww);
		space=customSpace;
	}

	public Patch(Vector location, float uu, float ww)
	{
		loc = location; u=uu;w=ww;
	//	out.println(u+" " + w);
	}
	public boolean isPatch(double tu, double tw)
	{
		if(abs(u-tu) < 1 && abs(w-tw) < 1) return true;
		return false;
	}
	public String toString()
	{
		return loc.toString();
	}
}
