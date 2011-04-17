package sequenceSummary;
/**
 * A general use exception for you to throw if you want to.
 * You CAN add additional constructors for this exception if you
 * would like.
 *
 */
public class SequenceSummaryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the exception
	 * @param s message
	 */
	public SequenceSummaryException(String s)
	{
		super(s);
	}
}
