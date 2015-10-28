// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import supermario.Utilities;
import java.awt.Graphics2D;
import supermario.game.Tile;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.sprites.enemies.Chomper;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;
import supermario.game.Sprite;

public class Pipe extends Sprite implements Warpable
{
    public static final int PIPE_COLOR_ORANGE = 4;
    public static final int PIPE_COLOR_BLUE = 3;
    public static final int PIPE_COLOR_GREEN = 1;
    public static final int PIPE_COLOR_DEFAULT = 0;
    public static final int PIPE_COLOR_WHITE = 2;
    public static final int WARP_ZONE_PIPE_COLOR_ORANGE = 0;
    public static final int WARP_ZONE_PIPE_COLOR_GREEN = 1;
    public static final int WARP_ZONE_PIPE_COLOR_WHITE = 2;
    public static final int WARP_ZONE_PIPE_COLOR_BLUE = 3;
    public static final int PIRANHA_LIGHT = 0;
    public static final int PIRANHA_DARK = 1;
    public static final int PIRANHA_RED = 2;
    public Warp warp;
    public static final int TYPE_LEFT_ENTRANCE = 0;
    public static final int TYPE_RIGHT_ENTRANCE = 1;
    public static final int TYPE_TOP_ENTRANCE = 2;
    public static final int TYPE_BOTTOM_ENTRANCE = 3;
    public static final int TYPE_TOP_SECTION = 4;
    public static final int TYPE_SIDE_SECTION = 5;
    public static final int TYPE_LEFT_CONNECTOR = 6;
    public static final int TYPE_RIGHT_CONNECTOR = 7;
    public static final int TYPE_TOP_CONNECTOR = 8;
    public static final int TYPE_BOTTOM_CONNECTOR = 9;
    public static final int TYPE_DOUBLE_CONNECTOR = 10;
    public static final int TYPE_WARP_ZONE = 11;
    public static final int PIPE_TYPE_COUNT = 12;
    public static final int WARP_ZONE_PROXIMITY = 56;
    public boolean hasChomper;
    public boolean idDisplayable;
    public boolean redChomper;
    private Chomper chomper;
    public int type;
    
    public Pipe(final Game game, final ImageIcon[] images, final int type, final boolean hasChomper, final boolean redChomper) {
        super(game, images);
        if (!hasChomper && redChomper) {
            throw new IllegalStateException("Can't have a red pirhana in a pipe with no pirhana.");
        }
        this.type = type;
        this.hasChomper = hasChomper;
        this.redChomper = redChomper;
    }
    
    @Override
    public Sprite reset() {
        final Pipe pipe = new Pipe(this.game, this.images, this.type, this.hasChomper, false);
        pipe.warp = this.warp;
        return pipe;
    }
    
    public void activate(final Tile tile) {
        if (this.hasChomper) {
            boolean lightLevel = true;
            if (this.game.level.levelType == 1 || this.game.level.levelType == 2) {
                lightLevel = false;
            }
            this.chomper = new Chomper(this.game, this.game.textures.getChomperTextures(this.type == 2 || this.type == 3 || this.type == 11), this, lightLevel, this.redChomper);
            this.game.level.sprites.add(this.chomper);
        }
    }
    
    private void checkChomperDisappear() {
        if (this.type == 11) {
            final int marioCenter = this.game.mario.getXCenter();
            final int pipeCenter = this.x + this.width / 2;
            if (Math.abs(marioCenter - pipeCenter) < 56) {
                this.chomper.visible = false;
                this.chomper.collidable = false;
                this.idDisplayable = true;
            }
        }
    }
    
    @Override
    public void update(final double delta) {
        this.checkChomperDisappear();
        if (!this.game.mario.transitioning && this.warp != null && this.warp.outgoing && this.warp.getRectangle().intersects(this.game.mario.getRectangle())) {
            if ((this.warp.type == 3 || this.warp.type == 5) && this.game.input.downDown) {
                this.game.mario.warp = this.warp;
                this.game.mario.ticks = 0.0;
                this.game.mario.invincible = false;
                this.game.mario.visible = true;
                this.game.mario.transitioning = true;
                this.game.mario.transitionState = 6;
                this.game.mario.shooting = false;
                this.game.audio.play(5);
            }
            else if (this.warp.type == 4 && this.game.input.upDown && this.game.mario.yVel < 0.0) {
                this.game.mario.warp = this.warp;
                this.game.mario.ticks = 0.0;
                this.game.mario.invincible = false;
                this.game.mario.visible = true;
                this.game.mario.transitioning = true;
                this.game.mario.transitionState = 8;
                this.game.mario.shooting = false;
                this.game.audio.play(5);
            }
            else if (this.warp.type == 1 && this.game.mario.grounded && this.game.input.rightDown) {
                this.game.mario.warp = this.warp;
                this.game.mario.ticks = 0.0;
                this.game.mario.transitioning = true;
                this.game.mario.invincible = false;
                this.game.mario.visible = true;
                this.game.mario.transitionState = 9;
                this.game.audio.play(5);
                this.game.mario.yPos = this.warp.yTile * 8;
                this.game.mario.y = (int)Math.round(this.game.mario.yPos);
                this.game.mario.shooting = false;
                if (!this.game.mario.isWalking()) {
                    this.game.mario.timeSinceLastWalkChange = 0.0;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 7;
                    }
                    else {
                        this.game.mario.spriteState = 1;
                    }
                }
            }
            else if (this.warp.type == 2 && this.game.mario.grounded && this.game.input.leftDown) {
                this.game.mario.warp = this.warp;
                this.game.mario.ticks = 0.0;
                this.game.mario.invincible = false;
                this.game.mario.visible = true;
                this.game.mario.transitioning = true;
                this.game.mario.transitionState = 10;
                this.game.audio.play(5);
                this.game.mario.yPos = this.warp.yTile * 8;
                this.game.mario.y = (int)Math.round(this.game.mario.yPos);
                this.game.mario.shooting = false;
                if (!this.game.mario.isWalking()) {
                    this.game.mario.timeSinceLastWalkChange = 0.0;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 7;
                    }
                    else {
                        this.game.mario.spriteState = 1;
                    }
                }
            }
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (this.type == 11 && this.idDisplayable && this.warp.sourceWarpID != -1) {
            final String displayID = String.valueOf(this.warp.sourceWarpID);
            int xCenter = this.x + this.width / 2;
            xCenter -= (int)Math.round(8.0 * (displayID.length() / 2.0));
            Utilities.drawTextAtPixels(g2D, displayID, xCenter, this.y - 24);
        }
        super.draw(g2D);
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
}
