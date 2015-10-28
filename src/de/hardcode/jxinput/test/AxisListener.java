// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.test;

import de.hardcode.jxinput.event.JXInputAxisEvent;
import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.event.JXInputAxisEventListener;

public class AxisListener implements JXInputAxisEventListener
{
    public AxisListener(final Axis axis) {
        JXInputEventManager.addListener(this, axis, 0.1);
    }
    
    public void changed(final JXInputAxisEvent jxInputAxisEvent) {
        System.out.println("Axis " + jxInputAxisEvent.getAxis().getName() + " changed : value=" + jxInputAxisEvent.getAxis().getValue() + ", event causing delta=" + jxInputAxisEvent.getDelta());
    }
}
