import static java.lang.System.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;
import java.awt.geom.*;
import static java.lang.Math.*;
import static FastMath.FastMath.*;
import static ThreeDimensional.Vector.*;
import ThreeDimensional.Vector;

class raycaster
{
	static float ambient = .00f;
	static int swidth = world.swidth, sheight = world.sheight;
	static double pd = camera.perspectiveDistance;
	static float lscale = 1.0f/25.0f; // 25 dist = 1 meter for light equations
	static double extra = 0.0005;
	static int numgloss = 250;
	static boolean radiosity = false; //TEMPORARY VARIABLE
	static boolean printProgress = true;

	public static void cast(BufferedImage image, ArrayList<object> objects , ArrayList<Light> lights)
	{
		doPatches(objects,lights);
		
		int[] dataRGB;	
		{
			DataBufferInt buf = (DataBufferInt) image.getRaster().getDataBuffer();
		 	dataRGB = buf.getData();
		}
		Vector up = new Vector(0,0,1);
		Vector yaxis = camera.dir;
		Vector xaxis = Vector.cross(yaxis, up);
		Vector zaxis = Vector.cross(xaxis, yaxis);
		
	//	out.println(xaxis);out.println(yaxis);out.println(zaxis);
		
		//coordinates on virtual screen
		for(int x=-swidth/2; x<swidth/2; x++)
		{
			if(printProgress)
				out.println((float)(x+swidth/2)/swidth * 100+"%");
			for(int z=-sheight/2; z<=sheight/2; z++) //z loop slightly dif. fixes backwards y on image coordinates
			{
				//********* CAMERA TO OBJECT **********
				Vector primhit;
				Vector primHitDir;
				object prim;
				{		
					Vector xcam = Vector.multiply(xaxis, x);
					Vector ycam = Vector.multiply(yaxis,pd);
					Vector zcam = Vector.multiply(zaxis, z);
					Vector camdir = Vector.add(new Vector[]{xcam,ycam,zcam});
					camdir.normalize();
																		
					RayTraceData rayTraceInfo = rayTrace(camera.loc,camdir,objects);				
					if(rayTraceInfo==null) continue; //hit no primatives/anything that would reflect light
					
					primhit = rayTraceInfo.loc;
					primHitDir = rayTraceInfo.dir;			
					prim = rayTraceInfo.objectHit;
				}
					
				//********* DIFFUSE/SPECULAR/AMBIENT LIGHTING *********
				float[] rgb = doLighting(primhit,primHitDir,prim,objects,lights);
				
				//************* Radiosity Lighting *************
				if(radiosity)
				{
					//TEMP ONLY SHOWING RADIOSITY TEMP TMPE DERPADER PTEMP HERPADERP
		//			rgb = new float[]{0,0,0}; //TEMP TEMP TEMPORARY TEMPTEMP
					
					float[] patchRGB  = prim.getPatchRGB(primhit);
					if(patchRGB!=null)
						rgb = addFloats(rgb , patchRGB);
					for(int i=0; i<rgb.length;i++) if(rgb[i]>1) rgb[i]=1;
				}
								
				//*********** SET PIXEL ***********
				int[] irgb = {(int)(rgb[0]*255) , (int)(rgb[1]*255) , (int)(rgb[2]*255)};
				int colorInt  = (255 << 24) + (irgb[0] << 16) + (irgb[1] << 8) + irgb[2]; //javadocs on color say that alpha bits 24-31, red 16-23,etc.
				int imageX = x+swidth/2;
				int imageY = (z*-1+sheight/2);
				if(imageX>=0&&imageY>=0&&imageX<swidth&&imageY<sheight)
					dataRGB[imageX + imageY*swidth] = colorInt; //z*-1 to bc y coordiantes are backwards on image								
			}
		}
		world.imageDone = true;	
	}
	
	private static float[] doLighting(Vector primhit, Vector primHitDir, object prim, ArrayList<object> objects, ArrayList<Light> lights)
	{
		float[] rgb = new float[3];
		for(Light light: lights)
		{
			for(int i=0; i<light.getLocs().length;i++)
			{
				double lightdist=-1;
				Vector lightLoc = light.getLocs()[i];
				Vector ldir = new Vector();
								
				Vector primToLight  = Vector.subtract(lightLoc, primhit);			
				lightdist = primToLight.mag(); //distance to the light							
				primToLight = Vector.divide(primToLight , lightdist); // makes a unit vector
				ldir = primToLight;
				
				RayTraceData lightTrace = rayTrace(primhit,primToLight,objects,false);
				//if it hits another prim before it hits the light
				if(lightTrace != null)
				{
					// mag problem
					double distance = primhit.distance(lightTrace.loc);
					if(distance < lightdist)
					{
				//		out.println("shadow");
						continue;
					}
				}
				
				//************ DIFFUSE LIGHT *************
				float tempintensity = (float)(Vector.dot( ldir , prim.getNormal(primhit) ));
				
				//POSSIBLE PROBLEM BELOW TEMP NEED FIX TEMP TEMP GMDPGDPGJGD PGDGD!@
				//CHOOSE ONE OF BELOW ONLY:
				tempintensity = abs(tempintensity); 
	//			if(tempintensity < 0) tempintensity = 0;
				
				tempintensity/=(float)(light.getLocs().length);
				
				double luminosity = light.getLuminosity();
				lightdist*=lscale;
					
				float diffuseLight = (float)( tempintensity * luminosity/(lightdist*lightdist) );
				for(int q=0; q < rgb.length; q++)
					rgb[q] += prim.getRGB(primhit)[q]*diffuseLight;
				
				if(primHitDir == null) continue;// null in patches, only diffuse lighting is done
				//********** SPECULAR LIGHT ***********
				if(prim.getSpecular() > 0 )
				{
					Vector lightReflectedDir = prim.getReflectedDir(primhit,ldir); 
					float objspecular = prim.getSpecular();float shine = prim.getShine();
					double dot = (Vector.dot(primHitDir , lightReflectedDir)); 
					if(dot>0)
					{
						//can also multiply objspecular by tempintensity
						float specular = tempintensity * objspecular * (float)pow((double)dot ,(double)shine );
						rgb = addFloats(rgb , specular );
					}		
				}					
			}		
		}
		//********  AMBIENT LIGHT *********				
		for(int i=0; i < rgb.length; i++){
			rgb[i] += prim.getRGB(primhit)[i]*ambient;
			if(rgb[i]>1) rgb[i] = 1;
		}
		return rgb;
	}
	private static RayTraceData rayTrace(Vector loc, Vector dir, ArrayList<object> objects){return rayTrace(loc,dir,objects,true);}
	private static RayTraceData rayTrace(Vector loc, Vector dir, ArrayList<object> objects, boolean recursive)
	{	
		double t = 0;
		Vector firsthit=null;// = new double[4];
		object objectHit=null;
		for(object o: objects)
		{
			Vector hit = o.getPointIntersectionLine( loc , dir );
			if(hit==null) continue;
			
			double distance = hit.distance(loc); // mag problem
			if( distance> extra && (distance < t  || t==0) ) 
			{
				firsthit=hit; t=distance;
				objectHit = o;
			}
		}
		if(t==0) return null;

		if(objectHit.reflects &&recursive)
		{
			Vector rdir = objectHit.getReflectedDir(firsthit,dir);
			return rayTrace(firsthit, rdir, objects);
		}
		if(objectHit.refracts &&recursive) // TEMP, refracts infinitely beyond temp plane that done with, TEMP
		{
			Vector rdir = objectHit.getRefractedDir(firsthit, dir, 1.0 , objectHit.indexRefraction);
			return rayTrace(firsthit, rdir, objects);
		}
		return new RayTraceData (firsthit, dir , objectHit );
	}
	
	private static void doPatches(ArrayList<object> objects, ArrayList<Light> lights)
	{
		for(object o: objects)
			for(Patch pat: o.mat.patches)
				pat.radianceRGB = multiplyFloats( doLighting(pat.loc,null,o,objects,lights) , o.mat.radiance );
		
		for(object o: objects)
			for(Patch giver: o.mat.patches)	
				for(object obj: objects)
				{
					for(Patch taker: obj.mat.patches)
					{
						//********* SEEING IF GETS TO PATCH ***************
						Vector direction = Vector.subtract(taker.loc,giver.loc);
						double distance = direction.mag();
						direction.divide(distance);
						if( !reaches(giver.loc,taker.loc, direction, distance, objects) ) continue;
						
						//***************** CALCULATING PATCH LIGHTING EXCHANGE ************
				//		if(o==obj) continue; //TEMP WONT LET OBJECTS REFLECT ONTO THEMSELVES
						distance*=lscale;
						float mult =(float)( (obj.patchSpace*obj.patchSpace *lscale*lscale) / (distance*distance) );
						taker.illuminatedRGB = multiplyFloats(giver.radianceRGB,mult);
						taker.rgb = addFloats(taker.rgb, taker.illuminatedRGB);
					}
				}
		if(true) return; //temp
		
		for(object o: objects)
			for(Patch giver: o.mat.patches)	
				for(object obj: objects)
				{
					for(Patch taker: obj.mat.patches)
					{
						//********* SEEING IF GETS TO PATCH ***************
						Vector direction = Vector.subtract(taker.loc,giver.loc);
						double distance = direction.mag();
						direction.divide(distance);
						if( !reaches(giver.loc,taker.loc, direction, distance, objects) ) continue;
						
						//***************** CALCULATING PATCH LIGHTING EXCHANGE ************	
				//		if(o==obj) continue; //TEMP WONT LET OBJECTS REFLECT ONTO THEMSELVES
						distance*=lscale;
						float mult =(float)( (obj.patchSpace*obj.patchSpace *lscale*lscale) / (distance*distance) );
		//				taker.rgb = addFloats(taker.rgb , multiplyFloats(giver.illuminatedRGB,mult*o.mat.radiance));				
					}
				}	
		
			
						
	}
	
	public static boolean onScreen(Vector v)
	{
		Vector rv = Vector.subtract(v,camera.loc);
	//	out.println(camera.xcos);
//		rv.rotate(camera.xcos,camera.xsin,camera.zcos,camera.zsin);
		if(rv.y<0) return false;
		double ratio = pd/rv.y;
		double nx = ratio*rv.x, nz = ratio*rv.z;
		return onScreen2D( nx, nz );
	}
	public static boolean onScreen2D(double x, double z)
	{
		if(x<-swidth/2) return false; if(x>swidth/2-1) return false;
		if(z<-sheight/2+1) return false; if(z>sheight/2) return false;
		return true;
	}
	private static boolean reaches(Vector start, Vector finish, ArrayList<object> objects)
	{
		Vector dir  = Vector.subtract(finish, start);	
		double actualDistance = dir.mag();
		dir.divide(actualDistance);
		return reaches(start,finish,dir,actualDistance,objects);
	}
	private static boolean reaches(Vector start, Vector finish, Vector dir,double actualDistance, ArrayList<object> objects)
	{				
		RayTraceData trace = rayTrace(start,dir,objects,false);
		if(trace==null) return false;
		double distance = start.distance(trace.loc);
		if(distance < actualDistance) return false;
		return true;
	}
}
