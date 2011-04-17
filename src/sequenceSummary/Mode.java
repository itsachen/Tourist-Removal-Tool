package sequenceSummary;

import java.util.HashMap;

/**
 * Represents the summary statistic mode.
 * The mode is the value that occurs most frequently in a dataset.
 * If two or more values occur with the same max frequency, returning either
 * is fine.
 *
 * @param <T> The type, which must implement Comparable, to find the mode over.
 */
public class Mode<T extends Comparable<T>> implements SummaryStatistic<T> {
	
	HashMap<T, Integer> map;
	
	/**
	 * Constructor for the mode object.  Note that you can have any fields 
	 * you want (including sort algorithms, if you desire),
	 *  but you must implement this constructor so that it takes no parameters.
	 * @throws SequenceSummaryException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 */
	public Mode() throws SequenceSummaryException{
		map = new HashMap<T, Integer>();
	}
	
	
	@Override
	public T getValue(T[] arr) throws SequenceSummaryException {
		
		// initialize the largestKey to the first element, which will be updated later.
		T largestKey = arr[0];
		int largestValue = 1;
		
		// if the value is already in the map, increment the count. otherwise put the value in the map with count 1.
		for (int i = 0; i < arr.length; i++) {
			if (map.containsKey(arr[i])) {
				map.put(arr[i], map.get(arr[i]) + 1);
			} else {
				map.put(arr[i], 1);
			}
		}
		
		// look for the largest key by checking the value associated with the key.
		for (int i = 0; i < arr.length; i++) {
			if (map.get(arr[i]) > largestValue) {
				largestKey = arr[i];
				largestValue = map.get(arr[i]);
			}
		}
		return largestKey;
	}
}