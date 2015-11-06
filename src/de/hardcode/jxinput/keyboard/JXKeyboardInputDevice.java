// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.keyboard;

import java.awt.Component;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.JXInputDevice;

public class JXKeyboardInputDevice implements JXInputDevice
{
    @SuppressWarnings("unused")
	private static final String DEVICENAME = "Swing Keyboard";
    private final KeyboardDriver mDriver;
    private Component mComponent;
    private int mMaxIdxCreated;
    
    public JXKeyboardInputDevice() {
        this.mDriver = new KeyboardDriver();
        this.mComponent = null;
        this.mMaxIdxCreated = 0;
    }
    
    public JXKeyboardInputDevice(final Component component) {
        this.mDriver = new KeyboardDriver();
        this.mComponent = null;
        this.mMaxIdxCreated = 0;
        this.listenTo(component);
    }
    
    public final void listenTo(final Component mComponent) {
        this.shutdown();
        (this.mComponent = mComponent).addKeyListener(this.mDriver);
    }
    
    public final void shutdown() {
        if (null != this.mComponent) {
            this.mComponent.removeKeyListener(this.mDriver);
        }
    }
    
    public Button createButton(final int mMaxIdxCreated) {
        if (0 > mMaxIdxCreated || 256 < mMaxIdxCreated) {
            throw new InvalidKeyCodeException();
        }
        KeyButton button;
        if (null == (button = this.mDriver.getButton(mMaxIdxCreated))) {
            button = new KeyButton(mMaxIdxCreated);
            this.mDriver.registerKeyButton(button);
            if (mMaxIdxCreated > this.mMaxIdxCreated) {
                this.mMaxIdxCreated = mMaxIdxCreated;
            }
        }
        return button;
    }
    
    public void removeButton(final Button button) {
        this.mDriver.unregisterKeyButton((KeyButton)button);
    }
    
    public Axis getAxis(final int n) {
        return null;
    }
    
    public Button getButton(final int n) {
        return this.mDriver.getButton(n);
    }
    
    public Directional getDirectional(final int n) {
        return null;
    }
    
    public int getMaxNumberOfAxes() {
        return 0;
    }
    
    public int getMaxNumberOfButtons() {
        return this.mMaxIdxCreated + 1;
    }
    
    public int getMaxNumberOfDirectionals() {
        return 0;
    }
    
    public String getName() {
        return "Swing Keyboard";
    }
    
    public int getNumberOfAxes() {
        return 0;
    }
    
    public int getNumberOfButtons() {
        return this.mDriver.getNumberOfButtons();
    }
    
    public int getNumberOfDirectionals() {
        return 0;
    }
}
