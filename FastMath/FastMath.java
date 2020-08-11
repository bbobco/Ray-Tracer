package FastMath;
import static ThreeDimensional.Vector.*;
import static java.lang.System.*;      
import static java.lang.Math.*;  
import ThreeDimensional.Vector;

public class FastMath
{
	static double[] cos = new double[91];
	
	public static void makevalues()
	{
		for(int x=0; x<=90; x++)
			cos[x]=Math.cos(toRadians(x));	
	}
	public static double cos(int angle)
	{
		while(angle>=360)angle-=360;
		while(angle<0)angle+=360;
		
		if(angle<=90)
			return cos[angle];
		else if(angle<=180)
			return -cos[180-angle];
		else if(angle<=270)
			return -cos[angle-180];
		else return cos[360-angle];
	}
	public static double sin(int angle)
	{
		return cos(90-angle);
	}



	public static double distance2D(double px1, double py1, double px2, double py2)
	{
		double x = px1-px2;
		double y = py1-py2;
		return sqrt(x*x+y*y);
	}

	public static float[] multiplyFloats(float[] f, float scale)
	{
		float[] returned = FastMath.arrayEquals(f);
		for(int i=0; i<returned.length; i++)
			returned[i]*=scale;
		return returned;
	}
	public static double[] arrayEquals(double[] b)
	{
		double[] a = new double[b.length];
		for(int i=0; i< b.length; i++)
			a[i]=b[i];
		return a;
	}
	public static float[] arrayEquals(float[] b)
	{
		float[] a = new float[b.length];
		for(int i=0; i< b.length; i++)
			a[i]=b[i];
		return a;
	}


	public static double[] arrayEquals(double[] a, double[] b, int length)
	{
		for(int i=0; i< length; i++)
			a[i]=b[i];
		return a;
	}
	public static float[] arrayEquals(float[] a, float[] b)
	{
		for(int i=0; i< b.length; i++)
			a[i]=b[i];
		return a;
	}
	public static float[] addFloats(float[] f, float a)
	{
		float[] returned = FastMath.arrayEquals(f);
		for(int i=0; i<returned.length; i++)
			returned[i]+=a;
		return returned;
	}
	public static float[] divideFloats(float[] f, float d)
	{
		float[] returned = FastMath.arrayEquals(f);
		for(int i =0;i<f.length;i++)
			returned[i]/=d;
		return returned;
	}

	public static float[] addFloats(float[] a, float[] b)
	{
		float[] ret = new float[3];
		for(int i=0;i<b.length;i++)
			ret[i]=a[i]+b[i];
		return ret;
	}
	 public static double sum(double[] vals)
	 {
		 double sum = 0;
		 for(int i=0;i<vals.length;i++)
			 sum+=vals[i];
		 return sum;
	 }
	 public static double sum(double[] vals, int end)
	 {
		 double sum = 0;
		 for(int i=0;i<end;i++)
			 sum+=vals[i];
		 return sum;
	 }
	 public static float sum(float[] vals)
	 {
		 float sum = 0;
		 for(int i=0;i<vals.length;i++)
			 sum+=vals[i];
		 return sum;
	 }
	 public static int getMaxIndex(double[] a)
	 {
		 int maxIndex = 0;
		 double max = a[0];
		 for(int i=1;i<a.length;i++)
			 if(a[i]>max){ max = a[i]; maxIndex=i;}
		 return maxIndex;
	 }
	 public static double getMax(double[] a)
	 {
		 double max = a[0];
		 for(int i=1;i<a.length;i++)
			 if(a[i]>max) max = a[i];
		 return max;
	 }
	 public static double getMin(double[] a)
	 {
		 double min = a[0];
		 for(int i=1;i<a.length;i++)
			 if(a[i]<min) min = a[i];
		 return min;
	 }
	 public static void print(float[] a)
	 {
		 for(int i=0; i<a.length;i++)
			 out.print(a[i]+ " ");
		 out.println();
	 }
	 public static void print(double[] a)
	 {
		 for(int i=0; i<a.length;i++)
			 out.print(a[i]+ " ");
		 out.println();
	 }
	 public static Vector getNormal(Vector p, Vector q, Vector r)
	 {
		 Vector pq = Vector.subtract(p,q);
		 Vector pr = Vector.subtract(p,r);
		 pq.normalize(); pr.normalize();
		 Vector normal = cross(pq,pr);
		 normal.normalize();
	//	 normal.scale(-1);
		 return normal;
	 }
}