// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.throwers;

import supermario.game.sprites.enemies.Bullet;
import supermario.game.Game;
import supermario.game.interfaces.EnemyThrower;

public class BulletThrower implements EnemyThrower
{
    private Game game;
    private static final int LEFT_MOST_X_BEFORE_BULLET_START;
    public static final int MIN_DELAY_BEFORE_THROW = 2000;
    public static final int MAX_DELAY_BEFORE_THROW = 5000;
    public static final int MIN_BULLET_Y_TILE = 0;
    public static final int MAX_BULLET_Y_TILE;
    private double ticks;
    private double throwDelay;
    private int bulletYTile;
    private int shadowType;
    
    public BulletThrower(final Game game, final int shadowType) {
        this.game = game;
        this.shadowType = shadowType;
        this.schedule();
    }
    
    @Override
    public void update(final double delta) {
        if (this.game.level.leftMostX < BulletThrower.LEFT_MOST_X_BEFORE_BULLET_START) {
            return;
        }
        if ((this.game.level.maxTravelX >= 0 && this.game.level.leftMostX + Game.renderWidth >= this.game.level.maxTravelX) || this.game.level.levelEndPresent) {
            return;
        }
        this.ticks += delta;
        if (this.ticks >= this.throwDelay) {
            this.launch();
            this.schedule();
        }
    }
    
    @Override
    public void launch() {
        this.game.audio.play(13);
        this.game.level.spritesToAdd.add(new Bullet(this.game, this.game.textures.getBulletTextures(), this.shadowType, this.game.level.leftMostX + Game.renderWidth, this.bulletYTile * 8, true));
    }
    
    @Override
    public void schedule() {
        this.ticks = 0.0;
        this.throwDelay = this.game.rand.nextInt(3001) + 2000;
        this.bulletYTile = this.game.rand.nextInt(BulletThrower.MAX_BULLET_Y_TILE - 0 + 1) + 0;
    }
    
    static {
        LEFT_MOST_X_BEFORE_BULLET_START = Game.renderWidth / 4;
        MAX_BULLET_Y_TILE = Game.yTiles - 2 - 3;
    }
}
