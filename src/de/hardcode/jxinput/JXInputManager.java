// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput;

import java.awt.Component;
import java.util.Iterator;

import de.hardcode.jxinput.virtual.JXVirtualInputDevice;
import de.hardcode.jxinput.keyboard.JXKeyboardInputDevice;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.JXInputEventManager;

import java.util.ArrayList;

public class JXInputManager
{
    private static long mTimeOfLastUpdate;
    private static final ArrayList<JXInputDevice> mDevices;
    private static final ArrayList<DirectInputDevice> mDIDevices;
    private static final ArrayList<JXVirtualInputDevice> mVirtualDevices;
    private static final ArrayList<JXKeyboardInputDevice> mKBDevices;
    
    public static int getNumberOfDevices() {
        return JXInputManager.mDevices.size();
    }
    
    public static JXInputDevice getJXInputDevice(final int n) {
        if (n >= JXInputManager.mDevices.size()) {
            return null;
        }
        return JXInputManager.mDevices.get(n);
    }
    
    public static synchronized void reset() {
        JXInputEventManager.reset();
        JXInputManager.mDevices.clear();
        JXInputManager.mVirtualDevices.clear();
        JXInputManager.mDIDevices.clear();
        DirectInputDevice.reset();
        for (int i = 0; i < DirectInputDevice.getNumberOfDevices(); ++i) {
            final DirectInputDevice directInputDevice = new DirectInputDevice(i);
            JXInputManager.mDevices.add(directInputDevice);
            JXInputManager.mDIDevices.add(directInputDevice);
        }
        DirectInputDevice.update();
        for (int size = JXInputManager.mKBDevices.size(), j = 0; j < size; ++j) {
            JXInputManager.mKBDevices.get(j).shutdown();
        }
        JXInputManager.mKBDevices.clear();
    }
    
    public static void updateFeatures() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - JXInputManager.mTimeOfLastUpdate;
        DirectInputDevice.update();
        final Iterator<JXVirtualInputDevice> iterator = (Iterator<JXVirtualInputDevice>)JXInputManager.mVirtualDevices.iterator();
        while (iterator.hasNext()) {
            iterator.next().update(n);
        }
        JXInputManager.mTimeOfLastUpdate = currentTimeMillis;
        JXInputEventManager.trigger();
    }
    
    public static long getLastUpdateTime() {
        return JXInputManager.mTimeOfLastUpdate;
    }
    
    public static JXKeyboardInputDevice createKeyboardDevice() {
        final JXKeyboardInputDevice jxKeyboardInputDevice = new JXKeyboardInputDevice();
        JXInputManager.mDevices.add(jxKeyboardInputDevice);
        JXInputManager.mKBDevices.add(jxKeyboardInputDevice);
        return jxKeyboardInputDevice;
    }
    
    public static JXKeyboardInputDevice createKeyboardDevice(final Component component) {
        final JXKeyboardInputDevice jxKeyboardInputDevice = new JXKeyboardInputDevice(component);
        JXInputManager.mDevices.add(jxKeyboardInputDevice);
        JXInputManager.mKBDevices.add(jxKeyboardInputDevice);
        return jxKeyboardInputDevice;
    }
    
    public static void deleteKeyboardDevice(final JXKeyboardInputDevice jxKeyboardInputDevice) {
        JXInputManager.mDevices.remove(jxKeyboardInputDevice);
        JXInputManager.mKBDevices.remove(jxKeyboardInputDevice);
        jxKeyboardInputDevice.shutdown();
    }
    
    public static JXVirtualInputDevice createVirtualDevice() {
        final JXVirtualInputDevice jxVirtualInputDevice = new JXVirtualInputDevice();
        JXInputManager.mDevices.add(jxVirtualInputDevice);
        JXInputManager.mVirtualDevices.add(jxVirtualInputDevice);
        return jxVirtualInputDevice;
    }
    
    public static void deleteVirtualDevice(final JXVirtualInputDevice jxVirtualInputDevice) {
        JXInputManager.mDevices.remove(jxVirtualInputDevice);
        JXInputManager.mVirtualDevices.remove(jxVirtualInputDevice);
    }
    
    static {
        mDevices = new ArrayList<JXInputDevice>();
        mDIDevices = new ArrayList<DirectInputDevice>();
        mVirtualDevices = new ArrayList<JXVirtualInputDevice>();
        mKBDevices = new ArrayList<JXKeyboardInputDevice>();
        reset();
    }
}
