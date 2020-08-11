import java.io.*;
import java.util.*;

import static java.lang.System.*;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import java.awt.*;
import java.awt.image.*;
public class video
{
	int counter = 0;
	String filename = "";
	String format = "gif";
	String desktop = "c:\\Users\\Everyone2\\Desktop";
	timer time = null;
	int rate = 33;
	int max=0;
	
	public video(String fn, boolean make){this(fn,make,0);}
	public video(String fn, boolean make, int seconds){
		filename = fn;
		if(make)
		{
			max  = (int)((double)seconds*(1000.0/(double)rate));
			makeDir();
			File file = new File(desktop+"\\"+filename+".txt");
			BufferedWriter write;
			try{
			write = new BufferedWriter(new FileWriter(desktop+"\\"+filename+"\\"+filename+".txt"));
			write.write(""+max);
			write.close();} 
			catch(IOException e){}	
			out.println("done constructor making");
		}
		else{
			time = new timer(); time.setTime();
			Scanner scan = null;
			try{
			scan = new Scanner(new File(desktop+"\\"+filename+"\\"+filename+".txt"));}
			catch(Exception e){out.println("scanner problem video");}
			max = scan.nextInt();
			scan.close();
			}	
	}
	
	public void saveImage(BufferedImage image)
	{
		counter++;
		if(counter>=max)return;
		File file = new File(filename + counter);
		try{
			ImageIO.write(image,format,new File(desktop+"\\"+filename+"\\"+filename+counter+"."+format));}
		catch(IOException e){out.println("error videomaker");}
		out.println(counter + " of " + max);
		
	}
	
	public void play(Graphics g)
	{
		counter++;
		if(counter>max) counter=max;
		long pass = time.getTimePassed();
		if(pass < rate) pause(rate-pass);
		
		Image image = null;
		try{
		image = ImageIO.read(new File(desktop+"\\"+filename+"\\"+filename+counter+"."+format));}
		catch(IOException e){out.println("error video play");}
		if(image!=null)  g.drawImage(image,0,0, null);
		else out.println("video play null image");
		time.setTime();
	}
	public int getMax(){return max;}
	public int getCounter(){return counter;}
	public void makeDir()
	{
		File file = new File(desktop+"\\"+filename);
		boolean made = file.mkdir();
		out.println("made directory " + made);
	}
	public void pause(long p)
	{
		try{Thread.sleep(p);}
		catch(InterruptedException e){}
	}
	
}
