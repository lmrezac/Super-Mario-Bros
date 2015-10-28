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

public class SpinyThrower extends Sprite implements Enemy
{
    private static final int LEFT_MOST_X_BEFORE_APPEARANCE;
    private boolean ducking;
    private boolean waiting;
    private boolean inStartingPosition;
    public static final int DUCK_TIME = 500;
    private static final int MIN_THROW_DELAY = 1000;
    private static final int MAX_THROW_DELAY = 3000;
    private static final int DEAD_TIME = 6000;
    private static final int RESET_X_SPEED = 80;
    private static final int SWEEP_PERIOD = 5000;
    private final int SWEEP_AMPLITUDE;
    private double sweepTicks;
    private int throwDelay;
    
    public SpinyThrower(final Game game, final ImageIcon[] images) {
        super(game, images);
        this.SWEEP_AMPLITUDE = Game.renderWidth / 2 - this.width / 2;
        this.yPos = 32.0;
        this.xPos = game.mario.xPos + Game.renderWidth + this.width;
        this.finalizePosition();
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.game.level.leftMostX < SpinyThrower.LEFT_MOST_X_BEFORE_APPEARANCE) {
            this.resetThrower();
        }
        if (!this.bumpKilled) {
            if (!this.inStartingPosition) {
                this.xPos -= 80.0 * delta / 1000.0;
                if (this.xPos <= this.game.level.exactLeftMostX + Game.renderWidth / 2 - this.width / 2) {
                    this.xPos = this.game.level.exactLeftMostX + Game.renderWidth / 2 - this.width / 2;
                    this.inStartingPosition = true;
                    this.setNextThrowTime();
                }
            }
            else if (!this.game.level.levelEndPresent) {
                this.sweepTicks = (this.sweepTicks + delta) % 5000.0;
                final double theta = this.sweepTicks / 5000.0 * 3.141592653589793 * 2.0;
                this.xPos = Math.round(this.game.level.exactLeftMostX + Game.renderWidth / 2 + this.SWEEP_AMPLITUDE * -Math.sin(theta) - this.width / 2);
                this.flip = (this.xPos + this.width / 2 >= this.game.level.exactLeftMostX + Game.renderWidth / 2);
                this.xVel = this.SWEEP_AMPLITUDE * 8 * -Math.cos(theta);
            }
        }
        else {
            this.applyGravity(delta, Level.GRAVITY);
        }
        if (!this.game.level.levelEndPresent) {
            if (this.bumpKilled && this.ticks >= 6000.0) {
                this.ticks = 0.0;
                this.resetThrower();
            }
            else if (this.waiting && this.ticks > this.throwDelay) {
                this.waiting = false;
                this.ducking = true;
                this.ticks = 0.0;
            }
            else if (this.ducking && this.ticks > 500.0) {
                this.ducking = false;
                this.throwSpiny();
                this.ticks = 0.0;
            }
        }
        else if (this.bumpKilled || this.xPos >= this.game.level.exactLeftMostX + Game.renderWidth) {
            if (this.bumpKilled && (this.yPos > Game.renderHeight || !this.bumpKilled)) {
                this.visible = false;
            }
        }
        else {
            this.xPos -= 80.0 * delta / 1000.0;
            if (this.xPos + this.width < this.game.level.exactLeftMostX) {
                this.visible = false;
            }
        }
        if (this.waiting) {
            this.imageIndex = 0;
        }
        else if (this.ducking) {
            this.imageIndex = 1;
        }
        else if (this.bumpKilled) {
            this.imageIndex = 2;
        }
        this.finalizePosition();
    }
    
    private void throwSpiny() {
        this.game.level.spritesToAdd.add(new Spiny(this.game, this.game.textures.getSpinyTextures(), this));
        this.setNextThrowTime();
    }
    
    private void setNextThrowTime() {
        this.throwDelay = this.game.rand.nextInt(2001) + 1000;
        this.waiting = true;
    }
    
    private void resetThrower() {
        this.yPos = 32.0;
        this.xPos = this.game.level.leftMostX + Game.renderWidth + this.width;
        this.finalizePosition();
        this.bumpKilled = false;
        this.collidable = true;
        this.imageIndex = 0;
        this.inStartingPosition = false;
        this.sweepTicks = 0.0;
    }
    
    @Override
    public Rectangle getRectangle() {
        if (this.ducking) {
            return new Rectangle(this.x, this.y + 9, this.width, this.height - 9);
        }
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.bumpKilled) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
            final Mario mario = this.game.mario;
            mario.points += 800;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 4));
        }
        this.collidable = false;
        this.bumpKilled = true;
        this.waiting = false;
        this.ducking = false;
        this.collidable = false;
        this.ticks = 0.0;
        this.xVel = 0.0;
    }
    
    @Override
    public void smushed(final Sprite killer) {
        this.bumpKilled(killer);
    }
    
    @Override
    public void xCollided() {
    }
    
    static {
        LEFT_MOST_X_BEFORE_APPEARANCE = Game.renderWidth / 4;
    }
}
