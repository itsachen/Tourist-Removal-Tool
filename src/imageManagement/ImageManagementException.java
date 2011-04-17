package imageManagement;
/**
 * Represents an exception which can be thrown in the
 * ImageManagement package, if you so desire.
 *
 */
public class ImageManagementException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 * @param s exception message
	 */
	public ImageManagementException(String s)
	{
		super(s);
	}
}
