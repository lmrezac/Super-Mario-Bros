// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.awt.Image;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import supermario.game.Game;
import supermario.game.LevelLoader;

public final class Item
{
    private BuilderFrame frame;
    public char character;
    public String name;
    public Button button;
    public Warp warp;
    public int tilesWidth;
    public int tilesHeight;
    public int checkpointType;
    public int oscOffset;
    public int coinCount;
    public int displayWarpNumber;
    public int fireballColor;
    public int warpPipeColor;
    public boolean inserted;
    public boolean draggable;
    public boolean immobile;
    public boolean flip;
    public boolean flippable;
    public boolean shifted;
    public boolean shiftable;
    public boolean speedBoost;
    public boolean platformRepeatable;
    public boolean singlePlatform;
    public boolean poison;
    public boolean redPiranha;
    public boolean superSpring;
    public int oldXTile;
    public int oldYTile;
    public int xTile;
    public int yTile;
    private int xPixel;
    private int yPixel;
    public static final int ANY_ROW = -1;
    public static final int RANDOM_COIN_COUNT = 0;
    public int requiredRow;
    public int horizontalDrawOffset;
    public int verticalDrawOffset;
    public static final int PLATFORM_PADDING = 0;
    public static final int REQUIRES_BACKGROUND = 0;
    public static final int REQUIRES_SHIFTABLE_BACKGROUND = 1;
    public static final int REQUIRES_IMAGE_TILES = 2;
    public static final int REQUIRES_REP_PLATFORM = 3;
    public static final int REQUIRES_FIREBAR = 4;
    public static final int REQUIRES_FLYING_KOOPA_V = 5;
    public static final int REQUIRES_FLYING_KOOPA_H = 6;
    public static final int REQUIRES_OSC_H_PLATFORM = 7;
    public static final int REQUIRES_BRIDGE = 8;
    public static final int REQUIRES_LEFT_FACING_PIPE = 9;
    public static final int REQUIRES_RIGHT_FACING_PIPE = 10;
    public static final int REQUIRES_UP_FACING_PIPE = 11;
    public static final int REQUIRES_DOWN_FACING_PIPE = 12;
    public static final int REQUIRES_WARP_ZONE_PIPE = 13;
    public static final int REQUIRES_ZIG_ZAG_FISH = 14;
    public static final int REQUIRES_COLUMN_INSERTION = 15;
    public static final int REQUIRES_COLUMN_REMOVAL = 16;
    public static final int REQUIRES_BEANSTALK_ARRIVAL = 17;
    public static final int REQUIRES_CONTENTS_BLOCK = 18;
    public static final int REQUIRES_BEANSTALK_BLOCK = 19;
    public static final int REQUIRES_ENDING_SPACE = 20;
    public static final int REQUIRES_HAMMER_BRO = 21;
    public static final int REQUIRES_FALLING_PLATFORM = 22;
    public static final int REQUIRES_PULLEY_PLATFORM = 23;
    public static final int REQUIRES_OSC_V_PLATFORM = 24;
    public static final int REQUIRES_INF_CORRIDOR = 25;
    public static final int REQUIRES_INVISIBLE_BLOCK = 26;
    public static final int REQUIRES_INVISIBLE_CONTENTS_BLOCK = 27;
    public static final int REQUIRES_INVISIBLE_BEANSTALK_BLOCK = 28;
    public int spaceRequirement;
    public int propertiesType;
    
    public Item(final BuilderFrame frame, final char character, final String name, final Button button, final int requiredRow, final int spaceRequirement, final int horizontalDrawOffset, final int verticalDrawOffset, final boolean draggable, final boolean immobile, final int propertiesType) {
        this.frame = frame;
        //old character exceptions. THIS IS TEMPORARY. REMOVE WHEN DONE.
        if(character == 'm' || character == 'n' || character == 'o' || character == 'p')
        	this.character = 'l';
        else 
        	this.character = character;
        this.name = name;
        this.button = button;
        if (button.placedImage == null) {
        	try{
            this.tilesWidth = this.button.iconImage.getIconWidth() / 8;
            this.tilesHeight = this.button.iconImage.getIconHeight() / 8;
        	}catch(NullPointerException e){
        		tilesWidth = 2;
        		tilesHeight = 2;
        	}
        }
        else {
            this.tilesWidth = this.button.placedImage.getIconWidth() / 8;
            this.tilesHeight = this.button.placedImage.getIconHeight() / 8;
        }
        this.requiredRow = requiredRow;
        this.spaceRequirement = spaceRequirement;
        this.horizontalDrawOffset = horizontalDrawOffset;
        this.verticalDrawOffset = verticalDrawOffset;
        this.draggable = draggable;
        this.immobile = immobile;
        this.propertiesType = propertiesType;
        this.coinCount = 0;
        this.fireballColor = 0;
        this.warpPipeColor = 0;
        this.checkpointType = 1;
        if (this.isWarpable()) {
            this.warp = new Warp(false, false, -1, -1, -1, -1, this);
        }
        this.flippable = LevelLoader.isFlippableEnemy(character);
        this.shiftable = LevelLoader.isShiftableBackground(character);
        this.platformRepeatable = LevelLoader.isRepeatablePlatform(character);
    }
    
    public Item copy() {
        final Item itemCopy = new Item(this.frame, this.character, this.name, this.button, this.requiredRow, this.spaceRequirement, this.horizontalDrawOffset, this.verticalDrawOffset, this.draggable, this.immobile, this.propertiesType);
        itemCopy.inserted = this.inserted;
        itemCopy.flip = this.flip;
        itemCopy.flippable = this.flippable;
        itemCopy.coinCount = this.coinCount;
        itemCopy.oscOffset = this.oscOffset;
        itemCopy.checkpointType = this.checkpointType;
        itemCopy.fireballColor = this.fireballColor;
        itemCopy.displayWarpNumber = this.displayWarpNumber;
        itemCopy.warpPipeColor = this.warpPipeColor;
        itemCopy.shifted = this.shifted;
        itemCopy.speedBoost = this.speedBoost;
        itemCopy.singlePlatform = this.singlePlatform;
        itemCopy.poison = this.poison;
        itemCopy.redPiranha = this.redPiranha;
        itemCopy.superSpring = this.superSpring;
        if (this.isWarpable()) {
            itemCopy.warp = this.warp;
            itemCopy.warp.item = itemCopy;
            this.warp = new Warp(false, false, -1, -1, -1, -1, this);
        }
        return itemCopy;
    }
    
    public static void transferProperties(final Item from, final Item to) {
        to.flip = from.flip;
        to.flippable = from.flippable;
        to.coinCount = from.coinCount;
        to.oscOffset = from.oscOffset;
        to.checkpointType = from.checkpointType;
        to.fireballColor = from.fireballColor;
        to.displayWarpNumber = from.displayWarpNumber;
        to.warpPipeColor = from.warpPipeColor;
        to.shifted = from.shifted;
        to.speedBoost = from.speedBoost;
        to.singlePlatform = from.singlePlatform;
        to.poison = from.poison;
        to.redPiranha = from.redPiranha;
    }
    
    public boolean isWarpable() {
        return this.character == '!' || this.character == '\u2663' || LevelLoader.isSideOpeningPipe(this.character) || (this.character == '~' || this.character == ';') || (this.character == '\u0110' || this.character == '¿') || (this.character == '\u0108' || this.character == '\u03e4') || (this.character == 'G' || this.character == 'N' || this.character == '\u25c4') || (this.character == '\u03d8' || this.character == '\u03dc') || this.character == '½';
    }
    
    public void insertInLevel(final int xTile, final int yTile) {
        this.frame.levelPanel.modified = true;
        this.xTile = xTile;
        this.yTile = yTile;
        this.replace(true);
    }
    
    private boolean onlyFillsImageTiles() {
        return this.spaceRequirement == 2 || this.spaceRequirement == 19 || (this.spaceRequirement == 9 || this.spaceRequirement == 10 || this.spaceRequirement == 11 || this.spaceRequirement == 12) || (this.spaceRequirement == 13 || this.spaceRequirement == 28);
    }
    
    private void replace(final boolean add) {
        if (this.spaceRequirement == 0) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile, this.yTile));
        }
        else if (this.spaceRequirement == 1) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + 1, this.yTile));
        }
        else if (this.onlyFillsImageTiles()) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + this.tilesWidth - 1, this.yTile + this.tilesHeight - 1));
        }
        else if (this.spaceRequirement == 21) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile - 2, this.yTile, this.xTile - 2 + this.tilesWidth - 1, this.yTile + this.tilesHeight - 1));
        }
        else if (this.spaceRequirement == 18 || this.spaceRequirement == 26 || this.spaceRequirement == 27) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + this.tilesWidth - 1, this.yTile + this.tilesWidth - 1));
        }
        else if (this.spaceRequirement == 17) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + this.tilesWidth - 1, Game.yTiles - 1));
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile + 2, this.yTile, this.xTile + 3, this.yTile + 5));
        }
        else if (this.spaceRequirement == 3) {
            final int width = this.button.placedImage.getIconWidth() / 8;
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, 0, this.xTile + width - 1, 0));
        }
        else if (this.spaceRequirement == 22 || this.spaceRequirement == 24) {
            final int width = this.button.placedImage.getIconWidth() / 8;
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + width - 1, this.yTile));
        }
        else if (this.spaceRequirement == 23) {
            final int width = getPulleyWidth(this.character);
            final int imageWidth = this.button.placedImage.getIconWidth() / 8;
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + width - 1, this.yTile));
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile + imageWidth - width, this.yTile + 4, this.xTile + imageWidth - 1, this.yTile + 4));
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile + width / 2, this.yTile - 8, this.xTile + imageWidth - width / 2 - 1, this.yTile - 8));
        }
        else if (this.spaceRequirement == 4) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + 1, this.yTile + 1));
        }
        else if (this.spaceRequirement == 14) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile - 2, this.xTile + 1, this.yTile + 3));
        }
        else if (this.spaceRequirement == 5) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile - 7, this.xTile + this.tilesWidth - 1, this.yTile + 3 + 7));
        }
        else if (this.spaceRequirement == 6) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile - 6, this.yTile - 1, this.xTile + 2 + 6 - 1, this.yTile + 3));
        }
        else if (this.spaceRequirement == 7) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile - 6, this.yTile, this.xTile - 6 + this.button.placedImage.getIconWidth() / 8 - 1, this.yTile));
        }
        else if (this.spaceRequirement == 8) {
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile, this.yTile));
        }
        else if (this.spaceRequirement == 20) {
            if (this.frame.levelPanel.level.levelEndType != 0) {
                this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, 0, this.frame.levelPanel.level.items[0].length, this.frame.levelPanel.level.items.length));
            }
        }
        else if (this.spaceRequirement == 25) {
            final int displacement = 56;
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile - displacement, this.yTile, this.xTile - displacement + 2 - 1, this.yTile + this.tilesHeight - 1));
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile, this.yTile, this.xTile + 7, this.yTile + this.tilesHeight - 1));
            this.setItemInRect(add, ItemFitting.Rect.tempRect.setBounds(this.xTile - displacement + 2, this.yTile + 3, this.xTile - 1, this.yTile + 3));
        }
    }
    
    private void setItemInRect(final boolean add, final ItemFitting.Rect rect) {
        final int levelLength = this.frame.levelPanel.level.items[0].length;
        for (int i = rect.y1; i <= rect.y2; ++i) {
            for (int j = rect.x1; j <= rect.x2; ++j) {
                if (j >= 0 && j < levelLength && i >= 0 && i < Game.yTiles) {
                    if (add) {
                        this.frame.levelPanel.level.items[i][j] = this;
                    }
                    else {
                        this.frame.levelPanel.level.items[i][j] = null;
                    }
                }
            }
        }
    }
    
    public void removeFromLevel() {
        this.replace(false);
    }
    
    public static int getPulleyWidth(final char c) {
        if (c == '\u0398') {
            return 4;
        }
        if (c == '\u25ac') {
            return 6;
        }
        throw new RuntimeException("Wrong character given: " + c);
    }
    
    public static boolean isPulleyLeftSide(final Item tempItem, final int xTile) {
        return xTile - tempItem.xTile <= getPulleyWidth(tempItem.character);
    }
    
    public void drawAtMouse(final Graphics2D g2D, final int xPixel, int yPixel, final double scaleFactor, final double TILE_SIZE, final boolean drawFitRect) {
        if (this.requiredRow != -1) {
            yPixel = (int)Math.round(this.requiredRow * 8 * scaleFactor);
        }
        if (this.spaceRequirement == 15 || this.spaceRequirement == 16) {
            if (this.frame.levelPanel.columnOperationPossible()) {
                if (this.spaceRequirement == 15) {
                    g2D.setColor(Color.WHITE);
                }
                else if (this.spaceRequirement == 16) {
                    if (this.frame.levelPanel.level.items[0].length <= Game.xTiles || ItemFitting.isInReservedColumn(this.frame.levelPanel.level, this.xTile, this.yTile)) {
                        return;
                    }
                    g2D.setColor(Color.RED);
                }
                final Composite oldComposite = g2D.getComposite();
                g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
                g2D.fillRect(xPixel, 0, (int)Math.ceil(TILE_SIZE * scaleFactor), this.frame.levelPanel.getHeight());
                g2D.setComposite(oldComposite);
            }
        }
        else if (!this.flip) {
            int xOffset = 0;
            if (this.shiftable && this.shifted) {
                xOffset = (int)Math.round((this.button.placedImage.getIconWidth() - 8) * scaleFactor);
            }
            if (this.character == '\u00fd' && this.shifted) {
                final int width = (int)Math.round(this.button.placedImage.getIconWidth() * scaleFactor);
                final int height = (int)Math.round(this.button.placedImage.getIconHeight() * scaleFactor);
                g2D.drawImage(this.getImage(), Math.round(xPixel + width), yPixel, xPixel, yPixel + height, 0, 0, this.button.placedImage.getIconWidth(), this.button.placedImage.getIconHeight(), null);
            }
            else if (this.poison) {
                final int w = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor);
                final int halfH = (int)Math.ceil(this.button.placedImage.getIconHeight() * scaleFactor / 2.0);
                g2D.drawImage(this.frame.game.textures.poisonMushroom.getImage(), xPixel, (int)Math.round(yPixel - this.verticalDrawOffset * scaleFactor), xPixel + w, (int)Math.round(yPixel - this.verticalDrawOffset * scaleFactor + halfH), 0, 0, 16, 16, null);
                g2D.drawImage(this.getImage(), xPixel, yPixel, xPixel + w, yPixel + halfH, 0, 16, 16, 32, null);
            }
            else {
                g2D.drawImage(this.getImage(), (int)Math.round(xPixel - this.horizontalDrawOffset * scaleFactor - xOffset), (int)Math.round(yPixel - this.verticalDrawOffset * scaleFactor), (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor), (int)Math.ceil(this.button.placedImage.getIconHeight() * scaleFactor), null);
            }
            if (this.character == '\u00f7') {
                this.drawInfiniteCorridorTickMarks(g2D, xPixel, yPixel, TILE_SIZE, scaleFactor, TILE_SIZE, true);
            }
            if (this.warp != null && this.warp.outgoing && !LevelLoader.isBeanstalkBlock(this.character) && this.character != '\u0108') {
                this.drawOutgoingWarpIndicator(g2D, xPixel, yPixel, scaleFactor);
            }
            if (LevelLoader.isOscSprite(this.character)) {
                if (LevelLoader.isFlyingKoopa(this.character)) {
                    this.drawOscKoopaIndicator(g2D, xPixel, yPixel, scaleFactor, false);
                }
                else {
                    this.drawOscPlatformIndicator(g2D, xPixel, yPixel, scaleFactor, false);
                }
            }
        }
        else {
            final int width2 = (int)Math.round(this.button.placedImage.getIconWidth() * scaleFactor);
            final int height2 = (int)Math.round(this.button.placedImage.getIconHeight() * scaleFactor);
            g2D.drawImage(this.getImage(), xPixel + width2, (int)Math.ceil(yPixel - this.verticalDrawOffset * scaleFactor), xPixel, (int)Math.ceil(yPixel - this.verticalDrawOffset * scaleFactor + height2), 0, 0, this.button.placedImage.getIconWidth(), this.button.placedImage.getIconHeight(), null);
        }
        if (drawFitRect) {
            this.drawFitRect(g2D, xPixel, yPixel, scaleFactor, TILE_SIZE);
        }
    }
    
    public void drawInLevel(final Graphics2D g2D, final double leftMostX, final double scaleFactor, final double TILE_SIZE, final boolean includeHighlights) {
        char c = this.character;
        if(c == '«'||c=='©'||c=='¦'||c=='¥'||c=='¤'||c=='£'||c=='\u00fd'||c=='¢'||c=='¡')
        	System.out.println("DRAWING BACKGROUND");
    	this.xPixel = (int)Math.round(this.xTile * TILE_SIZE * scaleFactor - leftMostX * scaleFactor - this.horizontalDrawOffset * scaleFactor);
        this.yPixel = (int)Math.round(this.yTile * TILE_SIZE * scaleFactor - this.verticalDrawOffset * scaleFactor);
        if (!this.flip) {
            int xOffset = 0;
            if (this.shiftable && this.shifted) {
                xOffset = (int)Math.round((this.button.placedImage.getIconWidth() - 8) * scaleFactor);
            }
            if (this.character == '\u00fd' && this.shifted) {
                final int width = (int)Math.round(this.button.placedImage.getIconWidth() * scaleFactor);
                final int height = (int)Math.round(this.button.placedImage.getIconHeight() * scaleFactor);
                g2D.drawImage(this.getImage(), Math.round(this.xPixel + width), this.yPixel, this.xPixel, this.yPixel + height, 0, 0, this.button.placedImage.getIconWidth(), this.button.placedImage.getIconHeight(), null);
            }
            else if (this.spaceRequirement == 18 || this.spaceRequirement == 27) {
                final Composite oldComposite = g2D.getComposite();
                g2D.setComposite(AlphaComposite.getInstance(3, 0.6f));
                final int w = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor);
                final int halfH = (int)Math.ceil(this.button.placedImage.getIconHeight() * scaleFactor / 2.0);
                if (this.poison) {
                    g2D.drawImage(this.frame.game.textures.poisonMushroom.getImage(), this.xPixel, this.yPixel, this.xPixel + w, this.yPixel + halfH, 0, 0, 16, 16, null);
                }
                else {
                    g2D.drawImage(this.getImage(), this.xPixel, this.yPixel, this.xPixel + w, this.yPixel + halfH, 0, 0, 16, 16, null);
                }
                g2D.setComposite(oldComposite);
                g2D.drawImage(this.getImage(), this.xPixel, this.yPixel + halfH, this.xPixel + w, this.yPixel + halfH * 2, 0, 16, 16, 32, null);
            }
            else {
                g2D.drawImage(this.getImage(), this.xPixel - xOffset, this.yPixel, (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor), (int)Math.ceil(this.button.placedImage.getIconHeight() * scaleFactor), null);
            }
            if (this.character == '\u00f7') {
                this.drawInfiniteCorridorTickMarks(g2D, this.xPixel, this.yPixel, TILE_SIZE, scaleFactor, leftMostX, false);
            }
            if (this.warp != null && this.warp.outgoing && !LevelLoader.isBeanstalkBlock(this.character) && this.character != '\u0108') {
                this.drawOutgoingWarpIndicator(g2D, this.xPixel, this.yPixel, scaleFactor);
            }
            if (LevelLoader.isOscSprite(this.character) && includeHighlights) {
                if (LevelLoader.isFlyingKoopa(this.character)) {
                    this.drawOscKoopaIndicator(g2D, this.xPixel, this.yPixel, scaleFactor, true);
                }
                else {
                    this.drawOscPlatformIndicator(g2D, this.xPixel, this.yPixel, scaleFactor, true);
                }
            }
        }
        else {
            final int width2 = (int)Math.round(this.button.placedImage.getIconWidth() * scaleFactor);
            final int height2 = (int)Math.round(this.button.placedImage.getIconHeight() * scaleFactor);
            g2D.drawImage(this.getImage(), this.xPixel + width2, this.yPixel, this.xPixel, this.yPixel + height2, 0, 0, this.button.placedImage.getIconWidth(), this.button.placedImage.getIconHeight(), null);
        }
        if (includeHighlights) {
            if (this.spaceRequirement == 0 || this.spaceRequirement == 1) {
                if (this.character == '¾' || this.character == '\u00e5' || this.character == 'g' || this.character == 'µ' || this.character == 'f') {
                    return;
                }
                g2D.setColor(Color.WHITE);
                final Composite oldComposite2 = g2D.getComposite();
                g2D.setComposite(AlphaComposite.getInstance(3, 0.25f));
                int width = (int)Math.ceil(8.0 * scaleFactor);
                if (this.spaceRequirement == 1) {
                    width *= 2;
                }
                g2D.fillRect(this.xPixel, this.yPixel, width, (int)Math.ceil(8.0 * scaleFactor));
                g2D.setComposite(oldComposite2);
            }
            else if (this.spaceRequirement == 3) {
                g2D.setColor(Color.WHITE);
                final Composite oldComposite2 = g2D.getComposite();
                g2D.setComposite(AlphaComposite.getInstance(3, 0.25f));
                final int width = (int)Math.ceil(8 * this.tilesWidth * scaleFactor);
                g2D.fillRect(this.xPixel, 0, width, (int)Math.ceil(8.0 * scaleFactor));
                g2D.setComposite(oldComposite2);
            }
        }
    }
    
    private void drawOscPlatformIndicator(final Graphics2D g2D, final int xPixel, final int yPixel, final double scaleFactor, final boolean inLevel) {
        final int offsetOffset = (int)Math.round(this.oscOffset * 8 * scaleFactor);
        final int circleSize = (int)Math.round(8.0 * scaleFactor);
        int arrowHOffset = 0;
        int arrowVOffset = 0;
        if (inLevel) {
            if (LevelLoader.isVOscPlatform(this.character)) {
                arrowVOffset = (int)Math.round(this.verticalDrawOffset * scaleFactor);
            }
            else if (LevelLoader.isHOscPlatform(this.character)) {
                arrowHOffset = (int)Math.round(this.horizontalDrawOffset * scaleFactor);
            }
        }
        final Composite oldComposite = g2D.getComposite();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
        g2D.setColor(Color.BLUE);
        g2D.fillOval(xPixel + arrowHOffset + offsetOffset, yPixel + arrowVOffset, circleSize, circleSize);
        g2D.setComposite(oldComposite);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private void drawOscKoopaIndicator(final Graphics2D g2D, final int xPixel, final int yPixel, final double scaleFactor, final boolean inLevel) {
        if (this.oscOffset == 0) {
            return;
        }
        ImageIcon image = null;
        if (this.character == '¬' || this.character == '*') {
            image = this.frame.game.textures.lightKoopa4;
        }
        else if (this.character == '±' || this.character == '-') {
            image = this.frame.game.textures.darkKoopa4;
        }
        else {
            image = this.frame.game.textures.redKoopa4;
        }
        double hOffset = 0.0;
        double vOffset = 0.0;
        if (inLevel) {
            hOffset = this.horizontalDrawOffset * scaleFactor;
            vOffset = this.verticalDrawOffset * scaleFactor;
        }
        boolean reverseImage = false;
        if (LevelLoader.isHOscKoopa(this.character)) {
            if (this.oscOffset == 1) {
                hOffset += -this.horizontalDrawOffset * scaleFactor;
                reverseImage = true;
            }
            else if (this.oscOffset == 2) {
                hOffset += image.getIconWidth() * scaleFactor;
                reverseImage = true;
            }
            else if (this.oscOffset == 3) {
                hOffset += (this.button.placedImage.getIconWidth() / 2 - image.getIconWidth() / 2) * scaleFactor;
            }
        }
        else if (this.oscOffset == 1) {
            vOffset += this.verticalDrawOffset * scaleFactor;
        }
        else if (this.oscOffset == 2) {
            vOffset += -image.getIconHeight() / 2 * scaleFactor;
        }
        else if (this.oscOffset == 3) {
            vOffset += -this.verticalDrawOffset * scaleFactor;
        }
        final Composite oldComposite = g2D.getComposite();
        final AffineTransform oldTransform = g2D.getTransform();
        g2D.setComposite(AlphaComposite.getInstance(3, 0.4f));
        g2D.translate(xPixel + hOffset, yPixel + vOffset);
        g2D.scale(scaleFactor, scaleFactor);
        if (reverseImage) {
            g2D.scale(-1.0, 1.0);
            g2D.translate(-image.getIconWidth(), 0);
        }
        g2D.drawImage(image.getImage(), 0, 0, null);
        g2D.setTransform(oldTransform);
        g2D.setComposite(oldComposite);
    }
    
    private void drawOutgoingWarpIndicator(final Graphics2D g2D, final int xPixel, final int yPixel, final double scaleFactor) {
        final Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
        g2D.setStroke(new BasicStroke((int)Math.round(2.0 * scaleFactor)));
        g2D.setColor(Color.BLUE);
        if (LevelLoader.isUpwardOpeningPipe(this.character)) {
            g2D.drawRect(xPixel, yPixel, (int)Math.round(this.tilesWidth * 8 * scaleFactor), (int)Math.round(16.0 * scaleFactor));
        }
        else if (LevelLoader.isDownwardOpeningPipe(this.character)) {
            g2D.drawRect(xPixel, (int)Math.round(yPixel + 16.0 * scaleFactor), (int)Math.round(this.tilesWidth * 8 * scaleFactor), (int)Math.round(16.0 * scaleFactor));
        }
        else if (LevelLoader.isLeftOpeningPipe(this.character)) {
            g2D.drawRect(xPixel, yPixel, (int)Math.round(16.0 * scaleFactor), (int)Math.round(8 * this.tilesHeight * scaleFactor));
        }
        else if (LevelLoader.isRightOpeningPipe(this.character)) {
            g2D.drawRect((int)Math.round(xPixel + 8 * this.tilesWidth * scaleFactor - 16.0 * scaleFactor), yPixel, (int)Math.round(16.0 * scaleFactor), (int)Math.round(8 * this.tilesHeight * scaleFactor));
        }
        g2D.setComposite(oldComposite);
    }
    
    private void drawInfiniteCorridorTickMarks(final Graphics2D g2D, final int xPixel, final int yPixel, final double TILE_SIZE, final double scaleFactor, final double leftMostX, final boolean atMouse) {
        g2D.setColor(Color.WHITE);
        if (atMouse) {
            g2D.fillRect((int)Math.round(xPixel - (Game.xTiles / 2 - 1 + 2) * TILE_SIZE * scaleFactor - scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
            g2D.fillRect((int)Math.round(xPixel - (Game.xTiles / 2 - 1) * TILE_SIZE * scaleFactor - this.horizontalDrawOffset * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
            g2D.fillRect((int)Math.round(xPixel + (Game.xTiles / 2 + 1 + 4) * TILE_SIZE * scaleFactor - this.horizontalDrawOffset * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
            final int trapWidth = 8;
            g2D.fillRect((int)Math.round(xPixel + (Game.xTiles / 2 - 1 + trapWidth + 2) * TILE_SIZE * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
        }
        else {
            g2D.fillRect((int)Math.round((this.xTile - Game.xTiles / 2 + 1 - 2) * TILE_SIZE * scaleFactor - leftMostX * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
            g2D.fillRect((int)Math.round((this.xTile - Game.xTiles / 2 + 1) * TILE_SIZE * scaleFactor - (leftMostX + this.horizontalDrawOffset) * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
            g2D.fillRect((int)Math.round((this.xTile + Game.xTiles / 2 + 1 + 4) * TILE_SIZE * scaleFactor - (leftMostX + this.horizontalDrawOffset) * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
            final int trapWidth = 8;
            g2D.fillRect((int)Math.round((this.xTile + Game.xTiles / 2 - 1 + trapWidth + 2) * TILE_SIZE * scaleFactor - leftMostX * scaleFactor), yPixel, 3, (int)Math.ceil(this.tilesHeight * TILE_SIZE * scaleFactor));
        }
    }
    
    private Image getImage() {
        if (this.speedBoost) {
            if (this.button.placedImage == this.frame.textures.displayFirebarDarkLong) {
                return this.frame.textures.displayFirebarDarkLongFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarDarkShort) {
                return this.frame.textures.displayFirebarDarkShortFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarLightShort) {
                return this.frame.textures.displayFirebarLightShortFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarLightLong) {
                return this.frame.textures.displayFirebarLightLongFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarStoneShort) {
                return this.frame.textures.displayFirebarStoneShortFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarStoneLong) {
                return this.frame.textures.displayFirebarStoneLongFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarWaterShort) {
                return this.frame.textures.displayFirebarWaterShortFast.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayFirebarWaterLong) {
                return this.frame.textures.displayFirebarWaterLongFast.getImage();
            }
            return null;
        }
        else if (this.singlePlatform) {
            if (this.button.placedImage == this.frame.textures.displayPlatformExtraShortRepDown) {
                return this.frame.textures.displayPlatformExtraShortRepDownSingle.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayPlatformExtraShortRepUp) {
                return this.frame.textures.displayPlatformExtraShortRepUpSingle.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayPlatformShortRepDown) {
                return this.frame.textures.displayPlatformShortRepDownSingle.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayPlatformShortRepUp) {
                return this.frame.textures.displayPlatformShortRepUpSingle.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayPlatformLongRepUp) {
                return this.frame.textures.displayPlatformLongRepUpSingle.getImage();
            }
            if (this.button.placedImage == this.frame.textures.displayPlatformLongRepDown) {
                return this.frame.textures.displayPlatformLongRepDownSingle.getImage();
            }
            return null;
        }
        else if (this.character == '.' && this.fireballColor != 2) {
            if (this.fireballColor == 0) {
                return this.frame.textures.displayLavaballRed.getImage();
            }
            if (this.fireballColor == 1) {
                return this.frame.textures.displayLavaballBlue.getImage();
            }
            return null;
        }
        else if (this.character == '\u0108' && this.warpPipeColor != 0) {
            if (this.warpPipeColor == 1) {
                return this.frame.game.textures.warpZonePipeGreen.getImage();
            }
            if (this.warpPipeColor == 2) {
                return this.frame.game.textures.warpZonePipeWhite.getImage();
            }
            if (this.warpPipeColor == 3) {
                return this.frame.game.textures.warpZonePipeBlue.getImage();
            }
            return null;
        }
        else if (this.button.placedImage == this.frame.textures.displayFlagEndingGreen) {
            if (this.frame.levelPanel.level.pipeColor == 2 || (this.frame.levelPanel.level.pipeColor == 0 && this.frame.levelPanel.level.levelType == 4)) {
                return this.frame.textures.displayFlagEndingWhite.getImage();
            }
            return this.button.placedImage.getImage();
        }
        else {
            if (this.frame.game.textures.customTextChars.containsKey(this.character)) {
                return this.frame.game.textures.symbols.get(this.frame.game.textures.customTextChars.get(this.character)).getImage();
            }
            if (this.redPiranha) {
                return ImageBuilder.createPipeImage(LevelLoader.getPipeType(this.character), this.frame.pipesPanel.getCurrentPipeColor(), 0, true).getImage();
            }
            if (!this.superSpring) {
                return this.button.placedImage.getImage();
            }
            if (this.button.iconImage == this.frame.game.textures.springLight) {
                return this.frame.game.textures.springGreenLight.getImage();
            }
            if (this.button.iconImage == this.frame.game.textures.springDark) {
                return this.frame.game.textures.springGreenDark.getImage();
            }
            if (this.button.iconImage == this.frame.game.textures.springGray) {
                return this.frame.game.textures.springGreenGray.getImage();
            }
            throw new IllegalStateException("Unknown super spring image type needed.");
        }
    }
    
    private void drawFitRect(final Graphics2D g2D, final int xPixel, final int yPixel, final double scaleFactor, final double TILE_SIZE) {
        g2D.setColor(Color.RED);
        final Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
        if (this.spaceRequirement == 0) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 1) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 2) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
        }
        else if (this.spaceRequirement == 21) {
            final int blockSize = (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor);
            g2D.fillRect(xPixel - blockSize, yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
        }
        else if (this.spaceRequirement == 18) {
            final int blockSize = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(xPixel, yPixel - blockSize, blockSize, blockSize * 2);
        }
        else if (this.spaceRequirement == 26) {
            final int blockSize = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(xPixel, yPixel, blockSize, blockSize * 2);
        }
        else if (this.spaceRequirement == 27) {
            final int blockSize = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(xPixel, yPixel - blockSize, blockSize, blockSize * 3);
        }
        else if (this.spaceRequirement == 17) {
            final int width = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(xPixel, yPixel, width, (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
            g2D.fillRect(xPixel + width, yPixel, (int)Math.ceil(TILE_SIZE * scaleFactor * 2.0), (int)Math.ceil(TILE_SIZE * 6.0 * scaleFactor));
        }
        else if (this.spaceRequirement == 3) {
            final int padding = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int width2 = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor + padding * 2);
            g2D.fillRect(xPixel - padding, 0, width2, this.frame.levelPanel.getHeight());
        }
        else if (this.spaceRequirement == 22) {
            final int padding = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int width2 = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor + padding * 2);
            g2D.fillRect(xPixel - padding, yPixel, width2, this.frame.levelPanel.getHeight() - yPixel);
        }
        else if (this.spaceRequirement == 23) {
            final int width = (int)Math.ceil(getPulleyWidth(this.character) * TILE_SIZE * scaleFactor);
            final int aboveArea = (int)Math.ceil(6.0 * TILE_SIZE * scaleFactor);
            final int padding2 = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int imageSize = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor);
            final int height = (int)Math.ceil(Game.yTiles * TILE_SIZE * scaleFactor - (yPixel - aboveArea));
            g2D.fillRect(xPixel - padding2, yPixel - aboveArea, width + padding2 * 2, height);
            g2D.fillRect(xPixel + imageSize - width - padding2, yPixel - aboveArea, width + padding2 * 2, height);
            g2D.fillRect(xPixel + width / 2, yPixel - (int)Math.ceil(8.0 * TILE_SIZE * scaleFactor), (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor) - width, (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 24) {
            final int padding = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int yOffset = (int)Math.ceil(4.0 * TILE_SIZE * scaleFactor);
            final int height2 = (int)Math.ceil(this.button.placedImage.getIconHeight() * scaleFactor + 2.0 * (4.0 * TILE_SIZE) * scaleFactor);
            final int width3 = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor + padding * 2);
            g2D.fillRect(xPixel - padding, yPixel - (int)Math.ceil(this.verticalDrawOffset * scaleFactor) - yOffset, width3, height2);
        }
        else if (this.spaceRequirement == 4) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor));
        }
        else if (this.spaceRequirement == 14) {
            g2D.fillRect(xPixel, (int)Math.ceil(yPixel - TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor * 3.0));
        }
        else if (this.spaceRequirement == 5) {
            final int flyHeight = (int)Math.ceil(7.0 * TILE_SIZE * scaleFactor);
            g2D.fillRect(xPixel, yPixel - flyHeight, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(flyHeight * 2 + TILE_SIZE * 3.0 * scaleFactor));
        }
        else if (this.spaceRequirement == 6) {
            final int flyWidth = (int)Math.ceil(6.0 * TILE_SIZE * scaleFactor);
            final int blockExtension = (int)Math.ceil(TILE_SIZE * scaleFactor);
            g2D.fillRect(xPixel - flyWidth, yPixel - blockExtension, (int)Math.ceil(flyWidth * 2 + TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor) + blockExtension * 2);
        }
        else if (this.spaceRequirement == 7) {
            final int flyWidth = (int)Math.ceil(6.0 * TILE_SIZE * scaleFactor);
            final int padding3 = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            g2D.fillRect(xPixel - flyWidth - padding3, yPixel - (int)Math.ceil(TILE_SIZE * scaleFactor * 4.0), (int)Math.ceil(this.tilesWidth * TILE_SIZE * scaleFactor + padding3 * 2), (int)Math.ceil(TILE_SIZE * scaleFactor * 9.0));
        }
        else if (this.spaceRequirement == 8) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 9) {
            g2D.fillRect((int)Math.round(xPixel - 2.0 * TILE_SIZE * scaleFactor), yPixel, (int)Math.round((2 + this.tilesWidth) * TILE_SIZE * scaleFactor), (int)Math.round(4.0 * TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 10) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil((2 + this.tilesWidth) * TILE_SIZE * scaleFactor), (int)Math.ceil(4.0 * TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 11 || this.spaceRequirement == 13) {
            g2D.fillRect(xPixel, (int)Math.round(yPixel - 4.0 * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * (4 + this.tilesHeight) * scaleFactor));
        }
        else if (this.spaceRequirement == 12) {
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * (4 + this.tilesHeight) * scaleFactor));
        }
        else if (this.spaceRequirement == 19) {
            final double blockSize2 = TILE_SIZE * 2.0 * scaleFactor;
            final int height2 = (int)Math.ceil(yPixel + blockSize2);
            g2D.fillRect(xPixel, 0, (int)Math.ceil(blockSize2), height2);
        }
        else if (this.spaceRequirement == 28) {
            final double blockSize2 = TILE_SIZE * 2.0 * scaleFactor;
            final int height2 = (int)Math.ceil(yPixel + blockSize2 * 2.0);
            g2D.fillRect(xPixel, 0, (int)Math.ceil(blockSize2), height2);
        }
        else if (this.spaceRequirement == 25) {
            final int displacement = 56;
            final int trapTiles = 8;
            g2D.fillRect(xPixel, yPixel, (int)Math.ceil(trapTiles * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
            g2D.fillRect(xPixel - (int)Math.ceil(displacement * TILE_SIZE * scaleFactor), yPixel, (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
            g2D.fillRect(xPixel - (int)Math.ceil((displacement - 2) * TILE_SIZE * scaleFactor), yPixel + (int)Math.ceil((this.tilesHeight - 1) * TILE_SIZE * scaleFactor), (int)Math.ceil((displacement - 2) * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        g2D.setComposite(oldComposite);
    }
    
    public void drawBlockingRectangle(final Graphics2D g2D, final double scaleFactor, final double TILE_SIZE) {
        g2D.setColor(Color.WHITE);
        final Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, 0.3f));
        if (this.spaceRequirement == 0) {
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 1) {
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 2) {
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
        }
        else if (this.spaceRequirement == 21) {
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
        }
        else if (this.spaceRequirement == 4) {
            g2D.fillRect(this.xPixel, (int)Math.ceil(this.yPixel + TILE_SIZE * (this.tilesHeight - 2) * scaleFactor), (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor));
        }
        else if (this.spaceRequirement == 14) {
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor * 3.0));
        }
        else if (this.spaceRequirement == 5) {
            final int flyHeight = (int)Math.ceil(7.0 * TILE_SIZE * scaleFactor);
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(flyHeight * 2 + TILE_SIZE * 3.0 * scaleFactor));
        }
        else if (this.spaceRequirement == 6) {
            final int flyWidth = (int)Math.ceil(6.0 * TILE_SIZE * scaleFactor);
            final int blockExtension = (int)Math.ceil(TILE_SIZE * scaleFactor);
            g2D.fillRect(this.xPixel, this.yPixel - blockExtension, (int)Math.ceil(flyWidth * 2 + TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor) + blockExtension * 2);
        }
        else if (this.spaceRequirement == 8) {
            g2D.fillRect(this.xPixel, (int)Math.ceil(this.yPixel + TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 25) {
            final int displacement = 56;
            final int trapTiles = 8;
            g2D.fillRect((int)Math.ceil(this.xPixel + displacement * TILE_SIZE * scaleFactor), this.yPixel, (int)Math.ceil(trapTiles * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * 2.0 * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
            g2D.fillRect((int)Math.ceil(this.xPixel + TILE_SIZE * scaleFactor * 2.0), this.yPixel + (int)Math.ceil((this.tilesHeight - 1) * TILE_SIZE * scaleFactor), (int)Math.ceil((displacement - 2) * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor));
        }
        else if (this.spaceRequirement == 3) {
            final int padding = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int width = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor + padding * 2);
            g2D.fillRect(this.xPixel - padding, 0, width, this.frame.levelPanel.getHeight());
        }
        else if (this.spaceRequirement == 22) {
            final int padding = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int width = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor + padding * 2);
            g2D.fillRect(this.xPixel - padding, this.yPixel, width, this.frame.levelPanel.getHeight() - this.yPixel);
        }
        else if (this.spaceRequirement == 24) {
            final int padding = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int yOffset = (int)Math.ceil(4.0 * TILE_SIZE * scaleFactor);
            final int height = (int)Math.ceil(this.button.placedImage.getIconHeight() * scaleFactor + 2.0 * (4.0 * TILE_SIZE) * scaleFactor);
            final int width2 = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor + padding * 2);
            g2D.fillRect(this.xPixel - padding, this.yPixel - yOffset, width2, height);
        }
        else if (this.spaceRequirement == 7) {
            final int flyWidth = (int)Math.ceil(6.0 * TILE_SIZE * scaleFactor);
            g2D.fillRect(this.xPixel - flyWidth + (int)Math.ceil((this.horizontalDrawOffset - 0.0 * TILE_SIZE) * scaleFactor), this.yPixel - (int)Math.ceil(TILE_SIZE * scaleFactor * 4.0), (int)Math.ceil((this.tilesWidth + 0) * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * scaleFactor * 9.0));
        }
        else if (this.spaceRequirement == 23) {
            final int width3 = (int)Math.ceil(getPulleyWidth(this.character) * TILE_SIZE * scaleFactor);
            final int blockSize = (int)Math.ceil((5.0 + TILE_SIZE) * scaleFactor);
            final int padding2 = (int)Math.ceil(0.0 * TILE_SIZE * scaleFactor);
            final int imageSize = (int)Math.ceil(this.button.placedImage.getIconWidth() * scaleFactor);
            final int height2 = (int)Math.ceil(Game.yTiles * TILE_SIZE * scaleFactor - this.yPixel);
            final int tile = (int)Math.ceil(TILE_SIZE * scaleFactor);
            g2D.fillRect(this.xPixel - padding2, this.yPixel + blockSize, width3 + padding2 * 2, height2);
            g2D.fillRect(this.xPixel + imageSize - width3 - padding2, this.yPixel + blockSize, width3 + padding2 * 2, height2);
            g2D.fillRect(this.xPixel + width3 / 2, this.yPixel - tile + (int)Math.round(5.0 * scaleFactor), imageSize - width3, tile);
        }
        else if (this.character == '!' || this.character == '\u2021') {
            g2D.fillRect((int)Math.round(this.xPixel - 2.0 * TILE_SIZE * scaleFactor), this.yPixel, (int)Math.round((2 + this.tilesWidth) * TILE_SIZE * scaleFactor), (int)Math.round(4.0 * TILE_SIZE * scaleFactor));
        }
        else if (this.character == '\u2663' || this.character == '\u00ee') {
            g2D.fillRect(Math.round(this.xPixel), this.yPixel, (int)Math.round((2 + this.tilesWidth) * TILE_SIZE * scaleFactor), (int)Math.round(4.0 * TILE_SIZE * scaleFactor));
        }
        else if (this.character == '~' || this.character == ';' || this.character == '\u0108') {
            g2D.fillRect(this.xPixel, (int)Math.round(this.yPixel - 4.0 * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * (4 + this.tilesHeight) * scaleFactor));
        }
        else if (this.character == '\u0110' || this.character == '¿') {
            g2D.fillRect(this.xPixel, this.yPixel, (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor), (int)Math.ceil(TILE_SIZE * (4 + this.tilesHeight) * scaleFactor));
        }
        else if (this.spaceRequirement == 18 || this.spaceRequirement == 26) {
            final int blockSize2 = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(this.xPixel, this.yPixel, blockSize2, blockSize2 * 2);
        }
        else if (this.spaceRequirement == 27) {
            final int blockSize2 = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(this.xPixel, this.yPixel, blockSize2, blockSize2 * 3);
        }
        else if (this.spaceRequirement == 19) {
            final int blockSize2 = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(this.xPixel, 0, blockSize2, this.yPixel + blockSize2);
        }
        else if (this.spaceRequirement == 28) {
            final int blockSize2 = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(this.xPixel, 0, blockSize2, this.yPixel + blockSize2 * 2);
        }
        else if (this.spaceRequirement == 17) {
            final int width3 = (int)Math.ceil(TILE_SIZE * this.tilesWidth * scaleFactor);
            g2D.fillRect(this.xPixel, this.yPixel, width3, (int)Math.ceil(TILE_SIZE * this.tilesHeight * scaleFactor));
            g2D.fillRect(this.xPixel + width3, this.yPixel, (int)Math.ceil(TILE_SIZE * scaleFactor * 2.0), (int)Math.ceil(TILE_SIZE * 6.0 * scaleFactor));
        }
        else if (this.spaceRequirement == 20) {
            g2D.fillRect(this.xPixel, 0, (int)Math.ceil(4.0 * TILE_SIZE * scaleFactor), (int)Math.ceil(TILE_SIZE * Game.yTiles * scaleFactor));
        }
        g2D.setComposite(oldComposite);
    }
    
    public void drawNumber(final Graphics2D g2D, final int value, final double xCenter, final double yCenter, final double scaleFactor, final double TILE_SIZE) {
        final char[] chars = String.valueOf(value).toCharArray();
        Image currentImage = null;
        final int blockSize = (int)Math.round(TILE_SIZE * scaleFactor);
        for (int i = 0; i < chars.length; ++i) {
            currentImage = this.frame.game.textures.symbols.get(chars[i]).getImage();
            if (this.frame.levelPanel.level.levelType == 4 || this.frame.levelPanel.level.levelType == 2 || this.frame.levelPanel.level.pipeColor == 2) {
                final Composite oldComposite = g2D.getComposite();
                g2D.setColor(Color.BLACK);
                g2D.setComposite(AlphaComposite.getInstance(3, 0.6f));
                g2D.fillRect((int)Math.round(xCenter - chars.length * TILE_SIZE * scaleFactor / 2.0 + i * TILE_SIZE * scaleFactor), (int)Math.round(yCenter - TILE_SIZE / 2.0 * scaleFactor), blockSize, blockSize);
                g2D.setComposite(oldComposite);
            }
            g2D.drawImage(currentImage, (int)Math.round(xCenter - chars.length * TILE_SIZE * scaleFactor / 2.0 + i * TILE_SIZE * scaleFactor), (int)Math.round(yCenter - TILE_SIZE / 2.0 * scaleFactor), blockSize, blockSize, null);
        }
    }
}
