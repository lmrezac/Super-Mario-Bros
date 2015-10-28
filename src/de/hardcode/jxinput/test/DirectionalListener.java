// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.test;

import de.hardcode.jxinput.event.JXInputDirectionalEvent;
import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.event.JXInputDirectionalEventListener;

public class DirectionalListener implements JXInputDirectionalEventListener
{
    public DirectionalListener(final Directional directional) {
        JXInputEventManager.addListener(this, directional, 1.0);
    }
    
    public void changed(final JXInputDirectionalEvent jxInputDirectionalEvent) {
        System.out.println("Directional " + jxInputDirectionalEvent.getDirectional().getName() + " changed : direction=" + jxInputDirectionalEvent.getDirectional().getDirection() + ", value=" + jxInputDirectionalEvent.getDirectional().getValue() + ", event causing delta=" + jxInputDirectionalEvent.getDirectionDelta());
    }
}
