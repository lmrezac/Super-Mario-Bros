// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.interfaces;

import java.awt.Graphics2D;

public interface Effect
{
    void update(double p0);
    
    void draw(Graphics2D p0);
    
    boolean isFinished();
}
