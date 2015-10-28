// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.event;

import de.hardcode.jxinput.Button;

public class JXInputButtonEvent
{
    final Button mButton;
    
    JXInputButtonEvent(final Button mButton) {
        this.mButton = mButton;
    }
    
    public final Button getButton() {
        return this.mButton;
    }
}
