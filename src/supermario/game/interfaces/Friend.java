// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.interfaces;

import java.awt.Rectangle;

public interface Friend
{
    public static final int GROWING_SPEED = 16;
    public static final int BUMP_Y_VELOCITY = -240;
    public static final int BUMP_Y_VELOCITY_WATER = -80;
    
    Rectangle getContactRectangle();
    
    void xCollided();
    
    void absorbed();
    
    void bumped();
}
