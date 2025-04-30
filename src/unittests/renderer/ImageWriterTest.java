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
	 * Test method for {@link renderer.ImageWriter#ImageWriter(int, int)}.
	 */
	@Test
	void testImageWriter() {
		ImageWriter image(800,500,BufferedImage.TYPE_INT_RGB);
	}

}
