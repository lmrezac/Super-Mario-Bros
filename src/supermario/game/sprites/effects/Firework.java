// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.effects;

import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.Point;
import supermario.game.sprites.friends.FireballFriend;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Effect;

public class Firework implements Effect
{
    private Game game;
    private ImageIcon[] fireworkImages;
    private int imageIndex;
    private int x;
    private int y;
    private double ticks;
    private double width;
    private double height;
    private boolean finished;
    private static final double IMAGE_CHANGE_DELAY = 70.0;
    
    public Firework(final Game game, final FireballFriend fireballFriend) {
        this.game = game;
        this.init();
        this.x = (int)Math.round(fireballFriend.xPos + fireballFriend.width / 2 - this.width / 2.0);
        this.y = (int)Math.round(fireballFriend.yPos + fireballFriend.height / 2 - this.height / 2.0);
    }
    
    public Firework(final Game game, final Point centerLocation) {
        this.game = game;
        this.init();
        this.x = (int)Math.round(centerLocation.x - this.width / 2.0);
        this.y = (int)Math.round(centerLocation.y - this.height / 2.0);
        game.audio.play(13);
    }
    
    private void init() {
        this.fireworkImages = this.game.textures.getFireworkTextures();
        this.width = this.fireworkImages[0].getIconWidth();
        this.height = this.fireworkImages[0].getIconHeight();
        this.imageIndex = 0;
        this.finished = false;
    }
    
    @Override
    public void update(final double delta) {
        this.ticks += delta;
        if (this.ticks > 70.0) {
            this.ticks -= 70.0;
            ++this.imageIndex;
            if (this.imageIndex == this.fireworkImages.length) {
                this.imageIndex = this.fireworkImages.length - 1;
                this.finished = true;
            }
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        g2D.drawImage(this.fireworkImages[this.imageIndex].getImage(), this.x, this.y, null);
    }
    
    @Override
    public boolean isFinished() {
        return this.finished;
    }
}
