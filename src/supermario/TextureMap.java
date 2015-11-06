package supermario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import supermario.game.Textures;
import static supermario.debug.Debugger.*;

public class TextureMap{
	private Image map;
	private static final String dir = System.getProperty("user.dir");
	private String folderin;

	public TextureMap(String filename) throws Textures.MessageException{
		try {
			map = ImageIO.read(new File(filename));
			int i = filename.lastIndexOf('/');
			folderin = filename.substring(0,i+1);
		} catch (IOException e) {
			if(filename.startsWith("images/")){
				//e.printStackTrace();
				//System.out.println("Error reading "+filename);
				//System.exit(0);
				throw new Textures.MessageException(e,"Error reading "+filename);
			}else{
				try {
					int i = filename.indexOf('/');
					String loc = filename.substring(i+1);
					filename = "images/"+loc;
					map = ImageIO.read(new File(filename));
					i = filename.lastIndexOf('/');
					folderin = filename.substring(0,i+1);
				} catch (IOException f) {
					//e.printStackTrace();
					//System.out.println("Error reading "+filename);
					throw new Textures.MessageException(f,"Error reading "+filename);
				}
			}
		}
	}
	public ImageIcon getTile(int tilex, int tiley){
		int x = tilex*16;
		int y = tiley*16;
		Image img = this.crop(x,y,16,16);
		return new ImageIcon(img);
	}
	public ImageIcon getSubImage(int x, int y, int width, int height){
		return new ImageIcon(crop(x,y,width,height));
	}
	public ImageIcon getTallSprite(int tilex, int tiley){
		return new ImageIcon(crop(tilex*16,tiley*16,16,32));
	}
	/*public ImageIcon applyPalette(ImageIcon imgicon, TextureMap map, String name){
		return applyPalette(imgicon,map.folderin+name);
	}*/
	public String palette(String name){
		return folderin+name+".png";
	}
	private static BufferedImage safeCopy(BufferedImage bi){
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm,raster,isAlphaPremultiplied,null);
	}
	public ImageIcon get16xSprite(int x, int y){ return getSubImage(x,y,16,16); }
	public static ImageIcon applyPalette(ImageIcon imgicon, String name){
			BufferedImage img = null;
			try {
				
				img = ImageIO.read(new File(name));
			} catch (IOException e) {
				if(name.startsWith("images/")){
					//e.printStackTrace();
					error(new Exception(),"Error reading "+name);
					//System.exit(0);
					return imgicon;
				}else{
					try{
						int i = name.indexOf('/');
						String loc = name.substring(i+1);
						name = "images/"+loc;
						img = ImageIO.read(new File(name));
					}catch(IOException f){
						if(name.startsWith("images/")){
							//f.printStackTrace();
							//System.out.println("Error reading "+dir+name);
							//System.exit(0);
							return imgicon;
						}
					}
				}
			}
			if(img.getHeight()==1)return imgicon;
			int[] original = new int[img.getWidth(null)];
			int[] replace = new int[original.length];
			for(int x = 0; x < img.getWidth(null); x++){
				original[x] = img.getRGB(x, 0);
				replace[x] = img.getRGB(x,1);
			}
			BufferedImage orig = (BufferedImage)imgicon.getImage();
			BufferedImage result = safeCopy(orig);
			Color trans = new Color(255,255,255,0);
			for(int y = 0; y < imgicon.getIconHeight();y++){
				for(int x = 0; x < imgicon.getIconWidth(); x++){
					int rgb = orig.getRGB(x,y);
					//result.setRGB(x,y,rgb);x
					for(int i = 0; i < original.length; i++){
						if(original[i] == rgb){
							result.setRGB(x, y, replace[i]);
							break;
						}else{
							result.setRGB(x,y,trans.getRGB());
						}
					}
				}
			}
			return new ImageIcon(result);
			
	
	}
	private Image crop(int x, int y,int width, int height){
		Image resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) resizedImage.getGraphics();
		g.drawImage(map, -x,-y, map.getWidth(null), map.getHeight(null), null);
		g.dispose();
		return resizedImage;
	}
}
