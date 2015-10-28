// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import supermario.game.interfaces.Shelled;
import java.awt.Rectangle;
import supermario.game.Level;
import supermario.game.interfaces.Constants;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.MarioFollower;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Spiny extends Sprite implements Enemy, MarioFollower
{
    private boolean egged;
    private boolean isLeftStarting;
    private boolean turnOnGrounded;
    private static final int INITIAL_Y_VELOCITY = -320;
    
    public Spiny(final Game game, final ImageIcon[] images, final SpinyThrower spinyThrower) {
        super(game, images);
        this.egged = true;
        this.xPos = spinyThrower.xPos;
        this.yPos = spinyThrower.yPos;
        this.finalizePosition();
        this.yVel = -320.0;
        this.xVel = 0.0;
        this.imageIndex = 0;
        this.avoidedCollisionCols = 0;
        this.avoidedCollisionRowsOnTop = 3;
        this.avoidedCollisionRowsOnBottom = 1;
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled && this.y < Game.renderHeight) {
            return new Spiny(this.game, this.images, this.isLeftStarting);
        }
        return null;
    }
    
    public Spiny(final Game game, final ImageIcon[] images, final boolean isLeftStarting) {
        super(game, images);
        this.isLeftStarting = isLeftStarting;
        this.egged = false;
        this.imageIndex = 1;
        if (isLeftStarting) {
            this.xVel = -Constants.ENEMY_X_SPEED;
            this.flip = false;
        }
        else {
            this.xVel = Constants.ENEMY_X_SPEED;
            this.flip = true;
        }
        this.avoidedCollisionCols = 0;
        this.avoidedCollisionRowsOnTop = 3;
        this.avoidedCollisionRowsOnBottom = 1;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.xPos += this.xVel * delta / 1000.0;
        this.applyGravity(delta, Level.GRAVITY);
        if (!this.bumpKilled && (!this.egged || this.yVel >= 0.0)) {
            this.checkForCollisions();
        }
        if (this.collidable) {
            this.checkForTurnAround();
            this.checkForUnsettledTiles();
        }
        this.grounded = this.isGrounded();
        if (!this.egged && !this.grounded && !this.injected && this.yVel >= Level.TERMINAL_VELOCITY) {
            this.turnOnGrounded = true;
        }
        if (this.grounded && this.egged && this.yVel > 0.0) {
            this.hatch();
            this.ticks = 0.0;
        }
        if (!this.egged && !this.bumpKilled && this.ticks > 150.0) {
            if (this.imageIndex == 1) {
                this.imageIndex = 2;
            }
            else {
                this.imageIndex = 1;
            }
            this.ticks -= 150.0;
        }
        else if (this.bumpKilled) {
            this.imageIndex = 3;
        }
        this.flip = (this.xVel > 0.0);
        this.finalizePosition();
    }
    
    private void hatch() {
        this.egged = false;
        this.imageIndex = 1;
        if (this.game.mario.getXCenter() < this.getXCenter()) {
            this.xVel = -Constants.ENEMY_X_SPEED;
            this.isLeftStarting = true;
        }
        else {
            this.flip = true;
            this.xVel = Constants.ENEMY_X_SPEED;
            this.isLeftStarting = false;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y + this.avoidedCollisionRowsOnTop, this.width, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        if (this.egged) {
            return new Rectangle(this.x + 1, this.y, this.width - 2, this.height);
        }
        return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.width - this.avoidedCollisionCols * 2, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.bumpKilled) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
            if (killer instanceof Shelled && ((Shelled)killer).isDangerous()) {
                ((Shelled)killer).shellKilled();
            }
            else {
                final Mario mario = this.game.mario;
                mario.points += 400;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 2));
            }
        }
        this.collidable = false;
        this.bumpKilled = true;
        this.yVel = -240.0;
        if (killer.xVel > 0.0) {
            this.xVel = -64.0;
        }
        else if (this.game.mario.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else if (this.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else {
            this.xVel = -64.0;
        }
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
        if (this.grounded) {
            this.xVel = -this.xVel;
        }
    }
    
    @Override
    public void checkForTurnAround() {
        if (!this.egged && this.turnOnGrounded && this.grounded) {
            if (this.game.mario.getXCenter() < this.getXCenter() && this.xVel > 0.0) {
                this.xVel = -this.xVel;
            }
            else if (this.game.mario.getXCenter() > this.getXCenter() && this.xVel < 0.0) {
                this.xVel = -this.xVel;
            }
            this.turnOnGrounded = false;
        }
    }
}
