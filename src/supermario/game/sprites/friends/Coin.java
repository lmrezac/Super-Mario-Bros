// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.friends;

import supermario.game.Tile;
import supermario.game.sprites.effects.BumpCoin;
import supermario.game.sprites.Mario;
import java.awt.Rectangle;
import supermario.Utilities;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Friend;
import supermario.game.Sprite;

public class Coin extends Sprite implements Friend
{
    private boolean absorbed;
    private int shadowColor;
    
    public Coin(final Game game, final ImageIcon[] images, final int shadowColor) {
        super(game, images);
        this.shadowColor = shadowColor;
        this.absorbed = false;
        if (shadowColor == 0) {
            this.imageIndex = 1;
        }
        else if (shadowColor == 1) {
            this.imageIndex = 0;
        }
        else if (shadowColor == 2) {
            this.imageIndex = 2;
        }
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
       /* this.imageIndex = Utilities.getPulsingImageIndex();
        if (this.shadowColor == 0) {
            this.imageIndex += this.images.length / 3;
        }
        else if (this.shadowColor == 2) {
            this.imageIndex += this.images.length / 3 * 2;
        }*/
        if (!this.absorbed) {
            this.checkForUnsettledTiles();
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void xCollided() {
    }
    
    @Override
    public void absorbed() {
        if (this.absorbed) {
            return;
        }
        this.visible = false;
        this.game.audio.play(12);
        this.absorbed = true;
        final Mario mario = this.game.mario;
        ++mario.coins;
        final Mario mario2 = this.game.mario;
        mario2.points += 200;
    }
    
    private void bumpAbsorbed() {
        this.absorbed();
        this.game.level.effects.add(new BumpCoin(this.game, this));
    }
    
    @Override
    public void bumped() {
    }
    
    @Override
    public void checkForUnsettledTiles() {
        final Rectangle rect = this.getContactRectangle();
        if (rect.y + rect.height + 1 >= Game.renderHeight) {
            return;
        }
        int minX = rect.x;
        if (minX < 0) {
            minX = 0;
        }
        int maxX = rect.x + rect.width;
        if (maxX > this.game.level.xTiles * 8) {
            maxX = this.game.level.xTiles * 8;
        }
        for (int i = minX; i < maxX; ++i) {
            final Tile tempTile = this.game.level.getTileAtPixel(i, rect.y + rect.height + 1);
            if (tempTile.sprite != null && !tempTile.sprite.settled && tempTile.sprite.justHit) {
                this.bumpAbsorbed();
                break;
            }
        }
    }
}
