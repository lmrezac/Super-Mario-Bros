// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.event;

import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Axis;

import java.util.ArrayList;

public class JXInputEventManager
{
    private static final ArrayList<axislistener> mAxisEventListeners;
    private static final ArrayList<buttonlistener> mButtonEventListeners;
    private static final ArrayList<directionallistener> mDirectionalEventListeners;
    private static autotrigger mAutoTrigger;
    
    public static void reset() {
        JXInputEventManager.mAxisEventListeners.clear();
        JXInputEventManager.mButtonEventListeners.clear();
        JXInputEventManager.mDirectionalEventListeners.clear();
    }
    
    public static void trigger() {
        for (int size = JXInputEventManager.mAxisEventListeners.size(), i = 0; i < size; ++i) {
            JXInputEventManager.mAxisEventListeners.get(i).checkTrigger();
        }
        for (int size2 = JXInputEventManager.mButtonEventListeners.size(), j = 0; j < size2; ++j) {
            JXInputEventManager.mButtonEventListeners.get(j).checkTrigger();
        }
        for (int size3 = JXInputEventManager.mDirectionalEventListeners.size(), k = 0; k < size3; ++k) {
            JXInputEventManager.mDirectionalEventListeners.get(k).checkTrigger();
        }
    }
    
    public static void setTriggerIntervall(final int n) {
        if (null != JXInputEventManager.mAutoTrigger) {
            JXInputEventManager.mAutoTrigger.mFinish = true;
            try {
                JXInputEventManager.mAutoTrigger.join();
            }
            catch (InterruptedException ex) {}
        }
        JXInputEventManager.mAutoTrigger = null;
        if (n > 0) {
            (JXInputEventManager.mAutoTrigger = new autotrigger(n)).start();
        }
    }
    
    public static void addListener(final JXInputAxisEventListener jxInputAxisEventListener, final Axis axis, final double n) {
        JXInputEventManager.mAxisEventListeners.add(new axislistener(jxInputAxisEventListener, axis, n));
    }
    
    public static void addListener(final JXInputAxisEventListener jxInputAxisEventListener, final Axis axis) {
        JXInputEventManager.mAxisEventListeners.add(new axislistener(jxInputAxisEventListener, axis, axis.getResolution()));
    }
    
    public static void removeListener(final JXInputAxisEventListener jxInputAxisEventListener) {
        JXInputEventManager.mAxisEventListeners.remove(jxInputAxisEventListener);
    }
    
    public static void addListener(final JXInputButtonEventListener jxInputButtonEventListener, final Button button) {
        JXInputEventManager.mButtonEventListeners.add(new buttonlistener(jxInputButtonEventListener, button));
    }
    
    public static void removeListener(final JXInputButtonEventListener jxInputButtonEventListener) {
        JXInputEventManager.mButtonEventListeners.remove(jxInputButtonEventListener);
    }
    
    public static void addListener(final JXInputDirectionalEventListener jxInputDirectionalEventListener, final Directional directional, final double n) {
        JXInputEventManager.mDirectionalEventListeners.add(new directionallistener(jxInputDirectionalEventListener, directional, n));
    }
    
    public static void addListener(final JXInputDirectionalEventListener jxInputDirectionalEventListener, final Directional directional) {
        JXInputEventManager.mDirectionalEventListeners.add(new directionallistener(jxInputDirectionalEventListener, directional, directional.getResolution()));
    }
    
    public static void removeListener(final JXInputDirectionalEventListener jxInputDirectionalEventListener) {
        JXInputEventManager.mDirectionalEventListeners.remove(jxInputDirectionalEventListener);
    }
    
    static {
        mAxisEventListeners = new ArrayList<axislistener>();
        mButtonEventListeners = new ArrayList<buttonlistener>();
        mDirectionalEventListeners = new ArrayList<directionallistener>();
        JXInputEventManager.mAutoTrigger = null;
    }
    
    private static final class autotrigger extends Thread
    {
        boolean mFinish;
        final int mDelay;
        
        autotrigger(final int mDelay) {
            this.mFinish = false;
            this.mDelay = mDelay;
        }
        
        public void run() {
            while (!this.mFinish) {
                try {
                    Thread.sleep(this.mDelay);
                    JXInputManager.updateFeatures();
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    private static class directionallistener
    {
        final JXInputDirectionalEventListener mListener;
        final double mValueTreshold;
        final JXInputDirectionalEvent mEvent;
        double mLastValueFired;
        boolean mLastCenteredFired;
        int mLastDirectionFired;
        
        directionallistener(final JXInputDirectionalEventListener mListener, final Directional directional, final double mValueTreshold) {
            this.mLastValueFired = 0.0;
            this.mLastCenteredFired = true;
            this.mLastDirectionFired = 0;
            this.mListener = mListener;
            this.mValueTreshold = mValueTreshold;
            this.mEvent = new JXInputDirectionalEvent(directional);
        }
        
        final void checkTrigger() {
            final double value = this.mEvent.getDirectional().getValue();
            final int direction = this.mEvent.getDirectional().getDirection();
            final boolean centered = this.mEvent.getDirectional().isCentered();
            final double mValueDelta = value - this.mLastValueFired;
            final int mDirectionDelta = direction - this.mLastDirectionFired;
            final boolean b = this.mLastCenteredFired != centered;
            if (Math.abs(mValueDelta) >= this.mValueTreshold || Math.abs(mDirectionDelta) > 0 || b) {
                this.mLastValueFired = value;
                this.mLastDirectionFired = direction;
                this.mLastCenteredFired = centered;
                this.mEvent.mValueDelta = mValueDelta;
                this.mEvent.mDirectionDelta = mDirectionDelta;
                this.mListener.changed(this.mEvent);
            }
        }
    }
    
    private static class buttonlistener
    {
        final JXInputButtonEventListener mListener;
        final JXInputButtonEvent mEvent;
        boolean mLastValueFired;
        
        buttonlistener(final JXInputButtonEventListener mListener, final Button button) {
            this.mLastValueFired = false;
            this.mListener = mListener;
            this.mEvent = new JXInputButtonEvent(button);
        }
        
        final void checkTrigger() {
            final boolean state = this.mEvent.getButton().getState();
            if (state != this.mLastValueFired) {
                this.mLastValueFired = state;
                this.mListener.changed(this.mEvent);
            }
        }
    }
    
    private static class axislistener
    {
        final JXInputAxisEventListener mListener;
        final double mTreshold;
        final JXInputAxisEvent mEvent;
        double mLastValueFired;
        
        axislistener(final JXInputAxisEventListener mListener, final Axis axis, final double mTreshold) {
            this.mLastValueFired = 0.0;
            this.mListener = mListener;
            this.mTreshold = mTreshold;
            this.mEvent = new JXInputAxisEvent(axis);
        }
        
        final void checkTrigger() {
            final double value = this.mEvent.getAxis().getValue();
            final double mDelta = value - this.mLastValueFired;
            if (Math.abs(mDelta) >= this.mTreshold) {
                this.mLastValueFired = value;
                this.mEvent.mDelta = mDelta;
                this.mListener.changed(this.mEvent);
            }
        }
    }
}
