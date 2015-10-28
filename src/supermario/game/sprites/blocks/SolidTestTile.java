// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.blocks;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import supermario.Utilities;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class SolidTestTile extends Sprite
{
    private float alpha;
    
    public SolidTestTile(final Game game, final ImageIcon[] images) {
        super(game, images);
        this.alpha = 1.0f;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        final int pulseIndex = Utilities.getPulsingImageIndex();
        if (pulseIndex == 2) {
            this.alpha = 0.5f;
        }
        else if (pulseIndex == 1) {
            this.alpha = 0.7f;
        }
        else if (pulseIndex == 0) {
            this.alpha = 0.9f;
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        final Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, this.alpha));
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        g2D.drawImage(this.images[0].getImage(), this.transform, null);
        g2D.setComposite(oldComposite);
    }
    
    @Override
    public Rectangle getRectangle() {
        return null;
    }
}
