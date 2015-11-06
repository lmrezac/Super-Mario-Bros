// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.effects.Points;
import supermario.game.sprites.Mario;
import supermario.game.sprites.friends.FireballFriend;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.sprites.misc.Pipe;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;
@SuppressWarnings("unused")
public class Chomper extends Sprite implements Enemy
{
    private Pipe pipe;
    private boolean redPiranha;
    private int generalImageIndex;
    private int direction;
    private static final int FACES_LEFT = 0;
    private static final int FACES_RIGHT = 1;
    private static final int FACES_UP = 2;
    private static final int FACES_DOWN = 3;
    private boolean active;
    private boolean rising;
    private boolean falling;
    private boolean atPeak;
    private final int ATTACK_DELAY = 1200;
    private final int TIME_AT_PEAK = 1200;
    private final int TIME_AT_PEAK_RED = 1000;
    private final int SPEED = 32;
    private final int DISTANCE_IN_RANGE = 8;
    private int normalY;
    private int normalX;
    private double attackDelayTicks;
    private double timeAtPeak;
    private double timeAtPeakThreshold;
    
    public Chomper(final Game game, final ImageIcon[] images, final Pipe pipe, final boolean lightLevel, final boolean redPiranha) {
        super(game, images);
        this.pipe = pipe;
        if (pipe.type == 2 || pipe.type == 11) {
            this.direction = 2;
        }
        else if (pipe.type == 0) {
            this.direction = 0;
        }
        else if (pipe.type == 1) {
            this.direction = 1;
        }
        else if (pipe.type == 3) {
            this.direction = 3;
        }
        if (redPiranha) {
            this.timeAtPeakThreshold = 1000.0;
        }
        else {
            this.timeAtPeakThreshold = 1200.0;
        }
        this.redPiranha = redPiranha;
        this.avoidedCollisionCols = 2;
        this.attackDelayTicks = 900.0;
        if (this.direction == 2 || this.direction == 3) {
            this.yVel = 32.0;
            if (redPiranha) {
                this.yVel *= 2.0;
            }
            this.x = pipe.x + 8;
            this.xPos = this.x;
            if (this.direction == 2) {
                this.yPos = pipe.y;
                this.avoidedCollisionRowsOnTop = 7;
            }
            else {
                this.vFlip = true;
                this.yPos = pipe.y + pipe.height - this.height;
                this.avoidedCollisionRowsOnBottom = 7;
            }
        }
        else if (this.direction == 0 || this.direction == 1) {
            this.xVel = 32.0;
            if (redPiranha) {
                this.xVel *= 2.0;
            }
            this.y = pipe.y + 8;
            this.yPos = this.y;
            this.avoidedCollisionCols = 7;
            if (this.direction == 0) {
                this.xPos = pipe.x;
            }
            else {
                this.flip = true;
                this.xPos = pipe.x + pipe.width - this.width;
            }
        }
        this.normalY = (int)this.yPos;
        this.normalX = (int)this.xPos;
        this.active = false;
        if (pipe.type == 11) {
            this.attackDelayTicks = 1200.0;
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
        if ((this.redPiranha || this.marioInRange()) && this.isOnScreen()) {
            this.attackDelayTicks += delta;
        }
        if (!this.active && this.attackDelayTicks > 1200.0) {
            this.active = true;
            this.rising = true;
            this.atPeak = false;
            this.falling = false;
            this.timeAtPeak = 0.0;
        }
        if (this.active) {
            if (this.rising && this.direction == 2 && this.yPos > this.normalY - this.height) {
                this.yPos -= this.yVel * delta / 1000.0;
            }
            else if (this.rising && this.direction == 3 && this.yPos < this.normalY + this.height) {
                this.yPos += this.yVel * delta / 1000.0;
            }
            else if (this.rising && this.direction == 0 && this.xPos > this.normalX - this.width) {
                this.xPos -= this.xVel * delta / 1000.0;
            }
            else if (this.rising && this.direction == 1 && this.xPos < this.normalX + this.width) {
                this.xPos += this.xVel * delta / 1000.0;
            }
            if (this.rising && this.direction == 2 && this.yPos <= this.normalY - this.height) {
                this.yPos = this.normalY - this.height;
                this.rising = false;
                this.atPeak = true;
                this.timeAtPeak = 0.0;
            }
            else if (this.rising && this.direction == 3 && this.yPos >= this.normalY + this.height) {
                this.yPos = this.normalY + this.height;
                this.rising = false;
                this.atPeak = true;
                this.timeAtPeak = 0.0;
            }
            else if (this.rising && this.direction == 0 && this.xPos <= this.normalX - this.width) {
                this.xPos = this.normalX - this.width;
                this.rising = false;
                this.atPeak = true;
                this.timeAtPeak = 0.0;
            }
            else if (this.rising && this.direction == 1 && this.xPos >= this.normalX + this.width) {
                this.xPos = this.normalX + this.width;
                this.rising = false;
                this.atPeak = true;
                this.timeAtPeak = 0.0;
            }
            if (this.atPeak && this.timeAtPeak <= this.timeAtPeakThreshold) {
                this.timeAtPeak += delta;
            }
            else if (this.atPeak && this.timeAtPeak > this.timeAtPeakThreshold) {
                this.atPeak = false;
                this.falling = true;
            }
            if (this.falling && this.direction == 2 && this.yPos < this.normalY) {
                this.yPos += this.yVel * delta / 1000.0;
            }
            else if (this.falling && this.direction == 3 && this.yPos > this.normalY) {
                this.yPos -= this.yVel * delta / 1000.0;
            }
            else if (this.falling && this.direction == 0 && this.xPos < this.normalX) {
                this.xPos += this.xVel * delta / 1000.0;
            }
            else if (this.falling && this.direction == 1 && this.xPos > this.normalX) {
                this.xPos -= this.xVel * delta / 1000.0;
            }
            if (this.falling && ((this.direction == 2 && this.yPos >= this.normalY) || (this.direction == 3 && this.yPos <= this.normalY))) {
                this.yPos = this.normalY;
                this.falling = false;
                this.active = false;
                this.attackDelayTicks = 0.0;
            }
            else if (this.falling && ((this.direction == 0 && this.xPos >= this.normalX) || (this.direction == 1 && this.xPos <= this.normalX))) {
                this.xPos = this.normalX;
                this.falling = false;
                this.active = false;
                this.attackDelayTicks = 0.0;
            }
        }
        if (this.active && this.ticks > 150.0) {
            if (this.generalImageIndex == 0) {
                this.generalImageIndex = 1;
            }
            else {
                this.generalImageIndex = 0;
            }
            this.imageIndex = this.generalImageIndex;
            if (this.redPiranha) {
                this.imageIndex += 2;
            }
           /* else if (!this.lightLevel) {
                this.imageIndex += 2;
            }*/
            this.ticks = 0.0;
        }
        this.finalizePosition();
    }
    
    private boolean marioInRange() {
        final int marioCenter = this.game.mario.getXCenter();
        return marioCenter < this.pipe.x - 8 || marioCenter > this.pipe.x + this.pipe.width + 8;
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.width - this.avoidedCollisionCols * 2, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.bumpKilled || !this.active) {
            return;
        }
        if (killer instanceof Hammer || killer instanceof FireballFriend || killer instanceof FireballEnemy || (killer instanceof Mario && this.game.mario.hasStar())) {
            if (this.isOnScreen()) {
                this.game.audio.play(10);
            }
            final Mario mario = this.game.mario;
            mario.points += 200;
            this.game.level.effectsToAdd.add(new Points(this.game, this.x + this.width / 2, this.y, 1));
            this.collidable = false;
            this.bumpKilled = true;
            this.visible = false;
        }
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
    }
}
