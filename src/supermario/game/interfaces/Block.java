// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.interfaces;

import supermario.game.Sprite;

public interface Block
{
    public static final double BUMP_GRAVITY = 80.0;
    public static final double BUMP_VELOCITY = -36.0;
    
    void bumped(Sprite p0);
    
    void setNormalYPosition(int p0);
}
