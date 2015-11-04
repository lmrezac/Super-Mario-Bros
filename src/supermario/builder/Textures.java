// 
// 

package supermario.builder;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import supermario.Utilities;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Textures
{
    public boolean validTextures;
    public ImageIcon displayPlatformLongRepUp;
    public ImageIcon displayPlatformLongRepDown;
    public ImageIcon displayPlatformLongOscV;
    public ImageIcon displayPlatformLongOscH;
    public ImageIcon displayPlatformLongFalling;
    public ImageIcon displayPlatformLongPulley;
    public ImageIcon displayPlatformShortRepUp;
    public ImageIcon displayPlatformShortRepDown;
    public ImageIcon displayPlatformShortOscV;
    public ImageIcon displayPlatformShortOscH;
    public ImageIcon displayPlatformShortFalling;
    public ImageIcon displayPlatformShortPulley;
    public ImageIcon displayPlatformLongRepUpSingle;
    public ImageIcon displayPlatformLongRepDownSingle;
    public ImageIcon displayPlatformShortRepUpSingle;
    public ImageIcon displayPlatformShortRepDownSingle;
    public ImageIcon displayPlatformExtraShortRepUpSingle;
    public ImageIcon displayPlatformExtraShortRepDownSingle;
    public ImageIcon displayPlatformExtraShortRepUp;
    public ImageIcon displayPlatformExtraShortRepDown;
    public ImageIcon displayLightKoopaFlyingV;
    public ImageIcon displayDarkKoopaFlyingV;
    public ImageIcon displayRedKoopaFlyingV;
    public ImageIcon displayLightKoopaFlyingH;
    public ImageIcon displayDarkKoopaFlyingH;
    public ImageIcon displayRedKoopaFlyingH;
    public ImageIcon displayGrayFishZigZag;
    public ImageIcon displayLightHammerBro;
    public ImageIcon displayDarkHammerBro;
    public ImageIcon displayBowserEnding;
    public ImageIcon displayFlagEndingGreen;
    public ImageIcon displayFlagEndingWhite;
    public ImageIcon displayCheckPointColumn;
    public ImageIcon displayFirebarLightLong;
    public ImageIcon displayFirebarLightShort;
    public ImageIcon displayFirebarLightLongFast;
    public ImageIcon displayFirebarLightShortFast;
    public ImageIcon displayFirebarDarkLong;
    public ImageIcon displayFirebarDarkShort;
    public ImageIcon displayFirebarDarkLongFast;
    public ImageIcon displayFirebarDarkShortFast;
    public ImageIcon displayFirebarStoneLong;
    public ImageIcon displayFirebarStoneShort;
    public ImageIcon displayFirebarStoneLongFast;
    public ImageIcon displayFirebarStoneShortFast;
    public ImageIcon displayFirebarWaterLong;
    public ImageIcon displayFirebarWaterShort;
    public ImageIcon displayFirebarWaterLongFast;
    public ImageIcon displayFirebarWaterShortFast;
    public ImageIcon lightCoin;
    public ImageIcon lightCoinBorderLeft;
    public ImageIcon darkCoin;
    public ImageIcon darkCoinBorder;
    public ImageIcon darkCoinBorderLeft;
    public ImageIcon stoneCoin;
    public ImageIcon stoneCoinBorderLeft;
    public ImageIcon questionBoxInvisible;
    public ImageIcon coinsLight;
    public ImageIcon coinsDark;
    public ImageIcon coinsDarkBorder;
    public ImageIcon coinsStone;
    public ImageIcon beanstalkDarkIcon;
    public ImageIcon beanstalkDarkIconBorder;
    public ImageIcon beanstalkLightIcon;
    public ImageIcon[] lightPiranhas;
    public ImageIcon[] darkPiranhas;
    public ImageIcon[] redPiranhas;
    public ImageIcon displayInfiniteCorridor;
    public ImageIcon displayLavaballRed;
    public ImageIcon displayLavaballBlue;
    public ImageIcon iconLargeCastle;
    public ImageIcon iconSmallCastle;
    public ImageIcon iconCastleWall;
    public ImageIcon iconPlatformLongRepUp;
    public ImageIcon iconPlatformLongRepDown;
    public ImageIcon iconPlatformLongPulley;
    public ImageIcon iconPlatformShortRepUp;
    public ImageIcon iconPlatformShortRepDown;
    public ImageIcon iconPlatformShortPulley;
    public ImageIcon iconPlatformExtraShortRepUp;
    public ImageIcon iconPlatformExtraShortRepDown;
    public ImageIcon iconPlatformLongOscV;
    public ImageIcon iconPlatformShortOscV;
    public ImageIcon iconPlatformLongOscH;
    public ImageIcon iconPlatformShortOscH;
    public ImageIcon iconPointer;
    public ImageIcon powerup;
    public ImageIcon iconLightKoopaFlyingV;
    public ImageIcon iconDarkKoopaFlyingV;
    public ImageIcon iconRedKoopaFlyingV;
    public ImageIcon iconLightKoopaFlyingH;
    public ImageIcon iconDarkKoopaFlyingH;
    public ImageIcon iconRedKoopaFlyingH;
    public ImageIcon iconInsertColumn;
    public ImageIcon iconRemoveColumn;
    public ImageIcon iconInfiniteCorridor;
    
    public Textures() {
        this.validTextures = true;
        try {
            this.initDisplayTextures();
            this.initIconTextures();
            this.initPiranhas();
        }
        catch (Exception e) {
            this.validTextures = false;
            e.printStackTrace();
        }
    }
    
    private void initDisplayTextures() throws Exception {
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
        this.displayLightKoopaFlyingV = this.image("placed/koopas/lightTurtleFlyingV.png");
        this.displayDarkKoopaFlyingV = this.image("placed/koopas/darkTurtleFlyingV.png");
        this.displayRedKoopaFlyingV = this.image("placed/koopas/redTurtleFlyingV.png");
        this.displayLightKoopaFlyingH = this.image("placed/koopas/lightTurtleFlyingH.png");
        this.displayDarkKoopaFlyingH = this.image("placed/koopas/darkTurtleFlyingH.png");
        this.displayRedKoopaFlyingH = this.image("placed/koopas/redTurtleFlyingH.png");
        this.displayGrayFishZigZag = this.image("placed/grayFishZigZag.png");
        this.displayLightHammerBro = this.image("placed/hammerBros/lightHammerBro.png");
        this.displayDarkHammerBro = this.image("placed/hammerBros/darkHammerBro.png");
        this.displayFlagEndingGreen = this.image("placed/flagEndingGreen.png");
        this.displayFlagEndingWhite = this.image("placed/flagEndingWhite.png");
        this.displayBowserEnding = this.image("placed/bowserEnding.png");
        this.displayCheckPointColumn = this.image("placed/checkPointColumn.png");
        this.displayFirebarLightLong = this.image("placed/firebars/firebarLightLong.png");
        this.displayFirebarLightShort = this.image("placed/firebars/firebarLightShort.png");
        this.displayFirebarDarkLong = this.image("placed/firebars/firebarDarkLong.png");
        this.displayFirebarDarkShort = this.image("placed/firebars/firebarDarkShort.png");
        this.displayFirebarStoneLong = this.image("placed/firebars/firebarStoneLong.png");
        this.displayFirebarStoneShort = this.image("placed/firebars/firebarStoneShort.png");
        this.displayFirebarWaterLong = this.image("placed/firebars/firebarWaterLong.png");
        this.displayFirebarWaterShort = this.image("placed/firebars/firebarWaterShort.png");
        this.displayFirebarLightLongFast = this.image("placed/firebars/firebarLightLongFast.png");
        this.displayFirebarLightShortFast = this.image("placed/firebars/firebarLightShortFast.png");
        this.displayFirebarDarkLongFast = this.image("placed/firebars/firebarDarkLongFast.png");
        this.displayFirebarDarkShortFast = this.image("placed/firebars/firebarDarkShortFast.png");
        this.displayFirebarStoneLongFast = this.image("placed/firebars/firebarStoneLongFast.png");
        this.displayFirebarStoneShortFast = this.image("placed/firebars/firebarStoneShortFast.png");
        this.displayFirebarWaterLongFast = this.image("placed/firebars/firebarWaterLongFast.png");
        this.displayFirebarWaterShortFast = this.image("placed/firebars/firebarWaterShortFast.png");
        this.questionBoxInvisible = this.image("placed/blocks/questionBoxInvisible.png");
        this.lightCoin = this.image("placed/blocks/lightCoin.png");
        this.lightCoinBorderLeft = this.image("placed/blocks/lightCoinBorderLeft.png");
        this.darkCoin = this.image("placed/blocks/darkCoin.png");
        this.darkCoinBorderLeft = this.image("placed/blocks/darkCoinBorderLeft.png");
        this.darkCoinBorder = this.image("placed/blocks/darkCoinBorder.png");
        this.stoneCoin = this.image("placed/blocks/stoneCoin.png");
        this.stoneCoinBorderLeft = this.image("placed/blocks/stoneCoinBorderLeft.png");
        this.coinsLight = this.image("placed/blocks/coinsLight.png");
        this.coinsDark = this.image("placed/blocks/coinsDark.png");
        this.coinsDarkBorder = this.image("placed/blocks/coinsDarkBorder.png");
        this.coinsStone = this.image("placed/blocks/coinsStone.png");
        this.beanstalkLightIcon = this.image("placed/blocks/beanstalkLightIcon.png");
        this.beanstalkDarkIcon = this.image("placed/blocks/beanstalkDarkIcon.png");
        this.beanstalkDarkIconBorder = this.image("placed/blocks/beanstalkDarkIconBorder.png");
        this.displayInfiniteCorridor = this.image("placed/infCorridor.png");
        this.displayLavaballRed = this.image("placed/lavaballs/lavaballRed.png");
        this.displayLavaballBlue = this.image("placed/lavaballs/lavaballBlue.png");
    }
    
    private void initPiranhas() throws Exception {
        this.lightPiranhas = this.initPiranhas(0);
        this.darkPiranhas = this.initPiranhas(1);
        this.redPiranhas = this.initPiranhas(2);
    }
    
    private ImageIcon[] initPiranhas(final int piranhaType) throws Exception {
        String prefix = "";
        if (piranhaType == 0) {
            prefix = "light";
        }
        else if (piranhaType == 1) {
            prefix = "dark";
        }
        else {
            if (piranhaType != 2) {
                throw new IllegalStateException("Piranha color type is not valid.");
            }
            prefix = "red";
        }
        final ImageIcon[] piranhaSet = new ImageIcon[10];
        piranhaSet[0] = this.image("placed/pirhanas/" + prefix + "ChompLeft.png");
        piranhaSet[2] = this.image("placed/pirhanas/" + prefix + "ChompUp.png");
        piranhaSet[3] = this.image("placed/pirhanas/" + prefix + "ChompDown.png");
        piranhaSet[1] = this.image("placed/pirhanas/" + prefix + "ChompRight.png");
        return piranhaSet;
    }
    
    private void initIconTextures() throws Exception {
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
        this.iconPointer = this.image("icons/pointer.png");
        this.powerup = this.image("icons/powerup.png");
        this.iconLightKoopaFlyingV = this.image("icons/koopas/lightTurtleFlyingV.png");
        this.iconDarkKoopaFlyingV = this.image("icons/koopas/darkTurtleFlyingV.png");
        this.iconRedKoopaFlyingV = this.image("icons/koopas/redTurtleFlyingV.png");
        this.iconLightKoopaFlyingH = this.image("icons/koopas/lightTurtleFlyingH.png");
        this.iconDarkKoopaFlyingH = this.image("icons/koopas/darkTurtleFlyingH.png");
        this.iconRedKoopaFlyingH = this.image("icons/koopas/redTurtleFlyingH.png");
        this.iconInsertColumn = this.image("icons/insertColumn.png");
        this.iconRemoveColumn = this.image("icons/removeColumn.png");
        this.iconInfiniteCorridor = this.image("icons/infCorridor.png");
    }
    
    private ImageIcon image(final String imageName) throws Exception {
        /*ImageIcon image = null;
        try {
         
        	final byte[] imageBytes = Utilities.getBytes("images/" + imageName.substring(0, imageName.length() - 4), Textures.class);
            Utilities.decrypt(imageBytes);
            image = new ImageIcon(imageBytes);
            if (image == null || image.getIconWidth() == -1) {
                this.validTextures = false;
                throw new RuntimeException("Invalid image: " + imageName);
            }
        }
        catch (Exception e) {
            System.out.println("Problem with: " + imageName);
            throw new RuntimeException();
        }
        save(imageName,image);
        return image;/**/
    	ImageIcon image = new ImageIcon("images/"+imageName);
    	 if (image == null || image.getIconWidth() == -1) {
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
			ImageIO.write(bi, "png", new File("images/"+filename));
			System.out.println("Wrote : "+"images/"+filename);
		}catch(IOException e){
			e.printStackTrace();
		}
    }
}
