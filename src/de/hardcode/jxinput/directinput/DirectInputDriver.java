// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.directinput;

class DirectInputDriver
{
    private static final String NATIVE_LIB_NAME = "jxinput";
    static boolean sIsOperational;
    private static double[][] sAxisValues;
    private static boolean[][] sButtonStates;
    private static int[][] sDirectionalValues;
    
    private static final void init() {
        DirectInputDriver.sIsOperational = false;
        if (nativeinit()) {
            final int numberOfDevices = getNumberOfDevices();
            DirectInputDriver.sAxisValues = new double[numberOfDevices][getMaxNumberOfAxes()];
            DirectInputDriver.sButtonStates = new boolean[numberOfDevices][getMaxNumberOfButtons()];
            DirectInputDriver.sDirectionalValues = new int[numberOfDevices][getMaxNumberOfDirectionals()];
            bind();
            DirectInputDriver.sIsOperational = true;
        }
    }
    
    private static native boolean nativeinit();
    
    private static native void nativeexit();
    
    private static native void bind();
    
    static native int getNumberOfDevices();
    
    static native String getName(final int p0);
    
    static native int getNumberOfAxes(final int p0);
    
    static native int getNumberOfButtons(final int p0);
    
    static native int getNumberOfDirectionals(final int p0);
    
    static native int getMaxNumberOfAxes();
    
    static native int getMaxNumberOfButtons();
    
    static native int getMaxNumberOfDirectionals();
    
    static native boolean isAxisAvailable(final int p0, final int p1);
    
    static native String getAxisName(final int p0, final int p1);
    
    static native int getAxisType(final int p0, final int p1);
    
    static native boolean isButtonAvailable(final int p0, final int p1);
    
    static native String getButtonName(final int p0, final int p1);
    
    static native int getButtonType(final int p0, final int p1);
    
    static native boolean isDirectionalAvailable(final int p0, final int p1);
    
    static native String getDirectionalName(final int p0, final int p1);
    
    static native void nativeupdate();
    
    public static boolean isAvailable() {
        return DirectInputDriver.sIsOperational;
    }
    
    static void shutdown() {
        nativeexit();
        DirectInputDriver.sAxisValues = null;
        DirectInputDriver.sButtonStates = null;
        DirectInputDriver.sDirectionalValues = null;
    }
    
    static void reset() {
        shutdown();
        init();
    }
    
    static double getAxisValue(final int n, final int n2) {
        return DirectInputDriver.sAxisValues[n][n2];
    }
    
    static boolean getButtonState(final int n, final int n2) {
        return DirectInputDriver.sButtonStates[n][n2];
    }
    
    static int getDirection(final int n, final int n2) {
        return DirectInputDriver.sDirectionalValues[n][n2];
    }
    
    public static void main(final String[] array) {
        if (!DirectInputDriver.sIsOperational) {
            return;
        }
        for (int i = 0; i < 5000; ++i) {
            nativeupdate();
        }
        shutdown();
    }
    
    static {
        DirectInputDriver.sIsOperational = false;
        try {
            System.loadLibrary("jxinput");
            init();
        }
        catch (SecurityException ex) {
            Log.logger.warning("Native library jxinput not loaded due to a SecurityException.");
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            Log.logger.info("Native library jxinput not loaded due to an UnsatisfiedLinkError.");
        }
    }
}
