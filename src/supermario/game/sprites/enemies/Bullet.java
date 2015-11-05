// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.Level;
import supermario.game.sprites.Mario;
import supermario.game.interfaces.Shelled;
import supermario.game.sprites.effects.Points;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Bullet extends Sprite implements Enemy
{
    public static double BULLET_SPEED;
    public static double SHELL_KILLED_Y_VEL;
    public static double BUMP_KILLED_GRAVITY;
    private int shadowColor;
    
    public Bullet(final Game game, final ImageIcon[] images, final int shadowColor, final int x, final int y, final boolean headingLeft) {
        super(game, images);
        this.shadowColor = shadowColor;
        this.xPos = x;
        this.x = x;
        this.yPos = y;
        this.y = y;
        this.imageIndex = 0;
        /*if (shadowColor == 0) {
            this.imageIndex = 0;
        }
        else if (shadowColor == 1) {
            this.imageIndex = 1;
        }
        else if (shadowColor == 2) {
            this.imageIndex = 2;
        }*/
        if (headingLeft) {
            this.flip = false;
            this.xVel = -Bullet.BULLET_SPEED;
        }
        else {
            this.flip = true;
            this.xVel = Bullet.BULLET_SPEED;
        }
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.xPos += this.xVel * delta / 1000.0;
        if (this.bumpKilled) {
            this.applyGravity(delta, Bullet.BUMP_KILLED_GRAVITY);
        }
        this.finalizePosition();
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        this.smushed(killer);
    }
    
    @Override
    public void smushed(final Sprite killer) {
        if (killer == this.game.mario) {
            if (this.game.mario.hasStar()) {
                this.game.audio.play(10);
            }
            else {
                this.game.audio.play(7);
            }
            final Mario mario = this.game.mario;
            mario.points += 200;
            this.game.level.effectsToAdd.add(new Points(this.game, this.x + this.width / 2, this.y, 1));
            this.yVel = 0.0;
        }
        else if (killer instanceof Shelled) {
            if (killer.isOnScreen()) {
                this.game.audio.play(10);
            }
            ((Shelled)killer).shellKilled();
            this.yVel = Bullet.SHELL_KILLED_Y_VEL;
        }
        this.xVel = 0.0;
        this.bumpKilled = true;
        this.collidable = false;
    }
    
    @Override
    public void xCollided() {
    }
    
    static {
        Bullet.BULLET_SPEED = 80.0;
        Bullet.SHELL_KILLED_Y_VEL = -240.0;
        Bullet.BUMP_KILLED_GRAVITY = Level.GRAVITY / 5.0 * 3.0;
    }
}
