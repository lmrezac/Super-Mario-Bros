// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.friends;

import supermario.game.sprites.effects.Points;
import java.awt.Rectangle;
import supermario.game.Level;
import supermario.game.interfaces.Constants;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Block;
import supermario.game.interfaces.Friend;
import supermario.game.Sprite;

public class Life extends Sprite implements Friend
{
    private Block block;
    private boolean absorbed;
    private boolean growing;
    private int startingY;
    
    public Life(final Game game, final ImageIcon[] images, final Block block) {
        super(game, images);
        this.block = block;
        final Sprite tempSprite = (Sprite)block;
        if (game.level.levelType == 1) {
            this.imageIndex = 1;
        }
        else {
            this.imageIndex = 0;
        }
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
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
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
        this.visible = false;
        this.game.audio.play(0);
        this.game.level.effectsToAdd.add(new Points(this.game, this.x + this.width / 2, this.y, 10));
        this.absorbed = true;
        this.visible = false;
        this.game.mario.extraLife();
    }
}
