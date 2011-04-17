package imageManagement;
/**
 * 
 * Pixlet
 * A pixlet represents a single colour component of a pixel in
 * either a PixelImage or a FrameSequence.
 * It has an x-y position, a colorType value (representing the colour component type),
 * and a value.  3 pixlets together would make up a normal coloured pixel,
 * but because we want to be able to implement the Comparable interface,
 * we need to represent each color component separately.
 * 
 * Note that several utility functions have been implemented for your convenience
 * (and some are geared towards the extensions);
 * you may or may not want or need to utilize them in a general implementation
 *
 */
public class Pixlet implements Comparable<Pixlet>
{
	public enum colorType {R, G, B};
	private int x, y;
	private colorType pixType;
	private int value;
	/**
	 * Constructor
	 * @param pixType the type of pixlet,e.g. R, G, or B.
	 * @param x the x location of the pixel
	 * @param y the y location of the pixel
	 * @param val the pixlet value (0 to 255).
	 */
	public Pixlet(colorType pixType, int x, int y, int val)
	{
		this.pixType = pixType;
		this.x = x;
		this.y = y;
		this.value = val;
	}
	/**
	 * 
	 * @return the colortype of the pixlet
	 */
	public colorType getPixType()
	{
		return pixType;
	}
	/**
	 * 
	 * @return the x position of the pixlet
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * 
	 * @return the y position of the pixlet
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * 
	 * @return the pixlet value (0 to 255).
	 */
	public int getValue()
	{
		return value;
	}
	@Override
	public int compareTo(Pixlet o) {
		if (value == o.value)
			return 0;
		return value>o.value?1:-1;
	}
	@Override
	public boolean equals(Object o){
		if(! (o instanceof Pixlet))
			return false;
		return (this.compareTo((Pixlet)o)==0);
		
	}
	@Override
	public int hashCode()
	{
		return value;
	}
	/**
	 * Returns the pixlet obtained by taking the (absolute)
	 * difference of the current pixlet and the parameter,
	 * at the position specified by the current pixlet.
	 * (Useful for one of the extensions--but most likely not
	 * for the general project)
	 * @param p
	 * @return the pixlet representing the calculated difference
	 */
	public Pixlet getDiffPix(Pixlet p)
	{
		return new Pixlet(pixType, x, y, Math.abs(value-p.value));
	}
}
