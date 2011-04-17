package sequenceSummary;

import imageManagement.FrameSequence;
import imageManagement.PixelImage;
import imageManagement.Pixlet;
import imageManagement.VideoPanel;
import imageManagement.ImageManagementException;

import java.awt.*;

import java.net.*;
import java.awt.event.*;

import javax.swing.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import javax.swing.event.*;

import sequenceSummary.Sorter;

import java.io.File;
import java.io.IOException;

/**
 * The GUI for the Tourist Removal Tool
 * @author Anthony Chen and Peter Joe
 */
public class Display extends JFrame implements ActionListener, ChangeListener {
	
	/** Constants that are changed through interaction with the GUI */
	Sorter.sortType sortType= Sorter.sortType.INSERTION_SORT;
	int framespeed= 50;
	double percent= 0.5;
	long runtime;
	int summarytype= 0; //This designates whether the option is percentile or mode. 0 for percentile, 1 for mode.
	
	
	//SpinnerNumberModels
	SpinnerNumberModel fss =
        new SpinnerNumberModel(50,		//initial value
                               50, 		//min
                               500,		//max
                               10);  	//step
	SpinnerNumberModel imageper =
        new SpinnerNumberModel(.5,		//initial value
                               0, 		//min
                               1, 		//max
                               .01);	//step
	
	//JComboBox lists
	private String[] algoList = { "Insertion sort", "Merge sort", "Quicksort", "Heapsort"};
	private String[] sumList = { "Percentile", "Mode"};
	
	//Buttons
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");
	private JButton browse = new JButton("Browse");
	private JButton save = new JButton("Save");
	private JButton createSumm = new JButton("Create summary image from box");
	private JButton refresh = new JButton("Refresh summary image");
	
	//JSpinners
	private JSpinner frame = new JSpinner(fss);
	private JSpinner imagepercent = new JSpinner(imageper);
	
	//lol
	public double rainbow; //ALL THE WAY ACROSS THE SKY
	
	//JComboBox
	private JComboBox algoBox = new JComboBox(algoList);
	private JComboBox sumBox = new JComboBox(sumList);
	
	//JTextArea
	private JTextArea time= new JTextArea(1,8);
	
	//Panels
	private JPanel pnlEast = new JPanel(); //East section
	private JPanel pnlWest = new JPanel(); //West section 
	private JPanel pnlVideo = new JPanel(); //Video panel
	private JPanel pnlVideoControl= new JPanel(); //Video control panel
	private JPanel pnlSummary = new JPanel(); //Summary panel
	private JPanel pnlIO = new JPanel(); //Browse and save
	private JPanel pnlAlg = new JPanel(); //Algorithm stuff
	private JPanel pnlSummaryI = new JPanel(); //Summary Image controls
	private JPanel pnlSummaryTop = new JPanel();
	
	//JFileChooser stuffs
	private File dirName;
	private File saveName;
	
	//Custom panels
	private FrameSequence fs;
	private VideoPanel vp;
	private SequenceSummarizer ss;
	private imageManagement.PixelImage pi;
	private imageManagement.PixelImagePanel pipanel;
	
	/**
	 * Main method to initalize the GUI
	 * @param args
	 */
	public static void main(String[] args) {
		Display gui = new Display();
		gui.launchFrame();
	}
    
	/**
	 * Constructor for the GUI. Initializes all of the components and listeners,
	 * sets up layout managers, handles panel assignments, adds components, changes sizes,
	 * and makes the borders.
	 */
    public Display() {	
    	
    	//JFrame initialization
    	setTitle("Tourist Removal Tool"); //Title @#
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	//JComboBox initializaion
    	algoBox.setSelectedIndex(0);
    	sumBox.setSelectedIndex(0);
    	
    	//Text field initialization
    	time.setEditable(false);
    	
    	//Initializing ActionListeners
    	browse.addActionListener(this);
    	save.addActionListener(this);
    	algoBox.addActionListener(this);
    	sumBox.addActionListener(this);
    	createSumm.addActionListener(this);
    	refresh.addActionListener(this);
    	
    	//Intializing ChangeListeners
    	frame.addChangeListener(this);
    	imagepercent.addChangeListener(this);
    	
    	//Setting LayoutManagers
    	getContentPane().setLayout(new BorderLayout());
    	pnlEast.setLayout(new BoxLayout(pnlEast, BoxLayout.PAGE_AXIS));
    	pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.PAGE_AXIS));
    	pnlSummaryI.setLayout(new BoxLayout(pnlSummaryI, BoxLayout.PAGE_AXIS));

    	//Panel assignments
    	getContentPane().add(pnlWest, BorderLayout.WEST);
    	getContentPane().add(pnlEast, BorderLayout.EAST);
    	pnlWest.add(pnlVideo);
    	pnlWest.add(pnlVideoControl);
    	pnlWest.add(pnlSummary);
    	
    	pnlEast.add(pnlIO);
    	pnlEast.add(pnlAlg);
    	pnlEast.add(pnlSummaryI);

    	
    	//Filler space
    	Dimension minSize = new Dimension(5, 90);
    	Dimension prefSize = new Dimension(5, 90);
    	Dimension maxSize = new Dimension(Short.MAX_VALUE, 90);
    	pnlWest.add(new Box.Filler(minSize, prefSize, maxSize));
    	
    	Dimension minSize2 = new Dimension(5, 540);
    	Dimension prefSize2 = new Dimension(5, 540);
    	Dimension maxSize2 = new Dimension(Short.MAX_VALUE, 540);
    	pnlEast.add(new Box.Filler(minSize2, prefSize2, maxSize2));
    	
    	//Border settings
    	pnlVideoControl.setBorder(BorderFactory.createTitledBorder("Video Control"));
    	pnlVideo.setBorder(BorderFactory.createTitledBorder("Video"));
    	pnlSummary.setBorder(BorderFactory.createTitledBorder("Summary Image"));
    	pnlIO.setBorder(BorderFactory.createTitledBorder("Browse/Save"));
    	pnlAlg.setBorder(BorderFactory.createTitledBorder("Sorting Algorithm Control"));
    	pnlSummaryI.setBorder(BorderFactory.createTitledBorder("Image Summary Control"));
    	
    	
    	//Adding components
    	pnlVideoControl.add(start);
    	pnlVideoControl.add(stop);
    	pnlVideoControl.add(frame);
    	
    	pnlIO.add(browse);
    	pnlIO.add(save);
    	
    	pnlAlg.add(algoBox);
    	pnlAlg.add(time);
    	
    	pnlSummaryTop.add(sumBox);
    	pnlSummaryTop.add(imagepercent);
    	
    	pnlSummaryI.add(pnlSummaryTop);
    	pnlSummaryI.add(refresh);
    	pnlSummaryI.add(createSumm);
    	createSumm.setAlignmentX(Component.CENTER_ALIGNMENT);
    	refresh.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	//Initializing the video panel and image summary panels
    	createVideoPanel();
    	createSequenceSummarizer();
    	
    	//Setting the sizes
    	setSize(680,880); //Size of whole display
    	pnlWest.setPreferredSize(new Dimension(420,800));
    	imagepercent.setPreferredSize(new Dimension(55,20));
    	pnlVideoControl.setPreferredSize(new Dimension(420,80)); //Size of video control panel
    	pnlVideo.setPreferredSize(new Dimension(440,440)); //Size of video panel
    	pnlSummary.setPreferredSize(new Dimension(440,440)); //Size of summary panel
    	
    }
    
    /**
     * Opens the browsing window and creates a FrameSequence and VideoPanel from the selected directory
     */
    public void createVideoPanel(){
    	
    	//File chooser stuffs
    	JFileChooser chooser = new JFileChooser();
    	chooser.setVisible(true);
    	chooser.setCurrentDirectory(new java.io.File("."));
    	chooser.setDialogTitle("Choose a directory");
    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	chooser.setAcceptAllFileFilterUsed(false);
    	
    	if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
    		dirName = chooser.getSelectedFile();
    	}
    	else {
    		System.out.println("No Selection!");
    	}
    	
    	//Frame sequence and videopanel creation
    	try {
    		
    		fs =  new FrameSequence(dirName, sortType);
    		vp= new VideoPanel(fs, framespeed);
    		
    		//Add to panel
    		pnlVideo.add(vp);
    		start.addActionListener(vp.getStartActionListener());
    		stop.addActionListener(vp.getStopActionListener());
    	}
    	catch (SequenceSummaryException e) {
    		System.out.println(e.getMessage());
    	}   	
    	catch (IOException e) {
    		System.out.println(e.getMessage());
    	}   	
    	catch (ImageManagementException e) {
    		System.out.println(e.getMessage());
    	}	
    }
    
    
    /** Creates the summary image */
    public void createSequenceSummarizer(){
    	
    	time.setText("ms"); 
    	
    	try {
    		long before= System.currentTimeMillis();
    		ss = new SequenceSummarizer(fs);
    		//Summary
    		if (summarytype == 0){
    			pi = ss.getSummary(new Percentile(percent, sortType));
    		}
    		else if (summarytype == 1){
    			pi = ss.getSummary(new Mode<Pixlet>());
    		}
    		long after= System.currentTimeMillis();
    		
    		
    		
    		pipanel = new imageManagement.PixelImagePanel(pi);
    		
    		pnlSummary.add(pipanel);
    		runtime= after - before;
    	}
    	
    	catch (SequenceSummaryException e){
    		System.out.println(e.getMessage());
    	}
    	
    	catch (ImageManagementException e){
    		System.out.println(e.getMessage());
    	}
    	time.insert(("" + runtime),0);
    }
    
    /** Creates the summary image from selected area */
    public void createBoxSequenceSummarizer(){
    	
    	time.setText("ms"); 
    	
    	try {
    		long before= System.currentTimeMillis();
    		ss = new SequenceSummarizer(fs);
    		//Summary
    		if (summarytype == 0){
    			pi = ss.getSummary(new Percentile<Pixlet>(percent, sortType), imageManagement.PixelImagePanel.getPointOne(), 
    					imageManagement.PixelImagePanel.getPointTwo(), vp.getCurrentFrame());
    		}
    		else if (summarytype == 1){
    			pi = ss.getSummary(new Mode<Pixlet>(), imageManagement.PixelImagePanel.getPointOne(), 
    					imageManagement.PixelImagePanel.getPointTwo(), vp.getCurrentFrame());
    			System.out.println("wat");
    		}
    		long after= System.currentTimeMillis();
    		
    		runtime= after - before;
    		
    		pipanel = new imageManagement.PixelImagePanel(pi);
    		
    		pnlSummary.add(pipanel);
    	}
    	
    	catch (SequenceSummaryException e){
    		System.out.println(e.getMessage());
    	}
    	
    	catch (ImageManagementException e){
    		System.out.println(e.getMessage());
    	}
    	time.insert(("" + runtime),0);
    }
    
    /**
     * Sets the JFrame to be visible
     */
    public void launchFrame() {
    	setVisible(true);
    }
    
    /**
     * Opens up a file browser that specifies the location for the image to be saved
     */
    public void saveDir(){
    	//File chooser stuffs
    	JFileChooser chooser = new JFileChooser();
    	chooser.setVisible(true);
    	chooser.setCurrentDirectory(new java.io.File("."));
    	
    	chooser.setDialogTitle("Choose a file name for the .jpg");
    	
    	chooser.setAcceptAllFileFilterUsed(false);
    	
    	if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { 
    		saveName = chooser.getSelectedFile();
    		System.out.println(saveName.toString());
    	}
    	else {
    		System.out.println("No Selection!");
    	}
    }   
	
    /**
     * The various actions that are performed when an ActionEvent is thrown
     */
	public void actionPerformed(ActionEvent e) {
		
		//Browse button
		if (e.getSource() == browse){
			pnlVideo.remove(vp);
			createVideoPanel();
			pnlVideo.revalidate();
			
			pnlSummary.remove(pipanel);
			createSequenceSummarizer();
			pnlSummary.revalidate();
			
			getContentPane().repaint();
		}
		
		//Save button
		if (e.getSource() == save){
			
			try {
				saveDir();
				pi.writeJPGImage(saveName.toString());
			}
			catch (IOException ex){
				System.out.println(ex.getMessage());
			}
		}
		
		//Algorithm selection box
		if (e.getSource() == algoBox){
			JComboBox cb = (JComboBox)e.getSource();
	        String algo = (String)cb.getSelectedItem();
	        
	        if (algo.equals("Insertion sort")){
	        	sortType = Sorter.sortType.INSERTION_SORT;
	        }
	        if (algo.equals("Merge sort")){
	        	sortType = Sorter.sortType.MERGE_SORT;
	        }
	        if (algo.equals("Quicksort")){
	        	sortType = Sorter.sortType.QUICK_SORT;
	        }
	        if (algo.equals("Heapsort")){
	        	sortType = Sorter.sortType.HEAP_SORT;
	        }   
		}		
		
		//Summary type selection
		if (e.getSource() == sumBox){
			JComboBox cb = (JComboBox)e.getSource();
	        String type = (String)cb.getSelectedItem();
	        
	        if (type.equals("Percentile")){
	        	summarytype= 0;
	        }
	        if (type.equals("Mode")){
	        	summarytype= 1;
	        }
		}
		
		//Refresh button
		if(e.getSource() == refresh){
			
			pnlSummary.remove(pipanel);
			createSequenceSummarizer();
			pnlSummary.revalidate();
			
			getContentPane().repaint();
		}
		
		//Button that says what frame the video is currently on
		if(e.getSource() == createSumm){
			
			pnlSummary.remove(pipanel);
			createBoxSequenceSummarizer();
			pnlSummary.revalidate();
			
			getContentPane().repaint();
		}
	}
	
	/**
     * The various actions that are performed when a ChangeEvent is thrown
     */
	public void stateChanged(ChangeEvent e){
		if (e.getSource() == frame){
			JSpinner temp= (JSpinner)e.getSource();
			framespeed= (Integer)temp.getValue();
		}
		
		if (e.getSource() == imagepercent){
			JSpinner temp= (JSpinner)e.getSource();
			percent= (Double)temp.getValue();
		}
	}
}