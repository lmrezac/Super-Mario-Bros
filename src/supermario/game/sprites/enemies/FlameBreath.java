// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class FlameBreath extends Sprite implements Enemy
{
    public final int IMAGE_CHANGE_DELAY = 50;
    public final int MIN_Y_TILE = 8;
    public final int MAX_Y_TILE = 18;
    public final double X_VELOCITY = 80.0;
    public final double Y_VELOCITY = 80.0;
    private int desiredY;
    
    public FlameBreath(final Game game, final ImageIcon[] images, final Bowser bowser) {
        super(game, images);
        if (bowser != null) {
            if (bowser.flip) {
                this.flip = true;
                this.xVel = 80.0;
                this.xPos = bowser.x + bowser.width - 8;
            }
            else {
                this.flip = false;
                this.xVel = -80.0;
                this.xPos = bowser.x - this.width + 8;
            }
        }
        else {
            this.flip = false;
            this.xVel = -80.0;
            this.xPos = game.level.leftMostX + Game.renderWidth;
        }
        if (bowser != null) {
            this.yPos = bowser.yPos + 8.0;
            this.desiredY = this.getRandomY();
        }
        else {
            this.yPos = this.getRandomY();
            this.desiredY = (int)this.yPos;
        }
        this.finalizePosition();
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.yPos < this.desiredY) {
            this.yPos += 80.0 * delta / 1000.0;
            if (this.yPos > this.desiredY) {
                this.yPos = this.desiredY;
            }
        }
        else if (this.yPos > this.desiredY) {
            this.yPos -= 80.0 * delta / 1000.0;
            if (this.yPos < this.desiredY) {
                this.yPos = this.desiredY;
            }
        }
        this.xPos += this.xVel * delta / 1000.0;
        if (this.ticks > 50.0) {
            if (this.imageIndex == 0) {
                this.imageIndex = 1;
            }
            else if (this.imageIndex == 1) {
                this.imageIndex = 0;
            }
            this.ticks -= 50.0;
        }
        this.finalizePosition();
    }
    
    private int getRandomY() {
        return (this.game.rand.nextInt(11) + 8) * 8;
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        if (!this.flip) {
            return new Rectangle(this.x + 7, this.y, this.width - 7, this.height);
        }
        return new Rectangle(this.x, this.y, this.width - 7, this.height);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
    }
}
