// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.effects;

import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import supermario.game.Sprite;
import supermario.game.interfaces.Block;
import supermario.game.sprites.friends.Coin;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Effect;

public class BumpCoin implements Effect
{
    private Game game;
    private ImageIcon[] coinImages;
    private int ticks;
    private int imageIndex;
    private double xPos;
    private double yPos;
    private double yVel;
    private double originalYPos;
    private final int COIN_EFFECT_GRAVITY = 1200;
    private final int COIN_EFFECT_INITIAL_Y_VELOCITY = -320;
    private static final int IMAGE_CHANGE_DELAY = 50;
    
    public BumpCoin(final Game game, final Coin coin) {
        this.game = game;
        this.initImages();
        this.xPos = coin.x;
        this.yPos = coin.y;
        this.originalYPos = this.yPos;
        this.yVel = -320.0;
        game.audio.play(12);
    }
    
    public BumpCoin(final Game game, final Block block, final boolean playSound) {
        this.game = game;
        this.initImages();
        this.xPos = ((Sprite)block).x;
        this.yPos = ((Sprite)block).y - 16;
        this.originalYPos = this.yPos;
        this.yVel = -320.0;
        if (playSound) {
            game.audio.play(12);
        }
    }
    
    public BumpCoin(final Game game, final Sprite sprite) {
        this.game = game;
        this.initImages();
        this.xPos = sprite.x + sprite.width / 2 - 8;
        this.yPos = sprite.y - 16;
        this.originalYPos = this.yPos;
        this.yVel = -320.0;
        game.audio.play(12);
    }
    
    private void initImages() {
        (this.coinImages = new ImageIcon[4])[0] = this.game.textures.coinCollected1;
        this.coinImages[1] = this.game.textures.coinCollected2;
        this.coinImages[2] = this.game.textures.coinCollected3;
        this.coinImages[3] = this.game.textures.coinCollected4;
    }
    
    @Override
    public void update(final double delta) {
        this.ticks += (int)Math.round(delta);
        if (this.ticks > 50) {
            this.imageIndex = (this.imageIndex + 1) % this.coinImages.length;
            this.ticks -= 50;
        }
        this.yVel += 1200.0 * delta / 1000.0;
        this.yPos += this.yVel * delta / 1000.0;
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        g2D.drawImage(this.coinImages[this.imageIndex].getImage(), (int)Math.round(this.xPos), (int)Math.round(this.yPos), null);
    }
    
    @Override
    public boolean isFinished() {
        if (this.yPos > this.originalYPos - 8.0 && this.yVel > 0.0) {
            this.game.level.effectsToAdd.add(new Points(this.game, this.xPos + this.coinImages[0].getIconWidth() / 2, this.yPos, 1));
            return true;
        }
        return false;
    }
}
