// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;
import supermario.game.Sprite;

public class ThinAirWarp extends Sprite implements Warpable
{
    public Warp warp;
    
    public ThinAirWarp(final Game game, final ImageIcon[] images) {
        super(game, images);
    }
    
    @Override
    public Sprite reset() {
        final ThinAirWarp taw = new ThinAirWarp(this.game, this.images);
        taw.warp = this.warp;
        return taw;
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
    
    @Override
    public void update(final double delta) {
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
    }
    
    @Override
    public Rectangle getRectangle() {
        return null;
    }
}
