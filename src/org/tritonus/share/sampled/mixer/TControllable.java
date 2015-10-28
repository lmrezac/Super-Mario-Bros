// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

public interface TControllable
{
    void setParentControl(TCompoundControl p0);
    
    TCompoundControl getParentControl();
    
    void commit();
}
