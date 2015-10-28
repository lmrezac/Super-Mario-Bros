// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import org.tritonus.share.TDebug;
import javax.sound.sampled.EnumControl;

public class TEnumControl extends EnumControl implements TControllable
{
    private TControlController m_controller;
    
    public TEnumControl(final Type type, final Object[] aValues, final Object value) {
        super(type, aValues, value);
        if (TDebug.TraceControl) {
            TDebug.out("TEnumControl.<init>: begin");
        }
        this.m_controller = new TControlController();
        if (TDebug.TraceControl) {
            TDebug.out("TEnumControl.<init>: end");
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
