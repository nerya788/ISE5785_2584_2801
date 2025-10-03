package renderer;

/**
 * Manages the distribution of pixels to multiple threads for rendering. This
 * class is thread-safe and ensures that each pixel is processed exactly once.
 * It also handles progress printing to the console. Based on the course
 * instructions for Mini-Project 2.
 */
public class PixelManager {

	/**
	 * A simple immutable record to represent a pixel's coordinates.
	 */
	public record Pixel(int row, int col) {
	}

	private final int nX;
	private final int nY;
	private final long totalPixels;

	/**
	 * The index of the next pixel to be processed. Using long to avoid overflow for
	 * large images.
	 */
	private long nextPixel = 0;
	/** The number of pixels that have been completed. */
	private long pixelsDone = 0;
	/** The interval for printing progress updates (0 means no printing). */
	private final double printInterval;
	/** The time when the rendering process started. */
	private final long startTime;

	/**
	 * Constructs a PixelManager for a given image resolution and print interval.
	 * 
	 * @param nY            The height of the image (number of rows).
	 * @param nX            The width of the image (number of columns).
	 * @param printInterval The interval (in seconds) for printing progress. 0 to
	 *                      disable.
	 */
	public PixelManager(int nY, int nX, double printInterval) {
		this.nY = nY;
		this.nX = nX;
		this.totalPixels = (long) nX * nY;
		this.printInterval = printInterval;
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Atomically retrieves the next pixel to be rendered. This method is
	 * synchronized to ensure thread safety.
	 * 
	 * @return The next {@link Pixel} to process, or {@code null} if all pixels have
	 *         been dispatched.
	 */
	public synchronized Pixel nextPixel() {
		if (nextPixel >= totalPixels) {
			return null;
		}

		long pixelIndex = nextPixel++;
		int row = (int) (pixelIndex / nX);
		int col = (int) (pixelIndex % nX);

		return new Pixel(row, col);
	}

	/**
	 * Notifies the manager that a pixel has been completed. This method is
	 * synchronized and handles progress printing.
	 */
	public synchronized void pixelDone() {
		pixelsDone++;
		if (printInterval > 0) {
			// Simple progress printing without flooding the console.
			// A more advanced implementation from the slides might use a separate object
			// for printing.
			if (pixelsDone % 1000 == 0 || pixelsDone == totalPixels) {
				long currentTime = System.currentTimeMillis();
				double elapsedTime = (currentTime - startTime) / 1000.0;
				double progress = (double) pixelsDone / totalPixels * 100;
				System.out.printf("\rRendering progress: %.2f%%, Elapsed time: %.2f seconds", progress, elapsedTime);
				if (pixelsDone == totalPixels) {
					System.out.println(); // New line after completion
				}
			}
		}
	}
}