// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.Sprite;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;

public class ArrivalVine extends Sprite implements Warpable
{
    public Warp warp;
    public boolean growing;
    
    public ArrivalVine(final Game game, final ImageIcon[] images) {
        super(game, images);
        this.yVel = 48.0;
    }
    
    @Override
    public Sprite reset() {
        final ArrivalVine av = new ArrivalVine(this.game, this.images);
        av.warp = this.warp;
        return av;
    }
    
    public void grow() {
        this.growing = true;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.growing) {
            this.yPos -= this.yVel * delta / 1000.0;
            if (this.yPos <= Game.renderHeight - this.height) {
                this.yPos = Game.renderHeight - this.height;
                this.growing = false;
                this.spriteState = 2;
            }
        }
        this.finalizePosition();
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        g2D.drawImage(this.images[0].getImage(), this.transform, null);
    }
    
    @Override
    public Rectangle getRectangle() {
        return null;
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
}
