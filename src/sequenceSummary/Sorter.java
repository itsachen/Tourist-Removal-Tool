package sequenceSummary;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Sorter<T> represents a set of sorting algorithms over a generic object
 * type.  It has only one public method, which can perform any of the sorting
 * algorithms, depending on the sortType provided.
 * 
 * @param <T> the type (must be comparable)
 */
public class Sorter<T extends Comparable<T>> {

	static Random rand = new Random();
	/**
	 * Enum representing the various sort algorithms.
	 */
	public static enum sortType {
		INSERTION_SORT, MERGE_SORT, QUICK_SORT, HEAP_SORT
	};
	/**
	 * Constructor
	 * @throws SequenceSummaryException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 * 
	 */
	public Sorter() throws SequenceSummaryException{
		//TODO: implement me!
	}

	/**
	 * Sorts the elements in arr by the value in the KeyValue object (the
	 * elements of type V) (performs in-place sorting.)
	 * 
	 * @param type the type of sorting algorithm to use.
	 * @param arr the array to sort
	 * @throws SequenceSummaryException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 */
	
	
	/**
	 * Based on the input sortType the array is sorted
	 */
	public void sort(Sorter.sortType type, T[] arr) throws SequenceSummaryException {
		//TODO: implement me!
		if (type == sortType.INSERTION_SORT) {
			insertionSort(arr);
		} else if (type == sortType.MERGE_SORT) {
			mergeSort(arr);
		} else if (type == sortType.QUICK_SORT) {
			quickSort(arr, 0, arr.length-1);
		} else {
			heapSort(arr);
		}
	}
	
	/**
	 * An implmentation of Insertion Sort
	 * @param x Array that is to be sorted
	 */
	 private void insertionSort(T[] x) {

	      // scan through all elements
	      for (int i = 1; i < x.length; i++) {
	         // invariant is: x[0],...,x[i-1] are sorted
	         // now find rightful position for x[i]
	         T tmp = x[i];
	         int j;
	         // move x[i] into sorted portion
	         for (j = i; j > 0 && x[j-1].compareTo(tmp) > 0; j--) x[j] = x[j-1];
	         x[j] = tmp;
	      }
	 }
	 
	 /**
	  * An implementation of Merge Sort
	  * @param x Array that is to be sorted
	  */
	 private void mergeSort(T[] x) {
		 mergeSortHelper(x, 0, x.length, Arrays.copyOf(x, x.length));
	   }

	   // sort the portion of Comparable array x between lo
	   // (inclusive) and hi (exclusive), using y as temp
	   // does not touch other parts of y or x
	 
	 /**
	  * Merge Sort helper method
	  */
	 private void mergeSortHelper(T[] x, int lo, int hi, T[] y) {

		 // base case
		 if (hi <= lo + 1) return; // nothing to do
	      
	      // at least 2 elements -- split and recursively sort
	      int mid = (lo + hi)/2;
	      mergeSortHelper(x, lo, mid, y);
	      mergeSortHelper(x, mid, hi, y);
	      merge(x, lo, mid, hi, y); // merge sorted sublists
	   }

	   // merge 2 subarrays of x, using y as temp
	   // subarrays are between lo and mid and between
	   // mid and hi, respectively
	   
	 /**
	  * Merge Sort helper method
	  */
	 private void merge(T[] x, int lo, int mid, int hi, T[] y) {
	      int i = lo; // subarray pointers
	      int j = mid;
	      int k = lo; // destination pointer

	      while (i < mid && j < hi) {
	         y[k++] = (x[i].compareTo(x[j]) > 0)? x[j++] : x[i++];
	      }
	      
	      // one of the subarrays is empty
	      // copy remaining elements from the other
	      System.arraycopy(x, i, y, k, mid - i);
	      System.arraycopy(x, j, y, k, hi - j);
	      // now copy everything back to original array
	      System.arraycopy(y, lo, x, lo, hi - lo);
	   }

	   private void quickSort(T[] a, int lo0, int hi0)  {
			
			int lo = lo0;
			int hi = hi0;
			
			
			if (lo >= hi) 
				return;
			
			int mid = (lo + hi) / 2;
			
			// sort the first, middle, last elements
			medianOfThree(a, lo, mid, hi);
			
			// set pivot to median of first, middle, last
			T pivot = a[(lo + hi) / 2];
			
			
			//throw values less than the pivot to the right of the pivot
			//throw values greater than the pivot to the right of the pivot
			while (lo < hi) {
				
				while (lo<hi && a[lo].compareTo(pivot) < 0){
					lo++;
				
				}//end while
				
				while (lo<hi && a[hi].compareTo(pivot) > 0){ 
					hi--;
		
				}//end while
				
				if (lo < hi) {
					T x = a[lo];
					a[lo] = a[hi];
					a[hi] = x;
					lo++;
					hi--;

				}//end if
			}//end while
			
			//if the indices ran past each other, swap them
			if (hi < lo) {
				int T = hi;
				hi = lo;
				lo = T;

			}//end if
			
			//sort each half
			quickSort(a, lo0, lo);
			quickSort(a, lo == lo0 ? lo+1 : lo, hi0);
		}//end quickSort

	   /**
	    *  Sorts the first, middle, and last elements
	    * @param arr Array that is to be sorted
	    * @param lo Index of one element
	    * @param mid Index of another element
	    * @param hi Index of the last element
	    */
	   private void medianOfThree(T[] arr, int lo, int mid, int hi) {
		   order(arr, lo, hi);
		   order(arr, mid, hi);
		   order(arr, lo, mid);
	   }
	   
	   /**
	    * Orders two given elements
	    * @param arr Array that contains the elements
	    * @param i Index of first element
	    * @param j Index of other element
	    */
	   private void order(T[] arr, int i, int j) {
		   if (arr[i].compareTo(arr[j]) > 0) {
			   swap(arr, i, j);
		   }
	   }

	   
	   /**
	    * An implementation of heapsort
	    * @param x Array that is to be sorted
	    */
	   private void heapSort(T[] x) {
		   // first build a heap
		   buildHeap(x);
		   // swap the first and last element
		   swap(x, 0, x.length-1);
		   // reheap using heapify and then swap the top element with the second-to-last element, and so on.
		   // continuously reheap and swap to put all elements in order.
		   for (int i = x.length-2; i > 0; i--) {
			   heapify(x, 0, i);
			   swap(x, 0, i);
		   }
	   }
	   
	   /**
	    * Builds a max heap by calling heapifying from the leaf closest to the end of the array and working toward arr[1]
	    */
	   private void buildHeap(T[] arr) {
		   for (int i = arr.length/2-1; i >= 0; i--) {
			   heapify(arr, i, arr.length-1);
		   }
	   }
	   
	   /**
	    * Swaps two elements in an array
	    * @param arr Array that is to be sorted
	    * @param i Index of one element
	    * @param j Index of other element
	    */
	   private void swap(T[] arr, int i, int j) {
		   T temp = arr[i];
		   arr[i] = arr[j];
		   arr[j] = temp;
	   }
	   
	   /**
	    * Transforms a semiheap into a heap
	    * @param arr semiheap that is to be transformed
	    */
	   private void heapify(T[] arr, int root, int last) {
		   int left = 2*root+1;

		   boolean done = false;
		   
		   // stores the root element into a temporary variable
		   T temp = arr[root];
		   while (!done && left <= last) {
			   int right = left + 1;
			   // first assume left child is largest child
			   int largest = left;
			   
			   // find the largest child of the root by comparing left and right
			   if (right <= last && arr[right].compareTo(arr[left]) > 0) {
				   largest = right;
			   }
			   // if the temp is smaller than the largest child, then the largest child is copied into the
			   // current root location, and the indices of root and left child are updated. The while loop continues
			   // Essentially, this puts the temp into the correct location without swapping all the elements.
			   // The value at the root in the parameter simply sinks down into the correct location.
			   if (temp.compareTo(arr[largest]) < 0) {
				   arr[root] = arr[largest];
				   root = largest;
				   left = 2*root + 1;
			   } else {     					// current location is correct location of temp
				   done = true;
			   }			   
		   }
		   // puts the temp, which was originally at the root in the parameter, into the correct position
		   arr[root] = temp;
	   }

}