// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.effects;

import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Effect;

public class AirBubble implements Effect
{
    private Game game;
    private ImageIcon image;
    private double yPos;
    private double yVel;
    private double ticks;
    private double theta;
    private int x;
    private int y;
    public static final int PERIOD = 1000;
    public static final int AMPLITUDE = 40;
    public static final int MIN_Y_VELOCITY = 16;
    
    public AirBubble(final Game game) {
        this.game = game;
        this.image = game.textures.airBubble;
        this.yPos = game.mario.y + 8;
        if (game.mario.flip) {
            this.x = game.mario.x + 2;
        }
        else {
            this.x = game.mario.x + game.mario.width - 4;
        }
        if (!game.mario.isLarge() || game.mario.spriteState == 11) {
            this.yPos += 8.0;
        }
        this.y = (int)Math.round(this.yPos);
    }
    
    @Override
    public void update(final double delta) {
        this.ticks = (this.ticks + delta) % 1000.0;
        this.theta = this.ticks / 1000.0 * 3.141592653589793 - 1.5707963267948966;
        this.yVel = 40.0 * Math.cos(this.theta);
        if (this.yVel < 16.0) {
            this.yVel = 16.0;
        }
        this.yPos -= this.yVel * delta / 1000.0;
        this.y = (int)Math.round(this.yPos);
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        g2D.drawImage(this.image.getImage(), this.x, this.y, null);
    }
    
    @Override
    public boolean isFinished() {
        return this.y <= 28.0;
    }
}
