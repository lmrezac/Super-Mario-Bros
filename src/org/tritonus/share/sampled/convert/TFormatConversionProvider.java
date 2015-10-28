// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.convert;

import org.tritonus.share.sampled.AudioFormats;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.spi.FormatConversionProvider;

public abstract class TFormatConversionProvider extends FormatConversionProvider
{
    protected static final AudioFormat.Encoding[] EMPTY_ENCODING_ARRAY;
    protected static final AudioFormat[] EMPTY_FORMAT_ARRAY;
    
    public AudioInputStream getAudioInputStream(final AudioFormat.Encoding targetEncoding, final AudioInputStream audioInputStream) {
        final AudioFormat sourceFormat = audioInputStream.getFormat();
        final AudioFormat targetFormat = new AudioFormat(targetEncoding, -1.0f, -1, -1, -1, -1.0f, sourceFormat.isBigEndian());
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TFormatConversionProvider.getAudioInputStream(AudioFormat.Encoding, AudioInputStream):");
            TDebug.out("trying to convert to " + targetFormat);
        }
        return this.getAudioInputStream(targetFormat, audioInputStream);
    }
    
    public boolean isConversionSupported(final AudioFormat targetFormat, final AudioFormat sourceFormat) {
        if (TDebug.TraceAudioConverter) {
            TDebug.out(">TFormatConversionProvider.isConversionSupported(AudioFormat, AudioFormat):");
            TDebug.out("class: " + this.getClass().getName());
            TDebug.out("checking if conversion possible");
            TDebug.out("from: " + sourceFormat);
            TDebug.out("to: " + targetFormat);
        }
        final AudioFormat[] aTargetFormats = this.getTargetFormats(targetFormat.getEncoding(), sourceFormat);
        for (int i = 0; i < aTargetFormats.length; ++i) {
            if (TDebug.TraceAudioConverter) {
                TDebug.out("checking against possible target format: " + aTargetFormats[i]);
            }
            if (aTargetFormats[i] != null && AudioFormats.matches(aTargetFormats[i], targetFormat)) {
                if (TDebug.TraceAudioConverter) {
                    TDebug.out("<result=true");
                }
                return true;
            }
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("<result=false");
        }
        return false;
    }
    
    public AudioFormat getMatchingFormat(final AudioFormat targetFormat, final AudioFormat sourceFormat) {
        if (TDebug.TraceAudioConverter) {
            TDebug.out(">TFormatConversionProvider.isConversionSupported(AudioFormat, AudioFormat):");
            TDebug.out("class: " + this.getClass().getName());
            TDebug.out("checking if conversion possible");
            TDebug.out("from: " + sourceFormat);
            TDebug.out("to: " + targetFormat);
        }
        final AudioFormat[] aTargetFormats = this.getTargetFormats(targetFormat.getEncoding(), sourceFormat);
        for (int i = 0; i < aTargetFormats.length; ++i) {
            if (TDebug.TraceAudioConverter) {
                TDebug.out("checking against possible target format: " + aTargetFormats[i]);
            }
            if (aTargetFormats[i] != null && AudioFormats.matches(aTargetFormats[i], targetFormat)) {
                if (TDebug.TraceAudioConverter) {
                    TDebug.out("<result=true");
                }
                return aTargetFormats[i];
            }
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("<result=false");
        }
        return null;
    }
    
    static {
        EMPTY_ENCODING_ARRAY = new AudioFormat.Encoding[0];
        EMPTY_FORMAT_ARRAY = new AudioFormat[0];
    }
}
