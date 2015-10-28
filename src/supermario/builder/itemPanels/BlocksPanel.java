// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import supermario.builder.ImageBuilder;
import java.awt.Component;
import supermario.builder.Item;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import supermario.builder.Button;
import supermario.builder.BuilderFrame;
import javax.swing.JPanel;

public final class BlocksPanel extends JPanel implements ItemPanel
{
    private BuilderFrame frame;
    public Button lightBrickNothing;
    public Button lightBrickCoin;
    public Button lightBrickCoins;
    public Button lightBrickGrow;
    public Button lightBrickLife;
    public Button lightBrickStar;
    public Button lightBrickBeanstalk;
    /*public Button darkBrickNothing;
    public Button darkBrickCoin;
    public Button darkBrickCoins;
    public Button darkBrickGrow;
    public Button darkBrickLife;
    public Button darkBrickStar;
    public Button darkBrickBeanstalk;
    public Button stoneBrickNothing;
    public Button stoneBrickCoin;
    public Button stoneBrickCoins;
    public Button stoneBrickGrow;
    public Button stoneBrickLife;
    public Button stoneBrickStar;
    public Button stoneBrickBeanstalk;
    */
    public Button questionBoxCoin;
    public Button questionBoxGrow;
    public Button questionBoxLife;
    public Button questionBoxStar;
    public Button questionBoxDeathShroom;
    public Button questionBoxBeanstalk;
    public Button questionBoxInvisibleCoin;
    public Button questionBoxInvisibleGrow;
    public Button questionBoxInvisibleLife;
    public Button questionBoxInvisibleStar;
    public Button questionBoxInvisibleBeanstalk;
    
    public BlocksPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.construct();
    }
    
    @Override
    public void init() {
        final ImageIcon tall = new ImageIcon(new BufferedImage(16, 32, 5));
        final ImageIcon std = new ImageIcon(new BufferedImage(16, 16, 5));
        (this.lightBrickNothing = new Button(this.frame, this.frame.game.textures.lightBrick, null, "<html><center>Nothing</center></html>", "Above ground brick that contains nothing. Is breakable.")).setItem(new Item(this.frame, 'A', "Light Brick w/Nothing", this.lightBrickNothing, -1, 2, 0, 0, true, true, 1));
        (this.lightBrickCoin = new Button(this.frame, std, null, "<html><center>Coin</center></html>", "Above ground brick that contains a single coin. Turns to metal.")).setItem(new Item(this.frame, 'B', "Light Brick w/Coin", this.lightBrickCoin, -1, 2, 0, 0, true, true, 1));
        (this.lightBrickCoins = new Button(this.frame, std, null, "<html><center>Coins</center></html>", "Above ground brick that contains multiple coins. Turns to metal.")).setItem(new Item(this.frame, 'C', "Light Brick w/Coins", this.lightBrickCoins, -1, 2, 0, 0, true, true, 4));
        (this.lightBrickGrow = new Button(this.frame, tall, null, "<html><center>Powerup</center></html>", "Above ground brick that contains either a Super Mushroom or Fire Flower. Turns to metal.")).setItem(new Item(this.frame, 'D', "Light Brick w/Powerup", this.lightBrickGrow, -1, 18, 0, 16, true, true, 16));
        (this.lightBrickLife = new Button(this.frame, tall, null, "<html><center>Life</center></html>", "Above ground brick that contains an extra life. Turns to metal.")).setItem(new Item(this.frame, 'E', "Light Brick w/Life", this.lightBrickLife, -1, 18, 0, 16, true, true, 1));
        (this.lightBrickStar = new Button(this.frame, tall, null, "<html><center>Starman</center></html>", "Above ground brick that contains a Starman. Turns to metal.")).setItem(new Item(this.frame, 'F', "Light Brick w/Starman", this.lightBrickStar, -1, 18, 0, 16, true, true, 1));
        (this.lightBrickBeanstalk = new Button(this.frame, std, null, "<html><center>Beanstalk</center></html>", "Above ground brick that contains a beanstalk warp. Turns to metal.")).setItem(new Item(this.frame, 'G', "Light Brick w/Beanstalk", this.lightBrickBeanstalk, -1, 19, 0, 0, true, true, 6));
        /*(this.darkBrickNothing = new Button(this.frame, this.frame.game.textures.darkBrick, null, "<html><center>Nothing</center></html>", "Below ground brick that contains nothing. Is breakable.")).setItem(new Item(this.frame, 'H', "Dark Brick w/Nothing", this.darkBrickNothing, -1, 2, 0, 0, true, true, 1));
        (this.darkBrickCoin = new Button(this.frame, std, null, "<html><center>Coin</center></html>", "Below ground brick that contains a single coin. Turns to metal.")).setItem(new Item(this.frame, 'I', "Dark Brick w/Coin", this.darkBrickCoin, -1, 2, 0, 0, true, true, 1));
        (this.darkBrickCoins = new Button(this.frame, std, null, "<html><center>Coins</center></html>", "Below ground brick that contains multiple coins. Turns to metal.")).setItem(new Item(this.frame, 'J', "Dark Brick w/Coins", this.darkBrickCoins, -1, 2, 0, 0, true, true, 4));
        (this.darkBrickGrow = new Button(this.frame, tall, null, "<html><center>Powerup</center></html>", "Below ground brick that contains either a Super Mushroom or Fire Flower. Turns to metal.")).setItem(new Item(this.frame, 'K', "Dark Brick w/Powerup", this.darkBrickGrow, -1, 18, 0, 16, true, true, 16));
        (this.darkBrickLife = new Button(this.frame, tall, null, "<html><center>Life</center></html>", "Below ground brick that contains an extra life. Turns to metal.")).setItem(new Item(this.frame, 'L', "Dark Brick w/Life", this.darkBrickLife, -1, 18, 0, 16, true, true, 1));
        (this.darkBrickStar = new Button(this.frame, tall, null, "<html><center>Starman</center></html>", "Below ground brick that contains a Starman. Turns to metal.")).setItem(new Item(this.frame, 'M', "Dark Brick w/Starman", this.darkBrickStar, -1, 18, 0, 16, true, true, 1));
        (this.darkBrickBeanstalk = new Button(this.frame, std, null, "<html><center>Beanstalk</center></html>", "Below ground brick that contains a beanstalk warp. Turns to metal.")).setItem(new Item(this.frame, 'N', "Dark Brick w/Beanstalk", this.darkBrickBeanstalk, -1, 19, 0, 0, true, true, 6));
        (this.stoneBrickNothing = new Button(this.frame, this.frame.game.textures.stoneBrick, null, "<html><center>Nothing</center></html>", "Castle brick that contains nothing. Is breakable.")).setItem(new Item(this.frame, '\u2022', "Stone Brick w/Nothing", this.stoneBrickNothing, -1, 2, 0, 0, true, true, 1));
        (this.stoneBrickCoin = new Button(this.frame, std, null, "<html><center>Coin</center></html>", "Castle brick that contains a single coin. Turns to metal.")).setItem(new Item(this.frame, '\u25d8', "Stone Brick w/Coin", this.stoneBrickCoin, -1, 2, 0, 0, true, true, 1));
        (this.stoneBrickCoins = new Button(this.frame, std, null, "<html><center>Coins</center></html>", "Castle brick that contains multiple coins. Turns to metal.")).setItem(new Item(this.frame, '\u2640', "Stone Brick w/Coins", this.stoneBrickCoins, -1, 2, 0, 0, true, true, 4));
        (this.stoneBrickGrow = new Button(this.frame, tall, null, "<html><center>Powerup</center></html>", "Castle brick that contains either a Super Mushroom or Fire Flower. Turns to metal.")).setItem(new Item(this.frame, '\u266b', "Stone Brick w/Powerup", this.stoneBrickGrow, -1, 18, 0, 16, true, true, 16));
        (this.stoneBrickLife = new Button(this.frame, tall, null, "<html><center>Life</center></html>", "Castle brick that contains an extra life. Turns to metal.")).setItem(new Item(this.frame, '\u263c', "Stone Brick w/Life", this.stoneBrickLife, -1, 18, 0, 16, true, true, 1));
        (this.stoneBrickStar = new Button(this.frame, tall, null, "<html><center>Starman</center></html>", "Castle brick that contains a Starman. Turns to metal.")).setItem(new Item(this.frame, '\u25ba', "Stone Brick w/Starman", this.stoneBrickStar, -1, 18, 0, 16, true, true, 1));
        (this.stoneBrickBeanstalk = new Button(this.frame, std, null, "<html><center>Beanstalk</center></html>", "Castle brick that contains a beanstalk warp. Turns to metal.")).setItem(new Item(this.frame, '\u25c4', "Stone Brick w/Beanstalk", this.stoneBrickBeanstalk, -1, 19, 0, 0, true, true, 6));
       */
        (this.questionBoxCoin = new Button(this.frame, std, null, "<html><center>Coin</center></html>", "Question box that contains a single coin. Turns to metal.")).setItem(new Item(this.frame, 'O', "Question Box w/Coin", this.questionBoxCoin, -1, 2, 0, 0, true, true, 1));
        (this.questionBoxGrow = new Button(this.frame, tall, null, "<html><center>Powerup</center></html>", "Question box that contains either a Super Mushroom or Fire Flower. Turns to metal.")).setItem(new Item(this.frame, 'P', "Question Box w/Powerup", this.questionBoxGrow, -1, 18, 0, 16, true, true, 16));
        (this.questionBoxLife = new Button(this.frame, tall, null, "<html><center>Life</center></html>", "Question box that contains an extra life. Turns to metal.")).setItem(new Item(this.frame, 'Q', "Question Box w/Life", this.questionBoxLife, -1, 18, 0, 16, true, true, 1));
        (this.questionBoxStar = new Button(this.frame, tall, null, "<html><center>Starman</center></html>", "Question box that contains a Starman. Turns to metal.")).setItem(new Item(this.frame, 'R', "Question Box w/Starman", this.questionBoxStar, -1, 18, 0, 16, true, true, 1));
        (this.questionBoxBeanstalk = new Button(this.frame, std, null, "<html><center>Beanstalk</center></html>", "Question box that contains a beanstalk warp. Turns to metal.")).setItem(new Item(this.frame, '\u03d8', "Question Box w/Beanstalk", this.questionBoxBeanstalk, -1, 19, 0, 0, true, true, 6));
        (this.questionBoxInvisibleCoin = new Button(this.frame, std, null, "<html><center>Coin</center></html>", "Invisible question box that contains a single coin. Turns to metal.")).setItem(new Item(this.frame, 'W', "Invisible Box w/Coin", this.questionBoxInvisibleCoin, -1, 26, 0, 0, true, true, 1));
        (this.questionBoxInvisibleGrow = new Button(this.frame, std, null, "<html><center>Powerup</center></html>", "Invisible question box that contains either a Super Mushroom or Fire Flower. Turns to metal.")).setItem(new Item(this.frame, 'X', "Invisible Box w/Powerup", this.questionBoxInvisibleGrow, -1, 27, 0, 16, true, true, 16));
        (this.questionBoxInvisibleLife = new Button(this.frame, tall, null, "<html><center>Life</center></html>", "Invisible question box that contains an extra life. Turns to metal.")).setItem(new Item(this.frame, 'Y', "Invisible Box w/Life", this.questionBoxInvisibleLife, -1, 27, 0, 16, true, true, 1));
        (this.questionBoxInvisibleStar = new Button(this.frame, tall, null, "<html><center>Starman</center></html>", "Invisible question box that contains a Starman. Turns to metal.")).setItem(new Item(this.frame, 'Z', "Invisible Box w/Starman", this.questionBoxInvisibleStar, -1, 27, 0, 16, true, true, 1));
        (this.questionBoxInvisibleBeanstalk = new Button(this.frame, std, null, "<html><center>Beanstalk</center></html>", "Invisible question box that contains a beanstalk warp. Turns to metal.")).setItem(new Item(this.frame, '\u03dc', "Invisible Box w/Beanstalk", this.questionBoxInvisibleBeanstalk, -1, 28, 0, 0, true, true, 6));
        this.setBlocksScheme(0);
    }
    
    @Override
    public void construct() {
        this.add(this.lightBrickNothing);
        this.add(this.lightBrickCoin);
        this.add(this.lightBrickCoins);
        this.add(this.lightBrickGrow);
        this.add(this.lightBrickLife);
        this.add(this.lightBrickStar);
        this.add(this.lightBrickBeanstalk);
        /*this.add(this.darkBrickNothing);
        this.add(this.darkBrickCoin);
        this.add(this.darkBrickCoins);
        this.add(this.darkBrickGrow);
        this.add(this.darkBrickLife);
        this.add(this.darkBrickStar);
        this.add(this.darkBrickBeanstalk);
        this.add(this.stoneBrickNothing);
        this.add(this.stoneBrickCoin);
        this.add(this.stoneBrickCoins);
        this.add(this.stoneBrickGrow);
        this.add(this.stoneBrickLife);
        this.add(this.stoneBrickStar);
        this.add(this.stoneBrickBeanstalk);*/
        this.add(this.frame.getSpacerButton());
        this.add(this.questionBoxCoin);
        this.add(this.frame.getSpacerButton());
        this.add(this.questionBoxGrow);
        this.add(this.questionBoxLife);
        this.add(this.questionBoxStar);
        this.add(this.questionBoxBeanstalk);
        this.add(this.frame.getSpacerButton());
        this.add(this.questionBoxInvisibleCoin);
        this.add(this.frame.getSpacerButton());
        this.add(this.questionBoxInvisibleGrow);
        this.add(this.questionBoxInvisibleLife);
        this.add(this.questionBoxInvisibleStar);
        this.add(this.questionBoxInvisibleBeanstalk);
    }
    
    @Override
    public void refreshIcons() {
        if (this.frame.levelPanel.level != null) {
            this.setBlocksScheme(this.frame.levelPanel.level.levelType);
        }
        else {
            this.setBlocksScheme(0);
        }
    }
    
    public void setWaterMode() {
        this.lightBrickNothing.setEnabled(false);
       // this.darkBrickNothing.setEnabled(false);
      //  this.stoneBrickNothing.setEnabled(false);
    }
    
    public void setLandMode() {
        this.lightBrickNothing.setEnabled(true);
      //  this.darkBrickNothing.setEnabled(true);
      //  this.stoneBrickNothing.setEnabled(true);
    }
    
    public void setBlocksScheme(final int levelType) {
        int scheme = 0;
        if (levelType == 0 || levelType == 4 || levelType == 3 || levelType == 5 || levelType == 6) {
            scheme = 0;
        }
        else if (levelType == 1) {
            scheme = 1;
        }
        else if (levelType == 2) {
            scheme = 2;
        }
        ImageIcon brick = this.frame.game.textures.getLevelTypeAlt(levelType,frame.game.textures.lightBrick);
        this.lightBrickNothing.setImages(this.frame.game.textures.lightBrick,brick);
        this.lightBrickCoin.setImages(ImageBuilder.createBlockImage(0, scheme, 3),brick);
        this.lightBrickCoins.setImages(ImageBuilder.createBlockImage(0, scheme, 4), brick);
        this.lightBrickGrow.setImages(ImageBuilder.createBlockImage(0, scheme, 0), brick);
        this.lightBrickLife.setImages(ImageBuilder.createBlockImage(0, scheme, 1), brick);
        this.lightBrickStar.setImages(ImageBuilder.createBlockImage(0, scheme, 2), brick);
        this.lightBrickBeanstalk.setImages(ImageBuilder.createBlockImage(0, scheme, 5), brick);
        /*this.darkBrickNothing.setImages(this.frame.game.textures.darkBrick, null);
        this.darkBrickCoin.setImages(ImageBuilder.createBlockImage(1, scheme, 3), null);
        this.darkBrickCoins.setImages(ImageBuilder.createBlockImage(1, scheme, 4), null);
        this.darkBrickGrow.setImages(ImageBuilder.createBlockImage(1, scheme, 0), null);
        this.darkBrickLife.setImages(ImageBuilder.createBlockImage(1, scheme, 1), null);
        this.darkBrickStar.setImages(ImageBuilder.createBlockImage(1, scheme, 2), null);
        this.darkBrickBeanstalk.setImages(ImageBuilder.createBlockImage(1, scheme, 5), null);
        this.stoneBrickNothing.setImages(this.frame.game.textures.stoneBrick, null);
        this.stoneBrickCoin.setImages(ImageBuilder.createBlockImage(2, scheme, 3), null);
        this.stoneBrickCoins.setImages(ImageBuilder.createBlockImage(2, scheme, 4), null);
        this.stoneBrickGrow.setImages(ImageBuilder.createBlockImage(2, scheme, 0), null);
        this.stoneBrickLife.setImages(ImageBuilder.createBlockImage(2, scheme, 1), null);
        this.stoneBrickStar.setImages(ImageBuilder.createBlockImage(2, scheme, 2), null);
        this.stoneBrickBeanstalk.setImages(ImageBuilder.createBlockImage(2, scheme, 5), null);
        */
        this.questionBoxCoin.setImages(ImageBuilder.createBlockImage(3, scheme, 3), null);
        this.questionBoxGrow.setImages(ImageBuilder.createBlockImage(3, scheme, 0), null);
        this.questionBoxLife.setImages(ImageBuilder.createBlockImage(3, scheme, 1), null);
        this.questionBoxStar.setImages(ImageBuilder.createBlockImage(3, scheme, 2), null);
        this.questionBoxBeanstalk.setImages(ImageBuilder.createBlockImage(3, scheme, 5), null);
        this.questionBoxInvisibleCoin.setImages(ImageBuilder.createBlockImage(4, scheme, 3), null);
        this.questionBoxInvisibleGrow.setImages(ImageBuilder.createBlockImage(4, scheme, 0), null);
        this.questionBoxInvisibleLife.setImages(ImageBuilder.createBlockImage(4, scheme, 1), null);
        this.questionBoxInvisibleStar.setImages(ImageBuilder.createBlockImage(4, scheme, 2), null);
        this.questionBoxInvisibleBeanstalk.setImages(ImageBuilder.createBlockImage(4, scheme, 5), null);
    }
    
    public boolean isLandBlockOnly(final Item i) {
        return i.button == this.lightBrickNothing /*|| i.button == this.darkBrickNothing || i.button == this.stoneBrickNothing*/;
    }
}
