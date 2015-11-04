// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import supermario.game.sprites.enemies.FireballEnemy;
import supermario.game.Tile;
import supermario.game.Game;
import javax.swing.ImageIcon;
import supermario.game.interfaces.EnemyHolder;
import supermario.game.Sprite;

public class Firebar extends Sprite implements EnemyHolder
{
    public boolean clockwise;
    public boolean speedBoost;
    public boolean longFirebar;
    public ImageIcon metal;
    private int firebarCount;
    private static final int SHORT_FIREBALL_COUNT = 6;
    private static final int LONG_FIREBALL_COUNT = 12;
    public static double FIREBAR_PERIOD_SLOW;
    public static double FIREBAR_PERIOD_FAST;
    public static final double FIREBALL_IMAGE_CHANGE_DELAY = 70.0;
    
    public Firebar(final Game game, final ImageIcon[] images, final boolean clockwise, final boolean speedBoost, final boolean longFirebar) {
        super(game, images);
        this.clockwise = clockwise;
        this.speedBoost = speedBoost;
        this.longFirebar = longFirebar;
        if (longFirebar) {
            this.firebarCount = 12;
        }
        else {
            this.firebarCount = 6;
        }
        this.imageIndex = 0;
    }
    
    @Override
    public Sprite reset() {
        return new Firebar(this.game, this.images, this.clockwise, this.speedBoost, this.longFirebar);
    }
    
    public void activate(final Tile tile) {
        this.setMetalType(tile);
        for (int i = 0; i < this.firebarCount; ++i) {
            this.game.level.sprites.add(new FireballEnemy(this.game, this, this.clockwise, this.speedBoost, 8 * i));
        }
    }
    
    private void setMetalType(final Tile tile) {
        final int levelType = this.game.level.levelType;
        /*if (levelType == 2) {
            this.metal = this.game.textures.stoneMetal;
        }
        else if (levelType == 0 || levelType == 4 || levelType == 5 || levelType == 6) {
         */   this.metal = this.game.textures.lightMetal;
        /*}
        else if (levelType == 1) {
            this.metal = this.game.textures.darkMetal;
        }*/
        /*else*/ if (levelType == 3) {
            this.metal = null;
            tile.solid = false;
            this.game.level.tiles[tile.yTile][tile.xTile + 1].solid = false;
            this.game.level.tiles[tile.yTile + 1][tile.xTile].solid = false;
            this.game.level.tiles[tile.yTile + 1][tile.xTile + 1].solid = false;
        }
    }
    
    @Override
    public void update(final double delta) {
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible || this.metal == null) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        g2D.drawImage(this.metal.getImage(), this.transform, null);
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public void checkForUnsettledTiles() {
    }
    
    static {
        Firebar.FIREBAR_PERIOD_SLOW = 3500.0;
        Firebar.FIREBAR_PERIOD_FAST = 2500.0;
    }
}
