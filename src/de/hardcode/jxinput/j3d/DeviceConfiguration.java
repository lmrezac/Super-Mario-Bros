// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.j3d;

import de.hardcode.jxinput.Axis;

public class DeviceConfiguration
{
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 2;
    axisvalue[] mAxisTrans;
    axisvalue[] mAxisRot;
    
    public DeviceConfiguration() {
        this.mAxisTrans = new axisvalue[3];
        this.mAxisRot = new axisvalue[3];
    }
    
    double getTranslational(final int n) {
        final axisvalue axisvalue = this.mAxisTrans[n];
        return (null == axisvalue) ? 0.0 : axisvalue.value();
    }
    
    double getRotational(final int n) {
        final axisvalue axisvalue = this.mAxisRot[n];
        return (null == axisvalue) ? 0.0 : axisvalue.value();
    }
    
    public void setTranslational(final int n, final Axis axis, final IsActiveCondition isActiveCondition, final IsActiveCondition isActiveCondition2, final double n2, final double n3) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException();
        }
        this.mAxisTrans[n] = new axisvalue(axis, isActiveCondition, isActiveCondition2, n2, n3);
    }
    
    public void setRotational(final int n, final Axis axis, final IsActiveCondition isActiveCondition, final IsActiveCondition isActiveCondition2, final double n2, final double n3) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException();
        }
        this.mAxisRot[n] = new axisvalue(axis, isActiveCondition, isActiveCondition2, n2, n3);
    }
    
    private static final class axisvalue
    {
        private final Axis mAxis;
        private final IsActiveCondition mIsActive;
        private final IsActiveCondition mIsIncremental;
        private final double mScale;
        private final double mOffset;
        private double mValue;
        
        axisvalue(final Axis mAxis, final IsActiveCondition mIsActive, final IsActiveCondition mIsIncremental, final double n, final double mScale) {
            this.mAxis = mAxis;
            this.mIsActive = mIsActive;
            this.mIsIncremental = mIsIncremental;
            this.mOffset = n;
            this.mValue = n;
            this.mScale = mScale;
        }
        
        double value() {
            if (this.mIsActive.isActive()) {
                final double n = this.mAxis.getValue() * this.mScale;
                if (this.mIsIncremental.isActive()) {
                    this.mValue += n;
                }
                else {
                    this.mValue = n + this.mOffset;
                }
            }
            return this.mValue;
        }
    }
}
