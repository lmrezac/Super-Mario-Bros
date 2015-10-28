// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.j3d;

public interface IsActiveCondition
{
    public static final IsActiveCondition ALWAYS = IsAlwaysActiveCondition.ALWAYS;
    public static final IsActiveCondition NEVER = IsAlwaysActiveCondition.NEVER;
    
    boolean isActive();
}
