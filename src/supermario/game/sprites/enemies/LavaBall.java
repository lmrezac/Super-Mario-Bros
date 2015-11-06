// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.Level;
import supermario.game.Sprite;
import supermario.game.interfaces.Enemy;
@SuppressWarnings("unused")
public class LavaBall extends Sprite implements Enemy
{
    private final int MIN_FIRE_DELAY = 1000;
    private final int MAX_FIRE_DELAY = 3001;
    private final int MAX_FIRE_Y_VELOCITY = 320;
    private final int MIN_FIRE_Y_VELOCITY = 256;
    public static final int BACKGROUND_COLOR_LAVA = 0;
    public static final int BACKGROUND_COLOR_WATER = 1;
    public static final int BACKGROUND_COLOR_BLANK = 2;
    private double fireDelay;
    private double fireYVelocity;
    private boolean firing;
    public int backgroundColorIndex;
    
    public LavaBall(final Game game, final ImageIcon[] images, final int backgroundColorIndex) {
        super(game, images);
        this.backgroundColorIndex = backgroundColorIndex;
    }
    
    @Override
    public Sprite reset() {
        return new LavaBall(this.game, this.images, this.backgroundColorIndex);
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (!this.firing && this.ticks > this.fireDelay) {
            this.fire();
            this.firing = true;
        }
        else if (this.firing) {
            this.applyGravity(delta, Level.GRAVITY / 4.0);
            if (this.yPos > Game.renderHeight) {
                this.firing = false;
                this.setNextRandomValues();
                this.ticks = 0.0;
            }
        }
        if (this.yVel <= 0.0) {
            this.imageIndex = 0;
        }
        else {
            this.imageIndex = 1;
        }
        this.finalizePosition();
    }
    
    public void activate(final int xTile, final int yTile) {
        if (this.backgroundColorIndex == 0) {
            this.game.level.tiles[yTile][xTile].image = this.game.textures.lavaBottom;
            this.game.level.tiles[yTile][xTile + 1].image = this.game.textures.lavaBottom;
            this.game.level.tiles[yTile + 1][xTile].image = this.game.textures.lavaBottom;
            this.game.level.tiles[yTile + 1][xTile + 1].image = this.game.textures.lavaBottom;
        }
        else if (this.backgroundColorIndex == 1) {
            this.game.level.tiles[yTile][xTile].image = this.game.textures.waterBottom;
            this.game.level.tiles[yTile][xTile + 1].image = this.game.textures.waterBottom;
            this.game.level.tiles[yTile + 1][xTile].image = this.game.textures.waterBottom;
            this.game.level.tiles[yTile + 1][xTile + 1].image = this.game.textures.waterBottom;
        }
        this.setNextRandomValues();
        this.yPos = Game.renderHeight;
    }
    
    public void activate() {
        this.setNextRandomValues();
        this.fireDelay = 100.0;
        this.yPos = Game.renderHeight;
    }
    
    private void setNextRandomValues() {
        this.fireDelay = this.game.rand.nextInt(2001) + 1000;
        this.fireYVelocity = -(this.game.rand.nextInt(64) + 256);
    }
    
    private void fire() {
        this.yPos = Game.renderHeight;
        this.yVel = this.fireYVelocity;
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
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
    }
}
