// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import supermario.game.sprites.effects.Points;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Sprite;

public class Checkpoint extends Sprite
{
    private float trans;
    public int levelNumber;
    public int type;
    private boolean grabbed;
    public static final float TRANS_REDUCTION_RATE = 3.0f;
    public static final int TYPE_VISIBLE_FLAG = 0;
    public static final int TYPE_INVISIBLE_COLUMN = 1;
    
    public Checkpoint(final Game game, final ImageIcon[] images, final int type) {
        super(game, images);
        this.trans = 1.0f;
        this.grabbed = false;
        this.type = type;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    public void activate() {
        if (this.game.mario.getXCenter() > this.x) {
            this.visible = false;
        }
        else {
            this.game.level.checkpoints.add(this);
        }
        this.levelNumber = this.game.level.levelNumber;
    }
    
    public boolean isFinished() {
        return !this.visible || this.x + this.width < this.game.level.leftMostX;
    }
    
    @Override
    public void update(final double delta) {
        if (this.grabbed) {
            if (this.type == 1) {
                this.visible = false;
            }
            else {
                this.trans -= (float)(3.0 * delta / 1000.0);
                if (this.trans < 0.0f) {
                    this.trans = 0.0f;
                    this.visible = false;
                }
            }
        }
    }
    
    public void gotCheckpoint() {
        if (!this.grabbed) {
            this.game.mario.checkpoint = this;
            this.grabbed = true;
            if (this.type == 0) {
                this.game.audio.play(0);
                this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 9));
            }
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible || this.type == 1) {
            return;
        }
        final Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, this.trans));
        g2D.drawImage(this.images[0].getImage(), this.x, this.y, null);
        g2D.setComposite(oldComposite);
    }
    
    @Override
    public Rectangle getRectangle() {
        if (this.type == 0) {
            return new Rectangle(this.x, this.y, this.width, this.height);
        }
        return new Rectangle(this.x, 0, this.width, Game.renderHeight);
    }
}
