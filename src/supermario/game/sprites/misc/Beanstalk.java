// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import supermario.game.interfaces.Block;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class Beanstalk extends Sprite
{
    private int stalks;
    private int startingY;
    private int imageHeight;
    public boolean growing;
    public Sprite block;
    public static final int GROWING_RATE = 48;
    public static final int HEIGHT_ABOVE_SCREEN_GROWTH = 24;
    
    public Beanstalk(final Game game, final ImageIcon[] images, final Block block) {
        super(game, images);
        game.audio.play(11);
        this.stalks = 1;
        this.growing = true;
        this.yVel = 48.0;
        this.block = (Sprite)block;
        this.xPos = this.block.x;
        this.yPos = this.block.y;
        this.xTile = this.block.x / 8;
        this.yTile = this.block.y / 8;
        this.startingY = this.block.y + this.block.height;
        this.imageHeight = this.height;
        this.finalizePosition();
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.growing) {
            this.yPos -= this.yVel * delta / 1000.0;
            if (this.y <= -24) {
                this.growing = false;
                this.yVel = 0.0;
            }
            this.stalks = (this.startingY - this.y) / this.imageHeight;
            this.height = this.stalks * this.imageHeight;
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
        if (this.game.level.levelType == 1 || this.game.level.levelType == 2) {
            g2D.drawImage(this.images[2].getImage(), this.transform, null);
        }
        else {
            g2D.drawImage(this.images[0].getImage(), this.transform, null);
        }
        for (int i = 1; i < this.stalks; ++i) {
            this.transform.translate(0.0, this.imageHeight);
            if (this.game.level.levelType == 1 || this.game.level.levelType == 2) {
                g2D.drawImage(this.images[3].getImage(), this.transform, null);
            }
            else {
                g2D.drawImage(this.images[1].getImage(), this.transform, null);
            }
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        final Rectangle marioRect = this.game.mario.getRectangle();
        return new Rectangle(this.x + 8 - 1, this.y + marioRect.height, 2, this.height - marioRect.height);
    }
}
