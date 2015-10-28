// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import supermario.game.Level;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Hammer extends Sprite implements Enemy
{
    private double theta;
    private boolean thrown;
    private Sprite thrower;
    public static final int ROTATION_PERIOD = 500;
    public static final int LAUNCH_Y_VELOCITY = -192;
    public static final int X_VELOCITY = 64;
    
    public Hammer(final Game game, final ImageIcon[] images, final Sprite thrower) {
        super(game, images);
        this.thrower = thrower;
        this.avoidedCollisionCols = 4;
        this.avoidedCollisionRowsOnTop = 1;
        this.avoidedCollisionRowsOnBottom = 7;
        this.imageIndex = 0;
        this.thrown = false;
        this.theta = 0.0;
        this.collidable = false;
        this.xPos = thrower.xPos;
        this.yPos = thrower.yPos;
        this.x = thrower.x;
        this.y = thrower.y;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (!this.thrown) {
            if (this.thrower.y >= Game.yTiles * 8) {
                this.thrown();
            }
            else if (this.thrower.flip) {
                this.flip = true;
                this.xPos = this.thrower.xPos + this.thrower.width - this.width;
            }
            else {
                this.flip = false;
                this.xPos = this.thrower.xPos - 1.0;
            }
            this.yPos = this.thrower.yPos - 6.0;
        }
        else {
            this.updateTheta();
            this.xPos += this.xVel * delta / 1000.0;
            this.applyGravity(delta, Level.GRAVITY / 4.0);
        }
        if (this.yPos > Game.renderHeight) {
            this.visible = false;
        }
        this.finalizePosition();
    }
    
    public void thrown() {
        this.yVel = -192.0;
        this.collidable = true;
        this.thrown = true;
        if (this.thrower.flip) {
            this.xVel = 64.0;
            this.flip = true;
        }
        else {
            this.xVel = -64.0;
            this.flip = false;
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        this.transform.rotate(this.theta, this.width / 2, this.height / 2);
        if (this.flip) {
            this.transform.translate(this.width, 0.0);
            this.transform.scale(-1.0, 1.0);
        }
        g2D.drawImage(this.images[this.imageIndex].getImage(), this.transform, null);
    }
    
    private Rectangle getRotatedRectangle() {
        final Rectangle stdRect = new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.width - this.avoidedCollisionCols * 2, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
        final int yPoint = stdRect.y + stdRect.height / 2;
        final int dist = this.y + this.height / 2 - yPoint;
        final int newX = (int)Math.round(this.getXCenter() + Math.sin(this.theta) * dist);
        final int newY = (int)Math.round(this.y + this.height / 2 - Math.cos(this.theta) * dist);
        return new Rectangle(newX - stdRect.width / 2, newY - stdRect.height / 2, 8, 8);
    }
    
    private void updateTheta() {
        this.ticks %= 500.0;
        if (!this.flip) {
            this.theta = -(this.ticks / 500.0) * 2.0 * 3.141592653589793;
        }
        else {
            this.theta = this.ticks / 500.0 * 2.0 * 3.141592653589793;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return this.getRotatedRectangle();
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
