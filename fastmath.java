import static java.lang.System.*;      
import static java.lang.Math.*;  

public class fastmath
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
	public static double[] crossProduct(double[] v, double[] u)
	{
		double[] w = new double[3];
		w[0] = v[1]*u[2]-v[2]*u[1];    //A
		w[1] = v[2]*u[0]-v[0]*u[2];    //B
		w[2] = v[0]*u[1]-v[1]*u[0];	   //C	

		return w;
	}
	public static double dotProduct(double[] v, double[] u)
	{
		double dp = 0;
		for(int i=0; i<3; i++)
			dp+= v[i]*u[i];
		return dp;
	}
	public static double[] addVectors(double[] v, double[] u)
	{
		return new double[] {v[0]+u[0], v[1]+u[1], v[2]+u[2]  } ;
	}
	public static double[] addVectors(double[][] vectors)
	{
		double[] sum = new double[3];
		for(int p=0;p<vectors.length;p++)
			for(int i=0; i<3; i++)
				sum[i]+=vectors[p][i];
		return sum;
	}
	public static double[] getNeg(double[] v)
	{
		double[] ret = arrayEquals(v);
		for(int i =0;i<3;i++)
			ret[i]*=-1;
		return ret;
	}
	public static double[] subtractVectors(double[] v, double[] u)
	{
		return new double[] {v[0]-u[0], v[1]-u[1], v[2]-u[2]  } ;
	}
	public static double[] unitVector(double[] v)
	{
		double[] ret = arrayEquals(v);
		double m = sqrt(  v[0]*v[0] + v[1]*v[1] + v[2]*v[2]  );
		for(int i=0; i<v.length;i++)
			ret[i]/=m;
		return ret;
	}
	public static double[] multiplyVector(double[] v, double s)
	{
		double[] ret = arrayEquals(v);
		for(int i =0; i<v.length; i++)
			ret[i]*=s;
		return ret;
	}
	public static double[] divideVector(double[] v, double s)
	{
		double[] ret = arrayEquals(v);
		for(int i =0; i<v.length; i++)
			ret[i]/=s;
		return ret;
	}
	public static double[] multiplyVectors(double[] v, double[] u)
	{
		double[] ret = arrayEquals(u);
		for(int i=0; i<v.length; i++)
			ret[i]*=u[i];
		return ret;
	}
	public static double[] divideVectors(double[] v, double[] u)
	{
		double[] ret = arrayEquals(u);
		for(int i=0; i<v.length; i++)
			ret[i]/=u[i];
		return ret;
	}	
	public static double getVectorMagnitude(double[] v)
	{
		return sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
	}
	

	public static float[] multiplyFloats(float[] f, float scale)
	{
		float[] returned = fastmath.arrayEquals(f);
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
	public static double[] rotateVector(double[] v, int xa, int za)
	{
		double[] ret = arrayEquals(v);
		double tempx=ret[0],tempy=ret[1],tempz=ret[2];		
						
		ret[0]= tempx*fastmath.cos(za) - tempy*fastmath.sin(za);
		ret[1]= tempx*fastmath.sin(za) + tempy*fastmath.cos(za);	
		tempx=ret[0];tempy=ret[1];tempz=ret[2];
		
		ret[1]= tempy*fastmath.cos(xa) - tempz*fastmath.sin(xa);
		ret[2]= tempy*fastmath.sin(xa) + tempz*fastmath.cos(xa);			
		return ret;
	}

	public static double[] rotateVectorExact(double[] v, double xa, double za)
	{
		double[] ret = arrayEquals(v);
		double tempx=ret[0],tempy=ret[1],tempz=ret[2];		
						
		ret[0]= tempx*Math.cos(za) - tempy*Math.sin(za);
		ret[1]= tempx*Math.sin(za) + tempy*Math.cos(za);	
		tempx=ret[0];tempy=ret[1];tempz=ret[2];
		
		ret[1]= tempy*Math.cos(xa) - tempz*Math.sin(xa);
		ret[2]= tempy*Math.sin(xa) + tempz*Math.cos(xa);			
		return ret;
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
		float[] returned = fastmath.arrayEquals(f);
		for(int i=0; i<returned.length; i++)
			returned[i]+=a;
		return returned;
	}
	public static float[] divideFloats(float[] f, float d)
	{
		for(int i =0;i<f.length;i++)
			f[i]/=d;
		return f;
	}
	public static double[] invert(double[] v)
	{
		double[] ret = new double[v.length];
		for(int i=0;i<v.length;i++)
			ret[i]=1.0/v[i];
		return ret;
	}
	public static float[] addFloats(float[] a, float[] b)
	{
		float[] ret = new float[3];
		for(int i=0;i<b.length;i++)
			ret[i]=a[i]+b[i];
		return ret;
	}
	
}