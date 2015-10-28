// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput;

public interface Button extends Feature
{
    public static final int PUSHBUTTON = 0;
    public static final int TOGGLEBUTTON = 1;
    
    int getType();
    
    boolean getState();
}
