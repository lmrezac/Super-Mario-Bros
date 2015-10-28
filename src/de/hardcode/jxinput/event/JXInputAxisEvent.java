// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.event;

import de.hardcode.jxinput.Axis;

public class JXInputAxisEvent
{
    private final Axis mAxis;
    double mDelta;
    
    JXInputAxisEvent(final Axis mAxis) {
        this.mAxis = mAxis;
    }
    
    public final Axis getAxis() {
        return this.mAxis;
    }
    
    public double getDelta() {
        return this.mDelta;
    }
}
