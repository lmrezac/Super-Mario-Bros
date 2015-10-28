// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import supermario.game.sprites.enemies.Bullet;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.EnemyHolder;
import supermario.game.Sprite;

public class Cannon extends Sprite implements EnemyHolder
{
    private boolean tallCannon;
    public static final int MIN_FIRE_BULLET_DELAY = 1500;
    public static final int MAX_FIRE_BULLET_DELAY = 4001;
    private int fireDelay;
    private int shadowColor;
    private final int DISTANCE_IN_RANGE = 32;
    
    public Cannon(final Game game, final ImageIcon[] images, final int shadowColor, final boolean tallCannon) {
        super(game, images);
        this.shadowColor = shadowColor;
        this.tallCannon = tallCannon;
        if (shadowColor == 0) {
            this.imageIndex = 0;
        }
        else if (shadowColor == 1) {
            this.imageIndex = 1;
        }
        else if (shadowColor == 2) {
            this.imageIndex = 2;
        }
        this.fireDelay = game.rand.nextInt(2501) + 1500;
    }
    
    @Override
    public Sprite reset() {
        return new Cannon(this.game, this.images, this.shadowColor, this.tallCannon);
    }
    
    @Override
    public void update(final double delta) {
        if (this.marioInRange() && this.isOnScreen()) {
            this.ticks += delta;
        }
        if (this.ticks > this.fireDelay && (!this.game.mario.transitioning || this.game.mario.transitionState != 3)) {
            this.fire();
            this.ticks = 0.0;
        }
    }
    
    private boolean marioInRange() {
        final int marioCenter = this.game.mario.x + this.game.mario.width / 2;
        return marioCenter < this.x - 32 || marioCenter > this.x + this.width + 32;
    }
    
    private void fire() {
        this.game.audio.play(13);
        boolean leftStart = true;
        if (this.game.mario.x + this.game.mario.width / 2 > this.x + this.width / 2) {
            leftStart = false;
        }
        this.game.level.spritesToAdd.add(new Bullet(this.game, this.game.textures.getBulletTextures(), this.shadowColor, this.x, this.y, leftStart));
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
