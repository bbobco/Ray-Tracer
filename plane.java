import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;
import static java.lang.System.*;      
import java.util.*;
import static java.lang.Math.*; 


class plane extends object
{
	Vector n; // normal vector
	Vector dir1,dir2;
	Vector center;
	double d; // distance from origin to point p in constructor
	ArrayList<window> windows = new ArrayList<window>();
	
	public plane(Vector p, Vector q, Vector r, material m, double patchMax){this(p,q,r,m);}
	public plane(Vector p, Vector q, Vector r, material m){this(p,q,r,m,-1,-1);}
	public plane(double x1,double y1,double z1, double x2, double y2, double z2, double x3, double y3, double z3, material m){this(new Vector(x1,y1,z1),new Vector(x2,y2,z2),new Vector(x3,y3,z3),m,-1,-1);}
	public plane(double x1,double y1,double z1, double x2, double y2, double z2, double x3, double y3, double z3){this(new Vector(x1,y1,z1),new Vector(x2,y2,z2),new Vector(x3,y3,z3),null,-1,-1);}
	public plane(Vector p, Vector q, Vector r, material m,double pm1,double pm2)
	{
		super("plane");
		mat = m;
		center = p;
		dir1=new Vector(q.x,q.y,q.z); dir1.normalize();
		dir2=new Vector(r.x,r.y,r.z); dir2.normalize();
		
		// normal = cross product: PR x PQ
		n = Vector.cross(dir2,dir1);
		n.normalize();	
		d = Vector.dot(n , p);	
		
		if(!reflects)
		{
			double md1 = Patch.defaultSpace*Patch.rows, md2=Patch.defaultSpace*Patch.rows;
			if(pm1>0 && pm2>0){md1=pm1; md2=pm2;}
	//		out.println(md1 + " " + md2);
			
				int mod = (int)( (abs(md1)+abs(md2))/Patch.defaultSpace ) + 1;
				patchSpace =(float)( (abs(md1) + abs(md2)) /mod );
			//	out.println(patchSpace);
		
				for(double d1= -md1/2; d1<=md1/2; d1+=patchSpace)
				{
					for(double d2 = -md2/2; d2<=md2/2; d2+=patchSpace)
					{
						Vector vd1 = Vector.multiply(dir1, d1);
						Vector vd2 = Vector.multiply(dir2, d2);
						Vector pv = Vector.add(new Vector[] { p,vd1,vd2 } );
				//		if(raycaster.onScreen(pv))
						mat.patches.add(new Patch(pv,(float)d1,(float)d2,patchSpace));
						
					}
				}
		}
	//	out.println(patchSpace);
	}

	
	public double getDistIntersectionLine(Vector loc, Vector dir)
	{
		double denom = Vector.dot(n,dir);
		if(denom==0) return 0; // this is bottom of dot product, dot product is undefined when line is parallel, so == paralell == no intersection
		
		double t = ( d - ( Vector.dot(n,loc) )   ) / denom;	
		return t;
	}
	public Vector getPointIntersectionLine(Vector loc, Vector dir)
	{
		Vector point = super.getPointIntersectionLine(loc,dir);
		if(point==null)return null;
		if(windows.size()==0) return point;
		Vector subpoint = Vector.subtract(point,center);
		double u = dot(subpoint,dir1);
		double w = dot(subpoint,dir2);
		for(window win: windows)
			if(win.inWindow(u, w))
				return null;
		return point;
	}
	public double getPointDist(Vector loc)
	{
		return abs(Vector.dot(loc,n)+d);// d is distance of plane from origin, d was calculated by dotproduct of normal and point
	}
	
	public Vector getNormal(Vector loc)
	{
		if(mat==null) return n;
		if(mat.nmap==null) return n;
		else
			return mat.nmap.getNormal(loc,n,dir1,dir2);
	}
	public float[] getTextureRGB(Vector loc, Texture texture)
	{
		double ixdot = Vector.dot(dir1,loc);
		double iydot = Vector.dot(dir2,loc);
		ixdot*=texture.scale; iydot*=texture.scale;

		int textureX =(int)round( ixdot  );
		int textureY =(int)round( iydot );
		return texture.getRGB(textureX, textureY);
	}
	public float[] getTextureRGB(Vector loc){
		return getTextureRGB(loc,mat.texture);
	}
	
	public float[] getPatchRGB(Vector loc)
	{
		float[] tl=null,tr=null,br=null,bl=null;
		
		Patch closest = null;
		Vector subloc = Vector.subtract(loc,center);
		double u = dot(subloc,dir1);
		double w = dot(subloc,dir2);	
		double min = 9999999; // big number!
		for(Patch pat: mat.patches)
		{	
			double tu = u-pat.u, tw = w -pat.w;
			double dist = sqrt(tu*tu + tw*tw);
			if(dist<min){ min = dist; closest = pat;}
		}
		if(closest==null) //BOX PROBLEM NEED TO FIX TEMP TEMP
			return null;
		
		boolean up= w > closest.w;
		boolean right = u > closest.u;
		for(Patch pat : mat.patches)
		{
			if(!up && right) break;
			else if(!up && !right && pat.isPatch(closest.u - patchSpace,closest.w)){ closest = pat; break;}
			else if(up && right && pat.isPatch(closest.u, closest.w+patchSpace)){closest = pat; break;}
			else if(up && !right &&  pat.isPatch(closest.u - patchSpace,closest.w + patchSpace)){ closest = pat; break;}
		}
		//divided by patchSpace to make 2 dimensional unit vectors. x and y always be between 0 and 1
		double x = u-closest.u; x/=patchSpace;
		double y = closest.w - w; y/=patchSpace;
		if(abs(x)>1 || abs(y)>1) out.println(x + " " + y);
		tl = closest.rgb;
		
		//tl its my topleft corner, below getting other 3, so can interpolate throughout a square, knowing the four corner values
		for(Patch pat: mat.patches)
		{		
			double uadd= patchSpace , wadd= -patchSpace;
			if(pat.isPatch(closest.u,closest.w+wadd))bl = pat.rgb;
			if(pat.isPatch(closest.u+uadd,closest.w))tr = pat.rgb;
			if(pat.isPatch(closest.u+uadd,closest.w+wadd))br =  pat.rgb;
		}
		if(tl==null || tr == null || bl == null || br == null) return new float[] {0,0,0} ; //TEMPORARY ON EDGES
		
		float[] rgb = new float[3];

		
		//BILINEAR INTERPOLATION! < wiki article explained well
		for(int q =0; q< 3; q++)
			rgb[q] = (float)( tl[q]*(1-x)*(1-y) + tr[q]*x*(1-y) + bl[q]*(1-x)*y + br[q]*x*y );
		
		return rgb;
		
	
	}
	
}

	
