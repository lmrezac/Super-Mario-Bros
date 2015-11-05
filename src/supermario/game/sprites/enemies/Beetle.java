// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.effects.Points;
import supermario.game.sprites.Mario;
import java.awt.Rectangle;
import supermario.game.Level;
import supermario.game.interfaces.Constants;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.MarioFollower;
import supermario.game.interfaces.Shelled;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Beetle extends Sprite implements Enemy, Shelled, MarioFollower
{
    public static final int STATE_NORMAL = 0;
    public static final int STATE_SHELLED = 1;
    public static final int STATE_SHELL_LAUNCHED = 2;
    public static final int LIGHT_BEETLE = 0;
    public static final int DARK_BEETLE = 1;
    public static final int GRAY_BEETLE = 2;
    private boolean shellDangerous;
    private boolean shellLaunchLeft;
    private boolean upsideDown;
    private boolean shellStoppedHeadingLeft;
    private boolean marioGroundedSinceLaunch;
    private boolean startingLeft;
    private boolean turnOnGrounded;
    private int shellDistanceFromLaunch;
    private int killCount;
    private int generalImageIndex;
    private int colorIndex;
    public static final int SHELL_SLEEP_TIME = 3800;
    private double timeStuckBetweenTiles;
    
    public Beetle(final Game game, final ImageIcon[] images, final boolean startingLeft, final int colorIndex) {
        super(game, images);
        this.colorIndex = colorIndex;
        this.startingLeft = startingLeft;
        this.avoidedCollisionCols = 0;
        this.avoidedCollisionRowsOnTop = 0;
        this.avoidedCollisionRowsOnBottom = 1;
        this.avoidedUpsideDownCollisionRowsOnBottom = 3;
        if (startingLeft) {
            this.xVel = -Constants.ENEMY_X_SPEED;
        }
        else {
            this.flip = true;
            this.xVel = Constants.ENEMY_X_SPEED;
        }
        this.spriteState = 0;
        /*if (colorIndex == 0) {
            this.imageIndex = 0;
        }
        else if (colorIndex == 1) {
            this.imageIndex = 4;
        }
        else if (colorIndex == 2) {
            this.imageIndex = 8;
        }*/
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled && this.y < Game.renderHeight) {
            return new Beetle(this.game, this.images, this.startingLeft, this.colorIndex);
        }
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.xPos += this.xVel * delta / 1000.0;
        this.applyGravity(delta, Level.GRAVITY);
        if (!this.bumpKilled && this.collidable) {
            this.checkForCollisions();
            this.checkForTurnAround();
            this.checkForUnsettledTiles();
        }
        this.flip = (this.xVel > 0.0);
        this.finalizePosition();
        if (this.spriteState == 2 && !this.shellDangerous) {
            if (!this.marioGroundedSinceLaunch && this.game.mario.grounded) {
                this.marioGroundedSinceLaunch = true;
                this.shellDistanceFromLaunch = 0;
                if (this.xVel < 0.0) {
                    this.shellLaunchLeft = true;
                }
                else {
                    this.shellLaunchLeft = false;
                }
            }
            this.shellDistanceFromLaunch += (int)Math.abs(this.x - this.lastX);
            if (this.marioGroundedSinceLaunch) {
                if (this.shellLaunchLeft && this.xVel > 0.0) {
                    this.shellDangerous = true;
                }
                else if (!this.shellLaunchLeft && this.xVel < 0.0) {
                    this.shellDangerous = true;
                }
                else if (this.shellDistanceFromLaunch > this.game.mario.width) {
                    this.shellDangerous = true;
                }
            }
        }
        else if (this.spriteState != 1 || this.ticks >= 3800.0) {
            if (this.spriteState == 1 && this.ticks >= 3800.0) {
                this.spriteState = 0;
                this.generalImageIndex = 0;
                this.upsideDown = false;
                this.vFlip = false;
                this.ticks = 0.0;
                if (this.shellStoppedHeadingLeft) {
                    this.xVel = Constants.ENEMY_X_SPEED;
                    this.flip = true;
                }
                else {
                    this.xVel = -Constants.ENEMY_X_SPEED;
                    this.flip = false;
                }
            }
        }
        if (this.spriteState == 1 && this.grounded && this.upsideDown && this.yVel >= 0.0) {
            this.xVel = 0.0;
        }
        if (this.collidable) {
            this.grounded = this.isGrounded();
            if (!this.grounded && !this.injected && this.yVel >= Level.TERMINAL_VELOCITY) {
                this.turnOnGrounded = true;
            }
        }
        if (this.spriteState == 2 && this.grounded && this.isBetweenTwoSolidTiles()) {
            this.timeStuckBetweenTiles += delta;
            if (this.timeStuckBetweenTiles > 1000.0) {
                if (this.isOnScreen()) {
                    this.bumpKilled(this);
                }
                else {
                    this.visible = false;
                }
            }
        }
        else {
            this.timeStuckBetweenTiles = 0.0;
        }
        if (this.bumpKilled) {
            this.generalImageIndex = 3;
        }
        else if (this.spriteState == 1 || this.spriteState == 2) {
            this.generalImageIndex = 2;
        }
        else if (this.spriteState == 0 && this.ticks > 150.0) {
            if (this.generalImageIndex == 0) {
                this.generalImageIndex = 1;
            }
            else {
                this.generalImageIndex = 0;
            }
            this.ticks -= 150.0;
        }
        /*if (this.colorIndex == 0) {
            this.imageIndex = this.generalImageIndex + 0;
        }
        else if (this.colorIndex == 1) {
            this.imageIndex = this.generalImageIndex + 4;
        }
        else if (this.colorIndex == 2) {
            this.imageIndex = this.generalImageIndex + 8;
        }*/
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        if (this.spriteState == 1 || this.spriteState == 2) {
            return new Rectangle(this.x, this.y + 3, this.width, this.height - this.avoidedCollisionRowsOnBottom - 3);
        }
        return this.getRectangle();
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.bumpKilled) {
            return;
        }
        if (this.isOnScreen()) {
            if (!this.upsideDown) {
                this.game.audio.play(10);
            }
            if (killer instanceof Shelled && ((Shelled)killer).isDangerous()) {
                ((Shelled)killer).shellKilled();
                this.upsideDown = false;
            }
            else if (killer instanceof Mario && (!this.game.mario.hasStar() || !this.game.mario.getRectangle().intersects(this.getSpriteContactRectangle()))) {
                final Mario mario = this.game.mario;
                mario.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
                this.upsideDown = true;
                this.vFlip = true;
                this.vFlip = true;
                this.ticks = 0.0;
                this.spriteState = 1;
            }
            else {
                final Mario mario2 = this.game.mario;
                mario2.points += 200;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 1));
                this.upsideDown = false;
            }
        }
        if (!this.upsideDown) {
            this.collidable = false;
            this.bumpKilled = true;
        }
        this.yVel = -240.0;
        if (this.xVel > 0.0) {
            this.xVel = -64.0;
        }
        else if (this.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else if (killer.getXCenter() < this.getXCenter()) {
            this.xVel = 64.0;
        }
        else {
            this.xVel = -64.0;
        }
    }
    
    @Override
    public void smushed(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer)) {
            return;
        }
        if (this.spriteState == 1 && this.grounded) {
            if (this.isOnScreen()) {
                this.game.audio.play(10);
                final Mario mario = this.game.mario;
                mario.points += 400;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 2));
            }
            this.ticks = 0.0;
            this.killCount = 0;
            this.spriteState = 2;
            this.marioGroundedSinceLaunch = false;
            this.shellDangerous = false;
            if (this.game.mario.getXCenter() <= this.getXCenter()) {
                this.xVel = Constants.LAUNCHED_SHELL_X_SPEED;
            }
            else {
                this.xVel = -Constants.LAUNCHED_SHELL_X_SPEED;
            }
        }
        else if (this.spriteState == 0) {
            if (this.isOnScreen()) {
                this.game.audio.play(7);
                final Mario mario2 = this.game.mario;
                mario2.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
            }
            this.spriteState = 1;
            this.ticks = 0.0;
            if (this.xVel < 0.0) {
                this.shellStoppedHeadingLeft = true;
            }
            else {
                this.shellStoppedHeadingLeft = false;
            }
            this.xVel = 0.0;
        }
        else if (this.spriteState == 2 && this.shellDangerous) {
            if (this.isOnScreen()) {
                this.game.audio.play(7);
            }
            this.spriteState = 1;
            this.ticks = 0.0;
            this.xVel = 0.0;
        }
    }
    
    @Override
    public void xCollided() {
        if ((!this.isBetweenTwoSolidTiles() || this.spriteState == 2) && (this.spriteState == 2 || this.grounded)) {
            if (this.spriteState == 2 && this.isOnScreen()) {
                this.game.audio.play(8);
            }
            this.xVel = -this.xVel;
        }
    }
    
    @Override
    public void checkForTurnAround() {
        if (this.spriteState == 0 && this.turnOnGrounded && this.grounded) {
            if (this.game.mario.getXCenter() < this.getXCenter() && this.xVel > 0.0) {
                this.xVel = -this.xVel;
            }
            else if (this.game.mario.getXCenter() > this.getXCenter() && this.xVel < 0.0) {
                this.xVel = -this.xVel;
            }
            this.turnOnGrounded = false;
        }
    }
    
    @Override
    public boolean isLaunched() {
        return this.spriteState == 2;
    }
    
    @Override
    public boolean isDangerous() {
        return this.shellDangerous;
    }
    
    @Override
    public boolean isShelled() {
        return this.spriteState == 1;
    }
    
    @Override
    public void shellKilled() {
        ++this.killCount;
        if (this.killCount == 1) {
            final Mario mario = this.game.mario;
            mario.points += 500;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 3));
        }
        else if (this.killCount == 2) {
            final Mario mario2 = this.game.mario;
            mario2.points += 800;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 4));
        }
        else if (this.killCount == 3 && this.killCount < 7) {
            final Mario mario3 = this.game.mario;
            mario3.points += 1000;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 5));
        }
        else if (this.killCount == 4) {
            final Mario mario4 = this.game.mario;
            mario4.points += 2000;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 6));
        }
        else if (this.killCount == 5) {
            final Mario mario5 = this.game.mario;
            mario5.points += 5000;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 7));
        }
        else if (this.killCount == 6) {
            final Mario mario6 = this.game.mario;
            mario6.points += 8000;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 8));
        }
        else if (this.killCount >= 7) {
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 10));
            this.game.audio.play(0);
            this.game.mario.extraLife();
        }
    }
}
