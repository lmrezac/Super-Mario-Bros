// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import java.awt.Component;
import supermario.builder.Item;
import javax.swing.ImageIcon;
import supermario.builder.Button;
import supermario.builder.BuilderFrame;
import javax.swing.JPanel;

public final class SolidsPanel extends JPanel implements ItemPanel
{
    private BuilderFrame frame;
    public Button lightGround;
    //public Button darkGround;
    //public Button stoneGround;
    //public Button seaGround;
    //public Button seaStone;
    //public Button cloudGround;
    public Button lightBlock;
   // public Button darkBlock;
   // public Button stoneBlock;
    public Button lightMetal;
    //public Button darkMetal;
   // public Button stoneMetal;
    public Button treeTopLeft;
    public Button treeTopMiddle;
    public Button treeTopRight;
    public Button treeBark;
    public Button mushroomTopLeft;
    public Button mushroomTopMiddle;
    public Button mushroomTopRight;
    public Button mushroomBarkTop;
    public Button mushroomBark;
    public Button coral;
    public Button bridge;
    public Button bowserBridge;
    
    public SolidsPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.construct();
    }
    
    @Override
    public void init() {
        (this.lightGround = new Button(this.frame, this.frame.game.textures.lightGround, null, null, "Solid platform found above ground.")).setItem(new Item(this.frame, 'l', "Light Ground", this.lightGround, -1, 2, 0, 0, true, true, 1));
        //(this.darkGround = new Button(this.frame, this.frame.game.textures.darkGround, null, null, "Solid platform found below ground.")).setItem(new Item(this.frame, 'm', "Dark Ground", this.darkGround, -1, 2, 0, 0, true, true, 1));
        //(this.stoneGround = new Button(this.frame, this.frame.game.textures.stoneGround, null, null, "Solid platform found in castles.")).setItem(new Item(this.frame, 'n', "Stone Ground", this.stoneGround, -1, 2, 0, 0, true, true, 1));
        //(this.seaGround = new Button(this.frame, this.frame.game.textures.seaGround, null, null, "Solid platform found underwater.")).setItem(new Item(this.frame, 'o', "Sea Ground", this.seaGround, -1, 2, 0, 0, true, true, 1));
       // (this.seaStone = new Button(this.frame, this.frame.game.textures.seaBlock, null, null, "Solid platform found as walls in water.")).setItem(new Item(this.frame, '§', "Sea Stone", this.seaStone, -1, 2, 0, 0, true, true, 1));
       // (this.cloudGround = new Button(this.frame, this.frame.game.textures.cloudGround, null, null, "Solid platform found in the sky.")).setItem(new Item(this.frame, 'p', "Cloud Ground", this.cloudGround, -1, 2, 0, 0, true, true, 1));
        (this.lightBlock = new Button(this.frame, this.frame.game.textures.lightBlock, null, null, "Solid platform found as walls above ground.")).setItem(new Item(this.frame, 'q', "Light Block", this.lightBlock, -1, 2, 0, 0, true, true, 1));
       // (this.darkBlock = new Button(this.frame, this.frame.game.textures.darkBlock, null, null, "Solid platform found as walls below ground.")).setItem(new Item(this.frame, 'r', "Dark Block", this.darkBlock, -1, 2, 0, 0, true, true, 1));
       // (this.stoneBlock = new Button(this.frame, this.frame.game.textures.stoneBlock, null, null, "Solid platform found as walls in castles.")).setItem(new Item(this.frame, '\u045e', "Stone Block", this.stoneBlock, -1, 2, 0, 0, true, true, 1));
        (this.lightMetal = new Button(this.frame, this.frame.game.textures.lightMetal, null, null, "Solid metal platform found above ground.")).setItem(new Item(this.frame, 's', "Light Metal", this.lightMetal, -1, 2, 0, 0, true, true, 1));
      //  (this.darkMetal = new Button(this.frame, this.frame.game.textures.darkMetal, null, null, "Solid metal platform found below ground.")).setItem(new Item(this.frame, 't', "Dark Metal", this.darkMetal, -1, 2, 0, 0, true, true, 1));
      //  (this.stoneMetal = new Button(this.frame, this.frame.game.textures.stoneMetal, null, null, "Solid metal platform found in castles.")).setItem(new Item(this.frame, 'u', "Stone Metal", this.stoneMetal, -1, 2, 0, 0, true, true, 1));
        (this.treeTopLeft = new Button(this.frame, this.frame.game.textures.treeTopLeftEnd, null, null, "Solid platform that is the left side of a tree.")).setItem(new Item(this.frame, 'v', "Tree Left", this.treeTopLeft, -1, 2, 0, 0, false, true, 1));
        (this.treeTopMiddle = new Button(this.frame, this.frame.game.textures.treeTopMiddle, null, null, "Solid platform that is the middle of a tree.")).setItem(new Item(this.frame, 'y', "Tree Middle", this.treeTopMiddle, -1, 2, 0, 0, true, true, 1));
        (this.treeTopRight = new Button(this.frame, this.frame.game.textures.treeTopRightEnd, null, null, "Solid platform that is the right side of a tree.")).setItem(new Item(this.frame, 'w', "Tree Right", this.treeTopRight, -1, 2, 0, 0, false, true, 1));
        (this.treeBark = new Button(this.frame, this.frame.game.textures.treeBark, null, null, "Permeable object that forms the bark of the tree top.")).setItem(new Item(this.frame, 'f', "Tree Bark", this.treeBark, -1, 0, 0, 0, true, false, 1));
        (this.mushroomTopLeft = new Button(this.frame, this.frame.game.textures.mushroomTreeLeftEnd, null, "<html><center>Mushroom<br>Left</center></html>", "Solid platform that is the left side of a mushroom.")).setItem(new Item(this.frame, '\u25cb', "Mushroom Top Left Side", this.mushroomTopLeft, -1, 2, 0, 0, false, true, 1));
        (this.mushroomTopMiddle = new Button(this.frame, this.frame.game.textures.mushroomTreeMiddleSection, null, "<html><center>Mushroom<br>Middle</center></html>", "Solid platform that is the middle of a mushroom.")).setItem(new Item(this.frame, '\u2642', "Mushroom Top Middle", this.mushroomTopMiddle, -1, 2, 0, 0, true, true, 1));
        (this.mushroomTopRight = new Button(this.frame, this.frame.game.textures.mushroomTreeRightEnd, null, "<html><center>Mushroom<br>Right</center></html>", "Solid platform that is the right side of a mushroom.")).setItem(new Item(this.frame, '\u25d9', "Mushroom Top Right", this.mushroomTopRight, -1, 2, 0, 0, false, true, 1));
        (this.mushroomBarkTop = new Button(this.frame, this.frame.game.textures.mushroomTreeBarkTop, null, "<html><center>Mushroom<br>Bark</center></html>", "Permeable object that forms the top portion of a stem of a mushroom top.")).setItem(new Item(this.frame, '\u2193', "Mushroom Bark Top", this.mushroomBarkTop, -1, 2, 0, 0, false, false, 1));
        (this.mushroomBark = new Button(this.frame, this.frame.game.textures.mushroomTreeBark, null, "<html><center>Mushroom<br>Bark</center></html>", "Permeable object that forms the stem of a mushroom top.")).setItem(new Item(this.frame, '\u2660', "Mushroom Bark", this.mushroomBark, -1, 2, 0, 0, true, false, 1));
        (this.coral = new Button(this.frame, this.frame.game.textures.coral, null, null, "Solid and vertically repeatable object that occurs in under water levels.")).setItem(new Item(this.frame, 'z', "Coral", this.coral, -1, 2, 0, 0, true, true, 1));
        (this.bridge = new Button(this.frame, this.frame.game.textures.bridge, null, null, "Solid platform typically found above water or lava.")).setItem(new Item(this.frame, '\u25b2', "Bridge", this.bridge, -1, 8, 0, 8, true, true, 1));
        (this.bowserBridge = new Button(this.frame, this.frame.game.textures.bowserBridgeSection, null, "<html><center>Bowser<br>Bridge</center></html>", "Solid platform typically found in Bowser battles.")).setItem(new Item(this.frame, '\u00e6', "Bowser Bridge", this.bowserBridge, -1, 2, 0, 0, true, true, 1));
    }
    
    @Override
    public void construct() {
        this.add(this.lightGround);
        //this.add(this.darkGround);
        //this.add(this.stoneGround);
        //this.add(this.seaGround);
        //this.add(this.seaStone);
       // this.add(this.cloudGround);
        this.add(this.lightBlock);
       // this.add(this.darkBlock);
       // this.add(this.stoneBlock);
        this.add(this.lightMetal);
      //  this.add(this.darkMetal);
      //  this.add(this.stoneMetal);
        this.add(this.treeTopLeft);
        this.add(this.treeTopMiddle);
        this.add(this.treeTopRight);
        this.add(this.treeBark);
        this.add(this.coral);
        this.add(this.bridge);
        this.add(this.mushroomTopLeft);
        this.add(this.mushroomTopMiddle);
        this.add(this.mushroomTopRight);
        this.add(this.mushroomBarkTop);
        this.add(this.mushroomBark);
        this.add(this.bowserBridge);
    }
    
    @Override
    public void refreshIcons() {
        this.lightGround.setImages(this.frame.game.textures.lightGround, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.lightGround));
      //  this.darkGround.setImages(this.frame.game.textures.darkGround, null);
       // this.stoneGround.setImages(this.frame.game.textures.stoneGround, null);
        this.lightBlock.setImages(this.frame.game.textures.lightBlock, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.lightBlock));
       // this.darkBlock.setImages(this.frame.game.textures.darkBlock, null);
       // this.stoneBlock.setImages(this.frame.game.textures.stoneBlock, null);
        this.treeTopLeft.setImages(this.frame.game.textures.treeTopLeftEnd, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.treeTopLeftEnd));
        this.treeTopMiddle.setImages(this.frame.game.textures.treeTopMiddle, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.treeTopMiddle));
        this.treeTopRight.setImages(this.frame.game.textures.treeTopRightEnd, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.treeTopRightEnd));
        this.treeBark.setImages(this.frame.game.textures.treeBark, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.treeBark));
        this.bridge.setImages(this.frame.game.textures.bridge, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.bridge));
        this.bowserBridge.setImages(this.frame.game.textures.bowserBridgeSection, frame.game.textures.getLevelTypeAlt(this.frame.levelPanel.level.levelType,this.frame.game.textures.bowserBridgeSection));
    }
}
