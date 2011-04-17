package imageManagement;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A PixelImage provides our wrapper for images.  It provides
 * a way of abstracting away methods that you don't need to use.
 * A PixelImage represents an image with a specified width and height.
 * It contains functions which allow pixelwise manipulation of the image
 * (in fact, it provides pixlet-wise manipulation of the image)
 * and also contains a function which returns a GUI panel of the image.
 *
 */
public abstract class PixelImage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height;
	private BufferedImage image;
	/**
	 * Creates a new PixelImage given the file; throws
	 * exception if the file is not an image file.
	 * @param file
	 * @throws IOException
	 */
	public PixelImage(File file) throws IOException
	{
		image = ImageIO.read(file);
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	/**
	 * Creates a new (blank) pixelImage of the specified width,
	 * height, and type.
	 * @param width
	 * @param height
	 * @param bufImType
	 */
	public PixelImage(int width, int height, int bufImType)
	{
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, bufImType);
	}
	/**
	 * Returns the buffered image used in the implementation.
	 * Note that this is effectively a private method--it should
	 * only be used by children of PixelImage.
	 * @return a BufferedImage backing the PixelImage
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}
	/**
	 * Sets the buffered image used in this implementation.
	 * Note that this is effectively a private method--it should only be
	 * used by the children of PixelImage.
	 * @param img
	 */
	protected void setImage(BufferedImage img)
	{
		this.image = img;
	}
	/**
	 * Sets a pixlet at a particular location and type.
	 * @param p
	 */
	public abstract void setPixlet(Pixlet p);
	/**
	 * Returns all color types used in this image.
	 * @return the set of colorTypes
	 */
	public abstract Pixlet.colorType[] getColorTypes();
	/**
	 * Returns the pixlet at the specified location.
	 * A pixlet is definedz
	 * @param x
	 * @param y
	 * @param type
	 * @return the pixlet at the specified location.
	 */
	public abstract Pixlet getPixlet(int x, int y, Pixlet.colorType type);
	/**
	 * 
	 * @return width of image
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * 
	 * @return height of image
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * Returns a PixelImagePanel object of this particular image. 
	 * @return a PixelImagePanel object of this particular image.
	 */
	public PixelImagePanel getPanel()
	{
		return new PixelImagePanel(this);
	}
	/**
	 * Writes image out as a jpeg image, given the provided filename
	 * @param fileName the file to write out to.
	 * @throws IOException 
	 */
	public void writeJPGImage(String fileName) throws IOException
	{
		ImageIO.write(image, "jpg", new File(fileName));
	}
}
