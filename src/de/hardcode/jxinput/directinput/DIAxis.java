// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Axis;

class DIAxis implements Axis
{
    private final int mDeviceIdx;
    private final int mIdx;
    
    DIAxis(final int mDeviceIdx, final int mIdx) {
        this.mDeviceIdx = mDeviceIdx;
        this.mIdx = mIdx;
    }
    
    public String getName() {
        return DirectInputDriver.getAxisName(this.mDeviceIdx, this.mIdx);
    }
    
    public boolean hasChanged() {
        return true;
    }
    
    public double getValue() {
        return DirectInputDriver.getAxisValue(this.mDeviceIdx, this.mIdx);
    }
    
    public int getType() {
        return DirectInputDriver.getAxisType(this.mDeviceIdx, this.mIdx);
    }
    
    public double getResolution() {
        return (this.getType() == 2) ? 1.52587890625E-5 : 3.0517578125E-5;
    }
}
