// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import supermario.game.LevelLoader;
import java.io.CharArrayWriter;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import supermario.game.Game;
import java.util.LinkedList;

public class Level
{
	public static final int LEVEL_TYPE_OUTSIDE = 0;
    public static final int LEVEL_TYPE_UNDERGROUND = 1;
    public static final int LEVEL_TYPE_CASTLE = 2;
    public static final int LEVEL_TYPE_UNDER_WATER = 3;
    public static final int LEVEL_TYPE_OUTSIDE_NIGHT = 4;
    public static final int LEVEL_TYPE_COIN_ZONE_DAY = 5;
    public static final int LEVEL_TYPE_COIN_ZONE_NIGHT = 6;
    public static final int LEVEL_TYPE_GHOST_HOUSE = 7;
	
	
    private BuilderFrame frame;
    public LinkedList<Warp> incomingWarps;
    public LinkedList<Warp> outgoingWarps;
    public Item[][] items;
    public double leftMostX;
    public String name1;
    public String name2;
    public boolean hasLakitu;
    public boolean hasFlyingFish;
    public boolean hasBullets;
    public boolean timedLevel;
    public boolean blackAndWhite;
    public boolean autoScrolling;
    public int levelNumber;
    public int levelType;
    public int levelEndType;
    public int nextLevelNumber;
    public int cliffDestLevel;
    public int cliffDestID;
    public int levelTime;
    public int pipeColor;
    public int texturePack;
    public Item marioStartItem;
    public Item endItem;
    public static final int NEXT_LEVEL_NOTHING = -1;
    public static final int BLANK_LEVEL = -1;
    public static final int DEFAULT_MARIO_START_X_TILE = 3;
    public static final int DEFAULT_MARIO_START_Y_TILE;
    public static final int LEVEL_END_NOTHING = 0;
    public static final int LEVEL_END_FLAG_SM_CASTLE = 1;
    public static final int LEVEL_END_FLAG_LG_CASTLE = 2;
    public static final int LEVEL_END_BOWSER_1 = 3;
    public static final int LEVEL_END_BOWSER_2 = 4;
    public static final int LEVEL_END_BOWSER_3 = 5;
    public static final int LEVEL_END_BOWSER_4 = 6;
    public static final int LEVEL_END_BOWSER_5 = 7;
    public static final int LEVEL_END_BOWSER_6 = 8;
    public static final int LEVEL_END_BOWSER_7 = 9;
    public static final int LEVEL_END_BOWSER_8 = 10;
    public static final int MAX_LEVEL_LENGTH = 1000000;
    public static final int INSERT_AT_START = -1;
    public static final int INSERT_AT_END = -2;
    public static final int LEVEL_END_ITEM_WIDTH = 4;
    
    public Level(final BuilderFrame frame, final int copyIndex) {
        this.frame = frame;
        this.incomingWarps = new LinkedList<Warp>();
        this.outgoingWarps = new LinkedList<Warp>();
        if (copyIndex == -1) {
            this.items = new Item[Game.yTiles][Game.xTiles];
            this.marioStartItem = frame.miscPanel.mario.item.copy();
            this.marioStartItem.xTile = 3;
            this.marioStartItem.yTile = Level.DEFAULT_MARIO_START_Y_TILE;
            this.marioStartItem.inserted = true;
            this.pipeColor = 0;
            this.texturePack = 0;
        }
        else {
            frame.levelPanel.level = this;
            final Level copy = frame.levelPanel.levels[copyIndex];
            if (copy.levelEndType != 0) {
                this.items = new Item[Game.yTiles][copy.items[0].length - 4];
            }
            else {
                this.items = new Item[Game.yTiles][copy.items[0].length];
            }
            for (int i = 0; i < Game.yTiles; ++i) {
                for (int j = 0; j < this.items[0].length; ++j) {
                    final Item tempItem = copy.items[i][j];
                    if (tempItem != null && tempItem.xTile == j && tempItem.yTile == i) {
                        final Item newItem = tempItem.copy();
                        if (tempItem.isWarpable()) {
                            tempItem.warp = newItem.warp;
                            tempItem.warp.item = tempItem;
                            newItem.warp = new Warp(false, false, -1, -1, -1, -1, newItem);
                            frame.levelPanel.assignMandatoryWarpData(newItem);
                        }
                        newItem.insertInLevel(j, i);
                        if (newItem.character == 'k') {
                            this.marioStartItem = newItem;
                        }
                    }
                }
            }
            this.pipeColor = copy.pipeColor;
            this.hasLakitu = copy.hasLakitu;
            this.hasFlyingFish = copy.hasFlyingFish;
            this.hasBullets = copy.hasBullets;
            this.blackAndWhite = copy.blackAndWhite;
            this.levelType = copy.levelType;
            this.timedLevel = copy.timedLevel;
            this.levelTime = copy.levelTime;
            this.autoScrolling = copy.autoScrolling;
            this.texturePack = copy.texturePack;
        }
        this.name1 = "";
        this.name2 = "";
        this.nextLevelNumber = -1;
        this.cliffDestLevel = -1;
    }
    
    public String getTitle() {
        String number;
        for (number = String.valueOf(this.levelNumber + 1); number.length() < 3; number = "0" + number) {}
        return number + " " + this.name1 + " " + this.name2;
    }
    
    public Warp getLevelWarp(final boolean cliffDeath) {
        if (cliffDeath) {
            return new Warp(false, true, this.levelNumber, 0, this.cliffDestLevel, this.cliffDestID, this.frame.miscPanel.mario.item);
        }
        return new Warp(false, true, this.levelNumber, 0, this.nextLevelNumber, 0, this.frame.miscPanel.mario.item);
    }
    
    public int getLevelEndIndex() {
        if (this.levelEndType == 0) {
            return 0;
        }
        if (this.levelType == 0 || this.levelType == 4) {
            if (this.levelEndType == 1) {
                return 1;
            }
            if (this.levelEndType == 2) {
                return 2;
            }
        }
        else if (this.levelType == 2) {
            if (this.levelEndType == 3) {
                return 1;
            }
            if (this.levelEndType == 4) {
                return 2;
            }
            if (this.levelEndType == 5) {
                return 3;
            }
            if (this.levelEndType == 6) {
                return 4;
            }
            if (this.levelEndType == 7) {
                return 5;
            }
            if (this.levelEndType == 8) {
                return 6;
            }
            if (this.levelEndType == 9) {
                return 7;
            }
            if (this.levelEndType == 10) {
                return 8;
            }
        }
        return 0;
    }
    
    public int getWarpsIndex(final int warpID) {
        int index = 0;
        final Iterator<Warp> warpsIter = this.incomingWarps.iterator();
        while (warpsIter.hasNext()) {
            if (warpsIter.next().sourceWarpID == warpID) {
                return index;
            }
            ++index;
        }
        return -1;
    }
    
    public int getNextAvailableWarpID() {
        if (this.incomingWarps.size() == 0) {
            return 1;
        }
        int expectedID = 1;
        for (final Warp warp : this.incomingWarps) {
            if (warp.sourceWarpID > expectedID) {
                return expectedID;
            }
            ++expectedID;
        }
        return expectedID;
    }
    
    public boolean insertIncomingWarp(final Warp warp) {
        if (this.incomingWarps.size() >= 999) {
            return false;
        }
        if (this.incomingWarps.size() == 0) {
            this.incomingWarps.add(warp);
        }
        else {
            int index = 0;
            final Iterator<Warp> warpsIter = this.incomingWarps.iterator();
            while (warpsIter.hasNext()) {
                if (warpsIter.next().sourceWarpID > warp.sourceWarpID) {
                    this.incomingWarps.add(index, warp);
                    return true;
                }
                ++index;
            }
            this.incomingWarps.addLast(warp);
        }
        return true;
    }
    
    public void reassignWarpSourceLevelNumbers() {
        for (final Warp tempWarp : this.incomingWarps) {
            tempWarp.sourceLevelNumber = this.levelNumber;
        }
        for (final Warp tempWarp2 : this.outgoingWarps) {
            tempWarp2.sourceLevelNumber = this.levelNumber;
        }
    }
    
    public String[] getIncomingWarpIDList() {
        if (this.incomingWarps.size() == 0) {
            return new String[0];
        }
        final String[] names = new String[this.incomingWarps.size()];
        int index = 0;
        for (final Warp tempWarp : this.incomingWarps) {
            final String name = tempWarp.sourceWarpID + " (x:" + tempWarp.item.xTile + " y:" + tempWarp.item.yTile + ")";
            names[index++] = name;
        }
        return names;
    }
    
    public void insertAtColumn(final int columnNumber) {
        if (this.items[0].length >= 1000000) {
            return;
        }
        final Item[][] newItems = new Item[this.items.length][this.items[0].length + 1];
        if (columnNumber == -2 && this.levelEndType != 0) {
            final Item item2 = this.items[0][this.items[0].length - 4];
            ++item2.xTile;
            for (int i = 0; i < newItems.length; ++i) {
                for (int j = 0; j < this.items[0].length - 4; ++j) {
                    newItems[i][j] = this.items[i][j];
                }
            }
            for (int i = 0; i < newItems.length; ++i) {
                for (int j = this.items[0].length - 4 + 1; j <= this.items[0].length; ++j) {
                    newItems[i][j] = this.items[i][this.items[0].length - 4];
                }
            }
        }
        else if (columnNumber >= this.items[0].length || columnNumber == -2) {
            for (int i = 0; i < newItems.length; ++i) {
                for (int j = 0; j < this.items[0].length; ++j) {
                    newItems[i][j] = this.items[i][j];
                }
            }
        }
        else if (columnNumber == -1) {
            final LinkedList<Item> itemsToShift = new LinkedList<Item>();
            for (int k = 0; k < newItems.length; ++k) {
                for (int l = 0; l < this.items[0].length; ++l) {
                    newItems[k][l + 1] = this.items[k][l];
                    if (this.items[k][l] != null && this.items[k][l].yTile == k && this.items[k][l].xTile == l) {
                        itemsToShift.add(this.items[k][l]);
                    }
                }
            }
            while (itemsToShift.size() > 0) {
                final Item item3 = itemsToShift.remove();
                ++item3.xTile;
            }
        }
        else {
            for (int i = 0; i < newItems.length; ++i) {
                for (int j = 0; j < columnNumber; ++j) {
                    newItems[i][j] = this.items[i][j];
                }
            }
            final LinkedList<Item> itemsToShift = new LinkedList<Item>();
            for (int k = 0; k < newItems.length; ++k) {
                for (int l = columnNumber; l < this.items[0].length; ++l) {
                    newItems[k][l + 1] = this.items[k][l];
                    if (this.items[k][l] != null && this.items[k][l].yTile == k && this.items[k][l].xTile == l) {
                        itemsToShift.add(this.items[k][l]);
                    }
                }
            }
            while (itemsToShift.size() > 0) {
                final Item item4 = itemsToShift.remove();
                ++item4.xTile;
            }
        }
        this.items = newItems;
        for (int i = 0; i < this.items.length; ++i) {
            final Item item = this.items[i][this.items[0].length - 2];
            if (item != null && ItemFitting.itemAllowsPartialInsertion(item)) {
                item.insertInLevel(item.xTile, item.yTile);
            }
        }
        this.frame.updateColumnStatus();
    }
    
    public void removeAtColumn(int columnNumber) {
        final Item[][] newItems = new Item[this.items.length][this.items[0].length - 1];
        if (columnNumber == -2) {
            if (this.levelEndType == 0) {
                columnNumber = this.items[0].length - 1;
            }
            else {
                columnNumber = this.items[0].length - 1 - 4;
            }
        }
        if (columnNumber > newItems[0].length) {
            columnNumber = newItems[0].length;
        }
        for (int i = 0; i < newItems.length; ++i) {
            for (int j = 0; j < columnNumber; ++j) {
                newItems[i][j] = this.items[i][j];
            }
        }
        final LinkedList<Item> itemsToShift = new LinkedList<Item>();
        for (int k = 0; k < newItems.length; ++k) {
            for (int l = columnNumber + 1; l < this.items[0].length; ++l) {
                newItems[k][l - 1] = this.items[k][l];
                if (this.items[k][l] != null && this.items[k][l].yTile == k && this.items[k][l].xTile == l) {
                    itemsToShift.add(this.items[k][l]);
                }
            }
        }
        while (itemsToShift.size() > 0) {
            final Item item = itemsToShift.remove();
            --item.xTile;
        }
        this.items = newItems;
        this.frame.updateColumnStatus();
    }
    
    public int getLevelEndTypeIndexFromEndType(final int endType) {
        if (endType == 0 || this.levelType == 5 || this.levelType == 6 || this.levelType == 1 || this.levelType == 3) {
            return 0;
        }
        if (this.levelType == 0 || this.levelType == 4) {
            if (endType == 1) {
                return 1;
            }
            if (endType == 2) {
                return 2;
            }
        }
        else if (this.levelType == 2) {
            return endType - 2;
        }
        return 0;
    }
    
    public void changeLevelEndType(final int endTypeComboIndex, final boolean expansionAllowed) {
        int newEndType = 0;
        if (this.levelType == 0 || this.levelType == 4) {
            if (endTypeComboIndex == 0) {
                newEndType = 0;
            }
            else if (endTypeComboIndex == 1) {
                newEndType = 1;
            }
            else if (endTypeComboIndex == 2) {
                newEndType = 2;
            }
        }
        else if (this.levelType == 1 || this.levelType == 3) {
            newEndType = 0;
        }
        else if (this.levelType == 2) {
            if (endTypeComboIndex == 0) {
                newEndType = 0;
            }
            else if (endTypeComboIndex == 1) {
                newEndType = 3;
            }
            else if (endTypeComboIndex == 2) {
                newEndType = 4;
            }
            else if (endTypeComboIndex == 3) {
                newEndType = 5;
            }
            else if (endTypeComboIndex == 4) {
                newEndType = 6;
            }
            else if (endTypeComboIndex == 5) {
                newEndType = 7;
            }
            else if (endTypeComboIndex == 6) {
                newEndType = 8;
            }
            else if (endTypeComboIndex == 7) {
                newEndType = 9;
            }
            else if (endTypeComboIndex == 8) {
                newEndType = 10;
            }
        }
        if (expansionAllowed) {
            if (this.levelEndType == 0 && newEndType != 0) {
                for (int i = 0; i < this.items.length; ++i) {
                    final Item item = this.items[i][this.items[0].length - 1];
                    if (item != null && ItemFitting.itemAllowsPartialInsertion(item) && item.xTile + item.tilesWidth - 1 >= this.items[0].length) {
                        item.removeFromLevel();
                    }
                }
                final Item[][] newItems = new Item[this.items.length][this.items[0].length + 4];
                for (int j = 0; j < newItems.length; ++j) {
                    for (int k = 0; k < this.items[0].length; ++k) {
                        newItems[j][k] = this.items[j][k];
                    }
                }
                this.items = newItems;
                this.frame.levelPanel.placeLevel();
                this.frame.levelPanel.updateSlider();
            }
            else if (this.levelEndType != 0 && newEndType == 0) {
                final Item[][] newItems = new Item[this.items.length][this.items[0].length - 4];
                for (int j = 0; j < newItems.length; ++j) {
                    for (int k = 0; k < newItems[0].length; ++k) {
                        newItems[j][k] = this.items[j][k];
                    }
                }
                this.items = newItems;
                this.frame.levelPanel.placeLevel();
                this.frame.levelPanel.updateSlider();
            }
        }
        this.levelEndType = newEndType;
        this.endItem = null;
        if (newEndType == 1) {
            (this.endItem = this.frame.miscPanel.levelEndingSmFlag.item.copy()).insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 2) {
            (this.endItem = this.frame.miscPanel.levelEndingLgFlag.item.copy()).insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 3) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u2666';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 4) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '¼';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 5) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u00d7';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 6) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u00d8';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 7) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u00de';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 8) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u00df';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 9) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u00f0';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        else if (newEndType == 10) {
            this.endItem = this.frame.miscPanel.levelEndingBowserBattle.item.copy();
            this.endItem.character = '\u00ff';
            this.endItem.insertInLevel(this.items[0].length - 4, 0);
        }
        this.frame.levelPanel.repaint();
    }
    
    public void matchItemsToLevelType() {
        for (int i = 0; i < this.items.length; ++i) {
            for (int j = 0; j < this.items[0].length; ++j) {
                if (this.items[i][j] != null && this.items[i][j].yTile == i && this.items[i][j].xTile == j) {
                    this.matchItem(this.items[i][j]);
                }
            }
        }
    }
    
    private void matchItem(final Item item) {
        Item newItem = null;
        final char c = item.character;
        final int day = 0;
        final int night = 4;
        final int castle = 2;
        final int under = 1;
        if ((c == '$' || c == '\u02e6') && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkGoomba.item.copy();
        }
        else if ((c == '%' || c == '\u02e6') && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.enemiesPanel.lightGoomba.item.copy();
        }
        else if ((c == '$' || c == '%') && this.levelType == castle) {
            newItem = this.frame.enemiesPanel.grayGoomba.item.copy();
        }
        else if (c == '^' && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkKoopaNormal.item.copy();
        }
        else if (c == '&' && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkKoopaBouncing.item.copy();
        }
        else if (c == '*' && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkKoopaFlyingV.item.copy();
        }
        else if (c == '¬' && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkKoopaFlyingH.item.copy();
        }
        else if (c == '(' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.enemiesPanel.darkKoopaNormal.item.copy();
        }
        else if (c == ')' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.enemiesPanel.lightKoopaBouncing.item.copy();
        }
        else if (c == '-' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.enemiesPanel.lightKoopaFlyingV.item.copy();
        }
        else if (c == '±' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.enemiesPanel.lightKoopaFlyingH.item.copy();
        }
        else if ((c == ':' || c == '\u02e7') && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkBeetle.item.copy();
        }
        else if ((c == '\u00f4' || c == '\u02e7') && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.enemiesPanel.lightBeetle.item.copy();
        }
        else if ((c == ':' || c == '\u00f4') && this.levelType == castle) {
            newItem = this.frame.enemiesPanel.grayBeetle.item.copy();
        }
        else if (c == '}' && this.levelType == under) {
            newItem = this.frame.enemiesPanel.darkHammerBro.item.copy();
        }
        else if (c == '\u00f6' && (this.levelType == day || this.levelType == night || this.levelType == castle)) {
            newItem = this.frame.enemiesPanel.lightHammerBro.item.copy();
        }
        /*
        else if (c == 'A' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickNothing.item.copy();
        }
        else if (c == 'A' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickNothing.item.copy();
        }
        else if (c == 'G' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickBeanstalk.item.copy();
        }
        else if (c == 'G' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickBeanstalk.item.copy();
        }
        else if (c == 'C' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickCoins.item.copy();
        }
        else if (c == 'C' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickCoins.item.copy();
        }
        else if (c == 'D' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickGrow.item.copy();
        }
        else if (c == 'D' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickGrow.item.copy();
        }
        else if (c == 'E' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickLife.item.copy();
        }
        else if (c == 'E' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickLife.item.copy();
        }
        else if (c == 'B' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickCoin.item.copy();
        }
        else if (c == 'B' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickCoin.item.copy();
        }
        else if (c == 'F' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickStar.item.copy();
        }
        else if (c == 'F' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickStar.item.copy();
        }
        else if (c == 'H' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickNothing.item.copy();
        }
        */
        else if (c == 'H' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickNothing.item.copy();
        }
        /*
        else if (c == 'N' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickBeanstalk.item.copy();
        }
        */
        else if (c == 'N' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickBeanstalk.item.copy();
        }
        /*
        else if (c == 'J' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickCoins.item.copy();
        }
        */
        else if (c == 'J' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickCoins.item.copy();
        }
        /*
        else if (c == 'K' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickGrow.item.copy();
        }
        */
        else if (c == 'K' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickGrow.item.copy();
        }
        /*
        else if (c == 'L' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickLife.item.copy();
        }
        */
        else if (c == 'L' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickLife.item.copy();
        }
        /*
        else if (c == 'I' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickCoin.item.copy();
        }
        */
        else if (c == 'I' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickCoins.item.copy();
        }
        /*
        else if (c == 'M' && this.levelType == castle) {
            newItem = this.frame.blocksPanel.stoneBrickStar.item.copy();
        }
        */
        else if (c == 'M' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickStar.item.copy();
        }
        else if (c == '\u2022' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickNothing.item.copy();
        }
        /*
        else if (c == '\u2022' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickNothing.item.copy();
        }
        */
        else if (c == '\u25c4' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickBeanstalk.item.copy();
        }
        /*
        else if (c == '\u25c4' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickBeanstalk.item.copy();
        }
        */
        else if (c == '\u2640' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickCoins.item.copy();
        }
        /*
        else if (c == '\u2640' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickCoins.item.copy();
        }
        */
        else if (c == '\u266b' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickGrow.item.copy();
        }
        /*
        else if (c == '\u266b' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickGrow.item.copy();
        }
        */
        else if (c == '\u263c' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickLife.item.copy();
        }
        /*
        else if (c == '\u263c' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickLife.item.copy();
        }
        */
        else if (c == '\u25d8' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickCoin.item.copy();
        }
        /*
        else if (c == '\u25d8' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickCoin.item.copy();
        }*/
        else if (c == '\u25ba' && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.blocksPanel.lightBrickStar.item.copy();
        }
        /*
        else if (c == '\u25ba' && this.levelType == under) {
            newItem = this.frame.blocksPanel.darkBrickStar.item.copy();
        }
        else if ((c == 'q' || c == '\u045e') && this.levelType == under) {
            newItem = this.frame.solidsPanel.darkBlock.item.copy();
        }
        */
        else if ((c == 'r' || c == '\u045e') && (this.levelType == day || this.levelType == night)) {
            newItem = this.frame.solidsPanel.block.item.copy();
        }
        /*
        else if ((c == 'q' || c == 'r') && this.levelType == castle) {
            newItem = this.frame.solidsPanel.stoneBlock.item.copy();
        }
        */
        if (newItem != null) {
            newItem.flip = item.flip;
            newItem.coinCount = item.coinCount;
            newItem.oscOffset = item.oscOffset;
            newItem.checkpointType = item.checkpointType;
            newItem.fireballColor = item.fireballColor;
            newItem.speedBoost = item.speedBoost;
            item.removeFromLevel();
            newItem.insertInLevel(item.xTile, item.yTile);
            this.frame.levelPanel.repaint();
        }
    }
    
    public BufferedImage getLevelImage() throws Exception {
        final int width = this.items[0].length * 8;
        final int height = this.items.length * 8;
        final BufferedImage image = new BufferedImage(width, height, 5);
        final Graphics2D g2D = (Graphics2D)image.getGraphics();
        if (this.levelType == LEVEL_TYPE_OUTSIDE || 
        		this.levelType == LEVEL_TYPE_COIN_ZONE_DAY) {
            g2D.setColor(this.frame.game.textures.skyBlue);
            g2D.fillRect(0, 0, width, height);
        }
        else if (this.levelType == LEVEL_TYPE_UNDERGROUND || 
        		this.levelType == LEVEL_TYPE_OUTSIDE_NIGHT || 
        		this.levelType == LEVEL_TYPE_CASTLE || 
        		this.levelType == LEVEL_TYPE_COIN_ZONE_NIGHT || 
        		this.levelType == LEVEL_TYPE_GHOST_HOUSE) {
            g2D.setColor(this.frame.game.textures.black);
            g2D.fillRect(0, 0, width, height);
        }
        else if (this.levelType == LEVEL_TYPE_UNDER_WATER) {
            g2D.setColor(this.frame.game.textures.skyBlue);
            g2D.fillRect(0, 0, width, (int)Math.ceil(32.0));
            g2D.setColor(this.frame.game.textures.waterBlue);
            g2D.fillRect(0, Math.round(32.0f), width, (int)Math.ceil(height - 32));
            final double offset = this.leftMostX % 8.0;
            for (double i = -offset; i < (width / 8 + 1) * 8; i += 8.0) {
                g2D.drawImage(this.frame.game.textures.waves.getImage(), (int)Math.round(i), Math.round(24.0f), (int)Math.ceil(this.frame.game.textures.waves.getIconWidth()), (int)Math.ceil(this.frame.game.textures.waves.getIconHeight()), null);
            }
        }
        for (int j = 0; j < this.items.length; ++j) {
            for (int k = 0; k < this.items[0].length; ++k) {
                if (this.items[j][k] != null && this.items[j][k].yTile == j && this.items[j][k].xTile == k && (this.items[j][k].spaceRequirement == 0 || this.items[j][k].spaceRequirement == 1)) {
                    this.items[j][k].drawInLevel(g2D, 0.0, 1.0, 8.0, false);
                }
            }
        }
        for (int j = 0; j < this.items.length; ++j) {
            for (int k = 0; k < this.items[0].length; ++k) {
                if (this.items[j][k] != null && this.items[j][k].yTile == j && this.items[j][k].xTile == k && this.items[j][k].spaceRequirement != 0 && this.items[j][k].spaceRequirement != 1) {
                    this.items[j][k].drawInLevel(g2D, this.leftMostX, 1.0, 8.0, false);
                }
            }
        }
        return image;
    }
    
    public byte[] getLevelBytes() throws Exception {
        return String.valueOf(this.getLevelChars()).getBytes(Game.ENCODING);
    }
    
    public char[] getLevelChars() throws Exception {
        final String lineBreak = "\r\n";
        char[][] charData = this.getCharData();
        charData = this.expandLevelSizeForEnding(charData);
        this.assignWarpsToCharData(charData);
        final CharArrayWriter cWriter = new CharArrayWriter();
        for (int i = 0; i < Game.yTiles; ++i) {
            cWriter.write(charData[i]);
            cWriter.write(lineBreak);
        }
        cWriter.write(String.valueOf(this.levelType));
        final String present = "1";
        final String missing = "x";
        if (this.hasLakitu) {
            cWriter.write(present);
        }
        else {
            cWriter.write(missing);
        }
        if (this.hasFlyingFish) {
            cWriter.write(present);
        }
        else {
            cWriter.write(missing);
        }
        if (this.hasBullets) {
            cWriter.write(present);
        }
        else {
            cWriter.write(missing);
        }
        if (this.blackAndWhite) {
            cWriter.write(present);
        }
        else {
            cWriter.write(missing);
        }
        cWriter.write(lineBreak);
        if (this.cliffDestLevel == -1) {
            cWriter.write("xxxxxx");
        }
        else {
            cWriter.write(this.padStringToSize(String.valueOf(this.cliffDestLevel), 3, '0', true));
            cWriter.write(this.padStringToSize(String.valueOf(this.cliffDestID), 3, '0', true));
        }
        cWriter.write(lineBreak);
        cWriter.write(this.padStringToSize(this.name1, 8, ' ', false));
        cWriter.write(lineBreak);
        cWriter.write(this.padStringToSize(this.name2, 8, ' ', false));
        cWriter.write(lineBreak);
        if (this.timedLevel) {
            cWriter.write("1" + lineBreak);
            cWriter.write(this.padStringToSize(String.valueOf(this.levelTime), 4, '0', true) + lineBreak);
        }
        else {
            cWriter.write("0" + lineBreak);
            cWriter.write("xxxx" + lineBreak);
        }
        cWriter.write(String.valueOf(this.pipeColor));
        cWriter.write(String.valueOf((int)(this.autoScrolling ? 1 : 0)));
        cWriter.write(String.valueOf(this.texturePack));
        System.out.println("Saved level with texture "+String.valueOf(this.texturePack));
        return cWriter.toCharArray();
    }
    
    private char[][] expandLevelSizeForEnding(final char[][] charData) throws Exception {
        if (this.levelEndType != 0) {
            int endingSize = 0;
            if (this.levelEndType >= 3 && this.levelEndType <= 10) {
                endingSize = 68;
            }
            else if (this.levelEndType == 1 || this.levelEndType == 2) {
                endingSize = 28;
            }
            final char[][] newCharData = new char[Game.yTiles][this.items[0].length - 4 + endingSize];
            for (int i = 0; i < charData.length; ++i) {
                for (int j = 0; j < charData[0].length; ++j) {
                    newCharData[i][j] = charData[i][j];
                }
            }
            for (int i = 0; i < newCharData.length; ++i) {
                for (int j = charData[0].length; j < newCharData[0].length; ++j) {
                    newCharData[i][j] = 'x';
                }
            }
            return newCharData;
        }
        return charData;
    }
    
    private char[][] getCharData() throws Exception {
        final char[][] charData = new char[Game.yTiles][this.items[0].length];
        for (int i = 0; i < Game.yTiles; ++i) {
            for (int j = 0; j < this.items[0].length; ++j) {
                if (this.items[i][j] == null) {
                    charData[i][j] = '0';
                }
                else if (this.items[i][j].yTile == i && this.items[i][j].xTile == j) {
                    charData[i][j] = this.items[i][j].character;
                    if (this.addAnySupplementalInfo(charData, this.items[i][j], j, i)) {
                        ++j;
                    }
                }
                else if (charData[i][j] == '\0') {
                    charData[i][j] = 'x';
                }
            }
        }
        return charData;
    }
    
    private boolean addAnySupplementalInfo(final char[][] charData, final Item item, final int xTile, final int yTile) throws Exception {
        if (item.flippable) {
            if (item.flip) {
                charData[yTile][xTile + 1] = 'x';
            }
            else {
                charData[yTile][xTile + 1] = '0';
            }
            return true;
        }
        if (item.shiftable) {
            if (item.shifted) {
                charData[yTile][xTile + 1] = '0';
            }
            else {
                charData[yTile][xTile + 1] = 'x';
            }
            return true;
        }
        if (item.character == ',' || item.character == '<') {
            if (!item.flip && !item.speedBoost) {
                charData[yTile][xTile + 1] = '2';
            }
            else if (!item.flip && item.speedBoost) {
                charData[yTile][xTile + 1] = '3';
            }
            else if (item.flip && !item.speedBoost) {
                charData[yTile][xTile + 1] = '0';
            }
            else {
                charData[yTile][xTile + 1] = '1';
            }
            return true;
        }
        if (item.platformRepeatable) {
            if (item.singlePlatform) {
                charData[yTile][xTile + 1] = '0';
            }
            else {
                charData[yTile][xTile + 1] = 'x';
            }
            return true;
        }
        if (LevelLoader.isPowerupBlock(item.character)) {
            if (item.poison) {
                charData[yTile][xTile + 1] = '0';
            }
            else {
                charData[yTile][xTile + 1] = 'x';
            }
            return true;
        }
        if (item.redPiranha) {
            if (LevelLoader.isSideOpeningPipe(item.character)) {
                charData[yTile][xTile + 1] = '0';
                return true;
            }
            charData[yTile + 1][xTile] = '0';
            return false;
        }
        else {
            if (item.superSpring) {
                charData[yTile][xTile + 1] = '0';
                return true;
            }
            if (LevelLoader.multiCoinBlock(item.character)) {
                charData[yTile][xTile + 1] = String.valueOf(item.coinCount).toCharArray()[0];
                return true;
            }
            if (item.character == '.') {
                charData[yTile][xTile + 1] = String.valueOf(item.fireballColor).toCharArray()[0];
                return true;
            }
            if (LevelLoader.isOscSprite(item.character)) {
                charData[yTile][xTile + 1] = String.valueOf(item.oscOffset).toCharArray()[0];
                return true;
            }
            if (item.character == '\u0292') {
                charData[yTile][xTile + 1] = String.valueOf(item.checkpointType).toCharArray()[0];
                return true;
            }
            return false;
        }
    }
    
    private void assignWarpsToCharData(final char[][] charData) throws Exception {
        if (this.nextLevelNumber >= 0) {
            final char[] destLevelChars = this.padStringToSize(String.valueOf(this.nextLevelNumber), 3, '0', true).toCharArray();
            charData[1][this.items[0].length - 3] = destLevelChars[0];
            charData[1][this.items[0].length - 2] = destLevelChars[1];
            charData[1][this.items[0].length - 1] = destLevelChars[2];
            charData[2][this.items[0].length - 3] = '0';
            charData[2][this.items[0].length - 2] = '0';
            charData[2][this.items[0].length - 1] = '0';
        }
        final Iterator<Warp> incomingWarpsIter = this.incomingWarps.iterator();
        while (incomingWarpsIter.hasNext()) {
            this.assignWarpID(charData, incomingWarpsIter.next());
        }
        for (final Warp tempWarp : this.outgoingWarps) {
            if (tempWarp.item.character == '\u0108') {
                tempWarp.sourceWarpID = tempWarp.item.displayWarpNumber;
                charData[tempWarp.item.yTile + 1][tempWarp.item.xTile] = String.valueOf(tempWarp.item.warpPipeColor).toCharArray()[0];
            }
            this.assignWarpDestination(charData, tempWarp);
        }
    }
    
    private void assignWarpID(final char[][] charData, final Warp warp) throws Exception {
        final char[] warpIDChars = this.padStringToSize(String.valueOf(warp.sourceWarpID), 3, '0', true).toCharArray();
        final int xTile = warp.item.xTile;
        final int yTile = warp.item.yTile;
        final char warpChar = warp.item.character;
        if (warpChar == ';' || warpChar == '~' || warpChar == '¿' || warpChar == '\u0110') {
            charData[yTile][xTile + 1] = warpIDChars[0];
            charData[yTile][xTile + 2] = warpIDChars[1];
            charData[yTile][xTile + 3] = warpIDChars[2];
        }
        else if (warpChar == '\u03e4') {
            charData[yTile + 1][xTile] = warpIDChars[0];
            charData[yTile + 2][xTile] = warpIDChars[1];
            charData[yTile + 3][xTile] = warpIDChars[2];
            final char[] array = charData[yTile + 1];
            final int n = xTile + 1;
            final char[] array2 = charData[yTile + 2];
            final int n2 = xTile + 1;
            final char[] array3 = charData[yTile + 3];
            final int n3 = xTile + 1;
            final char c = 'x';
            array3[n3] = c;
            array[n] = (array2[n2] = c);
        }
        else if (warpChar == '½') {
            charData[yTile + 1][xTile] = warpIDChars[0];
            charData[yTile + 2][xTile] = warpIDChars[1];
            charData[yTile + 3][xTile] = warpIDChars[2];
            final char[] array4 = charData[yTile + 1];
            final int n4 = xTile + 1;
            final char[] array5 = charData[yTile + 2];
            final int n5 = xTile + 1;
            final char[] array6 = charData[yTile + 3];
            final int n6 = xTile + 1;
            final char c2 = 'x';
            array6[n6] = c2;
            array4[n4] = (array5[n5] = c2);
        }
        else if (LevelLoader.isSideOpeningPipe(warpChar)) {
            charData[yTile + 1][xTile] = warpIDChars[0];
            charData[yTile + 1][xTile + 1] = warpIDChars[1];
            charData[yTile + 1][xTile + 2] = warpIDChars[2];
        }
    }
    
    private void assignWarpDestination(final char[][] charData, final Warp warp) throws Exception {
        final char[] destLevelChars = this.padStringToSize(String.valueOf(warp.destLevelNumber), 3, '0', true).toCharArray();
        final char[] destIDChars = this.padStringToSize(String.valueOf(warp.destWarpID), 3, '0', true).toCharArray();
        final int xTile = warp.item.xTile;
        final int yTile = warp.item.yTile;
        final char warpChar = warp.item.character;
        if (LevelLoader.isBeanstalkBlock(warpChar)) {
            charData[yTile - 3][xTile] = destLevelChars[0];
            charData[yTile - 2][xTile] = destLevelChars[1];
            charData[yTile - 1][xTile] = destLevelChars[2];
            charData[yTile - 3][xTile + 1] = destIDChars[0];
            charData[yTile - 2][xTile + 1] = destIDChars[1];
            charData[yTile - 1][xTile + 1] = destIDChars[2];
        }
        else if (warpChar == ';' || warpChar == '~' || warpChar == '\u0108' || warpChar == '\u0110' || warpChar == '¿') {
            if (warpChar == '\u0108') {
                char[] displayIDChars;
                if (warp.item.displayWarpNumber == -1) {
                    displayIDChars = new char[] { 'x', 'x', 'x' };
                }
                else {
                    displayIDChars = this.padStringToSize(String.valueOf(warp.item.displayWarpNumber), 3, '0', true).toCharArray();
                }
                charData[yTile][xTile + 1] = displayIDChars[0];
                charData[yTile][xTile + 2] = displayIDChars[1];
                charData[yTile][xTile + 3] = displayIDChars[2];
            }
            charData[yTile + 1][xTile + 1] = destLevelChars[0];
            charData[yTile + 1][xTile + 2] = destLevelChars[1];
            charData[yTile + 1][xTile + 3] = destLevelChars[2];
            charData[yTile + 2][xTile + 1] = destIDChars[0];
            charData[yTile + 2][xTile + 2] = destIDChars[1];
            charData[yTile + 2][xTile + 3] = destIDChars[2];
        }
        else if (LevelLoader.isSideOpeningPipe(warpChar)) {
            charData[yTile + 2][xTile] = destLevelChars[0];
            charData[yTile + 2][xTile + 1] = destLevelChars[1];
            charData[yTile + 2][xTile + 2] = destLevelChars[2];
            charData[yTile + 3][xTile] = destIDChars[0];
            charData[yTile + 3][xTile + 1] = destIDChars[1];
            charData[yTile + 3][xTile + 2] = destIDChars[2];
        }
    }
    
    private String padStringToSize(String string, final int size, final char padChar, final boolean padAtStart) {
        while (string.length() < size) {
            if (padAtStart) {
                string = padChar + string;
            }
            else {
                string += padChar;
            }
        }
        return string;
    }
    
    public void createLevel(final char[] levelChars, final int levelNumber) throws Exception {
        this.levelNumber = levelNumber;
        final int lineLength = this.getLineLength(levelChars);
        this.setLevelType(levelChars, lineLength);
        this.setLevelNames(levelChars, lineLength);
        this.hasBullets = this.hasBulletThrower(levelChars, lineLength);
        this.hasFlyingFish = this.hasFlyingFish(levelChars, lineLength);
        this.hasLakitu = this.hasSpinyThrower(levelChars, lineLength);
        this.blackAndWhite = this.isBlackAndWhite(levelChars, lineLength);
        if (this.isTimedLevel(levelChars, lineLength)) {
            this.timedLevel = true;
            this.levelTime = this.getLevelTimeLimit(levelChars, lineLength);
        }
        else {
            this.timedLevel = false;
        }
        this.pipeColor = this.getPipeColor(levelChars, lineLength);
        this.autoScrolling = this.hasAutoScrolling(levelChars, lineLength);
        this.texturePack = this.getTexturePack(levelChars, lineLength);
        this.cliffDestLevel = this.getCliffDestLevel(levelChars, lineLength);
        if (this.cliffDestLevel != -1) {
            this.cliffDestID = this.getCliffDestID(levelChars, lineLength);
        }
        final char[][] levelChars2D = this.getLevelChars2D(levelChars, lineLength);
        this.createItems(levelChars2D, levelNumber);
    }
    
    private int getLineLength(final char[] chars) throws Exception {
        int lineLength = -1;
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '\r') {
                lineLength = i;
                break;
            }
        }
        if (lineLength == -1 || lineLength < Game.xTiles) {
            throw new RuntimeException("Line length is invalid: " + lineLength);
        }
        return lineLength;
    }
    
    private char[][] getLevelChars2D(final char[] levelChars, final int lineLength) throws Exception {
        final char[][] levelChars2D = new char[Game.yTiles][lineLength];
        int x = 0;
        for (int i = 0; i < Game.yTiles; ++i) {
            for (int j = 0; j < lineLength; ++j) {
                levelChars2D[i][j] = levelChars[x++];
            }
            x += 2;
        }
        return levelChars2D;
    }
    
    private void createItems(final char[][] levelChars2D, final int levelNumber) throws Exception {
        this.levelEndType = 0;
        this.items = new Item[Game.yTiles][levelChars2D[0].length];
        for (int i = 0; i < levelChars2D.length; ++i) {
            for (int j = 0; j < levelChars2D[0].length; ++j) {
                char followingChar = '0';
                if (j + 1 < levelChars2D[0].length) {
                    followingChar = levelChars2D[i][j + 1];
                    if (levelChars2D[i][j] == '\u0108' || LevelLoader.isVerticalOpeningPipe(levelChars2D[i][j])) {
                        followingChar = levelChars2D[i + 1][j];
                    }
                }
                final Item item = this.getItem(levelChars2D, levelChars2D[i][j], followingChar, j, i);
                if (item != null) {
                    if (item.isWarpable()) {
                        this.setWarpInfo(levelChars2D, item, j, i, levelNumber);
                    }
                    item.inserted = true;
                    item.insertInLevel(j, i);
                }
            }
        }
        if (this.levelEndType != 0) {
            if (this.levelEndType == 1) {
                this.reduceItemWidthBy(24);
                this.changeLevelEndType(1, false);
            }
            else if (this.levelEndType == 2) {
                this.reduceItemWidthBy(24);
                this.changeLevelEndType(2, false);
            }
            else if (this.levelEndType >= 3 && this.levelEndType <= 10) {
                this.reduceItemWidthBy(64);
                this.changeLevelEndType(this.levelEndType - 2, false);
            }
        }
    }
    
    private void reduceItemWidthBy(final int x) throws Exception {
        final Item[][] newItems = new Item[Game.yTiles][this.items[0].length - x];
        for (int i = 0; i < Game.yTiles; ++i) {
            for (int j = 0; j < newItems[0].length; ++j) {
                newItems[i][j] = this.items[i][j];
            }
        }
        this.items = newItems;
    }
    
    private void setWarpInfo(final char[][] chars, final Item item, final int x, final int y, final int levelNumber) throws Exception {
        boolean incoming = false;
        boolean outgoing = false;
        int sourceID = -1;
        int destLevel = -1;
        int destID = -1;
        final char[] sourceIDChars = new char[3];
        final char[] destLevelChars = new char[3];
        final char[] destIDChars = new char[3];
        if (item.character == '~' || item.character == ';' || item.character == '\u0110' || item.character == '¿') {
            sourceIDChars[0] = chars[y][x + 1];
            sourceIDChars[1] = chars[y][x + 2];
            sourceIDChars[2] = chars[y][x + 3];
            destLevelChars[0] = chars[y + 1][x + 1];
            destLevelChars[1] = chars[y + 1][x + 2];
            destLevelChars[2] = chars[y + 1][x + 3];
            destIDChars[0] = chars[y + 2][x + 1];
            destIDChars[1] = chars[y + 2][x + 2];
            destIDChars[2] = chars[y + 2][x + 3];
        }
        else if (item.character == '\u0108') {
            final char[] array = sourceIDChars;
            final int n = 0;
            final char[] array2 = sourceIDChars;
            final int n2 = 1;
            final char[] array3 = sourceIDChars;
            final int n3 = 2;
            final char c = 'x';
            array3[n3] = c;
            array[n] = (array2[n2] = c);
            final char[] displayLevelChars = { chars[y][x + 1], chars[y][x + 2], chars[y][x + 3] };
            final String displayLevelValue = String.valueOf(displayLevelChars);
            item.displayWarpNumber = (displayLevelValue.equals("xxx") ? -1 : Integer.valueOf(String.valueOf(displayLevelChars)));
            destLevelChars[0] = chars[y + 1][x + 1];
            destLevelChars[1] = chars[y + 1][x + 2];
            destLevelChars[2] = chars[y + 1][x + 3];
            final char[] array4 = destIDChars;
            final int n4 = 0;
            final char[] array5 = destIDChars;
            final int n5 = 1;
            final char[] array6 = destIDChars;
            final int n6 = 2;
            final char c2 = '0';
            array6[n6] = c2;
            array4[n4] = (array5[n5] = c2);
        }
        else if (LevelLoader.isSideOpeningPipe(item.character)) {
            sourceIDChars[0] = chars[y + 1][x];
            sourceIDChars[1] = chars[y + 1][x + 1];
            sourceIDChars[2] = chars[y + 1][x + 2];
            destLevelChars[0] = chars[y + 2][x];
            destLevelChars[1] = chars[y + 2][x + 1];
            destLevelChars[2] = chars[y + 2][x + 2];
            destIDChars[0] = chars[y + 3][x];
            destIDChars[1] = chars[y + 3][x + 1];
            destIDChars[2] = chars[y + 3][x + 2];
        }
        else if (LevelLoader.isBeanstalkBlock(item.character)) {
            final char[] array7 = sourceIDChars;
            final int n7 = 0;
            final char[] array8 = sourceIDChars;
            final int n8 = 1;
            final char[] array9 = sourceIDChars;
            final int n9 = 2;
            final char c3 = 'x';
            array9[n9] = c3;
            array7[n7] = (array8[n8] = c3);
            destLevelChars[0] = chars[y - 3][x];
            destLevelChars[1] = chars[y - 2][x];
            destLevelChars[2] = chars[y - 1][x];
            destIDChars[0] = chars[y - 3][x + 1];
            destIDChars[1] = chars[y - 2][x + 1];
            destIDChars[2] = chars[y - 1][x + 1];
        }
        else if (item.character == '\u03e4') {
            sourceIDChars[0] = chars[y + 1][x];
            sourceIDChars[1] = chars[y + 2][x];
            sourceIDChars[2] = chars[y + 3][x];
            final char[] array10 = destLevelChars;
            final int n10 = 0;
            final char[] array11 = destLevelChars;
            final int n11 = 1;
            final char[] array12 = destLevelChars;
            final int n12 = 2;
            final char c4 = 'x';
            array12[n12] = c4;
            array10[n10] = (array11[n11] = c4);
            final char[] array13 = destIDChars;
            final int n13 = 0;
            final char[] array14 = destIDChars;
            final int n14 = 1;
            final char[] array15 = destIDChars;
            final int n15 = 2;
            final char c5 = 'x';
            array15[n15] = c5;
            array13[n13] = (array14[n14] = c5);
        }
        else if (item.character == '½') {
            sourceIDChars[0] = chars[y + 1][x];
            sourceIDChars[1] = chars[y + 2][x];
            sourceIDChars[2] = chars[y + 3][x];
            final char[] array16 = destLevelChars;
            final int n16 = 0;
            final char[] array17 = destLevelChars;
            final int n17 = 1;
            final char[] array18 = destLevelChars;
            final int n18 = 2;
            final char c6 = 'x';
            array18[n18] = c6;
            array16[n16] = (array17[n17] = c6);
            final char[] array19 = destIDChars;
            final int n19 = 0;
            final char[] array20 = destIDChars;
            final int n20 = 1;
            final char[] array21 = destIDChars;
            final int n21 = 2;
            final char c7 = 'x';
            array21[n21] = c7;
            array19[n19] = (array20[n20] = c7);
        }
        if (sourceIDChars[0] != 'x') {
            incoming = true;
            sourceID = Integer.valueOf(String.valueOf(sourceIDChars));
        }
        if (destLevelChars[0] != 'x') {
            outgoing = true;
            destLevel = Integer.valueOf(String.valueOf(destLevelChars));
            destID = Integer.valueOf(String.valueOf(destIDChars));
        }
        final Warp warp = new Warp(outgoing, incoming, levelNumber, sourceID, destLevel, destID, item);
        if (incoming) {
            this.insertIncomingWarp(warp);
        }
        if (outgoing) {
            this.outgoingWarps.add(warp);
        }
        item.warp = warp;
    }
    
    private Item getItem(final char[][] levelChars, final char c, char fc, final int xTile, final int yTile) throws Exception {
        if (Character.isDigit(c) || c == 'x') {
            return null;
        }
        if (c == '«') {
            final Item i = this.frame.backgroundPanel.singleCloud.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '©') {
            final Item i = this.frame.backgroundPanel.doubleCloud.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '¦') {
            final Item i = this.frame.backgroundPanel.tripleCloud.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '¥') {
            final Item i = this.frame.backgroundPanel.singleBush.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '¤') {
            final Item i = this.frame.backgroundPanel.doubleBush.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '£') {
            final Item i = this.frame.backgroundPanel.tripleBush.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '\u00fd') {
            final Item i = this.frame.backgroundPanel.bridgeChain.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == '¢') {
            final Item i = this.frame.backgroundPanel.tallTrimmedBush.item.copy();
            return i;
        }
        if (c == '¡') {
            final Item i = this.frame.backgroundPanel.shortTrimmedBush.item.copy();
            return i;
        }
        if (c == '\u2192') {
            final Item i = this.frame.backgroundPanel.shortSnowyBush.item.copy();
            return i;
        }
        if (c == '\u2190') {
            final Item i = this.frame.backgroundPanel.tallSnowyBush.item.copy();
            return i;
        }
        if (c == 'i') {
            return this.frame.backgroundPanel.picketFence.item.copy();
        }
        if (c == 'a') {
            return this.frame.backgroundPanel.castleWall.item.copy();
        }
        if (c == 'b') {
            final Item i = this.frame.backgroundPanel.smallHill.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == 'c') {
            final Item i = this.frame.backgroundPanel.bigHill.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == 'd') {
            final Item i = this.frame.backgroundPanel.smallCastle.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == 'e') {
            final Item i = this.frame.backgroundPanel.largeCastle.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == 'f') {
            return this.frame.solidsPanel.treeBark.item.copy();
        }
        if (c == '\u2660') {
            return this.frame.solidsPanel.mushroomStem.item.copy();
        }
        if (c == 'g') {
            return this.frame.backgroundPanel.lavaTop.item.copy();
        }
        if (c == 'µ') {
            return this.frame.backgroundPanel.lavaBottom.item.copy();
        }
        if (c == '¾') {
            return this.frame.backgroundPanel.waterTop.item.copy();
        }
        if (c == '\u00e5') {
            return this.frame.backgroundPanel.waterBottom.item.copy();
        }
        if (c == '\u00f1') {
            final Item i = this.frame.pipesPanel.warpZoneMessage.item.copy();
            i.shifted = this.getShift(fc);
            return i;
        }
        if (c == 'h') {
            return this.frame.miscPanel.coin.item.copy();
        }
        if (c == '\u0292') {
            final Item i = this.frame.miscPanel.checkpoint.item.copy();
            i.checkpointType = this.getCheckpointType(fc);
            return i;
        }
        if (c == '½') {
            return this.frame.miscPanel.thinAirWarp.item.copy();
        }
        if (c == '\u00f7') {
            return this.frame.miscPanel.infiniteCorridor.item.copy();
        }
        if (c == '\u03e4') {
            return this.frame.miscPanel.beanstalkArrival.item.copy();
        }
        if (c == 'j') {
            final Item i = this.frame.miscPanel.spring.item.copy();
            i.superSpring = this.getSuperSpring(fc);
            return i;
        }
        if (c == 'k') {
            final Item item = this.frame.miscPanel.mario.item.copy();
            item.xTile = xTile;
            item.yTile = yTile;
            return this.marioStartItem = item;
        }
        if (c == 'l') {
            return this.frame.solidsPanel.ground.item.copy();
        }
        
        if (c == 'm') {
            return this.frame.solidsPanel.ground.item.copy();
        }
        if (c == 'n') {
            return this.frame.solidsPanel.ground.item.copy();
        }
        if (c == 'o') {
            return this.frame.solidsPanel.ground.item.copy();
        }
        if (c == '§') {
            return this.frame.solidsPanel.block.item.copy();
        }
        if (c == 'p') {
            return this.frame.solidsPanel.ground.item.copy();
        }
       
        if (c == 'q') {
            return this.frame.solidsPanel.block.item.copy();
        }
        
        if (c == 'r') {
            return this.frame.solidsPanel.block.item.copy();
        }
        if (c == '\u045e') {
            return this.frame.solidsPanel.block.item.copy();
        }
        if (c == 's') {
            return this.frame.solidsPanel.metal.item.copy();
        }
        
        if (c == 't') {
            return this.frame.solidsPanel.metal.item.copy();
        }
        if (c == 'u') {
            return this.frame.solidsPanel.metal.item.copy();
        }
        //end comment
        if (c == 'v') {
            return this.frame.solidsPanel.treeLeft.item.copy();
        }
        if (c == 'w') {
            return this.frame.solidsPanel.treeRight.item.copy();
        }
        if (c == 'y') {
            return this.frame.solidsPanel.treeMiddle.item.copy();
        }
        if (c == '\u25cb') {
            return this.frame.solidsPanel.mushroomLeft.item.copy();
        }
        if (c == '\u25d9') {
            return this.frame.solidsPanel.mushroomRight.item.copy();
        }
        if (c == '\u2642') {
            return this.frame.solidsPanel.mushroomMiddle.item.copy();
        }
        if (c == '\u2193') {
            return this.frame.solidsPanel.mushroomStemTop.item.copy();
        }
        if (c == 'z') {
            return this.frame.solidsPanel.coral.item.copy();
        }
        if (c == '\u25b2') {
            return this.frame.solidsPanel.bridge.item.copy();
        }
        if (c == '\u00e6') {
            return this.frame.solidsPanel.bowserBridge.item.copy();
        }
        if (c == 'A') {
            return this.frame.blocksPanel.lightBrickNothing.item.copy();
        }
        if (c == 'B') {
            return this.frame.blocksPanel.lightBrickCoin.item.copy();
        }
        if (c == 'C') {
            final Item i = this.frame.blocksPanel.lightBrickCoins.item.copy();
            if (fc == 'x') {
                i.coinCount = 5;
            }
            else {
                i.coinCount = Integer.valueOf(String.valueOf(fc));
            }
            return i;
        }
        if (c == 'D') {
            final Item i = this.frame.blocksPanel.lightBrickGrow.item.copy();
            i.poison = this.getPoison(fc);
            return i;
        }
        if (c == 'E') {
            return this.frame.blocksPanel.lightBrickLife.item.copy();
        }
        if (c == 'F') {
            return this.frame.blocksPanel.lightBrickStar.item.copy();
        }
        if (c == 'G') {
            return this.frame.blocksPanel.lightBrickBeanstalk.item.copy();
        }
        //TODO remove this
        if (c == 'H') {
            return this.frame.blocksPanel.lightBrickNothing.item.copy();
        }
        if (c == 'I') {
            return this.frame.blocksPanel.lightBrickCoin.item.copy();
        }
        if (c == 'J') {
            final Item i = this.frame.blocksPanel.lightBrickCoins.item.copy();
            if (fc == 'x') {
                i.coinCount = 5;
            }
            else {
                i.coinCount = Integer.valueOf(String.valueOf(fc));
            }
            return i;
        }
        
        if (c == 'K') {
            final Item i = this.frame.blocksPanel.lightBrickGrow.item.copy();
            i.poison = this.getPoison(fc);
            return i;
        }
        if (c == 'L') {
            return this.frame.blocksPanel.lightBrickLife.item.copy();
        }
        if (c == 'M') {
            return this.frame.blocksPanel.lightBrickStar.item.copy();
        }
        if (c == 'N') {
            return this.frame.blocksPanel.lightBrickBeanstalk.item.copy();
        }
        if (c == '\u2022') {
            return this.frame.blocksPanel.lightBrickNothing.item.copy();
        }
        if (c == '\u25d8') {
            return this.frame.blocksPanel.lightBrickCoin.item.copy();
        }
        if (c == '\u2640') {
            final Item i = this.frame.blocksPanel.lightBrickCoins.item.copy();
            if (fc == 'x') {
                i.coinCount = 5;
            }
            else {
                i.coinCount = Integer.valueOf(String.valueOf(fc));
            }
            return i;
        }
        if (c == '\u266b') {
            final Item i = this.frame.blocksPanel.lightBrickGrow.item.copy();
            i.poison = this.getPoison(fc);
            return i;
        }
        if (c == '\u263c') {
            return this.frame.blocksPanel.lightBrickLife.item.copy();
        }
        if (c == '\u25ba') {
            return this.frame.blocksPanel.lightBrickStar.item.copy();
        }
        if (c == '\u25c4') {
            return this.frame.blocksPanel.lightBrickBeanstalk.item.copy();
        }
        //put end of comment HERE
        if (c == 'O') {
            return this.frame.blocksPanel.questionBoxCoin.item.copy();
        }
        if (c == 'P') {
            final Item i = this.frame.blocksPanel.questionBoxGrow.item.copy();
            i.poison = this.getPoison(fc);
            return i;
        }
        if (c == 'Q') {
            return this.frame.blocksPanel.questionBoxLife.item.copy();
        }
        if (c == 'R') {
            return this.frame.blocksPanel.questionBoxStar.item.copy();
        }
        if (c == '\u03d8') {
            return this.frame.blocksPanel.questionBoxBeanstalk.item.copy();
        }
        if (c == 'W') {
            return this.frame.blocksPanel.questionBoxInvisibleCoin.item.copy();
        }
        if (c == 'X') {
            final Item i = this.frame.blocksPanel.questionBoxInvisibleGrow.item.copy();
            i.poison = this.getPoison(fc);
            return i;
        }
        if (c == 'Y') {
            return this.frame.blocksPanel.questionBoxInvisibleLife.item.copy();
        }
        if (c == 'Z') {
            return this.frame.blocksPanel.questionBoxInvisibleStar.item.copy();
        }
        if (c == '\u03dc') {
            return this.frame.blocksPanel.questionBoxInvisibleBeanstalk.item.copy();
        }
        if (c == '`') {
            return this.frame.pipesPanel.sideSection.item.copy();
        }
        if (c == '~') {
            return this.frame.pipesPanel.topWOChomp.item.copy();
        }
        if (c == ';') {
            final Item i = this.frame.pipesPanel.topWChomp.item.copy();
            i.redPiranha = this.getRedPiranha(fc);
            return i;
        }
        if (c == '\u0110') {
            return this.frame.pipesPanel.bottomWOChomp.item.copy();
        }
        if (c == '¿') {
            final Item i = this.frame.pipesPanel.bottomWChomp.item.copy();
            i.redPiranha = this.getRedPiranha(fc);
            return i;
        }
        if (c == '!') {
            return this.frame.pipesPanel.leftWOChomp.item.copy();
        }
        if (c == '\u2021') {
            final Item i = this.frame.pipesPanel.leftWChomp.item.copy();
            i.redPiranha = this.getRedPiranha(fc);
            return i;
        }
        if (c == '\u2663') {
            return this.frame.pipesPanel.rightWOChomp.item.copy();
        }
        if (c == '\u00ee') {
            final Item i = this.frame.pipesPanel.rightWChomp.item.copy();
            i.redPiranha = this.getRedPiranha(fc);
            return i;
        }
        if (c == '@') {
            return this.frame.pipesPanel.topSection.item.copy();
        }
        if (c == '#') {
            return this.frame.pipesPanel.leftConnector.item.copy();
        }
        if (c == 'S') {
            return this.frame.pipesPanel.rightConnector.item.copy();
        }
        if (c == 'T') {
            return this.frame.pipesPanel.doubleConnector.item.copy();
        }
        if (c == 'U') {
            return this.frame.pipesPanel.topConnector.item.copy();
        }
        if (c == '\u010e') {
            return this.frame.pipesPanel.bottomConnector.item.copy();
        }
        if (c == '\u0108') {
            final Item i = this.frame.pipesPanel.warpZonePipe.item.copy();
            if (fc == 'x') {
                fc = '0';
            }
            i.warpPipeColor = Integer.valueOf(String.valueOf(fc));
            return i;
        }
        if (c == '$') {
            final Item i = this.frame.enemiesPanel.lightGoomba.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '%') {
            final Item i = this.frame.enemiesPanel.darkGoomba.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '\u02e6') {
            final Item i = this.frame.enemiesPanel.grayGoomba.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '^') {
            final Item i = this.frame.enemiesPanel.lightKoopaNormal.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '&') {
            final Item i = this.frame.enemiesPanel.lightKoopaBouncing.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '*') {
            final Item i = this.frame.enemiesPanel.lightKoopaFlyingV.item.copy();
            i.oscOffset = this.getOscOffset(fc);
            return i;
        }
        if (c == '¬') {
            final Item i = this.frame.enemiesPanel.lightKoopaFlyingH.item.copy();
            i.oscOffset = this.getOscOffset(fc);
            return i;
        }
        if (c == '(') {
            final Item i = this.frame.enemiesPanel.darkKoopaNormal.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == ')') {
            final Item i = this.frame.enemiesPanel.darkKoopaBouncing.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '-') {
            final Item i = this.frame.enemiesPanel.darkKoopaFlyingV.item.copy();
            i.oscOffset = this.getOscOffset(fc);
            return i;
        }
        if (c == '±') {
            final Item i = this.frame.enemiesPanel.darkKoopaFlyingH.item.copy();
            i.oscOffset = this.getOscOffset(fc);
            return i;
        }
        if (c == '_') {
            final Item i = this.frame.enemiesPanel.redKoopaNormal.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '=') {
            final Item i = this.frame.enemiesPanel.redKoopaBouncing.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '+') {
            final Item i = this.frame.enemiesPanel.redKoopaFlyingV.item.copy();
            i.oscOffset = this.getOscOffset(fc);
            return i;
        }
        if (c == '®') {
            final Item i = this.frame.enemiesPanel.redKoopaFlyingH.item.copy();
            i.oscOffset = this.getOscOffset(fc);
            return i;
        }
        if (c == '[') {
            return this.frame.enemiesPanel.squid.item.copy();
        }
        if (c == '{') {
            final Item i = this.frame.enemiesPanel.redFish.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == ']') {
            final Item i = this.frame.enemiesPanel.grayFishStraight.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '|') {
            final Item i = this.frame.enemiesPanel.grayFishZigZag.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '}') {
            return this.frame.enemiesPanel.lightHammerBro.item.copy();
        }
        if (c == '\u00f6') {
            return this.frame.enemiesPanel.darkHammerBro.item.copy();
        }
        if (c == '\\') {
            final Item i = this.frame.enemiesPanel.spiny.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == ':') {
            final Item i = this.frame.enemiesPanel.lightBeetle.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '\u00f4') {
            final Item i = this.frame.enemiesPanel.darkBeetle.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '\u02e7') {
            final Item i = this.frame.enemiesPanel.grayBeetle.item.copy();
            i.flip = this.getFlip(fc);
            return i;
        }
        if (c == '\'') {
            return this.frame.enemiesPanel.tallCannon.item.copy();
        }
        if (c == '\u260e') {
            return this.frame.enemiesPanel.cannonBase.item.copy();
        }
        if (c == '\"') {
            return this.frame.enemiesPanel.shortCannon.item.copy();
        }
        if (c == ',') {
            final Item i = this.frame.enemiesPanel.firebarShort.item.copy();
            i.flip = this.getFirebarFlip(fc);
            i.speedBoost = this.getFirebarFast(fc);
            return i;
        }
        if (c == '<') {
            final Item i = this.frame.enemiesPanel.firebarLong.item.copy();
            i.flip = this.getFirebarFlip(fc);
            i.speedBoost = this.getFirebarFast(fc);
            return i;
        }
        if (c == '.') {
            final Item i = this.frame.enemiesPanel.lavaBall.item.copy();
            if (fc == 'x') {
                i.fireballColor = 0;
                return i;
            }
            final int fcInt = Integer.valueOf(String.valueOf(fc));
            if (fcInt == 0) {
                i.fireballColor = 0;
            }
            else if (fcInt == 1) {
                i.fireballColor = 1;
            }
            else if (fcInt == 2) {
                i.fireballColor = 2;
            }
            return i;
        }
        else {
            if (c == '>') {
                final Item i = this.frame.platformsPanel.longRepUp.item.copy();
                i.singlePlatform = this.getRepPlatformSingle(fc);
                return i;
            }
            if (c == '/') {
                final Item i = this.frame.platformsPanel.longRepDown.item.copy();
                i.singlePlatform = this.getRepPlatformSingle(fc);
                return i;
            }
            if (c == '?') {
                final Item i = this.frame.platformsPanel.longOscV.item.copy();
                i.oscOffset = this.getOscOffset(fc);
                return i;
            }
            if (c == '\u263a') {
                final Item i = this.frame.platformsPanel.longOscH.item.copy();
                i.oscOffset = this.getOscOffset(fc);
                return i;
            }
            if (c == '\u25bc') {
                return this.frame.platformsPanel.longFalling.item.copy();
            }
            if (c == '\u2194') {
                final Item i = this.frame.platformsPanel.shortRepUp.item.copy();
                i.singlePlatform = this.getRepPlatformSingle(fc);
                return i;
            }
            if (c == '\u2191') {
                final Item i = this.frame.platformsPanel.shortRepDown.item.copy();
                i.singlePlatform = this.getRepPlatformSingle(fc);
                return i;
            }
            if (c == '\u266a') {
                final Item i = this.frame.platformsPanel.shortOscV.item.copy();
                i.oscOffset = this.getOscOffset(fc);
                return i;
            }
            if (c == '\u2195') {
                final Item i = this.frame.platformsPanel.shortOscH.item.copy();
                i.oscOffset = this.getOscOffset(fc);
                return i;
            }
            if (c == '\u203c') {
                return this.frame.platformsPanel.shortFalling.item.copy();
            }
            if (c == '¶') {
                final Item i = this.frame.platformsPanel.extraShortRepUp.item.copy();
                i.singlePlatform = this.getRepPlatformSingle(fc);
                return i;
            }
            if (c == 'V') {
                final Item i = this.frame.platformsPanel.extraShortRepDown.item.copy();
                i.singlePlatform = this.getRepPlatformSingle(fc);
                return i;
            }
            if (c == '\u25ac') {
                return this.frame.platformsPanel.longPulley.item.copy();
            }
            if (c == '\u0398') {
                return this.frame.platformsPanel.shortPulley.item.copy();
            }
            if (c == '\u263b') {
                return this.frame.platformsPanel.carrierLong.item.copy();
            }
            if (c == '\u00e7') {
                return this.frame.platformsPanel.carrierShort.item.copy();
            }
            if (c == '\u2665') {
                this.levelEndType = 1;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u21a8') {
                this.levelEndType = 2;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u2666') {
                this.levelEndType = 3;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '¼') {
                this.levelEndType = 4;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u00d7') {
                this.levelEndType = 5;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u00d8') {
                this.levelEndType = 6;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u00de') {
                this.levelEndType = 7;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u00df') {
                this.levelEndType = 8;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u00f0') {
                this.levelEndType = 9;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (c == '\u00ff') {
                this.levelEndType = 10;
                this.setNextLevelNumber(levelChars, xTile);
                return null;
            }
            if (this.frame.game.textures.customTextChars.containsKey(c)) {
                return this.frame.pipesPanel.customTextItems.get(c).copy();
            }
            throw new RuntimeException("Unknown item character when loading a level: " + c);
        }
    }
    
    private void setNextLevelNumber(final char[][] chars, final int x) {
        final char[] destLevelChars = { chars[1][x + 1], chars[1][x + 2], chars[1][x + 3] };
        this.nextLevelNumber = Integer.valueOf(String.valueOf(destLevelChars));
    }
    
    private boolean getFlip(final char c) {
        return c != '0';
    }
    
    private boolean getPoison(final char c) {
        return c == '0';
    }
    
    private boolean getSuperSpring(final char c) {
        return c == '0';
    }
    
    private boolean getRedPiranha(final char c) {
        return c == '0';
    }
    
    private int getCheckpointType(final char fc) {
        if (fc == '0' || fc == 'x') {
            return 0;
        }
        return 1;
    }
    
    private boolean getShift(final char c) {
        return c == '0';
    }
    
    private boolean getRepPlatformSingle(final char c) {
        return c == '0';
    }
    
    private int getOscOffset(final char c) {
        if (c == 'x' || c == '0') {
            return 0;
        }
        if (c == '1') {
            return 1;
        }
        if (c == '2') {
            return 2;
        }
        if (c == '3') {
            return 3;
        }
        return 0;
    }
    
    private boolean getFirebarFlip(final char c) {
        return c == '0' || c == '1';
    }
    
    private boolean getFirebarFast(final char c) {
        return c == '1' || c == '3';
    }
    
    private void setLevelType(final char[] levelChars, final int lineLength) throws Exception {
        final char levelTypeChar = levelChars[(lineLength + 2) * Game.yTiles];
        if (levelTypeChar == '0') {
            this.levelType = 0;
        }
        else if (levelTypeChar == '1') {
            this.levelType = 1;
        }
        else if (levelTypeChar == '2') {
            this.levelType = 2;
        }
        else if (levelTypeChar == '3') {
            this.levelType = 3;
        }
        else if (levelTypeChar == '4') {
            this.levelType = 4;
        }
        else if (levelTypeChar == '5') {
            this.levelType = 5;
        }
        else {
            if (levelTypeChar != '6') {
                throw new RuntimeException("Level type character is invalid in file");
            }
            this.levelType = 6;
        }
    }
    
    private void setLevelNames(final char[] chars, final int lineLength) throws Exception {
        final char[] levelName1Chars = new char[8];
        final char[] levelName2Chars = new char[8];
        int x = 0;
        int i;
        for (int nameIndex = i = (lineLength + 2) * Game.yTiles + 2 + 5 + 8; i < nameIndex + 8; ++i) {
            levelName1Chars[x] = chars[i];
            levelName2Chars[x++] = chars[i + 8 + 2];
        }
        this.name1 = String.valueOf(levelName1Chars).trim();
        this.name2 = String.valueOf(levelName2Chars).trim();
    }
    
    private boolean hasBulletThrower(final char[] chars, final int lineLength) throws Exception {
        final char hasBulletChar = chars[(lineLength + 2) * Game.yTiles + 3];
        return hasBulletChar == '1';
    }
    
    private boolean hasFlyingFish(final char[] chars, final int lineLength) throws Exception {
        final char hasFishChar = chars[(lineLength + 2) * Game.yTiles + 2];
        return hasFishChar == '1';
    }
    
    private boolean hasSpinyThrower(final char[] chars, final int lineLength) throws Exception {
        final char hasThrowerChar = chars[(lineLength + 2) * Game.yTiles + 1];
        return hasThrowerChar == '1';
    }
    
    private boolean isBlackAndWhite(final char[] chars, final int lineLength) throws Exception {
        final char blackAndWhiteChar = chars[(lineLength + 2) * Game.yTiles + 4];
        return blackAndWhiteChar == '1';
    }
    
    private boolean isTimedLevel(final char[] chars, final int lineLength) throws Exception {
        final char isTimedChar = chars[(lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10];
        return isTimedChar == '1';
    }
    
    private int getLevelTimeLimit(final char[] chars, final int lineLength) throws Exception {
        final char[] timeChars = new char[4];
        for (int i = 0; i < timeChars.length; ++i) {
            timeChars[i] = chars[(lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + i];
        }
        return Integer.valueOf(String.valueOf(timeChars));
    }
    
    private int getPipeColor(final char[] chars, final int lineLength) throws Exception {
        char pipeColorChar = '0';
        try {
            pipeColorChar = chars[(lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + 6];
        }
        catch (Exception ex) {}
        if (pipeColorChar == '1') {
            return 1;
        }
        if (pipeColorChar == '2') {
            return 2;
        }
        if (pipeColorChar == '3') {
            return 3;
        }
        if (pipeColorChar == '4') {
            return 4;
        }
        return 0;
    }
    
    private boolean hasAutoScrolling(final char[] chars, final int lineLength) {
        try {
            final char as = chars[(lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + 6 + 1];
            return as == '1';
        }
        catch (Exception e) {
            return false;
        }
    }
    
    private int getTexturePack(final char[] chars, final int lineLength) {
        char texturePackChar = '0';
        try {
            texturePackChar = chars[(lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + 6 + 2];
        }
        catch (Exception ex) {}
        if (texturePackChar == '0') {
            return 0;
        }
        if (texturePackChar == '1') {
            return 1;
        }
        return 0;
    }
    
    private int getCliffDestLevel(final char[] chars, final int lineLength) throws Exception {
        final char[] destLevelChars = new char[3];
        final int startingPoint = (lineLength + 2) * Game.yTiles + 5 + 2;
        destLevelChars[0] = chars[startingPoint];
        destLevelChars[1] = chars[startingPoint + 1];
        destLevelChars[2] = chars[startingPoint + 2];
        if (destLevelChars[0] == 'x') {
            return -1;
        }
        return Integer.valueOf(String.valueOf(destLevelChars));
    }
    
    private int getCliffDestID(final char[] chars, final int lineLength) throws Exception {
        final char[] destIDChars = new char[3];
        final int startingPoint = (lineLength + 2) * Game.yTiles + 5 + 2 + 3;
        destIDChars[0] = chars[startingPoint];
        destIDChars[1] = chars[startingPoint + 1];
        destIDChars[2] = chars[startingPoint + 2];
        return Integer.valueOf(String.valueOf(destIDChars));
    }
    
    static {
        DEFAULT_MARIO_START_Y_TILE = Game.yTiles - 4 - 3;
    }
}
