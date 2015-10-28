// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import supermario.Utilities;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class Platform extends Sprite
{
    public static final int PLATFORM_LONG_REP_UP = 0;
    public static final int PLATFORM_LONG_REP_DOWN = 1;
    public static final int PLATFORM_LONG_OSC_VERTICALLY = 2;
    public static final int PLATFORM_LONG_OSC_HORIZONTALLY = 3;
    public static final int PLATFORM_LONG_FALLING = 4;
    public static final int PLATFORM_SHORT_REP_UP = 5;
    public static final int PLATFORM_SHORT_REP_DOWN = 6;
    public static final int PLATFORM_SHORT_OSC_VERTICALLY = 7;
    public static final int PLATFORM_SHORT_OSC_HORIZONTALLY = 8;
    public static final int PLATFORM_SHORT_FALLING = 9;
    public static final int PLATFORM_EXTRA_SHORT_REP_UP = 10;
    public static final int PLATFORM_EXTRA_SHORT_REP_DOWN = 11;
    public static final int PLATFORM_CLOUD_CARRIER = 13;
    public static final int PLATFORM_PULLEY_LONG = 14;
    public static final int PLATFORM_PULLEY_SHORT = 15;
    public static double FALLING_PLATFORM_Y_VELOCITY;
    public static double CLOUD_CARRIER_X_VELOCITY;
    public static final int OSC_Y_AMPLITUDE = 9;
    public static final int OSC_X_AMPLITUDE = 6;
    public static int TOTAL_OSC_Y_PERIOD;
    public static int TOTAL_OSC_X_PERIOD;
    public static final int EXTRA_SHORT_PLATFORM_TILES = 3;
    public static final int SHORT_PLATFORM_TILES = 4;
    public static final int LONG_PLATFORM_TILES = 6;
    public static final int CARRIER_PLATFORM_TILES = 6;
    public double oscPeriod;
    public double oscYCenter;
    public double oscXCenter;
    public double xChange;
    public double yChange;
    public float startingOffset;
    public int type;
    public static double REPEATING_Y_VELOCITY;
    public boolean carrierActive;
    public boolean carrierFinished;
    public boolean single;
    
    public Platform(final Game game, final ImageIcon[] images, final int type, final boolean single) {
        super(game, images);
        this.type = type;
        this.single = single;
        this.setRepeatingVelocity();
        if (type == 13) {
            this.xVel = 0.0;
            this.yVel = 0.0;
        }
        this.imageIndex = 0;
    }
    
    private void setRepeatingVelocity() {
        if (this.type == 0 || this.type == 5 || this.type == 10) {
            this.xVel = 0.0;
            this.yVel = -Platform.REPEATING_Y_VELOCITY;
        }
        else if (this.type == 1 || this.type == 6 || this.type == 11) {
            this.xVel = 0.0;
            this.yVel = Platform.REPEATING_Y_VELOCITY;
        }
        else if (this.type == 3 || this.type == 8) {
            this.yVel = 0.0;
        }
    }
    
    @Override
    public Sprite reset() {
        return new Platform(this.game, this.images, this.type, this.single);
    }
    
    public void activate() {
        if (!this.single && (this.type == 1 || this.type == 0 || this.type == 11 || this.type == 10 || this.type == 6 || this.type == 5)) {
            this.yPos = 0.0;
            Platform platform2 = new Platform(this.game, this.images, this.type, this.single);
            platform2.xPos = this.xPos;
            platform2.yPos = Game.renderHeight / 2;
            platform2.update(1.0);
            this.game.level.platforms.add(platform2);
            platform2 = new Platform(this.game, this.images, this.type, this.single);
            platform2.xPos = this.xPos;
            platform2.yPos = Game.renderHeight;
            platform2.update(1.0);
            this.game.level.platforms.add(platform2);
        }
        else if (this.type == 7 || this.type == 2) {
            this.oscYCenter = this.yPos;
        }
        else if (this.type == 3 || this.type == 8) {
            this.oscXCenter = this.xPos;
        }
        else if (this.type == 14 || this.type == 15) {
            final Platform sibling = new Platform(this.game, this.images, this.type, this.single);
            sibling.xPos = this.xPos + this.width + 64.0;
            if (this.type == 15) {
                final Platform platform3 = sibling;
                platform3.xPos += 16.0;
            }
            sibling.yPos = this.yPos + 32.0;
            sibling.finalizePosition();
            this.game.level.platforms.add(sibling);
            final Pulley pulley = new Pulley(this.game, new ImageIcon[] { this.game.textures.pulley }, this, sibling);
            pulley.xPos = this.xPos + this.width / 2 - 8.0;
            pulley.yPos = this.yPos - 64.0 - 1.0;
            pulley.finalizePosition();
            this.game.level.pulleys.add(pulley);
            this.finalizePosition();
        }
        this.update(1.0);
        this.game.level.platformsToAdd.add(this);
    }
    
    @Override
    public void update(final double delta) {
        this.lastX = this.x;
        this.lastY = this.yPos;
        if (this.isRepeatingPlatform()) {
            this.setRepeatingVelocity();
            this.xPos += this.xVel * delta / 1000.0;
            this.yPos += this.yVel * delta / 1000.0;
            this.checkForReposition();
        }
        else if (this.type == 14 || this.type == 15) {
            this.yPos += this.yVel * delta / 1000.0;
        }
        else if (this.isOscillatingPlatform()) {
            this.setOscillatingPosition();
        }
        else if (this.isFallingPlatform()) {
            final Platform platform = this.game.mario.onPlatform();
            if (platform == this && this.marioStandingOnThisPlatform()) {
                this.yVel = Platform.FALLING_PLATFORM_Y_VELOCITY;
                this.yPos += this.yVel * delta / 1000.0;
            }
            else {
                this.yVel = 0.0;
            }
        }
        else if (this.type == 13) {
            if (!this.carrierActive && !this.carrierFinished) {
                final Platform platform = this.game.mario.onPlatform();
                if (platform == this && this.marioStandingOnThisPlatform() && this.game.mario.yVel > 0.0 && this.game.mario.lastY + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom <= platform.y) {
                    this.carrierActive = true;
                    this.xVel = Platform.CLOUD_CARRIER_X_VELOCITY;
                }
            }
            if (this.carrierActive && !this.carrierFinished) {
                this.xPos += this.xVel * delta / 1000.0;
                this.xChange = Math.round(this.xPos) - this.lastX;
                if (this.isColliding() || this.isInPathOfPlatform() || (this.game.level.maxTravelX >= 0 && this.x + this.width > this.game.level.maxTravelX) || this.x + this.width > this.game.level.xTiles * 8) {
                    this.carrierFinished = true;
                    this.xPos -= this.xVel * delta / 1000.0;
                    this.xVel = 0.0;
                    this.xChange = 0.0;
                }
            }
        }
        this.finalizePosition();
    }
    
    private boolean isInPathOfPlatform() {
        for (final Platform p : this.game.level.platforms) {
            final int lmp = this.leftMostPoint(p);
            if (this.x + this.width > lmp && this.x < lmp && this.withinVerticalRange(p)) {
                return true;
            }
        }
        return false;
    }
    
    private int leftMostPoint(final Platform p) {
        if (p.isVerticalMotionPlatform() || p.isPulleyPlatform()) {
            return p.x - 16;
        }
        if (p.isHorizontallyOscillatingPlatform()) {
            return (p.xTile - 6 - 2) * 8;
        }
        return this.game.level.xTiles * 8;
    }
    
    private boolean withinVerticalRange(final Platform p) {
        final int padding = this.game.mario.tilesHeight;
        return p.isRepeatingPlatform() || (p.isFallingPlatform() && this.yTile >= p.yTile - padding) || (p.isVerticallyOscillatingPlatform() && this.yTile >= p.yTile - 9 - padding && this.yTile <= p.yTile + 9 + padding) || (p.isPulleyPlatform() && this.yTile >= p.yTile - 6 - padding) || (p.isHorizontallyOscillatingPlatform() && this.yTile >= p.yTile - padding && this.yTile <= p.yTile + padding);
    }
    
    public boolean marioStandingOnThisPlatform() {
        if (this.game.mario.transitioning && this.game.mario.transitionState == 3) {
            return false;
        }
        final Rectangle marioRect = this.game.mario.getRectangle();
        return marioRect.y + marioRect.height <= this.y + this.height && this.game.mario.lastX + this.game.mario.width - this.game.mario.avoidedCollisionCols > this.x && this.game.mario.lastX + this.game.mario.avoidedCollisionCols < this.x + this.width;
    }
    
    public void drawLine(final Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.setStroke(new BasicStroke(2.0f));
        g2D.drawLine(this.getXCenter(), 0, this.getXCenter(), Game.renderHeight);
    }
    
    private void setOscillatingPosition() {
        if (this.type == 2 || this.type == 7) {
            this.oscPeriod = Utilities.getYPlatformOscillationPeriod();
            final double theta = (this.oscPeriod + Platform.TOTAL_OSC_Y_PERIOD * this.startingOffset) % Platform.TOTAL_OSC_Y_PERIOD / Platform.TOTAL_OSC_Y_PERIOD * 3.141592653589793 * 2.0;
            this.yPos = Math.round(this.oscYCenter + 72.0 * Math.sin(theta));
            this.yChange = this.yPos - this.lastY;
            this.yVel = 72.0 * Math.cos(theta);
        }
        else if (this.type == 3 || this.type == 8) {
            this.oscPeriod = Utilities.getXPlatformOscillationPeriod();
            final double theta = (this.oscPeriod + Platform.TOTAL_OSC_X_PERIOD * this.startingOffset) % Platform.TOTAL_OSC_X_PERIOD / Platform.TOTAL_OSC_X_PERIOD * 3.141592653589793 * 2.0;
            this.xPos = Math.round(this.oscXCenter + 48.0 * -Math.sin(theta));
            this.xChange = this.xPos - this.lastX;
            this.xVel = 48.0 * -Math.cos(theta);
        }
    }
    
    private void checkForReposition() {
        if (this.single) {
            if ((this.type == 1 || this.type == 6 || this.type == 11) && this.yPos > Game.renderHeight + this.game.mario.height) {
                this.yPos = -this.height;
            }
            else if ((this.type == 0 || this.type == 5 || this.type == 10) && this.yPos < 0 - this.height) {
                this.yPos = Game.renderHeight + this.game.mario.height;
            }
        }
        else if ((this.type == 1 || this.type == 6 || this.type == 11) && this.yPos > Game.renderHeight + Game.renderHeight / 4) {
            this.yPos = -Game.renderHeight / 4;
        }
        else if ((this.type == 0 || this.type == 5 || this.type == 10) && this.yPos < 0 - this.height) {
            this.yPos += Game.renderHeight / 2 * 3;
        }
    }
    
    public boolean isExtraShortPlatform() {
        return this.type == 11 || this.type == 10;
    }
    
    public boolean isRepeatingPlatform() {
        return this.type == 1 || this.type == 6 || this.type == 11 || (this.type == 0 || this.type == 5 || this.type == 10);
    }
    
    public boolean isPulleyPlatform() {
        return this.type == 15 || this.type == 14;
    }
    
    public boolean isFallingPlatform() {
        return this.type == 4 || this.type == 9;
    }
    
    public boolean isOscillatingPlatform() {
        return this.type == 3 || this.type == 2 || this.type == 8 || this.type == 7;
    }
    
    public boolean isHorizontallyOscillatingPlatform() {
        return this.type == 3 || this.type == 8;
    }
    
    public boolean isVerticallyOscillatingPlatform() {
        return this.type == 2 || this.type == 7;
    }
    
    public boolean isVerticalMotionPlatform() {
        return this.type == 11 || this.type == 10 || this.type == 4 || (this.type == 1 || this.type == 0 || this.type == 9) || (this.type == 6 || this.type == 5 || this.type == 2 || this.type == 7);
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    static {
        Platform.FALLING_PLATFORM_Y_VELOCITY = 136.0;
        Platform.CLOUD_CARRIER_X_VELOCITY = 64.0;
        Platform.TOTAL_OSC_Y_PERIOD = 6000;
        Platform.TOTAL_OSC_X_PERIOD = 4500;
        Platform.REPEATING_Y_VELOCITY = 64.0;
    }
}
