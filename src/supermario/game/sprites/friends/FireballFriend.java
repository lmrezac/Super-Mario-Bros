// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.friends;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.Level;
import supermario.game.Sprite;
import supermario.game.interfaces.Friend;
import supermario.game.sprites.effects.Firework;

public class FireballFriend extends Sprite implements Friend
{
    public static final double FIREBALL_BOUNCE_Y_VELOCITY = -176.0;
    public static final double FIREBALL_X_VELOCITY = 216.0;
    public static final int ROTATION_PERIOD = 280;
    private double imageTheta;
    
    public FireballFriend(final Game game, final ImageIcon[] images) {
        super(game, images);
        if (game.mario.flip) {
            this.xVel = -216.0;
            this.xPos = game.mario.xPos + game.mario.avoidedCollisionCols - this.width / 2;
        }
        else {
            this.xVel = 216.0;
            this.xPos = game.mario.xPos + game.mario.width - game.mario.avoidedCollisionCols * 2 - this.width / 2;
        }
        this.flip = (this.xVel < 0.0);
        this.yVel = Level.TERMINAL_VELOCITY;
        this.yPos = game.mario.yPos + 4.0;
        this.imageIndex = 0;
        this.finalizePosition();
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.xPos += this.xVel * delta / 1000.0;
        this.applyGravity(delta, Level.GRAVITY / 4.0 * 3.0);
        this.checkForRemoval();
        this.updateImageTheta();
        this.checkForCollisions();
        if (this.grounded) {
            this.bounce();
        }
        this.grounded = this.isGrounded();
        this.finalizePosition();
    }
    
    private void updateImageTheta() {
        this.ticks %= 280.0;
        this.imageTheta = this.ticks / 280.0 * 2.0 * 3.141592653589793;
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        if (this.flip) {
            this.transform.translate(this.width, 0.0);
            this.transform.scale(-1.0, 1.0);
        }
        this.transform.rotate(this.imageTheta, this.width / 2, this.height / 2);
        g2D.drawImage(this.images[this.imageIndex].getImage(), this.transform, null);
    }
    
    public void bounce() {
        if (this.yVel > 0.0) {
            this.yVel = -176.0;
        }
    }
    
    public void contact(final boolean killedAnEnemy) {
        this.collidable = false;
        this.visible = false;
        if (!killedAnEnemy && this.isOnScreen()) {
            this.game.audio.play(8);
        }
        this.game.level.effects.add(new Firework(this.game, this));
        this.xVel = 0.0;
        this.yVel = 0.0;
    }
    
    private void checkForRemoval() {
        if (this.x + this.width < this.game.level.leftMostX || this.x > this.game.level.leftMostX + Game.renderWidth || this.y > Game.renderHeight) {
            this.visible = false;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void xCollided() {
        if (this.xVel > 0.0) {
            this.xPos += 8.0;
        }
        else {
            this.xPos -= 8.0;
        }
        this.finalizePosition();
        this.contact(false);
    }
    
    @Override
    public void bumped() {
    }
    
    @Override
    public void absorbed() {
    }
}
