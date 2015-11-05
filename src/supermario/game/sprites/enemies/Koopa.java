// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.effects.Points;
import supermario.game.sprites.Mario;
import supermario.game.sprites.friends.FireballFriend;
import java.awt.Rectangle;
import supermario.game.Level;
import supermario.game.interfaces.Constants;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.MarioFollower;
import supermario.game.interfaces.Shelled;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Koopa extends Sprite implements Enemy, Shelled, MarioFollower
{
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_BOUNCING = 1;
    public static final int TYPE_FLYING_V = 2;
    public static final int TYPE_FLYING_H = 3;
    public static final int COLOR_GREEN = 0;
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_RED = 2;
    public static final int STATE_BOUNCING = 0;
    public static final int STATE_FLYING_V = 1;
    public static final int STATE_FLYING_H = 2;
    public static final int STATE_NORMAL = 3;
    public static final int STATE_SHELLED = 4;
    public static final int STATE_SHELL_LAUNCHED = 5;
    public static final int TIME_BEFORE_SHELL_BEGINS_TO_AWAKEN = 2400;
    public static final int SHELL_AWAKEN_TIME = 1400;
    public static final int V_OSC_PERIOD = 2000;
    public static int V_FLYING_PERIOD;
    public static int H_FLYING_PERIOD;
    public static double BOUNCING_GRAVITY;
    public static double BOUNCE_VELOCITY;
    public static final double V_SIN_AMPLITUDE = 7.0;
    public static final double H_SIN_AMPLITUDE = 6.0;
    private int color;
    private int type;
    private int generalImageIndex;
    private boolean shellDangerous;
    private boolean shellLaunchLeft;
    private boolean shellAwakening;
    private boolean shellStoppedHeadingLeft;
    private boolean marioGroundedSinceLaunch;
    private boolean leftStarting;
    private boolean upsideDown;
    private boolean turnOnGrounded;
    private int shellDistanceFromLaunch;
    private double motionTicks;
    private double flyingPeriod;
    private double vOscPeriod;
    private double lastTurnAroundTicks;
    public float startingOffset;
    public static final int MINIMUM_TURN_AROUND_DELAY = 500;
    public double flyingYCenter;
    public double flyingXCenter;
    private double timeStuckBetweenTiles;
    private int killCount;
    
    public Koopa(final Game game, final ImageIcon[] images, final int color, final int type, final boolean leftStarting) {
        super(game, images);
        this.leftStarting = leftStarting;
        this.color = color;
        this.type = type;
        if (type == 2) {
            this.xVel = 0.0;
        }
        else if (type == 3) {
            this.flyingPeriod = Koopa.H_FLYING_PERIOD / 2;
        }
        else if (leftStarting) {
            this.xVel = -Constants.ENEMY_X_SPEED;
        }
        else {
            this.flip = true;
            this.xVel = Constants.ENEMY_X_SPEED;
        }
        this.generalImageIndex = 0;
        this.avoidedCollisionCols = 1;
        this.avoidedCollisionRowsOnBottom = 1;
        this.avoidedCollisionRowsOnTop = 8;
        this.avoidedUpsideDownCollisionRowsOnBottom = 9;
        this.lastTurnAroundTicks = 500.0;
        if (type == 1) {
            this.spriteState = 0;
        }
        else if (type == 2) {
            this.spriteState = 1;
        }
        else if (type == 3) {
            this.spriteState = 2;
        }
        else if (type == 0) {
            this.spriteState = 3;
        }
        if (color == 0) {
            this.imageIndex = 0;
        }
        /*else if (color == 1) {
            this.imageIndex = 7;
        }*/
        else if (color == 2) {
            this.imageIndex = 7;
        }
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled && this.y < Game.renderHeight) {
            return new Koopa(this.game, this.images, this.color, this.type, this.leftStarting);
        }
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.lastTurnAroundTicks += delta;
        if (this.lastTurnAroundTicks > 500.0) {
            this.lastTurnAroundTicks = 500.0;
        }
        this.preUpdate(delta);
        this.xPos += this.xVel * delta / 1000.0;
        if ((this.spriteState != 0 && this.spriteState != 1 && this.spriteState != 2) || this.bumpKilled) {
            this.applyGravity(delta, Level.GRAVITY);
        }
        else if (this.spriteState == 0) {
            this.applyGravity(delta, Koopa.BOUNCING_GRAVITY);
        }
        else if (this.spriteState == 1) {
            this.setVerticalFlyingPosition(delta);
        }
        else if (this.spriteState == 2) {
            this.setHorizontalFlyingPosition(delta);
        }
        if (!this.bumpKilled && this.collidable && this.spriteState != 1 && this.spriteState != 2) {
            this.checkForCollisions();
            this.checkForTurnAround();
            if (this.spriteState == 0 && this.grounded) {
                this.yVel = Koopa.BOUNCE_VELOCITY;
            }
            this.checkForUnsettledTiles();
        }
        this.flip = (this.xVel > 0.0);
        this.finalizePosition();
        if (this.spriteState == 5 && !this.shellDangerous) {
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
        else if (this.spriteState == 4 && this.ticks > 2400.0) {
            this.ticks = 0.0;
            this.shellAwakening = true;
        }
        else if (this.spriteState == 4 && this.shellAwakening) {
            if (this.ticks < 1400.0) {
                this.motionTicks += delta;
                if (this.motionTicks > 150.0) {
                    if (this.generalImageIndex == 4) {
                        this.generalImageIndex = 5;
                    }
                    else {
                        this.generalImageIndex = 4;
                    }
                    this.motionTicks -= 150.0;
                }
            }
            else {
                this.spriteState = 3;
                this.generalImageIndex = 0;
                this.shellAwakening = false;
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
        if (this.spriteState == 4 && this.upsideDown && this.grounded && this.yVel >= 0.0) {
            this.xVel = 0.0;
        }
        if (this.collidable) {
            this.grounded = this.isGrounded();
            if (!this.grounded && !this.injected && this.yVel >= Level.TERMINAL_VELOCITY) {
                this.turnOnGrounded = true;
            }
        }
        if (this.spriteState == 5 && this.grounded && this.isBetweenTwoSolidTiles()) {
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
            this.generalImageIndex = 6;
        }
        else if ((this.spriteState == 4 && !this.shellAwakening) || this.spriteState == 5) {
            this.generalImageIndex = 4;
        }
        else if (this.spriteState == 3) {
            this.motionTicks += delta;
            if (this.motionTicks > 150.0) {
                if (this.generalImageIndex == 0) {
                    this.generalImageIndex = 1;
                }
                else {
                    this.generalImageIndex = 0;
                }
                this.motionTicks -= 150.0;
            }
        }
        else if (this.spriteState == 0 || this.spriteState == 1 || this.spriteState == 2) {
            this.motionTicks += delta;
            if (this.motionTicks > 150.0) {
                if (this.generalImageIndex == 2) {
                    this.generalImageIndex = 3;
                }
                else {
                    this.generalImageIndex = 2;
                }
                this.motionTicks -= 150.0;
            }
        }
        this.imageIndex = this.generalImageIndex;
        this.setIndexBasedOnColor();
    }
    
    private void setIndexBasedOnColor() {
        if (this.color == 2) {
            this.imageIndex += 7;
        }
//        else if (this.color == 2) {
//            this.imageIndex += 14;
//        }
    }
    
    private void setVerticalFlyingPosition(final double delta) {
        this.flyingPeriod = (this.flyingPeriod + delta) % Koopa.V_FLYING_PERIOD;
        final double theta = (this.flyingPeriod + Koopa.V_FLYING_PERIOD * this.startingOffset) % Koopa.V_FLYING_PERIOD / Koopa.V_FLYING_PERIOD * 3.141592653589793 * 2.0;
        this.yPos = this.flyingYCenter + 56.0 * Math.sin(theta);
        this.yVel = 7.0 * Math.cos(theta);
    }
    
    private void setHorizontalFlyingPosition(final double delta) {
        this.flyingPeriod = (this.flyingPeriod + delta) % Koopa.H_FLYING_PERIOD;
        final double theta = (this.flyingPeriod + Koopa.H_FLYING_PERIOD * this.startingOffset) % Koopa.H_FLYING_PERIOD / Koopa.H_FLYING_PERIOD * 3.141592653589793 * 2.0;
        this.xPos = this.flyingXCenter + 48.0 * Math.sin(theta);
        this.xVel = 6.0 * Math.cos(theta);
        this.flip = (this.xVel >= 0.0);
        this.vOscPeriod = (this.vOscPeriod + delta) % 2000.0;
        final double oscTheta = this.vOscPeriod / 2000.0 * 3.141592653589793 * 2.0;
        this.yPos = this.flyingYCenter + 8.0 * Math.sin(oscTheta);
        this.yVel = Math.cos(oscTheta);
    }
    
    @Override
    public void checkForTurnAround() {
        if (this.spriteState == 3) {
            if (this.color == 2 && this.isOnOneTile() && this.lastTurnAroundTicks >= 500.0) {
                this.lastTurnAroundTicks = 0.0;
                if (this.xVel > 0.0) {
                    --this.xPos;
                }
                else {
                    ++this.xPos;
                }
                this.xVel = -this.xVel;
            }
            else if (this.turnOnGrounded && this.grounded) {
                if (this.game.mario.getXCenter() < this.getXCenter() && this.xVel > 0.0) {
                    this.xVel = -this.xVel;
                }
                else if (this.game.mario.getXCenter() > this.getXCenter() && this.xVel < 0.0) {
                    this.xVel = -this.xVel;
                }
                this.turnOnGrounded = false;
            }
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.width - this.avoidedCollisionCols * 2, this.height - this.avoidedCollisionRowsOnBottom - this.avoidedCollisionRowsOnTop);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.bumpKilled) {
            return;
        }
        if ((this.spriteState == 1 || this.spriteState == 2) && (!(killer instanceof Shelled) || !((Shelled)killer).isLaunched()) && !(killer instanceof Hammer) && !(killer instanceof FireballEnemy) && !(killer instanceof FireballFriend) && (!(killer instanceof Mario) || !this.game.mario.hasStar())) {
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
            else if (killer instanceof FireballFriend) {
                final Mario mario = this.game.mario;
                mario.points += 200;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 1));
                this.upsideDown = false;
            }
            else if (killer instanceof Mario && (!this.game.mario.hasStar() || !this.game.mario.getRectangle().intersects(this.getSpriteContactRectangle()))) {
                final Mario mario2 = this.game.mario;
                mario2.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
                this.upsideDown = true;
                this.shellAwakening = false;
                this.vFlip = true;
                this.ticks = 0.0;
                this.spriteState = 4;
            }
            else {
                final Mario mario3 = this.game.mario;
                mario3.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
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
        else if (killer.x + killer.width / 2 < this.xPos + this.width / 2) {
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
        if (this.spriteState == 4 && this.grounded) {
            if (this.isOnScreen()) {
                this.game.audio.play(10);
                final Mario mario = this.game.mario;
                mario.points += 400;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 2));
            }
            this.ticks = 0.0;
            this.shellAwakening = false;
            this.spriteState = 5;
            this.killCount = 0;
            this.marioGroundedSinceLaunch = false;
            this.shellDangerous = false;
            if (this.game.mario.getXCenter() <= this.getXCenter()) {
                this.xVel = Constants.LAUNCHED_SHELL_X_SPEED;
            }
            else {
                this.xVel = -Constants.LAUNCHED_SHELL_X_SPEED;
            }
        }
        else if (this.spriteState == 3) {
            if (this.isOnScreen()) {
                this.game.audio.play(7);
                final Mario mario2 = this.game.mario;
                mario2.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
            }
            this.spriteState = 4;
            this.ticks = 0.0;
            if (this.xVel < 0.0) {
                this.shellStoppedHeadingLeft = true;
            }
            else {
                this.shellStoppedHeadingLeft = false;
            }
            this.xVel = 0.0;
        }
        else if (this.spriteState == 5 && this.shellDangerous && this.grounded) {
            if (this.isOnScreen()) {
                this.game.audio.play(7);
            }
            this.spriteState = 4;
            this.shellAwakening = false;
            this.ticks = 0.0;
            this.xVel = 0.0;
        }
        else if (this.spriteState == 0) {
            if (this.isOnScreen()) {
                this.game.audio.play(7);
                final Mario mario3 = this.game.mario;
                mario3.points += 400;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 2));
            }
            this.spriteState = 3;
            this.generalImageIndex = 0;
            this.ticks = 0.0;
            this.yVel = 0.0;
            this.imageIndex = 0;
            this.setIndexBasedOnColor();
        }
        else if (this.spriteState == 1 || this.spriteState == 2) {
            if (this.isOnScreen()) {
                this.game.audio.play(7);
                final Mario mario4 = this.game.mario;
                mario4.points += 400;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 2));
            }
            this.spriteState = 3;
            this.ticks = 0.0;
            this.generalImageIndex = 0;
            this.yVel = 0.0;
            this.xVel = -Constants.ENEMY_X_SPEED;
            this.imageIndex = 0;
            this.setIndexBasedOnColor();
        }
    }
    
    @Override
    public void xCollided() {
        if ((!this.isBetweenTwoSolidTiles() || this.spriteState == 5) && (this.grounded || this.spriteState == 5 || (this.spriteState == 0 && this.lastTurnAroundTicks >= 500.0))) {
            this.lastTurnAroundTicks = 0.0;
            if (this.spriteState == 5 && this.isOnScreen()) {
                this.game.audio.play(8);
            }
            this.xVel = -this.xVel;
        }
    }
    
    @Override
    public boolean isLaunched() {
        return this.spriteState == 5;
    }
    
    @Override
    public boolean isDangerous() {
        return this.shellDangerous;
    }
    
    @Override
    public boolean isShelled() {
        return this.spriteState == 4;
    }
    
    @Override
    public void shellKilled() {
        ++this.killCount;
        final int xCenter = this.getXCenter();
        if (this.killCount == 1) {
            final Mario mario = this.game.mario;
            mario.points += 500;
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 3));
        }
        else if (this.killCount == 2) {
            final Mario mario2 = this.game.mario;
            mario2.points += 800;
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 4));
        }
        else if (this.killCount == 3) {
            final Mario mario3 = this.game.mario;
            mario3.points += 1000;
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 5));
        }
        else if (this.killCount == 4) {
            final Mario mario4 = this.game.mario;
            mario4.points += 2000;
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 6));
        }
        else if (this.killCount == 5) {
            final Mario mario5 = this.game.mario;
            mario5.points += 5000;
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 7));
        }
        else if (this.killCount == 6) {
            final Mario mario6 = this.game.mario;
            mario6.points += 8000;
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 8));
        }
        else if (this.killCount >= 7) {
            this.game.level.effectsToAdd.add(new Points(this.game, xCenter, this.y, 10));
            this.game.audio.play(0);
            this.game.mario.extraLife();
        }
    }
    
    static {
        Koopa.V_FLYING_PERIOD = 6000;
        Koopa.H_FLYING_PERIOD = 5000;
        Koopa.BOUNCING_GRAVITY = Level.GRAVITY / 2.0;
        Koopa.BOUNCE_VELOCITY = -256.0;
    }
}
