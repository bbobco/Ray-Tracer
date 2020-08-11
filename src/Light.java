import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
abstract class Light
{
	String type;
	float[] rgb = {1,1,1};
	double luminosity;
	
	public Light(double lumin)
	{
		luminosity = lumin;
	}
	
	public abstract void move(Vector m);

	public void addLuminosity(double alum)
	{luminosity+=alum;}
	public abstract Vector[] getLocs();
	public float[] getRGB(){return rgb;}
	public void setRGB(float r, float g, float b){rgb[0]=r;rgb[1]=g;rgb[2]=b;}
	public double getLuminosity(){return luminosity;}
}