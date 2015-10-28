// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import org.tritonus.share.TDebug;

public class TControlController implements TControllable
{
    private TCompoundControl m_parentControl;
    
    public void setParentControl(final TCompoundControl compoundControl) {
        this.m_parentControl = compoundControl;
    }
    
    public TCompoundControl getParentControl() {
        return this.m_parentControl;
    }
    
    public void commit() {
        if (TDebug.TraceControl) {
            TDebug.out("TControlController.commit(): called [" + this.getClass().getName() + "]");
        }
        if (this.getParentControl() != null) {
            this.getParentControl().commit();
        }
    }
}
