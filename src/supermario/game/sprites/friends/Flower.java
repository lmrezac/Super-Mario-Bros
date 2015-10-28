// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.friends;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Block;
import supermario.game.interfaces.Friend;
import supermario.game.Sprite;

public class Flower extends Sprite implements Friend
{
    private Block block;
    private boolean absorbed;
    private int startingY;
    private boolean growing;
    private boolean lightFlower;
    private int generalImageIndex;
    public static final int IMAGE_CHANGE_DELAY = 50;
    
    public Flower(final Game game, final ImageIcon[] images, final Block block) {
        super(game, images);
        this.block = block;
        this.lightFlower = (game.level.levelType != 1);
        final Sprite tempSprite = (Sprite)block;
        this.xPos = tempSprite.x;
        this.yPos = tempSprite.y;
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
        this.startingY = (int)this.yPos;
        this.growing = true;
        game.audio.play(9);
        this.absorbed = false;
        this.xVel = 0.0;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.ticks > 50.0) {
            this.generalImageIndex = (this.generalImageIndex + 1) % (this.images.length / 2);
            this.ticks -= 50.0;
        }
        if (this.growing) {
            this.yPos -= 16.0 * delta / 1000.0;
            if (this.yPos < this.startingY - 16) {
                this.yPos = this.startingY - 16;
                this.growing = false;
            }
        }
        this.finalizePosition();
        if (!this.lightFlower) {
            this.imageIndex = this.generalImageIndex + this.images.length / 2;
        }
        else {
            this.imageIndex = this.generalImageIndex;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public void checkForUnsettledTiles() {
    }
    
    @Override
    public Rectangle getContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void xCollided() {
    }
    
    @Override
    public void bumped() {
    }
    
    @Override
    public void absorbed() {
        if (this.absorbed) {
            return;
        }
        if (this.growing && this.game.mario.y + this.game.mario.height - this.game.mario.avoidedCollisionRowsOnBottom > ((Sprite)this.block).y) {
            return;
        }
        if (!this.growing && this.game.mario.pixelsToMove != 0.0) {
            return;
        }
        final Mario mario = this.game.mario;
        mario.points += 1000;
        this.game.level.effectsToAdd.add(new Points(this.game, this.x + this.width / 2, this.y, 5));
        this.visible = false;
        this.game.audio.play(1);
        this.visible = false;
        this.absorbed = true;
        this.game.mario.grow(true);
    }
}
