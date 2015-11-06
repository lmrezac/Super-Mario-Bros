// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import supermario.Utilities;
import supermario.builder.BuilderFrame;
import supermario.builder.Button;
import supermario.builder.Item;

public final class BackgroundPanel extends JPanel implements ItemPanel
{
    private static final long serialVersionUID = -8454316529412949628L;
	private BuilderFrame frame;
    public Button singleCloud;
    public Button doubleCloud;
    public Button tripleCloud;
    public Button singleBush;
    public Button doubleBush;
    public Button tripleBush;
    public Button tallTrimmedBush;
    public Button shortTrimmedBush;
    public Button tallSnowyBush;
    public Button shortSnowyBush;
    public Button picketFence;
    public Button castleWall;
    public Button smallHill;
    public Button bigHill;
    public Button smallCastle;
    public Button largeCastle;
    public Button lavaTop;
    public Button lavaBottom;
    public Button waterTop;
    public Button waterBottom;
    public Button bridgeChain;
    
    public BackgroundPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.construct();
    }
    
    @Override
    public void init() {
        (this.singleCloud = new Button(this.frame, this.frame.game.textures.singleCloud, null, null, "Background decoration. A small-sized cloud.")).setItem(new Item(this.frame, '«', "Single Cloud", this.singleCloud, -1, 1, 0, 0, false, false, 8));
        (this.doubleCloud = new Button(this.frame, this.frame.game.textures.doubleCloud, null, null, "Background decoration. A medium-sized cloud.")).setItem(new Item(this.frame, '©', "Double Cloud", this.doubleCloud, -1, 1, 0, 0, false, false, 8));
        (this.tripleCloud = new Button(this.frame, this.frame.game.textures.tripleCloud, null, null, "Background decoration. A large-sized cloud.")).setItem(new Item(this.frame, '¦', "Triple Cloud", this.tripleCloud, -1, 1, 0, 0, false, false, 8));
        (this.singleBush = new Button(this.frame, this.frame.game.textures.singleBush, null, null, "Background decoration. A small-sized bush.")).setItem(new Item(this.frame, '¥', "Single Bush", this.singleBush, -1, 1, 0, 0, false, false, 8));
        (this.doubleBush = new Button(this.frame, this.frame.game.textures.doubleBush, null, null, "Background decoration. A medium-sized bush.")).setItem(new Item(this.frame, '¤', "Double Bush", this.doubleBush, -1, 1, 0, 0, false, false, 8));
        (this.tripleBush = new Button(this.frame, this.frame.game.textures.tripleBush, null, null, "Background decoration. A large-sized bush.")).setItem(new Item(this.frame, '£', "Triple Bush", this.tripleBush, -1, 1, 0, 0, false, false, 8));
        (this.tallTrimmedBush = new Button(this.frame, this.frame.game.textures.tallTrimmedBush, null, null, "Background decoration. A tall bush.")).setItem(new Item(this.frame, '¢', "Tall Bush", this.tallTrimmedBush, -1, 0, 0, 0, false, false, 1));
        (this.shortTrimmedBush = new Button(this.frame, this.frame.game.textures.shortTrimmedBush, null, null, "Background decoration. A short bush.")).setItem(new Item(this.frame, '¡', "Short Bush", this.shortTrimmedBush, -1, 0, 0, 0, false, false, 1));
        (this.tallSnowyBush = new Button(this.frame, this.frame.game.textures.tallSnowyBush, null, null, "Background decoration. A tall snowy bush.")).setItem(new Item(this.frame, '\u2190', "Tall Bush", this.tallSnowyBush, -1, 0, 0, 0, false, false, 1));
        (this.shortSnowyBush = new Button(this.frame, this.frame.game.textures.shortSnowyBush, null, null, "Background decoration. A short snowy bush.")).setItem(new Item(this.frame, '\u2192', "Short Bush", this.shortSnowyBush, -1, 0, 0, 0, false, false, 1));
        (this.picketFence = new Button(this.frame, this.frame.game.textures.picketFence, null, null, "Background decoration. A short fence.")).setItem(new Item(this.frame, 'i', "Picket Fence", this.picketFence, -1, 0, 0, 0, true, false, 1));
        (this.castleWall = new Button(this.frame, this.frame.textures.iconCastleWall, this.frame.game.textures.castleWall, null, "Background decoration. A long castle wall section.")).setItem(new Item(this.frame, 'a', "Castle Wall", this.castleWall, -1, 0, 0, 0, false, false, 1));
        (this.smallHill = new Button(this.frame, this.frame.game.textures.smallHill, null, null, "Background decoration. A small hill.")).setItem(new Item(this.frame, 'b', "Small Hill", this.smallHill, -1, 1, 0, 0, false, false, 8));
        (this.bigHill = new Button(this.frame, this.frame.game.textures.smallHill, this.frame.game.textures.bigHill, null, "Background decoration. A large hill.")).setItem(new Item(this.frame, 'c', "Big Hill", this.bigHill, -1, 1, 0, 0, false, false, 8));
        (this.smallCastle = new Button(this.frame, this.frame.textures.iconSmallCastle, this.frame.game.textures.smallCastle, null, "Background decoration. A small castle.")).setItem(new Item(this.frame, 'd', "Small Castle", this.smallCastle, -1, 1, 0, 0, false, false, 8));
        (this.largeCastle = new Button(this.frame, this.frame.textures.iconLargeCastle, this.frame.game.textures.largeCastle, null, "Background decoration. A large castle.")).setItem(new Item(this.frame, 'e', "Large Castle", this.largeCastle, -1, 1, 0, 0, false, false, 8));
        (this.lavaTop = new Button(this.frame, this.frame.game.textures.lavaTop, null, "<html><center>Lava<br>Top</center></html>", "Background decoration. Represents the top part of lava.")).setItem(new Item(this.frame, 'g', "Lava Top", this.lavaTop, -1, 0, 0, 0, true, false, 1));
        (this.lavaBottom = new Button(this.frame, this.frame.game.textures.lavaBottom, null, "<html><center>Lava<br>Bottom</center></html>", "Background decoration. Represents the bottom part of lava.")).setItem(new Item(this.frame, 'µ', "Lava Bottom", this.lavaBottom, -1, 0, 0, 0, true, false, 1));
        (this.waterTop = new Button(this.frame, this.frame.game.textures.waterTop, null, "<html><center>Water<br>Top</center></html>", "Background decoration. Represents the top part of water.")).setItem(new Item(this.frame, '¾', "Water Top", this.waterTop, -1, 0, 0, 0, true, false, 1));
        (this.waterBottom = new Button(this.frame, this.frame.game.textures.waterBottom, null, "<html><center>Water<br>Bottom</center></html>", "Background decoration. Represents the bottom part of water.")).setItem(new Item(this.frame, '\u00e5', "Water Bottom", this.waterBottom, -1, 0, 0, 0, true, false, 1));
        (this.bridgeChain = new Button(this.frame, this.frame.game.textures.bowserChain, null, "<html><center>Bridge<br>Chain</center><html>", "Chain to attach between bridges and solids.")).setItem(new Item(this.frame, '\u00fd', "Bridge Chain", this.bridgeChain, -1, 1, 0, 0, false, false, 8));
    }
    
    @Override
    public void construct() {
        this.add(this.singleCloud);
        this.add(this.doubleCloud);
        this.add(this.tripleCloud);
        this.add(this.singleBush);
        this.add(this.doubleBush);
        this.add(this.tripleBush);
        this.add(this.bridgeChain);
        this.add(this.tallTrimmedBush);
        this.add(this.shortTrimmedBush);
        this.add(this.tallSnowyBush);
        this.add(this.shortSnowyBush);
        this.add(this.smallHill);
        this.add(this.bigHill);
        this.add(this.picketFence);
        this.add(this.castleWall);
        this.add(this.smallCastle);
        this.add(this.largeCastle);
        this.add(this.lavaTop);
        this.add(this.lavaBottom);
        this.add(this.waterTop);
        this.add(this.waterBottom);
    }
    
    @Override
    public void refreshIcons() {	
    	//frame.game.textures.init();
        this.singleCloud.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.singleCloud),null);
        this.doubleCloud.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.doubleCloud),null);
        this.tripleCloud.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.tripleCloud),null);
        this.singleBush.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.singleBush),null);
        this.doubleBush.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.doubleBush),null);
        this.tripleBush.setImages( frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.tripleBush),null);
        this.tallTrimmedBush.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.tallTrimmedBush),null);
        this.shortTrimmedBush.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.shortTrimmedBush),null);
        this.tallSnowyBush.setImages( frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.tallSnowyBush),null);
        this.shortSnowyBush.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.shortSnowyBush),null);
        this.smallHill.setImages(frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.smallHill),null);
        this.bigHill.setImages( frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.smallHill),frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.bigHill));
        this.picketFence.setImages( frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.picketFence),null);
        this.castleWall.setImages(
        		Utilities.cropIcon(frame.game.textures.getLevelTypeAlt(frame.levelPanel.level.levelType,frame.game.textures.castleWall), 0,0, 16, 48),
        		frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.castleWall));
        this.smallCastle.setImages(/*this.frame.textures.iconSmallCastle*/new ImageIcon(Utilities.resizeImage(frame.game.textures.getLevelTypeAlt(frame.levelPanel.level.levelType, frame.game.textures.smallCastle).getImage(),50,50)), frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.smallCastle));
        this.largeCastle.setImages(new ImageIcon(Utilities.resizeImage(frame.game.textures.getLevelTypeAlt(frame.levelPanel.level.levelType, frame.game.textures.largeCastle).getImage(),50,50)), frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.largeCastle));
    }
    
    public boolean isBackgroundDecoration(final Item i) {
        return i.button == this.singleCloud || i.button == this.doubleCloud || i.button == this.tripleCloud || (i.button == this.singleBush || (i.button == this.doubleBush | i.button == this.tripleBush) || i.button == this.bridgeChain) || i.button == this.tallTrimmedBush || i.button == this.shortTrimmedBush || (i.button == this.tallSnowyBush || i.button == this.shortSnowyBush) || (i.button == this.picketFence || i.button == this.castleWall) || (i.button == this.smallHill || i.button == this.bigHill) || (i.button == this.smallCastle || i.button == this.largeCastle) || (i.button == this.lavaTop || i.button == this.lavaBottom || i.button == this.waterTop || i.button == this.waterBottom);
    }
    
    public void setEnabledState(final boolean enabledState) {
        this.singleCloud.setEnabled(enabledState);
        this.doubleCloud.setEnabled(enabledState);
        this.tripleCloud.setEnabled(enabledState);
        this.singleBush.setEnabled(enabledState);
        this.doubleBush.setEnabled(enabledState);
        this.tripleBush.setEnabled(enabledState);
        this.bridgeChain.setEnabled(enabledState);
        this.shortTrimmedBush.setEnabled(enabledState);
        this.tallTrimmedBush.setEnabled(enabledState);
        this.shortSnowyBush.setEnabled(enabledState);
        this.tallSnowyBush.setEnabled(enabledState);
        this.picketFence.setEnabled(enabledState);
        this.castleWall.setEnabled(enabledState);
        this.smallHill.setEnabled(enabledState);
        this.bigHill.setEnabled(enabledState);
        this.smallCastle.setEnabled(enabledState);
        this.largeCastle.setEnabled(enabledState);
        this.lavaTop.setEnabled(enabledState);
        this.lavaBottom.setEnabled(enabledState);
        this.waterTop.setEnabled(enabledState);
        this.waterBottom.setEnabled(enabledState);
    }
}
