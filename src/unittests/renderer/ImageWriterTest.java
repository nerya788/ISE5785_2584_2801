/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import renderer.*;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import primitives.Color;


/**
 * 
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#ImageWriter(int nX, int nY)}.
	 */
	@Test
	void testImageWriter() {
		   ImageWriter images = new ImageWriter(800,500);
		   for (int i = 0; i < 800; i++) {
			   for (int j = 0; j < 500; j++) {
				   
				   if (i % 50 == 0)
					   images.writePixel(i, j, new Color(225,0,0));
				   else if (j % 50 == 0)
					   images.writePixel(i, j, new Color(225,0,0));
				   else
					   images.writePixel(i, j, new Color(225,225,0));
			   }
		   }
		   
		   
		   for (int i = 0; i < 800; i++) 
			   images.writePixel(i, 499, new Color(225,0,0));
		   for (int j = 0; j < 500; j++)
			   images.writePixel(799, j, new Color(225,0,0));

		   images.writeToImage("final");
	}
}
