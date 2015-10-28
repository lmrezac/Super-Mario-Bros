// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.friends;

import supermario.game.Level;
import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import supermario.game.Tile;
import java.awt.Rectangle;
import supermario.game.interfaces.Constants;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Block;
import supermario.game.interfaces.Friend;
import supermario.game.Sprite;

public class Star extends Sprite implements Friend
{
    public static double BOUNCING_GRAVITY;
    public static double BOUNCE_VELOCITY;
    public static final int IMAGE_CHANGE_DELAY = 40;
    private boolean absorbed;
    private boolean growing;
    private boolean lightLevel;
    private int startingY;
    private Block block;
    
    public Star(final Game game, final ImageIcon[] images, final Block block) {
        super(game, images);
        this.block = block;
        if (game.level.levelType == 1) {
            this.lightLevel = false;
        }
        else {
            this.lightLevel = true;
        }
        final Sprite tempSprite = (Sprite)block;
        this.xPos = tempSprite.x;
        this.yPos = tempSprite.y;
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
        this.startingY = (int)this.yPos;
        this.growing = true;
        game.audio.play(9);
        this.avoidedCollisionCols = 1;
        this.absorbed = false;
        this.xVel = 0.0;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.growing) {
            this.yPos -= 16.0 * delta / 1000.0;
            if (this.yPos < this.startingY - 16) {
                this.yPos = this.startingY - 16;
                this.xVel = Constants.FRIEND_X_SPEED;
                this.growing = false;
            }
        }
        else {
            this.applyGravity(delta, Star.BOUNCING_GRAVITY);
            this.xPos += this.xVel * delta / 1000.0;
            this.checkForCollisions();
            this.checkForUnsettledTiles();
            if (!this.absorbed) {
                this.checkForBounce();
            }
        }
        this.finalizePosition();
        this.grounded = this.isGrounded();
        if (this.ticks > 40.0) {
            if (this.imageIndex == 0) {
                if (this.lightLevel) {
                    this.imageIndex = 1;
                }
                else {
                    this.imageIndex = 2;
                }
            }
            else if (this.imageIndex == 1 || this.imageIndex == 2) {
                this.imageIndex = 3;
            }
            else if (this.imageIndex == 3) {
                this.imageIndex = 4;
            }
            else if (this.imageIndex == 4) {
                this.imageIndex = 0;
            }
            this.ticks -= 40.0;
        }
    }
    
    private void checkForBounce() {
        if (this.grounded) {
            this.yVel = Star.BOUNCE_VELOCITY;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x + this.avoidedCollisionCols, this.y, this.width - this.avoidedCollisionCols * 2, this.height);
    }
    
    @Override
    public void checkForUnsettledTiles() {
        final Rectangle rect = this.getContactRectangle();
        if (rect.y + rect.height + 1 >= Game.renderHeight) {
            return;
        }
        int minX = rect.x;
        if (minX < 0) {
            minX = 0;
        }
        int maxX = rect.x + rect.width;
        if (maxX > this.game.level.xTiles * 8) {
            maxX = this.game.level.xTiles * 8;
        }
        for (int i = minX; i < maxX; ++i) {
            final Tile tempTile = this.game.level.getTileAtPixel(i, rect.y + rect.height + 1);
            if (tempTile.sprite != null && !tempTile.sprite.settled && tempTile.sprite.justHit) {
                this.bumped();
                break;
            }
        }
    }
    
    @Override
    public void bumped() {
        if (this.game.level.levelType == 3) {
            this.yVel = -80.0;
        }
        else {
            this.yVel = -240.0;
        }
    }
    
    @Override
    public Rectangle getContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void xCollided() {
        if (!this.isBetweenTwoSolidTiles()) {
            this.xVel = -this.xVel;
        }
    }
    
    @Override
    public void absorbed() {
        if (this.absorbed) {
            return;
        }
        if (this.growing && this.game.mario.y + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom > ((Sprite)this.block).y) {
            return;
        }
        if (!this.growing && this.game.mario.pixelsToMove != 0.0) {
            return;
        }
        final Mario mario = this.game.mario;
        mario.points += 1000;
        this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 5));
        this.game.audio.play(1);
        this.visible = false;
        this.game.mario.caughtStar();
        this.absorbed = true;
        this.visible = false;
    }
    
    static {
        Star.BOUNCING_GRAVITY = Level.GRAVITY / 10.0 * 3.0;
        Star.BOUNCE_VELOCITY = -176.0;
    }
}
