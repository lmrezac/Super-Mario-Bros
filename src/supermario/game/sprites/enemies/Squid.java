// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import java.awt.Rectangle;
import supermario.game.Level;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Squid extends Sprite implements Enemy
{
    public static final int FALL_VELOCITY = 32;
    public static final int RISE_MAX_VELOCITY = 120;
    public static final int RISE_ACCELERATION = 320;
    public static final int X_VELOCITY = 80;
    public static final int CONTRACT_TIME = 300;
    public static final int RELAX_TIME = 1000;
    public static final int CHANGE_DIRECTION_MIN_TIME = 500;
    public static final int LOWER_Y_BOUND = 184;
    public static final int UPPER_Y_BOUND = 32;
    public static final int REQ_DISTANCE = 40;
    public static final int WITHIN_FOLLOW_DISTANCE = 80;
    private double distance;
    private double directionTicks;
    private double xSign;
    private boolean contracted;
    private boolean falling;
    
    public Squid(final Game game, final ImageIcon[] images) {
        super(game, images);
        this.contracted = false;
        this.falling = true;
        this.avoidedCollisionRowsOnBottom = 8;
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled) {
            return new Squid(this.game, this.images);
        }
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.bumpKilled) {
            this.imageIndex = 2;
            this.yVel += Level.WATER_GRAVITY * delta / 1000.0;
            this.yPos += this.yVel * delta / 1000.0;
        }
        else {
            if (this.contracted) {
                this.imageIndex = 1;
                if (this.ticks > 300.0) {
                    this.contracted = false;
                    this.ticks -= 300.0;
                }
            }
            else {
                this.imageIndex = 0;
                if (this.ticks > 1000.0) {
                    this.contracted = true;
                    this.ticks -= 1000.0;
                }
            }
            if (this.falling) {
                this.directionTicks += delta;
                this.yPos += 32.0 * delta / 1000.0;
                this.distance += 32.0 * delta / 1000.0;
                if (this.rightAboveMario() || (this.yPos - this.avoidedCollisionRowsOnBottom >= this.game.mario.yPos && this.directionTicks >= 500.0) || (this.game.mario.yPos <= this.yPos - this.avoidedCollisionRowsOnBottom && this.distance > 40.0)) {
                    this.falling = false;
                    this.directionTicks = 0.0;
                    this.yVel = 0.0;
                    this.distance = 0.0;
                    this.xSign = this.getXSign();
                }
            }
            else {
                this.yVel -= 320.0 * delta / 1000.0;
                if (this.yVel < -120.0) {
                    this.yVel = -120.0;
                }
                final double change = this.yVel * delta / 1000.0;
                this.yPos += change;
                this.xPos += change * this.xSign;
                this.distance -= change;
                if (this.distance > 40.0) {
                    this.falling = true;
                    this.distance = 0.0;
                    this.xVel = 0.0;
                }
            }
            if (this.yPos < 32.0) {
                this.yPos = 32.0;
            }
        }
        this.finalizePosition();
    }
    
    private boolean rightAboveMario() {
        final Rectangle marioRect = this.game.mario.getRectangle();
        return this.game.mario.grounded && this.xPos <= this.game.mario.xPos + this.game.mario.width - this.avoidedCollisionCols && this.xPos + this.width >= this.game.mario.xPos + this.avoidedCollisionCols && this.yPos + this.height - this.avoidedCollisionRowsOnBottom >= marioRect.y - 2;
    }
    
    private double getXSign() {
        final double marioCenter = this.game.mario.xPos + this.game.mario.width / 2;
        final double squidCenter = this.xPos + this.width / 2;
        if (marioCenter > squidCenter && marioCenter - squidCenter <= 80.0) {
            return -1.0;
        }
        if (marioCenter < squidCenter && squidCenter - marioCenter <= 80.0) {
            return 1.0;
        }
        return 1.0;
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.bumpKilled) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
            final Mario mario = this.game.mario;
            mario.points += 400;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 2));
        }
        this.collidable = false;
        this.bumpKilled = true;
        this.yVel = -96.0;
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
    }
}
