import java.awt.image.BufferedImage;
import static java.lang.System.*;
import java.io.*;
import static java.lang.Math.*;
public class Texture 
{
	BufferedImage image;
//	byte[] imageData;
	int width,height;
	float scale =1;

	public Texture(ImageInfoData info, float sc){this(info); scale=sc;}
	public Texture(ImageInfoData info){
		image = info.image; width=image.getWidth(); height=image.getHeight();
		//imageData = info.data;
		//width=info.w; height=info.h;
	}
	public float[] getRGB(int x, int y)
	{	
		//Fix so points can be points on image, would make tiles
		while(x<0)x+=width; while(y<0) y+=height;
		while(x>=width)x-=width; while(y>=height) y-=height;
		
		float[] rgb = intToFloat(image.getRGB(x, y));
		
		return rgb;
		
	}
	

	
	//private int getImageRGB(int x, int y){
	//	return imageData[x+y*height];
	//}
	private int[] getImageRGB2(int x, int y)
	{
		//TEMP 
		return null;
		
//		int[] rgb =  { imageData[(x+y*height)*3+0] + 128, imageData[(x+y*height)*3 +1] + 128, imageData[(x+y*height)*3+2] + 128 } ;
		
	//	for(int i=0; i<rgb.length;i++)
	//		out.print(rgb[i]+ " " );
	//	out.println();
		
	//	return rgb;
	}
	
	public float[] intToFloat(int color)
	{
		color-=255<<24;
		int red  = color >> 16; color-=red<<16;
		int green = color >> 8; color-=green<<8;
		int blue = color;
		
		return new float[] {red/255f,green/255f,blue/255f};
	// (255 << 24) + (RED << 16) + (GREEN << 8) + BLUE;
	}
	public String toString()
	{
		String s = "w: "+ width + " " + "h: "+height;
		return s;
	}
}
