import static FastMath.FastMath.*;
import java.io.*;

import static ThreeDimensional.Vector.*;
import FastMath.FastMath;
import ThreeDimensional.Vector;
import static java.lang.System.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import static java.lang.Math.*;

class world extends Frame implements KeyListener,MouseMotionListener,MouseListener
{
	static boolean imageDone = false;
	boolean tookScreen = false;
	boolean takeScreen = false;
	boolean loop = false;
	static int swidth = 1366, sheight = 768;
	//	static int swidth = 3000,  sheight = 2000;
//	static int swidth = 500,  sheight = 500;
//	static int swidth = 1024,  sheight = 768;
//	static int swidth = 1680,  sheight = 1050;
	
	BufferedImage image = new BufferedImage(swidth,sheight,BufferedImage.TYPE_INT_RGB);		
	timer time = new timer();	
	String[] stats = new String[1];
	//*** data *****
	ArrayList<object> objects = new ArrayList<object>();
	ArrayList<Light> lights = new ArrayList<Light>();	
	//*** mouse ****
	int mousex; int mousey;

	String textureRoot = "textures\\"; String textureType = ".gif";
//	String screenDirectory = "c:\\Users\\Everyone2\\Desktop\\mandelbulb-screens\\";
	String screenDirectory = "screenshots\\";
	
	public void addthings()
	{
		camera.setloc(140,-40, 35);
//		camera.setdir(0.0,1.0,-0.5); //IMPORTANT
		camera.setdir(-1.0,1.4,0.5); //IMPORTANT
		
		material mtexture = new material( getImageFile("smallrocks"),0.41f,      230.2f, 0.04f);
		material mtexture2 = new material( getImageFile("wood"), 0.41f,      230.2f, 0.04f);	
	//	material mnorm = new material(new NormalMap(),new float[]{0.98f,0.92f,0.84f} , 0.20f, 50.0f);
		
		material m = new material(0.98f,0.92f,0.84f,  0.40f, 30.0f, 0.04f);
		
		material m2 = new material(0.2f,1.0f,0.4f,    0.00f, 30.0f, 0.04f);
		material m3 = new material(0.12f,0.80f,0.35f,    0.00f, 30.0f, 0.04f);
		
		objects.add(new plane(0,0,0,  0,1,0,   1,0,0   ,mtexture2));	
		objects.add(new plane(0,1000,0,  1,0,0,  0,0,1   , mtexture));	
		m=new material(0.5f,0.2f,0.85f, 0.0f,0.0f,0.0f);
		
		plane pl = new plane(-70,60,485, 1,0,0, 0,1,0,  m3);
		window win = new window(0,0,20,20);
		pl.windows.add(win);
//		objects.add(pl);
		
//		objects.add(new mirror( 0,500,0,  1,0,0,  0,0,1  ));
		
//		objects.add(new RefractingPlane(0,400,0,  1,400,0,  0,400,1,  1.0));
		
		m=new material(0.80f,0.02f,0.20f , 0.80f, 400.0f , 0.04f);
		sphere s = new sphere(60,170,25,25   ,m);	
		objects.add(s);
		
		m=new material(0.80f,0.02f,0.90f , 0.80f, 400.0f , 0.04f);
		sphere ds = new sphere(60,130,95,25   ,m);	
		objects.add(ds);
		
		m=new material(0.10f,0.02f,0.92f , 0.80f, 400.0f , 0.04f);
		sphere fs = new sphere(0,130,25,25   ,m);	
		objects.add(fs);
		
		m=new material(0.10f,0.72f,0.20f , 7.80f, 255.0f , .04f);
		sphere ns = new sphere(115,170,38,38   ,m);	
		objects.add(ns);
		
			
		m=new material(0.75f,0.15f,0.15f,  0.35f, 60.0f , 0.04f);
		box b = new box(-150,230,0, 80,0,0,  0,80,0,  80  ,m);
//		objects.add(b);
						objects.add(new spheremirror(-80,190,80,40));
						objects.add(new spheremirror(-180,90,80,60));
						
					//	objects.add(new spheremirror(-480,960,80,460));
						
						objects.add(new spheremirror(0,140,220,100));
		//objects.add(new RefractingSphere(-50,200,100,  100.85,  -0.65 ));
		
//		m = new material(0.98f,0.92f,0.85f, 0.00f, 400.0f, 0.0f);

	
	//	lights.add(new DirectionalLight(new Vector(0,1,0)));
	//	lights.add(new AreaLight(0,0,800,  70,0,0,   0,70,0, 4*0, 320));
		lights.add(new BasicLight(0,499.99,100,100));
		lights.add(new BasicLight(25,50.99,150,40));
		/*
		lights.add(new BasicLight(-70,-50,50, 65));

		Mandelbulb bulb = new Mandelbulb(0,310,0,130 ,m);
		objects.add(bulb);
	*/
	}
	public void init()
	{	
		boringinit();
		FastMath.makevalues();
		addthings();
		time.setTime();
		
		if(!loop) raycaster.cast(image,objects,lights);
		
	}
	
	public void config()
	{			
		makeStats();
		time.setTime();				
	}	
	public void paint(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D) g;  //	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if(loop){
			imageDone = false;
			image = new BufferedImage(swidth,sheight,BufferedImage.TYPE_INT_RGB);
			config();
			
			raycaster.cast(image,objects,lights);
			
			Graphics gi = image.getGraphics();
			drawStats(gi);
		}
		
			
		
			
		g2.drawImage(image,0,0,null);
		
		if(takeScreen && !tookScreen && imageDone)
			saveScreen();
		
		this.repaint();
	}

	public ImageInfoData getImageFile(String name)
	{
		String fileName=textureRoot+name+textureType;
		BufferedImage textureImage = null;
		try{
			textureImage = ImageIO.read(new File(fileName));}
		catch(IOException e){out.println("world getImageFile error!!@$$!@%##!!@");}
		return new ImageInfoData(textureImage);
	//if(textureImage==null)return null;
	//	DataBufferByte buf = (DataBufferByte)(textureImage.getRaster().getDataBuffer());	
	// 	return new ImageInfoData(buf.getData(),textureImage.getWidth(),textureImage.getHeight());
	}
	
	public void saveScreen()
	{
		String format = "jpg";
		while(!imageDone) pause(200);
		try{
			ImageIO.write(image,format,new File(screenDirectory+Math.random()+"."+format));}
		catch(IOException e){out.println("error world - saveScreen");}
		out.println("screenshot done");
		tookScreen = true;
	}
	
	
	
	

//************ KEYBOARD **************
	public void keyPressed(KeyEvent e)
	{
   		switch(e.getKeyCode())
     	 { 	  	
			 case KeyEvent.VK_SPACE :takeScreen=true; out.println("will take screenshot"); break;
			 case KeyEvent.VK_ESCAPE: System.exit(0);
	     }  
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
	      {
//			 case KeyEvent.VK_RIGHT : right=false;break;		     	  
	      }
	}
	public void keyTyped(KeyEvent e){}
	
	
	
//************** MOUSE **************
	public void mouseMoved(MouseEvent e)
	{
		mousex=e.getX();
		mousey=e.getY();
	}		
	public void mouseDragged(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}



	public void makeStats()
	{
		double passed = time.getTimePassed();
		double fps = 1000.0/passed;
		String stringfps = String.format("%.2f",fps);
		stats[0] = "fps: " + stringfps;
	}		
	public void drawStats(Graphics g)
	{
		g.setColor(new Color(200,0,255));
		for(int i=0; i < stats.length; i++)
			g.drawString(stats[i],150,50*i+100);
	}
	public void boringinit()
	{
		this.setVisible(true);
 		this.addKeyListener(this);
        this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent evt) {
        System.exit(0);}});
	}	
	public void pause(int p)
	{
		try{Thread.sleep(p);} 
		catch(InterruptedException e){}
	}
	
	public void update(Graphics g) 
	{
	    Image offscreen = null;
	    Graphics offgc = null;
	    if(offscreen == null)
	    {
	        offscreen = createImage((int)swidth, (int)sheight);
	        offgc = offscreen.getGraphics();
	    }
	    offgc.setColor(getBackground());
	    offgc.fillRect(0, 0, (int)swidth, (int)sheight);
	    offgc.setColor(getForeground());
	    paint(offgc);
	    // transfer offscreen to window
	    g.drawImage(offscreen, 0, 0, this);
	
	}
	public static int getswidth(){return swidth;}
	public static int getsheight(){return sheight;}		
}