// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import org.tritonus.share.TDebug;
import javax.sound.sampled.BooleanControl;

public class TBooleanControl extends BooleanControl implements TControllable
{
    private TControlController m_controller;
    
    public TBooleanControl(final Type type, final boolean bInitialValue) {
        this(type, bInitialValue, null);
    }
    
    public TBooleanControl(final Type type, final boolean bInitialValue, final TCompoundControl parentControl) {
        super(type, bInitialValue);
        if (TDebug.TraceControl) {
            TDebug.out("TBooleanControl.<init>: begin");
        }
        this.m_controller = new TControlController();
        if (TDebug.TraceControl) {
            TDebug.out("TBooleanControl.<init>: end");
        }
    }
    
    public TBooleanControl(final Type type, final boolean bInitialValue, final String strTrueStateLabel, final String strFalseStateLabel) {
        this(type, bInitialValue, strTrueStateLabel, strFalseStateLabel, null);
    }
    
    public TBooleanControl(final Type type, final boolean bInitialValue, final String strTrueStateLabel, final String strFalseStateLabel, final TCompoundControl parentControl) {
        super(type, bInitialValue, strTrueStateLabel, strFalseStateLabel);
        if (TDebug.TraceControl) {
            TDebug.out("TBooleanControl.<init>: begin");
        }
        this.m_controller = new TControlController();
        if (TDebug.TraceControl) {
            TDebug.out("TBooleanControl.<init>: end");
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
