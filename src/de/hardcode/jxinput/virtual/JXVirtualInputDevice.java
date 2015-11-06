// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.virtual;

import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputDevice;

public class JXVirtualInputDevice implements JXInputDevice
{
    @SuppressWarnings("unused")
	private static final String DEVICENAME = "Virtual Device";
    private final VirtualDriver mDriver;
    
    public JXVirtualInputDevice() {
        this.mDriver = new VirtualDriver();
    }
    
    public final void update(final long n) {
        this.mDriver.update(n);
    }
    
    public VirtualAxis createAxis(final int n) {
        final VirtualAxis virtualAxis = new VirtualAxis(n);
        this.mDriver.registerVirtualAxis(n, virtualAxis);
        return virtualAxis;
    }
    
    public void removeAxis(final VirtualAxis virtualAxis) {
        this.mDriver.unregisterVirtualAxis(virtualAxis);
    }
    
    public Axis getAxis(final int n) {
        return this.mDriver.getAxis(n);
    }
    
    public Button getButton(final int n) {
        return null;
    }
    
    public Directional getDirectional(final int n) {
        return null;
    }
    
    public int getMaxNumberOfAxes() {
        return 8;
    }
    
    public int getMaxNumberOfButtons() {
        return 0;
    }
    
    public int getMaxNumberOfDirectionals() {
        return 0;
    }
    
    public String getName() {
        return "Virtual Device";
    }
    
    public int getNumberOfAxes() {
        return this.mDriver.getNumberOfAxes();
    }
    
    public int getNumberOfButtons() {
        return 0;
    }
    
    public int getNumberOfDirectionals() {
        return 0;
    }
}
