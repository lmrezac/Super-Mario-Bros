// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput;

public interface Axis extends Feature
{
    public static final int ID_X = 0;
    public static final int ID_Y = 1;
    public static final int ID_Z = 2;
    public static final int ID_ROTX = 3;
    public static final int ID_ROTY = 4;
    public static final int ID_ROTZ = 5;
    public static final int ID_SLIDER0 = 6;
    public static final int ID_SLIDER1 = 7;
    public static final int NUMBER_OF_ID = 8;
    public static final int TRANSLATION = 0;
    public static final int ROTATION = 1;
    public static final int SLIDER = 2;
    
    int getType();
    
    double getValue();
    
    double getResolution();
}
