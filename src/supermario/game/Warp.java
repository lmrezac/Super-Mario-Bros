// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.Rectangle;

public class Warp
{
    public static final int WARP_TYPE_CHECKPOINT = -1;
    public static final int WARP_TYPE_STANDARD_MARIO_START = 0;
    public static final int WARP_TYPE_TUBE_FACING_LEFT = 1;
    public static final int WARP_TYPE_TUBE_FACING_RIGHT = 2;
    public static final int WARP_TYPE_TUBE_FACING_UP = 3;
    public static final int WARP_TYPE_TUBE_FACING_DOWN = 4;
    public static final int WARP_TYPE_WARP_ZONE = 5;
    public static final int WARP_TYPE_NEXT_LEVEL_WARP = 6;
    public static final int WARP_TYPE_BEANSTALK_DEPARTURE = 7;
    public static final int WARP_TYPE_BEANSTALK_ARRIVAL = 8;
    public static final int WARP_TYPE_THIN_AIR = 9;
    public static final int STANDARD_STARTING_WARP_ID = 0;
    public static final int LEVEL_ENDING_WARP_ID = 1000;
    public static final int GAME_OVER_LEVEL_NUMBER = 999;
    public static final int GAME_OVER_WARP_ID = 999;
    public static final int DISPLAY_WARP_ZONE_EMPTY = -1;
    public int sourceLevelNumber;
    public int sourceWarpID;
    public int destLevelNumber;
    public int destWarpID;
    public int xTile;
    public int yTile;
    public int type;
    public boolean outgoing;
    public boolean incoming;
    
    public Warp(final boolean outgoing, final boolean incoming, final int sourceLevelNumber, final int sourceWarpID, final int destLevelNumber, final int destWarpID, final int xTile, final int yTile, final int type) {
        this.outgoing = outgoing;
        this.incoming = incoming;
        this.sourceLevelNumber = sourceLevelNumber;
        this.sourceWarpID = sourceWarpID;
        this.destLevelNumber = destLevelNumber;
        this.destWarpID = destWarpID;
        this.xTile = xTile;
        this.yTile = yTile;
        this.type = type;
    }
    
    public Rectangle getRectangle() {
        if (this.type == 1) {
            return new Rectangle(this.xTile * 8 - 1, this.yTile * 8, 1, 32);
        }
        if (this.type == 2) {
            return new Rectangle(this.xTile * 8 + 24 + 1, this.yTile * 8, 1, 32);
        }
        if (this.type == 3 || this.type == 5) {
            return new Rectangle(this.xTile * 8 + 16 - 1, this.yTile * 8 - 1, 2, 1);
        }
        if (this.type == 4) {
            return new Rectangle(this.xTile * 8 + 16 - 1, (this.yTile + 4) * 8 + 1, 2, 1);
        }
        return null;
    }
}
