// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.virtual;

import de.hardcode.jxinput.Axis;

class VirtualDriver
{
    private final VirtualAxis[] mVAxes;
    
    VirtualDriver() {
        this.mVAxes = new VirtualAxis[8];
    }
    
    final void update(final long n) {
        for (int i = 0; i < this.mVAxes.length; ++i) {
            if (null != this.mVAxes[i]) {
                this.mVAxes[i].update(n);
            }
        }
    }
    
    final int getNumberOfAxes() {
        int n = 0;
        for (int i = 0; i < this.mVAxes.length; ++i) {
            if (null != this.mVAxes[i]) {
                ++n;
            }
        }
        return n;
    }
    
    Axis getAxis(final int n) {
        return this.mVAxes[n];
    }
    
    final void registerVirtualAxis(final int n, final VirtualAxis virtualAxis) {
        this.mVAxes[n] = virtualAxis;
    }
    
    final void unregisterVirtualAxis(final VirtualAxis virtualAxis) {
        for (int i = 0; i < this.mVAxes.length; ++i) {
            if (this.mVAxes[i] == virtualAxis) {
                this.mVAxes[i] = null;
                break;
            }
        }
    }
}
