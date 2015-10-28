// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import javax.sound.sampled.Control;
import java.util.Collection;
import javax.sound.sampled.Line;
import javax.sound.sampled.Port;

public class TPort extends TLine implements Port
{
    public TPort(final TMixer mixer, final Line.Info info) {
        super(mixer, info);
    }
    
    public TPort(final TMixer mixer, final Line.Info info, final Collection<Control> controls) {
        super(mixer, info, controls);
    }
}
