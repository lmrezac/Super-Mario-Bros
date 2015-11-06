// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import supermario.builder.BuilderFrame;
import supermario.builder.Button;
import supermario.builder.ImageBuilder;
import supermario.builder.Item;
import supermario.builder.Level;

public final class PipesPanel extends JPanel implements ItemPanel
{
    private static final long serialVersionUID = -9030918068660956668L;
	private BuilderFrame frame;
    public Button topWChomp;
    public Button topWOChomp;
    public Button bottomWChomp;
    public Button bottomWOChomp;
    public Button leftWChomp;
    public Button leftWOChomp;
    public Button rightWChomp;
    public Button rightWOChomp;
    public Button topSection;
    public Button sideSection;
    public Button leftConnector;
    public Button rightConnector;
    public Button doubleConnector;
    public Button topConnector;
    public Button bottomConnector;
    public Button warpZoneMessage;
    public Button warpZonePipe;
    public Button customText;
    private int currentPipeColor;
    public HashMap<Character, Item> customTextItems;
    
    public PipesPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.construct();
    }
    
    @Override
    public void init() {
        final ImageIcon square = new ImageIcon(new BufferedImage(32, 32, 5));
        final ImageIcon thin = new ImageIcon(new BufferedImage(24, 32, 5));
        (this.topWChomp = new Button(this.frame, square, null, "<html><center>Top w/<br>Piranha</center></html>", "A top-opening pipe with a piranha. Can be used to warp.")).setItem(new Item(this.frame, ';', "Pipe Top with Piranha", this.topWChomp, -1, 11, 0, 0, false, true, 5));
        (this.topWOChomp = new Button(this.frame, this.frame.game.textures.greenPipes[2], null, "Top", "A top-opening pipe without a piranha. Can be used to warp.")).setItem(new Item(this.frame, '~', "Pipe Top", this.topWOChomp, -1, 11, 0, 0, false, true, 5));
        (this.bottomWOChomp = new Button(this.frame, this.frame.game.textures.greenPipes[3], null, "Bottom", "A bottom-opening pipe without a piranha. Can be used to warp.")).setItem(new Item(this.frame, '\u0110', "Pipe Bottom", this.bottomWOChomp, -1, 12, 0, 0, false, true, 5));
        (this.bottomWChomp = new Button(this.frame, square, null, "<html><center>Bottom w/<br>Piranha</center></html>", "A bottom-opening pipe with a piranha. Can be used to warp.")).setItem(new Item(this.frame, '¿', "Pipe Bottom with Piranha", this.bottomWChomp, -1, 12, 0, 0, false, true, 5));
        (this.leftWChomp = new Button(this.frame, thin, null, "<html><center>Left w/<br>Piranha</center></html>", "A left-opening pipe with a piranha. Can be used to warp.")).setItem(new Item(this.frame, '\u2021', "Pipe Left with Piranha", this.leftWChomp, -1, 9, 0, 0, false, true, 5));
        (this.leftWOChomp = new Button(this.frame, this.frame.game.textures.greenPipes[0], null, "<html><center>Left</center></html>", "A pipe that can be entered by Mario. Can be used to warp.")).setItem(new Item(this.frame, '!', "Pipe Left", this.leftWOChomp, -1, 9, 0, 0, false, true, 5));
        (this.rightWOChomp = new Button(this.frame, this.frame.game.textures.greenPipes[1], null, "<html><center>Right</center></html>", "A pipe that can be entered by Mario. Can be used to warp.")).setItem(new Item(this.frame, '\u2663', "Pipe Right", this.rightWOChomp, -1, 10, 0, 0, false, true, 5));
        (this.rightWChomp = new Button(this.frame, thin, null, "<html><center>Right<br>w/Piranha</center></html>", "A pipe that can be entered by Mario. Can be used to warp.")).setItem(new Item(this.frame, '\u00ee', "Pipe Right with Piranha", this.rightWChomp, -1, 10, 0, 0, false, true, 5));
        (this.topSection = new Button(this.frame, this.frame.game.textures.greenPipes[4], null, "<html><center>Top<br>Section</center></html>", "A section of a pipe that is headed in a vertical direction.")).setItem(new Item(this.frame, '@', "Pipe Top Section", this.topSection, -1, 2, 0, 0, true, true, 1));
        (this.sideSection = new Button(this.frame, this.frame.game.textures.greenPipes[5], null, "<html><center>Side<br>Section</center></html>", "A section of a pipe that is headed in a horizontal direction.")).setItem(new Item(this.frame, '`', "Pipe Side Section", this.sideSection, -1, 2, 0, 0, true, true, 1));
        (this.leftConnector = new Button(this.frame, this.frame.game.textures.greenPipes[6], null, "<html><center>Left<br>Connector</center></html>", "A pipe junction that allows a vertical pipe to connect to a pipe on the left side.")).setItem(new Item(this.frame, '#', "Pipe Left Connector", this.leftConnector, -1, 2, 0, 0, true, true, 1));
        (this.rightConnector = new Button(this.frame, this.frame.game.textures.greenPipes[7], null, "<html><center>Right<br>Connector</center></html>", "A pipe junction that allows a vertical pipe to connect to a pipe on the right side.")).setItem(new Item(this.frame, 'S', "Pipe Right Connector", this.rightConnector, -1, 2, 0, 0, true, true, 1));
        (this.doubleConnector = new Button(this.frame, this.frame.game.textures.greenPipes[10], null, "<html><center>Double<br>Connector</center></html>", "A pipe junction that joints a vertical pipe with both left and right side pipes.")).setItem(new Item(this.frame, 'T', "Pipe Double Connector", this.doubleConnector, -1, 2, 0, 0, true, true, 1));
        (this.topConnector = new Button(this.frame, this.frame.game.textures.greenPipes[8], null, "<html><center>Top<br>Connector</center></html>", "A pipe that joins a vertical pipe with a horizonally running pipe.")).setItem(new Item(this.frame, 'U', "Pipe Top Connector", this.topConnector, -1, 2, 0, 0, true, true, 1));
        (this.bottomConnector = new Button(this.frame, this.frame.game.textures.greenPipes[9], null, "<html><center>Bottom<br>Connector</center></html>", "A pipe that joins a vertical pipe with a horizontally running pipe.")).setItem(new Item(this.frame, '\u010e', "Pipe Bottom Connector", this.bottomConnector, -1, 2, 0, 0, true, true, 1));
        (this.warpZoneMessage = new Button(this.frame, this.frame.game.textures.warpZoneMessage, null, "<html><center>Welcome<br>To<br>Warp<br>Zone<br>Message</center></html>", "The message that should be displayed above a set of warp zone pipes.")).setItem(new Item(this.frame, '\u00f1', "Warp Zone Message", this.warpZoneMessage, -1, 1, 0, 0, false, false, 8));
        this.warpZoneMessage.setIcon(null);
        this.warpZoneMessage.setFont(this.frame.bold);
        (this.warpZonePipe = new Button(this.frame, this.frame.game.textures.warpZonePipeOrange, null, "<html><center>Warp Zone</center></html>", "A Warp Zone pipe. It cannot be warped to, and always leads to a new level start.")).setItem(new Item(this.frame, '\u0108', "Warp Zone Pipe", this.warpZonePipe, -1, 13, 0, 0, false, true, 7));
        (this.customText = new Button(this.frame, this.frame.game.textures.symbols.get('a'), null, "<html><center>Custom<br>Text</center></hrml>", "Letters that can be individually inserted as background decorations.")).setItem(new Item(this.frame, this.frame.game.textures.customTextCharsInverted.get('a'), "Custom Text", this.customText, -1, 2, 0, 0, false, false, 15));
        this.customText.setIcon(null);
        this.customText.setFont(this.frame.bold);
        this.customTextItems = new HashMap<Character, Item>();
        for (final Map.Entry<Character, Character> entry : this.frame.game.textures.customTextChars.entrySet()) {
            final Item i = new Item(this.frame, entry.getKey(), entry.getValue().toString().toUpperCase() + " Symbol", this.customText, -1, 2, 0, 0, false, false, 15);
            this.customTextItems.put(entry.getKey(), i);
        }
        this.setPipeColor(1);
    }
    
    @Override
    public void construct() {
        this.add(this.topWOChomp);
        this.add(this.bottomWOChomp);
        this.add(this.leftWOChomp);
        this.add(this.rightWOChomp);
        this.add(this.topSection);
        this.add(this.warpZonePipe);
        this.add(this.topWChomp);
        this.add(this.bottomWChomp);
        this.add(this.leftWChomp);
        this.add(this.rightWChomp);
        this.add(this.sideSection);
        this.add(this.warpZoneMessage);
        this.add(this.topConnector);
        this.add(this.bottomConnector);
        this.add(this.leftConnector);
        this.add(this.rightConnector);
        this.add(this.doubleConnector);
        this.add(this.customText);
    }
    
    @Override
    public void refreshIcons() {
    }
    
    public int getCurrentPipeColor() {
        return this.currentPipeColor;
    }
    
    public void setPipeColor(final int type) {
        if (type == 0) {
            final int levelType = this.frame.levelPanel.level.levelType;
            if (levelType == Level.LEVEL_TYPE_OUTSIDE || levelType == Level.LEVEL_TYPE_COIN_ZONE_DAY || levelType == Level.LEVEL_TYPE_COIN_ZONE_NIGHT || levelType == Level.LEVEL_TYPE_UNDERGROUND) {
                this.setPipeType(1);
            }
            else if (levelType == Level.LEVEL_TYPE_CASTLE || levelType == Level.LEVEL_TYPE_OUTSIDE_NIGHT || levelType == Level.LEVEL_TYPE_GHOST_HOUSE) {
                this.setPipeType(2);
            }
            else if (levelType == Level.LEVEL_TYPE_UNDER_WATER) {
                this.setPipeType(3);
            }
        }
        else {
            this.setPipeType(type);
        }
    }
    
    private void setPipeType(final int pipeColor) {
        final boolean light = this.frame.levelPanel.level == null || this.frame.levelPanel.level.levelType == 0 || this.frame.levelPanel.level.levelType == 5 || this.frame.levelPanel.level.levelType == 6 || this.frame.levelPanel.level.levelType == 3;
        ImageIcon[] pipeImages;
        if (pipeColor == 1) {
            pipeImages = this.frame.game.textures.greenPipes;
        }
        else if (pipeColor == 3) {
            pipeImages = this.frame.game.textures.bluePipes;
        }
        else if (pipeColor == 2) {
            pipeImages = this.frame.game.textures.whitePipes;
        }
        else {
            if (pipeColor != 4) {
                throw new IllegalStateException("Unknown pipe color type requested: " + pipeColor);
            }
            pipeImages = this.frame.game.textures.orangePipes;
        }
        int shadowType;
        if (light) {
            shadowType = 0;
        }
        else {
            shadowType = 1;
        }
        this.topWChomp.setImages(ImageBuilder.createPipeImage(2, pipeColor, shadowType, false), null);
        this.bottomWChomp.setImages(ImageBuilder.createPipeImage(3, pipeColor, shadowType, false), null);
        this.leftWChomp.setImages(ImageBuilder.createPipeImage(0, pipeColor, shadowType, false), null);
        this.rightWChomp.setImages(ImageBuilder.createPipeImage(1, pipeColor, shadowType, false), null);
        this.topWOChomp.setImages(pipeImages[2], null);
        this.bottomWOChomp.setImages(pipeImages[3], null);
        this.leftWOChomp.setImages(pipeImages[0], null);
        this.rightWOChomp.setImages(pipeImages[1], null);
        this.topSection.setImages(pipeImages[4], null);
        this.sideSection.setImages(pipeImages[5], null);
        this.leftConnector.setImages(pipeImages[6], null);
        this.rightConnector.setImages(pipeImages[7], null);
        this.doubleConnector.setImages(pipeImages[10], null);
        this.topConnector.setImages(pipeImages[8], null);
        this.bottomConnector.setImages(pipeImages[9], null);
        this.currentPipeColor = pipeColor;
    }
}
