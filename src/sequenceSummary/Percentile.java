package sequenceSummary;

/**
 * A percentile object provides a utility for finding the nth percentile over
 * an array of objects.  The percentile can be found by sorting the objects,
 * then iterating until the relevant element is found.
 * For example, if we have 201 objects and the Percentile represented is the median
 * (percentile = .5), then we would return the 100th object.
 * Note that if there are an even number of objects, the median (or other percentiles)
 * may be ambiguous; choose the lower value.  For example, if there are 200 objects 
 * and percentile = .5, return the 100th element.
 *
 * @param <T> the type to find the percentile over (must implement Comparable interface)
 */
public class Percentile<T extends Comparable<T>> implements SummaryStatistic<T>
{
	
	private double percentile;
	private Sorter<T> sorter;
	private Sorter.sortType type;
	/**
	 * Creates a new Percentile object, with the percentile set as the parameter
	 * percent and sorter algorithm of type sort.
	 * @param percent the percentile of this summary statistic.
	 */
	public Percentile(double percent, Sorter.sortType sort) 
								throws SequenceSummaryException
	{
		percentile = percent;
		sorter = new Sorter<T>();
		type = sort;
		
	}
	

	/**
	 * 
	 * @return the percentile of this summary statistic instance.
	 */
	public double getPercentile()
	{
		return percentile;
	}

	@Override
	public T getValue(T[] arr) throws SequenceSummaryException
	{
		// first sort the array
		sorter.sort(type, arr);
		
		// handle corner cases, where percentile is 0 or 100. Returns the first or last element, respectively.
		if (percentile == 0.0) {
			return arr[0];
		}
		if (percentile == 100.0) {
			return arr[arr.length-1];
		}
		
		// n represents the position in the array associated with a certain percentile.
		int n = (int) Math.floor(arr.length * percentile) -1;
		return arr[n];
	}
}
