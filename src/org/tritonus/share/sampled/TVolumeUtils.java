// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

public class TVolumeUtils
{
    private static final double FACTOR1;
    private static final double FACTOR2 = 0.05;
    
    public static double lin2log(final double dLinear) {
        return TVolumeUtils.FACTOR1 * Math.log(dLinear);
    }
    
    public static double log2lin(final double dLogarithmic) {
        return Math.pow(10.0, dLogarithmic * 0.05);
    }
    
    static {
        FACTOR1 = 20.0 / Math.log(10.0);
    }
}
