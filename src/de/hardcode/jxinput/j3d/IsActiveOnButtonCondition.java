// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.j3d;

import de.hardcode.jxinput.Button;

public class IsActiveOnButtonCondition implements IsActiveCondition
{
    private final boolean mActiveState;
    private final Button mButton;
    
    public IsActiveOnButtonCondition(final Button mButton, final boolean mActiveState) {
        this.mActiveState = mActiveState;
        this.mButton = mButton;
    }
    
    public boolean isActive() {
        return this.mButton.getState() == this.mActiveState;
    }
}
