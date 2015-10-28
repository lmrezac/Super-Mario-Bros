// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import org.tritonus.share.TDebug;
import javax.sound.sampled.FloatControl;

public class TFloatControl extends FloatControl implements TControllable
{
    private TControlController m_controller;
    
    public TFloatControl(final Type type, final float fMinimum, final float fMaximum, final float fPrecision, final int nUpdatePeriod, final float fInitialValue, final String strUnits) {
        super(type, fMinimum, fMaximum, fPrecision, nUpdatePeriod, fInitialValue, strUnits);
        if (TDebug.TraceControl) {
            TDebug.out("TFloatControl.<init>: begin");
        }
        this.m_controller = new TControlController();
        if (TDebug.TraceControl) {
            TDebug.out("TFloatControl.<init>: end");
        }
    }
    
    public TFloatControl(final Type type, final float fMinimum, final float fMaximum, final float fPrecision, final int nUpdatePeriod, final float fInitialValue, final String strUnits, final String strMinLabel, final String strMidLabel, final String strMaxLabel) {
        super(type, fMinimum, fMaximum, fPrecision, nUpdatePeriod, fInitialValue, strUnits, strMinLabel, strMidLabel, strMaxLabel);
        if (TDebug.TraceControl) {
            TDebug.out("TFloatControl.<init>: begin");
        }
        this.m_controller = new TControlController();
        if (TDebug.TraceControl) {
            TDebug.out("TFloatControl.<init>: end");
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
