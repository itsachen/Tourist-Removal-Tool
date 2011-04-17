package imageManagement;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import sequenceSummary.SequenceSummaryException;
import sequenceSummary.Sorter;


/**
 * Represents a sequence of frames (video).
 * Provides utilities for returning a VideoCanvas (a GUI interface),
 * individual PixelImages from the sequence (indexed by time), and
 * single arrays of a particular x-y-position in the image over all time t.
 * 
 * Note that your sorting algorithms--via Sorter--are used to sequence the 
 * frames.
 */
public class FrameSequence 
{
	//number of frames in sequence
	private int numFrames;
	//pix width
	private int width;
	//pix height
	private int height;
	private Sorter.sortType sort;
	//longitudinal pixel lists; indexed by <x><y><pixeltype>.
	//the arraylist itself is indexed by time.
	//private ArrayList<Pixlet>[][][] pixStream;
	//list of all pixel images
	private ArrayList<PixelImage> frames;
	
	/**
	 * Constructs a frame sequence given a directory name 
	 * where all of the files are stored.
	 * The directory should contain a set of jpeg images, each of
	 * which has a number associated with it.  All items in the directory
	 * which are not of the proper format will be ignored.
	 * The jpg files must be of the following format:
	 * <i>fname</i>_<i>num</i>.jpg
	 * They will be sequenced according to the ordering defined by <i>num</i>.<br>
	 * <b>Note that YOUR SORTING ALGORITHMS (via Sorter) will 
	 * be used to sequence the frames.</b><br>
	 * 
	 * Example: say you have a directory "dir" with 4 files inside of it:
	 * pic_1.jpg, august_45.jpg, party_21.jpg, and format3.jpg
	 * Then the FrameSequence produced will be made up of the first three images;
	 * the last is not in the correct format and will be ignored.  The frame
	 * sequence will contain:
	 * pic_1.jpg, party_21.jpg, august_45.jpg  
	 * 
	 * @param dirName the name of the directory to be loaded from.
	 * @param sort the type of sorting algorithm to use to load the frame sequence.
	 * @throws IOException 
	 */
	public FrameSequence(File dirName, Sorter.sortType sort) throws IOException, SequenceSummaryException
	{
		this.sort = sort;
		load(dirName);
	}
	/**
	 * SHOULD ONLY be used in the the video extension.  Constructor for creating
	 * a video from a set of frames.
	 * @param arr
	 * @param sort
	 * @throws IOException
	 * @throws SequenceSummaryException
	 */
	public FrameSequence(ArrayList<PixelImage> arr, Sorter.sortType sort) throws IOException, SequenceSummaryException
	{
		this.sort = sort;
		this.frames = arr;
		this.numFrames = arr.size();
		if (numFrames==0)
		{
			this.width=0;
			this.height=0;
		}
		else
		{
			this.width = arr.get(0).getWidth();
			this.height=arr.get(0).getHeight();
		}
	}
	/**
	 * Loads the given file and performs a lot of preprocessing--
	 * saves each image and also constructs sets of pixlets
	 * @param fname
	 * @throws IOException
	 * @throws SortException 
	 */
	private void load(File f) throws IOException, SequenceSummaryException
	{
		numFrames = 0;
		frames = new ArrayList<PixelImage>();
		//File f = new File(fname);
		if (!f.isDirectory() || !f.exists())
			throw new IOException("Directory does not exist");
		File[] files = f.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name){

                if(name.toLowerCase().matches(SortFile.getFileFormat())){
                	return true;
                }
                return false;
            }
		});
		Sorter<SortFile> sorter = new Sorter<SortFile>();
		SortFile[] sFiles = new SortFile[files.length];
		for (int i = 0; i<files.length; i++){
			sFiles[i]= new SortFile(files[i]);
		}
		sorter.sort(this.sort, sFiles);
		boolean first = true;
		for (SortFile file:sFiles){
			numFrames++;
			PixelImage pi = new RGBPixelImage(file.getFile());
			this.frames.add(pi);
			if (first){
				first = false;
				this.width = pi.getWidth();
				this.height = pi.getHeight();
			}
		}
		
	}
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getNumFrames(){
		return numFrames;
	}

	/**
	 * Returns the image which is at time t in the frame sequence.
	 * Note that frame sequences are 0-indexed (the first image is at
	 * time 0).
	 * @param t the time/index of the image to be returned.
	 * @return the image at index t.
	 */
	public PixelImage getImage(int t) throws ImageManagementException
	{
		if (t<0 || t>=numFrames)
			throw new ImageManagementException("Not valid time in sequence: "+t+" "+numFrames);
		return this.frames.get(t);
	}

	/**
	 * Returns the array of pixlets at position (x,y) with color c
	 * over all time (0 to max time)
	 * @param x the x position
	 * @param y the y position
	 * @param c the colorType of the set of pixlets to return
	 * @return an array of pixlets of the required color and at position
	 * (x,y) over all time t.
	 */
	public Pixlet[] getPixletArray(int x, int y, Pixlet.colorType c) throws ImageManagementException
	{
		if(x<0||x>=width||y<0||y>=height)
			throw new ImageManagementException("Pixel coords out of bounds!");
		Pixlet[] pixx = new Pixlet[this.numFrames];
		int ind = 0;
		for(PixelImage im: frames)
		{
			pixx[ind] = im.getPixlet(x, y, c);
			ind++;
		}
		return pixx;
	}
	
	
}
/**
 * Requires: correct format for files.
 *
 */
class SortFile implements Comparable<SortFile>
{
	private static String ending = ".jpg";
	private File f;
	private int i;

	public static String getFileFormat()
	{
		return ".*_[0-9]+"+ending;
	}
	/**
	 * Requires: f has the correct format.
	 * @param f
	 */
	public SortFile(File f)
	{
		this.f = f;
		String s = f.getName();
		String[] splits = s.split("_");
		String x = splits[splits.length-1];
		x = x.replace(ending, "").trim();
		i = Integer.valueOf(x);
	}
	public File getFile()
	{
		return f;
	}
	@Override
	public int compareTo(SortFile o) {
		return this.i - o.i;
	}
	@Override
	public boolean equals(Object o){
		if (!(o instanceof SortFile))
			return false;
		return this.compareTo((SortFile)o)==0;
	}
}
