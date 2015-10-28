// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class GrayFish extends Sprite implements Enemy
{
    private boolean zigZag;
    private boolean headingUp;
    private boolean leftStarting;
    private int centerY;
    public static final int X_SPEED = 16;
    public static final int Y_SPEED = 8;
    public static final double DIST_FROM_START = 20.0;
    
    public GrayFish(final Game game, final ImageIcon[] images, final boolean leftStarting, final boolean zigZag) {
        super(game, images);
        this.leftStarting = leftStarting;
        if (leftStarting) {
            this.xVel = -16.0;
            this.flip = false;
        }
        else {
            this.xVel = 16.0;
            this.flip = true;
        }
        this.zigZag = zigZag;
        this.headingUp = true;
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled) {
            return new GrayFish(this.game, this.images, this.leftStarting, this.zigZag);
        }
        return null;
    }
    
    public void activate() {
        this.centerY = this.y + this.height / 2;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (!this.bumpKilled) {
            this.xPos += this.xVel * delta / 1000.0;
            if (this.zigZag) {
                if (this.headingUp) {
                    this.yPos -= 8.0 * delta / 1000.0;
                    if (this.yPos <= this.centerY - 20.0) {
                        this.yPos = this.centerY - 20.0;
                        this.headingUp = false;
                    }
                }
                else {
                    this.yPos += 8.0 * delta / 1000.0;
                    if (this.yPos >= this.centerY + 20.0) {
                        this.yPos = this.centerY + 20.0;
                        this.headingUp = true;
                    }
                }
            }
        }
        else {
            this.applyGravity(delta, Enemy.BUMP_KILL_WATER_GRAVITY);
        }
        if (this.bumpKilled) {
            this.imageIndex = 2;
        }
        else if (this.ticks > 150.0) {
            if (this.imageIndex == 0) {
                this.imageIndex = 1;
            }
            else if (this.imageIndex == 1) {
                this.imageIndex = 0;
            }
            this.ticks -= 150.0;
        }
        this.finalizePosition();
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.bumpKilled) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
            final Mario mario = this.game.mario;
            mario.points += 200;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 1));
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
