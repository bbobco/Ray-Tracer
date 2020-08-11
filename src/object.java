import static java.lang.System.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;

import java.util.ArrayList;

abstract class object
{
	material mat = material.basic;
	boolean reflects = false;
	boolean refracts = false;
	double indexRefraction;
	String type;
	float patchSpace;
	
	public object(String objtype){type=objtype;}
	
	public material getMaterial(){return mat;}
	public float getSpecular(){return mat.getSpecular();}
	public float getShine(){return mat.getShine();}
	public boolean getReflects(){return reflects;}
	public String getType(){return type;}
	public Vector getReflectedDir(Vector origdir){return null;}
	public boolean getGloss(){return mat.gloss;}
	public abstract Vector getNormal(Vector loc);
	public abstract double getDistIntersectionLine(Vector loc, Vector dir);
	
	public abstract float[] getPatchRGB(Vector loc);
	
	public float[] getRGB(Vector loc)
	{
		if(mat.texture==null)
			return mat.getRGB();
		return getTextureRGB(loc);
	}
	public abstract float[] getTextureRGB(Vector loc);
	
	public Vector getReflectedDir(Vector loc, Vector origdir)
	{
		Vector normal = getNormal(loc);
		double scalar = 2 * Vector.dot(origdir, normal);	
		
		Vector rdir = new Vector(normal.x,normal.y,normal.z);
		rdir.scale(scalar);
		rdir = Vector.subtract(origdir, rdir);
		return rdir;
	}
	
	public Vector getRefractedDir(Vector loc, Vector i, double n1, double n2)
	{
		//everything needs to be normalized
		// tperpMag sign change changes from reflection to refraction etc. negative for refraction 
		Vector n = getNormal(loc);
		Vector incidentScaled = Vector.multiply(i,(n1/n2));
		double icos = Vector.dot(i,n);
		Vector iperp = Vector.multiply(n, n1/n2 * icos);
		Vector tpara = Vector.subtract(incidentScaled , iperp);
		
		double tperpMag = -Math.sqrt( 1.0 - Math.pow(tpara.mag() , 2));
		Vector tperp = Vector.multiply(n, tperpMag );
		Vector t = Vector.add(tpara, tperp);
		
		return t;
	}
	public Vector getPointIntersectionLine(Vector loc, Vector dir)
	{
		double t = getDistIntersectionLine(loc,dir);
		if(t==0) return null; //not necessarily 0, returns 0 if denom is 0 as well .. in below method
		if(t<0) return null;
		
		Vector v =  Vector.add(loc, Vector.multiply(dir, t));
	//	dir.mag=t; //mag problem
		return v;
	}	
	
}