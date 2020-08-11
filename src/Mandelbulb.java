import static java.lang.System.*;
import static java.lang.Math.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import FastMath.FastMath;
import ThreeDimensional.Vector;

//not working depending on viewer location NEED FIX
public class Mandelbulb extends object 
{
	boolean colorme = false;
	Vector lastNormal = null;
	Vector center;
	int n = 8;
	double size;
	double normalOff = 0.00005; //number of pixels off it will be
	int alternatingSavingNormal = -1;
	
	int iterations = 5;
	int baseDivision = 150;
	int secondaryDivision = 12;
	int numtimes = 5;
	int numNormals = 4;
	double stepback = 0.4;
	
	int xangle=-45,zangle=0;
	double xcos,xsin,zcos,zsin;
	
	public Mandelbulb(double x, double y, double z, double siize, material m)
	{
		this(x,y,z,siize);
		mat=m;
	}
	public Mandelbulb(double x, double y, double z, double siize)
	{
		super("mandelbulb");
		center = new Vector(x,y,z);
		size=siize;
		xcos = cos(toRadians(xangle)); xsin = sin(toRadians(xangle));
		zcos = cos(toRadians(zangle)); zsin = sin(toRadians(zangle));
	//	out.println("mandelbulb dirextra: " + dirextra);
	}
	
	
	public Vector getNormal(Vector loc) 
	{
	//	out.println(lastNormal);
		return lastNormal;
		//return new Vector(0,0,1); //TEMP TEMP
	}
	
	public double getDistIntersectionLine(Vector loc, Vector dir) 
	{
		boolean primTrace = alternatingSavingNormal==-1;
		if(primTrace) // -1 prim tracing, +1 light tracing
			lastNormal = null;
		
		double[] tempminmax = estimatedMinMax(loc,dir);
		if(tempminmax==null) return 0;
		
		double min = tempminmax[0];
		double max = tempminmax[1];
		
		double t = loopit(loc,dir,min,max,numtimes);
		
		//***** ESTIMATING NORMAL
		if(t!=0)
		{
			Vector hit = Vector.multiply(dir, t); hit.add(loc);
			Vector[] offdir = new Vector[numNormals];
			double pdscale = raycaster.pd/dir.y;
			for(int i=0; i<numNormals; i++)
				offdir[i] = Vector.multiply(dir,pdscale);
			for(int i =0;i<numNormals;i++)
			{
				switch(i){
				case 0: offdir[0].add(normalOff,0,0); break;
				case 1: offdir[1].add(0,0,normalOff); break;
				case 2: offdir[2].add(-normalOff,0,0); break;
				case 3: offdir[3].add(0,0,-normalOff); break;
				}
			}
			
			for(int i=0; i <numNormals;i++) offdir[i].normalize();
			double[] offt = new double[numNormals];
			for(int i = 0; i < numNormals; i++)
				offt[i] = loopit(loc,offdir[i],min,max,numtimes);
			Vector[] offhit = new Vector[numNormals];
			for(int i=0;i<numNormals;i++)
				offhit[i] = add( loc, multiply(offdir[i],offt[i]) );
			Vector[] normals = new Vector[numNormals];
			if(primTrace)
			{
				normals[numNormals-1] = FastMath.getNormal(hit,offhit[0],offhit[numNormals-1]);
				for(int i=0; i<numNormals-1;i++)
				{
					normals[i]= FastMath.getNormal(hit,offhit[i],offhit[i+1]);
				}
				lastNormal = new Vector(0,0,0);
				for(int i=0;i<numNormals;i++)
					lastNormal.add(normals[i]);
				lastNormal.normalize();
				alternatingSavingNormal = 1;
			}
			else 
				alternatingSavingNormal = -1;
		}
		if(t==0)
			alternatingSavingNormal = -1;
		
		return t;
	}
	
	public double loopit(Vector loc,Vector dir, double min, double max, int times)
	{
		int division;
		if(times == numtimes) division = baseDivision;
		else division = secondaryDivision;
		
		double increment = (max-min)/division;
		for(int count = 0; count<=division; count++)
		{
			double sc = min + increment*count;
			if(contains(loc,Vector.multiply(dir,sc))) 
			{
				if(times>0)
					return loopit(loc,dir,  sc-increment*(division*stepback),  sc,times-1);
				if(times == 0)
					return sc+increment/2;
			}
		}
		if(times!=numtimes)
			out.println("problem loopit");
		
		return 0;
	}
	
	
	public boolean contains(Vector camloc , Vector loc)
	{
		//POSSIBLE PROBLEM HERE
		// location needs to be relative to the CENTER OF BULB in the end, SHADOW PROBLEMS CAUSED HERE
		Vector relativeLoc = Vector.subtract(center,camloc);
		relativeLoc.subtract(loc);
		relativeLoc.divide(size);
		
		relativeLoc.rotate(xcos, xsin, zcos, zsin);
		
		double x = relativeLoc.x,y=relativeLoc.y,z=relativeLoc.z;
//		out.println(x+ " " + y + " " + z);
		double ox=x,oy=y,oz=z;
//		
		for(int i = 0; i<iterations; i++)
		{
			double r = sqrt(x*x+y*y+z*z);
			double rn = pow(r,n);
			
	//		double theta = atan(y/x);
			double theta = atan2(y,x);		
			double phi =  asin(z/r);
			
			x = rn*cos(n*theta)*cos(n*phi);
			y = rn*sin(n*theta)*cos(n*phi);
			z = rn*sin(n*phi);  
			//CHANGES FROM ACTUAL EQUATION
			z*=-1; //IMPORTANT MAKING NEGATIVE 
		
			x+=ox;y+=oy;z+=oz;
			if(x*x+y*y+z*z>4) return false; // > 4 maybe add on some extra to be safe
		}
		
		return true;
	}
	
	public double[] estimatedMinMax(Vector loc, Vector dir)
	{
		Vector relativeLoc = Vector.subtract(center,loc);
		double extramult = 1.1; //error comes if is 1, too lazy to find out, assumming just need extra cause of rounding errors or something
		double[] minmax = new double[2];
		double b = Vector.dot(dir,relativeLoc);
		double ac = Vector.dot(relativeLoc,relativeLoc) - size*extramult*size*extramult; // 2 still cuts a little off, not sure why i check to see if radius^2 is greater than 4 to see if its in the set
		
		double discriminant = b*b - ac; 
		if(discriminant < 0 ) return null;
		
		double root = Math.sqrt(discriminant);
		
		minmax[0] = b-root;
		minmax[1] = b+root;

		return minmax;
	}
	
	public float[] getPatchRGB(Vector loc) {return null;}
	public float[] getTextureRGB(Vector loc) {		
		return null;
	}
	public float[] getRGB(Vector loc)
	{
		if(!colorme)
			return super.getRGB(loc);
		float div = 1.5f;
		Vector rel = Vector.subtract(loc,center);
		float r = (float)(abs(rel.x)/size)/div + 1.0f-(1/div);
		float g = (float)(abs(rel.y)/size)/div + 1.0f-(1/div);
		float b = (float)(abs(rel.z)/size)/div + 1.0f-(1/div);
		return new float[] {r,g,b};
	}
}
