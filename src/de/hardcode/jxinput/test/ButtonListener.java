// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.test;

import de.hardcode.jxinput.event.JXInputButtonEvent;
import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.event.JXInputButtonEventListener;

public class ButtonListener implements JXInputButtonEventListener
{
    public ButtonListener(final Button button) {
        JXInputEventManager.addListener(this, button);
    }
    
    public void changed(final JXInputButtonEvent jxInputButtonEvent) {
        System.out.println("Button " + jxInputButtonEvent.getButton().getName() + " changed : state=" + jxInputButtonEvent.getButton().getState());
    }
}
