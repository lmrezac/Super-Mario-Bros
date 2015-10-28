// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.j3d;

final class IsAlwaysActiveCondition implements IsActiveCondition
{
    private final boolean mIsActive;
    public static final IsActiveCondition ALWAYS;
    public static final IsActiveCondition NEVER;
    
    private IsAlwaysActiveCondition(final boolean mIsActive) {
        this.mIsActive = mIsActive;
    }
    
    public boolean isActive() {
        return this.mIsActive;
    }
    
    static {
        ALWAYS = new IsAlwaysActiveCondition(true);
        NEVER = new IsAlwaysActiveCondition(false);
    }
}
