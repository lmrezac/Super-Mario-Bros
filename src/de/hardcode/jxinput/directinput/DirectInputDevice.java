// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;

public class DirectInputDevice implements JXInputDevice
{
    int mDeviceIdx;
    private DIAxis[] mAxes;
    private DIButton[] mButtons;
    private DIDirectional[] mDirectionals;
    
    public static int getNumberOfDevices() {
        if (DirectInputDriver.isAvailable()) {
            return DirectInputDriver.getNumberOfDevices();
        }
        return 0;
    }
    
    public static void update() {
        if (DirectInputDriver.isAvailable()) {
            DirectInputDriver.nativeupdate();
        }
    }
    
    public DirectInputDevice(final int mDeviceIdx) {
        this.mDeviceIdx = mDeviceIdx;
        this.init();
    }
    
    public static void reset() {
        if (DirectInputDriver.isAvailable()) {
            DirectInputDriver.reset();
        }
    }
    
    private final void init() {
        this.mAxes = new DIAxis[this.getMaxNumberOfAxes()];
        this.mButtons = new DIButton[this.getMaxNumberOfButtons()];
        this.mDirectionals = new DIDirectional[this.getMaxNumberOfDirectionals()];
        for (int i = 0; i < this.mAxes.length; ++i) {
            if (DirectInputDriver.isAxisAvailable(this.mDeviceIdx, i)) {
                this.mAxes[i] = new DIAxis(this.mDeviceIdx, i);
            }
        }
        for (int j = 0; j < this.mButtons.length; ++j) {
            if (DirectInputDriver.isButtonAvailable(this.mDeviceIdx, j)) {
                this.mButtons[j] = new DIButton(this.mDeviceIdx, j);
            }
        }
        for (int k = 0; k < this.mDirectionals.length; ++k) {
            if (DirectInputDriver.isDirectionalAvailable(this.mDeviceIdx, k)) {
                this.mDirectionals[k] = new DIDirectional(this.mDeviceIdx, k);
            }
        }
    }
    
    public String getName() {
        final String name = DirectInputDriver.getName(this.mDeviceIdx);
        if (null == name) {
            return "Win32 DirectInput Joystick";
        }
        return name;
    }
    
    public int getNumberOfButtons() {
        return DirectInputDriver.getNumberOfButtons(this.mDeviceIdx);
    }
    
    public int getNumberOfAxes() {
        return DirectInputDriver.getNumberOfAxes(this.mDeviceIdx);
    }
    
    public int getNumberOfDirectionals() {
        return DirectInputDriver.getNumberOfDirectionals(this.mDeviceIdx);
    }
    
    public int getMaxNumberOfButtons() {
        return DirectInputDriver.getMaxNumberOfButtons();
    }
    
    public int getMaxNumberOfAxes() {
        return DirectInputDriver.getMaxNumberOfAxes();
    }
    
    public int getMaxNumberOfDirectionals() {
        return DirectInputDriver.getMaxNumberOfDirectionals();
    }
    
    public Axis getAxis(final int n) {
        return this.mAxes[n];
    }
    
    public Button getButton(final int n) {
        return this.mButtons[n];
    }
    
    public Directional getDirectional(final int n) {
        return this.mDirectionals[n];
    }
}
