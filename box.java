import java.util.*;
import static java.lang.Math.*;
import static java.lang.System.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
class box extends object 
{
	ArrayList<plane> planes = new ArrayList<plane>();
	private int lastIndex=-1;
	private int everyOtherUsedForLastIndexBecauseLightingCallsRayTraceMethod = 1;
	Vector start = new Vector();
	Vector size = new Vector();
	
	public box(double x, double y, double z, double vx, double vy, double vz, double ux, double uy, double uz, double s,material m){this(new Vector(x,y,z),new Vector(vx,vy,vz),new Vector(ux,uy,uz),s,m);}	
	public box(double x, double y, double z, double vx, double vy, double vz, double ux, double uy, double uz, double s){this(new Vector(x,y,z),new Vector(vx,vy,vz),new Vector(ux,uy,uz),s,material.basic);}
	public box(Vector loc,Vector v, Vector u, double s){this(loc,v,u,s,material.basic);}
	public box(Vector loc,Vector v, Vector u, double s, material m)
	{
		super("box");
		mat = m;
		start=loc;
		
		Vector w = Vector.cross(v,u);
		w.normalize();
		w.scale(s);
		size = Vector.add(new Vector[]{v,u,w});
		
		//PROBLEM DONT WANT TO WASTE MEMORY GIVING EACH PLANE MAT, NEED TO GET FROM BOX NOT STORE PERMANENTLY FOR EACH PLANE
		Vector hv = multiply(v,0.5); Vector hu = multiply(u,0.5); Vector hw = multiply(w,0.5); //half v,u,w
		planes.add(new plane(add(loc,add(hv,hu)), v, u ,mat,v.mag(),u.mag()));
		planes.add(new plane(add(loc,add(hv,hw)), v, w ,mat,v.mag(),w.mag()));
		planes.add(new plane(add(loc,add(hu,hw)), u, w ,mat,u.mag(),w.mag()));
		planes.add(new plane(add(add(w,loc),add(hv,hu)), v, u ,mat,v.mag(),u.mag()));
		planes.add(new plane(add(add(u,loc),add(hv,hw)), v, w ,mat,v.mag(),w.mag()));
		planes.add(new plane(add(add(v,loc),add(hu,hw)), u, w ,mat,u.mag(),w.mag()));	
		
		patchSpace = planes.get(0).patchSpace; //TEMP TEMP ASSUMING A CUBE NOT A BOX
		out.println(patchSpace);
	}

	public double getDistIntersectionLine(Vector loc, Vector dir) 
	{
		double min=0;
		int minIndex=-1;
		for(int i=0; i<planes.size(); i++)
		{
			Vector point = planes.get(i).getPointIntersectionLine(loc,dir);
			if(point==null) continue;
			double dist = point.distance(loc);
		
			if(dist<=0) continue; // NEED FIX , NEGATIVES SHOULD BE ACCEPTED
			if(dist<raycaster.extra)continue;
			if(!isInside(point))continue;
			if(dist<min || min==0){ min=dist; minIndex=i;}
		}
		if(minIndex!=-1 && everyOtherUsedForLastIndexBecauseLightingCallsRayTraceMethod==1)
			lastIndex=minIndex;
		everyOtherUsedForLastIndexBecauseLightingCallsRayTraceMethod*=-1;
		return min;	
	}
	
	public boolean isInside(Vector point)
	{
		double extra = raycaster.extra;
		if( !(point.x+extra > start.x) ) return false;
		if( !(point.x-extra < start.x+size.x) ) return false;
		if( !(point.y+extra > start.y) ) return false;
		if( !(point.y-extra < start.y+size.y) ) return false;
		if( !(point.z+extra > start.z) ) return false;
		if( !(point.z-extra < start.z+size.z) ) return false;
		return true;
		
	}
	public Vector getNormal(Vector loc)
	{
		return planes.get(lastIndex).getNormal(loc);
	}	
	public float[] getTextureRGB(Vector loc)
	{
		return planes.get(lastIndex).getTextureRGB(loc,mat.texture);
	}
	public float[] getPatchRGB(Vector loc)
	{
		return planes.get(lastIndex).getPatchRGB(loc);
	}
}
