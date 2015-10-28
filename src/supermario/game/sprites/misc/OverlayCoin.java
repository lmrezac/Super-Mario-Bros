// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import supermario.Utilities;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class OverlayCoin extends Sprite
{
    public OverlayCoin(final Game game, final ImageIcon[] images) {
        super(game, images);
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        if (this.locked) {
            this.imageIndex = 0;
        }
        else if (this.game.getGameState() == 1) {
            if (this.game.level.levelType == 1) {
                this.imageIndex = Utilities.getPulsingImageIndex() + 3;
            }
            else if (this.game.level.levelType == 2) {
                this.imageIndex = Utilities.getPulsingImageIndex() + 6;
            }
            else {
                this.imageIndex = Utilities.getPulsingImageIndex();
            }
        }
        else {
            this.imageIndex = Utilities.getPulsingImageIndex();
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return null;
    }
}
