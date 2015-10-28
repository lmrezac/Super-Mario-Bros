// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import org.tritonus.share.TDebug;
import javax.sound.sampled.Control;
import javax.sound.sampled.CompoundControl;

public class TCompoundControl extends CompoundControl implements TControllable
{
    private TControlController m_controller;
    
    public TCompoundControl(final Type type, final Control[] aMemberControls) {
        super(type, aMemberControls);
        if (TDebug.TraceControl) {
            TDebug.out("TCompoundControl.<init>: begin");
        }
        this.m_controller = new TControlController();
        if (TDebug.TraceControl) {
            TDebug.out("TCompoundControl.<init>: end");
        }
    }
    
    public void setParentControl(final TCompoundControl compoundControl) {
        this.m_controller.setParentControl(compoundControl);
    }
    
    public TCompoundControl getParentControl() {
        return this.m_controller.getParentControl();
    }
    
    public void commit() {
        this.m_controller.commit();
    }
}
