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

public class RedFish extends Sprite implements Enemy
{
    public static final int X_SPEED = 24;
    public static final int MAX_FLYING_X_SPEED_RIGHT = 80;
    public static final int MAX_FLYING_X_SPEED_LEFT = 48;
    public static final int MIN_FLYING_X_SPEED = 16;
    public static final int FLYING_Y_VELOCITY = -320;
    public static final int FLYING_FISH_GRAVITY = 240;
    public static final int MIN_PADDING_FROM_SCREEN_X_START = 16;
    public boolean flying;
    private boolean leftStarting;
    private boolean smushed;
    
    public RedFish(final Game game, final ImageIcon[] images, final boolean leftStarting, final boolean flying) {
        super(game, images);
        this.leftStarting = leftStarting;
        if (!(this.flying = flying)) {
            if (leftStarting) {
                this.xVel = -24.0;
                this.flip = false;
            }
            else {
                this.xVel = 24.0;
                this.flip = true;
            }
        }
        else {
            this.xPos = game.level.leftMostX + game.rand.nextInt(Game.renderWidth - 16) + 16;
            this.yPos = Game.renderHeight - 1;
            this.finalizePosition();
            this.yVel = -320.0;
            if (game.rand.nextInt(2) == 0) {
                this.xVel = game.rand.nextInt(65) + 16;
            }
            else {
                this.xVel = -(game.rand.nextInt(33) + 16);
            }
            this.xVel += game.mario.xVel / 2.0;
            this.flip = (this.xVel > 0.0);
        }
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled) {
            return new RedFish(this.game, this.images, this.leftStarting, this.flying);
        }
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (!this.bumpKilled) {
            this.xPos += this.xVel * delta / 1000.0;
            if (this.flying) {
                this.applyGravity(delta, Level.TERMINAL_VELOCITY);
            }
        }
        else if (!this.flying) {
            this.applyGravity(delta, Enemy.BUMP_KILL_WATER_GRAVITY);
        }
        else {
            this.applyGravity(delta, Level.GRAVITY);
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
        }
        this.collidable = false;
        this.bumpKilled = true;
        if (!this.flying) {
            this.yVel = -96.0;
        }
        else {
            this.yVel = -240.0;
        }
        final Mario mario = this.game.mario;
        mario.points += 200;
        this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 1));
    }
    
    @Override
    public void smushed(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.smushed || !this.flying) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(7);
            final Mario mario = this.game.mario;
            mario.points += 200;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 1));
        }
        this.smushed = true;
        this.collidable = false;
        this.ticks = 0.0;
        this.xVel = 0.0;
        if (this.yVel < 0.0) {
            this.yVel = 0.0;
        }
    }
    
    @Override
    public void xCollided() {
    }
}
