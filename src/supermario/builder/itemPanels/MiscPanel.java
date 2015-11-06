// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import supermario.builder.BuilderFrame;
import supermario.builder.Button;
import supermario.builder.Item;
import supermario.game.Game;

public final class MiscPanel extends JPanel implements ItemPanel
{
    private static final long serialVersionUID = 2665117765795226493L;
	private BuilderFrame frame;
    public Button mario;
    public Button thinAirWarp;
    public Button infiniteCorridor;
    public Button spring;
    public Button coin;
    public Button checkpoint;
    public Button beanstalkArrival;
    public Button insertColumn;
    public Button removeColumn;
    public Button pointer;
    public ImageIcon currentGreenSpring;
    public Button levelEndingSmFlag;
    public Button levelEndingLgFlag;
    public Button levelEndingBowserBattle;
    
    public MiscPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.construct();
        this.currentGreenSpring = frame.game.textures.springGreenLight;
    }
    
    @Override
    public void init() {
        (this.spring = new Button(this.frame, this.frame.game.textures.springLight, null, null, "Allows Mario jump to great heights.")).setItem(new Item(this.frame, 'j', "Spring", this.spring, -1, 2, 0, 0, true, true, 17));
        (this.mario = new Button(this.frame, this.frame.game.textures.marioWalk1, null, null, "The position of Mario in the beginning of a level. This item can be repositioned but not removed.")).setItem(new Item(this.frame, 'k', "Mario Start", this.mario, -1, 2, 0, 0, false, false, 1));
        (this.coin = new Button(this.frame, this.frame.game.textures.lightCoin, null, null, "Collectible. 100 of them adds an extra life.")).setItem(new Item(this.frame, 'h', "Coin", this.coin, -1, 2, 0, 0, true, false, 1));
        (this.checkpoint = new Button(this.frame, this.frame.game.textures.checkPtFlag, null, null, "If grabbed by Mario, it is the location he will respawn at upon death.")).setItem(new Item(this.frame, '\u0292', "Checkpoint", this.checkpoint, -1, 2, 0, 0, false, false, 14));
        (this.thinAirWarp = new Button(this.frame, this.frame.game.textures.marioStarBlackWalk1, null, null, "A \"thin air\" warp appearance point for Mario.")).setItem(new Item(this.frame, '½', "Thin Air Warp", this.thinAirWarp, -1, 2, 0, 0, false, false, 12));
        (this.infiniteCorridor = new Button(this.frame, this.frame.textures.iconInfiniteCorridor, this.frame.textures.displayInfiniteCorridor, "<html><center>Infinite<br>Corridor</center></html>", "If walked on by Mario, he will be transported backward.")).setItem(new Item(this.frame, '\u00f7', "Infinite Corridor", this.infiniteCorridor, -1, 25, 448, 0, false, false, 1));
        (this.beanstalkArrival = new Button(this.frame, this.frame.game.textures.beanstalkTopLight, this.frame.game.textures.entryVineLight, "<html><center>Beanstalk<br>Arrival Point</center></html>", "A warp point that Mario can arrive at.")).setItem(new Item(this.frame, '\u03e4', "Beanstalk Arrival Point", this.beanstalkArrival, Game.yTiles - 11, 17, 0, 0, false, true, 12));
        (this.pointer = new Button(this.frame, this.frame.textures.iconPointer, null, "<html><center>Selector/<br>Dragger Tool</center></html>", "A tool to drag existing level items, or to change their properties.")).setText(this.pointer.title);
        (this.insertColumn = new Button(this.frame, this.frame.textures.iconInsertColumn, null, "Insert Column", "Inserts a new column into the level.")).setItem(new Item(this.frame, 'x', "Insert Column", this.insertColumn, -1, 15, 0, 0, true, true, 1));
        (this.removeColumn = new Button(this.frame, this.frame.textures.iconRemoveColumn, null, "Remove Column", "Removes a column from the level.")).setItem(new Item(this.frame, 'x', "Remove Column", this.removeColumn, -1, 16, 0, 0, true, true, 1));
        (this.levelEndingSmFlag = new Button(this.frame, this.frame.textures.displayFlagEndingGreen, null, "", "")).setItem(new Item(this.frame, '\u2665', "Flag w/Small Castle", this.levelEndingSmFlag, 0, 20, 0, 0, false, true, 1));
        (this.levelEndingLgFlag = new Button(this.frame, this.frame.textures.displayFlagEndingGreen, null, "", "")).setItem(new Item(this.frame, '\u21a8', "Flag w/Large Castle", this.levelEndingLgFlag, 0, 20, 0, 0, false, true, 1));
        (this.levelEndingBowserBattle = new Button(this.frame, this.frame.textures.displayBowserEnding, null, "", "")).setItem(new Item(this.frame, '\u2666', "Bowser Battle", this.levelEndingBowserBattle, 0, 20, 0, 0, false, true, 1));
    }
    
    @Override
    public void construct() {
        this.add(this.pointer);
        this.add(this.insertColumn);
        this.add(this.removeColumn);
        this.add(this.coin);
        this.add(this.spring);
        this.add(this.mario);
        this.add(this.thinAirWarp);
        this.add(this.infiniteCorridor);
        this.add(this.checkpoint);
        this.add(this.beanstalkArrival);
    }
    
    @Override
    public void refreshIcons() {
        this.levelEndingSmFlag.setImages(this.frame.textures.displayFlagEndingGreen, null);
        this.levelEndingLgFlag.setImages(this.frame.textures.displayFlagEndingGreen, null);
        this.levelEndingBowserBattle.setImages(this.frame.textures.displayBowserEnding, null);
        this.coin.setImages(this.frame.game.textures.getLevelTypeAlt(frame.levelPanel.level.levelType,this.frame.game.textures.lightCoin), null);
    }
    
    public boolean isLandItemOnly(final Item i) {
        return i.button == this.spring || i.button == this.infiniteCorridor;
    }
    
    public void setWaterMode() {
        this.spring.setEnabled(false);
        this.infiniteCorridor.setEnabled(false);
        this.mario.setImages(this.frame.game.textures.marioSwim1, null);
        this.coin.setImages(this.frame.game.textures.lightCoin, null);
    }
    
    public void setLandMode() {
        this.spring.setEnabled(true);
        this.infiniteCorridor.setEnabled(true);
        this.mario.setImages(this.frame.game.textures.marioWalk1, null);
        this.coin.setImages(this.frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.lightCoin), null);
        if (this.frame.levelPanel.level != null && this.frame.levelPanel.level.levelType == 1) {
            
            this.spring.setImages(this.frame.game.textures.springDark, null);
            this.currentGreenSpring = this.frame.game.textures.springGreenDark;
            this.beanstalkArrival.setImages(this.frame.game.textures.beanstalkTopDark, this.frame.game.textures.entryVineDark);
        }
        else if (this.frame.levelPanel.level != null && this.frame.levelPanel.level.levelType == 2) {
            //this.coin.setImages(this.frame.game.textures.stoneCoin, null);
            this.spring.setImages(this.frame.game.textures.springGray, null);
            this.currentGreenSpring = this.frame.game.textures.springGreenGray;
            this.beanstalkArrival.setImages(this.frame.game.textures.beanstalkTopLight, this.frame.game.textures.entryVineDark);
        }
        else {
            //this.coin.setImages(this.frame.game.textures.lightCoin, null);
            this.spring.setImages(this.frame.game.textures.springLight, null);
            this.currentGreenSpring = this.frame.game.textures.springGreenLight;
            this.beanstalkArrival.setImages(this.frame.game.textures.beanstalkTopLight, this.frame.game.textures.entryVineLight);
        }
    }
}
