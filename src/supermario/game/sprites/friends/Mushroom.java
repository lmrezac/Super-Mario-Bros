// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.friends;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.Level;
import supermario.game.Sprite;
import supermario.game.interfaces.Block;
import supermario.game.interfaces.Constants;
import supermario.game.interfaces.Friend;
import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;

public class Mushroom extends Sprite implements Friend
{
    private Block block;
    private boolean growing;
    private boolean absorbed;
    private boolean poison;
    private int startingY;
    
    public Mushroom(final Game game, final ImageIcon[] images, final Block block, final boolean poison) {
        super(game, images);
        this.block = block;
        this.poison = poison;
        final Sprite tempSprite = (Sprite)block;
        this.xPos = tempSprite.x;
        this.yPos = tempSprite.y;
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
        this.startingY = (int)this.yPos;
        this.growing = true;
        game.audio.play(9);
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
            this.checkForCollisions();
            this.checkForUnsettledTiles();
        }
        this.finalizePosition();
        this.grounded = this.isGrounded();
    }
    
    @Override
    public Rectangle getContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void xCollided() {
        if (this.grounded && !this.isBetweenTwoSolidTiles()) {
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
        if (this.poison) {
            this.game.mario.attacked(false, null);
            this.visible = false;
            this.absorbed = true;
        }
        else {
            final Mario mario = this.game.mario;
            mario.points += 1000;
            this.game.level.effectsToAdd.add(new Points(this.game, this.x + this.width / 2, this.y, 5));
            this.visible = false;
            this.game.audio.play(1);
            this.absorbed = true;
            this.visible = false;
            if (!this.game.mario.isLarge()) {
                this.game.mario.grow(false);
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
        if (this.game.mario.getXCenter() <= this.getXCenter()) {
            this.xVel *= ((this.xVel > 0.0) ? 1.0 : -1.0);
        }
        else {
            this.xVel *= ((this.xVel < 0.0) ? 1.0 : -1.0);
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
