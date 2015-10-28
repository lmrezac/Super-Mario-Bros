// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.interfaces;

public interface Shelled
{
    public static final int TIME_SHELL_STUCK_BOUNCING_BEFORE_SUICIDE = 1000;
    
    boolean isLaunched();
    
    boolean isDangerous();
    
    boolean isShelled();
    
    void shellKilled();
}
