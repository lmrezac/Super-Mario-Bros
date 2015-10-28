// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.event;

import de.hardcode.jxinput.Directional;

public class JXInputDirectionalEvent
{
    private final Directional mDirectional;
    double mValueDelta;
    int mDirectionDelta;
    
    JXInputDirectionalEvent(final Directional mDirectional) {
        this.mDirectional = mDirectional;
    }
    
    public final Directional getDirectional() {
        return this.mDirectional;
    }
    
    public double getValueDelta() {
        return this.mValueDelta;
    }
    
    public int getDirectionDelta() {
        return this.mDirectionDelta;
    }
}
