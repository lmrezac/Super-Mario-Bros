// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyboardDriver implements KeyListener
{
    int mNumberOfKeysObserved;
    KeyButton[] mKeysObserved;
    
    public KeyboardDriver() {
        this.mNumberOfKeysObserved = 0;
        this.mKeysObserved = new KeyButton[256];
    }
    
    final int getNumberOfButtons() {
        return this.mNumberOfKeysObserved;
    }
    
    final boolean registerKeyButton(final KeyButton keyButton) {
        final int keyCode = keyButton.getKeyCode();
        if (0 > keyCode || 256 < keyCode) {
            throw new InvalidKeyCodeException();
        }
        if (null == this.mKeysObserved[keyCode]) {
            this.mKeysObserved[keyCode] = keyButton;
            ++this.mNumberOfKeysObserved;
            return true;
        }
        return false;
    }
    
    final void unregisterKeyButton(final KeyButton keyButton) {
        final int keyCode = keyButton.getKeyCode();
        if (0 > keyCode || 256 < keyCode) {
            throw new InvalidKeyCodeException();
        }
        if (null != this.mKeysObserved[keyButton.getKeyCode()]) {
            this.mKeysObserved[keyCode] = null;
            --this.mNumberOfKeysObserved;
        }
    }
    
    final KeyButton getButton(final int n) {
        if (0 > n || 256 < n) {
            throw new InvalidKeyCodeException();
        }
        return this.mKeysObserved[n];
    }
    
    public void keyPressed(final KeyEvent keyEvent) {
        final KeyButton button = this.getButton(keyEvent.getKeyCode());
        if (null != button) {
            button.setIsPressed(true);
        }
    }
    
    public void keyReleased(final KeyEvent keyEvent) {
        final KeyButton button = this.getButton(keyEvent.getKeyCode());
        if (null != button) {
            button.setIsPressed(false);
        }
    }
    
    public void keyTyped(final KeyEvent keyEvent) {
    }
}
