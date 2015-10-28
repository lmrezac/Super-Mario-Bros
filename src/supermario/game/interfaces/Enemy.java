// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.interfaces;

import supermario.game.Level;
import supermario.game.Sprite;
import java.awt.Rectangle;

public interface Enemy
{
    public static final double ENEMY_DOUBLE_IMAGE_CHANGE_DELAY = 150.0;
    public static final double BUMP_KILL_Y_VELOCITY = -240.0;
    public static final double BUMP_KILL_Y_VELOCITY_WATER = -96.0;
    public static final double BUMP_KILL_X_VELOCITY = 64.0;
    public static final double BUMP_KILL_WATER_GRAVITY = Level.GRAVITY / 4.0;
    
    Rectangle getSpriteContactRectangle();
    
    void bumpKilled(Sprite p0);
    
    void smushed(Sprite p0);
    
    void xCollided();
}
