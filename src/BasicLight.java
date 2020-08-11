import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
public class BasicLight extends Light 
{
	Vector loc;
	public BasicLight(double x, double y, double z, double lumin ){this(new Vector(x,y,z),lumin);}
	public BasicLight(Vector location, double lumin)
	{
		super(lumin);
		loc=location;	
	}
	public Vector[] getLocs(){return new Vector[]{loc}; }
	public void move(Vector move){
		loc.add(move);
	}

}
