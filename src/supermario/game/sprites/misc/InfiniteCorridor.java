// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class InfiniteCorridor extends Sprite
{
    public static final int TILES_BACKWARD = 56;
    public static final int TRAP_TILES_LENGTH = 8;
    
    public InfiniteCorridor(final Game game, final ImageIcon[] images) {
        super(game, images);
    }
    
    @Override
    public Sprite reset() {
        final InfiniteCorridor ic = new InfiniteCorridor(this.game, this.images);
        return ic;
    }
    
    @Override
    public void update(final double delta) {
        if (this.game.mario.grounded && this.game.mario.getRectangle().intersects(this.getRectangle()) && this.aboveTrap()) {
            this.game.level.exactLeftMostX = this.xPos - 448.0 - Game.xTiles * 8 / 2 + (Game.xTiles * 8 / 2 - (this.game.mario.xPos - this.game.level.exactLeftMostX));
            this.game.level.leftMostX = (int)Math.round(this.game.level.exactLeftMostX);
            if (this.game.level.leftMostX < 0) {
                this.game.level.leftMostX = 0;
            }
            this.game.mario.xPos = this.xPos - 448.0;
            this.game.mario.finalizePosition();
            this.game.level.leftMostTile = this.game.level.getTileAtPixel(this.game.level.leftMostX, 0).xTile;
        }
    }
    
    private boolean aboveTrap() {
        final Rectangle rect = this.getRectangle();
        final int mBot = this.game.mario.y + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom;
        return mBot >= rect.y && mBot <= rect.y + rect.height;
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y + this.height - 1, this.width, 1);
    }
}
