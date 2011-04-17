package imageManagement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Represents a type of RGB image.  You should not need to modify it.
 *
 */
public class RGBPixelImage extends PixelImage
{	
	private Pixlet.colorType[] colors;
	/**
	 * Constructs an RGB image of the given file.
	 * @param file 
	 * @throws IOException
	 */
	public RGBPixelImage(File file) throws IOException{
		super(file);
		this.colors = Arrays.copyOf(Pixlet.colorType.values(), 
				Pixlet.colorType.values().length);
	}
	/**
	 * Constructs an "empty" RGB Pixel image with the given
	 * height and width.
	 * @param width
	 * @param height
	 */
	public RGBPixelImage(int width, int height)
	{
		super(width, height, BufferedImage.TYPE_INT_RGB);
		this.colors = Arrays.copyOf(Pixlet.colorType.values(), 
				Pixlet.colorType.values().length);
	}
	
	@Override
	public void setPixlet(Pixlet p)
	{
		//Uses bitwise modification to set the correct value
		int pixel = this.getImage().getRGB(p.getX(), p.getY());
		int pixletval = p.getValue();
		switch (p.getPixType()){
		case R:
			pixel = (pixel&0xff00ffff)|(pixletval<<16);
			break;
		case G:
			pixel = (pixel&0xffff00ff)|(pixletval<<8);
			break;
		case B:
			pixel = (pixel&0xffffff00)|pixletval;
			break;
		}
		this.getImage().setRGB(p.getX(), p.getY(), pixel);
	}
	@Override
	public Pixlet.colorType[] getColorTypes()
	{
		return colors;
	}
	@Override
	public Pixlet getPixlet(int x, int y, Pixlet.colorType type)
	{
		//Uses bitwise modification to get the correct value
		int pixel = this.getImage().getRGB(x, y);
		switch (type){
		case R:
			pixel = (pixel<<8)>>>24;
			break;
		case G:
			pixel = (pixel<<16)>>>24;
			break;
		case B:
			pixel = (pixel<<24)>>>24;
			break;
		}
		return new Pixlet(type,x,y,pixel);
	}
}
