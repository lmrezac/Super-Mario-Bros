// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import static supermario.debug.Debugger.debug;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TexturePacks
{
    private Textures bTextures;
    public boolean validTextures;
    private int texturePack;
    private ImageIcon displayPlatformLongRepUp;
    private ImageIcon displayPlatformLongRepDown;
    private ImageIcon displayPlatformLongOscV;
    private ImageIcon displayPlatformLongOscH;
    private ImageIcon displayPlatformLongFalling;
    private ImageIcon displayPlatformLongPulley;
    private ImageIcon displayPlatformShortRepUp;
    private ImageIcon displayPlatformShortRepDown;
    private ImageIcon displayPlatformShortOscV;
    private ImageIcon displayPlatformShortOscH;
    private ImageIcon displayPlatformShortFalling;
    private ImageIcon displayPlatformShortPulley;
    private ImageIcon displayPlatformLongRepUpSingle;
    private ImageIcon displayPlatformLongRepDownSingle;
    private ImageIcon displayPlatformShortRepUpSingle;
    private ImageIcon displayPlatformShortRepDownSingle;
    private ImageIcon displayPlatformExtraShortRepUpSingle;
    private ImageIcon displayPlatformExtraShortRepDownSingle;
    private ImageIcon displayPlatformExtraShortRepUp;
    private ImageIcon displayPlatformExtraShortRepDown;
    private ImageIcon displayBowserEnding;
    private ImageIcon displayFlagEndingGreen;
    private ImageIcon displayFlagEndingWhite;
    private ImageIcon iconLargeCastle;
    private ImageIcon iconSmallCastle;
    private ImageIcon iconCastleWall;
    private ImageIcon iconPlatformLongRepUp;
    private ImageIcon iconPlatformLongRepDown;
    private ImageIcon iconPlatformLongPulley;
    private ImageIcon iconPlatformShortRepUp;
    private ImageIcon iconPlatformShortRepDown;
    private ImageIcon iconPlatformShortPulley;
    private ImageIcon iconPlatformExtraShortRepUp;
    private ImageIcon iconPlatformExtraShortRepDown;
    private ImageIcon iconPlatformLongOscV;
    private ImageIcon iconPlatformShortOscV;
    private ImageIcon iconPlatformLongOscH;
    private ImageIcon iconPlatformShortOscH;
    private ImageIcon displayPlatformLongRepUpBackup;
    private ImageIcon displayPlatformLongRepDownBackup;
    private ImageIcon displayPlatformLongOscVBackup;
    private ImageIcon displayPlatformLongOscHBackup;
    private ImageIcon displayPlatformLongFallingBackup;
    private ImageIcon displayPlatformLongPulleyBackup;
    private ImageIcon displayPlatformShortRepUpBackup;
    private ImageIcon displayPlatformShortRepDownBackup;
    private ImageIcon displayPlatformShortOscVBackup;
    private ImageIcon displayPlatformShortOscHBackup;
    private ImageIcon displayPlatformShortFallingBackup;
    private ImageIcon displayPlatformShortPulleyBackup;
    private ImageIcon displayPlatformLongRepUpSingleBackup;
    private ImageIcon displayPlatformLongRepDownSingleBackup;
    private ImageIcon displayPlatformShortRepUpSingleBackup;
    private ImageIcon displayPlatformShortRepDownSingleBackup;
    private ImageIcon displayPlatformExtraShortRepUpSingleBackup;
    private ImageIcon displayPlatformExtraShortRepDownSingleBackup;
    private ImageIcon displayPlatformExtraShortRepUpBackup;
    private ImageIcon displayPlatformExtraShortRepDownBackup;
    private ImageIcon displayBowserEndingBackup;
    private ImageIcon displayFlagEndingGreenBackup;
    private ImageIcon displayFlagEndingWhiteBackup;
    private ImageIcon iconLargeCastleBackup;
    private ImageIcon iconSmallCastleBackup;
    private ImageIcon iconCastleWallBackup;
    private ImageIcon iconPlatformLongRepUpBackup;
    private ImageIcon iconPlatformLongRepDownBackup;
    private ImageIcon iconPlatformLongPulleyBackup;
    private ImageIcon iconPlatformShortRepUpBackup;
    private ImageIcon iconPlatformShortRepDownBackup;
    private ImageIcon iconPlatformShortPulleyBackup;
    private ImageIcon iconPlatformExtraShortRepUpBackup;
    private ImageIcon iconPlatformExtraShortRepDownBackup;
    private ImageIcon iconPlatformLongOscVBackup;
    private ImageIcon iconPlatformShortOscVBackup;
    private ImageIcon iconPlatformLongOscHBackup;
    private ImageIcon iconPlatformShortOscHBackup;
    
    public TexturePacks(final Textures bTextures) {
        this.texturePack = 0;
        this.bTextures = bTextures;
        this.validTextures = true;
        try {
            this.init();
        }
        catch (Exception e) {
            this.validTextures = false;
            e.printStackTrace();
        }
    }
    
    public int getTexturePack() {
        return this.texturePack;
    }
    
    public boolean setTexturePack(final int newTexturePack) {
    	debug(new Exception(),"setTexturePack (1) "+newTexturePack);
        if (newTexturePack == 0) {
            if (this.texturePack != 0) {
                this.switchToStandardTextures();
                return true;
            }
            return false;
        }
        else if (newTexturePack == 1) {
            if (this.texturePack != 1) {
                this.switchToLostLevelsTextures();
                return true;
            }
            return false;
        }
        else if (newTexturePack == 2){
        	if(this.texturePack != 2){
        		debug(new Exception(),"switch to SMB3 textures");
        		this.switchToSMB3Textures();
        		return true;
        	}
        	return false;
        }
        else {
            if (this.texturePack != 0) {
                this.switchToStandardTextures();
                return true;
            }
            return false;
        }
    }
    
    private void switchToLostLevelsTextures() {
        this.texturePack = 1;
        debug(new Exception(),"switchToLostLevelsTextures (builder)");
        this.bTextures.displayPlatformExtraShortRepUp = this.displayPlatformExtraShortRepUp;
        this.bTextures.displayPlatformShortRepUp = this.displayPlatformShortRepUp;
        this.bTextures.displayPlatformLongRepUp = this.displayPlatformLongRepUp;
        this.bTextures.displayPlatformExtraShortRepDown = this.displayPlatformExtraShortRepDown;
        this.bTextures.displayPlatformShortRepDown = this.displayPlatformShortRepDown;
        this.bTextures.displayPlatformLongRepDown = this.displayPlatformLongRepDown;
        this.bTextures.displayPlatformExtraShortRepUpSingle = this.displayPlatformExtraShortRepUpSingle;
        this.bTextures.displayPlatformShortRepUpSingle = this.displayPlatformShortRepUpSingle;
        this.bTextures.displayPlatformLongRepUpSingle = this.displayPlatformLongRepUpSingle;
        this.bTextures.displayPlatformExtraShortRepDownSingle = this.displayPlatformExtraShortRepDownSingle;
        this.bTextures.displayPlatformShortRepDownSingle = this.displayPlatformShortRepDownSingle;
        this.bTextures.displayPlatformLongRepDownSingle = this.displayPlatformLongRepDownSingle;
        this.bTextures.displayPlatformLongFalling = this.displayPlatformLongFalling;
        this.bTextures.displayPlatformLongPulley = this.displayPlatformLongPulley;
        this.bTextures.displayPlatformLongOscH = this.displayPlatformLongOscH;
        this.bTextures.displayPlatformLongOscV = this.displayPlatformLongOscV;
        this.bTextures.displayPlatformShortFalling = this.displayPlatformShortFalling;
        this.bTextures.displayPlatformShortPulley = this.displayPlatformShortPulley;
        this.bTextures.displayPlatformShortOscH = this.displayPlatformShortOscH;
        this.bTextures.displayPlatformShortOscV = this.displayPlatformShortOscV;
        this.bTextures.displayBowserEnding = this.displayBowserEnding;
        this.bTextures.displayFlagEndingGreen = this.displayFlagEndingGreen;
        this.bTextures.displayFlagEndingWhite = this.displayFlagEndingWhite;
        this.bTextures.iconLargeCastle = this.iconLargeCastle;
        this.bTextures.iconSmallCastle = this.iconSmallCastle;
        this.bTextures.iconCastleWall = this.iconCastleWall;
        this.bTextures.iconPlatformLongRepUp = this.iconPlatformLongRepUp;
        this.bTextures.iconPlatformLongRepDown = this.iconPlatformLongRepDown;
        this.bTextures.iconPlatformLongPulley = this.iconPlatformLongPulley;
        this.bTextures.iconPlatformShortRepUp = this.iconPlatformShortRepUp;
        this.bTextures.iconPlatformShortRepDown = this.iconPlatformShortRepDown;
        this.bTextures.iconPlatformShortPulley = this.iconPlatformShortPulley;
        this.bTextures.iconPlatformExtraShortRepUp = this.iconPlatformExtraShortRepUp;
        this.bTextures.iconPlatformExtraShortRepDown = this.iconPlatformExtraShortRepDown;
        this.bTextures.iconPlatformLongOscV = this.iconPlatformLongOscV;
        this.bTextures.iconPlatformShortOscV = this.iconPlatformShortOscV;
        this.bTextures.iconPlatformLongOscH = this.iconPlatformLongOscH;
        this.bTextures.iconPlatformShortOscH = this.iconPlatformShortOscH;
    }
    
    private void switchToSMB3Textures(){
    	this.texturePack = 2;
    	debug(new Exception(),"switchToSMB3Textures (builder)");
    }
    
    private void switchToStandardTextures() {
        this.texturePack = 0;
        this.bTextures.displayPlatformExtraShortRepUp = this.displayPlatformExtraShortRepUpBackup;
        this.bTextures.displayPlatformShortRepUp = this.displayPlatformShortRepUpBackup;
        this.bTextures.displayPlatformLongRepUp = this.displayPlatformLongRepUpBackup;
        this.bTextures.displayPlatformExtraShortRepDown = this.displayPlatformExtraShortRepDownBackup;
        this.bTextures.displayPlatformShortRepDown = this.displayPlatformShortRepDownBackup;
        this.bTextures.displayPlatformLongRepDown = this.displayPlatformLongRepDownBackup;
        this.bTextures.displayPlatformExtraShortRepUpSingle = this.displayPlatformExtraShortRepUpSingleBackup;
        this.bTextures.displayPlatformShortRepUpSingle = this.displayPlatformShortRepUpSingleBackup;
        this.bTextures.displayPlatformLongRepUpSingle = this.displayPlatformLongRepUpSingleBackup;
        this.bTextures.displayPlatformExtraShortRepDownSingle = this.displayPlatformExtraShortRepDownSingleBackup;
        this.bTextures.displayPlatformShortRepDownSingle = this.displayPlatformShortRepDownSingleBackup;
        this.bTextures.displayPlatformLongRepDownSingle = this.displayPlatformLongRepDownSingleBackup;
        this.bTextures.displayPlatformLongFalling = this.displayPlatformLongFallingBackup;
        this.bTextures.displayPlatformLongPulley = this.displayPlatformLongPulleyBackup;
        this.bTextures.displayPlatformLongOscH = this.displayPlatformLongOscHBackup;
        this.bTextures.displayPlatformLongOscV = this.displayPlatformLongOscVBackup;
        this.bTextures.displayPlatformShortFalling = this.displayPlatformShortFallingBackup;
        this.bTextures.displayPlatformShortPulley = this.displayPlatformShortPulleyBackup;
        this.bTextures.displayPlatformShortOscH = this.displayPlatformShortOscHBackup;
        this.bTextures.displayPlatformShortOscV = this.displayPlatformShortOscVBackup;
        this.bTextures.displayBowserEnding = this.displayBowserEndingBackup;
        this.bTextures.displayFlagEndingGreen = this.displayFlagEndingGreenBackup;
        this.bTextures.displayFlagEndingWhite = this.displayFlagEndingWhiteBackup;
        this.bTextures.iconLargeCastle = this.iconLargeCastleBackup;
        this.bTextures.iconSmallCastle = this.iconSmallCastleBackup;
        this.bTextures.iconCastleWall = this.iconCastleWallBackup;
        this.bTextures.iconPlatformLongRepUp = this.iconPlatformLongRepUpBackup;
        this.bTextures.iconPlatformLongRepDown = this.iconPlatformLongRepDownBackup;
        this.bTextures.iconPlatformLongPulley = this.iconPlatformLongPulleyBackup;
        this.bTextures.iconPlatformShortRepUp = this.iconPlatformShortRepUpBackup;
        this.bTextures.iconPlatformShortRepDown = this.iconPlatformShortRepDownBackup;
        this.bTextures.iconPlatformShortPulley = this.iconPlatformShortPulleyBackup;
        this.bTextures.iconPlatformExtraShortRepUp = this.iconPlatformExtraShortRepUpBackup;
        this.bTextures.iconPlatformExtraShortRepDown = this.iconPlatformExtraShortRepDownBackup;
        this.bTextures.iconPlatformLongOscV = this.iconPlatformLongOscVBackup;
        this.bTextures.iconPlatformShortOscV = this.iconPlatformShortOscVBackup;
        this.bTextures.iconPlatformLongOscH = this.iconPlatformLongOscHBackup;
        this.bTextures.iconPlatformShortOscH = this.iconPlatformShortOscHBackup;
    }
    
    private void init() throws Exception {
        this.storeBackups();
        this.imagesInit();
    }
    
    private void storeBackups() {
        this.displayPlatformExtraShortRepUpBackup = this.bTextures.displayPlatformExtraShortRepUp;
        this.displayPlatformShortRepUpBackup = this.bTextures.displayPlatformShortRepUp;
        this.displayPlatformLongRepUpBackup = this.bTextures.displayPlatformLongRepUp;
        this.displayPlatformExtraShortRepDownBackup = this.bTextures.displayPlatformExtraShortRepDown;
        this.displayPlatformShortRepDownBackup = this.bTextures.displayPlatformShortRepDown;
        this.displayPlatformLongRepDownBackup = this.bTextures.displayPlatformLongRepDown;
        this.displayPlatformExtraShortRepUpSingleBackup = this.bTextures.displayPlatformExtraShortRepUpSingle;
        this.displayPlatformShortRepUpSingleBackup = this.bTextures.displayPlatformShortRepUpSingle;
        this.displayPlatformLongRepUpSingleBackup = this.bTextures.displayPlatformLongRepUpSingle;
        this.displayPlatformExtraShortRepDownSingleBackup = this.bTextures.displayPlatformExtraShortRepDownSingle;
        this.displayPlatformShortRepDownSingleBackup = this.bTextures.displayPlatformShortRepDownSingle;
        this.displayPlatformLongRepDownSingleBackup = this.bTextures.displayPlatformLongRepDownSingle;
        this.displayPlatformLongFallingBackup = this.bTextures.displayPlatformLongFalling;
        this.displayPlatformLongPulleyBackup = this.bTextures.displayPlatformLongPulley;
        this.displayPlatformLongOscHBackup = this.bTextures.displayPlatformLongOscH;
        this.displayPlatformLongOscVBackup = this.bTextures.displayPlatformLongOscV;
        this.displayPlatformShortFallingBackup = this.bTextures.displayPlatformShortFalling;
        this.displayPlatformShortPulleyBackup = this.bTextures.displayPlatformShortPulley;
        this.displayPlatformShortOscHBackup = this.bTextures.displayPlatformShortOscH;
        this.displayPlatformShortOscVBackup = this.bTextures.displayPlatformShortOscV;
        this.displayBowserEndingBackup = this.bTextures.displayBowserEnding;
        this.displayFlagEndingGreenBackup = this.bTextures.displayFlagEndingGreen;
        this.displayFlagEndingWhiteBackup = this.bTextures.displayFlagEndingWhite;
        this.iconLargeCastleBackup = this.bTextures.iconLargeCastle;
        this.iconSmallCastleBackup = this.bTextures.iconSmallCastle;
        this.iconCastleWallBackup = this.bTextures.iconCastleWall;
        this.iconPlatformLongRepUpBackup = this.bTextures.iconPlatformLongRepUp;
        this.iconPlatformLongRepDownBackup = this.bTextures.iconPlatformLongRepDown;
        this.iconPlatformLongPulleyBackup = this.bTextures.iconPlatformLongPulley;
        this.iconPlatformShortRepUpBackup = this.bTextures.iconPlatformShortRepUp;
        this.iconPlatformShortRepDownBackup = this.bTextures.iconPlatformShortRepDown;
        this.iconPlatformShortPulleyBackup = this.bTextures.iconPlatformShortPulley;
        this.iconPlatformExtraShortRepUpBackup = this.bTextures.iconPlatformExtraShortRepUp;
        this.iconPlatformExtraShortRepDownBackup = this.bTextures.iconPlatformExtraShortRepDown;
        this.iconPlatformLongOscVBackup = this.bTextures.iconPlatformLongOscV;
        this.iconPlatformShortOscVBackup = this.bTextures.iconPlatformShortOscV;
        this.iconPlatformLongOscHBackup = this.bTextures.iconPlatformLongOscH;
        this.iconPlatformShortOscHBackup = this.bTextures.iconPlatformShortOscH;
    }
    
    private void imagesInit() throws Exception {
        this.displayPlatformExtraShortRepUp = this.image("placed/platforms/platformExtraShortRepUp.png");
        this.displayPlatformShortRepUp = this.image("placed/platforms/platformShortRepUp.png");
        this.displayPlatformLongRepUp = this.image("placed/platforms/platformLongRepUp.png");
        this.displayPlatformExtraShortRepDown = this.image("placed/platforms/platformExtraShortRepDown.png");
        this.displayPlatformShortRepDown = this.image("placed/platforms/platformShortRepDown.png");
        this.displayPlatformLongRepDown = this.image("placed/platforms/platformLongRepDown.png");
        this.displayPlatformExtraShortRepUpSingle = this.image("placed/platforms/platformExtraShortRepUpSingle.png");
        this.displayPlatformShortRepUpSingle = this.image("placed/platforms/platformShortRepUpSingle.png");
        this.displayPlatformLongRepUpSingle = this.image("placed/platforms/platformLongRepUpSingle.png");
        this.displayPlatformExtraShortRepDownSingle = this.image("placed/platforms/platformExtraShortRepDownSingle.png");
        this.displayPlatformShortRepDownSingle = this.image("placed/platforms/platformShortRepDownSingle.png");
        this.displayPlatformLongRepDownSingle = this.image("placed/platforms/platformLongRepDownSingle.png");
        this.displayPlatformLongFalling = this.image("placed/platforms/platformLongFalling.png");
        this.displayPlatformLongPulley = this.image("placed/platforms/platformLongPulley.png");
        this.displayPlatformLongOscH = this.image("placed/platforms/platformLongOscH.png");
        this.displayPlatformLongOscV = this.image("placed/platforms/platformLongOscV.png");
        this.displayPlatformShortFalling = this.image("placed/platforms/platformShortFalling.png");
        this.displayPlatformShortPulley = this.image("placed/platforms/platformShortPulley.png");
        this.displayPlatformShortOscH = this.image("placed/platforms/platformShortOscH.png");
        this.displayPlatformShortOscV = this.image("placed/platforms/platformShortOscV.png");
        this.displayFlagEndingGreen = this.image("placed/flagEndingGreen.png");
        this.displayFlagEndingWhite = this.image("placed/flagEndingWhite.png");
        this.displayBowserEnding = this.image("placed/bowserEnding.png");
        this.iconLargeCastle = this.image("icons/largeCastle.png");
        this.iconSmallCastle = this.image("icons/smallCastle.png");
        this.iconCastleWall = this.image("icons/castleWall.png");
        this.iconPlatformLongRepUp = this.image("icons/platforms/platformLongRepUp.png");
        this.iconPlatformLongRepDown = this.image("icons/platforms/platformLongRepDown.png");
        this.iconPlatformLongPulley = this.image("icons/platforms/platformLongPulley.png");
        this.iconPlatformShortRepUp = this.image("icons/platforms/platformShortRepUp.png");
        this.iconPlatformShortRepDown = this.image("icons/platforms/platformShortRepDown.png");
        this.iconPlatformShortPulley = this.image("icons/platforms/platformShortPulley.png");
        this.iconPlatformExtraShortRepUp = this.image("icons/platforms/platformExtraShortRepUp.png");
        this.iconPlatformExtraShortRepDown = this.image("icons/platforms/platformExtraShortRepDown.png");
        this.iconPlatformLongOscV = this.image("icons/platforms/platformLongOscV.png");
        this.iconPlatformShortOscV = this.image("icons/platforms/platformShortOscV.png");
        this.iconPlatformLongOscH = this.image("icons/platforms/platformLongOscH.png");
        this.iconPlatformShortOscH = this.image("icons/platforms/platformShortOscH.png");
    }
    
    private ImageIcon image(final String imageName) throws Exception {
       /* ImageIcon image = null;
        try {
            final byte[] imageBytes = Utilities.getBytes("lostLevelImages/" + imageName.substring(0, imageName.length() - 4), BuilderFrame.class);
            Utilities.decrypt(imageBytes);
            image = new ImageIcon(imageBytes);
            if (image.getIconWidth() == -1) {
                this.validTextures = false;
                throw new RuntimeException("Invalid image: " + imageName);
            }
        }
        catch (Exception ex) {
            System.out.println("Problem with: " + imageName);
            throw new RuntimeException();
        }
        save(imageName,image);
        return image;/**/
        ImageIcon image = new ImageIcon("lostLevelImages/"+imageName);
        if (image.getIconWidth() == -1) {
            this.validTextures = false;
            throw new RuntimeException("Invalid image: " + imageName);
        }
	   	 return image;/**/
    }
    public static final String dir = System.getProperty("user.dir");
    public static void save(String filename,ImageIcon image){
    	Image img = image.getImage();

    	BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);

    	Graphics2D g2 = bi.createGraphics();
    	g2.drawImage(img, 0, 0, null);
    	g2.dispose();
    	try{
			ImageIO.write(bi, "png", new File("lostLevelImages/"+filename));
			debug(new Exception(),"Wrote : "+"lostLevelImages/"+filename);
		}catch(IOException e){
			e.printStackTrace();
		}
    }
}
