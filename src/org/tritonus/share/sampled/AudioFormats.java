// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import javax.sound.sampled.AudioFormat;

public class AudioFormats
{
    private static boolean doMatch(final int i1, final int i2) {
        return i1 == -1 || i2 == -1 || i1 == i2;
    }
    
    private static boolean doMatch(final float f1, final float f2) {
        return f1 == -1.0f || f2 == -1.0f || Math.abs(f1 - f2) < 1.0E-9;
    }
    
    public static boolean matches(final AudioFormat format1, final AudioFormat format2) {
        return format1.getEncoding().equals(format2.getEncoding()) && (format2.getSampleSizeInBits() <= 8 || format1.getSampleSizeInBits() == -1 || format2.getSampleSizeInBits() == -1 || format1.isBigEndian() == format2.isBigEndian()) && doMatch(format1.getChannels(), format2.getChannels()) && doMatch(format1.getSampleSizeInBits(), format2.getSampleSizeInBits()) && doMatch(format1.getFrameSize(), format2.getFrameSize()) && doMatch(format1.getSampleRate(), format2.getSampleRate()) && doMatch(format1.getFrameRate(), format2.getFrameRate());
    }
    
    public static boolean equals(final AudioFormat format1, final AudioFormat format2) {
        return format1.getEncoding().equals(format2.getEncoding()) && format1.getChannels() == format2.getChannels() && format1.getSampleSizeInBits() == format2.getSampleSizeInBits() && format1.getFrameSize() == format2.getFrameSize() && Math.abs(format1.getSampleRate() - format2.getSampleRate()) < 1.0E-9 && Math.abs(format1.getFrameRate() - format2.getFrameRate()) < 1.0E-9;
    }
}
