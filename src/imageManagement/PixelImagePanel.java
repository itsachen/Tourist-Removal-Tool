package imageManagement;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * This is a Panel that holds an PixelImage and displays it. 
 * It allows for interaction with the mouse, but you will be responsible
 * for implementing this functionality.  In addition, you may want 
 * to modify it/add additional methods to implement BoundingBox.
 */
public class PixelImagePanel extends JPanel implements MouseListener
{

	private int x= 0;
	private int y= 0;
	
	private int x2= 0;
	private int y2= 0;
	
	public static Point pointOne;
	public static Point pointTwo;
	
	
	/** Keeps track if this is the first or second click */
	int clickcounter =0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PixelImage image;
	/**
	 * Creates a new PixelImagePanel.
	 * @param image
	 */
	public PixelImagePanel(PixelImage image){
		this.setImage(image);
		this.setBounds(0, 0, image.getWidth(), image.getHeight());
		this.addMouseListener(this);
	}
	
	/**Getters for the points */
	public static Point getPointOne(){
		return pointOne;
	}
	
	public static Point getPointTwo(){
		return pointTwo;
	}

	/**
	 * 
	 * @return the ACTUAL image contained in this panel, NOT a copy.
	 */
	public PixelImage getPixelImage()
	{
		return image;
	}
	/**
	 * Sets the image represented by this object to the parameter image.
	 * @param image
	 */
	public void setImage(PixelImage image){
		
		this.image = image;
		this.setSize(image.getWidth(),image.getHeight());
		Dimension d = this.getSize();
		this.setPreferredSize(d);
		this.repaint();	
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0, 0, null);
		g.setColor(Color.RED);
		//g.drawRect(x, y, Math.abs(x2-x), Math.abs(y2-y));
		g.drawRect(Math.min(x,x2),Math.min(y,y2), Math.abs(x2-x), Math.abs(y2-y));
	}
	//TODO:
	//The following methods are from implementing MouseListener; you probably
	//want to implement some of them (although you only need to implement ones that
	//you actually utilize in your GUI).
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (clickcounter == 0){
			
			x= (int) e.getPoint().getX();
			y= (int)e.getPoint().getY();
			pointOne= e.getPoint();
			System.out.println("Point 1: "+ e.getPoint().toString());
			clickcounter++;
			repaint();	
		}
		else if (clickcounter == 1){
			x2= (int) e.getPoint().getX();
			y2= (int) e.getPoint().getY();
			pointTwo= e.getPoint();
			System.out.println("Point 2: "+e.getPoint().toString());
			clickcounter = 0;
			repaint();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {	
	}
}
