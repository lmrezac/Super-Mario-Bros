// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Button;

class DIButton implements Button
{
    private final int mDeviceIdx;
    private final int mIdx;
    
    DIButton(final int mDeviceIdx, final int mIdx) {
        this.mDeviceIdx = mDeviceIdx;
        this.mIdx = mIdx;
    }
    
    public String getName() {
        return DirectInputDriver.getButtonName(this.mDeviceIdx, this.mIdx);
    }
    
    public boolean hasChanged() {
        return true;
    }
    
    public int getType() {
        return DirectInputDriver.getButtonType(this.mDeviceIdx, this.mIdx);
    }
    
    public boolean getState() {
        return DirectInputDriver.getButtonState(this.mDeviceIdx, this.mIdx);
    }
}
