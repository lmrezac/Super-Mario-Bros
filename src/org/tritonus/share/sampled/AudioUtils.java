// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import java.util.Iterator;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class AudioUtils
{
    public static long getLengthInBytes(final AudioInputStream audioInputStream) {
        return getLengthInBytes(audioInputStream.getFormat(), audioInputStream.getFrameLength());
    }
    
    public static long getLengthInBytes(final AudioFormat audioFormat, final long lLengthInFrames) {
        final int nFrameSize = audioFormat.getFrameSize();
        if (lLengthInFrames >= 0L && nFrameSize >= 1) {
            return lLengthInFrames * nFrameSize;
        }
        return -1L;
    }
    
    public static boolean containsFormat(final AudioFormat sourceFormat, final Iterator possibleFormats) {
        while (possibleFormats.hasNext()) {
            final AudioFormat format = (AudioFormat) possibleFormats.next();
            if (AudioFormats.matches(format, sourceFormat)) {
                return true;
            }
        }
        return false;
    }
    
    public static long millis2Bytes(final long ms, final AudioFormat format) {
        return millis2Bytes(ms, format.getFrameRate(), format.getFrameSize());
    }
    
    public static long millis2Bytes(final long ms, final float frameRate, final int frameSize) {
        return (long)(ms * frameRate / 1000.0f * frameSize);
    }
    
    public static long millis2BytesFrameAligned(final long ms, final AudioFormat format) {
        return millis2BytesFrameAligned(ms, format.getFrameRate(), format.getFrameSize());
    }
    
    public static long millis2BytesFrameAligned(final long ms, final float frameRate, final int frameSize) {
        return (long)(ms * frameRate / 1000.0f) * frameSize;
    }
    
    public static long millis2Frames(final long ms, final AudioFormat format) {
        return millis2Frames(ms, format.getFrameRate());
    }
    
    public static long millis2Frames(final long ms, final float frameRate) {
        return (long)(ms * frameRate / 1000.0f);
    }
    
    public static long bytes2Millis(final long bytes, final AudioFormat format) {
        return (long)(bytes / format.getFrameRate() * 1000.0f / format.getFrameSize());
    }
    
    public static long frames2Millis(final long frames, final AudioFormat format) {
        return (long)(frames / format.getFrameRate() * 1000.0f);
    }
    
    public static String NS_or_number(final int number) {
        return (number == -1) ? "NOT_SPECIFIED" : String.valueOf(number);
    }
    
    public static String NS_or_number(final float number) {
        return (number == -1.0f) ? "NOT_SPECIFIED" : String.valueOf(number);
    }
    
    public static String format2ShortStr(final AudioFormat format) {
        return format.getEncoding() + "-" + NS_or_number(format.getChannels()) + "ch-" + NS_or_number(format.getSampleSizeInBits()) + "bit-" + NS_or_number((int)format.getSampleRate()) + "Hz-" + (format.isBigEndian() ? "be" : "le");
    }
}
