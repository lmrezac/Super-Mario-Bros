// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.util;

import de.hardcode.jxinput.Button;

public class OrButton implements Button
{
    private final Button mButton1;
    private final Button mButton2;
    
    public OrButton(final Button mButton1, final Button mButton2) {
        this.mButton1 = mButton1;
        this.mButton2 = mButton2;
    }
    
    public String getName() {
        return this.mButton1.getName();
    }
    
    public boolean getState() {
        return this.mButton1.getState() || this.mButton2.getState();
    }
    
    public int getType() {
        return this.mButton1.getType();
    }
    
    public boolean hasChanged() {
        return this.mButton1.hasChanged() || this.mButton2.hasChanged();
    }
}
