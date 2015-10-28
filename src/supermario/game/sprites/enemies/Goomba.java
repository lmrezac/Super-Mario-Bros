// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import supermario.game.interfaces.Shelled;
import java.awt.Rectangle;
import supermario.game.Level;
import supermario.game.interfaces.Constants;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Goomba extends Sprite implements Enemy
{
    private int colorIndex;
    public boolean smushed;
    public boolean startingLeft;
    private int generalImageIndex;
    public static final int LIGHT_GOOMBA = 0;
    public static final int DARK_GOOMBA = 1;
    public static final int GRAY_GOOMBA = 3;
    
    public Goomba(final Game game, final ImageIcon[] images, final int colorIndex, final boolean startingLeft) {
        super(game, images);
        this.startingLeft = startingLeft;
        this.colorIndex = colorIndex;
        this.xVel = Constants.ENEMY_X_SPEED * (startingLeft ? -1 : 1);
        this.avoidedCollisionRowsOnTop = 0;
        this.avoidedCollisionCols = 0;
        this.avoidedCollisionRowsOnBottom = 1;
        if (colorIndex == 0) {
            this.imageIndex = 0;
        }
        else if (colorIndex == 1) {
            this.imageIndex = 4;
        }
        else if (colorIndex == 3) {
            this.imageIndex = 8;
        }
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled && !this.smushed && this.y < Game.renderHeight) {
            return new Goomba(this.game, this.images, this.colorIndex, this.startingLeft);
        }
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.xPos += this.xVel * delta / 1000.0;
        this.applyGravity(delta, Level.GRAVITY);
        if (!this.bumpKilled) {
            this.checkForCollisions();
        }
        if (this.collidable) {
            this.checkForUnsettledTiles();
        }
        if (this.bumpKilled) {
            this.generalImageIndex = 3;
        }
        else if (this.smushed) {
            this.generalImageIndex = 2;
        }
        else {
            if (this.ticks > 150.0) {
                ++this.generalImageIndex;
                this.ticks -= 150.0;
            }
            if (this.generalImageIndex == 2) {
                this.generalImageIndex = 0;
            }
        }
        this.finalizePosition();
        if (this.collidable) {
            this.grounded = this.isGrounded();
        }
        if (this.colorIndex == 0) {
            this.imageIndex = this.generalImageIndex + 0;
        }
        else if (this.colorIndex == 1) {
            this.imageIndex = this.generalImageIndex + 4;
        }
        else if (this.colorIndex == 3) {
            this.imageIndex = this.generalImageIndex + 8;
        }
        if (this.smushed && this.ticks > 700.0) {
            this.visible = false;
        }
    }
    
    @Override
    public void xCollided() {
        if (this.grounded) {
            this.xVel = -this.xVel;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.width - this.avoidedCollisionCols * 2, this.height - this.avoidedCollisionRowsOnBottom - this.avoidedCollisionRowsOnTop);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.bumpKilled || this.smushed) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
            if (killer instanceof Shelled && ((Shelled)killer).isDangerous()) {
                ((Shelled)killer).shellKilled();
            }
            else {
                final Mario mario = this.game.mario;
                mario.points += 100;
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
            }
        }
        this.collidable = false;
        this.bumpKilled = true;
        this.yVel = -240.0;
        if (killer.xVel > 0.0) {
            this.xVel = -64.0;
        }
        else if (this.game.mario.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else if (this.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else {
            this.xVel = -64.0;
        }
    }
    
    @Override
    public void smushed(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.smushed || this.bumpKilled) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(7);
            final Mario mario = this.game.mario;
            mario.points += 100;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 0));
        }
        this.smushed = true;
        this.ticks = 0.0;
        this.xVel = 0.0;
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return new Rectangle(this.x + 3, this.y + 3, this.width - 6, this.height - 3 - this.avoidedCollisionRowsOnBottom);
    }
}
