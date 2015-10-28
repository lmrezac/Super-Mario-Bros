// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Directional;

class DIDirectional implements Directional
{
    private final int mDeviceIdx;
    private final int mIdx;
    
    DIDirectional(final int mDeviceIdx, final int mIdx) {
        this.mDeviceIdx = mDeviceIdx;
        this.mIdx = mIdx;
    }
    
    public String getName() {
        return DirectInputDriver.getDirectionalName(this.mDeviceIdx, this.mIdx);
    }
    
    public boolean hasChanged() {
        return true;
    }
    
    public boolean isCentered() {
        return 0xFFFF == (DirectInputDriver.getDirection(this.mDeviceIdx, this.mIdx) & 0xFFFF);
    }
    
    public int getDirection() {
        return this.isCentered() ? 0 : DirectInputDriver.getDirection(this.mDeviceIdx, this.mIdx);
    }
    
    public double getValue() {
        if (this.isCentered()) {
            return 0.0;
        }
        return 1.0;
    }
    
    public double getResolution() {
        return 1.0;
    }
}
