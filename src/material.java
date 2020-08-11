import java.awt.image.*;
import java.util.ArrayList;
class material 
{
	ArrayList<Patch> patches = new ArrayList<Patch>();
	NormalMap nmap = null;
	Texture texture = null;
	static material basic = new material(new float[]{1,1,1} , 0 , 0 , 0);
	float[] rgb;
	float shine;
	float specular;
	float radiance = 0;
	boolean gloss=false;
	
	public material(NormalMap normap,float[] RGB,  float sp, float sh, float rad){this(RGB,sp,sh,rad); nmap=normap;}
	public material(ImageInfoData iminf, NormalMap normap, float textsc, float sp, float sh, float rad){specular=sp;shine=sh;radiance=rad; texture = new Texture(iminf,textsc);nmap=normap;}
	public material(ImageInfoData iminf, float textsc, float sp, float sh, float rad){specular=sp;shine=sh;radiance=rad; texture = new Texture(iminf,textsc);}
	public material(ImageInfoData iminf, float sp, float sh, float rad)
	{
		specular=sp; shine=sh; radiance=rad;
		texture = new Texture(iminf);
	}
	public material(float[] RGB,  float sp, float sh, float rad){
		rgb=RGB;  specular = sp; shine=sh; radiance=rad;
	}
	public material(float r, float g, float b,  float sp, float sh, float rad){this(new float[]{r,g,b},sp,sh,rad);}	
	public material(float r, float g, float b, float rad){this(new float[]{r,g,b},0,0,rad);}
	public material(float[] RGB,float rad){this(RGB,0,0,rad);}
	
	
	public float[] getRGB(int x, int y)
	{
		return texture.getRGB(x,y);
	}
	
	
	public float[] getRGB(){return rgb;}
	public float getShine(){return shine;}
	public float getSpecular(){return specular;}
	public void setRGB(float[] RGB){rgb=RGB;}
	public void setRGB(float r, float g, float b){rgb[0]=r;rgb[1]=g;rgb[2]=b;}
	public void setShine(float sh){shine=sh;}
	public void setSpecular(float sp){specular=sp;}
}
