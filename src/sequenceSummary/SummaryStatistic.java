package sequenceSummary;

public interface SummaryStatistic <T extends Comparable<T>>
{
	/**
	 * Returns the summary statistic's value over the array arr.
	 * For example, if the summary statistic is the median (50th percentile),
	 * and the summary statistic is over a set of pixel objects, then 
	 * arr would be an array of pixels and this would
	 * return the median pixel of the array arr.
	 * @param arr
	 * @return the summary statistic over arr
	 * @throws SummaryStatException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 * @throws SortException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 */
	public T getValue(T[] arr) throws SequenceSummaryException;
}
