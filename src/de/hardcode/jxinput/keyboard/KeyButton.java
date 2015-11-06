// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.keyboard;

import java.awt.event.KeyEvent;

import de.hardcode.jxinput.Button;

class KeyButton implements Button
{
    private final int mKeyCode;
    private boolean mIsPressed;
    @SuppressWarnings("unused")
	private boolean mHasChanged;
    
    public KeyButton(final int mKeyCode) {
        this.mKeyCode = mKeyCode;
    }
    
    public final int getKeyCode() {
        return this.mKeyCode;
    }
    
    final void setIsPressed(final boolean mIsPressed) {
        this.mIsPressed = mIsPressed;
    }
    
    public String getName() {
        return KeyEvent.getKeyText(this.mKeyCode);
    }
    
    public boolean getState() {
        return this.mIsPressed;
    }
    
    public int getType() {
        return 0;
    }
    
    public boolean hasChanged() {
        return true;
    }
}
