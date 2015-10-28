package supermario.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import supermario.Utilities;

public class AnimatedIcon extends ImageIcon{
	private static final long serialVersionUID = 1L;
	private ImageIcon[] images;
	private double wait = 0;
	private int index = 0;
	private int height, width;
	//private int tick = 0, counter = 0;
	public AnimatedIcon(ImageIcon[] images){
		this.images = images;
		this.height = images[0].getIconHeight();
		this.width = images[0].getIconWidth();
	
	}
	public int getIconHeight(){ return height; }
	public int getIconWidth(){ return width; }
	public AnimatedIcon(BufferedImage bimg,int expectedHeight){
		int height = bimg.getHeight();
		this.width = bimg.getWidth();
		this.height = expectedHeight;
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		//if(bimg.getWidth()!=16)bimg = Utilities.resize(16, height, bimg);
		for(int y = 0; y < height; y+=expectedHeight){
			images.add(Utilities.cropImage(bimg, 0, y, bimg.getWidth(), expectedHeight));
		}
		this.images = new ImageIcon[images.size()];
		for(int i = 0; i < images.size();i++){
			this.images[i] = new ImageIcon(images.get(i));
		}
	}
	public Image getImage(){
		Image i = images[index].getImage();
		
		double temp = Utilities.ticks;//wait++;
		if(wait != temp){
			double diff = Math.abs(temp-wait);
			if(diff > 15)	
				index++;
			
			wait = temp;
			
			//index++;
		}
		
		if(index >= images.length)index = 0;
		return i;
	}
	public ImageIcon[] getImages(){
		return images;
	}

}
