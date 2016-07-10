// 
// Decompiled by Procyon v0.5.29
// 

package supermario;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import sun.awt.image.ToolkitImage;
import supermario.game.AnimatedIcon;
import supermario.game.Game;
import supermario.game.Textures;
import supermario.game.sprites.misc.Firebar;
import supermario.game.sprites.misc.Platform;

public class Utilities
{
    public static Textures textures;
    private static final byte[] cypher;
    private static final ImageIcon icon;
    private static BufferedImage letterBImage;
    public static String BLANK_CURSOR_DESCRIPTION;
    public static Cursor blankCursor;
    public static Cursor defaultCursor;
    private static double oscXPeriod;
    private static double oscYPeriod;
    public static double ticks;
    private static int pulsingIndex;
    private static double firebarThetaSlow;
    private static double firebarThetaFast;
    private static double firebarTicksSlow;
    private static double firebarTicksFast;
    private static int fireballImageIndex;
    private static double fireballTicks;
    private static boolean pulseGettingDarker;
    private static int backupPulsingIndex;
    private static boolean backupGettingDarker;
    private static double backupTicks;
    
    public static String setDefaultLookAndFeel() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                return UIManager.getSystemLookAndFeelClassName();
            }
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            return UIManager.getCrossPlatformLookAndFeelClassName();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return "";
        }
    }
    
    private static ImageIcon initIcon() {
        try {
            return new ImageIcon(decrypt(getBytes("images/icon", Game.class)));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void setIcon(final Frame frame) {
        if (Utilities.icon != null) {
            frame.setIconImage(Utilities.icon.getImage());
        }
    }
    
    public static void showAbout(final Component component) {
        String about = "<html><b>Super Mario Bros. NES Game & Builder</b></html>";
        about += "\r\nUpdated on "+Game.LATEST_DATE+". Version "+Game.VERSION;
        about += "\r\n<html>All rights to <b>Nintendo(R)</b></html>";
        about += "\r\nOriginal game by Andrew Kellogg";
        about += "\r\nemail: AirmanAJK@yahoo.com";
        about += "\r\nMod by Rick Meyer";
        about += "\r\nemail: sirrichardmeyer@gmail.com";
        JOptionPane.showMessageDialog(component, about, "About", 0, Utilities.icon);
    }
    
    private static byte[] getCypher() {
        try {
            return String.valueOf(new char[] { 'M', 'a', 'r', 'i', 'o', 's' }).getBytes(Game.ENCODING);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static byte[] getBytes(final String path, final Class<?> c) throws Exception {
        final BufferedInputStream bufStream = new BufferedInputStream(c.getResourceAsStream(path));
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        for (b = 0, b = bufStream.read(); b != -1; b = bufStream.read()) {
            byteStream.write(b);
        }
        bufStream.close();
        byteStream.close();
        return byteStream.toByteArray();
    }
    
    public static byte[] decrypt(final byte[] bytes) {
        int pos = 0;
        for (int bytePos = 0; bytePos < bytes.length; ++bytePos) {
            bytes[bytePos] ^= Utilities.cypher[pos++ % Utilities.cypher.length];
        }
        return bytes;
    }
    
    public static int getDisplayNumber(final Frame comp) {
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice[] devices = environment.getScreenDevices();
        if (!comp.isVisible()) {
            final GraphicsDevice defaultDevice = environment.getDefaultScreenDevice();
            for (int i = 0; i < devices.length; ++i) {
                if (defaultDevice.equals(devices[i])) {
                    return i;
                }
            }
        }
        final Rectangle[] deviceBounds = new Rectangle[devices.length];
        for (int i = 0; i < devices.length; ++i) {
            deviceBounds[i] = devices[i].getConfigurations()[0].getBounds();
        }
        final Integer[] intersectionAreas = new Integer[deviceBounds.length];
        int maxArea = 0;
        int maxDisplay = 0;
        final Point screenLoc = comp.getLocationOnScreen();
        final Rectangle compRect = new Rectangle(screenLoc.x, screenLoc.y, comp.getWidth(), comp.getHeight());
        for (int j = 0; j < intersectionAreas.length; ++j) {
            if (compRect.intersects(deviceBounds[j])) {
                final Rectangle intersection = compRect.intersection(deviceBounds[j]);
                intersectionAreas[j] = intersection.width * intersection.height;
                if (intersectionAreas[j] > maxArea) {
                    maxArea = intersectionAreas[j];
                    maxDisplay = j;
                }
            }
        }
        return maxDisplay;
    }
    
    public static Rectangle getDisplayBounds(final int displayNumber) {
        final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice[] devices = environment.getScreenDevices();
        return devices[displayNumber].getConfigurations()[0].getBounds();
    }
    
    public static Point getFrameCenter(final Frame comp) {
        final Point screenLoc = comp.getLocationOnScreen();
        return new Point(screenLoc.x + comp.getWidth() / 2, screenLoc.y + comp.getHeight() / 2);
    }
    
    public static void centerOnPoint(final Frame comp, final Point p) {
        comp.setLocation(p.x - comp.getWidth() / 2, p.y - comp.getHeight() / 2);
    }
    
    public static void centerOnDisplay(final Frame comp) {
        final Rectangle displayRect = getDisplayBounds(getDisplayNumber(comp));
        final int x = displayRect.x + displayRect.width / 2 - comp.getWidth() / 2;
        final int y = displayRect.y + displayRect.height / 2 - comp.getHeight() / 2;
        comp.setLocation(x, y);
    }
    
    public static void correctScreenLocation(final Frame comp) {
        final Rectangle displayRect = getDisplayBounds(getDisplayNumber(comp));
        final Point compLoc = comp.getLocationOnScreen();
        final Point newScreenLoc = new Point(compLoc);
        if (compLoc.x < displayRect.x) {
            newScreenLoc.x = displayRect.x;
        }
        else if (compLoc.x + comp.getWidth() > displayRect.x + displayRect.width) {
            newScreenLoc.x = displayRect.x + displayRect.width - comp.getWidth();
        }
        if (compLoc.y < displayRect.y) {
            newScreenLoc.y = displayRect.y;
        }
        else if (compLoc.y + comp.getHeight() > displayRect.y + displayRect.height) {
            newScreenLoc.y = displayRect.y + displayRect.height - comp.getHeight();
        }
        if (!compLoc.equals(newScreenLoc)) {
            comp.setLocation(newScreenLoc);
        }
    }
    
    public static void takeSnapshot(final Game game) {
        final BufferedImage screenshot = new BufferedImage(Game.renderWidth, Game.renderHeight, 5);
        final Graphics2D g2D = (Graphics2D)screenshot.getGraphics();
        game.level.draw(g2D);
        game.drawOverlay(g2D);
        try {
            ImageIO.write(screenshot, "png", new File(game.lastGameName + "_" + System.currentTimeMillis() + ".png"));
        }
        catch (IOException ex) {}
    }
    
    public static void drawAtTile(final Graphics2D g2D, final Image image, final int xTile, final int yTile) {
        g2D.drawImage(image, 8 * xTile, 8 * yTile, null);
    }
    
    public static void drawTextAtTiles(final Graphics2D g2D, final String text, final int xTile, final int yTile) {
        drawText(g2D, text, xTile * 8, yTile * 8, null, 1.0f);
    }
    
    public static void drawTextAtTiles(final Graphics2D g2D, final String text, final int xTile, final int yTile, final Color color, final float composite) {
        drawText(g2D, text, xTile * 8, yTile * 8, color, composite);
    }
    
    public static void drawTextAtPixels(final Graphics2D g2D, final String text, final int xPixel, final int yPixel) {
        drawText(g2D, text, xPixel, yPixel, null, 1.0f);
    }
    
    public static void drawTextAtPixels(final Graphics2D g2D, final String text, final int xPixel, final int yPixel, final Color color, final float composite) {
        drawText(g2D, text, xPixel, yPixel, color, composite);
    }
    
    private static void drawText(final Graphics2D g2D, String text, int xPixel, final int yPixel, final Color color, final float composite) {
        text = text.toUpperCase();
        final char[] chars = text.toLowerCase().toCharArray();
        final Image[] textImages = new Image[text.length()];
        for (int i = 0; i < chars.length; ++i) {
            final ImageIcon ii = Utilities.textures.symbols.get(chars[i]);
            textImages[i] = ((ii == null) ? Utilities.textures.symbols.get('\ufffb').getImage() : ii.getImage());
        }
        final Graphics2D bGraphics = (Graphics2D)Utilities.letterBImage.getGraphics();
        for (int j = 0; j < textImages.length; ++j) {
            if (color == null && composite == 1.0f) {
                g2D.drawImage(textImages[j], xPixel, yPixel, null);
            }
            else {
                bGraphics.setComposite(AlphaComposite.getInstance(3));
                bGraphics.fillRect(0, 0, 8, 8);
                bGraphics.setComposite(AlphaComposite.getInstance(5, 1.0f));
                bGraphics.drawImage(textImages[j], 0, 0, null);
                bGraphics.setComposite(AlphaComposite.getInstance(5, composite));
                bGraphics.setColor(color);
                bGraphics.fillRect(0, 0, 8, 8);
                g2D.drawImage(Utilities.letterBImage, xPixel, yPixel, null);
            }
            xPixel += 8;
        }
    }
    
    public static void updatePeriodicTheta(final double delta) {
        Utilities.firebarTicksSlow += delta;
        Utilities.firebarTicksSlow %= Firebar.FIREBAR_PERIOD_SLOW;
        Utilities.firebarThetaSlow = Utilities.firebarTicksSlow / Firebar.FIREBAR_PERIOD_SLOW * 2.0 * 3.141592653589793;
        Utilities.firebarTicksFast += delta;
        Utilities.firebarTicksFast %= Firebar.FIREBAR_PERIOD_FAST;
        Utilities.firebarThetaFast = Utilities.firebarTicksFast / Firebar.FIREBAR_PERIOD_FAST * 2.0 * 3.141592653589793;
    }
    
    public static double getPeriodicTheta(final boolean fastFirebar) {
        if (fastFirebar) {
            return Utilities.firebarThetaFast;
        }
        return Utilities.firebarThetaSlow;
    }
    
    public static void resetSynchronizedSprites() {
        Utilities.oscXPeriod = (Utilities.oscYPeriod = 0.0);
        Utilities.firebarThetaSlow = (Utilities.firebarThetaFast = 0.0);
        Utilities.firebarTicksSlow = (Utilities.firebarTicksFast = 0.0);
    }
    
    public static void updateFireballImageIndex(final double delta) {
        Utilities.fireballTicks += delta;
        if (Utilities.fireballTicks > 70.0) {
            if (++Utilities.fireballImageIndex == 4) {
                Utilities.fireballImageIndex = 0;
            }
            Utilities.fireballTicks -= 70.0;
        }
    }
    
    public static void updateOscillatingPlatformPeriods(final double delta) {
        Utilities.oscXPeriod = (Utilities.oscXPeriod + delta) % Platform.TOTAL_OSC_X_PERIOD;
        Utilities.oscYPeriod = (Utilities.oscYPeriod + delta) % Platform.TOTAL_OSC_Y_PERIOD;
    }
    
    public static double getXPlatformOscillationPeriod() {
        return Utilities.oscXPeriod;
    }
    
    public static double getYPlatformOscillationPeriod() {
        return Utilities.oscYPeriod;
    }
    
    public static void updatePulsingImageIndex(final double delta) {
        Utilities.ticks += delta;
        if (Utilities.pulsingIndex == 0) {
            if (Utilities.ticks >= 380.0) {
                ++Utilities.pulsingIndex;
                Utilities.pulseGettingDarker = true;
                Utilities.ticks -= 380.0;
            }
        }
        else if (Utilities.pulsingIndex == 1) {
            if (Utilities.ticks >= 140.0) {
                if (Utilities.pulseGettingDarker) {
                    ++Utilities.pulsingIndex;
                }
                else {
                    --Utilities.pulsingIndex;
                }
                Utilities.ticks -= 140.0;
            }
        }
        else if (Utilities.pulsingIndex == 2 && Utilities.ticks >= 140.0) {
            --Utilities.pulsingIndex;
            Utilities.pulseGettingDarker = false;
            Utilities.ticks -= 140.0;
        }
    }
    
    public static int getPulsingImageIndex() {
        return Utilities.pulsingIndex;
    }
    
    public static void savePulsingState() {
        Utilities.backupPulsingIndex = Utilities.pulsingIndex;
        Utilities.backupGettingDarker = Utilities.pulseGettingDarker;
        Utilities.backupTicks = Utilities.ticks;
    }
    
    public static void restorePulsingState() {
        Utilities.pulsingIndex = Utilities.backupPulsingIndex;
        Utilities.pulseGettingDarker = Utilities.backupGettingDarker;
        Utilities.ticks = Utilities.backupTicks;
    }
    
    public static void resetPulsingImageIndex() {
        Utilities.pulsingIndex = 0;
        Utilities.pulseGettingDarker = true;
        Utilities.ticks = 0.0;
    }
    
    static {
        cypher = getCypher();
        icon = initIcon();
        Utilities.letterBImage = new BufferedImage(8, 8, 2);
        Utilities.BLANK_CURSOR_DESCRIPTION = "invisibleCursor";
        Utilities.blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, 2), new Point(0, 0), Utilities.BLANK_CURSOR_DESCRIPTION);
        Utilities.defaultCursor = Cursor.getDefaultCursor();
        Utilities.pulseGettingDarker = true;
    }
    public static BufferedImage horizontalFlip(Image img) { 
    	return horizontalFlip((BufferedImage)img);
    }
    /** Horizontally flips img. */
	public static BufferedImage horizontalFlip(BufferedImage img) {   
		//BufferedImage img = (BufferedImage)image;
        int w = img.getWidth();   
        int h = img.getHeight();   
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());     
        Graphics2D g = dimg.createGraphics();   
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);   
        g.dispose();   
        return dimg;   
    }  
	public static BufferedImage horizontalFlip(ToolkitImage tk){
		tk.getWidth();
		return horizontalFlip(tk.getBufferedImage());
	}
	public static BufferedImage resize(int newWidth, int newHeight, BufferedImage img){
		BufferedImage newImg = toBufferedImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
		return newImg;
	}
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	public static Image resizeImage(Image bimg, int width, int height){
		//scales image
		Image img = bimg.getScaledInstance(width, height, 0);
		//output image
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		
		return bimage;
	}
	
	public static BufferedImage cropImage(BufferedImage original,int x, int y,int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(original, -x,-y, original.getWidth(), original.getHeight(), null);
		g.dispose();
		return resizedImage;
	}
	public static ImageIcon cropIcon(ImageIcon original, int x, int y, int width, int height){
		if(original instanceof AnimatedIcon){
			AnimatedIcon icon  = (AnimatedIcon)original;
			ImageIcon[] images = icon.getImages();
			ImageIcon[] newimages = new ImageIcon[images.length];
			for(int i = 0; i < images.length; i++){
				newimages[i] = cropIcon(images[i],x,y,width,height);
			}
			return new AnimatedIcon(newimages);
			
		}else{
			return new ImageIcon(cropImage((BufferedImage)original.getImage(),x,y,width,height));
		}
	}
	public static ImageIcon addImages(ImageIcon base, ImageIcon add, String direction){
		BufferedImage bimg;
		if(direction.equals("down")){
			int width; 
			if(base.getIconWidth()>add.getIconWidth())width = base.getIconWidth();
			else width = add.getIconWidth();
			bimg = cropImage((BufferedImage)base.getImage(),0,0,width,base.getIconHeight()+add.getIconHeight());
			Graphics2D g = bimg.createGraphics();
			g.drawImage(add.getImage(),0,base.getIconHeight(),null);
			g.dispose();
		}else if(direction.equals("up")){
			int width; 
			if(base.getIconWidth()>add.getIconWidth())width = base.getIconWidth();
			else width = add.getIconWidth();
			bimg = cropImage((BufferedImage)add.getImage(),0,0,width,base.getIconHeight()+add.getIconHeight());
			Graphics2D g = bimg.createGraphics();
			g.drawImage(base.getImage(),0,add.getIconHeight(),null);
			g.dispose();
		}else if(direction.equals("left")){
			int height; 
			if(base.getIconHeight()>add.getIconHeight())height = base.getIconHeight();
			else height = add.getIconHeight();
			bimg = cropImage((BufferedImage)add.getImage(),0,0,base.getIconWidth()+add.getIconWidth(),height);
			Graphics2D g = bimg.createGraphics();
			g.drawImage(base.getImage(),add.getIconWidth(),0,null);
			g.dispose();
		}else if(direction.equals("right")){
			int height; 
			if(base.getIconHeight()>add.getIconHeight())height = base.getIconHeight();
			else height = add.getIconHeight();
			bimg = cropImage((BufferedImage)base.getImage(),0,0,base.getIconWidth()+add.getIconWidth(),height);
			Graphics2D g = bimg.createGraphics();
			g.drawImage(add.getImage(),base.getIconWidth(),0,null);
			g.dispose();
		}else if(direction.equals("overlay")){
			bimg = (BufferedImage)base.getImage();
			Graphics2D g = bimg.createGraphics();
			g.drawImage(add.getImage(),0,0,null);
			g.dispose();
		}else{
			bimg = (BufferedImage) base.getImage();
		}
		return new ImageIcon(bimg);
	}
}
