package sequenceSummary;
import java.awt.Point;
import imageManagement.FrameSequence;
import imageManagement.ImageManagementException;
import imageManagement.PixelImage;
import imageManagement.Pixlet;
import imageManagement.RGBPixelImage;

/**
 * Contains functionality for summarizing a frame sequence and returning 
 * and image representing this summary.
 *
 */
public class SequenceSummarizer
{
	private FrameSequence seq;
	/**
	 * Given a FrameSequence, returns a PixelImage which summarizes
	 * the information using the appropriate SummaryStatistic
	 * @param seq
	 */
	
	public SequenceSummarizer(FrameSequence seq)throws SequenceSummaryException
	{
		this.seq = seq;
	}

	/**
	 * Returns a new PixelImage composed of two different components:
	 * for the area inside the bounding box defined by points p1 and p2,
	 * returns the pixels which summarize the sequence using 
	 * summarystatistic summStat.  For the area of the image outside of
	 * the bounding box, returns the pixels from the image at index
	 * currFrame in the frame sequence.
	 * 
	 * @param summStat the summary statistic object to use in calculation 
	 * over the sequence represented by the sequenceSummarizer.
	 * @param p1 one point used to form the bounding box
	 * @param p2 the second point used to form the bounding box.
	 * @param currFrame the time/index of the frame to use in the area
	 * outside the bounding box.
	 * @return a PixelImage where the summary statistic is calculated
	 * over all pixels in the image.
	 * @throws SequenceSummaryException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 * @throws ImageManagementException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 */
	public PixelImage getSummary(SummaryStatistic<Pixlet> summStat, 
			Point p1, Point p2, int currFrame) 
			throws SequenceSummaryException, ImageManagementException
	{
		// initialize new PixelImage of the same size as the frame sequence images
		PixelImage image = new RGBPixelImage(seq.getWidth(), seq.getHeight());

		// calculate the start and end points for bounding box.
		int xMin = (int) Math.min(p1.getX(), p2.getX());
		int xMax = (int) Math.max(p1.getX(), p2.getX());
		int yMin = (int) Math.min(p1.getY(), p2.getY());
		int yMax = (int) Math.max(p1.getY(), p2.getY());
		
		// iterate through each point in the bounding box
		for (int x = xMin; x <= xMax; x++) {
			for (int y = yMin; y <= yMax; y++) {
				
				// get three Pixlet arrays at the current position, one for each color type.
				Pixlet[] arrayR = seq.getPixletArray(x, y, Pixlet.colorType.R);
				Pixlet[] arrayG = seq.getPixletArray(x, y, Pixlet.colorType.G);
				Pixlet[] arrayB = seq.getPixletArray(x, y, Pixlet.colorType.B);
				
				// get the summary for each color type Pixlet
				Pixlet r = summStat.getValue(arrayR);
				Pixlet g = summStat.getValue(arrayG);
				Pixlet b = summStat.getValue(arrayB);
				
				// set each Pixlet at the current position for the new image to be returned.
				image.setPixlet(r);
				image.setPixlet(g);
				image.setPixlet(b);
 			}
		}
		
		// iterate through all points of the image
		for (int x = 0; x < seq.getWidth(); x++) {
			for (int y = 0; y < seq.getHeight(); y++) {
				
				// if the current point is outside of the bounding box, set the Pixlet to the Pixlet of the currFrame
				if (!(x >= xMin && x <= xMax && y >= yMin && y <= yMax)) {
					image.setPixlet(seq.getImage(currFrame).getPixlet(x, y, Pixlet.colorType.R));
					image.setPixlet(seq.getImage(currFrame).getPixlet(x, y, Pixlet.colorType.G));
					image.setPixlet(seq.getImage(currFrame).getPixlet(x, y, Pixlet.colorType.B));
				}
			}
		}
		
		return image;
	}
	/**
	 * Returns a summary image of the sequence of images
	 * provided in the constructor
	 * of this SequenceSummarizer using the summarystatistic summStat.
	 * 
	 * @param summStat the summary statistic object to use in calculation 
	 * over the sequence represented by the sequenceSummarizer.
	 * @return a PixelImage where the summary statistic is calculated
	 * over all pixels in the image.
	 * @throws SequenceSummaryException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 * @throws ImageManagementException (Note that although we allow you
	 * to throw an exception in this method if you so choose, you do not
	 * need to do so.)
	 */
	public PixelImage getSummary(SummaryStatistic<Pixlet> summStat) 
			throws SequenceSummaryException, ImageManagementException{
		
		PixelImage image = new RGBPixelImage(seq.getWidth(), seq.getHeight());

		// iterate through all points in the image
		for (int x = 0; x < seq.getWidth(); x++) {
			for (int y = 0; y < seq.getHeight(); y++) {
				
				// get three Pixlet arrays at the current position, one for each color type.
				Pixlet[] arrayR = seq.getPixletArray(x, y, Pixlet.colorType.R);
				Pixlet[] arrayG = seq.getPixletArray(x, y, Pixlet.colorType.G);
				Pixlet[] arrayB = seq.getPixletArray(x, y, Pixlet.colorType.B);
				
				// get the summary for each color type Pixlet
				Pixlet r = summStat.getValue(arrayR);
				Pixlet g = summStat.getValue(arrayG);
				Pixlet b = summStat.getValue(arrayB);
				
				// set each Pixlet at the current position for the new image to be returned.
				image.setPixlet(r);
				image.setPixlet(g);
				image.setPixlet(b);
 			}
		}
		return image;
	}
	
}
