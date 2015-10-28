// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput;

public interface JXInputDevice
{
    String getName();
    
    int getNumberOfAxes();
    
    int getNumberOfButtons();
    
    int getNumberOfDirectionals();
    
    int getMaxNumberOfAxes();
    
    int getMaxNumberOfButtons();
    
    int getMaxNumberOfDirectionals();
    
    Axis getAxis(int p0);
    
    Button getButton(int p0);
    
    Directional getDirectional(int p0);
}
