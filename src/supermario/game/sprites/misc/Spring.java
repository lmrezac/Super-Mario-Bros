// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.Level;
import supermario.game.Sprite;
import supermario.game.sprites.Mario;
@SuppressWarnings("unused")
public class Spring extends Sprite
{
    private int baseY;
    private int jointOffset;
    private int levelType;
    private double restingBaseY;
    private double baseYPos;
    private boolean underMario;
    private boolean launchAllowed;
    private boolean jumpReleased;
    private boolean isResting;
    private boolean isSuper;
    private Color baseColor;
    private Color lineColor;
    private Color jointColor;
    private static final int RETURN_Y_SPEED = 160;
    private static final int SINK_LIMIT_FROM_NORMAL_BASE = 16;
    private static final double MAX_FALL_VELOCITY_ON_SPRING;
    private static final double MAX_LAUNCH_X_VEL;
    private static final double MED_LAUNCH_X_VEL;
    public static double LAUNCH_VELOCITY;
    public static double SUPER_LAUNCH_FACTOR;
    public static double BOUNCE_VELOCITY;
    
    public Spring(final Game game, final ImageIcon[] images, final int levelType, final boolean isSuper) {
        super(game, images);
        this.levelType = levelType;
        this.isSuper = isSuper;
        if (levelType == 2) {
            this.imageIndex = 2;
            this.baseColor = game.textures.brickBlue;
            this.lineColor = new Color(131, 58, 17);
            this.jointColor = new Color(251, 188, 171);
        }
        else if (levelType == 0 || levelType == 4 || levelType == 5 || levelType == 6) {
            this.imageIndex = 0;
            this.baseColor = game.textures.brickRed;
            this.lineColor = new Color(234, 158, 34);
            this.jointColor = Color.WHITE;
        }
        else if (levelType == 1) {
            this.imageIndex = 1;
            this.baseColor = game.textures.brickBlue;
            this.lineColor = new Color(131, 58, 17);
            this.jointColor = new Color(251, 188, 171);
        }
        if (isSuper) {
            this.baseColor = game.textures.superSpringGreen;
        }
    }
    
    @Override
    public Sprite reset() {
        return new Spring(this.game, this.images, this.levelType, this.isSuper);
    }
    
    public void activate() {
        this.restingBaseY = this.y + 1;
        this.baseYPos = this.restingBaseY;
        this.baseY = (int)Math.round(this.baseYPos);
        this.game.level.springs.add(this);
    }
    
    @Override
    public void update(final double delta) {
        if (this.underMario()) {
            if (this.game.mario.yVel >= 0.0) {
                this.baseYPos = this.game.mario.yPos + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom;
                if (this.game.mario.yVel > Spring.MAX_FALL_VELOCITY_ON_SPRING) {
                    this.game.mario.yVel = Spring.MAX_FALL_VELOCITY_ON_SPRING;
                }
            }
            else {
                this.baseYPos -= 160.0 * delta / 1000.0;
            }
            if (!this.game.mario.grounded && this.game.mario.yVel >= 0.0) {
                this.game.mario.xVel = 0.0;
            }
        }
        else if (this.baseYPos > this.restingBaseY) {
            this.baseYPos -= 160.0 * delta / 1000.0;
        }
        this.setLaunchState();
        if (this.baseYPos <= this.restingBaseY) {
            this.baseYPos = this.restingBaseY;
            this.isResting = true;
            this.jointOffset = 0;
        }
        else if (this.baseYPos >= this.restingBaseY + 16.0) {
            this.baseYPos = this.restingBaseY + 16.0;
            this.isResting = false;
            this.launchMario();
        }
        else {
            this.isResting = false;
            final double ratio = (this.baseYPos - this.restingBaseY) / 16.0;
            if (ratio <= 0.25) {
                this.jointOffset = 0;
            }
            else if (ratio <= 0.5) {
                this.jointOffset = 1;
            }
            else if (ratio <= 0.75) {
                this.jointOffset = 2;
            }
            else if (ratio <= 1.0) {
                this.jointOffset = 3;
            }
        }
        this.baseY = (int)Math.round(this.baseYPos);
    }
    
    private void setLaunchState() {
        if (this.game.input.jumpDown && this.jumpReleased && this.game.mario.yVel > 0.0) {
            this.launchAllowed = true;
        }
        else {
            this.launchAllowed = false;
        }
        if (!this.game.mario.grounded && !this.game.input.jumpDown && this.game.mario.yVel > 0.0 && this.game.mario.yPos + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom >= this.restingBaseY - 16.0) {
            this.jumpReleased = true;
        }
        if (this.game.mario.grounded || this.game.mario.yVel < 0.0) {
            this.jumpReleased = false;
        }
    }
    
    private void launchMario() {
        this.game.mario.springLaunched = true;
        this.game.mario.yPos = this.baseYPos + this.game.mario.avoidedCollisionRowsOnBottom - this.game.mario.height;
        if (this.launchAllowed) {
            this.game.mario.yVel = Spring.LAUNCH_VELOCITY * (this.isSuper ? Spring.SUPER_LAUNCH_FACTOR : 1.0);
            if (this.game.input.rightDown && this.game.input.runDown) {
                this.game.mario.xVel = Spring.MAX_LAUNCH_X_VEL;
            }
            else if (this.game.input.rightDown) {
                this.game.mario.xVel = Spring.MED_LAUNCH_X_VEL;
            }
            else if (this.game.input.leftDown && this.game.input.runDown) {
                this.game.mario.xVel = -Spring.MAX_LAUNCH_X_VEL;
            }
            else if (this.game.input.leftDown) {
                this.game.mario.xVel = -Spring.MED_LAUNCH_X_VEL;
            }
            if (this.game.mario.isLarge()) {
                this.game.audio.play(2);
            }
            else {
                this.game.audio.play(3);
            }
        }
        else {
            this.game.mario.yVel = Spring.BOUNCE_VELOCITY;
        }
        this.jumpReleased = false;
        this.launchAllowed = false;
    }
    
    private boolean underMario() {
        if (!this.game.mario.getRectangle().intersects(this.getRectangle())) {
            return this.underMario = false;
        }
        if (this.underMario || (!this.underMario && this.game.mario.yVel > 0.0 && this.game.mario.yPos + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom <= this.restingBaseY + 4.0) || this.game.mario.lastY + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom <= this.restingBaseY + 4.0) {
            this.underMario = true;
            if (!this.game.mario.grounded) {
                if (this.game.mario.isLarge()) {
                    this.game.mario.spriteState = 7;
                    this.game.mario.stateHeightHalved = false;
                }
                else {
                    this.game.mario.spriteState = 1;
                    this.game.mario.stateHeightHalved = true;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        if (this.isResting) {
            int refImage = 0;
            if (this.imageIndex == 0) {
                refImage = 3;
            }
            else if (this.imageIndex == 1) {
                refImage = 4;
            }
            else if (this.imageIndex == 2) {
                refImage = 5;
            }
            g2D.drawImage(this.images[refImage].getImage(), this.transform, null);
        }
        else {
            g2D.drawImage(this.images[this.imageIndex].getImage(), this.transform, null);
            g2D.setColor(this.baseColor);
            g2D.fillRect(this.x, this.baseY, this.width, 2);
            g2D.setColor(this.jointColor);
            g2D.fillRect(this.x + 4 + 1, this.baseY + 2, 2, 2);
            g2D.fillRect(this.x + this.width - 4 - 3, this.baseY + 2, 2, 2);
            g2D.fillRect(this.x + 4 + 1, this.y + this.height - 11, 2, 2);
            g2D.fillRect(this.x + this.width - 4 - 3, this.y + this.height - 11, 2, 2);
            final int jointY = (int)Math.round((this.baseYPos + (this.y + this.height - 7)) / 2.0 - 2.0);
            g2D.fillRect(this.x + 2 - this.jointOffset, jointY, 2, 4);
            g2D.fillRect(this.x + this.width - 4 + this.jointOffset, jointY, 2, 4);
            g2D.setColor(this.lineColor);
            g2D.drawLine(this.x + 4 + 1, this.baseY + 3, this.x + 3 - this.jointOffset, jointY);
            g2D.drawLine(this.x + this.width - 4 - 2, this.baseY + 3, this.x + this.width - 4 + this.jointOffset, jointY);
            g2D.drawLine(this.x + 3 - this.jointOffset, jointY + 3, this.x + 4 + 1, this.y + this.height - 11);
            g2D.drawLine(this.x + this.width - 4 + this.jointOffset, jointY + 3, this.x + this.width - 4 - 2, this.y + this.height - 11);
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height - 9);
    }
    
    static {
        MAX_FALL_VELOCITY_ON_SPRING = Level.TERMINAL_VELOCITY / 2.0;
        MAX_LAUNCH_X_VEL = Mario.MAX_RUNNING_SPEED / 4.0 * 3.0;
        MED_LAUNCH_X_VEL = Mario.MAX_RUNNING_SPEED / 4.0;
        Spring.LAUNCH_VELOCITY = -600.0;
        Spring.SUPER_LAUNCH_FACTOR = 3.0;
        Spring.BOUNCE_VELOCITY = -320.0;
    }
}
