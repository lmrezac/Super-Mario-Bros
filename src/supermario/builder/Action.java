// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

public class Action
{
    public static final int TYPE_ITEM_MOVE = 0;
    public static final int TYPE_ITEM_INSERT = 1;
    public static final int TYPE_ITEM_REMOVE = 2;
    public static final int TYPE_COLUMN_INSERT = 3;
    public static final int TYPE_COLUMN_REMOVE = 4;
    public static final int TYPE_LEVEL_PROP_CHANGE = 8;
    public static final int TYPE_ENEMY_REVERSE = 9;
    public static final int TYPE_COINS_COUNT = 10;
    public static final int TYPE_BACKGROUND_REVERSE = 11;
    public static final int TYPE_BEANSTALK_BLOCK_PROP_CHANGE = 12;
    public static final int TYPE_WARP_ZONE_PROP_CHANGE = 13;
    public static final int TYPE_PIPE_PROP_CHANGE = 14;
    public static final int TYPE_FIREBAR_PROP_CHANGE = 15;
    public static final int TYPE_REP_PLATFORM_PROP_CHANGE = 16;
    public static final int TYPE_LAVABALL_PROP_CHANGE = 17;
    public static final int TYPE_OSC_PLATFORM_PROP_CHANGE = 18;
    public static final int TYPE_CHECKPOINT_PROP_CHANGE = 19;
    public static final int TYPE_TOGGLE_PIRANHA = 20;
    public static final int TYPE_TOGGLE_POISON = 21;
    public static final int TYPE_TOGGLE_RED_PIRANHA = 22;
    public static final int TYPE_TOGGLE_SUPER_SPRING = 23;
    private int type;
    private int column;
    private Item item;
    private Warp warp;
    private Warp oldWarp;
    private Warp newWarp;
    private int oldXTile;
    private int oldYTile;
    private int newXTile;
    private int newYTile;
    private int oldCount;
    private int newCount;
    private int oldType;
    private int newType;
    private int oldDestLvl;
    private int oldDestID;
    private int newDestLvl;
    private int newDestID;
    private int oldDisplay;
    private int newDisplay;
    private boolean oldFlip;
    private boolean newFlip;
    private boolean oldSpeedBoost;
    private boolean newSpeedBoost;
    private int oldColor;
    private int newColor;
    private int oldOffset;
    private int newOffset;
    private String oldName1;
    private String oldName2;
    private String newName1;
    private String newName2;
    private boolean oldTimed;
    private boolean oldLakitu;
    private boolean oldFish;
    private boolean oldBullets;
    private boolean oldBandW;
    private boolean oldAutoScrolling;
    private boolean newTimed;
    private boolean newLakitu;
    private boolean newFish;
    private boolean newBullets;
    private boolean newBandW;
    private boolean newAutoScrolling;
    private int oldTime;
    private int oldLevelType;
    private int oldLevelEndIndex;
    private int oldNextLevel;
    private int oldDeathLevel;
    private int oldDeathID;
    private int oldPipeColor;
    private int oldTexturePack;
    private int newTime;
    private int newLevelType;
    private int newLevelEndIndex;
    private int newNextLevel;
    private int newDeathLevel;
    private int newDeathID;
    private int newPipeColor;
    private int newTexturePack;
    
    public static Action itemMoved(final Item item, final Warp warp, final int oldXTile, final int oldYTile, final int newXTile, final int newYTile) {
        final Action action = new Action();
        action.type = 0;
        action.item = item;
        if (item.isWarpable()) {
            action.warp = warp;
        }
        action.oldXTile = oldXTile;
        action.oldYTile = oldYTile;
        action.newXTile = newXTile;
        action.newYTile = newYTile;
        return action;
    }
    
    public static Action itemInserted(final Item item, final Warp warp) {
        final Action action = new Action();
        action.type = 1;
        action.item = item;
        if (item.isWarpable()) {
            action.warp = warp;
        }
        return action;
    }
    
    public static Action itemRemoved(final Item item, final Warp warp) {
        final Action action = new Action();
        action.type = 2;
        action.item = item;
        if (item.isWarpable()) {
            action.warp = warp;
        }
        return action;
    }
    
    public static Action columnInserted(final int column) {
        final Action action = new Action();
        action.type = 3;
        action.column = column;
        return action;
    }
    
    public static Action columnRemoved(final int column) {
        final Action action = new Action();
        action.type = 4;
        action.column = column;
        return action;
    }
    
    public static Action enemyReverse(final Item item) {
        final Action action = new Action();
        action.type = 9;
        action.item = item;
        return action;
    }
    
    public static Action coinCount(final Item item, final int oldCount, final int newCount) {
        final Action action = new Action();
        action.type = 10;
        action.item = item;
        action.oldCount = oldCount;
        action.newCount = newCount;
        return action;
    }
    
    public static Action backgroundReverse(final Item item) {
        final Action action = new Action();
        action.type = 11;
        action.item = item;
        return action;
    }
    
    public static Action beanstalkPropChange(final Item item, final int oldDestLvl, final int oldDestID, final int newDestLvl, final int newDestID) {
        final Action action = new Action();
        action.type = 12;
        action.item = item;
        action.oldDestLvl = oldDestLvl;
        action.oldDestID = oldDestID;
        action.newDestLvl = newDestLvl;
        action.newDestID = newDestID;
        return action;
    }
    
    public static Action warpZonePropChange(final Item item, final int oldDestLevel, final int oldDisplay, final int oldColor, final int newDestLvl, final int newDisplay, final int newColor) {
        final Action action = new Action();
        action.type = 13;
        action.item = item;
        action.oldDestLvl = oldDestLevel;
        action.oldDisplay = oldDisplay;
        action.oldColor = oldColor;
        action.newDestLvl = newDestLvl;
        action.newDisplay = newDisplay;
        action.newColor = newColor;
        return action;
    }
    
    public static Action firebarPropChange(final Item item, final boolean oldFlip, final boolean newFlip, final boolean oldSpeedBoost, final boolean newSpeedBoost) {
        final Action action = new Action();
        action.type = 15;
        action.item = item;
        action.oldFlip = oldFlip;
        action.newFlip = newFlip;
        action.oldSpeedBoost = oldSpeedBoost;
        action.newSpeedBoost = newSpeedBoost;
        return action;
    }
    
    public static Action repPlatformPropChange(final Item item) {
        final Action action = new Action();
        action.type = 16;
        action.item = item;
        return action;
    }
    
    public static Action powerupPropChange(final Item item) {
        final Action action = new Action();
        action.type = 21;
        action.item = item;
        return action;
    }
    
    public static Action springPropChange(final Item item) {
        final Action action = new Action();
        action.type = 23;
        action.item = item;
        return action;
    }
    
    public static Action lavaballPropChange(final Item item, final int oldColor, final int newColor) {
        final Action action = new Action();
        action.type = 17;
        action.item = item;
        action.oldColor = oldColor;
        action.newColor = newColor;
        return action;
    }
    
    public static Action oscPlatformPropChange(final Item item, final int oldOffset, final int newOffset) {
        final Action action = new Action();
        action.type = 18;
        action.item = item;
        action.oldOffset = oldOffset;
        action.newOffset = newOffset;
        return action;
    }
    
    public static Action checkpointPropChange(final Item item, final int oldType, final int newType) {
        final Action action = new Action();
        action.type = 19;
        action.item = item;
        action.oldType = oldType;
        action.newType = newType;
        return action;
    }
    
    public static Action togglePiranha(final Item item) {
        final Action action = new Action();
        action.type = 20;
        action.item = item;
        return action;
    }
    
    public static Action toggleRedPiranha(final Item item) {
        final Action action = new Action();
        action.type = 22;
        action.item = item;
        return action;
    }
    
    public static Action pipePropChange(final Item item, final Warp warp, final Warp oldWarp, final Warp newWarp) {
        final Action action = new Action();
        action.type = 14;
        action.item = item;
        action.warp = warp;
        action.oldWarp = oldWarp;
        action.newWarp = newWarp;
        return action;
    }
    
    public static Action levelPropChange(final String oldName1, final String oldName2, final boolean oldTimed, final int oldTime, final int oldLevelType, final int oldLevelEndIndex, final int oldNextLevel, final boolean oldLakitu, final boolean oldFish, final boolean oldBullets, final boolean oldBandW, final int oldDeathLevel, final int oldDeathID, final int oldPipeColor, final boolean oldAutoScrolling, final int oldTexturePack, final String newName1, final String newName2, final boolean newTimed, final int newTime, final int newLevelType, final int newLevelEndIndex, final int newNextLevel, final boolean newLakitu, final boolean newFish, final boolean newBullets, final boolean newBandW, final int newDeathLevel, final int newDeathID, final int newPipeColor, final boolean newAutoScrolling, final int newTexturePack) {
        final Action action = new Action();
        action.type = 8;
        action.oldName1 = oldName1;
        action.oldName2 = oldName2;
        action.oldTimed = oldTimed;
        action.oldTime = oldTime;
        action.oldLevelType = oldLevelType;
        action.oldLevelEndIndex = oldLevelEndIndex;
        action.oldNextLevel = oldNextLevel;
        action.oldLakitu = oldLakitu;
        action.oldFish = oldFish;
        action.oldBullets = oldBullets;
        action.oldBandW = oldBandW;
        action.oldDeathLevel = oldDeathLevel;
        action.oldDeathID = oldDeathID;
        action.oldPipeColor = oldPipeColor;
        action.oldAutoScrolling = oldAutoScrolling;
        action.oldTexturePack = oldTexturePack;
        action.newName1 = newName1;
        action.newName2 = newName2;
        action.newTimed = newTimed;
        action.newTime = newTime;
        action.newLevelType = newLevelType;
        action.newLevelEndIndex = newLevelEndIndex;
        action.newNextLevel = newNextLevel;
        action.newLakitu = newLakitu;
        action.newFish = newFish;
        action.newBullets = newBullets;
        action.newBandW = newBandW;
        action.newDeathLevel = newDeathLevel;
        action.newDeathID = newDeathID;
        action.newPipeColor = newPipeColor;
        action.newTexturePack = newTexturePack;
        return action;
    }
    
    public void undoAction(final BuilderFrame frame) {
        if (this.type == 0) {
            this.item.removeFromLevel();
            this.item.insertInLevel(this.oldXTile, this.oldYTile);
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            if (this.item.isWarpable()) {
                this.item.warp = this.warp;
                this.warp.item = this.item;
            }
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Moved " + this.item.name + " from (" + this.newXTile + "," + this.newYTile + ") to (" + this.oldXTile + "," + this.oldYTile + ")");
        }
        else if (this.type == 1) {
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.removeFromLevel();
            if (this.item.isWarpable()) {
                frame.levelPanel.level.outgoingWarps.remove(this.item.warp);
                frame.levelPanel.level.incomingWarps.remove(this.item.warp);
                this.item.warp = this.warp;
                this.warp.item = this.item;
            }
            frame.changePropertiesPanel(new PropertiesPanel(frame, 1, null));
            frame.statusBar.setText("Removed " + this.item.name + " from (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 2) {
            this.item.insertInLevel(this.item.xTile, this.item.yTile);
            if (this.item.isWarpable()) {
                this.item.warp = this.warp;
                this.warp.item = this.item;
                if (this.warp.incoming) {
                    frame.levelPanel.level.insertIncomingWarp(this.warp);
                }
                if (this.warp.outgoing) {
                    frame.levelPanel.level.outgoingWarps.add(this.warp);
                }
            }
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Reinserted " + this.item.name + " at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 3) {
            frame.levelPanel.findColumn(this.column);
            frame.levelPanel.level.removeAtColumn(this.column);
            frame.changePropertiesPanel(new PropertiesPanel(frame, 1, null));
            frame.statusBar.setText("Removed a column.");
        }
        else if (this.type == 4) {
            frame.levelPanel.level.insertAtColumn(this.column);
            frame.levelPanel.findColumn(this.column);
            frame.changePropertiesPanel(new PropertiesPanel(frame, 1, null));
            frame.statusBar.setText("Inserted a column.");
        }
        else if (this.type == 9) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.flip = !this.item.flip;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Reversed " + this.item.name + " at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 10) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.coinCount = this.oldCount;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the coin count of the block at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 11) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.shifted = !this.item.shifted;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Reversed " + this.item.name + " at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 12) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.warp.destLevelNumber = this.oldDestLvl;
            this.item.warp.destWarpID = this.oldDestID;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the beanstalk block properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 13) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.warp.destLevelNumber = this.oldDestLvl;
            this.item.displayWarpNumber = this.oldDisplay;
            this.item.warpPipeColor = this.oldColor;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the warp zone pipe properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 15) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.flip = this.oldFlip;
            this.item.speedBoost = this.oldSpeedBoost;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the firebar properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 16) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.singlePlatform = !this.item.singlePlatform;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the platform properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 21) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.poison = !this.item.poison;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the powerup properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 22) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.redPiranha = !this.item.redPiranha;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed piranha red status at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 23) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.superSpring = !this.item.superSpring;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed spring super status at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 17) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.fireballColor = this.oldColor;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the Podoboo properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 18) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.oscOffset = this.oldOffset;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the oscillating platform properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 14) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            if (this.oldWarp.incoming && !this.newWarp.incoming) {
                this.warp.incoming = true;
                this.warp.sourceLevelNumber = this.oldWarp.sourceLevelNumber;
                this.warp.sourceWarpID = this.oldWarp.sourceWarpID;
                frame.levelPanel.level.insertIncomingWarp(this.warp);
            }
            else if (!this.oldWarp.incoming && this.newWarp.incoming) {
                this.warp.incoming = false;
                frame.levelPanel.level.incomingWarps.remove(this.warp);
            }
            if (this.oldWarp.outgoing && !this.newWarp.outgoing) {
                this.warp.outgoing = true;
                this.warp.destLevelNumber = this.oldWarp.destLevelNumber;
                this.warp.destWarpID = this.oldWarp.destWarpID;
                frame.levelPanel.level.outgoingWarps.add(this.warp);
            }
            else if (!this.oldWarp.outgoing && this.newWarp.outgoing) {
                this.warp.outgoing = false;
                frame.levelPanel.level.outgoingWarps.remove(this.warp);
            }
            else if (this.oldWarp.outgoing && this.newWarp.outgoing) {
                this.warp.destLevelNumber = this.oldWarp.destLevelNumber;
                this.warp.destWarpID = this.oldWarp.destWarpID;
            }
            this.item.warp = this.warp;
            this.warp.item = this.item;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the pipe properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 8) {
            final Level l = frame.levelPanel.level;
            l.name1 = this.oldName1;
            l.name2 = this.oldName2;
            l.timedLevel = this.oldTimed;
            l.levelTime = this.oldTime;
            l.levelType = this.oldLevelType;
            l.changeLevelEndType(this.oldLevelEndIndex, true);
            l.nextLevelNumber = this.oldNextLevel;
            l.hasLakitu = this.oldLakitu;
            l.hasFlyingFish = this.oldFish;
            l.hasBullets = this.oldBullets;
            l.blackAndWhite = this.oldBandW;
            l.cliffDestLevel = this.oldDeathLevel;
            l.cliffDestID = this.oldDeathID;
            l.pipeColor = this.oldPipeColor;
            l.autoScrolling = this.oldAutoScrolling;
            l.texturePack = this.oldTexturePack;
            frame.levelPanel.setLevelScheme();
            frame.changePropertiesPanel(new PropertiesPanel(frame, 2, null));
            frame.statusBar.setText("Changed the properties of level " + (l.levelNumber + 1));
        }
        else if (this.type == 19) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.checkpointType = this.oldType;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the checkpoint type at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 20) {
            Item newItem = null;
            String msg = null;
            if (this.item.character == '~') {
                newItem = frame.pipesPanel.topWChomp.item.copy();
                msg = "Added a Piranha to a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == ';') {
                newItem = frame.pipesPanel.topWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u0110') {
                newItem = frame.pipesPanel.bottomWChomp.item.copy();
                msg = "Added a Piranha to a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '¿') {
                newItem = frame.pipesPanel.bottomWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u2021') {
                newItem = frame.pipesPanel.leftWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '!') {
                newItem = frame.pipesPanel.leftWChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u00ee') {
                newItem = frame.pipesPanel.rightWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u2663') {
                newItem = frame.pipesPanel.rightWChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            newItem.warp = this.item.warp;
            this.item.removeFromLevel();
            newItem.insertInLevel(this.item.xTile, this.item.yTile);
            frame.changePropertiesPanel(new PropertiesPanel(frame, 5, newItem));
            frame.statusBar.setText(msg);
        }
        frame.levelPanel.placeLevel();
        frame.levelPanel.repaint();
    }
    
    public void redoAction(final BuilderFrame frame) {
        if (this.type == 0) {
            this.item.removeFromLevel();
            this.item.insertInLevel(this.newXTile, this.newYTile);
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.warp = this.warp;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Moved a " + this.item.name + " from (" + this.oldXTile + "," + this.oldYTile + ") to (" + this.newXTile + "," + this.newYTile + ")");
        }
        else if (this.type == 1) {
            this.item.insertInLevel(this.item.xTile, this.item.yTile);
            this.item.warp = this.warp;
            if (this.item.isWarpable()) {
                if (this.warp.outgoing) {
                    frame.levelPanel.level.outgoingWarps.add(this.warp);
                }
                if (this.warp.incoming) {
                    frame.levelPanel.level.insertIncomingWarp(this.warp);
                }
            }
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Reinserted " + this.item.name + " at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 2) {
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.removeFromLevel();
            if (this.item.isWarpable()) {
                frame.levelPanel.level.incomingWarps.remove(this.item.warp);
                frame.levelPanel.level.outgoingWarps.remove(this.item.warp);
            }
            this.item.warp = this.warp;
            frame.changePropertiesPanel(new PropertiesPanel(frame, 1, null));
            frame.statusBar.setText("Removed a " + this.item.name + " from (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 3) {
            frame.levelPanel.level.insertAtColumn(this.column);
            frame.levelPanel.findColumn(this.column);
            frame.changePropertiesPanel(new PropertiesPanel(frame, 1, null));
            frame.statusBar.setText("Inserted a column.");
        }
        else if (this.type == 4) {
            frame.levelPanel.findColumn(this.column);
            frame.levelPanel.level.removeAtColumn(this.column);
            frame.changePropertiesPanel(new PropertiesPanel(frame, 1, null));
            frame.statusBar.setText("Removed a column.");
        }
        else if (this.type == 9) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.flip = !this.item.flip;
            frame.changePropertiesPanel(new PropertiesPanel(frame, 3, this.item));
            frame.statusBar.setText("Reversed the " + this.item.name + " at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 10) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.coinCount = this.newCount;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the coin count of the block at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 11) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.shifted = !this.item.shifted;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Reversed the " + this.item.name + " at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 12) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.warp.destLevelNumber = this.newDestLvl;
            this.item.warp.destWarpID = this.newDestID;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the beanstalk block properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 13) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.warp.destLevelNumber = this.newDestLvl;
            this.item.displayWarpNumber = this.newDisplay;
            this.item.warpPipeColor = this.newColor;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the warp zone pipe properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 15) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.flip = this.newFlip;
            this.item.speedBoost = this.newSpeedBoost;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the firebar properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 16) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.singlePlatform = !this.item.singlePlatform;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the platform properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 21) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.poison = !this.item.poison;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the powerup properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 22) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.redPiranha = !this.item.redPiranha;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed piranha red status at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 23) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.superSpring = !this.item.superSpring;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed spring super status at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 17) {
            this.item = frame.levelPanel.level.items[this.item.requiredRow][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.fireballColor = this.newColor;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the Podoboo properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 18) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.oscOffset = this.newOffset;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the oscillating platform properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 14) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            if (!this.oldWarp.incoming && this.newWarp.incoming) {
                this.warp.incoming = true;
                this.warp.sourceLevelNumber = this.newWarp.sourceLevelNumber;
                this.warp.sourceWarpID = this.newWarp.sourceWarpID;
                frame.levelPanel.level.insertIncomingWarp(this.warp);
            }
            else if (this.oldWarp.incoming && !this.newWarp.incoming) {
                this.warp.incoming = false;
                frame.levelPanel.level.incomingWarps.remove(this.warp);
            }
            if (!this.oldWarp.outgoing && this.newWarp.outgoing) {
                this.warp.outgoing = true;
                this.warp.destLevelNumber = this.newWarp.destLevelNumber;
                this.warp.destWarpID = this.newWarp.destWarpID;
                frame.levelPanel.level.outgoingWarps.add(this.warp);
            }
            else if (this.oldWarp.outgoing && !this.newWarp.outgoing) {
                this.warp.outgoing = false;
                frame.levelPanel.level.outgoingWarps.remove(this.warp);
            }
            else if (this.oldWarp.outgoing && this.newWarp.outgoing) {
                this.warp.destLevelNumber = this.newWarp.destLevelNumber;
                this.warp.destWarpID = this.newWarp.destWarpID;
            }
            this.item.warp = this.warp;
            this.warp.item = this.item;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the pipe properties at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 8) {
            final Level l = frame.levelPanel.level;
            l.name1 = this.newName1;
            l.name2 = this.newName2;
            l.timedLevel = this.newTimed;
            l.levelTime = this.newTime;
            l.levelType = this.newLevelType;
            l.changeLevelEndType(this.newLevelEndIndex, true);
            l.nextLevelNumber = this.newNextLevel;
            l.hasLakitu = this.newLakitu;
            l.hasFlyingFish = this.newFish;
            l.hasBullets = this.newBullets;
            l.blackAndWhite = this.newBandW;
            l.cliffDestLevel = this.newDeathLevel;
            l.cliffDestID = this.newDeathID;
            l.pipeColor = this.newPipeColor;
            l.autoScrolling = this.newAutoScrolling;
            l.texturePack = this.newTexturePack;
            frame.levelPanel.setLevelScheme();
            frame.changePropertiesPanel(new PropertiesPanel(frame, 2, null));
            frame.statusBar.setText("Changed the properties of level " + (l.levelNumber + 1));
        }
        else if (this.type == 19) {
            this.item = frame.levelPanel.level.items[this.item.yTile][this.item.xTile];
            if (!frame.levelPanel.itemOnScreen(this.item)) {
                frame.levelPanel.findItem(this.item);
            }
            this.item.checkpointType = this.newType;
            frame.changePropertiesPanel(new PropertiesPanel(frame, this.item.propertiesType, this.item));
            frame.statusBar.setText("Changed the checkpoint type at (" + this.item.xTile + "," + this.item.yTile + ")");
        }
        else if (this.type == 20) {
            Item newItem = null;
            String msg = null;
            if (this.item.character == ';') {
                newItem = frame.pipesPanel.topWChomp.item.copy();
                msg = "Added a Piranha to a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '~') {
                newItem = frame.pipesPanel.topWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '¿') {
                newItem = frame.pipesPanel.bottomWChomp.item.copy();
                msg = "Added a Piranha to a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u0110') {
                newItem = frame.pipesPanel.bottomWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u2021') {
                newItem = frame.pipesPanel.leftWChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '!') {
                newItem = frame.pipesPanel.leftWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u00ee') {
                newItem = frame.pipesPanel.rightWChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            else if (this.item.character == '\u2663') {
                newItem = frame.pipesPanel.rightWOChomp.item.copy();
                msg = "Removed a Piranha from a pipe at (" + this.item.xTile + "," + this.item.yTile + ")";
            }
            newItem.warp = this.item.warp;
            this.item.removeFromLevel();
            newItem.insertInLevel(this.item.xTile, this.item.yTile);
            frame.changePropertiesPanel(new PropertiesPanel(frame, 5, newItem));
            frame.statusBar.setText(msg);
        }
        frame.levelPanel.repaint();
    }
}
