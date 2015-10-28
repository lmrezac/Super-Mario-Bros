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
import supermario.game.interfaces.Block;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class PoisonMushroom extends Sprite implements Enemy
{
    private Block block;
    private boolean growing;
    private int startingY;
    
    public PoisonMushroom(final Game game, final ImageIcon[] images, final Block block) {
        super(game, images);
        this.block = block;
        final Sprite tempSprite = (Sprite)block;
        this.xPos = tempSprite.x;
        this.yPos = tempSprite.y;
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
        this.startingY = (int)this.yPos;
        this.growing = true;
        game.audio.play(9);
        this.xVel = 0.0;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.growing && !this.bumpKilled) {
            this.yPos -= 16.0 * delta / 1000.0;
            if (this.yPos < this.startingY - 16) {
                this.yPos = this.startingY - 16;
                if (this.game.mario.swimming) {
                    this.xVel = Constants.FRIEND_X_SPEED / 4.0 * 3.0;
                }
                else {
                    this.xVel = Constants.FRIEND_X_SPEED;
                }
                this.growing = false;
            }
        }
        else {
            if (this.game.mario.swimming) {
                this.applyGravity(delta, Level.WATER_GRAVITY);
            }
            else {
                this.applyGravity(delta, Level.GRAVITY);
            }
            this.xPos += this.xVel * delta / 1000.0;
            if (this.collidable) {
                this.checkForCollisions();
                this.checkForUnsettledTiles();
            }
        }
        this.finalizePosition();
        if (this.collidable) {
            this.grounded = this.isGrounded();
        }
        if (this.bumpKilled) {
            this.vFlip = true;
        }
    }
    
    @Override
    public void xCollided() {
        if (this.grounded && !this.isBetweenTwoSolidTiles()) {
            this.xVel = -this.xVel;
        }
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
        if (this.game.level.levelType == 3) {
            this.yVel = -80.0;
        }
        else {
            this.yVel = -240.0;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
            if (killer instanceof Shelled && ((Shelled)killer).isDangerous()) {
                ((Shelled)killer).shellKilled();
            }
            else {
                final Mario mario = this.game.mario;
                mario.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
            }
        }
        this.collidable = false;
        this.bumpKilled = true;
        this.growing = false;
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
}
