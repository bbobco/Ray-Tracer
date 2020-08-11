import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import static java.lang.Math.*;

public class run
{
	public static void main(String args [])
	{
		//cursor transparency, just copied/pasted this 
		 int[] pixels = new int[16 * 16];
   	 	Image image = Toolkit.getDefaultToolkit().createImage(
        new MemoryImageSource(16, 16, pixels, 0, 16));
    	Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        image, new Point(0, 0), "invisibleCursor");
        ///////////end invisible cursor
    
		world screen = new world();
		screen.setSize(world.getswidth(),world.getsheight());
		screen.init();	
  		screen.setCursor(transparentCursor);  	
	}
}
