package imageManagement;
/**
 * Represents a pair of objects
 * @param <T> the pair type
 */
public class Pair<T>{
	private T t1, t2;
	public Pair(T t1, T t2){
		this.t1 = t1;
		this.t2 = t2;
	}
	/**
	 * 
	 * @return the first item in the pair
	 */
	public T getFirst(){return t1;}
	/**
	 * 
	 * @return the second item in the pair
	 */
	public T getSecond(){return t2;}
}
