import static java.lang.System.*;
import static java.lang.Math.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
public class AreaLight extends Light 
{
	Vector[] locs;
	public AreaLight(double x, double y, double z,double vx, double vy, double vz,double ux, double uy, double uz, int sets,double lumin){this(new Vector(x,y,z),new Vector(vx,vy,vz),new Vector(ux,uy,uz),sets,lumin);}
	public AreaLight(double x, double y, double z,double vx, double vy, double vz,double ux, double uy, double uz, double lumin){this(new Vector(x,y,z),new Vector(vx,vy,vz),new Vector(ux,uy,uz),lumin);}
	public AreaLight(Vector cent, Vector v, Vector u, double lumin){
		this(cent,v,u,1,lumin);
	}
	// only working as 2D SQUARE light
	public AreaLight(Vector cent, Vector v, Vector u, int sets, double lumin)
	{
		super(lumin);
		double side = v.x+v.y+v.z; //TEMPORARY
		locs = new Vector[sets*4+1];
		locs[0] = cent;
		
		double increase = side/(double)sets;
		double previous=0;
		for(int i = 0 ; i <sets; i++)
		{
			// cant increase at linear scale in rectangle, need increase so AREA INCREASE remains constant, not side length increse
			double newside = sqrt(previous*previous + increase*increase);
			previous = newside;
			double num = newside/side; 
			
			Vector vs = new Vector(v.x,v.y,v.z); Vector us = new Vector(u.x,u.y,u.z);
			
			vs.scale(num); us.scale(num); 			
			locs[i*4+1] = Vector.add(vs,us);
			locs[i*4+2] = Vector.add(vs,Vector.negative(us));
			locs[i*4+3] = Vector.add(Vector.negative(vs),us);
			locs[i*4+4] = Vector.add(Vector.negative(vs),Vector.negative(us));
			
			double angle = Math.toRadians(90.0*Math.random());
			for(int q=i*4+1; q<=i*4+4; q++)
			{
				//no y rotation 
				if(u.x-v.x==0)
					locs[q].rotateExact(angle,0);
				else if(u.z-v.z==0)
					locs[q].rotateExact(0,angle);
				locs[q].add(cent);
			}
		}
	}
	
	public Vector[] getLocs()
	{
		return locs;
	}
	public void move(Vector m)
	{
		for(Vector v: locs)
			v.add(m);
	}
}
