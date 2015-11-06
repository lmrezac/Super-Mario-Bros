// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.virtual;

import java.security.InvalidParameterException;

import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Axis;

public class VirtualAxis implements Axis
{
    private int mType;
    @SuppressWarnings("unused")
	private final int mID;
    private String mName;
    private double mCurrentValue;
    private Button mButtonIncrease;
    private Button mButtonDecrease;
    private double mSpeed;
    private double mSpringSpeed;
    
    public VirtualAxis(final int mid) {
        this.mType = 0;
        this.mName = "VirtualAxis";
        this.mCurrentValue = 0.0;
        this.mButtonIncrease = null;
        this.mButtonDecrease = null;
        this.mSpeed = 0.002;
        this.mSpringSpeed = 0.002;
        this.mID = mid;
    }
    
    public void setType(final int mType) {
        if (1 != mType && 0 != mType && 2 != mType) {
            throw new InvalidParameterException("Invalid type for axis!");
        }
        this.mType = mType;
    }
    
    final void update(final long n) {
        final double n2 = this.mSpeed * n;
        double min = this.mSpringSpeed * n;
        final boolean b = null != this.mButtonIncrease && this.mButtonIncrease.getState();
        final boolean b2 = null != this.mButtonDecrease && this.mButtonDecrease.getState();
        //final boolean b3 = b || b2;
        double n3 = 0.0;
        if (b) {
            n3 += n2;
        }
        if (b2) {
            n3 -= n2;
        }
        this.mCurrentValue += n3;
        if (this.mCurrentValue > 0.0 && !b) {
            min = Math.min(this.mCurrentValue, min);
            this.mCurrentValue -= min;
        }
        if (this.mCurrentValue < 0.0 && !b2) {
            this.mCurrentValue += Math.min(-this.mCurrentValue, min);
        }
        if (this.mCurrentValue > 1.0) {
            this.mCurrentValue = 1.0;
        }
        final double mCurrentValue = (2 == this.mType) ? 0.0 : -1.0;
        if (this.mCurrentValue < mCurrentValue) {
            this.mCurrentValue = mCurrentValue;
        }
    }
    
    public final void setIncreaseButton(final Button mButtonIncrease) {
        if (null == mButtonIncrease) {
            throw new InvalidParameterException("Button may not be null!");
        }
        this.mButtonIncrease = mButtonIncrease;
    }
    
    public final void setButtons(final Button mButtonIncrease, final Button mButtonDecrease) {
        if (null == mButtonIncrease || null == mButtonDecrease) {
            throw new InvalidParameterException("Buttons may not be null!");
        }
        this.mButtonIncrease = mButtonIncrease;
        this.mButtonDecrease = mButtonDecrease;
    }
    
    public final void setSpeed(final double mSpeed) {
        this.mSpeed = mSpeed;
    }
    
    public final void setSpringSpeed(final double mSpringSpeed) {
        this.mSpringSpeed = mSpringSpeed;
    }
    
    public final void setTimeFor0To1(final int n) {
        if (0 >= n) {
            this.mSpeed = 0.0;
        }
        else {
            this.mSpeed = 1.0 / n;
        }
    }
    
    public final void setTimeFor1To0(final int n) {
        if (0 >= n) {
            this.mSpringSpeed = 0.0;
        }
        else {
            this.mSpringSpeed = 1.0 / n;
        }
    }
    
    public final void setName(final String mName) {
        this.mName = mName;
    }
    
    public String getName() {
        return this.mName;
    }
    
    public double getResolution() {
        return 1.52587890625E-5;
    }
    
    public int getType() {
        return this.mType;
    }
    
    public double getValue() {
        return this.mCurrentValue;
    }
    
    public boolean hasChanged() {
        return true;
    }
}
