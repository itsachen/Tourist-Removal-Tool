package imageManagement;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 * This is a panel that uses the FrameSequence to 
 * display all the frames as if it were a movie.
 * Right now it has a variable wait that specifies
 * the time in milliseconds to wait in between frames. 
 * This could be changed to rate if necessary
 */
public class VideoPanel extends JLabel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean run = false; //Used to go through the frames and stop if necessary
	private int nextFrame;
	//wait between frames, in milliseconds.
	private int wait;
	private FrameSequence fs;
	private PixelImagePanel pip;
	private ActionListener startL, stopL;
	/**
	 * Create a new VideoPanel, a JPanel which can display the framesequence fs.
	 * @param fs frameSequence
	 * @param waitTime number of milliseconds between frames.
	 * @throws ImageManagementException
	 */
	public VideoPanel(FrameSequence fs, int waitTime) throws ImageManagementException{
		super();
		wait = waitTime;
		this.nextFrame = 1;
		this.fs = fs;
		pip = new PixelImagePanel(fs.getImage(0));
		this.add(pip);
		this.setSize(pip.getWidth(),pip.getHeight());
		Dimension d = this.getSize();
		this.setPreferredSize(d);
		repaint();
		this.startL = new StartActionListener();
		this.stopL = new StopActionListener();
		(new Thread(this)).start();
	}
	/**
	 * Create a new VideoPanel, a JPanel which can display the framesequence fs.
	 * Uses default wait time of 50 ms/frame
	 * @param fs frameSequence
	 * @throws ImageManagementException
	 */
	public VideoPanel(FrameSequence fs) throws ImageManagementException{
		this(fs,50);
			
	}
	/**
	 * 
	 * @return the index of the current frame being displayed.
	 */
	public int getCurrentFrame()
	{
		return (this.nextFrame+fs.getNumFrames()-1)%fs.getNumFrames();
	}
	/**
	 * 
	 * @return the pause between frames, in milliseconds.
	 */
	public int getWaitTime()
	{
		return this.wait;
	}
	/**
	 * 
	 * @param wait the new value to set as the pause between frames,
	 * in milliseconds.
	 */
	public void setWaitTime(int wait)
	{
		this.wait = wait;
	}
	/**
	 * Returns an ActionListener object which has implemented
	 * the actionPerformed method so that when the ActionListener
	 * is activated, it will start the video in the video panel
	 * @return actionListener which can be used to start the video.
	 */
	public ActionListener getStartActionListener()
	{
		return startL;
	}
	/**
	 * Returns an ActionListener object which has implemented the
	 * actionPerformed method so that when the ActionListener
	 * is activated, it will stop the video in the video panel
	 * @return actionListener which can be used to stop the video.
	 */
	public ActionListener getStopActionListener()
	{
		return stopL;
	}
	
	
	@Override
	public void run() throws RuntimeException
	{
		while(true){
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (run && nextFrame<fs.getNumFrames()){
				try {
					pip.setImage(fs.getImage(nextFrame));
				} catch (ImageManagementException e) {
					RuntimeException rr= new RuntimeException();
					rr.setStackTrace(e.getStackTrace());
					throw rr;
				}
				nextFrame++;
				if (nextFrame == fs.getNumFrames()){
					nextFrame = 0;
					run = false;
				}
			}
			
		}
	}
	private class StartActionListener implements ActionListener{
		public void actionPerformed(ActionEvent evt)
		{
			if (nextFrame >= fs.getNumFrames())
				nextFrame =0;
			run = true;
		}
	}
	private class StopActionListener implements ActionListener{
		public void actionPerformed(ActionEvent evt)
		{
			run = false;
			//currFrame = fs.getNumFrames();
		}
	}
}
