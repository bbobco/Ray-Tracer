import java.awt.image.*;
public class ImageInfoData 
{
	BufferedImage image = null;
	byte[] data;
	int w,h;
	public ImageInfoData(byte[] dat, int width, int height)
	{
		data=dat;w=width;h=height;
	}
	public ImageInfoData(BufferedImage im)
	{
		image=im;
	}
}
