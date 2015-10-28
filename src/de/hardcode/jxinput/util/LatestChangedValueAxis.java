// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.util;

import de.hardcode.jxinput.Axis;

public class LatestChangedValueAxis implements Axis
{
    private final Axis mAxis1;
    private final Axis mAxis2;
    private Axis mAxisInUse;
    private double mSaved1;
    private double mSaved2;
    
    public LatestChangedValueAxis(final Axis axis, final Axis mAxis2) {
        this.mAxis1 = axis;
        this.mAxis2 = mAxis2;
        this.mAxisInUse = axis;
        this.mSaved1 = axis.getValue();
        this.mSaved2 = mAxis2.getValue();
    }
    
    public String getName() {
        return this.mAxis1.getName();
    }
    
    public double getResolution() {
        return this.mAxis1.getResolution();
    }
    
    public int getType() {
        return this.mAxis1.getType();
    }
    
    public double getValue() {
        final double value = this.mAxis1.getValue();
        final double value2 = this.mAxis2.getValue();
        if (Math.abs(value2 - this.mSaved2) > 0.2) {
            this.mAxisInUse = this.mAxis2;
            this.mSaved2 = value2;
        }
        if (Math.abs(value - this.mSaved1) > 0.2) {
            this.mAxisInUse = this.mAxis1;
            this.mSaved1 = value;
        }
        return this.mAxisInUse.getValue();
    }
    
    public boolean hasChanged() {
        return true;
    }
}
