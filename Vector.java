import static java.lang.Math.*;
import static java.lang.System.*;

public class Vector 
{
	double x,y,z;
	double mag = -1;
	
	public Vector(){x=0;y=0;z=0;}
	public Vector(double xx, double yy, double zz)
	{
		x=xx;y=yy;z=zz;
	}
	public void normalize()
	{
		mag = sqrt(x*x+y*y+z*z);
		x/=mag;y/=mag;z/=mag;
	}
	public void scale(double scalar)
	{
		x*=scalar;y*=scalar;z*=scalar;
	}
	public void divide(double scalar)
	{
		x/=scalar;y/=scalar;z/=scalar;
	}
	public void add(Vector u){
		x+=u.x;y+=u.y;z+=u.z;
	}
	public void subtract(Vector u){
		x-=u.x;y-=u.y;z-=u.z;
	}
	public double mag()
	{
		if(mag!=-1)return mag;
		mag = sqrt(x*x+y*y+z*z);
		return mag;
	}
	public static double dot(Vector v, Vector u)
	{
		return v.x*u.x + v.y*u.y + v.z*u.z;
	}
	public static Vector cross(Vector v, Vector u)
	{
		double tempx = v.y*u.z-v.z*u.y;    //A
		double tempy = v.z*u.x-v.x*u.z;    //B
		double tempz = v.x*u.y-v.y*u.x;	   //C	
		return new Vector(tempx,tempy,tempz);
	}
	public double ch(char c)
	{
		if(c=='x') return x;
		if(c=='y') return y;
		if(c=='z') return z;
		else{out.println("Vector.ch() error not x,y, or z@!#@!"); return -568;} //shouldnt ever be here
	}
	public static Vector getDirection(Vector start, Vector end)
	{
		return new Vector(end.x-start.x,end.y-start.y,end.z-start.z);
	}
	public static Vector add(Vector[] vectors)
	{
		Vector w = new Vector();
		for(int i=0;i<vectors.length;i++)
			w.add(vectors[i]);
		return w;
	}
	public static Vector subtract(Vector[] vectors)
	{
		Vector w = new Vector();
		for(int i=0;i<vectors.length;i++)
			w.subtract(vectors[i]);
		return w;
	}
	public static Vector negative(Vector v)
	{
		return new Vector(-v.x, -v.y, -v.z);
	}
	public static Vector multiply(Vector v, double scalar)
	{
		return new Vector(v.x*scalar,v.y*scalar,v.z*scalar);
	}
	public static Vector divide(Vector v, double scalar)
	{
		return new Vector(v.x/scalar,v.y/scalar,v.z/scalar);
	}
	public static Vector rotateExact(Vector v, double xa, double za) //radians
	{
		double tempx=v.x,tempy=v.y,tempz=v.z;
		double x,y,z;
						
		x= tempx*Math.cos(za) - tempy*Math.sin(za);
		y= tempx*Math.sin(za) + tempy*Math.cos(za);	
		tempx=x;tempy=y;
		
		y= tempy*Math.cos(xa) - tempz*Math.sin(xa);
		z= tempy*Math.sin(xa) + tempz*Math.cos(xa);	
		
		return new Vector(x,y,z);
	}
	public void rotateExact(double xa, double za) //radians
	{
		double tempx=x,tempy=y,tempz=z;						
		x= tempx*Math.cos(za) - tempy*Math.sin(za);
		y= tempx*Math.sin(za) + tempy*Math.cos(za);	
		tempx=x;tempy=y;

		y= tempy*Math.cos(xa) - tempz*Math.sin(xa);
		z= tempy*Math.sin(xa) + tempz*Math.cos(xa);	
	}
	public static Vector add(Vector v, Vector u)
	{
		return new Vector(v.x+u.x,v.y+u.y,v.z+u.z);
	}
	public static Vector subtract(Vector v, Vector u)
	{
		return new Vector(v.x-u.x,v.y-u.y,v.z-u.z);
	}
	public String toString()
	{
		return "x: " + x + " y: " + y+ " z: " + z ;
	}

}
