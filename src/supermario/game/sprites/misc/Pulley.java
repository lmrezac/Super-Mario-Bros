// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class Pulley extends Sprite
{
    public Platform leftChild;
    public Platform rightChild;
    private int leftLength;
    private int rightLength;
    private boolean cordSnapped;
    public static final int MAX_LINE_LENGTH = 128;
    public static final double PULLEY_ACCELERATION = 80.0;
    public static final double PULLEY_DECELERATION = -240.0;
    public static final double PULLEY_ACCELERATION_SNAPPED = 240.0;
    public static final double PULLEY_TERMINAL_VELOCITY_UNSNAPPED = 120.0;
    public static final double PULLEY_TERMINAL_VELOCITY_SNAPPED = 160.0;
    
    public Pulley(final Game game, final ImageIcon[] images, final Platform leftChild, final Platform rightChild) {
        super(game, images);
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.imageIndex = 0;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        final Platform platform = this.game.mario.onPlatform();
        if (!this.cordSnapped && platform == this.leftChild && this.leftChild.marioStandingOnThisPlatform()) {
            final Platform leftChild = this.leftChild;
            leftChild.yVel += 80.0 * delta / 1000.0;
            this.rightChild.yVel = -this.leftChild.yVel;
        }
        else if (!this.cordSnapped && platform == this.rightChild && this.rightChild.marioStandingOnThisPlatform()) {
            final Platform rightChild = this.rightChild;
            rightChild.yVel += 80.0 * delta / 1000.0;
            this.leftChild.yVel = -this.rightChild.yVel;
        }
        else if (!this.cordSnapped) {
            if (this.leftChild.yVel > 0.0) {
                final Platform leftChild2 = this.leftChild;
                leftChild2.yVel += -240.0 * delta / 1000.0;
                if (this.leftChild.yVel < 0.0) {
                    this.leftChild.yVel = 0.0;
                }
                this.rightChild.yVel = -this.leftChild.yVel;
            }
            else if (this.rightChild.yVel > 0.0) {
                final Platform rightChild2 = this.rightChild;
                rightChild2.yVel += -240.0 * delta / 1000.0;
                if (this.rightChild.yVel < 0.0) {
                    this.rightChild.yVel = 0.0;
                }
                this.leftChild.yVel = -this.rightChild.yVel;
            }
        }
        if (!this.cordSnapped) {
            this.leftLength = this.leftChild.y - (this.y + this.height);
            this.rightLength = this.rightChild.y - (this.y + this.height);
            if (this.leftLength > 128) {
                this.leftLength = 128;
                this.cordSnapped = true;
            }
            else if (this.rightLength > 128) {
                this.rightLength = 128;
                this.cordSnapped = true;
            }
            if (this.cordSnapped) {
                this.leftChild.yVel = 0.0;
                this.rightChild.yVel = 0.0;
                this.game.level.effectsToAdd.add(new Points(this.game, this.game.mario.getXCenter(), this.game.mario.y, 5));
                final Mario mario = this.game.mario;
                mario.points += 1000;
            }
        }
        else {
            final Platform leftChild3 = this.leftChild;
            leftChild3.yVel += 240.0 * delta / 1000.0;
            if (this.leftChild.y > Game.yTiles * 8 * 2) {
                this.leftChild.yVel = 0.0;
            }
            final Platform rightChild3 = this.rightChild;
            rightChild3.yVel += 240.0 * delta / 1000.0;
            if (this.rightChild.y > Game.yTiles * 8 * 2) {
                this.rightChild.yVel = 0.0;
            }
        }
        this.setTerminalVelocities();
    }
    
    private void setTerminalVelocities() {
        double terminalVelocity = 120.0;
        if (this.cordSnapped) {
            terminalVelocity = 160.0;
        }
        if (this.leftChild.yVel < -terminalVelocity) {
            this.leftChild.yVel = -terminalVelocity;
        }
        else if (this.leftChild.yVel > terminalVelocity) {
            this.leftChild.yVel = terminalVelocity;
        }
        if (this.rightChild.yVel < -terminalVelocity) {
            this.rightChild.yVel = -terminalVelocity;
        }
        else if (this.rightChild.yVel > terminalVelocity) {
            this.rightChild.yVel = terminalVelocity;
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        g2D.drawImage(this.images[this.imageIndex].getImage(), this.transform, null);
        g2D.setColor(this.game.textures.pink);
        g2D.fillRect(this.x + 7, this.y + this.height, 2, this.leftLength);
        g2D.fillRect(this.x + this.width - 7 - 2, this.y + this.height, 2, this.rightLength);
    }
    
    @Override
    public Rectangle getRectangle() {
        return null;
    }
}
